/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.Staff;

import model.Product;
import Service.SendMail;
import Service.TextEmail;
import dal.AutoPartDAO;
import dal.ClientServiceDAO;
import dal.EmployeeProfileDAO;
import dal.ServiceDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;

import model.Account;
import model.AutoPart;
import model.ClientService;
import com.google.gson.Gson;
import dal.ClientAccountDAO;
import dal.HistoryServiceInvoiceDAO;
import java.sql.Timestamp;
import java.util.List;
import model.Service;
import Service.Sercurity;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.HistoryServiceInvoice;

/**
 *
 * @author hieu
 */
@WebServlet(name = "ManageServiceClient", urlPatterns = {"/manageserviceclient"})
public class ManageServiceClient extends BaseAuthencationRequire {

    
    //trạng thái cho dịch vụ
    private static final String STATUS_WAITING_FOR_APPLICATION_APPROVAl = "CHỜ DUYỆT ĐƠN";
    private static final String STATUS_DOING_SERVICE = "ĐANG LÀM DỊCH VỤ";
    private static final String STATUS_SERVICE_HAS_BEEN_COMPLETED = "DỊCH VỤ ĐÃ HOÀN THÀNH";

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

        //tạo list chứa các service
        ArrayList<ClientService> clientServiceList = new ArrayList();
        //lấy list từ clientServiceDAO có phân trang
        clientServiceList = ClientServiceDAO.getInstance().getClientServicesByPage(index, search, pageSize, serviceStatusFilter);
        int count = ClientServiceDAO.getInstance().getTotalClientServicesBySearch(search, serviceStatusFilter);
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

        request.setAttribute("clientServiceList", clientServiceList);

