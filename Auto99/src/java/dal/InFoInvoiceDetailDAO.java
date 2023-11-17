/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.InFoInvoiceDetail;

/**
 *
 * @author HieuHT
 */
public class InFoInvoiceDetailDAO extends DBContext {
    //singleton

    private static InFoInvoiceDetailDAO instance = null;

    // Private constructor to prevent external instantiation
    private InFoInvoiceDetailDAO() {
    }

    // Public method to get the singleton instance
    public static InFoInvoiceDetailDAO getInstance() {
        if (instance == null) {
            instance = new InFoInvoiceDetailDAO();
        }
        return instance;
    }

    //---------------------------------------------------------hiếu---------------------------------------------------------    
    public ArrayList<InFoInvoiceDetail> getAllInforDetailPayment(int serviceInvoiceID) throws SQLException {
        ArrayList<InFoInvoiceDetail> infoInvoiceDetails = new ArrayList<>();
        String sql = "SELECT * FROM InFoInvoiceDetail WHERE serviceInvoiceID = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, serviceInvoiceID);
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            InFoInvoiceDetail detail = new InFoInvoiceDetail();
            detail.setInfoDetailID(resultSet.getInt("infoDetailID"));
            detail.setServiceInvoiceID(resultSet.getInt("serviceInvoiceID"));
            detail.setProductName(resultSet.getString("productName"));
            detail.setUnitPrice(resultSet.getString("unitPrice"));
            detail.setQuantity(resultSet.getString("quantity"));
            infoInvoiceDetails.add(detail);
        }
        return infoInvoiceDetails;
    }

//    public static void main(String[] args) {
//        // Replace these with your database connection details
//        String url = "jdbc:mysql://your_database_server/your_database";
//        String username = "your_username";
//        String password = "your_password";
//
//        int serviceInvoiceID = 123; // Replace with the desired serviceInvoiceID
//
//        try  {
//            InFoInvoiceDetailDAO inFoInvoiceDetailDAO = new InFoInvoiceDetailDAO(); // Replace with the actual DAO class
//
//            ArrayList<InFoInvoiceDetail> infoInvoiceDetails = inFoInvoiceDetailDAO.getAllInforDetailPayment(serviceInvoiceID);
//
//            if (infoInvoiceDetails.isEmpty()) {
//                System.out.println("No InFoInvoiceDetail records found for serviceInvoiceID: " + serviceInvoiceID);
//            } else {
//                for (InFoInvoiceDetail detail : infoInvoiceDetails) {
//                    System.out.println("Info Detail ID: " + detail.getInfoDetailID());
//                    System.out.println("Service Invoice ID: " + detail.getServiceInvoiceID());
//                    System.out.println("Product Name: " + detail.getProductName());
//                    System.out.println("Unit Price: " + detail.getUnitPrice());
//                    System.out.println("Quantity: " + detail.getQuantity());
//                    System.out.println();
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
}
