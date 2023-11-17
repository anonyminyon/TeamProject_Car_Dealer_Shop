/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.Staff;

import dal.AccountDAO;
import dal.AutoPartDAO;
import dal.BlogDAO;
import dal.CarDAO;
import dal.ClientDAO;
import dal.ServiceDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author ACER
 */
@WebServlet(name = "AdminHomePage", urlPatterns = {"/adminhome"})
public class AdminHomePage extends HttpServlet {

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
            CarDAO CarDAO = new CarDAO();
            AccountDAO AccDAO = new AccountDAO();
            BlogDAO BlogDAO = new BlogDAO();
            AutoPartDAO PartDAO = new AutoPartDAO();
            ServiceDAO SerDAO = new ServiceDAO();
            ClientDAO ClientDAO = new ClientDAO();
            int carCount = CarDAO.getTotalCarAdmin("", 0, 0, 0, 0, CarDAO.getMaxPriceCar("", 0, 0, 0));
            int accCount = AccDAO.getTotalActiveAcc();
            int blogCount = BlogDAO.getTotalBlogBySearch("", "", 0, "", "");
            int partCount = PartDAO.getTotalPartByName("");
            int serCount = SerDAO.getTotalServicesByName("", "");
            int clientCount = ClientDAO.getTotalClient("");
            
            request.setAttribute("carCount", carCount);
            request.setAttribute("accCount", accCount);
            request.setAttribute("blogCount", blogCount);
            request.setAttribute("partCount", partCount);
            request.setAttribute("serCount", serCount);
            request.setAttribute("clientCount", clientCount);
            
            request.getRequestDispatcher("./Staff/AdminHomePage.jsp").forward(request, response);
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

}
