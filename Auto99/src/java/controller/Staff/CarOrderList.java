package controller.Staff;

import dal.CarOrderDAO;
import dal.DBContext;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.CarOrder;

@WebServlet(name = "CarOrderList", urlPatterns = {"/carorderlist"})
public class CarOrderList extends BaseAuthencationAndAuthorRequire {

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
        String pageIndex = request.getParameter("index");
        if (pageIndex == null || pageIndex.isEmpty()) {
            pageIndex = "1";
        }

        String search = request.getParameter("search");
        if (search == null) {
            search = "";
        }

        String status = request.getParameter("status");
        if (status == null) {
            status = "";
        }
        
        String paymentType = request.getParameter("paymentType");
        if (paymentType == null) {
            paymentType = "";
        }

        String startDate = request.getParameter("startDate");
        if (startDate == null) {
            startDate = "";
        }
        String endDate = request.getParameter("endDate");
        if (endDate == null) {
            endDate = "";
        }

        String quantityShow = request.getParameter("quantity");
        if (quantityShow == null) {
            quantityShow = "10";
        }
        int quantity = Integer.parseInt(quantityShow);
        int currentIndex = Integer.parseInt(pageIndex);
        CarOrderDAO DAO = new CarOrderDAO();
        List<CarOrder> CarOrderList = DAO.getListCarOrderBySearch(currentIndex, search, quantity, status, paymentType,startDate, endDate);
        int count = DAO.getTotalCarOrderBySearch(search, status, paymentType, startDate, endDate);
        int endPage = count / quantity;
        if (count % quantity != 0) {
            endPage++;
        }

        request.setAttribute("endPage", endPage);
        request.setAttribute("ListCarOrder", CarOrderList);
        request.setAttribute("currentIndex", currentIndex);
        request.setAttribute("index", pageIndex);
        request.setAttribute("count", count);
        request.setAttribute("quantity", quantity);
        request.setAttribute("search", search);
        request.setAttribute("status", status);
        request.setAttribute("paymentType", paymentType);
        request.setAttribute("startDate", startDate);
        request.setAttribute("endDate", endDate);
        request.getRequestDispatcher("./Staff/CarOrderList.jsp").forward(request, response);
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
