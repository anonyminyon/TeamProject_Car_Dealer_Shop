/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.Staff;

import dal.VoucherDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import model.Voucher;
import model.ObjectVoucher;

/**
 *
 * @author Dao Anh Duc
 */
@WebServlet(name = "VoucherCRUD", urlPatterns = {"/vouchercrud"})

public class VoucherCRUD extends BaseAuthencationAndAuthorRequire {

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
        String action = request.getParameter("action");

        VoucherDAO vDAO = new VoucherDAO();
        List<ObjectVoucher> listov = vDAO.getAllOV();
        request.setAttribute("ListOV", listov);
        request.getRequestDispatcher("./Staff/AddVoucher.jsp").forward(request, response);

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
        String action = request.getParameter("action");

        //tạo đối tượng gọi các hàm VoucherDAO
        VoucherDAO vDAO = new VoucherDAO();
        //lấy dữ liệu từ add voucher
        if ("InsertVoucher".equals(action)) {
            String voucherCode = request.getParameter("voucherCode");
            if (vDAO.checkCodeExist(voucherCode)) {
                response.sendRedirect("vouchercrud?action=InsertVoucher&&error=2");
                return;
            }
            String description = request.getParameter("description").trim();
            String objectVoucherID = request.getParameter("objectVoucherID");
            String discountType = request.getParameter("discountType");
            String discountValue = request.getParameter("discountValue");
            int maxUsage = Integer.parseInt(request.getParameter("maxUsage"));

            LocalDateTime currentDateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String createdOn = currentDateTime.format(formatter);

            String startDate = request.getParameter("startDate").trim();
            String endDate = request.getParameter("endDate").trim();

            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("d-M-y");
            DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

// Chuyển đổi startDate và endDate sang định dạng mong muốn
            LocalDate startDateObj = LocalDate.parse(startDate, inputFormatter);
            LocalDate endDateObj = LocalDate.parse(endDate, inputFormatter);

            startDate = startDateObj.format(outputFormatter);
            endDate = endDateObj.format(outputFormatter);

            int createdBy = Integer.parseInt(request.getParameter("user"));

            vDAO.InsertVoucher(voucherCode, description, objectVoucherID, discountType, discountValue, maxUsage, createdOn, createdBy, startDate, endDate);
            response.sendRedirect("voucherlist");
        } else if ("UpdateVoucher".equals(action)) {
            int modifiedBy = Integer.parseInt(request.getParameter("user"));
            String voucherIDParam = request.getParameter("voucherID");
            int voucherID = 0;

            if (voucherIDParam != null && !voucherIDParam.isEmpty()) {
                voucherID = Integer.parseInt(voucherIDParam);
            }
            boolean status = Boolean.parseBoolean(request.getParameter("status"));
            LocalDateTime currentDateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String dateTimeUpdate = currentDateTime.format(formatter);
            vDAO.updateVoucher(dateTimeUpdate, modifiedBy, status, voucherID);
            response.sendRedirect("voucherlist");

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
