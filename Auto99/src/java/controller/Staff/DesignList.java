/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.Staff;

import LocationFile.Location;
import dal.CarDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.Account;
import model.CarDesign;

@MultipartConfig(fileSizeThreshold = 2048 * 2048 * 2,
        maxFileSize = 2048 * 2048 * 10,
        maxRequestSize = 2048 * 2048 * 50)
@WebServlet(name = "DesignList", urlPatterns = {"/designlist"})
public class DesignList extends BaseAuthencationAndAuthorRequire {

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
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
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

            CarDAO dao = new CarDAO();
            List<CarDesign> DesignList = dao.getAllDesignByName(index, quantity, name);
            int count = dao.getTotalDesignByName(name);
            int endPage = count / quantity;
            if (count % quantity != 0) {
                endPage++;
            }

            request.setAttribute("endPage", endPage);
            request.setAttribute("DesignList", DesignList);
            request.setAttribute("index", index);
            request.setAttribute("count", count);
            request.setAttribute("quantity", quantity);
            request.setAttribute("name", name);
            request.getRequestDispatcher("./Staff/DesignList.jsp").forward(request, response);
        }
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
        CarDAO dao = new CarDAO();
        String action = request.getParameter("action");
        String designName = request.getParameter("designName");
        Account acc = (Account) request.getSession().getAttribute("acc");

        if ("AddDesign".equals(action)) {
            dao.addDesign(designName, acc.getAccID());

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

            doGet(request, response);
        } else if ("UpdateDesign".equals(action)) {
            int designID = Integer.parseInt(request.getParameter("designID"));
            dao.updateDesign(designName, designID, acc.getAccID());

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

            doGet(request, response);
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
