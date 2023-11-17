/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import com.sun.org.apache.xerces.internal.impl.dv.xs.DateTimeDV;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import model.CarBrand;
import model.Client;
import model.ClientService;
import model.HistoryServiceInvoice;
import model.Product;
import model.Service;

/**
 *
 * @author HieuHT
 */
public class HistoryServiceInvoiceDAO extends DBContext {
    //singleton
    private static HistoryServiceInvoiceDAO instance = null;

    // Private constructor to prevent external instantiation
    private HistoryServiceInvoiceDAO() {
    }

    // Public method to get the singleton instance
    public static HistoryServiceInvoiceDAO getInstance() {
        if (instance == null) {
            instance = new HistoryServiceInvoiceDAO();
        }
        return instance;
    }
//==========================================================================================DÙNG CHO PHÍA CLIENT============================================================================================
    //---------------------------------------------------------hiếu---------------------------------------------------------
    //hai hàm này giúp load toàn bộ các service khách hàng đã đặt và hoàn thành/ show ra màn hình client side để khách xem lại và tương tác

    public ArrayList<HistoryServiceInvoice> getAllServiceInvoiceByClientID(int clientID) {
        ArrayList<HistoryServiceInvoice> historyServiceInvoices = new ArrayList<>();
        String sql = "SELECT HSI.* "
                + "FROM HistoryServiceInvoice HSI "
                + "INNER JOIN ClientService CS ON HSI.clientServiceID = CS.clientServiceID "
                + "WHERE CS.clientID = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, clientID);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                HistoryServiceInvoice invoice = new HistoryServiceInvoice();
                invoice.setServiceInvoiceID(resultSet.getInt("serviceInvoiceID"));
                // You'll need to retrieve the associated ClientService object here

                // You can write a separate method to get ClientService by clientServiceID
                invoice.setClientServiceID(getClientServiceById(resultSet.getInt("clientServiceID")));
                invoice.setDateComplete(resultSet.getTimestamp("dateComplete"));
                invoice.setFeedback(resultSet.getString("feedback"));
                historyServiceInvoices.add(invoice);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return historyServiceInvoices;
    }

