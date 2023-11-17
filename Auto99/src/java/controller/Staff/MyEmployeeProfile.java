/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.Staff;

import LocationFile.Location;
import dal.EmployeeProfileDAO;
import jakarta.servlet.ServletContext;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Account;
import model.EmployeeProfile;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

/**
 *
 * @author HieuHT
 */
@MultipartConfig(fileSizeThreshold = 2048 * 2048 * 2,
        maxFileSize = 2048 * 2048 * 10,
        maxRequestSize = 2048 * 2048 * 50)

@WebServlet(name = "MyEmployeeProfile", urlPatterns = {"/myemployeeprofile"})
public class MyEmployeeProfile extends BaseAuthencationRequire {

 EmployeeProfileDAO employeeProfileDAO = new EmployeeProfileDAO();

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void processGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Get the Account object from the session
        Account acc = (Account) request.getSession().getAttribute("acc");

        // Get the EmployeeProfile using accID
        EmployeeProfile employeeProfile = null;
        try {
            employeeProfile = employeeProfileDAO.getEmployeeProfileByAccID(acc.getAccID());
        } catch (SQLException ex) {
            Logger.getLogger(MyEmployeeProfile.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Set the EmployeeProfile as an attribute
        request.setAttribute("employeeProfile", employeeProfile);

        // Forward the request to ShowMyProfile.jsp
        request.getRequestDispatcher("./Staff/ShowMyProfile.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void processPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("ACTION");
        //lấy thông tin account dã login hiện tại
        Account acc = (Account) request.getSession().getAttribute("acc");
        
        if ("UpdateIMGEmployee".equals(action)) {
            //lấy dữ liệu từ add blog
            Location lo = new Location();
            int employeeID = Integer.parseInt(request.getParameter("employeeID"));

            //get parameter main image of the product
            Part partFileImageUpload = request.getPart("ImageUpload");
            String ImageUpload = "";

            String allowedExtensions = ".jpg|.jpeg|.png|.gif";

            if (partFileImageUpload == null) {
                ImageUpload = "";
            } else {
                ImageUpload = partFileImageUpload.getSubmittedFileName();
            }

            if (ImageUpload != null && !ImageUpload.isEmpty()) {
                String fileExtension = ImageUpload.substring(ImageUpload.lastIndexOf("."));
                if (allowedExtensions.contains(fileExtension.toLowerCase())) {
                    try {
                        StringBuilder absolutePathDirectory = Location.getRelativePath(request.getServletContext(), lo.LOADIMAGETOFILEIMGEMPLOYEE);
                        // Generate a unique folder name based on a timestamp or some other identifier.
                        String newEmployeeIMGFolder = String.valueOf(employeeID);
                        // Create the directory if it doesn't exist.
                        File directory = new File(absolutePathDirectory + newEmployeeIMGFolder);
                        if (directory.exists()) {
                           // Get a list of all files in the directory.xóa ảnh cũ
                            File[] files = directory.listFiles();

                            // If there are any files, delete them all.
                            if (files != null) {
                                for (File file : files) {
                                    if (file.isFile()) {
                                        file.delete();
                                    }
                                }
                            }
                        }else{
                             directory.mkdirs();
                        }

                        // Build the absolute path to the image file.
                        String absolutePath = absolutePathDirectory + newEmployeeIMGFolder + "/";

                        //load image to file img
                        DiskFileItemFactory factory = new DiskFileItemFactory();
                        ServletContext servletContext = this.getServletConfig().getServletContext();
                        File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
                        factory.setRepository(repository);
                        ServletFileUpload upload = new ServletFileUpload(factory);

                        upload.setHeaderEncoding("UTF-8");
                        OutputStream out = null;
                        InputStream filecontent = null;
                        try {
                            //read the image loaded into the file for main image of product
                            File fileImageUpload = new File(absolutePath + ImageUpload);
                            out = new FileOutputStream(fileImageUpload);
                            filecontent = partFileImageUpload.getInputStream();
                            int readImageUpload = 0;
                            final byte[] bytesImageUpload = new byte[2048];
                            while ((readImageUpload = filecontent.read(bytesImageUpload)) != -1) {
                                out.write(bytesImageUpload, 0, readImageUpload);
                            }
                        } catch (FileNotFoundException fne) {
                            System.out.println(fne);
                        }
                        //update new img
                        employeeProfileDAO.updateEmployeeProfile(employeeID, null , null , null , null, ImageUpload , null , null , null , null , null , acc.getAccID());

                        // Forward the request to ShowMyProfile.jsp
                        doGet(request, response);
                    } catch (SQLException ex) {
                        Logger.getLogger(MyEmployeeProfile.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ParseException ex) {
                        Logger.getLogger(MyEmployeeProfile.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    request.setAttribute("mess", "File không hợp lệ");
                    // Forward the request to ShowMyProfile.jsp
                    request.getRequestDispatcher("./Staff/ShowMyProfile.jsp").forward(request, response);
                }
            } else {
                request.setAttribute("mess", "Không tìm thấy file");
                // Forward the request to ShowMyProfile.jsp
                request.getRequestDispatcher("ShowMyProfile.jsp").forward(request, response);
            }
        }

    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
