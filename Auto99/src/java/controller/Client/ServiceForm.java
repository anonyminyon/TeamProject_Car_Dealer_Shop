/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.Client;

import dal.BlogDAO;
import dal.BrandDAO;
import dal.ClientDAO;
import dal.ClientNumberPlateDAO;
import dal.ClientServiceDAO;
import dal.ServiceDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Blog;
import model.BlogCategory;
import model.CarBrand;
import model.Client;

/**
 *
 * @author Admin
 */
@WebServlet(name = "ServiceForm", urlPatterns = {"/serviceform"})
public class ServiceForm extends HttpServlet {
    //trạng thái cho dịch vụ
    private static final String STATUS_WAITING_FOR_APPLICATION_APPROVAL = "CHỜ DUYỆT ĐƠN";
    
    
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();

        //-----------------------------------------phần này để load cái header với footer-----------------------------------------
        //Lấy list blog tin tức và blog sub category để hiển thị ơ homepage
        BlogDAO bdao = new BlogDAO();
        List<BlogCategory> listbc = bdao.getAllBlogCategory();
        session.setAttribute("ListBC", listbc);
        List<Blog> listb = bdao.getAllBlog();
        List<Blog> listb1 = bdao.getAllTrueBlog();

        Map<Integer, Integer> parentIDCountMap = new HashMap<>();
        for (Blog blog : listb) {
            int parentID = blog.getParentID();
            if (parentID != 0) {
                parentIDCountMap.put(parentID, parentIDCountMap.getOrDefault(parentID, 0) + 1);
            }
        }

        List<List<Blog>> allBlogByParentID = new ArrayList<>();

        for (Blog b : listb) {
            int parentID = b.getParentID();
            List<Blog> blogsByPI = bdao.getBlogByParentID(parentID);
            allBlogByParentID.add(blogsByPI);
        }
        List<Blog> listf = bdao.getFooter();
        session.setAttribute("Footer", listf);

        session.setAttribute("AllBlogsByPI", allBlogByParentID);
        // Lưu kết quả vào session
        session.setAttribute("ParentIDCountMap", parentIDCountMap);
        session.setAttribute("ListBlog", listb);
        session.setAttribute("ListBlog1", listb1);
        //lấy lấy list dịch vụ và list hãng xe mà showroom hỗ trợ làm dịch vụ
        ServiceDAO sd = new ServiceDAO();
        session.setAttribute("ListServiceAvailable", sd.getAllServiceAvailable());
        BrandDAO bd = new BrandDAO();
        session.setAttribute("ListCarBrand", bd.getAllCarBrandTable());
        request.getRequestDispatcher("./Client/ServiceForm.jsp").forward(request, response);
        //-----------------------------------------phần này để load cái header với footer-----------------------------------------
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //1.lấy dữ liệu từ thẻ form của trang ServiceForm.jsp
        String serviceID = request.getParameter("serviceID");//lấy value kiểu service(Service)
        String numberPlate = request.getParameter("numberPlate");//Lấy value số xe của khách hàng(ClientNumberPlate)
        String clientName = request.getParameter("clientName");// Lấy họ và tên của khách(Client)
        String phoneNumber = request.getParameter("phoneNumber");//Lấy số điện thoại của khách(Client)
        String email = request.getParameter("email");//Lấy số điện thoại của khách(Client)
        String dateService = request.getParameter("dateService");//Lấy số điện thoại của khách(ClientService)
        String brandID = request.getParameter("brandID");//lấy value kiểu hãng xe của khách(CarBrand)
  
        //2.Validate dữ liệu 
        /*bây giờ trong bảng client nó sẽ quản lý cả thông tin khách hàng mua xe và khách hàng đã dùng dịch vụ tại showroom
        vậy nên trước khi add các trường dữ liệu này vào ClientNumberPlate và Client ta cần check duplicate thông tin trong hai bảng này
        ,ý tưởng sẽ là nếu đây là khách hàng mới chưa bao giờ dùng service và mua xe tại showroom thì ta xác định
        điều này qua numberPlate từ tường dữ liệu này ta mới biết rằng ô tô của khách không có dữ liệu ở showroom
        vậy nên ta mới phải add thông tin mới vào ClientNumberPlate và Client còn không lấy thông tin từ hai bảng này đút vào
        ClientService để quản lý đơn dịch vụ */
        //tìm clientID by numberPlate nếu nó tồn tại thì đây không phải khách mới, đã từng mua xe hoặc dùng dịch vụ ở showroom rồi
        ClientNumberPlateDAO cnpdao = new ClientNumberPlateDAO();
        int clientID = cnpdao.getClientIDByNumberPlate(numberPlate);
        ClientDAO cd = new ClientDAO();
        //không tìm thấy tức là phải add thông tin của khách mới vào ClientNumberPlate và Client 
        if (clientID == -1) {
            //3.đẩy dữ liệu vào bảng Client và ClientNumberPlate
            /* thực ra đến đây ta vẫn cần check duplicate dữ liệu vì có 1 trường hợp khi khách đến thăm show room chưa dùng dịch vụ hay mua xe tại đây nhưng vẫn để lại thông tin */
            int clientExist = cd.getClientID(clientName, email, phoneNumber, null, null,null);
            if (clientExist == -1) {
                //đầu tiên add data vào bảng Client trước vì nó chứa khóa ngoại của hai bảng còn lại 
                cd.AddANewClient(clientName, email, phoneNumber, null, null,null);//dateOfBirth and gender temporaty = null(cái này dành cho khách mua xe)
            }
            //sau đó add data vào ClientNumberPlate
            clientID = cd.getClientID(clientName, email, phoneNumber, null, null,null);
            cnpdao.addNumberPlateByClientID(clientID, numberPlate);
        }
        try {
            //4.sau khi xử lý dữ liệu ClientNumberPlate và Client ta xử lý ClientService
            //check liêụ khách nhây đặt cùng 1 dịch vụ trên cùng 1 xe và cùng 1 ngày 
            if(ClientServiceDAO.getInstance().doesClientServiceExist(serviceID, numberPlate, dateService)){
                request.setAttribute("mess", "BẠN ĐÃ ĐĂNG KÝ DỊCH VỤ NÀY CHO XE CÓ BIỂN SỐ " + numberPlate + " VÀ LỊCH HẸN LÀ " + dateService + ", VUI LÒNG CHỜ LIÊN HỆ QUA EMAIL");
                doGet(request, response);
            }else{
                //lý do phải lấy id bằng getClientID để luôn lấy đúng thăng khách vùa làm dịch vụ
                ClientServiceDAO.getInstance().addClientService(
                        String.valueOf(cd.getClientID(clientName, email, phoneNumber, null, null,null))
                        , serviceID, numberPlate, dateService, brandID, STATUS_WAITING_FOR_APPLICATION_APPROVAL);
                //5.gửi mess đăng đơn thành công
                request.setAttribute("mess", "BẠN ĐÃ ĐĂNG KÝ THÀNH CÔNG DỊCH VỤ VUI LÒNG CHỜ LIÊN HỆ QUA EMAIL");   
                doGet(request, response);
            }
        } catch (ParseException ex) {
            Logger.getLogger(ServiceForm.class.getName()).log(Level.SEVERE, null, ex);
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