        //chuyển dữ liệu đến ManageServiceClient.jsp
        request.getRequestDispatcher("./Staff/ManageServiceClient.jsp").forward(request, response);
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
                if (productListJson != null) {
                    // Initialize Gson
                    Gson gson = new Gson();

                    // Convert the JSON string into an array of Product objects
                    Product[] productArray = gson.fromJson(productListJson, Product[].class);

                    // Create a list and add elements from the array
                    List<Product> productList = new ArrayList<>();
                    for (Product product : productArray) {
                        productList.add(product);
                    }

                    // Now, productList is a list of Product objects
                    for (Product product : productList) {
             
                        System.out.println(product.getProductName());
                        System.out.println(product.getUnitPrice());
                        System.out.println(product.getQuantity());
                    }
                    // You can use productList as needed in logic                  
                    //lấy ngày tạo hóa đơn
                    //String dateDoInvoice = request.getParameter("dateDoInvoice");//cái này lưu vào bang HistoryServiceInvoice(có một thứ đó là trong lúc làm đơn thì ngày 
                    //không được cập nhập liên tục nên fix sẽ là lấy ngày giờ khi bấm submit không phải ngày giờ tạo đơn)
                    // Get the current timestamp
                    LocalDateTime currentDateTime = LocalDateTime.now();
                    Timestamp currentTimestamp = Timestamp.valueOf(currentDateTime);
                    //lấy clientServiceID cái thông tin này đại diện cho 1 cái dịch vụ trên 1 khách hàng và chứa thông tin liên quan đến dịch vụ khách đặt
                    String clientServiceID = request.getParameter("clientServiceID");//cái này lưu vào bang HistoryServiceInvoice
                    //lấy id nhân viên trưởng thợ máy(cái này lưu vào clientservice)
                    String crewChiefID = request.getParameter("selectedMechanicID");

                    //lấy id nhân viên đang đăng nhập và tạo cái đơn dịch vụ này(cái này lưu vào clientservice) thực ra lúc duyệt đơn của khách hàng nó đã có lưu id 
                    //thằng duyệt rồi nhưng ở đây nếu là thằng khác tạo hóa đơn thì lưu đè thằng đấy vào và thằng đấy chính là thằng đang đăng nhập
                    //gọi hàm update cái clientservice ấy và update cái status + id nhân viên tạo hóa đơn + id nhân viên thợ máy làm dịch vụ cho khách
                    ClientServiceDAO.getInstance().changeStatusAClientServiceAndStoreHistoryEmployeeID(Integer.parseInt(clientServiceID), STATUS_SERVICE_HAS_BEEN_COMPLETED, acc.getAccID(), Integer.parseInt(crewChiefID));
                    //add clientServiceID + dateComplete vào thằng HistoryServiceInvoice chừa lại feedback cho khách feedback
                    int serviceInvoiceID = HistoryServiceInvoiceDAO.getInstance().updateClientServiceIDAndDateComplete(Integer.parseInt(clientServiceID), currentTimestamp);
                    //lấy cái id serviceInvoiceID vừa mới có khi add info vào HistoryServiceInvoice và đẩy vào từng InFoInvoiceDetail 
                    //từng dòng dữ liệu trong bảng này nó lưu những thứ khách phải trả khi dùng dịch vụ(dưới đây là logic làm nó)
                    HistoryServiceInvoiceDAO.getInstance().insertDetailProductInvoice(serviceInvoiceID, productList);

                    //tạo tài khoản cho khách vào phản hồi dịch vụ
                    //set up các hàm đê gửi mail
                    TextEmail tE = new TextEmail();
                    SendMail sm = new SendMail();
                    /*phần này sẽ lấy email từ 1 clientservice sau đó check xem khách này có tài khoản đăng nhập web chưa nếu chưa tạo mới và gửi mail cho khách*/
                    String clientEmail = request.getParameter("clientEmail");
                    ClientAccountDAO cad = new ClientAccountDAO();
                    //hàm này sẽ trả về false nếu không tìm thấy email giống clientEmail trong bảng clientAccount tức là khách chưa có tài khoản
                    if (cad.checkEmail(clientEmail)) {
                        /*tạo tài khoản cho khách và lưu thông tin tài khoản cho khách trong clientAccount*/
                        //generate ra string password bất kỳ
                        Sercurity security = new Sercurity();
                        String pass = security.generateRandomString();
                        //mã hóa mật khẩu để lưu vào db
                        String passMD5 = null;
                        try {
                            passMD5 = security.MD5(pass);
                        } catch (NoSuchAlgorithmException ex) {
                            Logger.getLogger(ManageServiceClient.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        //gọi hàm lưu tài khoản mật khẩu cho khách 
                        ClientAccountDAO aO = new ClientAccountDAO();
                        int clientID = Integer.parseInt(request.getParameter("clientID"));
                        //lưu tài khoản mới cho khách
                        aO.insertNewAccount(clientID, clientEmail, passMD5);
                        //gửi mail cho khách để khách phản hồi dịch vụ
                        String content = "<p>Chúng tôi đã tạo hóa đơn cho bạn vui lòng bấm vào link bên dưới và đăng nhập theo tài khoản </p> <br>"
                                         +" username :" + clientEmail + "<BR>" 
                                         +" pass: " + pass + "<BR>" ;
                        sm.sendEmail(clientEmail, tE.textSendEmail("XÁC MINH LẠI HÓA ĐƠN", content, "Trân Trọng", "", "http://localhost:8015/Auto99/login"), "CẢM ƠN QUÝ KHÁCH ĐÃ SỬ DỤNG DỊCH VỤ CỦA CHÚNG TÔI");
                    } else {
                        //gửi mail cho khách để khách phản hồi dịch vụ
                        String content = "<p>Chúng tôi đã tạo hóa đơn cho bạn vui lòng bấm vào link bên dưới và đăng nhập theo tài khoản </p> <br>"
                                +" username :" + clientEmail + "<BR>";
                        sm.sendEmail(clientEmail, tE.textSendEmail("XÁC MINH LẠI HÓA ĐƠN", content, "Trân Trọng", "", "http://localhost:8015/Auto99/login"), "CẢM ƠN QUÝ KHÁCH ĐÃ SỬ DỤNG DỊCH VỤ CỦA CHÚNG TÔI");
                    }
                    //gửi lại mess cho nhân viên biết đã tạo hóa đơn thành công
                    request.setAttribute("mess", "TẠO HÓA ĐƠN DỊCH VỤ CHO KHÁCH (" + clientEmail + ") THÀNH CÔNG! ĐỢI KHÁCH PHẢN HỒI!");
                    //chuyển lại trang 
                    doGet(request, response);
                }
            }
        } //chạy vào đây khi trạng thái đơn dịch vụ là chờ duyệt đơn
        else if ("SendMailConfirmAppointment".equals(action)) {

            // Set the response content type to HTML with UTF-8 encoding
            //cái này để dịnh dạng lại mail gửi không bị lỗi
            response.setContentType("text/html;charset=UTF-8");

            //1. Get the all parameter from the HTTP request
            request.setAttribute("index", request.getAttribute("index"));
            request.setAttribute("pageSize", request.getAttribute("pageSize"));
            request.setAttribute("search", request.getAttribute("search"));

            //2. get id of service appointment(clientServiceID)
            int clientServiceID = Integer.parseInt(request.getParameter("clientServiceID"));

            //3. Create an instance of ClientService and get value by ID itself
            ClientService clientService = ClientServiceDAO.getInstance().getClientServiceByClientServiceID(clientServiceID);
            String email = clientService.getClientID().getEmail();//get email from Client data

            //4. Encode the email in Base64 format
            String email64 = Base64.getEncoder().encodeToString(email.getBytes());

            //5. Calculate the expiration date 30 minutes from the current time
            LocalDateTime expirationDate = LocalDateTime.now().plusMinutes(30);
            SendMail sm = new SendMail();

            //6. construct an email content 
            String emailContent = "<h1 style=\"color:blue\">Hi " + clientService.getClientID().getClientName() + "</h1><br>"
                    + "We are receive your request about " + clientService.getServiceID().getServiceType() + " for your car has numberPlate ( " + clientService.getNumberPlate() + " )<br>"
                    + "Please go to Auto99 with your car in " + clientService.getDateService() + " so the technical department can work with you! <br>"
                    + "All the best,<br>Auto99.";
            //7. get account ID of current acc and use it to save which employee has approved this service appointment
            ClientServiceDAO.getInstance().changeStatusAClientServiceAndStoreHistoryEmployeeID(clientServiceID, STATUS_DOING_SERVICE, acc.getAccID(), -1);//cho id crewchief bằng -1 vì vừa mới duyệt đơn chưa chọn được thợ máy
            //8. Send the email with the constructed content
            TextEmail te = new TextEmail();
            sm.sendEmail(email, te.textSendEmail("OMG DID YOU KNOW", emailContent, "We appreciate your believe", "", "http://localhost:8015/Auto99/home"), "ĐĂNG KÍ DỊCH VỤ THÀNH CÔNG AUTO99");

            //9. Set a success message and forward to .jsp
            request.setAttribute("mess", "The email has been sent to Client. ");
            doGet(request, response);//tải lại trang cho nhân viên và để họ biết công việc đã thành công
        } else if ("ViewFeedback".equals(action)) {
            String clientServiceID = request.getParameter("clientServiceID");
            HistoryServiceInvoice hsi = HistoryServiceInvoiceDAO.getInstance().getServiceInvoices(Integer.parseInt(clientServiceID));
            request.setAttribute("historyServiceInvoice", hsi);
            
            request.getRequestDispatcher("./Staff/ViewFeedBackClient.jsp").forward(request, response);
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
