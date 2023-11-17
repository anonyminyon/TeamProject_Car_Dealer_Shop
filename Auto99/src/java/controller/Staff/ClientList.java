/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.Staff;

import dal.ClientDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import model.Client;

/**
 *
 * @author Admin
 */
@WebServlet(name = "ClientList", urlPatterns = {"/clientlist"})
public class ClientList extends BaseAuthencationAndAuthorRequire {

    static ClientDAO dao = new ClientDAO();

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
            out.println("<title>Servlet ClientList</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ClientList at " + request.getContextPath() + "</h1>");
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
    protected void processGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String numberClientDisplay = request.getParameter("numberClientDisplay");//hienthisoluongsanpham    
        String page = request.getParameter("page");
        String search = request.getParameter("search");
        if (page == null || page.isEmpty()) {
            page = "1";
        }
        if (numberClientDisplay == null || numberClientDisplay.isEmpty()) {
            numberClientDisplay = "10";
        }
        if (search == null) {
            search = "";
        }
        int currentPage = Integer.parseInt(page);
        ArrayList<Client> list = dao.getListClient(search, Integer.parseInt(page), Integer.parseInt(numberClientDisplay));
        int totalClient = dao.getTotalClient(search);
        int endPage = totalClient / Integer.parseInt(numberClientDisplay);
        if (totalClient % Integer.parseInt(numberClientDisplay) != 0) {
            endPage++;
        }
        
        request.setAttribute("search", search);
        request.setAttribute("numberClientDisplay", numberClientDisplay);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("page", page);
        request.setAttribute("endPage", endPage);
        request.setAttribute("listClient", list);
        request.getRequestDispatcher("./Staff/ClientList.jsp").forward(request, response);
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
