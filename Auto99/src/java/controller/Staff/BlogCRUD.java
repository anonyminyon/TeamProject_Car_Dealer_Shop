/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.Staff;

import LocationFile.Location;
import dal.BlogDAO;
import jakarta.servlet.ServletContext;
import java.io.IOException;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import model.Blog;
import model.BlogCategory;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

/**
 *
 * @author Dao Anh Duc
 */
@MultipartConfig(fileSizeThreshold = 2048 * 2048 * 2,
        maxFileSize = 2048 * 2048 * 10,
        maxRequestSize = 2048 * 2048 * 50)
@WebServlet(name = "BlogCRUD", urlPatterns = {"/blogcrud"})

public class BlogCRUD extends BaseAuthencationAndAuthorRequire {

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
        response.setContentType("text/html;charset=UTF-8");
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
    protected void processGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        BlogDAO blogDAO = new BlogDAO();
        if ("InsertBlog".equals(action)) {
            List<Blog> listbpi = blogDAO.getAllBlogSubCategory();
            request.setAttribute("ListPI", listbpi);
            request.getRequestDispatcher("./Staff/AddBlogPage.jsp").forward(request, response);
        } else if ("InsertBlogSubcategory".equals(action)) {
            List<BlogCategory> listbc = blogDAO.getAllBlogCategory();
            request.setAttribute("ListBC", listbc);
            request.getRequestDispatcher("./Staff/AddBlogSubCategory.jsp").forward(request, response);
        } else if ("AdvancedUpdateBlog".equals(action)) {
            List<Blog> listbpi = blogDAO.getAllBlogSubCategory();
            request.setAttribute("ListPI", listbpi);
            int blogID = Integer.parseInt(request.getParameter("blogID"));
            request.setAttribute("blogID", blogID);
            Blog b = blogDAO.getBlogByBlogID(blogID);
            request.setAttribute("Blog", b);
            request.getRequestDispatcher("./Staff/AdvancedUpdateBlog.jsp").forward(request, response);
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
    protected void processPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        //tạo đối tượng gọi các hàm BlogDAO
        BlogDAO bDAO = new BlogDAO();
        if ("InsertBlog".equals(action)) {
            //lấy dữ liệu từ add blog
            Location lo = new Location();
            String parentID = request.getParameter("parentID");
            String description = request.getParameter("description").trim();
            String title = request.getParameter("title").trim();
            if (bDAO.checkTitleExist(title)) {
                response.sendRedirect("blogcrud?action=InsertBlog&&error=2");
                return;
            }
            LocalDateTime currentDateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String createdOn = currentDateTime.format(formatter);
            String content = request.getParameter("content").trim();
            int createdBy = Integer.parseInt(request.getParameter("user"));

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

                    ImageUpload = title + fileExtension;
                    //load image to file img
                    DiskFileItemFactory factory = new DiskFileItemFactory();
                    ServletContext servletContext = this.getServletConfig().getServletContext();
                    File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
                    factory.setRepository(repository);
                    ServletFileUpload upload = new ServletFileUpload(factory);
                    upload.setHeaderEncoding("UTF-8");
                    OutputStream out = null;
                    InputStream filecontent = null;
                    StringBuilder absolutePath = Location.getRelativePath(request.getServletContext(), lo.LOADIMAGETOFILEIMGBLOG);
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
                    //add new blog
                    bDAO.InsertBlog(parentID, title, description, content, createdOn, createdBy, ImageUpload);
                    response.sendRedirect("bloglist");
                } else {
                    response.sendRedirect("blogcrud?action=InsertBlog&&error=1");
                }
            } else {
                request.getRequestDispatcher("./Staff/AddBlogPage.jsp").forward(request, response);
            }
        } else if ("UpdateBlog".equals(action)) {
            int modifiedBy = Integer.parseInt(request.getParameter("user"));
            String blogIDParam = request.getParameter("blogID");
            int blogID = 0;

            if (blogIDParam != null && !blogIDParam.isEmpty()) {
                blogID = Integer.parseInt(blogIDParam);
            }
            boolean status = Boolean.parseBoolean(request.getParameter("status"));
            LocalDateTime currentDateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String dateTimeUpdate = currentDateTime.format(formatter);
            bDAO.updateBlog(dateTimeUpdate, modifiedBy, status, blogID);
            response.sendRedirect("bloglist");

        } else if ("InsertBlogSubcategory".equals(action)) {
            //lấy dữ liệu từ add blog
            String blogCategoryID = request.getParameter("blogCategoryID");
            String title = request.getParameter("title").trim();
            LocalDateTime currentDateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String modifiedOn = currentDateTime.format(formatter);
            String accID = request.getParameter("accID");

            //add new blog
            bDAO.InsertBlogSubCategory(blogCategoryID, title, modifiedOn, accID);
            response.sendRedirect("bloglist");

        } else if ("AdvancedUpdateBlog".equals(action)) {

            // Lấy dữ liệu từ add blog
            Location lo = new Location();
            String blogID = request.getParameter("blogID");
            String parentID = request.getParameter("parentID");
            String description = request.getParameter("description").trim();
            String title = request.getParameter("title").trim();
            LocalDateTime currentDateTime = LocalDateTime.now();
            int modifiedBy = Integer.parseInt(request.getParameter("user"));
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String modifiedOn = currentDateTime.format(formatter);

            String content = request.getParameter("content").trim();
            String accID = request.getParameter("accID");

            // Get parameter main image of the product
            Part partFileImageUpload = request.getPart("ImageUpload");
            String ImageUpload = "";

            String allowedExtensions = ".jpg|.jpeg|.png|.gif";

            if (partFileImageUpload == null || partFileImageUpload.getSize() == 0) {
                // Không chọn ảnh mới, giữ lại ảnh cũ
                ImageUpload = bDAO.getMainImageName(blogID);
                bDAO.advancedUpdateBlog(parentID, title, description, content, modifiedOn, modifiedBy, ImageUpload, blogID);
                response.sendRedirect("blogcrud?action=AdvancedUpdateBlog&blogID=" + blogID);
            } else {
                ImageUpload = partFileImageUpload.getSubmittedFileName();
                String fileExtension = ImageUpload.substring(ImageUpload.lastIndexOf("."));
                if (allowedExtensions.contains(fileExtension.toLowerCase())) {

                    ImageUpload = title + fileExtension;
                    // Load image to file img
                    DiskFileItemFactory factory = new DiskFileItemFactory();
                    ServletContext servletContext = this.getServletConfig().getServletContext();
                    File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
                    factory.setRepository(repository);
                    ServletFileUpload upload = new ServletFileUpload(factory);
                    upload.setHeaderEncoding("UTF-8");
                    OutputStream out = null;
                    InputStream filecontent = null;
                    StringBuilder absolutePath = Location.getRelativePath(request.getServletContext(), lo.LOADIMAGETOFILEIMGBLOG);
                    try {
                        String imgName = bDAO.getMainImageName(blogID);
                        deleteImage(absolutePath + imgName);
                        bDAO.removeImage(blogID);
                        // Read the image loaded into the file for the main image of the product
                        File fileImageUpload = new File(absolutePath + ImageUpload);
                        out = new FileOutputStream(fileImageUpload);
                        filecontent = partFileImageUpload.getInputStream();
                        int readImageUpload = 0;
                        final byte[] bytesImageUpload = new byte[2048];
                        while ((readImageUpload = filecontent.read(bytesImageUpload)) != -1) {
                            out.write(bytesImageUpload, 0, readImageUpload);
                        }
                        IOUtils.closeQuietly(out);
                    } catch (FileNotFoundException fne) {
                        System.out.println(fne);
                    }
                    // Add new blog
                    bDAO.advancedUpdateBlog(parentID, title, description, content, modifiedOn, modifiedBy, ImageUpload, blogID);
                    response.sendRedirect("blogcrud?action=AdvancedUpdateBlog&blogID=" + blogID);
                } else {
                    response.sendRedirect("blogcrud?action=AdvancedUpdateBlog&blogID=" + blogID + "&error=1");
                }
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
