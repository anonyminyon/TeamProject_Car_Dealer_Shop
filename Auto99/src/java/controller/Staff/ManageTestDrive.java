/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.Staff;

import Service.SendMail;
import Service.TextEmail;
import dal.ClientServiceDAO;
import dal.TestDriveServiceDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import model.Account;
import model.ClientService;
import model.TestDriveService;

/**
 *
 * @author Admin
 */
@WebServlet(name="ManageTestDrive", urlPatterns={"/managetestdrive"})
public class ManageTestDrive extends BaseAuthencationRequire {
    private static final String STATUS_WAITING_FOR_APPLICATION_APPROVAl = "CHỜ DUYỆT ĐƠN";
    private static final String STATUS_DOING_SERVICE = "ĐANG LÀM DỊCH VỤ";
    private static final String STATUS_SERVICE_HAS_BEEN_COMPLETED = "DỊCH VỤ ĐÃ HOÀN THÀNH";
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void processGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
   
        //tạo list chứa các service
        ArrayList<TestDriveService> TestDriveServiceList = new ArrayList();
        //lấy list từ TestDriveServiceDAO có không phân trang
        TestDriveServiceList = TestDriveServiceDAO.getInstance().getAllTestDriveService();
        
        //set attribute       
        request.setAttribute("TestDriveServiceList", TestDriveServiceList);
        //chuyển dữ liệu đến ManageServiceClient.jsp
        request.getRequestDispatcher("./Staff/ManageTestDrive.jsp").forward(request, response);
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void processPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        //lấy data acc đang dùng chức năng trong màn này
        Account acc = (Account) request.getSession().getAttribute("acc");
        String action = request.getParameter("action");
        if ("SendMailConfirmAppointment".equals(action)) {

            // Set the response content type to HTML with UTF-8 encoding
            //cái này để dịnh dạng lại mail gửi không bị lỗi
            response.setContentType("text/html;charset=UTF-8");

            //1. Get the all parameter from the HTTP request           
            request.setAttribute("search", request.getAttribute("search"));

            //2. get id of service appointment(TestDriveServiceID)
            int TestDriveServiceID = Integer.parseInt(request.getParameter("TestDriveServiceID"));

            //3. Create an instance of TestDriveService and get value by ID itself
            TestDriveService Service = TestDriveServiceDAO.getInstance().getTestDriveServiceByID(TestDriveServiceID);
            String email = Service.getClientID().getEmail();//get email from Client data

            //4. Encode the email in Base64 format
            String email64 = Base64.getEncoder().encodeToString(email.getBytes());

            //5. Calculate the expiration date 30 minutes from the current time
            LocalDateTime expirationDate = LocalDateTime.now().plusMinutes(30);
            SendMail sm = new SendMail();

            //6. construct an email content 
            String emailContent = "<h1 style=\"color:blue\">Hi " + Service.getClientID().getClientName() + "</h1><br>"
                    + "We are receive your request about test drive  for this car  ( " + Service.getCarID().getCarName() + " )<br>"
                    + "Please go to Auto99 with your license in " + Service.getDateService() + " so the technical department can work with you! <br>"
                    + "All the best,<br>Auto99.";
            //7. get account ID of current acc and use it to save which employee has approved this service appointment
            TestDriveServiceDAO.getInstance().changeStatusATestDriveServiceAndStoreHistoryEmployeeID(TestDriveServiceID, STATUS_DOING_SERVICE, acc.getAccID());//cho id crewchief bằng -1 vì vừa mới duyệt đơn chưa chọn được thợ máy
            //8. Send the email with the constructed content
            TextEmail te = new TextEmail();
            sm.sendEmail(email, te.textSendEmail("OMG DID YOU KNOW", emailContent, "We appreciate your believe", "", "http://localhost:8015/Auto99/home"), "ĐĂNG KÍ DỊCH VỤ THÀNH CÔNG AUTO99");

            //9. Set a success message and forward to .jsp
            request.setAttribute("mess", "The email has been sent to Client. ");
            doGet(request, response);//tải lại trang cho nhân viên và để họ biết công việc đã thành công
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
