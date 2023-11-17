/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.Client;

import dal.BlogDAO;
import dal.BrandDAO;
import dal.EmployeeProfileDAO;
import dal.HistoryServiceInvoiceDAO;
import dal.InFoInvoiceDetailDAO;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Blog;
import model.BlogCategory;
import model.ClientAccount;
import model.EmployeeProfile;
import model.HistoryServiceInvoice;
import model.InFoInvoiceDetail;

/**
 *
 * @author hieuht
 */
//---------------------------------------------------------hiếu---------------------------------------------------------
@WebServlet(name = "MyServiceForm", urlPatterns = {"/myserviceform"})
public class MyServiceForm extends HttpServlet {

    private static final String MESS_UPDATE_FEEDBACK = "CÁM ƠN QUÝ KHÁCH ĐÃ PHẢN HỒI DỊCH VỤ";

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

        //1.Lấy thông tin account khách đang đăng nhập để lấy accID
        ClientAccount client = (ClientAccount) request.getSession().getAttribute("clientacc");
        if (client == null) {
            request.getRequestDispatcher("login").forward(request, response);
        }
        //2. đưa accID của khách vào hàm lấy lịch sử service trong data base sau đó trả về list các lịch sử hóa đơn làm dịch vụ của khách
        ArrayList<HistoryServiceInvoice> historyInvoice = HistoryServiceInvoiceDAO.getInstance().getAllServiceInvoiceByClientID(client.getAccID());
        //3. set list hóa đơn thành attribute để đẩy lên trang MyServiceForm.jsp
        request.setAttribute("historyInvoice", historyInvoice);
        //4. chuyển hướng lên trang MyServiceForm.jsp
        getInfoHeadAndFooter(request, response, "./Client/MyServiceForm.jsp");
    }

    protected void getInfoHeadAndFooter(HttpServletRequest request, HttpServletResponse response, String url)
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

        request.getRequestDispatcher(url).forward(request, response);
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

        EmployeeProfileDAO epd = new EmployeeProfileDAO();
        //1.lấy action để biết nó ấn nút nào
        String action = request.getParameter("action");
        //2. lấy serviceInvoiceID để show hoặc edit thông tin trong bảng
        String serviceInvoiceID = request.getParameter("serviceInvoiceID");
        //3. nếu action là viewFeedback
        if (action.equals("viewFeedback")) {
            //1.lấy thông tin của 1 hóa đơn dịch vụ đã hoàn thành thông qua serviceInvoiceID
            HistoryServiceInvoice hsi = HistoryServiceInvoiceDAO.getInstance().getServiceInvoiceByServiceInvoiceID(Integer.parseInt(serviceInvoiceID));
            request.setAttribute("historyServiceInvoice", hsi);
            //vì thông tin nhân viên không lấy bằng hàm getClientServiceById trong HistoryServiceInvoiceDAO
            //vậy nên ta phải gọi hàm lấy thông tin thằng duyệt đơn và thằng thợ máy để lấy thông tin cua chúng nó show ở FeedBackMyService
            try {              
                //lấy toàn bộ thông tin chi tiết về hóa đơn cho khách xem ở phần thông tin chi tiết
                //(lấy list InFoInvoiceDetail mà có serviceInvoiceID bằng serviceInvoiceID của HistoryServiceInvoice)
                ArrayList<InFoInvoiceDetail> iidList = InFoInvoiceDetailDAO.getInstance().getAllInforDetailPayment(hsi.getServiceInvoiceID());
                request.setAttribute("listProduct", iidList);

            } catch (SQLException ex) {
                Logger.getLogger(MyServiceForm.class.getName()).log(Level.SEVERE, null, ex);
            }

            //2.đẩy thông tin hóa đơn lên trang FeedBackMyService.jsp để người dùng xem hóa đơn và có thể edit feedback
            request.getRequestDispatcher("./Client/FeedBackMyService.jsp").forward(request, response);
        } //4. nếu action là updateFeedBack tức là người dùng vừa edit feedback trong bảng HistoryServiceInvoice và muốn update trường dữ liệu feedback
        else if (action.equals("updateFeedBack")) {
            //gọi hàm update và đưa serviceInvoiceID, feedback để nó update trong db
            String feedback = request.getParameter("comment");
            HistoryServiceInvoiceDAO.getInstance().updateFeedback(Integer.parseInt(serviceInvoiceID), feedback);
            //trả về 1 message trên trang MyServiceForm.jsp là để thông báo là đã feedback thành công
            request.setAttribute("message", MESS_UPDATE_FEEDBACK);
            request.setAttribute("iSModalActive", "true");

            //gọi lại doPost đê nó load lại trang MyServiceForm.jsp cũng như update phẩn hiển thị feedback cho khách
            doGet(request, response);
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
