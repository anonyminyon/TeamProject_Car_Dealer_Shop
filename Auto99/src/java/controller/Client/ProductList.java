/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.Client;

import dal.BlogDAO;
import dal.CarDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.Blog;
import model.BlogCategory;
import model.CarBrand;
import model.CarDesign;
import model.CarStaffPage;
import model.CarsHomePage;

/**
 *
 * @author ACER
 */
@WebServlet(name = "ProductList", urlPatterns = {"/productlist"})
public class ProductList extends HttpServlet {

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
            quantityShow = "4";
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
        List<CarStaffPage> CarList = DAO.getListCarHome(index, name, quantity, brand, design, status);
        List<CarDesign> DesignList = DAO.getAllCarDesign();
        List<CarBrand> BrandList = DAO.getAllCarBrand();
        List<String> FuelList = DAO.getAllFuel();
        List<String> GearList = DAO.getAllGear();
        List<Integer> SeatList = DAO.getAllNumberOfSeat();

        session.setAttribute("AllBlogsByPI", allBlogByParentID);
        // Lưu kết quả vào session
        session.setAttribute("ParentIDCountMap", parentIDCountMap);
        session.setAttribute("ListBlog", listb);
        session.setAttribute("ListBlog1", listb1);
        session.setAttribute("CarList", CarList);
        session.setAttribute("DesignList", DesignList);
        session.setAttribute("BrandList", BrandList);
        session.setAttribute("FuelList", FuelList);
        session.setAttribute("GearList", GearList);
        session.setAttribute("SeatList", SeatList);
        request.getRequestDispatcher("./Client/CarList.jsp").forward(request, response);
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
        String amountExist = request.getParameter("amount");
        if (amountExist == null) {
            amountExist = "0";
        }
        int amount = Integer.parseInt(amountExist);

        String name = request.getParameter("name");
        if (name == null) {
            name = "";
        }

        int quantity = 4;

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

        String fuel = request.getParameter("fuel");
        if (fuel == null) {
            fuel = "";
        }

        String gear = request.getParameter("gear");
        if (gear == null) {
            gear = "";
        }

        String seatNum = request.getParameter("seat");
        if (seatNum == null) {
            seatNum = "0";
        }
        int seat = Integer.parseInt(seatNum);

        CarDAO DAO = new CarDAO();

        double MAXCarPrice = DAO.getMaxPriceCar(name, brand, design, status);
        String minValue = request.getParameter("minPrice");
        if (minValue == null) {
            minValue = "0";
        }
        double minPrice = Double.parseDouble(minValue);

        double maxPrice;
        String maxValue = request.getParameter("maxPrice");
        if (maxValue == null || maxValue.equals("")) {
            maxPrice = MAXCarPrice;
        } else {
            maxPrice = Double.parseDouble(maxValue);
        }

        List<CarStaffPage> CarList = DAO.getListCarHomeAjax(amount, name, quantity, brand, design, status,
                fuel, minPrice, maxPrice, gear, seat);

        PrintWriter out = response.getWriter();
        response.setContentType("text/html;charset=UTF-8");
        for (CarStaffPage c : CarList) {
            DecimalFormat decimalFormat = new DecimalFormat("#,###");
            String formattedPrice = decimalFormat.format(c.getPrice());

            out.println("<li>\n"
                    + "                                <div class=\"box-image\">\n"
                    + "                                    <a href=\"product?carID="+c.getCarID()+"\">\n"
                    + "                                        <img class=\"image-car image-index\" alt=\"" + c.getCarName() + "\" src=\"img/Xe/" + c.getCarID() + "/" + c.getCarIMG().getCarIMG() + "\" alt=\"" + c.getCarName() + "\"\">\n"
                    + "                                        <img class=\"image-car image-hover\" alt=\"" + c.getCarName() + "\" src=\"img/Xe/" + c.getCarID() + "/" + c.getCarSubIMG().getCarSubIMG() + "\">\n"
                    + "                                    </a>\n"
                    + "                                </div>\n"
                    + "                                <div class=\"info\">\n"
                    + "                                   <a href=\"product?carID="+c.getCarID()+"\">\n"
                    + "                                            <h2>\n"
                    + "                                                "+c.getCarName()+"\n" 
                    + "                                            </h2>"
                    + "                                    <div class=\"pri\">Giá từ: <div>" + formattedPrice + "<span>VNĐ</span>\n"
                    + "                                            </div>\n"
                    + "                                        </div>\n"
                    + "                                        <div class=\"tech\">\n"
                    + "                                        <span>" + c.getNumberOfSeat() + "</span>\n"
                    + "                                        <span>" + c.getDesign().getDesign() + "</span>\n"
                    + "                                        <span>" + c.getFuel() + "</span>\n"
                    + "                                        <span>" + c.getMadeIn() + "</span>\n"
                    + "                                        <span>" + c.getGear() + "</span>\n"
                    + "                                        <span>" + c.getEngineType() + "</span>\n"
                    + "                                        <span>Dung tích: " + c.getCylinderCapacity() + " cc</span>\n"
                    + "                                    </div>\n"
                    + "                                                </a>"
                    + "                                    <div class=\"tool\">\n"
                    + "                                        <a class=\"a1\" href=\"costestimate\">Dự toán</a>\n"
                    + "                                        <a class=\"a2\" href=\"cardeposit?carID=" + c.getCarID() + "\">Đặt cọc xe</a>\n"
                    + "                                    </div>\n"
                    + "                                </div>\n"
                    + "                            </li>"
        
    

    );
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
