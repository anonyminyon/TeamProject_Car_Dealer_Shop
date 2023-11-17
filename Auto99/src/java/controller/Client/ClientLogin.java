/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.Client;

import dal.AccountDAO;
import dal.ClientAccountDAO;
import model.ClientAccount;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 *
 * @author daoan
 */
@WebServlet(name = "ClientLogin", urlPatterns = {"/login"})

public class ClientLogin extends HttpServlet {

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
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Cookie arr[] = request.getCookies();
        if (arr != null) {
            for (Cookie o : arr) {
                if (o.getName().equals("emailC")) {
                    // Cookie "emailC" tồn tại
                    String email = o.getValue();
                    request.setAttribute("email", email);
                }
                if (o.getName().equals("passC")) {
                    // Cookie "passC" tồn tại
                    String password = o.getValue();
                    request.setAttribute("pass", password);
                }
            }
        }
        request.getRequestDispatcher("./Client/Login.jsp").forward(request, response);
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
        String email = request.getParameter("email");
        String password = request.getParameter("pass");
        String remember = request.getParameter("remember");

        HttpSession session = request.getSession();

        ClientAccountDAO adao = new ClientAccountDAO();
        ClientAccount acc = adao.checkAccountLogin(email, password);
        if (acc == null) {
            request.setAttribute("error", "Sai tên đăng nhập hoặc mật khẩu!");
            request.getRequestDispatcher("./Client/Login.jsp").forward(request, response);
        } else {
            session.setAttribute("clientacc", acc);
            Cookie e = new Cookie("emailC", email);
            Cookie p = new Cookie("passC", password);
            e.setMaxAge(60 * 60 * 24 * 365); // Thời gian sống của cookie "emailC" là 1 năm
            if (remember != null) {
                p.setMaxAge(60 * 60); // Thời gian sống của cookie "passC" là 1 giờ
            } else {
                p.setMaxAge(0);
            }
            response.addCookie(e);
            response.addCookie(p);

            response.sendRedirect("home");
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
