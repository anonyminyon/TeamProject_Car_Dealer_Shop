/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.Staff;

import Service.SendMail;
import dal.AccountDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javax.mail.Message.RecipientType.TO;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import static javax.mail.Session.getDefaultInstance;
import static javax.mail.Transport.send;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import model.Account;

/**
 *
 * @author ACER
 */
@WebServlet(name = "EmailResetPasswordController", urlPatterns = {"/emailresetpass"})
public class EmailResetPasswordController extends HttpServlet {

    /*------------------------------ ThinhNT -------------------------------------*/
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException, ParseException {
        // Set the response content type to HTML with UTF-8 encoding
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet BlogHomeList</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet BlogHomeList at " + request.getContextPath() + "</h1>");
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
        request.getRequestDispatcher("./Staff/EmailResetPassword.jsp").forward(request, response);
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
        // Get the email parameter from the HTTP request
        String email = request.getParameter("Email");

        // Encode the email in Base64 format
        String email64 = Base64.getEncoder().encodeToString(email.getBytes());

        // Calculate the expiration date 30 minutes from the current time
        LocalDateTime expirationDate = LocalDateTime.now().plusMinutes(30);

        // Create an instance of AccountDAO
        AccountDAO DAO = new AccountDAO();
        SendMail sm = new SendMail();

        // Check if an account with the provided email exists
        if (DAO.getAccountByEmail(email) == null) {
            // If no account is found, set an error message and forward to EmailResetPassword.jsp
            request.setAttribute("mess", "The email didn't match any account. "
                    + "Please make sure you enter the correct email");
            request.getRequestDispatcher("./Staff/EmailResetPassword.jsp").forward(request, response);
        } else {

            // If an account is found, construct an email content with a reset password link
            String emailContent = "<h1 style=\"color:blue\">Hi there</h1><br>"
                    + "To finish reset password please go to the following page:<br>"
                    + "<a href=\"http://localhost:8015/Auto99/resetpassword?email64=" + email64
                    + "&expirationDate=" + expirationDate + "&type=./Staff/HomePage.jsp" + "\">Click here</a><br>"
                    + "If you do not wish to reset the password, ignore this message; it will expire in 30 minutes"
                    + "All the best,<br>Auto99.";

            // Send the email with the constructed content
            sm.sendEmail(email, emailContent, "RESET PASSWORD AUTO99");

            // Set a success message and forward to EmailResetPassword.jsp
            request.setAttribute("mess", "The email has been sent to you. "
                    + "Please verify it to finish resetting the password");
            request.getRequestDispatcher("./Staff/EmailResetPassword.jsp").forward(request, response);
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

    /*------------------------------ ThinhNT -------------------------------------*/
}
