package controller.Staff;

import dal.FeeDAO;
import jakarta.servlet.ServletException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Account;
import model.PolicyFee;

/**
 *
 * @author ACER
 */
@WebServlet(urlPatterns = {"/policyfeelist"})
public class PolicyFeeList extends BaseAuthencationAndAuthorRequire {

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

    @Override
    protected void processGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
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

            FeeDAO dao = new FeeDAO();
            List<PolicyFee> FeeList = dao.getListFee(index, quantity, name);
            int count = dao.getTotalPolicyFeeByName(name);
            int endPage = count / quantity;
            if (count % quantity != 0) {
                endPage++;
            }

            request.setAttribute("endPage", endPage);
            request.setAttribute("FeeList", FeeList);
            request.setAttribute("index", index);
            request.setAttribute("count", count);
            request.setAttribute("quantity", quantity);
            request.setAttribute("name", name);
            request.getRequestDispatcher("./Staff/FeeList.jsp").forward(request, response);
        }
    }

    @Override
    protected void processPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        FeeDAO dao = new FeeDAO();
        String feeName = request.getParameter("feeName");
        String fee = request.getParameter("fee");
        Account acc = (Account) request.getSession().getAttribute("acc");

        String action = request.getParameter("action");

        if ("AddFee".equals(action)) {
            dao.addPolicyFee(feeName, fee, acc.getAccID());

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

            request.setAttribute("index", index);
            request.setAttribute("quantity", quantity);
            request.setAttribute("name", name);

            doGet(request, response);

        } else if ("UpdateFee".equals(action)) {
            String FeeID = request.getParameter("feeID");
            if (FeeID == null) {
                FeeID = "1";
            }
            int feeID = Integer.parseInt(FeeID);

            dao.updatePolicyFee(feeName, fee, feeID, acc.getAccID());

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

            request.setAttribute("index", index);
            request.setAttribute("quantity", quantity);
            request.setAttribute("name", name);

            doGet(request, response);

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
