/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.Staff;

import dal.EmployeeProfileDAO;
import dal.ServiceDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author HieuHT
 */
@WebServlet(name = "ServiceManagement", urlPatterns = {"/servicemanagement"})
public class ServiceManagement extends BaseAuthencationAndAuthorRequire {

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
        
        doPost(request, response);
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
        String pageIndex = request.getParameter("index");
        if (pageIndex == null) {
            pageIndex = "1";
        }
        int index = Integer.parseInt(pageIndex);

        String search = request.getParameter("search");
        if (search == null) {
            search = "";
        }

        String quantityShow = request.getParameter("pageSize");
        if (quantityShow == null) {
            quantityShow = "5";
        }
        int pageSize = Integer.parseInt(quantityShow);

        String serviceStatusFilter = request.getParameter("serviceStatusFilter");
        if (serviceStatusFilter == null) {
            serviceStatusFilter = "";
        }

        //tạo đối tượng gọi các hàm ServiceDAO       
        ServiceDAO serviceDAO = new ServiceDAO();
        //tạo list chứa các service
        ArrayList serviceList = new ArrayList();
        //lấy list từ serviceDAO có phân trang
        serviceList = serviceDAO.getServicesByPage(index, search, pageSize, serviceStatusFilter);
        int count = serviceDAO.getTotalServicesByName(search, serviceStatusFilter);
        int endPage = count / pageSize;
        if (count % pageSize != 0) {
            endPage++;
        }

        //set attribute 
        request.setAttribute("endPage", endPage);
        request.setAttribute("index", index);
        request.setAttribute("count", count);
        request.setAttribute("pageSize", pageSize);
        request.setAttribute("serviceStatusFilter", serviceStatusFilter);

        request.setAttribute("serviceList", serviceList);
        //chuyển dữ liệu đến ServiceManagement.jsp
        request.getRequestDispatcher("./Staff/ServiceManagement.jsp").forward(request, response);
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
