package controller.Client;

import dal.CarDAO;
import dal.FeeDAO;
import dal.LocationDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import java.io.PrintWriter;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.text.DecimalFormat;
import java.util.List;
import model.Car;
import model.CarBrand;
import model.CarDesign;
import model.CarStaffPage;
import model.Location;
import model.PolicyFee;

import model.PolicyFee;

import model.Location;

import model.Location;

/**
 *
 * @author ACER
 */
@WebServlet(name = "CostEstimate", urlPatterns = {"/costestimate"})
public class CostEstimate extends HttpServlet {

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
        PrintWriter out = response.getWriter();

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
        CarDAO dao = new CarDAO();
        LocationDAO LDAO = new LocationDAO();
        FeeDAO FDAO = new FeeDAO();
        List<CarDesign> DesignList = dao.getAllCarDesign();
        List<CarBrand> BrandList = dao.getAllCarBrand();
        List<Location> LocationList = LDAO.getListLocation();
        List<PolicyFee> FeeList = FDAO.getListFee();
        session.setAttribute("DesignList", DesignList);
        session.setAttribute("BrandList", BrandList);
        session.setAttribute("LocationList", LocationList);
        session.setAttribute("FeeList", FeeList);

        request.getRequestDispatcher("./Client/CostEstimate.jsp").forward(request, response);
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
        CarDAO DAO = new CarDAO();
        String action = request.getParameter("action").trim();
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        if (action.equals("getCar")) {
            String brandID = request.getParameter("brand");
            if (brandID == null) {
                brandID = "0";
            }
            int brand = Integer.parseInt(brandID);

            String designID = request.getParameter("design");
            if (designID == null) {
                designID = "0";
            }
            int design = Integer.parseInt(designID);
            List<Car> CarList = DAO.getListCarCE(brand, design);

            out.println("                                <label for=\"carID\">Mẫu xe (*)</label>\n"
                    + "                                <select name=\"carID\" id=\"carID\" tabindex=\"3\">\n"
                    + "                                    <option value=\"0\">Chọn mẫu xe</option> <!-- Placeholder option -->\n");

            for (Car c : CarList) {
                out.println("                                    <option value=\"" + c.getCarID() + "\">" + c.getCarName() + "</option>\n");
            }

            out.println("                                </select>\n"
                    + "                                </div>");
        } else if (action.equals("getPrice")) {
            String CarID = request.getParameter("carID");
            if (CarID == null) {
                CarID = "0";
            }
            int carID = Integer.parseInt(CarID);
            String formattedPrice = "";
            if (carID != 0) {
                CarStaffPage car = DAO.getCarByID(carID);
                DecimalFormat decimalFormat = new DecimalFormat("#,###");
                formattedPrice = decimalFormat.format(car.getPrice()).replaceAll(",", ".") + " VNĐ";
            }
            out.println(formattedPrice);
        } else if (action.equals("Total")) {
            String CarID = request.getParameter("carID");
            if (CarID == null) {
                CarID = "0";
            }
            int carID = Integer.parseInt(CarID);

            String LocationID = request.getParameter("locationID");
            if (LocationID == null) {
                LocationID = "0";
            }
            int locationID = Integer.parseInt(LocationID);
            Long carPrice;
            Long preRegFee = 0L;
            Long regFee = 0L;
            LocationDAO LDAO = new LocationDAO();
            if (carID == 0) {
                out.println("");
            } else {
                FeeDAO FDAO = new FeeDAO();
                List<PolicyFee> FeeList = FDAO.getListFee();
                CarStaffPage car = DAO.getCarByID(carID);
                carPrice = car.getPrice();
                if (locationID != 0) {
                    Location location = LDAO.getLocationByID(locationID);
                    preRegFee = (carPrice * location.getPreRegFee()) / 100;
                    regFee = location.getRegFee();
                }
                double Total = carPrice + preRegFee + regFee;
                for(PolicyFee f : FeeList){
                     Total += f.getFee();
                }
                DecimalFormat decimalFormat = new DecimalFormat("#,###");
                String formattedPrice = decimalFormat.format(Total).replaceAll(",", ".") + " VNĐ";
                out.println(formattedPrice);
            }
        } else if (action.equals("getLocation")) {
            String CarID = request.getParameter("carID");
            if (CarID == null) {
                CarID = "0";
            }
            int carID = Integer.parseInt(CarID);

            String LocationID = request.getParameter("locationID");
            if (LocationID == null) {
                LocationID = "0";
            }
            int locationID = Integer.parseInt(LocationID);

            LocationDAO LDAO = new LocationDAO();
            String formattedpreRegFee = "";
            String formattedregFee = "";
            if (carID != 0 && locationID != 0) {
                CarStaffPage car = DAO.getCarByID(carID);
                Location location = LDAO.getLocationByID(locationID);
                Long preRegFee = (car.getPrice() * location.getPreRegFee()) / 100;
                Long regFee = location.getRegFee();
                DecimalFormat decimalFormat = new DecimalFormat("#,###");
                formattedpreRegFee = decimalFormat.format(preRegFee).replaceAll(",", ".") + " VNĐ";
                formattedregFee = decimalFormat.format(regFee).replaceAll(",", ".") + " VNĐ";
            }
            out.println("<p>\n"
                    + "                                    <label class=\"col-md-6\">Lệ phí trước bạ</label><br>\n"
                    + "                                    <input name=\"preRegFee\" class=\"text-right\" readonly id=\"preRegFee\" value=\"" + formattedpreRegFee + "\" tabindex=\"3\">\n"
                    + "                                </p>\n"
                    + "\n"
                    + "                                <p>\n"
                    + "                                    <label class=\"col-md-6\">Lệ phí đăng ký</label><br>\n"
                    + "                                    <input name=\"regFee\" class=\"text-right\" readonly id=\"regFee\" value=\"" + formattedregFee + "\" tabindex=\"3\">\n"
                    + "                                </p>");
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
