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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.Blog;
import model.BlogCategory;
import model.CarStaffPage;
import model.EngineAndChassis;

/**
 *
 * @author ACER
 */
@WebServlet(name = "ProductDetail", urlPatterns = {"/product"})
public class ProductDetail extends HttpServlet {

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
        HttpSession session = request.getSession();
        String ID = request.getParameter("carID");
        if (ID.equals("") || ID.equals(null)) {
            ID = "0";
        }
        int carID = Integer.parseInt(ID);

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
        
        CarDAO DAO = new CarDAO();
        CarStaffPage Car = DAO.getCarByIDHome(carID);
        EngineAndChassis eac = DAO.getEngineAndChassisByID(carID);
        
        session.setAttribute("AllBlogsByPI", allBlogByParentID);
        // Lưu kết quả vào session
        session.setAttribute("ParentIDCountMap", parentIDCountMap);
        session.setAttribute("ListBlog", listb);
        session.setAttribute("ListBlog1", listb1);
        session.setAttribute("c", Car);
        session.setAttribute("eac", eac);
        request.getRequestDispatcher("./Client/CarDetail.jsp").forward(request, response);
        
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
