/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.Client;

import Service.Sercurity;
import dal.ClientAccountDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.ClientAccount;

/**
 *
 * @author Admin
 */
@WebServlet(name = "ChangePassword", urlPatterns = {"/changepassword"})
public class ChangePassword extends HttpServlet {

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
        try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ChangePassword</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ChangePassword at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
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
        request.getRequestDispatcher("./Client/ChangePassword.jsp").forward(request, response);
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
        try {
            HttpSession session = request.getSession();
            Sercurity sercurity = new Sercurity();
            ClientAccountDAO dao = new ClientAccountDAO();
            String action = request.getParameter("action");

            String confirmPassword = request.getParameter("confirmPassword");
            String password = sercurity.MD5(confirmPassword);
            ClientAccount acc = (ClientAccount) session.getAttribute("clientacc");
            String email = "";
            if (acc == null) {
                email = session.getAttribute("email") + "";
            } else {
                email = acc.getEmail();
            }

            //sử dụng cho manf lay lai mat khau
            if (action.equals("createPass")) {
                dao.updatePasswordByEmail(email, password);
                request.getRequestDispatcher("./Client/Login.jsp").forward(request, response);
            } else if (action.equals("updatePass")) {
                String oldPasswordRequest = request.getParameter("oldpassword");
                String oldPassword = sercurity.MD5(oldPasswordRequest);
                if (oldPassword.equals(dao.getPasswordByEmail(email))) {
                    if(!oldPasswordRequest.equalsIgnoreCase(confirmPassword)){
                         dao.updatePasswordByEmail(email, password);
                    request.getRequestDispatcher("./Client/Login.jsp").forward(request, response);
                    }else{
                         request.setAttribute("error", "Mật khẩu mới không được giống mật khẩu cũ");
                         request.getRequestDispatcher("./Client/ChangePassword.jsp").forward(request, response);
                    }
                } else {
                    request.setAttribute("error", "Mật khẩu cũ khong dung ");
                    request.getRequestDispatcher("./Client/ChangePassword.jsp").forward(request, response);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
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
