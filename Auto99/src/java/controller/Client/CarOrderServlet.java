/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.Client;

import Service.Config;
import Service.SendMail;
import Service.Sercurity;
import Service.TextEmail;
import dal.BlogDAO;
import dal.CarDAO;
import dal.CarOrderDAO;
import dal.ClientAccountDAO;
import dal.ClientDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.Blog;
import model.BlogCategory;
import model.Car;
import model.CarOrder;
import model.CarStaffPage;
import model.Client;
import model.Voucher;

/**
 *
 * @author Dao Anh Duc
 */
@WebServlet(name = "CarDeposit", urlPatterns = {"/cardeposit"})
public class CarOrderServlet extends HttpServlet {

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
            out.println("<title>Servlet CarDeposit</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CarDeposit at " + request.getContextPath() + "</h1>");
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
        HttpSession session = request.getSession();

        //Lấy list blog tin tức và blog sub category để hiển thị ơ bloghome
        BlogDAO bdao = new BlogDAO();
        List<BlogCategory> listbc = bdao.getAllBlogCategory();
        session.setAttribute("ListBC", listbc);
        List<Blog> listb = bdao.getAllBlog();
        List<Blog> listb1 = bdao.getAllTrueBlog();
        List<Blog> listf = bdao.getFooter();
        session.setAttribute("Footer", listf);

        String ID = request.getParameter("carID");
        if (ID.equals("") || ID.equals(null)) {
            ID = "0";
        }
        int carID = Integer.parseInt(ID);

        Map<Integer, Integer> parentIDCountMap = new HashMap<>();
        for (Blog blog : listb) {
            int parentID = blog.getParentID();
            if (parentID != 0) {
                parentIDCountMap.put(parentID, parentIDCountMap.getOrDefault(parentID, 0) + 1);
            }
        }

        CarDAO DAO = new CarDAO();
        CarStaffPage Car = DAO.getCarByIDHome(carID);
        session.setAttribute("Car", Car);
        session.setAttribute("ParentIDCountMap", parentIDCountMap);
        session.setAttribute("ListBlog", listb);
        request.setAttribute("carID", carID);
        session.setAttribute("ListBlog1", listb1);

        request.getRequestDispatcher("./Client/CarOrder.jsp").forward(request, response);
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
        ClientDAO cDAO = new ClientDAO();
        CarOrderDAO coDAO = new CarOrderDAO();
        CarDAO carDAO = new CarDAO();
        Sercurity s = new Sercurity();
        SendMail sm = new SendMail();
        TextEmail textEmail = new TextEmail();
        ClientAccountDAO caDAO = new ClientAccountDAO();
        HttpSession session = request.getSession();
        String price = request.getParameter("price");

        if (price == null) {
            price = "0";
        }
        long finalPrice = Long.parseLong(price);
        String customerName = request.getParameter("customerName");
        String phoneNumber = request.getParameter("phoneNumber");
        String email = request.getParameter("email");
        String noID = request.getParameter("noID");
        String pay = request.getParameter("pay");
        String ID = request.getParameter("carID");
        Voucher voucher = (Voucher) session.getAttribute("VoucherInfo");
        int voucherID = 0;
        if (voucher != null) {
            voucherID = voucher.getVoucherID();
        }
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String createdOn = currentDateTime.format(formatter);

        if (ID.equals("") || ID.equals(null)) {
            ID = "0";
        }
        int carID = Integer.parseInt(ID);
        String carName = carDAO.getCarNameByCarID(carID);
        String carorderCode = Config.getRandomAlphaNumeric(13);
        String clientPassword = Sercurity.generateRandomString();

        int payment = Integer.parseInt(pay);

