/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author hiếu
 */
public class ClientNumberPlateDAO extends DBContext {
    //-----------------------------------------code-Hiếu-----------------------------------------------------------------
    public void addNumberPlateByClientID(int ClientID, String numberPlate) {
        // SQL query to insert the record
        String query = "INSERT INTO ClientNumberPlate (ClientID, NumberPlate) VALUES (?, ?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, ClientID);
            preparedStatement.setString(2, numberPlate);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            // Handle any database-related exceptions here
        }
    }
    //-----------------------------------------code-Hiếu-----------------------------------------------------------------
    public int getClientIDByNumberPlate(String numberPlate) {
        int clientID = -1; // Initialize with -1 (not found)

        // SQL query to select the ClientID based on the numberPlate
        String sql = "SELECT clientID FROM ClientNumberPlate WHERE numberPlate = ?";

        try{
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, numberPlate);

            // Execute the SQL query
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                // If a matching record is found, set the clientID
                clientID = resultSet.getInt("clientID");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            // Handle any database-related exceptions here
        }

        return clientID;
    }
    
}
