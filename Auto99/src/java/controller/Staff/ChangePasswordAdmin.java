/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.Staff;

import Service.Sercurity;
import dal.AccountDAO;
import dal.AutoPartDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Account;

/**
 *
 * @author ASUS
 */
@WebServlet(name = "ChangePasswordAdmin", urlPatterns = {"/change"})
public class ChangePasswordAdmin extends HttpServlet {

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
        request.getRequestDispatcher("./Staff/ChangePasswordForm.jsp").forward(request, response);
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
            Sercurity sercurity = new Sercurity();

            String user = request.getParameter("user");
            String oldpass = request.getParameter("opass");
            String pass = request.getParameter("pass");
            String rpass = request.getParameter("rpass");
            AccountDAO dao = new AccountDAO();
            Account acc = dao.checkAccountByaccName(user, oldpass);
            if (acc == null) {
                String ms = "Old Password is incorrect!";
                request.setAttribute("ms", ms);
                request.getRequestDispatcher("./Staff/ChangePasswordForm.jsp").forward(request, response);
            } else if (oldpass.equals(pass)) {
                String ms = "Old password is the same as new password";
                request.setAttribute("ms", ms);
                request.getRequestDispatcher("./Staff/ChangePasswordForm.jsp").forward(request, response);
            } else if (!pass.equals(rpass)) {
                String ms = "Re-type Password incorrect!";
                request.setAttribute("ms", ms);
                request.getRequestDispatcher("./Staff/ChangePasswordForm.jsp").forward(request, response);
            } else if (!pass.matches("^(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*])[A-Za-z0-9!@#$%^&*]{8,}$")){
                String ms = "Mật khẩu phải chứa 1 chữ in hoa, 1 số, 1 kí tự đặc biệt và ít nhất 8 kí tự";
                request.setAttribute("ms", ms);
                request.getRequestDispatcher("./Staff/ChangePasswordForm.jsp").forward(request, response);
            }else {
                String password = sercurity.MD5(pass);
                Account ac = new Account(acc.getAccID(), user, password, acc.getRoleID(), acc.getEmail());
                dao.change(ac);
                HttpSession session = request.getSession();
                session.setAttribute("account", ac);
                response.sendRedirect("adminhome");
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
