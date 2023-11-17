/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.Client;

import dal.BlogDAO;
import dal.CarDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;

import java.util.Map;
import model.Blog;
import model.BlogCategory;
import model.CarsHomePage;
import model.Car;
import model.CarDesign;
import model.CarIMG;
import model.CarSubIMG;
import model.CarSubIMGType;


/**
 *
 * @author Dao Anh Duc
 */
@WebServlet(name = "HomePageServlet", urlPatterns = {"/home"})

public class HomePageServlet extends HttpServlet {

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
        // Lấy list xe theo design để hiển thị ở homepage
        CarDAO cdao = new CarDAO();
        List<CarDesign> listcd = cdao.getAllCarDesign();

        session.setAttribute("ListCD", listcd);
        session.setAttribute("ListCD", listcd);

        List<List<CarsHomePage>> allCarsByDesign = new ArrayList<>();

        for (CarDesign cd : listcd) {
            int designID = cd.getDesignID();
            List<CarsHomePage> carsByDesign = cdao.getCarsByDesignID(designID);
            allCarsByDesign.add(carsByDesign);
        }

        session.setAttribute("AllCarsByDesign", allCarsByDesign);
        session.setAttribute("AllCarsByDesign", allCarsByDesign);

        //Lấy ảnh tương ứng với từng xe 
        List<Car> listc = cdao.getAllCar();
        List<List<CarIMG>> allCarImages = new ArrayList<>(); // Danh sách danh sách hình ảnh cho từng xe

        for (Car c : listc) {
            int carID = c.getCarID();
            List<CarIMG> listcimg = cdao.getCarIMGByCarID(carID);
            allCarImages.add(listcimg); // Thêm danh sách hình ảnh cho từng xe vào danh sách chính
        }

        session.setAttribute("AllCarImages", allCarImages);
        session.setAttribute("AllCarImages", allCarImages);

        //Lấy ảnh phụ tương ứng với từng xe
        List<List<CarSubIMG>> allCarSubImages = new ArrayList<>(); // Danh sách danh sách hình ảnh cho từng xe

        for (Car c : listc) {
            int carID = c.getCarID();
            // Lấy danh sách CarSubIMGType
            List<CarSubIMGType> carSubIMGTypes = cdao.getAllCarSubIMGType();

            List<CarSubIMG> carSubImagesForCar = new ArrayList<>(); // Danh sách CarSubIMG cho từng xe

            for (CarSubIMGType carSubIMGType : carSubIMGTypes) {
                int carSubIMGTypeID = carSubIMGType.getCarSubIMGTypeID();

                // Lấy danh sách CarSubIMG dựa trên carID và carSubIMGTypeID
                List<CarSubIMG> carSubImages = cdao.getCarSubIMGByCarIDAndCarSubIMGTypeID(carID, carSubIMGTypeID);

                // Thêm danh sách CarSubIMG này vào danh sách CarSubIMG cho từng xe
                carSubImagesForCar.addAll(carSubImages);
            }

            // Thêm danh sách CarSubIMG cho từng xe vào danh sách chính
            allCarSubImages.add(carSubImagesForCar);
        }

        session.setAttribute("AllCarSubImages", allCarSubImages);
        session.setAttribute("AllCarSubImages", allCarSubImages);

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

        session.setAttribute("AllBlogsByPI", allBlogByParentID);
        // Lưu kết quả vào session
        session.setAttribute("ParentIDCountMap", parentIDCountMap);
        session.setAttribute("ListBlog", listb);
        session.setAttribute("ListBlog1", listb1);
        List<Blog> listf = bdao.getFooter();
        session.setAttribute("Footer", listf);

        String uri = request.getRequestURI();
        String[] parts = uri.split("/");
        String pageName = parts[parts.length - 1];
        request.setAttribute("pageUrl", pageName);

        request.getRequestDispatcher("./Client/HomePage.jsp").forward(request, response);
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
        processRequest(request, response);
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
