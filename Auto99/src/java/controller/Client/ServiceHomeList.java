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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.Blog;
import model.BlogCategory;


/**
 *
 * @author Dao Anh Duc
 */
@WebServlet(name = "ServiceHomeList", urlPatterns = {"/servicehomelist"})
public class ServiceHomeList extends HttpServlet {

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
            out.println("<title>Servlet BlogHomeList</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet BlogHomeList at " + request.getContextPath() + "</h1>");
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
        List<Blog> listbdv = bdao.getAllDV();
        List<Blog> listb1 = bdao.getAllTrueBlog();
        List<Blog> listf = bdao.getFooter();
        session.setAttribute("Footer", listf);
        Map<Integer, Integer> parentIDCountMap = new HashMap<>();
        for (Blog blog : listb) {
            int parentID = blog.getParentID();
            if (parentID != 0) {
                parentIDCountMap.put(parentID, parentIDCountMap.getOrDefault(parentID, 0) + 1);
            }
        }

        String[] parentIDs = new String[listbdv.size()];

        for (int i = 0; i < listbdv.size(); i++) {
            Blog b = listbdv.get(i);
            int parentID = b.getParentID();
            parentIDs[i] = String.valueOf(parentID);
        }
        String pageIndex = request.getParameter("index");
        if (pageIndex == null || pageIndex.isEmpty()) {
            pageIndex = "1";
        }
        int currentIndex = Integer.parseInt(pageIndex);

        ArrayList<String> listParentID = new ArrayList<>(Arrays.asList(parentIDs));
        List<Blog> allBlogByParentID = bdao.getListBlogTTaKM(currentIndex, listParentID);
        int count = 0;
        int endPage = 0;

        request.setAttribute("AllBlogs", allBlogByParentID);
        // Lưu kết quả vào session
        session.setAttribute("ParentIDCountMap", parentIDCountMap);
        request.setAttribute("ListDV", listbdv);
        session.setAttribute("ListBlog", listb);
        session.setAttribute("ListBlog1", listb1);
        request.setAttribute("currentIndex", currentIndex);
        request.setAttribute("index", pageIndex);
        request.setAttribute("count", count);
        String blogIDParam = request.getParameter("blogID");
        if (blogIDParam != null) {
            int blogID = Integer.parseInt(blogIDParam);
            request.setAttribute("blogID", blogID);
            Blog b = bdao.getBlogByBlogID(blogID);
            request.setAttribute("Blog", b);
            List<Blog> listbsp = bdao.getAllSameParentIDBlogByBlogID(blogID);
            request.setAttribute("ListBlogSP", listbsp);
            count = bdao.getTotalAllSameParentIDBlogByBlogID(blogID);
            endPage = count / 6;
            if (count % 6 != 0) {
                endPage++;
            }
            request.setAttribute("endPage", endPage);
            String uri = request.getRequestURI();
            String[] parts = uri.split("/");
            String pageName = parts[parts.length - 1];
            request.setAttribute("pageUrl", pageName);
            request.getRequestDispatcher("./Client/ServiceHomeList.jsp").forward(request, response);

        } else {
            count = bdao.getTotalBlogTTaKM(listParentID);
            endPage = count / 6;
            if (count % 6 != 0) {
                endPage++;
            }
            request.setAttribute("endPage", endPage);
            String uri = request.getRequestURI();
            String[] parts = uri.split("/");
            String pageName = parts[parts.length - 1];
            request.setAttribute("pageUrl", pageName);
            request.getRequestDispatcher("./Client/ServiceHomeList.jsp").forward(request, response);
        }

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
