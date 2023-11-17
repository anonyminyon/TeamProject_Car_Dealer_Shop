/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.Staff;

import dal.AccountDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.Account;
import model.Feature;

/**
 *
 * @author HieuHT
 */
//@WebServlet(name="BaseAuthencationRequire", urlPatterns={"/BaseAuthencationRequire"})
//dùng abstract class để chắc chắn người dùng không thể truy cập
public abstract class BaseAuthencationAndAuthorRequire extends HttpServlet {

    //function check login
    private boolean isAuthenticated(HttpServletRequest request) {
        // Get the Account object from the session
        Account acc = (Account) request.getSession().getAttribute("acc");
        return acc != null;//khác null trả về true , = null trả về false
    }

    //function check login
    private boolean isAuthorized(HttpServletRequest request) {
        // Get the Account object from the session
        Account acc = (Account) request.getSession().getAttribute("acc");
        AccountDAO dao = new AccountDAO();
        boolean isAuthorized = false;
        List<Feature> featureList = dao.getAllFeaturebyRole(acc.getRoleID().getRoleID());
        String URL = request.getServletPath();
        if (featureList != null) {
            for (Feature feature : featureList) {
                if (URL.contains(feature.getFeature())) {
                    isAuthorized = true;
                    break;
                }
            }
        }
        return isAuthorized;
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

        if (isAuthenticated(request)) {
            if (isAuthorized(request)) {
                processGet(request, response);
            } else {
                request.getRequestDispatcher("./403Page.jsp").forward(request, response);
            }
        } else {
            // Forward the request to LoginPage.jsp
            request.getRequestDispatcher("./Staff/LoginPage.jsp").forward(request, response);
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

        if (isAuthenticated(request)) {
            if (isAuthorized(request)) {
                processPost(request, response);
            } else {
                request.getRequestDispatcher("./Staff/404Page.jsp").forward(request, response);
            }
        } else {
            // Forward the request to LoginPage.jsp
            request.getRequestDispatcher("./Staff/LoginPage.jsp").forward(request, response);
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

    //vì ta gọi lại sử lý check login nên bất cứ thăng nào muốn làm authentication đều phải extends class này và override lại process Get và Post (xem lại override là hiểu ngay)
    protected abstract void processGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException;

    protected abstract void processPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException;

}
