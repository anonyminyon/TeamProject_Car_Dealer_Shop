/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.Staff;


import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Account;




/**
 *
 * @author HieuHT
 */
//@WebServlet(name="BaseAuthencationRequire", urlPatterns={"/BaseAuthencationRequire"})
//dùng abstract class để chắc chắn người dùng không thể truy cập
public abstract class BaseAuthencationRequire extends HttpServlet {
    
    //function check login
    private boolean isAuthenticated(HttpServletRequest request){
         // Get the Account object from the session
        Account acc = (Account) request.getSession().getAttribute("acc");
        return acc != null;//khác null trả về true , = null trả về false
    }
    
    
   
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        
        if (isAuthenticated(request)) {
            processGet(request,response);
        }
        else{
            // Forward the request to LoginPage.jsp
            request.getRequestDispatcher("./Staff/LoginPage.jsp").forward(request, response);
        }
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        
        if (isAuthenticated(request)) {
            processPost(request,response);
        }
        else{
            // Forward the request to LoginPage.jsp
            request.getRequestDispatcher("./Staff/LoginPage.jsp").forward(request, response);
        }
    }

    /** 
     * Returns a short description of the servlet.
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
