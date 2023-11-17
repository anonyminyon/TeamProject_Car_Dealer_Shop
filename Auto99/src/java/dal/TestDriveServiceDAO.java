/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import model.Car;
import model.CarBrand;
import model.Client;
import model.EmployeeProfile;
import model.StatusCategory;
import model.TestDriveService;

/**
 *
 * @author Admin
 */
public class TestDriveServiceDAO extends DBContext {

    //singleton
    private static TestDriveServiceDAO instance = null;

    // Private constructor to prevent external instantiation
    private TestDriveServiceDAO() {
    }

    // Public method to get the singleton instance
    public static TestDriveServiceDAO getInstance() {
        if (instance == null) {
            instance = new TestDriveServiceDAO();
        }
        return instance;
    }
    //-------------------------------------------hàm-đao-dùng-cho-màn-client---------------------------------------------------

    public boolean isExist(int clientID, int carID, String dateService) {
        String sql = "SELECT *\n"
                + "FROM TestDriveService\n"
                + "WHERE clientID = ?\n"
                + "  AND carID = ?\n"
                + "  AND CONVERT(DATE, dateService) = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, clientID);
            preparedStatement.setInt(2, carID);
            preparedStatement.setString(3, dateService);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return true;
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception according to your needs
        }

        return false;
    }

    public void addTestDriveService(int clientID, int carID, Timestamp dateService, String status) {
        String sql = "INSERT INTO TestDriveService (clientID, carID, dateService, status) VALUES (?, ?, ?, ?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, clientID);
            preparedStatement.setInt(2, carID);
            preparedStatement.setTimestamp(3, dateService);
            preparedStatement.setString(4, status);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("TestDriveService added successfully.");
            } else {
                System.out.println("Failed to add TestDriveService.");
            }

            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception according to your needs
        }
    }
    //-------------------------------------------hàm-đao-dùng-cho-màn-admin---------------------------------------------------

    public ArrayList<TestDriveService> getAllTestDriveService() {
        ArrayList<TestDriveService> testDriveServices = new ArrayList<>();
        String sql = "SELECT * FROM TestDriveService";

        try ( PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int testDriveServiceID = resultSet.getInt("TestDriveServiceID");
                int clientID = resultSet.getInt("clientID");
                int carID = resultSet.getInt("carID");
                Timestamp dateService = resultSet.getTimestamp("dateService");
                String status = resultSet.getString("status");
                int employeeID = resultSet.getInt("employeeID"); // Assuming you have an employeeID column

                // Retrieve associated Client, Car, and EmployeeProfile
                Client client = getClientByID(clientID);
                Car car = getCarByID(carID);
                EmployeeProfile employeeProfile = getEmployeeProfileByID(employeeID);

                TestDriveService testDriveService = new TestDriveService(
                        testDriveServiceID, client, car, dateService, status, employeeProfile
                );

                testDriveServices.add(testDriveService);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception according to your needs
        }

        return testDriveServices;
    }

    // Example method to get a Car by ID
    private Car getCarByID(int carID) {
        Car car = null;

        String sql = "SELECT carID, carName, price, brandID, statusID "
                + "FROM Car WHERE carID = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, carID);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                car = new Car(
                        resultSet.getInt("carID"),
                        resultSet.getString("carName"),
                        resultSet.getLong("price"),
                        // Assuming you have a CarBrand class, adjust accordingly
                        new CarBrand(resultSet.getInt("brandID"))
                );
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception according to your needs
        }

        return car;
    }

    // Example method to get a Client by ID
    private Client getClientByID(int clientID) {
        String sql = "SELECT * FROM Client WHERE clientID = ?";

        try ( PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, clientID);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String clientName = resultSet.getString("clientName");
                String email = resultSet.getString("email");
                String phoneNumber = resultSet.getString("phoneNumber");

                java.sql.Date dob = resultSet.getDate("dob");
                String dobString = null;

                if (dob != null) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    dobString = sdf.format(dob);
                }
                boolean gender = resultSet.getBoolean("gender");
                String noID = resultSet.getString("noID");

                return new Client(clientID, clientName, email, phoneNumber, dobString, gender, noID);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception according to your needs
        }

        return null;
    }
    // Example method to get an EmployeeProfile by ID

    private EmployeeProfile getEmployeeProfileByID(int employeeID) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        EmployeeProfile employeeProfile = null;

        try {
            String query = "SELECT "
                    + "[employeeID], "
                    + "[firstName], "
                    + "[lastName], "
                    + "[email], "
                    + "[DOB], "
                    + "[phoneNumber], "
                    + "[img], "
                    + "[gender], "
                    + "[IDNo], "
                    + "[address], "
                    + "[startDate], "
                    + "[createdBy], " //(id nhân viên)(int)
                    + "[createdOn], " //(ngày tạo)(datetime)
                    + "[modifiedBy], " //(id nhân viên)(int)
                    + "[modifiedOn] " //(ngày sửa cái dòng đó)(datetime)
                    + "FROM [EmployeeProfile] "
                    + "WHERE [employeeID] = ?";

            // Create a PreparedStatement
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, employeeID);

            // Execute the query
            resultSet = preparedStatement.executeQuery();

            // Check if a record was found
            if (resultSet.next()) {
                // Create an EmployeeProfile object and populate it with data from the result set
                employeeProfile = new EmployeeProfile();
                employeeProfile.setEmployeeID(resultSet.getInt("employeeID"));
                employeeProfile.setFirstName(resultSet.getString("firstName"));
                employeeProfile.setLastName(resultSet.getString("lastName"));
                employeeProfile.setEmail(resultSet.getString("email"));
                employeeProfile.setDOB(resultSet.getDate("DOB"));
                employeeProfile.setPhoneNumber(resultSet.getString("phoneNumber"));
                employeeProfile.setImg(resultSet.getString("img"));
                employeeProfile.setGender(resultSet.getBoolean("gender"));
                employeeProfile.setIDNo(resultSet.getString("IDNo"));
                employeeProfile.setAddress(resultSet.getString("address"));
                employeeProfile.setStartDate(resultSet.getDate("startDate"));
                employeeProfile.setCreatedBy(resultSet.getInt("createdBy")); // Set the new attribute
                employeeProfile.setCreatedOn(resultSet.getTimestamp("createdOn")); // Set the new attribute
                employeeProfile.setModifiedBy(resultSet.getInt("modifiedBy")); // Set the new attribute
                employeeProfile.setModifiedOn(resultSet.getTimestamp("modifiedOn")); // Set the new attribute
            }
        } catch (SQLException e) {
            // Handle any SQL exceptions
            e.printStackTrace();
        }

        return employeeProfile;
    }

    public TestDriveService getTestDriveServiceByID(int testDriveServiceID) {
        String sql = "SELECT * FROM TestDriveService WHERE TestDriveServiceID = ?";
        TestDriveService testDriveService = null;

        try ( PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, testDriveServiceID);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int clientID = resultSet.getInt("clientID");
                int carID = resultSet.getInt("carID");
                Timestamp dateService = resultSet.getTimestamp("dateService");
                String status = resultSet.getString("status");
                int employeeID = resultSet.getInt("employeeID"); // Assuming you have an employeeID column

                // Retrieve associated Client, Car, and EmployeeProfile
                Client client = getClientByID(clientID);
                Car car = getCarByID(carID);
                EmployeeProfile employeeProfile = getEmployeeProfileByID(employeeID);

                testDriveService = new TestDriveService(
                        testDriveServiceID, client, car, dateService, status, employeeProfile
                );
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception according to your needs
        }

        return testDriveService;
    }

    public void changeStatusATestDriveServiceAndStoreHistoryEmployeeID(int TestDriveServiceID, String status, int accID) {

        String updateSql = "UPDATE TestDriveService SET status = ?, employeeID = ? WHERE TestDriveServiceID = ?";

        try ( PreparedStatement updateStatement = connection.prepareStatement(updateSql)) {
            // Update the status and employeeID in the main table
            updateStatement.setString(1, status);
            updateStatement.setInt(2, accID);
            updateStatement.setInt(3, TestDriveServiceID);
            updateStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception according to your needs
        }

    }

}
