/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.Staff;

import LocationFile.Location;
import dal.BrandDAO;
import dal.CarDAO;
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
import java.util.List;
import model.Account;
import model.CarBrand;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

@MultipartConfig(fileSizeThreshold = 2048 * 2048 * 2,
        maxFileSize = 2048 * 2048 * 10,
        maxRequestSize = 2048 * 2048 * 50)
@WebServlet(name = "BrandList", urlPatterns = {"/brandlist"})
public class BrandList extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

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
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            String pageIndex = request.getParameter("index");
            if (pageIndex == null) {
                pageIndex = "1";
            }
            int index = Integer.parseInt(pageIndex);

            String name = request.getParameter("name");
            if (name == null) {
                name = "";
            }

            String quantityShow = request.getParameter("quantity");
            if (quantityShow == null) {
                quantityShow = "5";
            }
            int quantity = Integer.parseInt(quantityShow);

            BrandDAO dao = new BrandDAO();
            List<CarBrand> BrandList = dao.getAllBrandByName(index, quantity, name);
            int count = dao.getTotalBrandByName(name);
            int endPage = count / quantity;
            if (count % quantity != 0) {
                endPage++;
            }

            request.setAttribute("endPage", endPage);
            request.setAttribute("BrandList", BrandList);
            request.setAttribute("index", index);
            request.setAttribute("count", count);
            request.setAttribute("quantity", quantity);
            request.setAttribute("name", name);
            request.getRequestDispatcher("./Staff/BrandList.jsp").forward(request, response);
        }
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Location lo = new Location();
        BrandDAO dao = new BrandDAO();
        Account acc = (Account) request.getSession().getAttribute("acc");

        String action = request.getParameter("action");

        if ("AddBrand".equals(action)) {
            String brandName = request.getParameter("brandName");

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

                    // Define the directory where the image will be saved.
                    StringBuilder absolutePath = Location.getRelativePath(request.getServletContext(), lo.LOADIMAGETOFILEIMGBRAND);
                    //load image to file img
                    DiskFileItemFactory factory = new DiskFileItemFactory();
                    ServletContext servletContext = this.getServletConfig().getServletContext();
                    File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
                    factory.setRepository(repository);
                    ServletFileUpload upload = new ServletFileUpload(factory);
                    upload.setHeaderEncoding("UTF-8");
                    readImageLoaded(absolutePath, ImageUpload, partFileImageUpload);

                    dao.addBrand(brandName, ImageUpload, acc.getAccID());

                    String pageIndex = request.getParameter("index");
                    if (pageIndex == null) {
                        pageIndex = "1";
                    }
                    int index = Integer.parseInt(pageIndex);

                    String name = request.getParameter("name");
                    if (name == null) {
                        name = "";
                    }

                    String quantityShow = request.getParameter("quantity");
                    if (quantityShow == null) {
                        quantityShow = "5";
                    }
                    int quantity = Integer.parseInt(quantityShow);

                    request.setAttribute("index", index);
                    request.setAttribute("quantity", quantity);
                    request.setAttribute("name", name);

                    doGet(request, response);

                } else {
                    request.setAttribute("mess", "File không hợp lệ");
                    request.getRequestDispatcher("./Staff/BrandList.jsp").forward(request, response);
                }
            } else {
                request.setAttribute("mess", "Không tìm thấy file");
                request.getRequestDispatcher("./Staff/BrandList.jsp").forward(request, response);
            }
        } else if ("UpdateBrand".equals(action)) {
            int brandID = Integer.parseInt(request.getParameter("brandID"));
            String brandName = request.getParameter("brandName");

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

                    // Define the directory where the image will be saved.
                    StringBuilder absolutePath = Location.getRelativePath(request.getServletContext(), lo.LOADIMAGETOFILEIMGBRAND);
                    //load image to file img
                    DiskFileItemFactory factory = new DiskFileItemFactory();
                    ServletContext servletContext = this.getServletConfig().getServletContext();
                    File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
                    factory.setRepository(repository);
                    ServletFileUpload upload = new ServletFileUpload(factory);
                    upload.setHeaderEncoding("UTF-8");
                    
                    String imgName = dao.getIMGname(brandID);
                    deleteImage(absolutePath + imgName);
                    
                    readImageLoaded(absolutePath, ImageUpload, partFileImageUpload);
                    dao.updateBrand(brandName, ImageUpload, brandID, acc.getAccID());

                    String pageIndex = request.getParameter("index");
                    if (pageIndex == null) {
                        pageIndex = "1";
                    }
                    int index = Integer.parseInt(pageIndex);

                    String name = request.getParameter("name");
                    if (name == null) {
                        name = "";
                    }

                    String quantityShow = request.getParameter("quantity");
                    if (quantityShow == null) {
                        quantityShow = "5";
                    }
                    int quantity = Integer.parseInt(quantityShow);

                    request.setAttribute("index", index);
                    request.setAttribute("quantity", quantity);
                    request.setAttribute("name", name);

                    doGet(request, response);

                } else {
                    request.setAttribute("mess", "File không hợp lệ");
                    request.getRequestDispatcher("./Staff/BrandList.jsp").forward(request, response);
                }
            } else {
                dao.updateBrand(brandName, brandID);
                doGet(request, response);
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

    public void readImageLoaded(StringBuilder absolutePath, String ImageUpload, Part partFileImageUpload) throws FileNotFoundException, IOException {
        OutputStream out = null;
        InputStream filecontent = null;
        File fileImageUpload = new File(absolutePath + ImageUpload);
        out = new FileOutputStream(fileImageUpload);
        filecontent = partFileImageUpload.getInputStream();
        int readImageUpload = 0;
        final byte[] bytesImageUpload = new byte[2048];
        while ((readImageUpload = filecontent.read(bytesImageUpload)) != -1) {
            out.write(bytesImageUpload, 0, readImageUpload);
        }
        IOUtils.closeQuietly(out);
    }

    public void deleteImage(String URL) {
        File img = new File(URL);           //file to be delete  
        if (img.delete()) //returns Boolean value  
        {
            System.out.println(img.getName() + " deleted");   //getting and printing the file name  
        } else {
            System.out.println("failed");
        }
    }

}
