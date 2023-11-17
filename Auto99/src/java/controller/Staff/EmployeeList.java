/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.Staff;

import LocationFile.Location;
import Message.ConstantMessages;
import Service.SendMail;
import Service.Sercurity;
import Service.TextEmail;
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
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import model.Account;
import model.EmployeeProfile;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

/**
 *
 * @author Admin
 */
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2,
        maxFileSize = 1024 * 1024 * 10,
        maxRequestSize = 1024 * 1024 * 50)
@WebServlet(name = "EmployeeList", urlPatterns = {"/employeelist"})
public class EmployeeList extends BaseAuthencationAndAuthorRequire {

    static EmployeeProfileDAO dao = new EmployeeProfileDAO();

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
    protected void processGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String numberEmployeeDisplay = request.getParameter("numberEmployeeDisplay");
        String page = request.getParameter("page");
        String search = request.getParameter("search");
        String action = request.getParameter("action");
        String filterName = request.getParameter("filterName");
        //validate request
        if (page == null || page.isEmpty()) {
            page = "1";
        }

        if (numberEmployeeDisplay == null || numberEmployeeDisplay.isEmpty()) {
            numberEmployeeDisplay = "5";
        }
        if (search == null) {
            search = "";
        }

        if (action == null) {
            action = "";
        }

        //Service function
        int currentPage = Integer.parseInt(page);
        ArrayList<EmployeeProfile> list = null;
        int totalClient = 0;
        int endPage = 0;
        if (action.equals("filter")) {
            list = dao.getListEmpployeeFilter(filterName, Integer.parseInt(page), Integer.parseInt(numberEmployeeDisplay));
            totalClient = dao.getTotalEmployee(action, filterName);
            endPage = totalClient / Integer.parseInt(numberEmployeeDisplay);
            if (totalClient % Integer.parseInt(numberEmployeeDisplay) != 0) {
                endPage++;
            }
        } else if (action.endsWith("search")) {
            list = dao.getListEmployeeSearch(search, Integer.parseInt(page), Integer.parseInt(numberEmployeeDisplay));
            totalClient = dao.getTotalEmployee(action, search);
            endPage = totalClient / Integer.parseInt(numberEmployeeDisplay);
            if (totalClient % Integer.parseInt(numberEmployeeDisplay) != 0) {
                endPage++;
            }
        } else {
            list = dao.getListEmployee(Integer.parseInt(page), Integer.parseInt(numberEmployeeDisplay));
            totalClient = dao.getTotalEmployee(action, search);
            endPage = totalClient / Integer.parseInt(numberEmployeeDisplay);
            if (totalClient % Integer.parseInt(numberEmployeeDisplay) != 0) {
                endPage++;
            }
        }
        request.setAttribute("listRole", dao.getListRole());
        request.setAttribute("search", search);
        request.setAttribute("numberEmployeeDisplay", numberEmployeeDisplay);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("page", page);
        request.setAttribute("endPage", endPage);
        request.setAttribute("listEmployee", list);
        request.getRequestDispatcher("./Staff/EmployeeList.jsp").forward(request, response);
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
        Location lo = new Location();
        ConstantMessages constanMessage = new ConstantMessages();
        HttpSession session = request.getSession();
        String action = request.getParameter("action");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String gender = request.getParameter("gender");
        String DOB = request.getParameter("DOB");
        String email = request.getParameter("email");
        String phoneNumber = request.getParameter("phoneNumber");
        String IDNo = request.getParameter("IDNo");
        String address = request.getParameter("address");
        String startDate = request.getParameter("startDate");
        Part partFileImg = request.getPart("img");
        String img = partFileImg.getSubmittedFileName();
        String roleId = request.getParameter("roleId");
        Account acc = (Account) session.getAttribute("acc");
        int modifiedBy = acc.getAccID();
        Timestamp modifiedOn = new Timestamp(System.currentTimeMillis());
        String employeeID = "";

        if (img == null || img.isEmpty()) {
            if (action.equals("edit")) {
                //Case use don't upload new image
                img = request.getParameter("img1");
                employeeID = request.getParameter("employeeID");
                dao.updateEmployeeById(Integer.parseInt(employeeID), firstName, lastName, email, phoneNumber, DOB, img, Integer.parseInt(gender), IDNo, address, startDate, modifiedBy, modifiedOn, Integer.parseInt(roleId));
                response.sendRedirect("employeelist");
                return;
            }
        }

        if (action.equals("add")) {
//            response.getWriter().print("1");
            SendMail sendMail = new SendMail();
            TextEmail textEmail = new TextEmail();
            Sercurity sercurity = new Sercurity();
            int createdBy = acc.getAccID();
            String accountName = request.getParameter("accountName");
            String password = sercurity.generateRandomString();

            try {
                String validate = dao.checkInsertEmployee(accountName, email, phoneNumber, IDNo);
                if (validate.equals(constanMessage.SUCCESS)) {
                    String title = "Chúc mừng bạn đã trở thành nhân viên của AUTO99";
                    String content = "Mật khẩu tài khoản của bạn";
                    String footer = "Đăng nhập ở đây";
                    String link = "http://localhost:8015/Auto99/loginpage";
                    sendMail.sendEmail(email, textEmail.textSendEmail(title, content, footer, password, link), "AUTO99: MẬT KHẨU CỦA BẠN");
                    int eId = dao.insertEmployee(firstName, lastName, email, phoneNumber, DOB, img, Integer.parseInt(gender), IDNo, address, startDate, createdBy, accountName, sercurity.MD5(password), Integer.parseInt(roleId));
                    employeeID = eId + "";
                    response.getWriter().print("success");
                } else {
//                    response.getWriter().print("error" + validate);
                    request.setAttribute("notification", validate);
                    request.getRequestDispatcher("./Staff/Notification.jsp").forward(request, response);
                    return;
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }

        if (action.equals("edit")) {
            //case user upload new image
            employeeID = request.getParameter("employeeID");
            dao.updateEmployeeById(Integer.parseInt(employeeID), firstName, lastName, email, phoneNumber, DOB, img, Integer.parseInt(gender), IDNo, address, startDate, modifiedBy, modifiedOn, Integer.parseInt(roleId));
        }

        //Image processing
        String allowedExtensions = ".jpg|.jpeg|.png|.gif";
        String fileExtension = img.substring(img.lastIndexOf("."));
        if (allowedExtensions.contains(fileExtension.toLowerCase())) {
            StringBuilder absolutePath = Location.getRelativePath(request.getServletContext(), lo.LOADIMAGETOFILEIMGEMPLOYEE);
            String path = absolutePath + employeeID;
            File directory = new File(path);
            if (!directory.exists()) {
                directory.mkdirs();
            }
            path += "/";
            DiskFileItemFactory factory = new DiskFileItemFactory();
            ServletContext servletContext = this.getServletConfig().getServletContext();
            File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
            factory.setRepository(repository);
            ServletFileUpload upload = new ServletFileUpload(factory);
            upload.setHeaderEncoding("UTF-8");
            lo.readImageLoaded(path, img, partFileImg);

            //delete old image
            String oldImage = request.getParameter("img1");
            if (oldImage != null) {
                lo.deleteImage(path + oldImage);
            }
            response.sendRedirect("employeelist");
            return;
        } else {
            request.setAttribute("notification", constanMessage.CHECKIMAGEFORMAT);
            request.getRequestDispatcher("./Staff/Notification.jsp").forward(request, response);
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
