/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Account;
import model.CarBrand;

public class BrandDAO extends MyDAO {

    public String getBrandNameByID(int ID) {
        try {
            String strSelect = "select brandName from CarBrand \n"
                    + "where brandID = ?";
            ps = con.prepareStatement(strSelect);
            ps.setInt(1, ID);
            rs = ps.executeQuery();
            while (rs.next()) {
                return rs.getString(1);
            }
        } catch (Exception e) {
            System.out.println("checkBrand: " + e.getMessage());
        }
        return null;
    }

    public List<CarBrand> getAllBrandByName(int index, int quantity, String brandName) {
        List<CarBrand> list = new ArrayList<>();
        try {
            String strSelect = "SELECT c.brandID, c.brandName, c.brandIMG, c.createdBy, a.accName,  c.createdOn, c.modifiedBy, b.accName, c.modifiedOn\n"
                    + "FROM CarBrand c\n"
                    + "LEFT JOIN Account a ON a.accID = c.createdBy\n"
                    + "LEFT JOIN Account b ON b.accID = c.modifiedBy\n"
                    + "WHERE c.brandName LIKE ?\n"
                    + "ORDER BY c.brandID\n"
                    + "OFFSET ? ROWS FETCH NEXT ? ROW ONLY;";
            ps = con.prepareStatement(strSelect);
            ps.setString(1, "%" + brandName + "%");
            ps.setInt(2, (index - 1) * quantity);
            ps.setInt(3, quantity);
            rs = ps.executeQuery();
            while (rs.next()) {
                CarBrand cb = new CarBrand(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        new Account(rs.getInt(4), rs.getString(5)),
                        rs.getTimestamp(6),
                        new Account(rs.getInt(7), rs.getString(8)),
                        rs.getTimestamp(9)
                );
                list.add(cb);
            }
            return list;
        } catch (Exception e) {
            System.out.println("checkBrand: " + e.getMessage());
        }
        return null;
    }
    
    public int getTotalBrandByName(String brandName) {
        try {
            String strSelect = "select count(brandID) from CarBrand \n"
                    + "where brandName like ?";
            ps = con.prepareStatement(strSelect);
            ps.setString(1, "%" + brandName + "%");
            rs = ps.executeQuery();
            while (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            System.out.println("checkBrand: " + e.getMessage());
        }
        return 0;
    }

    public void addBrand(String brandName, String brandIMG, int createdBy) {
        try {
            // SQL insert statement
            String sql = "Insert into CarBrand (brandName, brandIMG, createdBy, createdOn) values (?, ?, ?, GETDATE())";

            // Create a prepared statement
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, brandName);
            stm.setString(2, brandIMG);
            stm.setInt(3, createdBy);

            // Execute the insert statement
            stm.executeUpdate();

            // Handle success or error here
        } catch (SQLException e) {
            // Handle any errors that may occur
            e.printStackTrace();
            // Handle the error here
        }
    }

    public void updateBrand(String brandName, String brandIMG, int brandID, int modifiedBy) {
        try {
            // SQL insert statement
            String sql = "update CarBrand set brandName = ?, brandIMG = ?, modifiedBy = ?, modifiedOn = GETDATE() where brandID = ?";

            // Create a prepared statement
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, brandName);
            stm.setString(2, brandIMG);
            stm.setInt(3, modifiedBy);
            stm.setInt(4, brandID);

            // Execute the insert statement
            stm.executeUpdate();

            // Handle success or error here
        } catch (SQLException e) {
            // Handle any errors that may occur
            e.printStackTrace();
            // Handle the error here
        }
    }

    public String getIMGname(int brandID) {
        try {
            String strSelect = "select brandIMG from CarBrand \n"
                    + "where brandID = ? ";
            ps = con.prepareStatement(strSelect);
            ps.setInt(1, brandID);
            rs = ps.executeQuery();
            while (rs.next()) {
                return rs.getString(1);
            }
        } catch (Exception e) {
            System.out.println("checkBrand: " + e.getMessage());
        }
        return "";
    }

    public void updateBrand(String brandName, int brandID) {
        try {
            // SQL insert statement
            String sql = "update CarBrand set brandName = ? where brandID = ?";

            // Create a prepared statement
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, brandName);
            stm.setInt(2, brandID);

            // Execute the insert statement
            stm.executeUpdate();

            // Handle success or error here
        } catch (SQLException e) {
            // Handle any errors that may occur
            e.printStackTrace();
            // Handle the error here
        }
    }

    public static void main(String[] args) {
        BrandDAO dao = new BrandDAO();
        System.out.println(dao.getAllBrandByName(1, 5, ""));
        System.out.println(dao.getTotalBrandByName(""));
       
        
    }

    //-----------------------------------------------------phần của hiếu-----------------------------------------------------
    public ArrayList getAllCarBrandTable() {
        ArrayList<CarBrand> cb = new ArrayList<>();
        String query = "SELECT [brandID],[brandName] FROM CarBrand";
        try {
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                cb.add(new CarBrand(rs.getInt("brandID"), rs.getString("brandName")));
            }
        } catch (Exception e) {
            return null;
        }
        return cb;
    }
    //----------------------------------------------------------------------------------------------------------------------
}
