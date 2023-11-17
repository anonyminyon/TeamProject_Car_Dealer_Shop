/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.Staff;

import dal.AutoPartDAO;
import dal.ClientServiceDAO;
import dal.EmployeeProfileDAO;
import dal.ServiceDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import model.Account;
import model.AutoPart;
import model.ClientService;
import model.Service;

/**
 *
 * @author Admin
 */
@WebServlet(name="CreateInvoiceService", urlPatterns={"/Createinvoiceservice"})
public class CreateInvoiceService extends HttpServlet {
   
    
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        
    } 

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String action = request.getParameter("action");
        String actionAjax = request.getParameter("actionAjax");
        //lấy data acc đang dùng chức năng trong màn này
        Account acc = (Account) request.getSession().getAttribute("acc");

        if ("CompleteServiceAppointment".equals(action)) {
            boolean isCompleteInvoice = Boolean.parseBoolean(request.getParameter("isCompleteInvoice"));

            //if false mean employee not fill invoice and mechanic
            if (!isCompleteInvoice) {
                // 1. Set the value as an attribute in the request (cái này giúp lọc điều kiện để modal được show ra)
                
                // 2. get all employee is mechanic and push to a list using for employee select mechanic did service for client
                EmployeeProfileDAO epdao = new EmployeeProfileDAO();
                request.setAttribute("listMechanicInfo", epdao.getAllMechanic());

                //3. get id of service appointment(clientServiceID)
                int clientServiceID = Integer.parseInt(request.getParameter("clientServiceID"));
                //4. Create an instance of ClientService and get value by ID itself
                ClientService clientService = ClientServiceDAO.getInstance().getClientServiceByClientServiceID(clientServiceID);
                //5. set attribute clientService to show in modal
                request.setAttribute("clientService", clientService);

                //6. get current date to show on modal invoice
                // Get the current date and time
                Date currentDate = new Date();
                // Format the date as a string
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                String formattedDate = dateFormat.format(currentDate);
                // Set the formatted date as an attribute
                request.setAttribute("currentDateTime", formattedDate);

                //7. get all service
                ServiceDAO sd = new ServiceDAO();
                ArrayList<Service> serivceList = sd.getAllService();
                request.setAttribute("serviceList", serivceList);
                //. get all autopart
                AutoPartDAO apd = new AutoPartDAO();
                ArrayList<AutoPart> listAutoPart = (ArrayList<AutoPart>) apd.getAll();
                request.setAttribute("listAutoPart", listAutoPart);

                //. Forward the request and response to the JSP for employee fill invoice and mechanic
                request.getRequestDispatcher("./Staff/CreateInvoiceService.jsp").forward(request, response);
            } //chạy vào đây khi hóa đơn dịch vụ đã hoàn thành vừa đổi trạng thái vừa gửi mail và tài khoản cho khách để khách comment dịch vụ
            else {
                // Get the productList from the request parameters
                String productListJson = request.getParameter("productList");
                String dateDoInvoice = request.getParameter("dateDoInvoice");
                String clientServiceID = request.getParameter("clientServiceID");
                System.out.println(productListJson);
                System.out.println(dateDoInvoice);
                System.out.println(clientServiceID);
            }
        }
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
