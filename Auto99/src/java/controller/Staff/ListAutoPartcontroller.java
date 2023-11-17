/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.Staff;

import dal.AutoPartDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.Account;
import model.AutoPart;

/**
 *
 * @author ASUS
 */
@WebServlet(name = "ListAutoPartcontroller", urlPatterns = {"/autopart"})
public class ListAutoPartcontroller extends BaseAuthencationAndAuthorRequire {

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
        AutoPartDAO dao = new AutoPartDAO();
        String pageIndex = request.getParameter("index");
        if (pageIndex == null) {
            pageIndex = "1";
        }
        int index = Integer.parseInt(pageIndex);

        String name = request.getParameter("name");
        if (name == null) {
            name = "";
        }

        String quantityShow = request.getParameter("quantity");
        if (quantityShow == null) {
            quantityShow = "5";
        }
        int quantity = Integer.parseInt(quantityShow);

        String status = request.getParameter("status");
        if (status == null) {
            status = "";
        }

        List<AutoPart> autoPartList = dao.getPaggingPartByName(index, name, quantity, status);
        int count = dao.getTotalPartByName(name);
        int endPage = count / quantity;
        if (count % quantity != 0) {
            endPage++;
        }
        request.setAttribute("endPage", endPage);
        request.setAttribute("autoPartList", autoPartList);
        request.setAttribute("index", index);
        request.setAttribute("quantity", quantity);
        request.setAttribute("count", count);
        request.setAttribute("name", name);
        request.setAttribute("status", status);

        request.getRequestDispatcher("./Staff/AutoPartList.jsp").forward(request, response);
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
        AutoPartDAO dao = new AutoPartDAO();
        String autoPartID = request.getParameter("autoPartID");
        if (autoPartID == null) {
            autoPartID = "0";
        }
        int AutoPartID = Integer.parseInt(autoPartID);
        boolean Status = Boolean.parseBoolean(request.getParameter("Status"));
        String price = request.getParameter("price");
        if (price == null) {
            price = "0";
        }
        float Price = Float.parseFloat(price);

        Account acc = (Account) request.getSession().getAttribute("acc");
        dao.updatePart(AutoPartID, Price, Status, acc);

        String status = request.getParameter("status");
        if (status == null) {
            status = "";
        }

        String pageIndex = request.getParameter("index");
        if (pageIndex == null) {
            pageIndex = "1";
        }
        int index = Integer.parseInt(pageIndex);

        String name = request.getParameter("name");
        if (name == null) {
            name = "";
        }

        String quantityShow = request.getParameter("quantity");
        if (quantityShow == null) {
            quantityShow = "5";
        }
        int quantity = Integer.parseInt(quantityShow);

        request.setAttribute("index", index);
        request.setAttribute("quantity", quantity);
        request.setAttribute("name", name);
        request.setAttribute("status", status);
        doGet(request, response);
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
