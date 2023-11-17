/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.Staff;

import Service.SendMail;
import dal.CarOrderDAO;
import dal.ClientDAO;
import dal.VoucherDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Dao Anh Duc
 */
@WebServlet(name = "CarOrderCRUD", urlPatterns = {"/carordercrud"})
public class CarOrderCRUD extends BaseAuthencationAndAuthorRequire {

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
            out.println("<title>Servlet CarOrderCRUD</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CarOrderCRUD at " + request.getContextPath() + "</h1>");
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
    protected void processGet(HttpServletRequest request, HttpServletResponse response)
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
    protected void processPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        CarOrderDAO cDAO = new CarOrderDAO();
        ClientDAO clDAO = new ClientDAO();
        SendMail sm = new SendMail();
        if ("UpdateCarOrder".equals(action)) {
            int modifiedBy = Integer.parseInt(request.getParameter("user"));
            String carorderIDParam = request.getParameter("carorderID");
            int carorderID = 0;

            if (carorderIDParam != null && !carorderIDParam.isEmpty()) {
                carorderID = Integer.parseInt(carorderIDParam);
            }
            int status = Integer.parseInt(request.getParameter("status"));
            boolean paymentType = Boolean.parseBoolean(request.getParameter("paymentType"));

            String carorderCode = request.getParameter("carorderCode");
            String clientName = request.getParameter("clientName");
            String carName = request.getParameter("carName");
            int clientID = Integer.parseInt(request.getParameter("clientID"));
            String email = clDAO.getEmailByClientID(clientID);
            LocalDateTime currentDateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String dateTimeUpdate = currentDateTime.format(formatter);
            VoucherDAO vdao = new VoucherDAO();
            String voucherCode = request.getParameter("voucherCode");
            int usedCount = vdao.getUsedCountByVoucherCode(voucherCode);
            if (paymentType) {
                if (status == 1) {

                    String emailContent = "<!DOCTYPE html>\n"
                            + "<html>\n"
                            + "    <head>\n"
                            + "        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n"
                            + "    </head>\n"
                            + "    <body>\n"
                            + "        <div>\n"
                            + "            <span class=\"im\">\n"
                            + "                <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"width:100%\">\n"
                            + "                    <tbody>\n"
                            + "                        <tr>\n"
                            + "                            <td>\n"
                            + "                                <div style=\"margin:0px auto;max-width:600px\">\n"
                            + "                                    <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"width:100%\">\n"
                            + "                                        <tbody>\n"
                            + "                                            <tr>\n"
                            + "                                                <td style=\"direction:ltr;font-size:0px;padding:20px 0;text-align:center\">\n"
                            + "                                                    <div class=\"m_-2729878306671754177mj-column-px-600\" style=\"font-size:0px;text-align:left;direction:ltr;display:inline-block;vertical-align:top;width:100%\">\n"
                            + "                                                        <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"vertical-align:top\" width=\"100%\">\n"
                            + "                                                            <tbody>\n"
                            + "                                                                <tr>\n"
                            + "                                                                    <td align=\"right\" style=\"font-size:0px;padding:10px 25px;word-break:break-word\">\n"
                            + "                                                                        <div style=\"font-family:Mulish;font-size:12px;line-height:1;text-align:right;color:#000000\">\n"
                            + "                                                                        </div>\n"
                            + "                                                                    </td>\n"
                            + "                                                                </tr>\n"
                            + "                                                                <tr>\n"
                            + "                                                                    <td align=\"center\" style=\"font-size:0px;padding:20px 0 0 0;word-break:break-word\">\n"
                            + "                                                                        <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"border-collapse:collapse;border-spacing:0px\">\n"
                            + "                                                                            <tbody>\n"
                            + "                                                                                <tr>\n"
                            + "                                                                                    <td style=\"width:200px\">\n"
                            + "                                                                                        <img height=\"55\" src=\"https://scontent.fhan19-1.fna.fbcdn.net/v/t1.15752-9/368566981_880883536717815_8649698896739440215_n.png?_nc_cat=108&ccb=1-7&_nc_sid=8cd0a2&_nc_ohc=gr5ScB9rv0UAX_iAC_E&_nc_ht=scontent.fhan19-1.fna&oh=03_AdQZIf0F3cDuvUwa40IG1WmbxkUW2u9VJHPOi0ccuhEdcg&oe=65656FA1\" style=\"border:0;display:block;outline:none;text-decoration:none;height:55px;width:100%;font-size:13px\" width=\"200\" class=\"CToWUd\" data-bit=\"iit\">\n"
                            + "                                                                                    </td>\n"
                            + "                                                                                </tr>\n"
                            + "                                                                            </tbody>\n"
                            + "                                                                        </table>\n"
                            + "                                                                    </td>\n"
                            + "                                                                </tr>\n"
                            + "                                                            </tbody>\n"
                            + "                                                        </table>\n"
                            + "                                                    </div>\n"
                            + "                                                </td>\n"
                            + "                                            </tr>\n"
                            + "                                        </tbody>\n"
                            + "                                    </table>\n"
                            + "                                </div>\n"
                            + "                            </td>\n"
                            + "                        </tr>\n"
                            + "                    </tbody>\n"
                            + "                </table>\n"
                            + "            </span>\n"
                            + "            <div style=\"margin:0px auto;max-width:600px\">\n"
                            + "                <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"width:100%\">\n"
                            + "                    <tbody>\n"
                            + "                        <tr>\n"
                            + "                            <td style=\"direction:ltr;font-size:0px;padding:0 0 0 0;text-align:center\">\n"
                            + "                                <div class=\"m_-2729878306671754177mj-column-px-600\" style=\"font-size:0px;text-align:left;direction:ltr;display:inline-block;vertical-align:top;width:100%\">\n"
                            + "                                    <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" width=\"100%\">\n"
                            + "                                        <tbody>\n"
                            + "                                            <tr>\n"
                            + "                                                <td style=\"vertical-align:top;padding:30px 8.5% 30px 8.5%\">\n"
                            + "                                                    <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" width=\"100%\">\n"
                            + "                                                        <tbody>\n"
                            + "                                                            <tr>\n"
                            + "                                                                <td align=\"center\" style=\"font-size:0px;padding:0 0 30px 0;word-break:break-word\">\n"
                            + "                                                                    <div style=\"font-family:Mulish;font-size:24px;font-weight:600;line-height:30px;text-align:center;color:red\">\n"
                            + "                                                                        Kính gửi: Quý khách" + clientName + "</div>\n"
                            + "                                                                </td>\n"
                            + "                                                            </tr>\n"
                            + "                                                            <tr>\n"
                            + "                                                                <td align=\"left\" style=\"font-size:0px;padding:0 0 0 0;word-break:break-word\">\n"
                            + "                                                                    <div style=\"font-family:Mulish;font-size:13px;line-height:150%;text-align:left;color:#3c3c3c\">\n"
                            + "                                                                        Cảm ơn Quý khách đã đặt mua xe Auto tại <a href=\"http://localhost:8015/Auto99/home\" style=\"text-decoration:none\" target=\"_blank\" data-saferedirecturl=\"http://localhost:8015/Auto99/home\">Auto99.com</a>.<br> Đờn hàng của quý khách đã được xác nhận.<br>\n"
                            + "                                                                        Vui lòng đến Auto99<br>\n"
                            + "                                                                        Địa chỉ: 140 Nguyễn Văn Giáp, Quận Nam Từ Liêm, Hà Nội<br>\n"
                            + "                                                                        Để thực hiện việc làm hợp đồng mua xe</div>\n"
                            + "                                                                </td>\n"
                            + "                                                            </tr>\n"
                            + "                                                        </tbody>\n"
                            + "                                                    </table>\n"
                            + "                                                </td>\n"
                            + "                                            </tr>\n"
                            + "                                        </tbody>\n"
                            + "                                    </table>\n"
                            + "                                </div>\n"
                            + "                            </td>\n"
                            + "                        </tr>\n"
                            + "                    </tbody>\n"
                            + "                </table>\n"
                            + "            </div>\n"
                            + "            <div style=\"margin: 0px auto; max-width: 600px;\">\n"
                            + "                <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"width: 100%;\">\n"
                            + "                    <tbody>\n"
                            + "                        <tr>\n"
                            + "                            <td style=\"direction: ltr; font-size: 0px; padding: 0px 8.5% 30px 8.5%; text-align: center;\">\n"
                            + "                                <div class=\"m_-2729878306671754177mj-column-per-100\" style=\"font-size: 0px; text-align: left; direction: ltr; display: inline-block; vertical-align: top; width: 100%;\">\n"
                            + "                                    <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" width=\"100%\">\n"
                            + "                                        <tbody>\n"
                            + "                                            <tr>\n"
                            + "                                                <td style=\"vertical-align: top; padding: 0 0 0 0;\">\n"
                            + "                                                    <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" width=\"100%\">\n"
                            + "                                                        <tbody>\n"
                            + "                                                            <tr>\n"
                            + "                                                                <td align=\"left\" style=\"font-size: 0px; padding: 0 0 0 0; word-break: break-word;\">\n"
                            + "                                                                    <div style=\"font-family: Mulish; font-size: 13px; line-height: 150%; text-align: left; color: #3c3c3c;\">\n"
                            + "                                                                        <span class=\"im\"><b>THÔNG TIN CHI TIẾT ĐƠN HÀNG</b><br><br></span>\n"
                            + "                                                                        <b>Mã đơn hàng:</b>" + carorderCode + "<span class=\"im\"><br><br></span>\n"
                            + "                                                                        <b>Trạng thái đơn hàng: </b> <span style=\"color: red; font-weight: bold;\">XÁC NHẬN ĐẶT CỌC</span><br><br>\n"
                            + "                                                                        <b>Sản phẩm:</b>" + carName + "<br>\n"
                            + "                                                                        <b>Số lượng: </b> 01<br><br>\n"
                            + "                                                                        <b>Số tiền đã đặt cọc: </b> 20.000.000 VNĐ<br><br>\n"
                            + "                                                                        <b>Phương thức thanh toán: </b> Thanh toán qua VNPAY<br>                               \n"
                            + "                                                                    </div>\n"
                            + "                                                                </td>\n"
                            + "                                                            </tr>\n"
                            + "                                                        </tbody>\n"
                            + "                                                    </table>\n"
                            + "                                                </td>\n"
                            + "                                            </tr>\n"
                            + "                                        </tbody>\n"
                            + "                                    </table>\n"
                            + "                                </div>\n"
                            + "                            </td>\n"
                            + "                        </tr>\n"
                            + "                    </tbody>\n"
                            + "                </table>\n"
                            + "            </div>\n"
                            + "            <span class=\"im\"></span>\n"
                            + "            <div style=\"margin: 0px auto; max-width: 600px;\">\n"
                            + "                <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"width: 100%;\">\n"
                            + "                    <tbody>\n"
                            + "                        <tr>\n"
                            + "                            <td style=\"direction: ltr; font-size: 0px; padding: 0 0 0 0; text-align: center;\">\n"
                            + "                                <div class=\"m_-2729878306671754177mj-column-px-600\" style=\"font-size: 0px; text-align: left; direction: ltr; display: inline-block; vertical-align: top; width: 100%;\">\n"
                            + "                                    <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" width=\"100%\">\n"
                            + "                                        <tbody>\n"
                            + "                                            <tr>\n"
                            + "                                                <td style=\"vertical-align: top; padding: 0 8.5% 30px 8.5%;\">\n"
                            + "                                                    <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" width=\"100%\">\n"
                            + "                                                        <tbody>\n"
                            + "                                                            <tr>\n"
                            + "                                                                <td align=\"left\" style=\"font-size: 0px; padding: 0 0 30px 0; word-break: break-word;\">\n"
                            + "                                                                    <div style=\"font-family: Mulish; font-size: 13px; font-weight: 600; line-height: 24px; text-align: left; color: #3c3c3c;\">\n"
                            + "                                                                        Trân trọng.<br><br>\n"
                            + "                                                                        Auto99 - Cùng bạn bứt phá mọi giới hạn.\n"
                            + "                                                                    </div>\n"
                            + "                                                                </td>\n"
                            + "                                                            </tr>\n"
                            + "                                                        </tbody>\n"
                            + "                                                    </table>\n"
                            + "                                                </td>\n"
                            + "                                            </tr>\n"
                            + "                                        </tbody>\n"
                            + "                                    </table>\n"
                            + "                                </div>\n"
                            + "                            </td>\n"
                            + "                        </tr>\n"
                            + "                    </tbody>\n"
                            + "                </table>\n"
                            + "            </div>\n"
                            + "            <span class=\"im\"></span>\n"
                            + "            <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"width: 100%;\">\n"
                            + "                <tbody>\n"
                            + "                    <tr>\n"
                            + "                        <td>\n"
                            + "                            <div style=\"margin: 0px auto; max-width: 600px;\">\n"
                            + "                                <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"width: 100%;\">\n"
                            + "                                    <tbody>\n"
                            + "                                        <tr>\n"
                            + "                                            <td style=\"direction: ltr; font-size: 0px; padding: 0 0 0 0; text-align: center;\">\n"
                            + "                                                <div class=\"m_-2729878306671754177mj-column-px-600\" style=\"font-size: 0px; text-align: left; direction: ltr; display: inline-block; vertical-align: top; width: 100%;\">\n"
                            + "                                                    <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" width=\"100%\">\n"
                            + "                                                        <tbody>\n"
                            + "                                                            <tr>\n"
                            + "                                                                <td style=\"vertical-align: top; padding: 0 0 0 0;\">\n"
                            + "                                                                    <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" width=\"100%\">\n"
                            + "                                                                        <tbody>\n"
                            + "                                                                            <tr>\n"
                            + "                                                                                <td align=\"center\" style=\"font-size: 0px; padding: 0 0 0 0; word-break: break-word;\">\n"
                            + "                                                                                    <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"border-collapse: collapse; border-spacing: 0px;\">\n"
                            + "                                                                                        <tbody>\n"
                            + "                                                                                            <tr>\n"
                            + "                                                                                                <td style=\"width: 600px;\">\n"
                            + "                                                                                                    <div class=\"a6S\" dir=\"ltr\" style=\"opacity: 0.01; left: 635.5px; top: 1257px;\">\n"
                            + "                                                                                                        data-tooltip-class=\"a1V\" jsaction=\"JIbuQc:.CLIENT\" data-tooltip=\"Tải xuống\">\n"
                            + "                                                                                                        <div class=\"akn\">\n"
                            + "                                                                                                            <div class=\"aSK J-J5-Ji aYr\"></div>\n"
                            + "                                                                                                        </div>\n"
                            + "                                                                                                    </div>\n"
                            + "                                                                                                </td>\n"
                            + "                                                                                            </tr>\n"
                            + "                                                                                        </tbody>\n"
                            + "                                                                                    </table>\n"
                            + "                                                                                </td>\n"
                            + "                                                                            </tr>\n"
                            + "                                                                        </tbody>\n"
                            + "                                                                    </table>\n"
                            + "                                                                </td>\n"
                            + "                                                            </tr>\n"
                            + "                                                        </tbody>\n"
                            + "                                                    </table>\n"
                            + "                                                </div>\n"
                            + "                                            </td>\n"
                            + "                                        </tr>\n"
                            + "                                    </tbody>\n"
                            + "                                </table>\n"
                            + "                            </div>\n"
                            + "                        </td>\n"
                            + "                    </tr>\n"
                            + "                </tbody>\n"
                            + "            </table>\n"
                            + "            <div style=\"margin:0px auto;max-width:600px\">\n"
                            + "                <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"width:100%\">\n"
                            + "                    <tbody>\n"
                            + "                        <tr>\n"
                            + "                            <td style=\"direction:ltr;font-size:0px;padding:30px 0 0 0;text-align:center\">\n"
                            + "                                <div class=\"m_-2729878306671754177mj-column-px-600\" style=\"font-size:0px;text-align:left;direction:ltr;display:inline-block;vertical-align:top;width:100%\">\n"
                            + "                                    <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" width=\"100%\">\n"
                            + "                                        <tbody>\n"
                            + "                                            <tr>\n"
                            + "                                                <td style=\"vertical-align:top;padding-right:0px;padding-left:0px\">\n"
                            + "                                                    <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" width=\"100%\">\n"
                            + "                                                        <tbody>\n"
                            + "                                                            <tr>\n"
                            + "                                                                <td align=\"center\" style=\"font-size:0px;padding:2.5% 2.5% 0 2.5%;word-break:break-word\">\n"
                            + "                                                                    <div style=\"font-family:Mulish;font-size:13px;line-height:24px;text-align:center;color:red\">\n"
                            + "                                                                        Quý khách cần hỗ trợ vui lòng liên hệ với Auto99 qua<br>\n"
                            + "                                                                        <a href=\"tel:1900232389\" style=\"font-weight:bold;text-decoration:none\" target=\"_blank\">Hotline 1900 23 23 89.</a>\n"
                            + "                                                                    </div>\n"
                            + "                                                                </td>\n"
                            + "                                                            </tr>\n"
                            + "                                                        </tbody>\n"
                            + "                                                    </table>\n"
                            + "                                                </td>\n"
                            + "                                            </tr>\n"
                            + "                                        </tbody>\n"
                            + "                                    </table>\n"
                            + "                                </div>\n"
                            + "                            </td>\n"
                            + "                        </tr>\n"
                            + "                    </tbody>\n"
                            + "                </table>\n"
                            + "\n"
                            + "            </div>\n"
                            + "\n"
                            + "    </body>\n"
                            + "</html>          ";
                    sm.sendEmail(email, emailContent, "ĐẶT CỌC THÀNH CÔNG");
                    cDAO.updateCarOrder(dateTimeUpdate, modifiedBy, status, carorderID);
                } else {
                    cDAO.updateCarOrder(dateTimeUpdate, modifiedBy, status, carorderID);
                }
            } else {
                if (status == 1) {
                    usedCount++;
                    vdao.updateUsageCount(voucherCode, usedCount);
                    String emailContent = "<!DOCTYPE html>\n"
                            + "<html>\n"
                            + "    <head>\n"
                            + "        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n"
                            + "    </head>\n"
                            + "    <body>\n"
                            + "        <div>\n"
                            + "            <span class=\"im\">\n"
                            + "                <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"width:100%\">\n"
                            + "                    <tbody>\n"
                            + "                        <tr>\n"
                            + "                            <td>\n"
                            + "                                <div style=\"margin:0px auto;max-width:600px\">\n"
                            + "                                    <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"width:100%\">\n"
                            + "                                        <tbody>\n"
                            + "                                            <tr>\n"
                            + "                                                <td style=\"direction:ltr;font-size:0px;padding:20px 0;text-align:center\">\n"
                            + "                                                    <div class=\"m_-2729878306671754177mj-column-px-600\" style=\"font-size:0px;text-align:left;direction:ltr;display:inline-block;vertical-align:top;width:100%\">\n"
                            + "                                                        <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"vertical-align:top\" width=\"100%\">\n"
                            + "                                                            <tbody>\n"
                            + "                                                                <tr>\n"
                            + "                                                                    <td align=\"right\" style=\"font-size:0px;padding:10px 25px;word-break:break-word\">\n"
                            + "                                                                        <div style=\"font-family:Mulish;font-size:12px;line-height:1;text-align:right;color:#000000\">\n"
                            + "                                                                        </div>\n"
                            + "                                                                    </td>\n"
                            + "                                                                </tr>\n"
                            + "                                                                <tr>\n"
                            + "                                                                    <td align=\"center\" style=\"font-size:0px;padding:20px 0 0 0;word-break:break-word\">\n"
                            + "                                                                        <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"border-collapse:collapse;border-spacing:0px\">\n"
                            + "                                                                            <tbody>\n"
                            + "                                                                                <tr>\n"
                            + "                                                                                    <td style=\"width:200px\">\n"
                            + "                                                                                        <img height=\"55\" src=\"https://scontent.fhan19-1.fna.fbcdn.net/v/t1.15752-9/368566981_880883536717815_8649698896739440215_n.png?_nc_cat=108&ccb=1-7&_nc_sid=8cd0a2&_nc_ohc=gr5ScB9rv0UAX_iAC_E&_nc_ht=scontent.fhan19-1.fna&oh=03_AdQZIf0F3cDuvUwa40IG1WmbxkUW2u9VJHPOi0ccuhEdcg&oe=65656FA1\" style=\"border:0;display:block;outline:none;text-decoration:none;height:55px;width:100%;font-size:13px\" width=\"200\" class=\"CToWUd\" data-bit=\"iit\">\n"
                            + "                                                                                    </td>\n"
                            + "                                                                                </tr>\n"
                            + "                                                                            </tbody>\n"
                            + "                                                                        </table>\n"
                            + "                                                                    </td>\n"
                            + "                                                                </tr>\n"
                            + "                                                            </tbody>\n"
                            + "                                                        </table>\n"
                            + "                                                    </div>\n"
                            + "                                                </td>\n"
                            + "                                            </tr>\n"
                            + "                                        </tbody>\n"
                            + "                                    </table>\n"
                            + "                                </div>\n"
                            + "                            </td>\n"
                            + "                        </tr>\n"
                            + "                    </tbody>\n"
                            + "                </table>\n"
                            + "            </span>\n"
                            + "            <div style=\"margin:0px auto;max-width:600px\">\n"
                            + "                <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"width:100%\">\n"
                            + "                    <tbody>\n"
                            + "                        <tr>\n"
                            + "                            <td style=\"direction:ltr;font-size:0px;padding:0 0 0 0;text-align:center\">\n"
                            + "                                <div class=\"m_-2729878306671754177mj-column-px-600\" style=\"font-size:0px;text-align:left;direction:ltr;display:inline-block;vertical-align:top;width:100%\">\n"
                            + "                                    <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" width=\"100%\">\n"
                            + "                                        <tbody>\n"
                            + "                                            <tr>\n"
                            + "                                                <td style=\"vertical-align:top;padding:30px 8.5% 30px 8.5%\">\n"
                            + "                                                    <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" width=\"100%\">\n"
                            + "                                                        <tbody>\n"
                            + "                                                            <tr>\n"
                            + "                                                                <td align=\"center\" style=\"font-size:0px;padding:0 0 30px 0;word-break:break-word\">\n"
                            + "                                                                    <div style=\"font-family:Mulish;font-size:24px;font-weight:600;line-height:30px;text-align:center;color:red\">\n"
                            + "                                                                        Kính gửi: Quý khách" + clientName + "</div>\n"
                            + "                                                                </td>\n"
                            + "                                                            </tr>\n"
                            + "                                                            <tr>\n"
                            + "                                                                <td align=\"left\" style=\"font-size:0px;padding:0 0 0 0;word-break:break-word\">\n"
                            + "                                                                    <div style=\"font-family:Mulish;font-size:13px;line-height:150%;text-align:left;color:#3c3c3c\">\n"
                            + "                                                                        Cảm ơn Quý khách đã đặt mua xe Auto tại <a href=\"http://localhost:8015/Auto99/home\" style=\"text-decoration:none\" target=\"_blank\" data-saferedirecturl=\"http://localhost:8015/Auto99/home\">Auto99.com</a>.<br> Đờn hàng của quý khách đã được xác nhận.<br>\n"
                            + "                                                                        Vui lòng đến Auto99<br>\n"
                            + "                                                                        Địa chỉ: 140 Nguyễn Văn Giáp, Quận Nam Từ Liêm, Hà Nội<br>\n"
                            + "                                                                        Để thực hiện việc làm hợp đồng mua xe</div>\n"
                            + "                                                                </td>\n"
                            + "                                                            </tr>\n"
                            + "                                                        </tbody>\n"
                            + "                                                    </table>\n"
                            + "                                                </td>\n"
                            + "                                            </tr>\n"
                            + "                                        </tbody>\n"
                            + "                                    </table>\n"
                            + "                                </div>\n"
                            + "                            </td>\n"
                            + "                        </tr>\n"
                            + "                    </tbody>\n"
                            + "                </table>\n"
                            + "            </div>\n"
                            + "            <div style=\"margin: 0px auto; max-width: 600px;\">\n"
                            + "                <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"width: 100%;\">\n"
                            + "                    <tbody>\n"
                            + "                        <tr>\n"
                            + "                            <td style=\"direction: ltr; font-size: 0px; padding: 0px 8.5% 30px 8.5%; text-align: center;\">\n"
                            + "                                <div class=\"m_-2729878306671754177mj-column-per-100\" style=\"font-size: 0px; text-align: left; direction: ltr; display: inline-block; vertical-align: top; width: 100%;\">\n"
                            + "                                    <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" width=\"100%\">\n"
                            + "                                        <tbody>\n"
                            + "                                            <tr>\n"
                            + "                                                <td style=\"vertical-align: top; padding: 0 0 0 0;\">\n"
                            + "                                                    <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" width=\"100%\">\n"
                            + "                                                        <tbody>\n"
                            + "                                                            <tr>\n"
                            + "                                                                <td align=\"left\" style=\"font-size: 0px; padding: 0 0 0 0; word-break: break-word;\">\n"
                            + "                                                                    <div style=\"font-family: Mulish; font-size: 13px; line-height: 150%; text-align: left; color: #3c3c3c;\">\n"
                            + "                                                                        <span class=\"im\"><b>THÔNG TIN CHI TIẾT ĐƠN HÀNG</b><br><br></span>\n"
                            + "                                                                        <b>Mã đơn hàng:</b>" + carorderCode + "<span class=\"im\"><br><br></span>\n"
                            + "                                                                        <b>Trạng thái đơn hàng: </b> <span style=\"color: red; font-weight: bold;\">XÁC NHẬN ĐẶT CỌC</span><br><br>\n"
                            + "                                                                        <b>Sản phẩm:</b>" + carName + "<br>\n"
                            + "                                                                        <b>Số lượng: </b> 01<br><br>\n"
                            + "                                                                        <b>Số tiền đã đặt cọc: </b> 20.000.000 VNĐ<br><br>\n"
                            + "                                                                        <b>Phương thức thanh toán: </b> Chuyển khoản ngân hàng<br>                               \n"
                            + "                                                                    </div>\n"
                            + "                                                                </td>\n"
                            + "                                                            </tr>\n"
                            + "                                                        </tbody>\n"
                            + "                                                    </table>\n"
                            + "                                                </td>\n"
                            + "                                            </tr>\n"
                            + "                                        </tbody>\n"
                            + "                                    </table>\n"
                            + "                                </div>\n"
                            + "                            </td>\n"
                            + "                        </tr>\n"
                            + "                    </tbody>\n"
                            + "                </table>\n"
                            + "            </div>\n"
                            + "            <span class=\"im\"></span>\n"
                            + "            <div style=\"margin: 0px auto; max-width: 600px;\">\n"
                            + "                <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"width: 100%;\">\n"
                            + "                    <tbody>\n"
                            + "                        <tr>\n"
                            + "                            <td style=\"direction: ltr; font-size: 0px; padding: 0 0 0 0; text-align: center;\">\n"
                            + "                                <div class=\"m_-2729878306671754177mj-column-px-600\" style=\"font-size: 0px; text-align: left; direction: ltr; display: inline-block; vertical-align: top; width: 100%;\">\n"
                            + "                                    <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" width=\"100%\">\n"
                            + "                                        <tbody>\n"
                            + "                                            <tr>\n"
                            + "                                                <td style=\"vertical-align: top; padding: 0 8.5% 30px 8.5%;\">\n"
                            + "                                                    <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" width=\"100%\">\n"
                            + "                                                        <tbody>\n"
                            + "                                                            <tr>\n"
                            + "                                                                <td align=\"left\" style=\"font-size: 0px; padding: 0 0 30px 0; word-break: break-word;\">\n"
                            + "                                                                    <div style=\"font-family: Mulish; font-size: 13px; font-weight: 600; line-height: 24px; text-align: left; color: #3c3c3c;\">\n"
                            + "                                                                        Trân trọng.<br><br>\n"
                            + "                                                                        Auto99 - Cùng bạn bứt phá mọi giới hạn.\n"
                            + "                                                                    </div>\n"
                            + "                                                                </td>\n"
                            + "                                                            </tr>\n"
                            + "                                                        </tbody>\n"
                            + "                                                    </table>\n"
                            + "                                                </td>\n"
                            + "                                            </tr>\n"
                            + "                                        </tbody>\n"
                            + "                                    </table>\n"
                            + "                                </div>\n"
                            + "                            </td>\n"
                            + "                        </tr>\n"
                            + "                    </tbody>\n"
                            + "                </table>\n"
                            + "            </div>\n"
                            + "            <span class=\"im\"></span>\n"
                            + "            <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"width: 100%;\">\n"
                            + "                <tbody>\n"
                            + "                    <tr>\n"
                            + "                        <td>\n"
                            + "                            <div style=\"margin: 0px auto; max-width: 600px;\">\n"
                            + "                                <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"width: 100%;\">\n"
                            + "                                    <tbody>\n"
                            + "                                        <tr>\n"
                            + "                                            <td style=\"direction: ltr; font-size: 0px; padding: 0 0 0 0; text-align: center;\">\n"
                            + "                                                <div class=\"m_-2729878306671754177mj-column-px-600\" style=\"font-size: 0px; text-align: left; direction: ltr; display: inline-block; vertical-align: top; width: 100%;\">\n"
                            + "                                                    <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" width=\"100%\">\n"
                            + "                                                        <tbody>\n"
                            + "                                                            <tr>\n"
                            + "                                                                <td style=\"vertical-align: top; padding: 0 0 0 0;\">\n"
                            + "                                                                    <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" width=\"100%\">\n"
                            + "                                                                        <tbody>\n"
                            + "                                                                            <tr>\n"
                            + "                                                                                <td align=\"center\" style=\"font-size: 0px; padding: 0 0 0 0; word-break: break-word;\">\n"
                            + "                                                                                    <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"border-collapse: collapse; border-spacing: 0px;\">\n"
                            + "                                                                                        <tbody>\n"
                            + "                                                                                            <tr>\n"
                            + "                                                                                                <td style=\"width: 600px;\">\n"
                            + "                                                                                                    <div class=\"a6S\" dir=\"ltr\" style=\"opacity: 0.01; left: 635.5px; top: 1257px;\">\n"
                            + "                                                                                                        data-tooltip-class=\"a1V\" jsaction=\"JIbuQc:.CLIENT\" data-tooltip=\"Tải xuống\">\n"
                            + "                                                                                                        <div class=\"akn\">\n"
                            + "                                                                                                            <div class=\"aSK J-J5-Ji aYr\"></div>\n"
                            + "                                                                                                        </div>\n"
                            + "                                                                                                    </div>\n"
                            + "                                                                                                </td>\n"
                            + "                                                                                            </tr>\n"
                            + "                                                                                        </tbody>\n"
                            + "                                                                                    </table>\n"
                            + "                                                                                </td>\n"
                            + "                                                                            </tr>\n"
                            + "                                                                        </tbody>\n"
                            + "                                                                    </table>\n"
                            + "                                                                </td>\n"
                            + "                                                            </tr>\n"
                            + "                                                        </tbody>\n"
                            + "                                                    </table>\n"
                            + "                                                </div>\n"
                            + "                                            </td>\n"
                            + "                                        </tr>\n"
                            + "                                    </tbody>\n"
                            + "                                </table>\n"
                            + "                            </div>\n"
                            + "                        </td>\n"
                            + "                    </tr>\n"
                            + "                </tbody>\n"
                            + "            </table>\n"
                            + "            <div style=\"margin:0px auto;max-width:600px\">\n"
                            + "                <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"width:100%\">\n"
                            + "                    <tbody>\n"
                            + "                        <tr>\n"
                            + "                            <td style=\"direction:ltr;font-size:0px;padding:30px 0 0 0;text-align:center\">\n"
                            + "                                <div class=\"m_-2729878306671754177mj-column-px-600\" style=\"font-size:0px;text-align:left;direction:ltr;display:inline-block;vertical-align:top;width:100%\">\n"
                            + "                                    <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" width=\"100%\">\n"
                            + "                                        <tbody>\n"
                            + "                                            <tr>\n"
                            + "                                                <td style=\"vertical-align:top;padding-right:0px;padding-left:0px\">\n"
                            + "                                                    <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" width=\"100%\">\n"
                            + "                                                        <tbody>\n"
                            + "                                                            <tr>\n"
                            + "                                                                <td align=\"center\" style=\"font-size:0px;padding:2.5% 2.5% 0 2.5%;word-break:break-word\">\n"
                            + "                                                                    <div style=\"font-family:Mulish;font-size:13px;line-height:24px;text-align:center;color:red\">\n"
                            + "                                                                        Quý khách cần hỗ trợ vui lòng liên hệ với Auto99 qua<br>\n"
                            + "                                                                        <a href=\"tel:1900232389\" style=\"font-weight:bold;text-decoration:none\" target=\"_blank\">Hotline 1900 23 23 89.</a>\n"
                            + "                                                                    </div>\n"
                            + "                                                                </td>\n"
                            + "                                                            </tr>\n"
                            + "                                                        </tbody>\n"
                            + "                                                    </table>\n"
                            + "                                                </td>\n"
                            + "                                            </tr>\n"
                            + "                                        </tbody>\n"
                            + "                                    </table>\n"
                            + "                                </div>\n"
                            + "                            </td>\n"
                            + "                        </tr>\n"
                            + "                    </tbody>\n"
                            + "                </table>\n"
                            + "\n"
                            + "            </div>\n"
                            + "\n"
                            + "    </body>\n"
                            + "</html>          ";
                    sm.sendEmail(email, emailContent, "ĐẶT CỌC THÀNH CÔNG");
                    long orderValue = Long.parseLong(request.getParameter("orderValue"));
                    long paid = 20000000;
                    long paymentRequired = orderValue - paid;
                    cDAO.updateCarOrder2(dateTimeUpdate, modifiedBy, paymentRequired, paid, status, carorderID);
                } else {
                    long orderValue = Long.parseLong(request.getParameter("orderValue"));
                    long paid = 0;
                    long paymentRequired = orderValue - paid;
                    cDAO.updateCarOrder2(dateTimeUpdate, modifiedBy, paymentRequired, paid, status, carorderID);
                }
            }

            response.sendRedirect("carorderlist");
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
