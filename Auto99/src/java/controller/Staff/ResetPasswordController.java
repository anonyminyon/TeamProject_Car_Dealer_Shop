/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.Staff;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Base64;

/**
 *
 * @author ACER
 */
@WebServlet(name = "ResetPasswordController", urlPatterns = {"/resetpassword"})
public class ResetPasswordController extends HttpServlet {

    /*------------------------------ ThinhNT -------------------------------------*/
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Set the response content type to HTML with UTF-8 encoding
        response.setContentType("text/html;charset=UTF-8");
        try {
            PrintWriter pw = response.getWriter();

            // Retrieve parameters from the HTTP request: email64 (Base64-encoded email) and expirationDate
            String email64 = request.getParameter("email64");
            String expirationDate = request.getParameter("expirationDate");

            // Get the current time as a string
            String now = LocalDateTime.now().toString();

            // Check if the expiration date is greater than or equal to the current time
            if (expirationDate.compareTo(now) >= 0) {
                // If the email is still valid, decode it from Base64 and set it as an attribute
                byte[] decodedBytes = Base64.getDecoder().decode(email64);
                String email = new String(decodedBytes, StandardCharsets.UTF_8);
                request.setAttribute("email", email);

                // Forward the request to ResetPassword.jsp for password reset
                request.getRequestDispatcher("./Staff/ResetPassword.jsp").forward(request, response);
            } else {
                // If the email has expired, display an alert and redirect to 'resetpassword'
                pw.println("<script type=\"text/javascript\">");
                pw.println("alert('The email has expired!');");
                pw.println("location='resetpassword';");
                pw.println("</script>");
            }
        } catch (Exception e) {
            // Handle any exceptions here (the code currently catches all exceptions but doesn't take any action)
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
