package controller.Staff;

import LocationFile.Location;
import dal.CarDAO;
import jakarta.servlet.ServletContext;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import model.Account;
import model.CarBrand;
import model.CarDesign;
import model.CarStaffPage;
import model.EngineAndChassis;
import model.GeneralInfoCar;
import model.StatusCategory;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

/**
 *
 * @author ACER
 */
@MultipartConfig(fileSizeThreshold = 2048 * 2048 * 2,
        maxFileSize = 2048 * 2048 * 10,
        maxRequestSize = 2048 * 2048 * 50)
@WebServlet(name = "CarCRUD", urlPatterns = {"/carcrud"})
public class CarCRUD extends BaseAuthencationAndAuthorRequire {

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
    protected void processGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        CarDAO cdao = new CarDAO();
        List<CarDesign> CarDesign = cdao.getAllCarDesign();
        List<CarBrand> CarBrand = cdao.getAllCarBrand();
        List<StatusCategory> Status = cdao.getAllStatus();
        request.setAttribute("design", CarDesign);
        request.setAttribute("brand", CarBrand);
        request.setAttribute("status", Status);
        if (action.equals("EditCar")) {
            String ID = request.getParameter("carID");
            if (ID.equals("") || ID == null) {
                ID = "0";
            }
            int carID = Integer.parseInt(ID);
            CarStaffPage car = cdao.getCarByID(carID);
            GeneralInfoCar carGI = cdao.getGeneralInfoCarByID(carID);
            EngineAndChassis engine = cdao.getEngineAndChassisByID(carID);
            request.setAttribute("car", car);
            request.setAttribute("carGI", carGI);
            request.setAttribute("engine", engine);
            request.setAttribute("design", CarDesign);
            request.setAttribute("brand", CarBrand);
            request.setAttribute("status", Status);
            request.getRequestDispatcher("./Staff/EditCarPage.jsp").forward(request, response);

        } else {
            request.setAttribute("design", CarDesign);
            request.setAttribute("brand", CarBrand);
            request.setAttribute("status", Status);
            request.getRequestDispatcher("./Staff/AddCarPage.jsp").forward(request, response);
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
    protected void processPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Location lo = new Location();
        CarDAO dao = new CarDAO();
        String action = request.getParameter("action");
        Account acc = (Account) request.getSession().getAttribute("acc");

        if ("InsertCar".equals(action)) {
            String carName = request.getParameter("carName");

            if (dao.carNameExist(carName, 0)) {
                response.sendRedirect("carcrud?action=InsertCar&error=1");
                return;
            }

            String statusID = request.getParameter("statusID");
            String designID = request.getParameter("designID");
            String brandID = request.getParameter("brandID");
            String madeIn = request.getParameter("madeIn");
            String capacity = request.getParameter("capacity");
            String engineType = request.getParameter("engineType");
            String cylinderNumber = request.getParameter("cylinderNumber");
            String cylinderMaxCapacity = request.getParameter("cylinderMaxCapacity");
            String variableVale = request.getParameter("variableVale");
            String fuel = request.getParameter("fuel");
            String fuelSystem = request.getParameter("fuelSystem");
            String fuelCapacity = request.getParameter("fuelCapacity");
            String maxTorque = request.getParameter("maxTorque");
            String gear = request.getParameter("gear");
            String price = request.getParameter("price");
            String seatNumber = request.getParameter("seatNumber");
            String description = request.getParameter("description").trim();

            //get parameter main image of the product
            Part partFileImageUpload = request.getPart("ImageUpload");
            Part partFileSubImageUpload = request.getPart("SubImageUpload");
            String ImageUpload = "";
            String SubImageUpload = "";

            String allowedExtensions = ".jpg|.jpeg|.png|.gif";

            if (partFileImageUpload == null) {
                ImageUpload = "";
            } else {
                ImageUpload = partFileImageUpload.getSubmittedFileName();
            }

            if (partFileSubImageUpload == null) {
                SubImageUpload = "";
            } else {
                SubImageUpload = partFileSubImageUpload.getSubmittedFileName();
            }

            if (ImageUpload != null && !ImageUpload.isEmpty()) {
                if (SubImageUpload != null && !SubImageUpload.isEmpty()) {
                    String fileExtension = ImageUpload.substring(ImageUpload.lastIndexOf("."));
                    if (allowedExtensions.contains(fileExtension.toLowerCase())) {
                        fileExtension = SubImageUpload.substring(ImageUpload.lastIndexOf("."));
                        if (allowedExtensions.contains(fileExtension.toLowerCase())) {

                            //add new car
                            dao.addCar(carName, price, brandID, statusID, acc.getAccID());
                            int carID = dao.getNewestCarID();
                            String CarID = Integer.toString(carID).trim();
                            dao.addEngine(carID, capacity, engineType, cylinderNumber, cylinderMaxCapacity, variableVale, fuelSystem, fuelCapacity, maxTorque, gear);
                            dao.addGeneralInfoCar(carID, seatNumber, designID, madeIn, fuel, description);
                            // Generate a unique folder name based on a timestamp or some other identifier.
                            String newCarIMGFolder = CarID;

                            // Define the directory where the image will be saved.
                            StringBuilder absolutePathDirectory = Location.getRelativePath(request.getServletContext(), lo.LOADIMAGETOFILEIMGCAR);

                            // Create the directory if it doesn't exist.
                            File directory = new File(absolutePathDirectory + newCarIMGFolder);
                            if (!directory.exists()) {
                                directory.mkdirs();
                            }

                            // Build the absolute path to the image file.
                            String absolutePath = absolutePathDirectory + newCarIMGFolder + "/";

                            //load image to file img
                            DiskFileItemFactory factory = new DiskFileItemFactory();
                            ServletContext servletContext = this.getServletConfig().getServletContext();
                            File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
                            factory.setRepository(repository);
                            ServletFileUpload upload = new ServletFileUpload(factory);
                            upload.setHeaderEncoding("UTF-8");
                            readImageLoaded(absolutePath, ImageUpload, partFileImageUpload);
                            readImageLoaded(absolutePath, SubImageUpload, partFileSubImageUpload);

                            dao.addImage(carID, ImageUpload);
                            dao.addSubImage(carID, SubImageUpload);
                            request.getRequestDispatcher("/carlist").forward(request, response);

                        } else {
                            request.setAttribute("mess", "File không hợp lệ");
                            response.sendRedirect("carcrud?action=InsertCar&error=5");
                            return;
                        }
                    } else {
                        request.setAttribute("mess", "File không hợp lệ");
                        response.sendRedirect("carcrud?action=InsertCar&error=4");
                        return;
                    }
                } else {
                    request.setAttribute("messSubIMG", "Không tìm thấy file");
                    response.sendRedirect("carcrud?action=InsertCar&error=3");
                    return;
                }
            } else {
                request.setAttribute("mess", "Không tìm thấy file");
                response.sendRedirect("carcrud?action=InsertCar&error=2");
                return;
            }
        } else if ("EditCar".equals(action)) {
            String carName = request.getParameter("carName");
            String CarID = request.getParameter("carID");
            if (CarID == null || CarID == "") {
                CarID = "0";
            }
            int carID = Integer.parseInt(CarID);
            if (dao.carNameExist(carName, carID)) {
                request.setAttribute("messName", "Xe đã tồn tại!");
                response.sendRedirect("carcrud?action=EditCar&error=1&carID="+carID);
                return;
            }

            String statusID = request.getParameter("statusID");
            String designID = request.getParameter("designID");
            String brandID = request.getParameter("brandID");
            String madeIn = request.getParameter("madeIn");
            String capacity = request.getParameter("capacity");
            String engineType = request.getParameter("engineType");
            String cylinderNumber = request.getParameter("cylinderNumber");
            String cylinderMaxCapacity = request.getParameter("cylinderMaxCapacity");
            String variableVale = request.getParameter("variableVale");
            String fuel = request.getParameter("fuel");
            String fuelSystem = request.getParameter("fuelSystem");
            String fuelCapacity = request.getParameter("fuelCapacity");
            String maxTorque = request.getParameter("maxTorque");
            String gear = request.getParameter("gear");
            String price = request.getParameter("price");
            String seatNumber = request.getParameter("seatNumber");

            String description = request.getParameter("description").trim();

            dao.updateCar(carName, price, brandID, statusID, carID, acc.getAccID());
            dao.updateGeneralInfoCar(carID, seatNumber, designID, madeIn, fuel, description);
            dao.updateEngine(carID, capacity, engineType, cylinderNumber, cylinderMaxCapacity, variableVale, fuelSystem, fuelCapacity, maxTorque, gear);

            //get parameter main image of the product
            Part partFileImageUpload = request.getPart("ImageUpload");
            Part partFileSubImageUpload = request.getPart("SubImageUpload");
            String ImageUpload = "";
            String SubImageUpload = "";

            String allowedExtensions = ".jpg|.jpeg|.png|.gif";

            if (partFileImageUpload == null) {
                ImageUpload = "";
            } else {
                ImageUpload = partFileImageUpload.getSubmittedFileName();
            }

            if (partFileSubImageUpload == null) {
                SubImageUpload = "";
            } else {
                SubImageUpload = partFileSubImageUpload.getSubmittedFileName();
            }

            if (ImageUpload != null && !ImageUpload.isEmpty()) {
                String fileExtension = ImageUpload.substring(ImageUpload.lastIndexOf("."));
                if (allowedExtensions.contains(fileExtension.toLowerCase())) {
                    // Define the directory where the image will be saved.
                    StringBuilder absolutePathDirectory = Location.getRelativePath(request.getServletContext(), lo.LOADIMAGETOFILEIMGCAR);

                    // Create the directory if it doesn't exist.
                    File directory = new File(absolutePathDirectory + CarID);
                    if (!directory.exists()) {
                        directory.mkdirs();
                    }

                    // Build the absolute path to the image file.
                    String absolutePath = absolutePathDirectory + CarID + "/";

                    //load image to file img
                    DiskFileItemFactory factory = new DiskFileItemFactory();
                    ServletContext servletContext = this.getServletConfig().getServletContext();
                    File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
                    factory.setRepository(repository);
                    ServletFileUpload upload = new ServletFileUpload(factory);
                    upload.setHeaderEncoding("UTF-8");
                    readImageLoaded(absolutePath, ImageUpload, partFileImageUpload);

                    //remve and then add new image
                    String imgName = dao.getMainImageName(CarID);
                    deleteImage(absolutePath + imgName);
                    dao.removeImage(carID);
                    dao.addImage(carID, ImageUpload);

                } else {
                    request.setAttribute("mess", "File không hợp lệ");
                    response.sendRedirect("carcrud?action=EditCar&error=4&carID="+carID);
                    return;
                }
            }
            if (SubImageUpload != null && !SubImageUpload.isEmpty()) {
                String fileExtension = SubImageUpload.substring(ImageUpload.lastIndexOf("."));
                if (allowedExtensions.contains(fileExtension.toLowerCase())) {

                    // Define the directory where the image will be saved.
                    StringBuilder absolutePathDirectory = Location.getRelativePath(request.getServletContext(), lo.LOADIMAGETOFILEIMGCAR);

                    // Create the directory if it doesn't exist.
                    File directory = new File(absolutePathDirectory + CarID);
                    if (!directory.exists()) {
                        directory.mkdirs();
                    }

                    // Build the absolute path to the image file.
                    String absolutePath = absolutePathDirectory + CarID + "/";

                    //load image to file img
                    DiskFileItemFactory factory = new DiskFileItemFactory();
                    ServletContext servletContext = this.getServletConfig().getServletContext();
                    File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
                    factory.setRepository(repository);
                    ServletFileUpload upload = new ServletFileUpload(factory);
                    upload.setHeaderEncoding("UTF-8");
                    readImageLoaded(absolutePath, ImageUpload, partFileImageUpload);
                    readImageLoaded(absolutePath, SubImageUpload, partFileSubImageUpload);

                    //remve and then add new image
                    String imgName = dao.getMainImageName(CarID);
                    deleteImage(absolutePath + imgName);
                    dao.removeImage(carID);
                    dao.addImage(carID, ImageUpload);

                    //remve and then add new sub image
                    imgName = dao.getSubImageName(carID);
                    deleteImage(absolutePath + imgName);
                    dao.removeSubImage(carID);
                    dao.addSubImage(carID, SubImageUpload);
                } else {
                    request.setAttribute("mess", "File không hợp lệ");
                    response.sendRedirect("carcrud?action=EditCar&error=5&carID="+carID);
                    return;
                }
            }
            response.sendRedirect("carlist");
        }
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

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    public void deleteImage(String URL) {
        File img = new File(URL);           //file to be delete  
        if (img.delete()) //returns Boolean value  
        {
            System.out.println(img.getName() + " deleted");   //getting and printing the file name  
        } else {
            System.out.println("failed");
        }
    }

    public static void main(String[] args) {
        CarCRUD crud = new CarCRUD();
        String URL = "D:\\NotSWP\\SWP\\Auto99\\web\\img\\Xe\\17\\016.jpg";
        URL = "D:\\NotSWP\\SWP\\Auto99\\web\\img\\Xe\\17\\WIGO 5MT-hover.png";
        crud.deleteImage(URL);
    }

}
