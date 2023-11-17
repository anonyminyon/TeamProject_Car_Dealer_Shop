/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import model.CarBrand;
import model.Client;
import model.ClientService;
import model.Service;

/**
 *
 * @author Hieu
 */
public class ClientServiceDAO extends DBContext {

    //singleton
    private static ClientServiceDAO instance = null;

    // Private constructor to prevent external instantiation
    private ClientServiceDAO() {
    }

    // Public method to get the singleton instance
    public static ClientServiceDAO getInstance() {
        if (instance == null) {
            instance = new ClientServiceDAO();
        }
        return instance;
    }
    
    EmployeeProfileDAO ed = new EmployeeProfileDAO();
    /**
     * *************************************************************Client******************************************************************
     */
    //-----------------------------------------code-Hiếu-----------------------------------------------------------------
    //dùng cho serviceform
    public void addClientService(String clientID, String serviceID, String numberPlate, String dateService, String brandID, String status) {
        // SQL query to insert a new ClientService record
        String sql = "INSERT INTO ClientService (clientID, serviceID, numberPlate, dateService, brandID, status) VALUES (?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, Integer.parseInt(clientID));
            preparedStatement.setInt(2, Integer.parseInt(serviceID));
            preparedStatement.setString(3, numberPlate);
            /// Directly convert the date string to a Date
            Date sqlDate = Date.valueOf(dateService);
            preparedStatement.setDate(4, sqlDate);
            preparedStatement.setInt(5, Integer.parseInt(brandID));
            preparedStatement.setString(6, status);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            // Handle any database-related exceptions here
            e.printStackTrace();
        }
    }

    //-----------------------------------------code-Hiếu-----------------------------------------------------------------
    //using to balidate
    public boolean doesClientServiceExist(String serviceID, String numberPlate, String dateService) throws ParseException {
        // Construct the SQL query to check for the existence of a row
        String sql = "SELECT COUNT(*) FROM ClientService WHERE"
                + "serviceID = ? AND numberPlate = ? AND dateService = ?";

        try ( PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, Integer.parseInt(serviceID));
            preparedStatement.setString(2, numberPlate);
            // Convert the String dateService to a java.sql.Date
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date parsedDate = dateFormat.parse(dateService);
            java.sql.Date sqlDate = new java.sql.Date(parsedDate.getTime());
            preparedStatement.setDate(3, sqlDate);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0; // Return true if a row with the given parameters exists
            }
        } catch (SQLException e) {
            // Handle any database-related exceptions here
            e.printStackTrace();
        }

        return false; // Return false if any error occurs or no matching row is found
    }

    /**
     * *******************************************************************************************************************************
     */
    /**
     * *************************************************************Staff******************************************************************
     */
    //-----------------------------------------code-Hiếu-----------------------------------------------------------------
    public ArrayList<ClientService> getClientServicesByPage(int start, String search, int pageSize, String serviceStatusFilter) {
        ArrayList<ClientService> arrClientService = new ArrayList<>();
        
        try {
            String query = "SELECT s.clientServiceID, c.clientID, c.clientName, c.email, c.phoneNumber, c.dob, c.gender, c.noID, "
                    + "se.serviceID, se.serviceType, se.serviceContent, se.serviceStatus, s.numberPlate, s.dateService, s.status, "
                    + "s.employeeID, s.crewChiefID, cb.brandID, cb.brandName "
                    + "FROM ClientService s "
                    + "INNER JOIN Client c ON s.clientID = c.clientID "
                    + "INNER JOIN Service se ON s.serviceID = se.serviceID "
                    + "INNER JOIN CarBrand cb ON s.brandID = cb.brandID "
                    + "WHERE se.serviceType LIKE ? ";

            // Check if serviceStatusFilter is provided
            if (!serviceStatusFilter.equals("")) {
                query += "AND s.status LIKE  ? ";
            }

            query += "ORDER BY s.clientServiceID OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

            PreparedStatement stm = connection.prepareStatement(query);
            int position = 1;
            stm.setString(position++, "%" + search + "%");

            // Check if serviceStatusFilter is provided
            if (!serviceStatusFilter.equals("")) {
                stm.setString(position++, "%" + serviceStatusFilter + "%");
            }

            stm.setInt(position++, (start - 1) * pageSize);
            stm.setInt(position, pageSize);

            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                java.sql.Date dob = rs.getDate("dob");
                String dobString = null;

                if (dob != null) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    dobString = sdf.format(dob);
                }

                Client client = new Client(
                        rs.getInt("clientID"),
                        rs.getString("clientName"),
                        rs.getString("email"),
                        rs.getString("phoneNumber"),
                        dobString,
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

                ClientService clientService = new ClientService(
                        rs.getInt("clientServiceID"),
                        client,
                        service,
                        rs.getString("numberPlate"),
                        rs.getDate("dateService"),
                        carBrand, // Set the CarBrand property
                        rs.getString("status"),
                        ed.getEmployeeProfileByAccID(rs.getInt("employeeID")),
                        ed.getEmployeeProfileByAccID(rs.getInt("crewChiefID"))
                );

                arrClientService.add(clientService);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return arrClientService;
    }

    //-----------------------------------------code-Hiếu-----------------------------------------------------------------
    public int getTotalClientServicesBySearch(String search, String serviceStatusFilter) {
        String query = "SELECT COUNT(s.clientServiceID) "
                + "FROM ClientService s "
                + "INNER JOIN Client c ON s.clientID = c.clientID "
                + "INNER JOIN Service se ON s.serviceID = se.serviceID "
                + "INNER JOIN CarBrand cb ON s.brandID = cb.brandID " // Add the missing JOIN here
                + "WHERE se.serviceType LIKE ?";

        // Check if serviceStatusFilter is provided
        if (!serviceStatusFilter.equals("")) {
            query += " AND s.status LIKE % ? %";
        }

        try {
            PreparedStatement stm = connection.prepareStatement(query);
            int position = 1;
            stm.setString(position++, "%" + search + "%");

            // Check if serviceStatusFilter is provided
            if (!serviceStatusFilter.equals("")) {
                stm.setString(position++, serviceStatusFilter);
            }

            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    //-----------------------------------------code-Hiếu-----------------------------------------------------------------
    public ClientService getClientServiceByClientServiceID(int clientServiceID) {
        ClientService clientService = null;

        try {
            String query = "SELECT s.clientServiceID, c.clientID, c.clientName, c.email, c.phoneNumber, c.dob, c.gender, c.noID, "
                    + "se.serviceID, se.serviceType, se.serviceContent, se.serviceStatus, s.numberPlate, s.dateService, s.status, "
                    + "s.employeeID, s.crewChiefID, cb.brandID, cb.brandName "
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
        } catch (Exception e) {
            e.printStackTrace();
        }

        return clientService;
    }

    //-----------------------------------------code-Hiếu-----------------------------------------------------------------
    // hàm này dùng cho việc khi duyệt đơn cần thay đổi trạng thái đơn và lưu lại nhân viên duyệt đơn này
    public void changeStatusAClientServiceAndStoreHistoryEmployeeID(int clientServiceID, String status, int employeeID, int crewChiefID) {
        // Define your SQL update statement
        String updateSQL = "UPDATE [dbo].[ClientService] "
                + "SET [status] = ?, "
                + "[employeeID] = ?, "
                + "[crewChiefID] = ? "
                + "WHERE [clientServiceID] = ?"; // Specify your search condition

        try {
            // Create a PreparedStatement for the update
            PreparedStatement preparedStatement = connection.prepareStatement(updateSQL);

            // Set the parameters in the PreparedStatement
            preparedStatement.setString(1, status); // Set the new status value
            preparedStatement.setInt(2, employeeID); // Set the new employeeID value
            preparedStatement.setInt(3, crewChiefID); // Set the new crewChiefID value
            preparedStatement.setInt(4, clientServiceID); // Set the search condition

            // Execute the update
            int rowsUpdated = preparedStatement.executeUpdate();

            // Check if the update was successful
            if (rowsUpdated > 0) {
                System.out.println("ClientService updated successfully.");
            } else {
                System.out.println("ClientService update failed. No matching clientServiceID found.");
            }

            // Close the PreparedStatement
            preparedStatement.close();

        } catch (SQLException e) {
            // Handle any SQL exceptions
            e.printStackTrace();
        }
    }

    /**
     * ******************************************************************************************************************************
     */
    //-----------------------------------------code-Hiếu-----------------------------------------------------------------
//    public static void main(String[] args) {
//        // Initialize your database connection or connection pool here
//        // Assuming you have a ClientServiceDAO class for database operations
//
//        // Call the getClientServicesByPage method
//        List<ClientService> clientServices = ClientServiceDAO.getInstance().getClientServicesByPage(1, "", 10, "");
//
//        // Print the results to the console
//        for (ClientService service : clientServices) {
//            System.out.println("Client Service ID: " + service.getClientServiceID());
//            System.out.println("Client ID: " + service.getClientID().getClientID());
//            System.out.println("Client Name: " + service.getClientID().getClientName());
//            System.out.println("Client Email: " + service.getClientID().getEmail());
//            System.out.println("Client Phone Number: " + service.getClientID().getPhoneNumber());
//            System.out.println("Client Date of Birth: " + service.getClientID().getDateOfBrith()); // Corrected the field name
//            System.out.println("Client Gender: " + service.getClientID().isGender()); // Corrected the field name
//            System.out.println("Service ID: " + service.getServiceID().getServiceID());
//            System.out.println("Service Type: " + service.getServiceID().getServiceType());
//            System.out.println("Service Content: " + service.getServiceID().getServiceContent());
//            System.out.println("Service Status: " + service.getServiceID().isServiceStatus()); // Assuming it's a boolean field
//            System.out.println("Number Plate: " + service.getNumberPlate());
//            System.out.println("Date Service: " + service.getDateService());
//            System.out.println("CarBrand ID: " + service.getBrandID().getBrandID());
//            System.out.println("BrandName: " + service.getBrandID().getBrandName());
//            System.out.println("Status: " + service.getStatus());
//            System.out.println();
//        }
//
//        // Get the total number of client services by search
//        int totalClientServices = ClientServiceDAO.getInstance().getTotalClientServicesBySearch("Dịch vụ kiểm tra xe miễn phí", "");
//        System.out.println("Total Client Services: " + totalClientServices);
//    }
//    public static void main(String[] args) {
//     
//        try {
//            ClientServiceDAO clientServiceDAO = ClientServiceDAO.getInstance();
//
//           
//            // Test the addClientService method
//            clientServiceDAO.addClientService("123", "456", "ABC123", "2023-10-23", "789", "Active");
//
//            // Test the doesClientServiceExist method
//            boolean exists = clientServiceDAO.doesClientServiceExist(123, "456", "ABC123", "2023-10-23");
//            System.out.println("ClientService exists: " + exists);
//
//            // Test the getClientServicesByPage method
//            ArrayList<ClientService> clientServices = clientServiceDAO.getClientServicesByPage(1, "search", 10, "Active");
//            for (ClientService service : clientServices) {
//                System.out.println(service);
//            }
//
//            // Test the getTotalClientServicesBySearch method
//            int totalServices = clientServiceDAO.getTotalClientServicesBySearch("search", "Active");
//            System.out.println("Total services: " + totalServices);
//
//            // Test the getClientServiceByClientServiceID method
//            ClientService clientService = clientServiceDAO.getClientServiceByClientServiceID(1);
//            System.out.println(clientService);
//
//            // Test the changeStatusAClientServiceAndStoreHistoryEmployeeID method
//            clientServiceDAO.changeStatusAClientServiceAndStoreHistoryEmployeeID(1, "NewStatus", 2);
//
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//    }
}
