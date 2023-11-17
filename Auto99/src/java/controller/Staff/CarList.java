package controller.Staff;

import dal.CarDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.Account;
import model.CarBrand;
import model.CarDesign;
import model.CarStaffPage;
import model.StatusCategory;

/**
 *
 * @author ACER
 */
@WebServlet(name = "CarListController", urlPatterns = {"/carlist"})
public class CarList extends BaseAuthencationAndAuthorRequire {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

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
        String pageIndex = request.getParameter("index");
        if (pageIndex == null) {
            pageIndex = "1";
        }
        int index = Integer.parseInt(pageIndex);

        String name = request.getParameter("name");
        if (name == null) {
            name = "";
        }

        String quantityShow = request.getParameter("quantity");
        if (quantityShow == null) {
            quantityShow = "5";
        }
        int quantity = Integer.parseInt(quantityShow);

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

        String statusID = request.getParameter("status");
        if (statusID == null) {
            statusID = "0";
        }
        int status = Integer.parseInt(statusID);

        CarDAO DAO = new CarDAO();
        double MAXCarPrice = DAO.getMaxPriceCar(name, brand, design, status);
        String minValue = request.getParameter("minPrice");
        String maxValue = request.getParameter("maxPrice");
        String action = request.getParameter("action");
        double minPrice;
        double maxPrice;
        if (action != null) {
            if (minValue == null) {
                minValue = "0";
            } else {
                minValue = minValue.substring(0, minValue.length() - 2).replace(".", "");
            }
            if (maxValue == null) {
                maxValue = Double.toString(MAXCarPrice);
                maxPrice = MAXCarPrice;
            } else {
                maxValue = maxValue.substring(0, maxValue.length() - 2).replace(".", "");
                maxPrice = Double.parseDouble(maxValue);
            }
        } else {
            if (minValue == null) {
                minValue = "0";
            }
            minPrice = Double.parseDouble(minValue);

            if (maxValue == null) {
                maxValue = Double.toString(MAXCarPrice);
                maxPrice = MAXCarPrice;
            }
        }
        minPrice = Double.parseDouble(minValue);
        maxPrice = Double.parseDouble(maxValue);

        List<CarDesign> DesignList = DAO.getAllCarDesign();
        List<CarBrand> BrandList = DAO.getAllCarBrand();
        List<StatusCategory> StatusList = DAO.getAllStatus();
        List<CarStaffPage> CarList = DAO.getListCarAdmin(index, name, quantity, brand, design, status, minPrice, maxPrice);
        int count = DAO.getTotalCarAdmin(name, brand, design, status, minPrice, maxPrice);
        int endPage = count / quantity;
        if (count % quantity != 0) {
            endPage++;
        }
        request.setAttribute("endPage", endPage);
        request.setAttribute("CarList", CarList);
        request.setAttribute("index", index);
        request.setAttribute("count", count);
        request.setAttribute("quantity", quantity);
        request.setAttribute("name", name);
        request.setAttribute("minPrice", minValue);
        request.setAttribute("maxPrice", maxValue);
        request.setAttribute("MAXCarPrice", MAXCarPrice);

        request.setAttribute("design", design);
        request.setAttribute("DesignList", DesignList);

        request.setAttribute("brand", brand);
        request.setAttribute("BrandList", BrandList);

        request.setAttribute("status", status);
        request.setAttribute("StatusList", StatusList);
        request.getRequestDispatcher("./Staff/CarList.jsp").forward(request, response);
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
        Account acc = (Account) request.getSession().getAttribute("acc");
        CarDAO dao = new CarDAO();
        String CarID = request.getParameter("carID");
        if (CarID == null) {
            CarID = "0";
        }
        int carID = Integer.parseInt(CarID);
        String carName = request.getParameter("carName");
        String statusID = request.getParameter("statusID");

        String price = request.getParameter("price");
        dao.updateCar(carName, price, statusID, carID, acc.getAccID());
        String pageIndex = request.getParameter("index");
        if (pageIndex == null) {
            pageIndex = "1";
        }
        int index = Integer.parseInt(pageIndex);

        String name = request.getParameter("name");
        if (name == null) {
            name = "";
        }

        String quantityShow = request.getParameter("quantity");
        if (quantityShow == null) {
            quantityShow = "5";
        }
        int quantity = Integer.parseInt(quantityShow);

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

        statusID = request.getParameter("status");
        if (statusID == null) {
            statusID = "0";
        }
        int status = Integer.parseInt(statusID);

        CarDAO DAO = new CarDAO();
        double MAXCarPrice = DAO.getMaxPriceCar(name, brand, design, status);
        String minValue = request.getParameter("minPrice");
        if (minValue == null) {
            minValue = "0";
        }
        double minPrice = Double.parseDouble(minValue);

        double maxPrice;
        String maxValue = request.getParameter("maxPrice");
        if (maxValue == null) {
            maxPrice = MAXCarPrice;
        } else {
            maxPrice = Double.parseDouble(maxValue);
        }

        List<CarDesign> DesignList = DAO.getAllCarDesign();
        List<CarBrand> BrandList = DAO.getAllCarBrand();
        List<StatusCategory> StatusList = DAO.getAllStatus();
        List<CarStaffPage> CarList = DAO.getListCarAdmin(index, name, quantity, brand, design, status, minPrice, maxPrice);
        int count = DAO.getTotalCarAdmin(name, brand, design, status, minPrice, maxPrice);
        int endPage = count / quantity;
        if (count % quantity != 0) {
            endPage++;
        }
        request.setAttribute("endPage", endPage);
        request.setAttribute("CarList", CarList);
        request.setAttribute("index", index);
        request.setAttribute("count", count);
        request.setAttribute("quantity", quantity);
        request.setAttribute("name", name);
        request.setAttribute("minPrice", minPrice);
        request.setAttribute("maxPrice", maxPrice);
        request.setAttribute("MAXCarPrice", MAXCarPrice);

        request.setAttribute("design", design);
        request.setAttribute("DesignList", DesignList);

        request.setAttribute("brand", brand);
        request.setAttribute("BrandList", BrandList);

        request.setAttribute("status", status);
        request.setAttribute("StatusList", StatusList);
        request.getRequestDispatcher("./Staff/CarList.jsp").forward(request, response);
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
