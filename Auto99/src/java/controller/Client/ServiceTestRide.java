/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.Client;

import LocationFile.Location;
import dal.BlogDAO;
import dal.CarDAO;
import dal.ClientDAO;
import dal.ClientNumberPlateDAO;
import dal.ClientServiceDAO;
import dal.TestDriveServiceDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.Date;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Blog;
import model.BlogCategory;

import model.CarIMG;
import model.CarStaffPage;

/**
 *
 * @author Admin
 */
@WebServlet(name = "ServiceTestRide", urlPatterns = {"/servicetestride"})
public class ServiceTestRide extends HttpServlet {

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
        String action = request.getParameter("action");

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
        //-----------------------------------------phần này để load cái header với footer-----------------------------------------
        //lấy list xe mà showroom hỗ trợ làm dịch vụ lái thử

        CarDAO cd = new CarDAO();
        session.setAttribute("ListCar", cd.getAllCar());

        if (action != null) {
            if (action.equals("loadIMG")) {
                //ajax
                List<CarIMG> carIMGs = cd.getCarIMGByCarID(Integer.parseInt(request.getParameter("carID")));

                if (!carIMGs.isEmpty()) {
                    String imageURL = "img/Xe/" + carIMGs.get(0).getCarID().getCarID() + "/" + carIMGs.get(0).getCarIMG();
                    response.getWriter().write(imageURL);
                    return; // Make sure to return here to avoid forwarding to JSP
                }
            }
        }
        // Forward to JSP only if not handling AJAX request
        request.getRequestDispatcher("/Client/ServiceTestRide.jsp").forward(request, response);

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
        //1.lấy dữ liệu từ thẻ form của trang ServiceTestRide.jsp       
        String clientName = request.getParameter("clientName");// Lấy họ và tên của khách(Client)
        String phoneNumber = request.getParameter("phoneNumber");//Lấy số điện thoại của khách(Client)
        String email = request.getParameter("email");//Lấy số điện thoại của khách(Client)
        String dateServiceTemp = request.getParameter("dateService");//Lấy số điện thoại của khách(ClientService)
        String carID = request.getParameter("carID");//lấy value kiểu hãng xe của khách(car)

        //2.Validate dữ liệu 
        ClientDAO cd = new ClientDAO();
        /* thực ra đến đây ta vẫn cần check duplicate dữ liệu vì có 1 trường hợp khi khách đến thăm show room chưa dùng dịch vụ hay mua xe tại đây nhưng vẫn để lại thông tin */
        int clientExist = cd.getClientID(clientName, email, phoneNumber, null, null, null);
        if (clientExist == -1) {
            //3.đẩy dữ liệu vào bảng Client 
            //đầu tiên add data vào bảng Client trước vì nó chứa khóa ngoại của hai bảng còn lại 
            cd.AddANewClient(clientName, email, phoneNumber, null, null, null);//dateOfBirth and gender temporaty = null(cái này dành cho khách mua xe)
        }
        //sau đó add data vào 
        int clientID = cd.getClientID(clientName, email, phoneNumber, null, null, null);
        //lấy tên xe khách đã đăng ký bằng carID mà khách gửi xuống từ ServiceTestRide.jsp
        CarDAO cardao = new CarDAO();
        CarStaffPage car = cardao.getCarByID(Integer.parseInt(carID));

        //parse String to date
        Timestamp timestamp = convertStringToTimestamp(dateServiceTemp);

        //4.sau khi xử lý dữ liệu Client ta xử lý TestDriveService
        //check liêụ khách nhây đặt cùng 1 dịch vụ trên cùng 1 xe và cùng 1 ngày
        if (TestDriveServiceDAO.getInstance().isExist(cd.getClientID(clientName, email, phoneNumber, null, null, null), Integer.parseInt(carID), dateServiceTemp)) {
            request.setAttribute("mess", "BẠN ĐÃ ĐĂNG KÝ DỊCH VỤ LÁI THỬ NÀY CHO XE " + car.getCarName() + " VÀ LỊCH HẸN LÀ " + timestamp + ", VUI LÒNG CHỜ LIÊN HỆ QUA EMAIL");
            doGet(request, response);
        } else {
            //lý do phải lấy id bằng getClientID để luôn lấy đúng thăng khách vùa làm dịch vụ
            TestDriveServiceDAO.getInstance().addTestDriveService(
                    cd.getClientID(clientName, email, phoneNumber, null, null, null),
                    Integer.parseInt(carID), timestamp, STATUS_WAITING_FOR_APPLICATION_APPROVAL);
            //5.gửi mess đăng đơn thành công
            request.setAttribute("mess", "BẠN ĐÃ ĐĂNG KÝ THÀNH CÔNG DỊCH VỤ VUI LÒNG CHỜ LIÊN HỆ QUA EMAIL");
            doGet(request, response);
        }

    }
    private static Timestamp convertStringToTimestamp(String dateString) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            java.util.Date parsedDate = dateFormat.parse(dateString);
            return new Timestamp(parsedDate.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
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
