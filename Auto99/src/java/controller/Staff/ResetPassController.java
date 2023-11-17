/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.Staff;

import Service.Sercurity;
import dal.AccountDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ACER
 */
@WebServlet(name = "ResetPassController", urlPatterns = {"/resetpass"})
public class ResetPassController extends HttpServlet {

/*------------------------------ ThinhNT -------------------------------------*/
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Set the response content type to HTML with UTF-8 encoding
        response.setContentType("text/html;charset=UTF-8");

        // Get parameters from the HTTP request: email, password, and reentered password
        String email = request.getParameter("email");
        String pass = request.getParameter("Pass");
        String repass = request.getParameter("rePass");

        // Check if the password and reentered password match
        if (pass.equals(repass)) {
            try {
                // If they match, create an instance of AccountDAO and update the password
                Sercurity security = new Sercurity();
                String encryptPass = security.MD5(pass);
                AccountDAO DAO = new AccountDAO();
                DAO.updatePassword(email, encryptPass);
                
                // Send a JavaScript alert to inform the user that the password has been changed
                PrintWriter pw = response.getWriter();
                pw.println("<script type=\"text/javascript\">");
                pw.println("alert('The password has been changed!');");
                pw.println("location='resetpassword';"); // Redirect the user
                pw.println("</script>");
                
                // Forward the request to the AdminHomePage.jsp page
                request.getRequestDispatcher("./Staff/LoginPage.jsp").forward(request, response);
            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(ResetPassController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            // If passwords don't match, set an error message and forward to ResetPassword.jsp
            request.setAttribute("mess", "The password and reentered password didn't match!");
            request.setAttribute("email", email);
            request.getRequestDispatcher("./Staff/ResetPassword.jsp").forward(request, response);
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
        processRequest(request, response);
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
        processRequest(request, response);
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

    /*------------------------------ ThinhNT -------------------------------------*/
}