        if (payment == 1) {
            boolean emailExist = cDAO.checkEmailExist(email);
            LocalDateTime orderDateTime = currentDateTime.plusHours(1); // Thêm 1 giờ
            String orderDate = orderDateTime.format(formatter);
            // Nếu email tồn tại sẽ không tạo ra account mới cho người dùng mà sẽ chỉ chuyền car order vào trong account mà có email giống với email người dùng vừa nhập
            // Đồng thời thêm vào bảng CarOrderServlet
            if (emailExist) {
                int clientID;
                clientID = cDAO.getClientIDByEmail(email);
                if (voucher != null) {
                    coDAO.InsertCarOrder(carorderCode, clientID, carID, false, finalPrice, 0, finalPrice, createdOn, orderDate, "0", voucherID);
                } else {
                    coDAO.InsertCarOrder2(carorderCode, clientID, carID, false, finalPrice, 0, finalPrice, createdOn, orderDate, "0");
                }
                String emailContent = "<html>\n"
                        + "    <head>\n"
                        + "        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n"
                        + "    </head>\n"
                        + "    <body>\n"
                        + "        <div><span class=\"im\">\n"
                        + "    <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"width:100%\">\n"
                        + "      <tbody>\n"
                        + "        <tr>\n"
                        + "          <td>\n"
                        + "            \n"
                        + "            <div style=\"margin:0px auto;max-width:600px\">\n"
                        + "              <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"width:100%\">\n"
                        + "                <tbody>\n"
                        + "                  <tr>\n"
                        + "                    <td style=\"direction:ltr;font-size:0px;padding:20px 0;text-align:center\">\n"
                        + "                      \n"
                        + "                      <div class=\"m_-2729878306671754177mj-column-px-600\" style=\"font-size:0px;text-align:left;direction:ltr;display:inline-block;vertical-align:top;width:100%\">\n"
                        + "                        <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"vertical-align:top\" width=\"100%\">\n"
                        + "                          <tbody>\n"
                        + "                            <tr>\n"
                        + "                              <td align=\"right\" style=\"font-size:0px;padding:10px 25px;word-break:break-word\">\n"
                        + "                                <div style=\"font-family:Mulish;font-size:12px;line-height:1;text-align:right;color:#000000\">\n"
                        + "                                </div>\n"
                        + "                              </td>\n"
                        + "                            </tr>\n"
                        + "                            <tr>\n"
                        + "                              <td align=\"center\" style=\"font-size:0px;padding:20px 0 0 0;word-break:break-word\">\n"
                        + "                                <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"border-collapse:collapse;border-spacing:0px\">\n"
                        + "                                  <tbody>\n"
                        + "                                    <tr>\n"
                        + "                                      <td style=\"width:200px\">\n"
                        + "                                        <img height=\"55\" src=\"https://scontent.fhan19-1.fna.fbcdn.net/v/t1.15752-9/368566981_880883536717815_8649698896739440215_n.png?_nc_cat=108&ccb=1-7&_nc_sid=8cd0a2&_nc_ohc=gr5ScB9rv0UAX_iAC_E&_nc_ht=scontent.fhan19-1.fna&oh=03_AdQZIf0F3cDuvUwa40IG1WmbxkUW2u9VJHPOi0ccuhEdcg&oe=65656FA1\" style=\"border:0;display:block;outline:none;text-decoration:none;height:55px;width:100%;font-size:13px\" width=\"200\" class=\"CToWUd\" data-bit=\"iit\">\n"
                        + "                                      </td>\n"
                        + "                                    </tr>\n"
                        + "                                  </tbody>\n"
                        + "                                </table>\n"
                        + "                              </td>\n"
                        + "                            </tr>\n"
                        + "                          </tbody>\n"
                        + "                        </table>\n"
                        + "                      </div>\n"
                        + "                      \n"
                        + "                    </td>\n"
                        + "                  </tr>\n"
                        + "                </tbody>\n"
                        + "              </table>\n"
                        + "            </div>\n"
                        + "            \n"
                        + "          </td>\n"
                        + "        </tr>\n"
                        + "      </tbody>\n"
                        + "    </table>\n"
                        + "    \n"
                        + "    </span><div style=\"margin:0px auto;max-width:600px\">\n"
                        + "      <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"width:100%\">\n"
                        + "        <tbody>\n"
                        + "          <tr>\n"
                        + "            <td style=\"direction:ltr;font-size:0px;padding:0 0 0 0;text-align:center\">\n"
                        + "              \n"
                        + "              <div class=\"m_-2729878306671754177mj-column-px-600\" style=\"font-size:0px;text-align:left;direction:ltr;display:inline-block;vertical-align:top;width:100%\">\n"
                        + "                <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" width=\"100%\">\n"
                        + "                  <tbody>\n"
                        + "                    <tr>\n"
                        + "                      <td style=\"vertical-align:top;padding:30px 8.5% 30px 8.5%\">\n"
                        + "                        <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" width=\"100%\">\n"
                        + "                          <tbody>\n"
                        + "                            <tr>\n"
                        + "                              <td align=\"center\" style=\"font-size:0px;padding:0 0 30px 0;word-break:break-word\">\n"
                        + "                                <div style=\"font-family:Mulish;font-size:24px;font-weight:600;line-height:30px;text-align:center;color:red\">\n"
                        + "                                  Kính gửi: Quý khách " + customerName + "</div>\n"
                        + "                              </td>\n"
                        + "                            </tr>\n"
                        + "                            <tr>\n"
                        + "                              <td align=\"left\" style=\"font-size:0px;padding:0 0 0 0;word-break:break-word\">\n"
                        + "                                <div style=\"font-family:Mulish;font-size:13px;line-height:150%;text-align:left;color:#3c3c3c\">\n"
                        + "                                  Cảm ơn Quý khách đã đặt mua xe Auto tại <a href=\"http://localhost:8015/Auto99/home\" style=\"text-decoration:none\" target=\"_blank\" data-saferedirecturl=\"http://localhost:8015/Auto99/home\">Auto99.com</a>.<br> Chúng tôi đã tiếp nhận và\n"
                        + "                                  đang xử lý đơn hàng của Quý khách. Chúng tôi sẽ cập nhật tình trạng đơn hàng tới\n"
                        + "                                  Quý khách trong các email tiếp theo.</div>\n"
                        + "                              </td>\n"
                        + "                            </tr>\n"
                        + "                          </tbody>\n"
                        + "                        </table>\n"
                        + "                      </td>\n"
                        + "                    </tr>\n"
                        + "                  </tbody>\n"
                        + "                </table>\n"
                        + "              </div>\n"
                        + "              \n"
                        + "            </td>\n"
                        + "          </tr>\n"
                        + "        </tbody>\n"
                        + "      </table>\n"
                        + "    </div>\n"
                        + "    \n"
                        + "    <div style=\"margin:0px auto;max-width:600px\">\n"
                        + "      <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"width:100%\">\n"
                        + "        <tbody>\n"
                        + "          <tr>\n"
                        + "            <td style=\"direction:ltr;font-size:0px;padding:0px 8.5% 30px 8.5%;text-align:center\">\n"
                        + "              \n"
                        + "              <div class=\"m_-2729878306671754177mj-column-per-100\" style=\"font-size:0px;text-align:left;direction:ltr;display:inline-block;vertical-align:top;width:100%\">\n"
                        + "                <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" width=\"100%\">\n"
                        + "                  <tbody>\n"
                        + "                    <tr>\n"
                        + "                      <td style=\"vertical-align:top;padding:0 0 0 0\">\n"
                        + "                        <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" width=\"100%\">\n"
                        + "                          <tbody>\n"
                        + "                            <tr>\n"
                        + "                              <td align=\"left\" style=\"font-size:0px;padding:0 0 0 0;word-break:break-word\">\n"
                        + "                                <div style=\"font-family:Mulish;font-size:13px;line-height:150%;text-align:left;color:#3c3c3c\"><span class=\"im\">\n"
                        + "                                  <b>THÔNG TIN CHI TIẾT ĐƠN HÀNG</b> <br><br>\n"
                        + "                                  </span><b>Mã đơn hàng:</b>" + carorderCode + "<span class=\"im\"><br><br>\n"
                        + "                                  <b>Trạng thái đơn hàng: </b> <span style=\"color:red;font-weight:bold\">ĐANG CHỜ DUYỆT ĐƠN</span><br><br>\n"
                        + "                                  <b>Sản phẩm:</b>" + carName + "<br> \n"
                        + "                                  <b>Số lượng: </b> 01<br><br>\n"
                        + "\n"
                        + "                                  <b>Số tiền đặt cọc: </b> 20.000.000 VNĐ<br><br>\n"
                        + "                                  <b>Phương thức thanh toán: </b> Chuyển khoản ngân hàng<br><br></span>\n"
                        + "                                  Quý khách vui lòng thanh toán cho đơn hàng trước" + orderDate + " theo thông tin dưới đây:<span class=\"im\"><br><br>\n"
                        + "                                  <b>&nbsp;&nbsp;•&nbsp;Tên tài khoản:</b> TY TNHH KINH DOANH TM VA DV AUTO99<br>\n"
                        + "                                  </span><b>&nbsp;&nbsp;•&nbsp;Số tài khoản:</b> 9630630000159859<span class=\"im\"><br>\n"
                        + "                                  <b>&nbsp;&nbsp;•&nbsp;Ngân hàng:</b> Ngân hàng Đầu tư và Phát triển Việt Nam BIDV<br><br>\n"
                        + "                                  Nếu VinFast không nhận được khoản thanh toán sau thời gian trên, \n"
                        + "đơn hàng của Quý khách sẽ được chuyển sang trạng thái thanh toán không thành công.<br><br>\n"
                        + "\n"
                        + "                                  <b>Quý khách có thể tham khảo Điều Khoản Đặt Cọc <a href=\"https://click.contact.vinfastauto.com/?qs=e58fabfeb3d4e5dd4b4362a8664c0a3237f5f893bf08a793b225fc4553e2d5e1e197ebf1981a606fdd368331c97dedcb22be2a4a9e427ee3c4a379b1008a1e48\" style=\"color:red;text-decoration:none\" target=\"_blank\" data-saferedirecturl=\"https://www.google.com/url?q=https://click.contact.vinfastauto.com/?qs%3De58fabfeb3d4e5dd4b4362a8664c0a3237f5f893bf08a793b225fc4553e2d5e1e197ebf1981a606fdd368331c97dedcb22be2a4a9e427ee3c4a379b1008a1e48&amp;source=gmail&amp;ust=1698653324064000&amp;usg=AOvVaw2cZ01n7a3KrIT1CKxi4AL9\">tại đây.</a></b><br>\n"
                        + "                                </span></div>\n"
                        + "                              </td>\n"
                        + "                            </tr>\n"
                        + "                         \n"
                        + "                          </tbody>\n"
                        + "                        </table>\n"
                        + "                      </td>\n"
                        + "                    </tr>\n"
                        + "                  </tbody>\n"
                        + "                </table>\n"
                        + "              </div>\n"
                        + "              \n"
                        + "            </td>\n"
                        + "          </tr>\n"
                        + "        </tbody>\n"
                        + "      </table>\n"
                        + "    </div><span class=\"im\">\n"
                        + "    \n"
                        + "    <div style=\"margin:0px auto;max-width:600px\">\n"
                        + "      <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"width:100%\">\n"
                        + "        <tbody>\n"
                        + "          <tr>\n"
                        + "            <td style=\"direction:ltr;font-size:0px;padding:0 0 0 0;text-align:center\">\n"
                        + "              \n"
                        + "              <div class=\"m_-2729878306671754177mj-column-px-600\" style=\"font-size:0px;text-align:left;direction:ltr;display:inline-block;vertical-align:top;width:100%\">\n"
                        + "                <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" width=\"100%\">\n"
                        + "                  <tbody>\n"
                        + "                    <tr>\n"
                        + "                      <td style=\"vertical-align:top;padding:0 8.5% 30px 8.5%\">\n"
                        + "                        <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" width=\"100%\">\n"
                        + "                          <tbody>\n"
                        + "                            <tr>\n"
                        + "                              <td align=\"left\" style=\"font-size:0px;padding:0 0 30px 0;word-break:break-word\">\n"
                        + "                                <div style=\"font-family:Mulish;font-size:13px;font-weight:600;line-height:24px;text-align:left;color:#3c3c3c\">\n"
                        + "                                  Trân trọng.<br><br>\n"
                        + "                                  Auto99 - Cùng bạn bứt phá mọi giới hạn.</div>\n"
                        + "                              </td>\n"
                        + "                            </tr>\n"
                        + "\n"
                        + "                          </tbody>\n"
                        + "                        </table>\n"
                        + "                      </td>\n"
                        + "                    </tr>\n"
                        + "                  </tbody>\n"
                        + "                </table>\n"
                        + "              </div>\n"
                        + "              \n"
                        + "            </td>\n"
                        + "          </tr>\n"
                        + "        </tbody>\n"
                        + "      </table>\n"
                        + "    </div>\n"
                        + "    \n"
                        + "    <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"width:100%\">\n"
                        + "      <tbody>\n"
                        + "        <tr>\n"
                        + "          <td>\n"
                        + "            \n"
                        + "            <div style=\"margin:0px auto;max-width:600px\">\n"
                        + "              <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"width:100%\">\n"
                        + "                <tbody>\n"
                        + "                  <tr>\n"
                        + "                    <td style=\"direction:ltr;font-size:0px;padding:0 0 0 0;text-align:center\">\n"
                        + "                      \n"
                        + "                      <div class=\"m_-2729878306671754177mj-column-px-600\" style=\"font-size:0px;text-align:left;direction:ltr;display:inline-block;vertical-align:top;width:100%\">\n"
                        + "                        <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" width=\"100%\">\n"
                        + "                          <tbody>\n"
                        + "                            <tr>\n"
                        + "                              <td style=\"vertical-align:top;padding:0 0 0 0\">\n"
                        + "                                <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" width=\"100%\">\n"
                        + "                                  <tbody>\n"
                        + "                                    <tr>\n"
                        + "                                      <td align=\"center\" style=\"font-size:0px;padding:0 0 0 0;word-break:break-word\">\n"
                        + "                                        <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"border-collapse:collapse;border-spacing:0px\">\n"
                        + "                                          <tbody>\n"
                        + "                                            <tr>\n"
                        + "                                              <td style=\"width:600px\">\n"
                        + "                                                <img height=\"auto\" src=\"https://ci5.googleusercontent.com/proxy/4WDyNUNaV3GR_bVMn81yWSjZu1oayg6TuBh4xNxS-ekwFi5tZiIwLa8t8hgm6UaBrLdQICvIOcy6XVL4QTOWY0N9jxCLTEUcKP_xF4x4GQO1_Gra-RLHw19_WLbd0uUGu6qyHEsf-Xvtbewc7km_zmEEtCPvUld-dAS_jbtZ-Qw=s0-d-e1-ft#https://image.contact.vinfastauto.com/lib/fe3711737764047d751179/m/1/a25ec8f7-49c6-4c38-b780-963abadd31d8.png\" style=\"border:0;display:block;outline:none;text-decoration:none;height:auto;width:100%;font-size:13px\" width=\"600\" class=\"CToWUd a6T\" data-bit=\"iit\" tabindex=\"0\"><div class=\"a6S\" dir=\"ltr\" style=\"opacity: 0.01; left: 635.5px; top: 1257px;\"><div id=\":ox\" class=\"T-I J-J5-Ji aQv T-I-ax7 L3 a5q\" role=\"button\" tabindex=\"0\" aria-label=\"Tải xuống tệp đính kèm \" jslog=\"91252; u014N:cOuCgd,Kr2w4b,xr6bB; 4:WyIjbXNnLWY6MTc4MTA2MzIwNjY1NTUzNTc0MiIsbnVsbCxbXSxudWxsLG51bGwsbnVsbCxudWxsLG51bGwsbnVsbCxudWxsLG51bGwsbnVsbCxudWxsLG51bGwsbnVsbCxbXSxbXSxbXSxudWxsLG51bGwsbnVsbCxudWxsLFtdXQ..\" data-tooltip-class=\"a1V\" jsaction=\"JIbuQc:.CLIENT\" data-tooltip=\"Tải xuống\"><div class=\"akn\"><div class=\"aSK J-J5-Ji aYr\"></div></div></div></div>\n"
                        + "                                              </td>\n"
                        + "                                            </tr>\n"
                        + "                                          </tbody>\n"
                        + "                                        </table>\n"
                        + "                                      </td>\n"
                        + "                                    </tr>\n"
                        + "                                  </tbody>\n"
                        + "                                </table>\n"
                        + "                              </td>\n"
                        + "                            </tr>\n"
                        + "                          </tbody>\n"
                        + "                        </table>\n"
                        + "                      </div>\n"
                        + "                      \n"
                        + "                    </td>\n"
                        + "                  </tr>\n"
                        + "                </tbody>\n"
                        + "              </table>\n"
                        + "            </div>\n"
                        + "            \n"
                        + "          </td>\n"
                        + "        </tr>\n"
                        + "      </tbody>\n"
                        + "    </table>\n"
                        + "    \n"
                        + "    <div style=\"margin:0px auto;max-width:600px\">\n"
                        + "      <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"width:100%\">\n"
                        + "        <tbody>\n"
                        + "          <tr>\n"
                        + "            <td style=\"direction:ltr;font-size:0px;padding:30px 0 0 0;text-align:center\">\n"
                        + "              \n"
                        + "              <div class=\"m_-2729878306671754177mj-column-px-600\" style=\"font-size:0px;text-align:left;direction:ltr;display:inline-block;vertical-align:top;width:100%\">\n"
                        + "                <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" width=\"100%\">\n"
                        + "                  <tbody>\n"
                        + "                    <tr>\n"
                        + "                      <td style=\"vertical-align:top;padding-right:0px;padding-left:0px\">\n"
                        + "                        <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" width=\"100%\">\n"
                        + "                          <tbody>\n"
                        + "                            <tr>\n"
                        + "                              <td align=\"center\" style=\"font-size:0px;padding:2.5% 2.5% 0 2.5%;word-break:break-word\">\n"
                        + "                                <div style=\"font-family:Mulish;font-size:13px;line-height:24px;text-align:center;color:red\">\n"
                        + "                                  Quý khách cần hỗ trợ vui lòng liên hệ với Auto99 qua<br>\n"
                        + "                                  <a href=\"tel:1900232389\" style=\"font-weight:bold;text-decoration:none\" target=\"_blank\">Hotline 1900\n"
                        + "                                    23 23 89.</a>\n"
                        + "                                </div>\n"
                        + "                              </td>\n"
                        + "                            </tr>\n"
                        + "                          </tbody>\n"
                        + "                        </table>\n"
                        + "                      </td>\n"
                        + "                    </tr>\n"
                        + "                  </tbody>\n"
                        + "                </table>\n"
                        + "              </div>\n"
                        + "              \n"
                        + "            </td>\n"
                        + "          </tr>\n"
                        + "        </tbody>\n"
                        + "      </table><div class=\"yj6qo ajU\"><div id=\":ny\" class=\"ajR\" role=\"button\" tabindex=\"0\" data-tooltip=\"Hiển thị nội dung bị rút gọn\" aria-label=\"Hiển thị nội dung bị rút gọn\" aria-expanded=\"false\"><img class=\"ajT\" src=\"//ssl.gstatic.com/ui/v1/icons/mail/images/cleardot.gif\"></div></div><div class=\"adL\">\n"
                        + "    </div></div><div class=\"adL\">\n"
                        + "    \n"
                        + "  </div></span></div>"
                        + "    </body>\n"
                        + "</html>";
                sm.sendEmail(email, emailContent, "Đơn hàng đang được xử lý");
                session.removeAttribute("VoucherInfo");
                request.getRequestDispatcher("./Client/CarOrderCompletion.jsp").forward(request, response);
            } else if (!emailExist) {
                int clientID;
                clientID = cDAO.insertClientAndGetID(customerName, email, phoneNumber, noID);
                try {
                    String password = s.MD5(clientPassword);
                    caDAO.insertNewAccount(clientID, email, password);
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }

                if (voucher != null) {
                    coDAO.InsertCarOrder(carorderCode, clientID, carID, false, finalPrice, 0, finalPrice, createdOn, orderDate, "0", voucherID);
                } else {
                    coDAO.InsertCarOrder2(carorderCode, clientID, carID, false, finalPrice, 0, finalPrice, createdOn, orderDate, "0");
                }
                sm.sendEmail(email, textEmail.textSendEmailClient(clientPassword), "AUTO99: MẬT KHẨU CỦA BẠN");
                String emailContent = "<html>\n"
                        + "    <head>\n"
                        + "        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n"
                        + "    </head>\n"
                        + "    <body>\n"
                        + "        <div><span class=\"im\">\n"
                        + "    <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"width:100%\">\n"
                        + "      <tbody>\n"
                        + "        <tr>\n"
                        + "          <td>\n"
                        + "            \n"
                        + "            <div style=\"margin:0px auto;max-width:600px\">\n"
                        + "              <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"width:100%\">\n"
                        + "                <tbody>\n"
                        + "                  <tr>\n"
                        + "                    <td style=\"direction:ltr;font-size:0px;padding:20px 0;text-align:center\">\n"
                        + "                      \n"
                        + "                      <div class=\"m_-2729878306671754177mj-column-px-600\" style=\"font-size:0px;text-align:left;direction:ltr;display:inline-block;vertical-align:top;width:100%\">\n"
                        + "                        <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"vertical-align:top\" width=\"100%\">\n"
                        + "                          <tbody>\n"
                        + "                            <tr>\n"
                        + "                              <td align=\"right\" style=\"font-size:0px;padding:10px 25px;word-break:break-word\">\n"
                        + "                                <div style=\"font-family:Mulish;font-size:12px;line-height:1;text-align:right;color:#000000\">\n"
                        + "                                </div>\n"
                        + "                              </td>\n"
                        + "                            </tr>\n"
                        + "                            <tr>\n"
                        + "                              <td align=\"center\" style=\"font-size:0px;padding:20px 0 0 0;word-break:break-word\">\n"
                        + "                                <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"border-collapse:collapse;border-spacing:0px\">\n"
                        + "                                  <tbody>\n"
                        + "                                    <tr>\n"
                        + "                                      <td style=\"width:200px\">\n"
                        + "                                        <img height=\"55\" src=\"https://scontent.fhan19-1.fna.fbcdn.net/v/t1.15752-9/368566981_880883536717815_8649698896739440215_n.png?_nc_cat=108&ccb=1-7&_nc_sid=8cd0a2&_nc_ohc=gr5ScB9rv0UAX_iAC_E&_nc_ht=scontent.fhan19-1.fna&oh=03_AdQZIf0F3cDuvUwa40IG1WmbxkUW2u9VJHPOi0ccuhEdcg&oe=65656FA1\" style=\"border:0;display:block;outline:none;text-decoration:none;height:55px;width:100%;font-size:13px\" width=\"200\" class=\"CToWUd\" data-bit=\"iit\">\n"
                        + "                                      </td>\n"
                        + "                                    </tr>\n"
                        + "                                  </tbody>\n"
                        + "                                </table>\n"
                        + "                              </td>\n"
                        + "                            </tr>\n"
                        + "                          </tbody>\n"
                        + "                        </table>\n"
                        + "                      </div>\n"
                        + "                      \n"
                        + "                    </td>\n"
                        + "                  </tr>\n"
                        + "                </tbody>\n"
                        + "              </table>\n"
                        + "            </div>\n"
                        + "            \n"
                        + "          </td>\n"
                        + "        </tr>\n"
                        + "      </tbody>\n"
                        + "    </table>\n"
                        + "    \n"
                        + "    </span><div style=\"margin:0px auto;max-width:600px\">\n"
                        + "      <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"width:100%\">\n"
                        + "        <tbody>\n"
                        + "          <tr>\n"
                        + "            <td style=\"direction:ltr;font-size:0px;padding:0 0 0 0;text-align:center\">\n"
                        + "              \n"
                        + "              <div class=\"m_-2729878306671754177mj-column-px-600\" style=\"font-size:0px;text-align:left;direction:ltr;display:inline-block;vertical-align:top;width:100%\">\n"
                        + "                <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" width=\"100%\">\n"
                        + "                  <tbody>\n"
                        + "                    <tr>\n"
                        + "                      <td style=\"vertical-align:top;padding:30px 8.5% 30px 8.5%\">\n"
                        + "                        <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" width=\"100%\">\n"
                        + "                          <tbody>\n"
                        + "                            <tr>\n"
                        + "                              <td align=\"center\" style=\"font-size:0px;padding:0 0 30px 0;word-break:break-word\">\n"
                        + "                                <div style=\"font-family:Mulish;font-size:24px;font-weight:600;line-height:30px;text-align:center;color:red\">\n"
                        + "                                  Kính gửi: Quý khách " + customerName + "</div>\n"
                        + "                              </td>\n"
                        + "                            </tr>\n"
                        + "                            <tr>\n"
                        + "                              <td align=\"left\" style=\"font-size:0px;padding:0 0 0 0;word-break:break-word\">\n"
                        + "                                <div style=\"font-family:Mulish;font-size:13px;line-height:150%;text-align:left;color:#3c3c3c\">\n"
                        + "                                  Cảm ơn Quý khách đã đặt mua xe Auto tại <a href=\"http://localhost:8015/Auto99/home\" style=\"text-decoration:none\" target=\"_blank\" data-saferedirecturl=\"http://localhost:8015/Auto99/home\">Auto99.com</a>.<br> Chúng tôi đã tiếp nhận và\n"
                        + "                                  đang xử lý đơn hàng của Quý khách. Chúng tôi sẽ cập nhật tình trạng đơn hàng tới\n"
                        + "                                  Quý khách trong các email tiếp theo.</div>\n"
                        + "                              </td>\n"
                        + "                            </tr>\n"
                        + "                          </tbody>\n"
                        + "                        </table>\n"
                        + "                      </td>\n"
                        + "                    </tr>\n"
                        + "                  </tbody>\n"
                        + "                </table>\n"
                        + "              </div>\n"
                        + "              \n"
                        + "            </td>\n"
                        + "          </tr>\n"
                        + "        </tbody>\n"
                        + "      </table>\n"
                        + "    </div>\n"
                        + "    \n"
                        + "    <div style=\"margin:0px auto;max-width:600px\">\n"
                        + "      <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"width:100%\">\n"
                        + "        <tbody>\n"
                        + "          <tr>\n"
                        + "            <td style=\"direction:ltr;font-size:0px;padding:0px 8.5% 30px 8.5%;text-align:center\">\n"
                        + "              \n"
                        + "              <div class=\"m_-2729878306671754177mj-column-per-100\" style=\"font-size:0px;text-align:left;direction:ltr;display:inline-block;vertical-align:top;width:100%\">\n"
                        + "                <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" width=\"100%\">\n"
                        + "                  <tbody>\n"
                        + "                    <tr>\n"
                        + "                      <td style=\"vertical-align:top;padding:0 0 0 0\">\n"
                        + "                        <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" width=\"100%\">\n"
                        + "                          <tbody>\n"
                        + "                            <tr>\n"
                        + "                              <td align=\"left\" style=\"font-size:0px;padding:0 0 0 0;word-break:break-word\">\n"
                        + "                                <div style=\"font-family:Mulish;font-size:13px;line-height:150%;text-align:left;color:#3c3c3c\"><span class=\"im\">\n"
                        + "                                  <b>THÔNG TIN CHI TIẾT ĐƠN HÀNG</b> <br><br>\n"
                        + "                                  </span><b>Mã đơn hàng:</b>" + carorderCode + "<span class=\"im\"><br><br>\n"
                        + "                                  <b>Trạng thái đơn hàng: </b> <span style=\"color:red;font-weight:bold\">ĐANG CHỜ DUYỆT ĐƠN</span><br><br>\n"
                        + "                                  <b>Sản phẩm:</b>" + carName + "<br> \n"
                        + "                                  <b>Số lượng: </b> 01<br><br>\n"
                        + "\n"
                        + "                                  <b>Số tiền đặt cọc: </b> 20.000.000 VNĐ<br><br>\n"
                        + "                                  <b>Phương thức thanh toán: </b> Chuyển khoản ngân hàng<br><br></span>\n"
                        + "                                  Quý khách vui lòng thanh toán cho đơn hàng trước" + orderDate + " theo thông tin dưới đây:<span class=\"im\"><br><br>\n"
                        + "                                  <b>&nbsp;&nbsp;•&nbsp;Tên tài khoản:</b> TY TNHH KINH DOANH TM VA DV AUTO99<br>\n"
                        + "                                  </span><b>&nbsp;&nbsp;•&nbsp;Số tài khoản:</b> 9630630000159859<span class=\"im\"><br>\n"
                        + "                                  <b>&nbsp;&nbsp;•&nbsp;Ngân hàng:</b> Ngân hàng Đầu tư và Phát triển Việt Nam BIDV<br><br>\n"
                        + "                                  Nếu Auto99 không nhận được khoản thanh toán sau thời gian trên, \n"
                        + "đơn hàng của Quý khách sẽ được chuyển sang trạng thái thanh toán không thành công.<br><br>\n"
                        + "\n"
                        + "                                  <b>Quý khách có thể tham khảo Điều Khoản Đặt Cọc <a href=\"https://click.contact.vinfastauto.com/?qs=e58fabfeb3d4e5dd4b4362a8664c0a3237f5f893bf08a793b225fc4553e2d5e1e197ebf1981a606fdd368331c97dedcb22be2a4a9e427ee3c4a379b1008a1e48\" style=\"color:red;text-decoration:none\" target=\"_blank\" data-saferedirecturl=\"https://www.google.com/url?q=https://click.contact.vinfastauto.com/?qs%3De58fabfeb3d4e5dd4b4362a8664c0a3237f5f893bf08a793b225fc4553e2d5e1e197ebf1981a606fdd368331c97dedcb22be2a4a9e427ee3c4a379b1008a1e48&amp;source=gmail&amp;ust=1698653324064000&amp;usg=AOvVaw2cZ01n7a3KrIT1CKxi4AL9\">tại đây.</a></b><br>\n"
                        + "                                </span></div>\n"
                        + "                              </td>\n"
                        + "                            </tr>\n"
                        + "                         \n"
                        + "                          </tbody>\n"
                        + "                        </table>\n"
                        + "                      </td>\n"
                        + "                    </tr>\n"
                        + "                  </tbody>\n"
                        + "                </table>\n"
                        + "              </div>\n"
                        + "              \n"
                        + "            </td>\n"
                        + "          </tr>\n"
                        + "        </tbody>\n"
                        + "      </table>\n"
                        + "    </div><span class=\"im\">\n"
                        + "    \n"
                        + "    <div style=\"margin:0px auto;max-width:600px\">\n"
                        + "      <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"width:100%\">\n"
                        + "        <tbody>\n"
                        + "          <tr>\n"
                        + "            <td style=\"direction:ltr;font-size:0px;padding:0 0 0 0;text-align:center\">\n"
                        + "              \n"
                        + "              <div class=\"m_-2729878306671754177mj-column-px-600\" style=\"font-size:0px;text-align:left;direction:ltr;display:inline-block;vertical-align:top;width:100%\">\n"
                        + "                <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" width=\"100%\">\n"
                        + "                  <tbody>\n"
                        + "                    <tr>\n"
                        + "                      <td style=\"vertical-align:top;padding:0 8.5% 30px 8.5%\">\n"
                        + "                        <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" width=\"100%\">\n"
                        + "                          <tbody>\n"
                        + "                            <tr>\n"
                        + "                              <td align=\"left\" style=\"font-size:0px;padding:0 0 30px 0;word-break:break-word\">\n"
                        + "                                <div style=\"font-family:Mulish;font-size:13px;font-weight:600;line-height:24px;text-align:left;color:#3c3c3c\">\n"
                        + "                                  Trân trọng.<br><br>\n"
                        + "                                  Auto99 - Cùng bạn bứt phá mọi giới hạn.</div>\n"
                        + "                              </td>\n"
                        + "                            </tr>\n"
                        + "\n"
                        + "                          </tbody>\n"
                        + "                        </table>\n"
                        + "                      </td>\n"
                        + "                    </tr>\n"
                        + "                  </tbody>\n"
                        + "                </table>\n"
                        + "              </div>\n"
                        + "              \n"
                        + "            </td>\n"
                        + "          </tr>\n"
                        + "        </tbody>\n"
                        + "      </table>\n"
                        + "    </div>\n"
                        + "    \n"
                        + "    <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"width:100%\">\n"
                        + "      <tbody>\n"
                        + "        <tr>\n"
                        + "          <td>\n"
                        + "            \n"
                        + "            <div style=\"margin:0px auto;max-width:600px\">\n"
                        + "              <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"width:100%\">\n"
                        + "                <tbody>\n"
                        + "                  <tr>\n"
                        + "                    <td style=\"direction:ltr;font-size:0px;padding:0 0 0 0;text-align:center\">\n"
                        + "                      \n"
                        + "                      <div class=\"m_-2729878306671754177mj-column-px-600\" style=\"font-size:0px;text-align:left;direction:ltr;display:inline-block;vertical-align:top;width:100%\">\n"
                        + "                        <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" width=\"100%\">\n"
                        + "                          <tbody>\n"
                        + "                            <tr>\n"
                        + "                              <td style=\"vertical-align:top;padding:0 0 0 0\">\n"
                        + "                                <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" width=\"100%\">\n"
                        + "                                  <tbody>\n"
                        + "                                    <tr>\n"
                        + "                                      <td align=\"center\" style=\"font-size:0px;padding:0 0 0 0;word-break:break-word\">\n"
                        + "                                        <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"border-collapse:collapse;border-spacing:0px\">\n"
                        + "                                          <tbody>\n"
                        + "                                            <tr>\n"
                        + "                                              <td style=\"width:600px\">\n"
                        + "                                                <img height=\"auto\" src=\"https://ci5.googleusercontent.com/proxy/4WDyNUNaV3GR_bVMn81yWSjZu1oayg6TuBh4xNxS-ekwFi5tZiIwLa8t8hgm6UaBrLdQICvIOcy6XVL4QTOWY0N9jxCLTEUcKP_xF4x4GQO1_Gra-RLHw19_WLbd0uUGu6qyHEsf-Xvtbewc7km_zmEEtCPvUld-dAS_jbtZ-Qw=s0-d-e1-ft#https://image.contact.vinfastauto.com/lib/fe3711737764047d751179/m/1/a25ec8f7-49c6-4c38-b780-963abadd31d8.png\" style=\"border:0;display:block;outline:none;text-decoration:none;height:auto;width:100%;font-size:13px\" width=\"600\" class=\"CToWUd a6T\" data-bit=\"iit\" tabindex=\"0\"><div class=\"a6S\" dir=\"ltr\" style=\"opacity: 0.01; left: 635.5px; top: 1257px;\"><div id=\":ox\" class=\"T-I J-J5-Ji aQv T-I-ax7 L3 a5q\" role=\"button\" tabindex=\"0\" aria-label=\"Tải xuống tệp đính kèm \" jslog=\"91252; u014N:cOuCgd,Kr2w4b,xr6bB; 4:WyIjbXNnLWY6MTc4MTA2MzIwNjY1NTUzNTc0MiIsbnVsbCxbXSxudWxsLG51bGwsbnVsbCxudWxsLG51bGwsbnVsbCxudWxsLG51bGwsbnVsbCxudWxsLG51bGwsbnVsbCxbXSxbXSxbXSxudWxsLG51bGwsbnVsbCxudWxsLFtdXQ..\" data-tooltip-class=\"a1V\" jsaction=\"JIbuQc:.CLIENT\" data-tooltip=\"Tải xuống\"><div class=\"akn\"><div class=\"aSK J-J5-Ji aYr\"></div></div></div></div>\n"
                        + "                                              </td>\n"
                        + "                                            </tr>\n"
                        + "                                          </tbody>\n"
                        + "                                        </table>\n"
                        + "                                      </td>\n"
                        + "                                    </tr>\n"
                        + "                                  </tbody>\n"
                        + "                                </table>\n"
                        + "                              </td>\n"
                        + "                            </tr>\n"
                        + "                          </tbody>\n"
                        + "                        </table>\n"
                        + "                      </div>\n"
                        + "                      \n"
                        + "                    </td>\n"
                        + "                  </tr>\n"
                        + "                </tbody>\n"
                        + "              </table>\n"
                        + "            </div>\n"
                        + "            \n"
                        + "          </td>\n"
                        + "        </tr>\n"
                        + "      </tbody>\n"
                        + "    </table>\n"
                        + "    \n"
                        + "    <div style=\"margin:0px auto;max-width:600px\">\n"
                        + "      <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"width:100%\">\n"
                        + "        <tbody>\n"
                        + "          <tr>\n"
                        + "            <td style=\"direction:ltr;font-size:0px;padding:30px 0 0 0;text-align:center\">\n"
                        + "              \n"
                        + "              <div class=\"m_-2729878306671754177mj-column-px-600\" style=\"font-size:0px;text-align:left;direction:ltr;display:inline-block;vertical-align:top;width:100%\">\n"
                        + "                <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" width=\"100%\">\n"
                        + "                  <tbody>\n"
                        + "                    <tr>\n"
                        + "                      <td style=\"vertical-align:top;padding-right:0px;padding-left:0px\">\n"
                        + "                        <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" width=\"100%\">\n"
                        + "                          <tbody>\n"
                        + "                            <tr>\n"
                        + "                              <td align=\"center\" style=\"font-size:0px;padding:2.5% 2.5% 0 2.5%;word-break:break-word\">\n"
                        + "                                <div style=\"font-family:Mulish;font-size:13px;line-height:24px;text-align:center;color:red\">\n"
                        + "                                  Quý khách cần hỗ trợ vui lòng liên hệ với Auto99 qua<br>\n"
                        + "                                  <a href=\"tel:1900232389\" style=\"font-weight:bold;text-decoration:none\" target=\"_blank\">Hotline 1900\n"
                        + "                                    23 23 89.</a>\n"
                        + "                                </div>\n"
                        + "                              </td>\n"
                        + "                            </tr>\n"
                        + "                          </tbody>\n"
                        + "                        </table>\n"
                        + "                      </td>\n"
                        + "                    </tr>\n"
                        + "                  </tbody>\n"
                        + "                </table>\n"
                        + "              </div>\n"
                        + "              \n"
                        + "            </td>\n"
                        + "          </tr>\n"
                        + "        </tbody>\n"
                        + "      </table><div class=\"yj6qo ajU\"><div id=\":ny\" class=\"ajR\" role=\"button\" tabindex=\"0\" data-tooltip=\"Hiển thị nội dung bị rút gọn\" aria-label=\"Hiển thị nội dung bị rút gọn\" aria-expanded=\"false\"><img class=\"ajT\" src=\"//ssl.gstatic.com/ui/v1/icons/mail/images/cleardot.gif\"></div></div><div class=\"adL\">\n"
                        + "    </div></div><div class=\"adL\">\n"
                        + "    \n"
                        + "  </div></span></div>"
                        + "    </body>\n"
                        + "</html>";
                sm.sendEmail(email, emailContent, "Đơn hàng đang được xử lý");
                session.removeAttribute("VoucherInfo");
                response.sendRedirect("cardeposit?carID=" + carID);

//                request.getRequestDispatcher("cardeposit?carID=" + carID).forward(request, response);
            }

        } else {
            boolean emailExist = cDAO.checkEmailExist(email);

            LocalDateTime orderDateTime = currentDateTime.plusMinutes(15); // Thêm 15 phút
            String orderDate = orderDateTime.format(formatter);
            if (emailExist) {
                int clientID;
                clientID = cDAO.getClientIDByEmail(email);
                CarOrder co = new CarOrder(carorderCode, new Client(clientID), new Car(carID), finalPrice, LocalDateTime.parse(createdOn, formatter), LocalDateTime.parse(orderDate, formatter), "0", new Voucher(voucherID));
                session.setAttribute("OrderInfo", co);
                response.sendRedirect("payment");
            } else {
                int clientID;
                clientID = cDAO.insertClientAndGetID(customerName, email, phoneNumber, noID);
                try {
                    String password = s.MD5(clientPassword);
                    caDAO.insertNewAccount(clientID, email, password);
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
                CarOrder co = new CarOrder(carorderCode, new Client(clientID), new Car(carID), finalPrice, LocalDateTime.parse(createdOn, formatter), LocalDateTime.parse(orderDate, formatter), "0", new Voucher(voucherID));
                session.setAttribute("OrderInfo", co);
                response.sendRedirect("payment");
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
