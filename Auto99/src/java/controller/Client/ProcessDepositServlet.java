/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.Client;

import dal.CarDAO;
import dal.ClientDAO;
import dal.VoucherDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.math.RoundingMode;
import model.CarStaffPage;
import java.text.DecimalFormat;
import model.Voucher;

/**
 *
 * @author Dao Anh Duc
 */
@WebServlet(name = "ProcessDepositServlet", urlPatterns = {"/processdeposit"})
public class ProcessDepositServlet extends HttpServlet {

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
        try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ProcessDepositServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ProcessDepositServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String action = request.getParameter("action");
        PrintWriter out = response.getWriter();

        if (action.equals("checkVoucher")) {
            String voucher = request.getParameter("voucher");
            VoucherDAO dao = new VoucherDAO();
            Voucher Voucher = dao.getVoucherByVoucherCode(voucher);
            String pricest = request.getParameter("price");
            if (pricest == null) {
                pricest = "0";
            }
            long price = Long.parseLong(pricest);

            DecimalFormat decimalFormat = new DecimalFormat("###,###,###,###");
            String formattedPrice = decimalFormat.format(price);

            // Thay dấu phẩy (,) bằng dấu chấm (.)
            formattedPrice = formattedPrice.replace(",", ".");
            if (Voucher == null) {
                out.println("<div class=\"invalid-feedback\"> Voucher không tồn tại hoặc đã hết hạn</div>"
                        + "<div class=\"info\">"
                        + "<p>Giá phải trả</p>\n"
                        + "                                <p>\n"
                        + "                                    <input hidden value=\"" + price + "\" id=\"finalPriceValue\">\n"
                        + formattedPrice
                        + "                                    <span>VNĐ</span>\n"
                        + "                                </p"
                        + "</div>");
            } else if (Voucher.getObjectVoucherID().getObjectVoucherID() == 1) {
                if (Voucher.isDiscountType() == true) {
                    float discountValue = Voucher.getDiscountValue(); // Lấy giá trị discountValue dạng float từ Voucher
                    long discountValueLong = (long) discountValue;
                    price = price * (100 - discountValueLong) / 100;
                } else {
                    float discountValue = Voucher.getDiscountValue(); // Lấy giá trị discountValue dạng float từ Voucher
                    long discountValueLong = (long) discountValue;
                    price = price - discountValueLong;
                }

                session.setAttribute("VoucherInfo", Voucher);
                formattedPrice = decimalFormat.format(price);
                formattedPrice = formattedPrice.replace(",", ".");
                out.println("<div class=\"info\">"
                        + "<p>Giá phải trả</p>\n"
                        + "                                <p>\n"
                        + "                                    <input hidden value=\"" + price + "\" id=\"finalPriceValue\">\n"
                        + formattedPrice
                        + "                                    <span>VNĐ</span>\n"
                        + "                                </p"
                        + "</div>");
            } else {
                out.println("<div class=\"invalid-feedback\"> Không thể sử dụng voucher này</div>"
                        + "<div class=\"info\">"
                        + "<p>Giá phải trả</p>\n"
                        + "                                <p>\n"
                        + "                                    <input hidden value=\"" + price + "\" id=\"finalPriceValue\">\n"
                        + formattedPrice
                        + "                                    <span>VNĐ</span>\n"
                        + "                                </p"
                        + "</div>");
            }

        } else if (action.equals("checkNoIDExist")) {
            String noIDToCheck = request.getParameter("noID");
            ClientDAO clientDAO = new ClientDAO();
            // Thực hiện kiểm tra sự tồn tại của email bằng phương thức từ ClientDAO
            boolean noIDExists = clientDAO.checknoIDExist(noIDToCheck);

            // Chuẩn bị dữ liệu để gửi lại cho trình duyệt
            response.setContentType("application/json");

            // Tạo một đối tượng JSON đơn giản để gửi trạng thái kiểm tra email
            String jsonResponse = "{\"exists\":" + noIDExists + "}";

            // Gửi dữ liệu JSON trở lại trình duyệt
            out.print(jsonResponse);
            out.flush();

        } else if (action.equals("checkPhoneExist")) {
            String phoneToCheck = request.getParameter("phoneNumber");
            ClientDAO clientDAO = new ClientDAO();
            // Thực hiện kiểm tra sự tồn tại của email bằng phương thức từ ClientDAO
            boolean phoneExists = clientDAO.checkPhoneExist(phoneToCheck);

            // Chuẩn bị dữ liệu để gửi lại cho trình duyệt
            response.setContentType("application/json");

            // Tạo một đối tượng JSON đơn giản để gửi trạng thái kiểm tra email
            String jsonResponse = "{\"exists\":" + phoneExists + "}";

            // Gửi dữ liệu JSON trở lại trình duyệt
            out.print(jsonResponse);
            out.flush();

        } else {
            String ID = request.getParameter("carID");
            if (ID.equals("") || ID.equals(null)) {
                ID = "0";
            }
            int carID = Integer.parseInt(ID);
            String customerName = request.getParameter("customerName");
            String phoneNumber = request.getParameter("phoneNumber");
            String noID = request.getParameter("noID");
            String email = request.getParameter("email");
            String finalPriceValue = request.getParameter("finalPriceValue");
            double price = Double.parseDouble(finalPriceValue);

// Định dạng số thành chuỗi kiểu ###.###.###
            DecimalFormat decimalFormat = new DecimalFormat("###,###,###.###");
            String formattedPrice = decimalFormat.format(price);
            formattedPrice = formattedPrice.replace(",", ".");
            DecimalFormat decimalFormatNoCommas = new DecimalFormat("###");
            decimalFormatNoCommas.setRoundingMode(RoundingMode.HALF_UP);
            String formattedPriceNoCommas = decimalFormatNoCommas.format(price);

// Chuyển đổi chuỗi định dạng thành số nguyên
            int intValue = Integer.parseInt(formattedPriceNoCommas);
            String selectedCustomerType = request.getParameter("selectedCustomerType");
            String content2 = "<p>Quý khách kiểm tra lại thông tin đơn hàng và thực hiện thanh toán khoản đặt cọc cho xe</p>\n"
                    + "                        <h4>THÔNG TIN XE</h4>\n"
                    + "                        <div class=\"price\">\n"
                    + "                         <div class=\"info\">"
                    + "                            <p>Camry 2.0G</p>\n"
                    + "                            <p>\n";
            content2 += formattedPrice;
            content2
                    += "                                <span>VNĐ</span>\n"
                    + "                            </p>\n"
                    + "                        </div>\n"
                    + "                        </div>\n"
                    + "                        <small class=\"small\">Giá xe đã bao gồm VAT, và đã áp mã giảm giá(nếu có)</small>\n"
                    + "                        <h4>THÔNG TIN CHỦ XE</h4>\n"
                    + "                         <form action=\"cardeposit\" method=\"POST\">"
                    + "                        <div class=\"form-group\">\n";
            if (selectedCustomerType.equals("personal")) {
                content2
                        += "                            <label class=\"half-width\">Chủ xe</label>\n";
            } else {
                content2
                        += "                            <label class=\"half-width\">Tên doanh nghiệp</label>\n";
            }
            content2 += "<p class=\"half-width\">" + customerName + "</p>\n";
            content2
                    += "                        </div>\n"
                    + "                             <input hidden value=\"" + customerName + "\" name=\"customerName\">"
                    + "                        <div class=\"form-group\">\n"
                    + "                            <label class=\"half-width\">Số điện thoại</label>\n";
            content2 += "<p class=\"half-width\">" + phoneNumber + "</p>\n";
            content2
                    += "                        </div>\n"
                    + "                             <input hidden value=\"" + phoneNumber + "\" name=\"phoneNumber\">"
                    + "                        <div class=\"form-group\">\n"
                    + "                            <label class=\"half-width\">Email</label>\n";
            content2 += "<p class=\"half-width\">" + email + "</p>\n";
            content2
                    += "                        </div>\n"
                    + "                             <input hidden value=\"" + email + "\" name=\"email\">"
                    + "                        <div class=\"form-group\">\n";
            if (selectedCustomerType.equals("personal")) {
                content2
                        += "                            <label class=\"half-width\">Số CMND/CCCD</label>\n";
            } else {
                content2
                        += "                            <label class=\"half-width\">Mã số doanh nghiệp</label>\n";
            }

            content2 += "<p class=\"half-width\">" + noID + "</p>\n";
            content2
                    += "                       </div>\n"
                    + "                             <input hidden value=\"" + noID + "\" name=\"noID\">"
                    + "                             <input hidden value=\"" + intValue + "\" name=\"price\">"
                    + "                             <input hidden value=\"" + carID + "\" name=\"carID\">"
                    + "                        <small class=\"small\"></small>\n"
                    + "                        <h4>Hình thức thanh toán</h4>\n"
                    + "                            <div id=\"click-pay-1\" class=\"pay-method\">\n"
                    + "                                <input type=\"radio\" name=\"pay\" id=\"pay-1\" value=\"1\" required=\"\"/>\n"
                    + "                                <label for=\"pay-1\">Thanh toán bằng hình thức chuyển khoản</label>\n"
                    + "                            </div>\n"
                    + "                            <hr>\n"
                    + "                            <div id=\"click-pay-2\" class=\"pay-method\">\n"
                    + "                                <input type=\"radio\" name=\"pay\" id=\"pay-2\" value=\"2\" required=\"\"/>\n"
                    + "                                <label for=\"pay-2\">Cổng thanh toán VNPAY</label>\n"
                    + "                            </div>\n"
                    + "                            <label class=\"dieu_khoan\"><input type=\"checkbox\" name=\"checkbox1\" required> Tôi cam kết các thông tin đã cung cấp tại đây hoàn toàn chính xác.</label>\n"
                    + "                            <label class=\"dieu_khoan\"><input type=\"checkbox\" name=\"checkbox2\" required> Tôi đã đọc, hiểu rõ và xác nhận đồng ý với toàn bộ nội dung <a href=\"https://drive.google.com/file/d/1CVOKKTcGIpG_johcPxY0YNTN7_n_0_l9/view?usp=sharing\" target=\"_blank\" style=\"color: blue; text-decoration: blue;\">Điều kiện & Điều khoản</a> trong Thỏa Thuận Đặt Cọc trên cũng như Chính Sách Ưu Đãi áp dụng tại thời điểm đặt mua xe ô tô này trên Auto99 Online.</label>\n"
                    + "                            <label class=\"dieu_khoan\"><input type=\"checkbox\" name=\"checkbox3\" required> Tôi đồng ý với các <a href=\"blogdetail?blogID=48\" target=\"_blank\" style=\"color: blue; text-decoration: blue;\">Chính sách và quyển riêng tư</a> của Auto99 Online</label>\n"
                    + "                            <input type=\"submit\" id=\"depositButton\" value=\"THANH TOÁN ĐẶT CỌC\" >"
                    + "                    </form>\n"
                    + "                    </div>";

            // Thiết lập kiểu dữ liệu và mã kí tự
            response.setContentType("text/html");
            response.setCharacterEncoding("UTF-8");

            // In nội dung content2 để trả về cho AJAX
            out.println(content2);

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
