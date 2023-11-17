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
import java.util.ArrayList;
import java.util.List;
import model.Service;

/**
 *
 * @author Admin
 */
// ================================HIEUHT================================
public class ServiceDAO extends DBContext {

    //--------------------------------------------------------Staff--------------------------------------------------------
    //get all list service
    public ArrayList<Service> getAllService() {
        ArrayList<Service> arrService = new ArrayList<>();

        try {
            String query = "SELECT [serviceID], [serviceType], [serviceContent], [serviceStatus], [servicePrice], [createdBy], [createdOn], [modifiedBy], [modifiedOn] "
                    + " FROM [Service]";
            PreparedStatement stm = connection.prepareStatement(query);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Service s = new Service(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getBoolean(4),
                        rs.getFloat(5),
                        rs.getInt(6), // createdBy
                        rs.getTimestamp(7), // createdOn
                        rs.getInt(8), // modifiedBy
                        rs.getTimestamp(9) // modifiedOn
                );
                arrService.add(s);
            }
        } catch (Exception e) {
            e.printStackTrace(); // Handle any exceptions
        }
        return arrService;
    }

    public ArrayList<Service> getServicesByPage(int start, String name, int pageSize, String serviceStatusFilter) {
        ArrayList<Service> arrService = new ArrayList<>();

        try {
            String query = "SELECT [serviceID], [serviceType], [serviceContent], [serviceStatus], [servicePrice], [createdBy], [createdOn], [modifiedBy], [modifiedOn] "
                    + "FROM [Service] "
                    + "WHERE [serviceType] LIKE ?";

            if (!serviceStatusFilter.equals("")) {
                query += " AND [serviceStatus] = ?";
            }

            query += " ORDER BY [serviceID] OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

            PreparedStatement stm = connection.prepareStatement(query);
            int position = 1;
            stm.setString(position++, "%" + name + "%");

            if (!serviceStatusFilter.equals("")) {
                stm.setBoolean(position++, Boolean.parseBoolean(serviceStatusFilter));
            }

            stm.setInt(position++, (start - 1) * pageSize);
            stm.setInt(position, pageSize);

            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Service s = new Service(
                        rs.getInt("serviceID"),
                        rs.getString("serviceType"),
                        rs.getString("serviceContent"),
                        rs.getBoolean("serviceStatus"),
                        rs.getFloat("servicePrice"),
                        rs.getInt("createdBy"),
                        rs.getTimestamp("createdOn"),
                        rs.getInt("modifiedBy"),
                        rs.getTimestamp("modifiedOn")
                );
                arrService.add(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return arrService;
    }

    public int getTotalServicesByName(String name, String serviceStatusFilter) {
        String query = "SELECT Count([serviceID]) from [Service] "
                + "where [serviceType] like ?";
        if (!serviceStatusFilter.equals("")) {
            query += " AND [serviceStatus] = ? \n";
        }
        try {
            PreparedStatement stm = connection.prepareStatement(query);
            int position = 1;
            stm.setString(position++, "%" + name + "%");
            if (!serviceStatusFilter.equals("")) {
                stm.setBoolean(position++, Boolean.parseBoolean(serviceStatusFilter));
            }
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {

        }
        return 0;
    }

    public void addANewService(String serviceType, String serviceContent, String servicePrice, int createdBy) {
        try {
            // Get the current timestamp
            Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());

            // SQL insert statement without modifiedBy and modifiedOn
            String sql = "INSERT INTO [Service] (serviceType, serviceContent, serviceStatus, servicePrice, createdBy, createdOn) VALUES (?, ?, ?, ?, ?, ?)";

            // Create a prepared statement
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, serviceType);
            preparedStatement.setString(2, serviceContent);
            preparedStatement.setBoolean(3, true); // Set serviceStatus to true
            preparedStatement.setFloat(4, Float.parseFloat(servicePrice));
            preparedStatement.setInt(5, createdBy);//id of employee who created this service
            preparedStatement.setTimestamp(6, currentTimestamp); // Set createdOn to the current timestamp

            // Execute the insert statement
            preparedStatement.executeUpdate();

            // Close the database connection
            preparedStatement.close();

            // Handle success or error here
        } catch (SQLException e) {
            // Handle any errors that may occur
            e.printStackTrace();
            // Handle the error here
        }
    }

    public boolean isServiceExistAndNotValid(String serviceType) {
        try {

            // SQL query to check if a service exists with the given parameters and is not valid
            String sql = "SELECT [serviceType] FROM Service "
                    + "WHERE serviceType = ? ";

            // Create a prepared statement
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, serviceType);

            // Execute the query
            ResultSet resultSet = preparedStatement.executeQuery();

            //if rs has value
            if (resultSet.next()) {
                // Close resources
                resultSet.close();
                preparedStatement.close();

                return true;
            }
            // Close resources
            resultSet.close();
            preparedStatement.close();

            // No matching service found or it is valid
            return false;

        } catch (SQLException e) {
            // Handle any errors that may occur
            e.printStackTrace();
            // Handle the error here and return false or throw an exception
            return false;
        }
    }
    
    public boolean isServiceExistAndNotValid(String serviceType, int serviceID) {
        try {

            // SQL query to check if a service exists with the given parameters and is not valid
            String sql = "SELECT [serviceType] FROM Service "
                    + "WHERE serviceType = ? , serviceID <> ?";

            // Create a prepared statement
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, serviceType);
            preparedStatement.setInt(2, serviceID);

            // Execute the query
            ResultSet resultSet = preparedStatement.executeQuery();

            //if rs has value
            if (resultSet.next()) {
                // Close resources
                resultSet.close();
                preparedStatement.close();

                return true;
            }
            // Close resources
            resultSet.close();
            preparedStatement.close();

            // No matching service found or it is valid
            return false;

        } catch (SQLException e) {
            // Handle any errors that may occur
            e.printStackTrace();
            // Handle the error here and return false or throw an exception
            return false;
        }
    }

    public void deleteAllByServiceID(ArrayList<String> listServiceID) {
        // Check if the list is empty or null, and handle it accordingly
        if (listServiceID == null || listServiceID.isEmpty()) {
            System.out.println("No serviceIDs provided for deletion.");
            return;
        }

        // Create query
        StringBuilder query = new StringBuilder();
        query.append("DELETE FROM Service WHERE serviceID IN (");

        // Create placeholders for serviceID values
        for (int i = 0; i < listServiceID.size(); i++) {
            query.append("?");
            if (i < listServiceID.size() - 1) {
                query.append(",");
            }
        }
        query.append(")");

        try {
            // Prepare the statement and set the parameter values for the IN clause
            PreparedStatement preparedStatement = connection.prepareStatement(query.toString());
            for (int i = 0; i < listServiceID.size(); i++) {
                preparedStatement.setString(i + 1, listServiceID.get(i));
            }

            // Execute the DELETE statement
            int rowsDeleted = preparedStatement.executeUpdate();

            // Optionally, you can check the number of rows deleted
            System.out.println(rowsDeleted + " rows deleted.");

            // Close the PreparedStatement (optional but recommended)
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the SQLException appropriately
        }
    }

    public void updateServiceByAServiceID
        (String serviceID, String serviceType, String serviceContent, String serviceStatus, String servicePrice, int modifiedBy) {
        PreparedStatement preparedStatement = null;

        // Get the current timestamp
        Timestamp modifiedOn = new Timestamp(System.currentTimeMillis());

        try {
            // Define the base SQL update query
            StringBuilder updateQuery = new StringBuilder("UPDATE Service SET ");

            // Create a flag to check if any field needs updating
            boolean fieldsToUpdate = false;

            // Check and add parameters to the query as needed
            if (serviceType != null) {
                updateQuery.append("serviceType = ?, ");
                fieldsToUpdate = true;
            }

            if (serviceContent != null) {
                updateQuery.append("serviceContent = ?, ");
                fieldsToUpdate = true;
            }

            if (serviceStatus != null || !serviceStatus.isEmpty()) {
                updateQuery.append("serviceStatus = ?, ");
                fieldsToUpdate = true;
            }
            
            if (servicePrice != null) {
                updateQuery.append("servicePrice = ?, ");
                fieldsToUpdate = true;
            }

            // Add modifiedBy and modifiedOn fields to the query
            updateQuery.append("modifiedBy = ?, ");
            updateQuery.append("modifiedOn = ?, ");
            fieldsToUpdate = true;

            // Remove the trailing comma and space if any fields are updated
            if (fieldsToUpdate) {
                updateQuery.setLength(updateQuery.length() - 2);

                // Add the WHERE clause to specify which record to update
                updateQuery.append(" WHERE serviceID = ?");

                // Create a PreparedStatement
                preparedStatement = connection.prepareStatement(updateQuery.toString());

                // Set the parameters in the PreparedStatement
                int parameterIndex = 1;

                if (serviceType != null) {
                    preparedStatement.setString(parameterIndex++, serviceType);
                }

                if (serviceContent != null) {
                    preparedStatement.setString(parameterIndex++, serviceContent);
                }

                if (serviceStatus != null) {
                    preparedStatement.setBoolean(parameterIndex++, Boolean.parseBoolean(serviceStatus));
                }
                
                if (servicePrice != null) {
                    preparedStatement.setFloat(parameterIndex++, Float.parseFloat(servicePrice));
                }

                // Set modifiedBy and modifiedOn parameters
                preparedStatement.setInt(parameterIndex++, modifiedBy);
                preparedStatement.setTimestamp(parameterIndex++, modifiedOn);

                // Set the serviceID parameter
                preparedStatement.setInt(parameterIndex, Integer.parseInt(serviceID));

                // Execute the update
                preparedStatement.executeUpdate();

            } else {
                System.out.println("No fields provided for update.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close the resources in a finally block to ensure they are always closed
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public Service getAServiceByServiceID(int serviceID) {
        PreparedStatement stm = null;
        ResultSet rs = null;
        Service s = null;

        try {
            // Define the SQL query to retrieve the service details by serviceID, including the new attributes.
            String query = "SELECT serviceID, serviceType, serviceContent, serviceStatus, servicePrice, createdBy, createdOn, modifiedBy, modifiedOn FROM Service WHERE serviceID = ?";

            // Create a prepared statement with the query.
            stm = connection.prepareStatement(query);
            stm.setInt(1, serviceID); // Set the serviceID parameter.

            // Execute the query and get the result set.
            rs = stm.executeQuery();

            // Check if a row was found.
            if (rs.next()) {
                // Retrieve values from the result set and assign them to variables.
                int retrievedServiceID = rs.getInt("serviceID");
                String serviceType = rs.getString("serviceType");
                String serviceContent = rs.getString("serviceContent");
                boolean serviceStatus = rs.getBoolean("serviceStatus");
                float servicePrice = rs.getFloat("servicePrice");
                int createdBy = rs.getInt("createdBy");
                Timestamp createdOn = rs.getTimestamp("createdOn");
                int modifiedBy = rs.getInt("modifiedBy");
                Timestamp modifiedOn = rs.getTimestamp("modifiedOn");

                // Create a new Service object with the retrieved attributes.
                s = new Service(retrievedServiceID, serviceType, serviceContent, serviceStatus, servicePrice, createdBy, createdOn, modifiedBy, modifiedOn);
            }
        } catch (SQLException e) {
            // Handle any SQLException that might occur during database interaction.
            e.printStackTrace(); // Replace with appropriate error handling.
        } finally {
            // Close the resources (result set, statement, and connection).
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stm != null) {
                    stm.close();
                }
            } catch (SQLException e) {
                e.printStackTrace(); // Handle any exceptions during resource closing.
            }
        }
        return s;
    }

    //--------------------------------------------------------Client--------------------------------------------------------
    public ArrayList<Service> getAllServiceAvailable() {
        ArrayList<Service> arrService = new ArrayList<>();

        try {
            // Return all services with serviceStatus set to true
            String query = "SELECT [serviceID], [serviceType], [serviceContent], [serviceStatus], [servicePrice], [createdBy], [createdOn], [modifiedBy], [modifiedOn] FROM [Service] WHERE [serviceStatus] = 1";
            PreparedStatement stm = connection.prepareStatement(query);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Service s = new Service(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getBoolean(4),
                        rs.getFloat(5),
                        rs.getInt(6), // createdBy
                        rs.getTimestamp(7), // createdOn
                        rs.getInt(8), // modifiedBy
                        rs.getTimestamp(9) // modifiedOn
                );
                arrService.add(s);
            }
        } catch (Exception e) {
            e.printStackTrace(); // Handle any exceptions
        }
        return arrService;
    }

}
