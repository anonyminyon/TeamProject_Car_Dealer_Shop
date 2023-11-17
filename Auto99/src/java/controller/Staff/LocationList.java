package controller.Staff;

import dal.LocationDAO;
import jakarta.servlet.ServletException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Account;
import model.Location;

/**
 *
 * @author ACER
 */
@WebServlet(urlPatterns = {"/locationlist"})
public class LocationList extends BaseAuthencationAndAuthorRequire {

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

            LocationDAO dao = new LocationDAO();
            List<Location> LocationList = dao.getListLocation(index, quantity, name);
            int count = dao.getTotalLocation(name);
            int endPage = count / quantity;
            if (count % quantity != 0) {
                endPage++;
            }

            request.setAttribute("endPage", endPage);
            request.setAttribute("LocationList", LocationList);
            request.setAttribute("index", index);
            request.setAttribute("count", count);
            request.setAttribute("quantity", quantity);
            request.setAttribute("name", name);
            request.getRequestDispatcher("./Staff/LocationList.jsp").forward(request, response);
        }
    }

    @Override
    protected void processPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        LocationDAO dao = new LocationDAO();
        String locationName = request.getParameter("locationName");
        String preRegFee = request.getParameter("preRegFee");
        String regFee = request.getParameter("regFee");
        Account acc = (Account) request.getSession().getAttribute("acc");

        String action = request.getParameter("action");

        if ("AddLocation".equals(action)) {
            dao.addLocation(locationName, preRegFee, regFee, acc.getAccID());

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

        } else if ("UpdateLocation".equals(action)) {
            String LocationID = request.getParameter("locationID");
            if (LocationID == null) {
                LocationID = "1";
            }
            int locationID = Integer.parseInt(LocationID);

            try {
                dao.updateLocation(locationName, preRegFee, regFee, acc.getAccID(), locationID);
            } catch (Exception e) {
                e.printStackTrace(); // Print the exception details
                // Handle the exception or log it as needed
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
