package controller.Staff;

import dal.VoucherDAO;
import dal.DBContext;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.ObjectVoucher;
import model.Voucher;

@WebServlet(name = "VoucherList", urlPatterns = {"/voucherlist"})
public class VoucherList extends BaseAuthencationAndAuthorRequire {

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

        String parentID = request.getParameter("parent");
        if (parentID == null) {
            parentID = "0";
        }
        int parent = Integer.parseInt(parentID);
        
        

        String status = request.getParameter("status");
        if (status == null) {
            status = "";
        }
        
        String object = request.getParameter("object");
        if (object == null) {
            object = "";
        }
        
        String discountType = request.getParameter("discountType");
        if (discountType == null) {
            discountType = "";
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
        VoucherDAO DAO = new VoucherDAO();
        List<ObjectVoucher> listov = DAO.getAllOV();
        List<Voucher> VoucherList = DAO.getListVoucherBySearch(currentIndex, search, quantity, status,object,discountType, startDate, endDate);
        int count = DAO.getTotalVoucherBySearch(search, status, object, discountType, startDate, endDate);
        int endPage = count / quantity;
        if (count % quantity != 0) {
            endPage++;
        }

        request.setAttribute("endPage", endPage);
        request.setAttribute("ListOV", listov);
        request.setAttribute("ListVoucher", VoucherList);
        request.setAttribute("currentIndex", currentIndex);
        request.setAttribute("index", pageIndex);
        request.setAttribute("count", count);
        request.setAttribute("quantity", quantity);
        request.setAttribute("search", search);
        request.setAttribute("object", object);
        request.setAttribute("discountType", discountType);
        request.setAttribute("status", status);
        request.setAttribute("parent", parent);
        request.setAttribute("startDate", startDate);
        request.setAttribute("endDate", endDate);
        request.getRequestDispatcher("./Staff/VoucherList.jsp").forward(request, response);
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
