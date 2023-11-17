/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.Staff;

import dal.ServiceDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import model.Account;
import model.Service;

/**
 *
 * @author HieuHT
 */
@WebServlet(name = "ServiceCRUD", urlPatterns = {"/servicecrud"})
public class ServiceCRUD extends HttpServlet {

    ServiceDAO serviceDAO = new ServiceDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("addService".equals(action)) {
            request.getRequestDispatcher("/Staff/AddService.jsp").forward(request, response);
        } else if ("updateServiceAdvanced".equals(action)) {
            // Retrieve the values from the hidden input fields in form button chỉnh sửa nâng cao

            int serviceID = Integer.parseInt(request.getParameter("serviceID"));

            //get all infor of a service by id
            Service s = serviceDAO.getAServiceByServiceID(serviceID);
            long servicePrice = (long) s.getServicePrice();
            request.setAttribute("money", servicePrice);
            //set attr
            request.setAttribute("service", s);
            request.setAttribute("index", request.getParameter("index"));
            request.setAttribute("search", request.getParameter("search"));
            request.setAttribute("pageSize", request.getParameter("pageSize"));
            request.setAttribute("serviceStatusFilter", request.getParameter("serviceStatusFilter"));

            //move to updateServiceAdvanced.jsp
            request.getRequestDispatcher("./Staff/UpdateServiceAdvanced.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        //lấy thông tin account dã login hiện tại
        Account acc = (Account) request.getSession().getAttribute("acc");

        //tạo đối tượng gọi các hàm ServiceDAO
        serviceDAO = new ServiceDAO();
        //switch case
        if ("AddANewService".equals(action)) {
            //lấy dữ liệu từ add service
            String serviceType = request.getParameter("serviceType").trim();
            String serviceContent = request.getParameter("serviceContent").trim();
            String servicePrice = request.getParameter("servicePrice").trim();

            //kiểm tra nhập duplicate và nhập không hợp lệ
            if (serviceDAO.isServiceExistAndNotValid(serviceType)) {
                String errorServiceType = "Tên dịch vụ đã tồn tại hoặc không hợp lệ";

                //set attribute for error
                request.setAttribute("errorServiceType", errorServiceType);

                //chuyển dữ liệu đến ServiceCRUD.jsp
                request.removeAttribute(action);
                request.getRequestDispatcher("./Staff/AddService.jsp").forward(request, response);
            } else {
                //add new service
                serviceDAO.addANewService(serviceType, serviceContent, servicePrice, acc.getAccID());//add tên service, nội dung service , và id của người add cái này
                //set attribute msg
                request.setAttribute("msg", "Add new service successful!");
                //chuyển dữ liệu đến ServiceCRUD.jsp
                request.removeAttribute(action);
                request.getRequestDispatcher("./Staff/AddService.jsp").forward(request, response);
            }
        } else if ("UpdateStatusAService".equals(action)) {
            //get data
            if (request.getParameter("serviceID") != null && !request.getParameter("serviceID").isEmpty()) {
                String serviceID = request.getParameter("serviceID");
                String serviceType = null;
                String serviceContent = null;
                String serviceStatus = request.getParameter("serviceStatus");
                String servicePrice = null;
                //update by function
                serviceDAO.updateServiceByAServiceID(serviceID, serviceType, serviceContent, serviceStatus, servicePrice, acc.getAccID());
            }
            //paging data
            String pageIndex = request.getParameter("index");
            if (pageIndex == null) {
                pageIndex = "1";
            }
            int index = Integer.parseInt(pageIndex);
            String quantityShow = request.getParameter("pageSize");
            if (quantityShow == null) {
                quantityShow = "5";
            }
            int pageSize = Integer.parseInt(quantityShow);
            request.setAttribute("index", index);
            request.setAttribute("pageSize", pageSize);
            //chuyển dữ liệu đến ServiceCRUD.jsp
            request.removeAttribute(action);
            request.getRequestDispatcher("servicemanagement").forward(request, response);
        } else if ("updateServiceAdvanced".equals(action)) {
            // Retrieve the values from the hidden input fields in form button chỉnh sửa nâng cao
            String index = request.getParameter("index");
            String search = request.getParameter("search");
            String pageSize = request.getParameter("pageSize");
            String serviceStatusFilter = request.getParameter("serviceStatusFilter");

            //lấy dữ liệu từ update service
            String serviceType = request.getParameter("serviceType").trim();
            String serviceContent = request.getParameter("serviceContent").trim();
            String serviceID = request.getParameter("serviceID");
            String serviceStatus = request.getParameter("serviceStatus");
            String priceString = request.getParameter("servicePrice").trim();

// Remove non-numeric characters
            String numericValue = priceString.replaceAll("[^0-9]", "");
            int serID = Integer.parseInt(serviceID);

            //kiểm tra nhập duplicate và nhập không hợp lệ
            if (serviceDAO.isServiceExistAndNotValid(serviceType, serID)) {
                String errorServiceType = "Tên dịch vụ đã tồn tại hoặc không hợp lệ";

                //set attribute for error
                request.setAttribute("errorServiceType", errorServiceType);
                //get data paging
                request.setAttribute("index", request.getParameter("index"));
                request.setAttribute("search", request.getParameter("search"));
                request.setAttribute("pageSize", request.getParameter("pageSize"));
                request.setAttribute("serviceStatusFilter", request.getParameter("serviceStatusFilter"));
                //tạo 1 cái service đẩy lại updateServiceAdvanced.jsp để phòng trường hợp gửi mess error dupplicate mà không gửi lại dữ liệu của service đó
                Service s = new Service(Integer.parseInt(serviceID), serviceType, serviceContent, Boolean.parseBoolean(serviceStatusFilter));
                request.setAttribute("service", s);

                //chuyển dữ liệu đến ServiceCRUD.jsp
                request.removeAttribute(action);
                request.getRequestDispatcher("./Staff/UpdateServiceAdvanced.jsp").forward(request, response);
            } else {
                //add new service
                serviceDAO.updateServiceByAServiceID(serviceID, serviceType, serviceContent, serviceStatusFilter, numericValue, acc.getAccID());
                //set attribute msg
                request.setAttribute("msg", "Update service successful!");
                //chuyển dữ liệu đến ServiceCRUD.jsp
                request.removeAttribute(action);
                //get data paging
                request.setAttribute("index", request.getParameter("index"));
                request.setAttribute("search", request.getParameter("search"));
                request.setAttribute("pageSize", request.getParameter("pageSize"));
                request.setAttribute("serviceStatusFilter", request.getParameter("serviceStatusFilter"));

                //move to current page has service just update
                request.getRequestDispatcher("servicemanagement").forward(request, response);
            }
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