    private ClientService getClientServiceById(int clientServiceID) {
        ClientService clientService = null;
        EmployeeProfileDAO ed = new EmployeeProfileDAO();
        try {
            String query = "SELECT s.clientServiceID, c.clientID, c.clientName, c.email, c.phoneNumber, c.dob, c.gender, c.noID, "
                    + "se.serviceID, se.serviceType, se.serviceContent, se.serviceStatus, s.numberPlate, s.dateService, s.status, s.employeeID, s.crewChiefID, cb.brandID, cb.brandName "
                    + "FROM ClientService s "
                    + "INNER JOIN Client c ON s.clientID = c.clientID "
                    + "INNER JOIN Service se ON s.serviceID = se.serviceID "
                    + "INNER JOIN CarBrand cb ON s.brandID = cb.brandID " // Add the missing JOIN here
                    + "WHERE s.clientServiceID = ?";

            PreparedStatement stm = connection.prepareStatement(query);
            stm.setInt(1, clientServiceID);

            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                java.sql.Date dob = rs.getDate("dob"); // Replace "dob" with "dateOfBirth"
                String dobString = null;

                if (dob != null) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // Define the desired date format
                    dobString = sdf.format(dob);
                }

                Client client = new Client(
                        rs.getInt("clientID"),
                        rs.getString("clientName"),
                        rs.getString("email"),
                        rs.getString("phoneNumber"),
                        dobString, // Use the formatted date string
                        rs.getBoolean("gender"),
                        rs.getString("noID")
                );

                Service service = new Service(
                        rs.getInt("serviceID"),
                        rs.getString("serviceType"),
                        rs.getString("serviceContent"),
                        rs.getBoolean("serviceStatus")
                );

                CarBrand carBrand = new CarBrand(
                        rs.getInt("brandID"),
                        rs.getString("brandName")
                );

                clientService = new ClientService(
                        rs.getInt("clientServiceID"),
                        client,
                        service,
                        rs.getString("numberPlate"),
                        rs.getDate("dateService"),
                        carBrand,
                        rs.getString("status"),
                        ed.getEmployeeProfileByAccID(rs.getInt("employeeID")),
                        ed.getEmployeeProfileByAccID(rs.getInt("crewChiefID"))
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return clientService;
    }

    //    public static void main(String[] args) {
//
//        int clientID = 15; // Replace with the client ID you want to retrieve service invoices for
//
//        // Call the method to get service invoices for a specific client
//        ArrayList<HistoryServiceInvoice> serviceInvoices = HistoryServiceInvoiceDAO.getInstance().getAllServiceInvoiceByClientID(clientID);
//
//        // Check if service invoices were retrieved successfully
//        if (serviceInvoices != null) {
//            // Print or process the service invoices
//            for (HistoryServiceInvoice invoice : serviceInvoices) {
//                System.out.println("Service Invoice ID: " + invoice.getServiceInvoiceID());
//                System.out.println("Date Complete: " + invoice.getDateComplete());
//                System.out.println("Feedback: " + invoice.getFeedback());
//                System.out.println("Client Service ID: " + invoice.getClientServiceID().getClientServiceID());
//                System.out.println("Client Name: " + invoice.getClientServiceID().getClientID().getClientName());
//                // Print other relevant information
//
//                System.out.println("---------------------------------------------------");
//            }
//        } else {
//            System.out.println("No service invoices found for the given client ID.");
//        }
//    }
    //---------------------------------------------------------hiếu---------------------------------------------------------
    public HistoryServiceInvoice getServiceInvoiceByServiceInvoiceID(int serviceInvoiceID) {
        HistoryServiceInvoice invoice = null;
        String sql = "SELECT HSI.* "
                + "FROM HistoryServiceInvoice HSI "
                + "WHERE HSI.serviceInvoiceID = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, serviceInvoiceID);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                invoice = new HistoryServiceInvoice();
                invoice.setServiceInvoiceID(resultSet.getInt("serviceInvoiceID"));
                int clientServiceID = resultSet.getInt("clientServiceID");

                // Retrieve the associated ClientService object using the getClientServiceById method
                ClientService clientService = getClientServiceById(clientServiceID);
                invoice.setClientServiceID(clientService);
                invoice.setDateComplete(resultSet.getTimestamp("dateComplete"));
                invoice.setFeedback(resultSet.getString("feedback"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return invoice;
    }
    
    public HistoryServiceInvoice getServiceInvoices(int clientServiceIDtemp) {
        
        HistoryServiceInvoice invoice = null;
        String sql = "SELECT HSI.* "
                + "FROM HistoryServiceInvoice HSI "
                + "WHERE HSI.clientServiceID = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);      
            statement.setInt(1, clientServiceIDtemp);
            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next()) {
                invoice = new HistoryServiceInvoice();
                invoice.setServiceInvoiceID(resultSet.getInt("serviceInvoiceID"));
                int clientServiceID = resultSet.getInt("clientServiceID");

                // Retrieve the associated ClientService object using the getClientServiceById method
                ClientService clientService = getClientServiceById(clientServiceID);
                invoice.setClientServiceID(clientService);
                invoice.setDateComplete(resultSet.getTimestamp("dateComplete"));
                invoice.setFeedback(resultSet.getString("feedback"));
                
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return invoice;
    }

//    public static void main(String[] args) {
// 
//            // Replace 123 with the desired serviceInvoiceID to fetch
//            int serviceInvoiceID = 1;
//
//            // Create an instance of your DAO class
//            HistoryServiceInvoiceDAO historyServiceInvoiceDAO = HistoryServiceInvoiceDAO.getInstance();
//
//            // Call the getServiceInvoiceByServiceInvoiceID method to retrieve the invoice
//            HistoryServiceInvoice invoice = historyServiceInvoiceDAO.getServiceInvoiceByServiceInvoiceID(serviceInvoiceID);
//
//            // Check if the invoice was found
//            if (invoice != null) {
//                System.out.println("Service Invoice ID: " + invoice.getServiceInvoiceID());
//                System.out.println("Date Complete: " + invoice.getDateComplete());
//                System.out.println("Feedback: " + invoice.getFeedback());
//
//                // Access the associated ClientService object
//                ClientService clientService = invoice.getClientServiceID();
//                if (clientService != null) {
//                    System.out.println("Client Service ID: " + clientService.getStatus());
//                    // Print other attributes of the ClientService object as needed
//                } else {
//                    System.out.println("Client Service not found.");
//                }
//            } else {
//                System.out.println("Service Invoice not found.");
//            }
//
//            
//        } 
    //---------------------------------------------------------hiếu---------------------------------------------------------
    public void updateFeedback(int serviceInvoiceID, String feedback) {
        String sql = "UPDATE HistoryServiceInvoice SET feedback = ? WHERE serviceInvoiceID = ?";

        try {
            // Create a prepared statement
            PreparedStatement statement = connection.prepareStatement(sql);

            // Set the parameters for the update
            statement.setString(1, feedback); // Set the new feedback
            statement.setInt(2, serviceInvoiceID); // Specify the record to update based on serviceInvoiceID

            // Execute the update
            statement.executeUpdate();
        } catch (Exception e) {
        }
    }
//===================================================================================================DÙNG CHO PHÍA ADMIN===================================================================================
    //sau khi add dữ liệu vào xong thì tìm lại cái id của bảng vừa add và trả về

    public int updateClientServiceIDAndDateComplete(int clientServiceID, Timestamp dateComplete) {

        try {
            String sql = "INSERT INTO [dbo].[HistoryServiceInvoice] "
                    + "([clientServiceID], [dateComplete], [feedback]) "
                    + "VALUES (?, ?, ?)";

            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, clientServiceID);
            statement.setTimestamp(2, dateComplete);
            statement.setString(3, "YourFeedbackValueHere");
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int serviceInvoiceID = generatedKeys.getInt(1);
                    generatedKeys.close();
                    statement.close();
                    
                    return serviceInvoiceID;
                }
            }

            statement.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    //hàm này giúp ta add các dòng chi tiết của hóa đơn
    public void insertDetailProductInvoice(int serviceInvoiceID, List<Product> productList) {
        try {
            if (connection == null) {
                System.out.println("Connection is null. Check your database connection.");
                return;
            }

            String sql = "INSERT INTO [dbo].[InFoInvoiceDetail] "
                    + "([serviceInvoiceID], [productName], [unitPrice], [quantity]) "
                    + "VALUES (?, ?, ?, ?)";

            PreparedStatement statement = connection.prepareStatement(sql);

            for (Product product : productList) {
                statement.setInt(1, serviceInvoiceID);
                statement.setString(2, product.getProductName());
                statement.setString(3, String.valueOf(product.getUnitPrice()));
                statement.setString(4, String.valueOf(product.getQuantity()));
                statement.addBatch();
            }

            // Execute batch insert for all products
            int[] rowsAffected = statement.executeBatch();

            // Process the results if needed
            for (int rows : rowsAffected) {
                System.out.println("Rows affected: " + rows);
            }

            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("SQL Exception: " + e.getMessage());
        }
    }

}
