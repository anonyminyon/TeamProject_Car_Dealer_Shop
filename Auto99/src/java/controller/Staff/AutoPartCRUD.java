/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.Staff;

import LocationFile.Location;
import dal.AutoPartDAO;
import dal.CarDAO;
import jakarta.servlet.ServletContext;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import model.Account;
import model.AutoPart;
import model.Car;
import model.CarBrand;
import model.CarDesign;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

/**
 *
 * @author ASUS
 */
@MultipartConfig(fileSizeThreshold = 2048 * 2048 * 2,
        maxFileSize = 2048 * 2048 * 10,
        maxRequestSize = 2048 * 2048 * 50)
@WebServlet(name = "AutoPartCRUD", urlPatterns = {"/partcrud"})
public class AutoPartCRUD extends BaseAuthencationAndAuthorRequire {

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
    protected void processGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        AutoPartDAO dao = new AutoPartDAO();
        CarDAO cdao = new CarDAO();
        String action = request.getParameter("action").trim();
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();

        PrintWriter out = response.getWriter();
        if ("update".equals(action)) {
            List<CarDesign> DesignList = cdao.getAllCarDesign();
            List<CarBrand> BrandList = cdao.getAllCarBrand();

            String ID = request.getParameter("autoPartID");
            if (ID.equals("") || ID.equals(null)) {
                ID = "0";
            }
            int autoPartID = Integer.parseInt(ID);

            AutoPart part = dao.getPartByID(autoPartID);
            List<Car> carList = dao.getListCarbyAutoPart(autoPartID);

            request.setAttribute("DesignList", DesignList);
            request.setAttribute("BrandList", BrandList);
            request.setAttribute("p", part);
            session.setAttribute("carList", carList);
            request.setAttribute("carList", carList);
            request.getRequestDispatcher("./Staff/UpdateAutoPart.jsp").forward(request, response);
        } else if (action.equals("add")) {
            // Get the current session, create one if it doesn't exist
            List<CarDesign> DesignList = cdao.getAllCarDesign();
            List<CarBrand> BrandList = cdao.getAllCarBrand();
            request.setAttribute("DesignList", DesignList);
            request.setAttribute("BrandList", BrandList);
            List<Car> carList = new ArrayList<>();
            session.setAttribute("carList", carList);
            request.getRequestDispatcher("./Staff/AddAutoPart.jsp").forward(request, response);
        }
//        else if ("partdel".equals(action)) {
//            String id = request.getParameter("autoPartID");
//            dao.delete(id);
//            request.getSession().setAttribute("deleteSuccess", true);
//            PrintWriter out = response.getWriter();
//            out.println("<script>");
//            out.println("var deleteSuccess = 'true';"); // Simulate delete success
//            out.println("</script>");
//            response.sendRedirect("autopart");
//        }
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
        String action = request.getParameter("action").trim();
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        AutoPartDAO dao = new AutoPartDAO();
        CarDAO cdao = new CarDAO();
        if (action.equals("GetCar")) {
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
            List<Car> CarList = cdao.getListCarCE(brand, design);

            out.println("                                <label for=\"carID\">Mẫu xe (*)</label>\n"
                    + "                                <select name=\"carID\" id=\"carID\" tabindex=\"3\">\n"
                    + "                                    <option value=\"0\">Chọn mẫu xe</option> <!-- Placeholder option -->\n");

            for (Car c : CarList) {
                out.println("                                    <option value=\"" + c.getCarID() + "\">" + c.getCarName() + "</option>\n");
            }

            out.println("                                </select>\n"
                    + "                                </div>");
        } else if (action.equals("AddCar")) {
            String CarID = request.getParameter("carID");
            if (CarID == null) {
                CarID = "0";
            }
            int carID = Integer.parseInt(CarID);
            HttpSession session = request.getSession();
            List<Car> carList = (List<Car>) session.getAttribute("carList");

            if (carList == null) {
                carList = new ArrayList<>(); // Create a new list if it doesn't exist
            }

            if (!carExists(carID, carList) && carID != 0) {
                Car car = cdao.getCarbyID(carID);
                carList.add(car);
            }
            StringBuilder tableRows = createTable(carList);
            session.setAttribute("carList", carList);

            // Send the HTML content as the response
            response.setContentType("text/html");
            response.getWriter().write(tableRows.toString());
        } else if (action.equals("DeleteCar")) {
            String CarID = request.getParameter("carID");
            if (CarID == null) {
                CarID = "0";
            }
            int carID = Integer.parseInt(CarID);

            HttpSession session = request.getSession();
            List<Car> carList = (List<Car>) session.getAttribute("carList");

            if (carList != null) {
                // Remove the car with the specified ID
                carList.removeIf(car -> car.getCarID() == carID);
            }
            StringBuilder tableRows = createTable(carList);

            // Send the updated HTML content as the response
            response.setContentType("text/html");
            response.getWriter().write(tableRows.toString());
        } else if (action.equals("updatePart")) {
            Location lo = new Location();
            HttpSession session = request.getSession();
            String ID = request.getParameter("autoPartID");
            if (ID.equals("") || ID.equals(null)) {
                ID = "0";
            }
            int autoPartID = Integer.parseInt(ID);
            List<Car> carList = (List<Car>) session.getAttribute("carList");
            if (carList == null || carList.isEmpty()) {
                carList = dao.getListCarbyAutoPart(autoPartID); // Create a new list if it doesn't exist
            }
            String partName = request.getParameter("partName");

            if (dao.partNameExist(partName, autoPartID)) {
                response.sendRedirect("partcrud?action=update&error=1");
                return;
            }

            String description = request.getParameter("description");
            float price = Float.parseFloat(request.getParameter("price"));
            boolean Status = Boolean.parseBoolean(request.getParameter("Status"));
            Account acc = (Account) request.getSession().getAttribute("acc");
            AutoPart part = dao.getPartByID(autoPartID);

            //get parameter main image of the product
            Part partFileImageUpload = request.getPart("ImageUpload");
            String ImageUpload = "";

            String allowedExtensions = ".jpg|.jpeg|.png|.gif";

            if (partFileImageUpload == null) {
                ImageUpload = "";
            } else {
                ImageUpload = partFileImageUpload.getSubmittedFileName();
            }
            if (ImageUpload != null && !ImageUpload.isEmpty()) {
                String fileExtension = ImageUpload.substring(ImageUpload.lastIndexOf("."));
                if (allowedExtensions.contains(fileExtension.toLowerCase())) {

                    // Generate a unique folder name based on a timestamp or some other identifier.
                    String newPartIMGFolder = Integer.toString(part.getAutoPartID());

                    // Define the directory where the image will be saved.
                    StringBuilder absolutePathDirectory = Location.getRelativePath(request.getServletContext(), lo.LOADIMAGETOFILEIMGAUTOPART);

                    // Create the directory if it doesn't exist.
                    File directory = new File(absolutePathDirectory + newPartIMGFolder);
                    if (!directory.exists()) {
                        directory.mkdirs();
                    }

                    // Build the absolute path to the image file.
                    String absolutePath = absolutePathDirectory + newPartIMGFolder + "/";

                    //remove image
                    String imgName = part.getPartIMG();
                    deleteImage(absolutePath + imgName);

                    //load image to file img
                    DiskFileItemFactory factory = new DiskFileItemFactory();
                    ServletContext servletContext = this.getServletConfig().getServletContext();
                    File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
                    factory.setRepository(repository);
                    ServletFileUpload upload = new ServletFileUpload(factory);
                    upload.setHeaderEncoding("UTF-8");
                    readImageLoaded(absolutePath, ImageUpload, partFileImageUpload);
                } else {
                    request.setAttribute("mess", "File không hợp lệ");
                    response.sendRedirect("partcrud?action=update&error=4");
                    return;
                }
            } else {
                ImageUpload = part.getPartIMG();
            }

            dao.updatePart(autoPartID, partName.trim(), price, ImageUpload, Status, description, acc);
            dao.deleteCarAutoPart(autoPartID);
            dao.addCarAutoPart(carList, part.getAutoPartID());

            response.sendRedirect("autopart");

        } else if (action.equals("addPart")) {
            Location lo = new Location();
            HttpSession session = request.getSession();
            List<Car> carList = (List<Car>) session.getAttribute("carList");
            String partName = request.getParameter("partName");

            if (dao.partNameExist(partName, 0)) {
                response.sendRedirect("partcrud?action=add&error=1");
                return;
            }

            String description = request.getParameter("description");
            float price = Float.parseFloat(request.getParameter("price"));
            boolean Status = Boolean.parseBoolean(request.getParameter("Status"));
            Account acc = (Account) request.getSession().getAttribute("acc");

            //get parameter main image of the product
            Part partFileImageUpload = request.getPart("ImageUpload");
            String ImageUpload = "";

            String allowedExtensions = ".jpg|.jpeg|.png|.gif";

            if (partFileImageUpload == null) {
                ImageUpload = "";
            } else {
                ImageUpload = partFileImageUpload.getSubmittedFileName();
            }
            if (ImageUpload != null && !ImageUpload.isEmpty()) {
                String fileExtension = ImageUpload.substring(ImageUpload.lastIndexOf("."));
                if (allowedExtensions.contains(fileExtension.toLowerCase())) {

                    //add new car
                    dao.insertAutoPart(partName.trim(), price, ImageUpload, Status, description, acc);
                    AutoPart part = dao.getNewestPart();
                    dao.addCarAutoPart(carList, part.getAutoPartID());

                    // Generate a unique folder name based on a timestamp or some other identifier.
                    String newPartIMGFolder = Integer.toString(part.getAutoPartID());

                    // Define the directory where the image will be saved.
                    StringBuilder absolutePathDirectory = Location.getRelativePath(request.getServletContext(), lo.LOADIMAGETOFILEIMGAUTOPART);

                    // Create the directory if it doesn't exist.
                    File directory = new File(absolutePathDirectory + newPartIMGFolder);
                    if (!directory.exists()) {
                        directory.mkdirs();
                    }

                    // Build the absolute path to the image file.
                    String absolutePath = absolutePathDirectory + newPartIMGFolder + "/";

                    //load image to file img
                    DiskFileItemFactory factory = new DiskFileItemFactory();
                    ServletContext servletContext = this.getServletConfig().getServletContext();
                    File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
                    factory.setRepository(repository);
                    ServletFileUpload upload = new ServletFileUpload(factory);
                    upload.setHeaderEncoding("UTF-8");
                    readImageLoaded(absolutePath, ImageUpload, partFileImageUpload);

                    response.sendRedirect("autopart");
                } else {
                    request.setAttribute("mess", "File không hợp lệ");
                    response.sendRedirect("partcrud?action=add&error=4");
                    return;
                }

            } else {
                request.setAttribute("mess", "Không tìm thấy file");
                response.sendRedirect("partcrud?action=add&error=2");
                return;
            }
        }
    }

    private boolean carExists(int carID, List<Car> carList) {
        // Check if the car with the given id already exists in the list
        for (Car car : carList) {
            if (car.getCarID() == carID) {
                return true;
            }
        }
        return false;
    }

    private StringBuilder createTable(List<Car> carList) {
        StringBuilder tableRows = new StringBuilder();
        int No = 1;
        for (Car c : carList) {
            tableRows.append("<tr>\n"
                    + "    <td>" + No + "</td>\n"
                    + "    <td>" + c.getCarName() + "</td>\n"
                    + "    <td>\n"
                    + "        <button class=\"btn btn-primary btn-sm trash\" type=\"button\" title=\"Xóa\"\n"
                    + "                onclick=\"Delete(this, event)\" value=\"" + c.getCarID() + "\"><i class=\"fas fa-trash-alt\"></i> \n"
                    + "        </button>\n"
                    + "    </td>\n"
                    + "</tr>");
            No++;
        }
        return tableRows;
    }

    public void readImageLoaded(String absolutePath, String ImageUpload, Part partFileImageUpload) throws FileNotFoundException, IOException {
        OutputStream out = null;
        InputStream filecontent = null;
        File fileImageUpload = new File(absolutePath + ImageUpload);
        out = new FileOutputStream(fileImageUpload);
        filecontent = partFileImageUpload.getInputStream();
        int readImageUpload = 0;
        final byte[] bytesImageUpload = new byte[2048];
        while ((readImageUpload = filecontent.read(bytesImageUpload)) != -1) {
            out.write(bytesImageUpload, 0, readImageUpload);
        }
        IOUtils.closeQuietly(out);
    }

    public void deleteImage(String URL) {
        File img = new File(URL);           //file to be delete  
        if (img.delete()) //returns Boolean value  
        {
            System.out.println(img.getName() + " deleted");   //getting and printing the file name  
        } else {
            System.out.println("failed");
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
