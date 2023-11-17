/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Account;
import model.AutoPart;
import model.Car;
import model.Role;

public class AutoPartDAO extends DBContext {
    
    public Connection con = null;
    public PreparedStatement ps = null;
    public ResultSet rs = null;
    public String xSql = null;
    
    public AutoPartDAO() {
        con = connection;
    }
    
    public void finalize() {
        try {
            if (con != null) {
                con.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public List<AutoPart> getAll() {
        List<AutoPart> list = new ArrayList<>();
        String query = "SELECT [autoPartID]\n"
                + "      ,[partName]\n"
                + "      ,[price]\n"
                + "      ,[partIMG]\n"
                + "  FROM [dbo].[AutoPart]"
                + "  WHERE status = True";
        try {
            PreparedStatement stm = connection.prepareStatement(query);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                AutoPart u = new AutoPart(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getLong(3)
                );
                list.add(u);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }
    
    public void delete(String id) {
        String sql = "DELETE FROM AutoPart\n"
                + "WHERE autoPartID=?";
        try {
            con = (new DBContext()).connection;
            ps = con.prepareStatement(sql);
            ps.setString(1, id);
            ps.executeUpdate();
            con.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    
    public void insertAutoPart(String partName, float price, String partIMG, boolean status, String description, Account acc) {
        
        String sql = "INSERT INTO [dbo].[AutoPart]\n"
                + "           ([partName]\n"
                + "           ,[price]\n"
                + "           ,[partIMG]\n"
                + "           ,[status]\n"
                + "           ,[description]\n"
                + "           ,[createdDate]\n"
                + "           ,[createdBy])\n"
                + "VALUES(?,?,?,?,?,GETDATE(),?)";
        try {
            con = (new DBContext()).connection;
            ps = con.prepareStatement(sql);
            ps.setString(1, partName);
            ps.setFloat(2, price);
            ps.setString(3, partIMG);
            ps.setBoolean(4, status);
            ps.setString(5, description);
            ps.setInt(6, acc.getAccID());
            ps.executeUpdate();
        } catch (Exception e) {
        }
    }
    
    public AutoPart getNewestPart() {
        String query = "SELECT Top 1 [autoPartID]\n"
                + "      ,[partName]\n"
                + "      ,[partIMG]\n"
                + "FROM [dbo].[AutoPart]\n"
                + "ORDER BY autoPartID DESC";
        try {
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                AutoPart a = new AutoPart(
                        rs.getInt("autoPartID"),
                        rs.getString("partName"),
                        rs.getString("partIMG"));
                
                return a;
            }
        } catch (Exception e) {
            
        }
        return null;
    }
    
    public void addCarAutoPart(List<Car> carList, int autoPartID) {
        try {
            // SQL insert statement
            String sql = "INSERT INTO [dbo].[CarAutoPart]\n"
                    + "           ([carID]\n"
                    + "           ,[autoPartID])\n"
                    + "     VALUES  (?, ?)";

            // Create a prepared statement
            PreparedStatement stm = connection.prepareStatement(sql);
            for (Car c : carList) {
                stm.setInt(1, c.getCarID());
                stm.setInt(2, autoPartID);

                // set other parameters as needed
                stm.addBatch();
            }
            stm.executeBatch();

            // Handle success or error here
        } catch (SQLException e) {
            // Handle any errors that may occur
            e.printStackTrace();
            // Handle the error here
        }
    }
    
    public List<AutoPart> getPaggingPartByName(int index, String name, int quantityShow, String status) {
        List<AutoPart> list = new ArrayList<>();
        boolean Status = true;
        String query = "SELECT c.[autoPartID]\n"
                + "      ,c.[partName]\n"
                + "      ,c.[price]\n"
                + "      ,c.[partIMG]\n"
                + "      ,c.[status]\n"
                + "      ,c.[description]\n"
                + "      ,c.[createdDate]\n"
                + "      ,c.[createdBy]\n"
                + "	 ,a.accName\n"
                + "      ,c.[modifiedDate]\n"
                + "      ,c.[modifiedBy]\n"
                + "	  ,b.accName as modifier\n"
                + "  FROM [dbo].[AutoPart] c\n"
                + "  LEFT JOIN Account a ON a.accID = c.createdBy\n"
                + "  LEFT JOIN Account b ON b.accID = c.modifiedBy\n"
                + "where partName like ?\n";
        if (!status.equals("")) {
            Status = Boolean.parseBoolean(status);
            query += "and c.status = ?\n";
        }
        query += "ORDER BY c.autoPartID\n"
                + "OFFSET ? ROW FETCH NEXT ? ROW ONLY";
        try {
            ps = connection.prepareStatement(query);
            int position = 1;
            ps.setString(position, "%" + name + "%");
            if (!status.equals("")) {
                position++;
                ps.setBoolean(position, Status);
            }
            ps.setInt(position + 1, (index - 1) * quantityShow);
            ps.setInt(position + 2, quantityShow);
            rs = ps.executeQuery();
            while (rs.next()) {
                Account createdBy = new Account(rs.getInt("createdBy"), rs.getString("accName"));
                Account modifiedBy = new Account(rs.getInt("modifiedBy"), rs.getString("modifier"));
                AutoPart a;
                a = new AutoPart(
                        rs.getInt("autoPartID"),
                        rs.getString("partName"),
                        rs.getLong("price"),
                        rs.getString("partIMG"),
                        rs.getBoolean("status"),
                        rs.getString("description"),
                        createdBy,
                        rs.getTimestamp("createdDate"),
                        modifiedBy,
                        rs.getTimestamp("modifiedDate")
                );
                list.add(a);
            }
        } catch (Exception e) {
            
        }
        return list;
    }
    
    public int getTotalPartByName(String name) {
        String query = "SELECT Count(autoPartID) from AutoPart "
                + "where partName like ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, "%" + name + "%");
            rs = ps.executeQuery();
            while (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            
        }
        return 0;
    }
    
    public AutoPart getPartByID(int autoPartID) {
        String query = "SELECT c.[autoPartID]\n"
                + "      ,c.[partName]\n"
                + "      ,c.[price]\n"
                + "      ,c.[partIMG]\n"
                + "      ,c.[status]\n"
                + "      ,c.[description]\n"
                + "      ,c.[createdDate]\n"
                + "      ,c.[createdBy]\n"
                + "	  ,a.accName\n"
                + "      ,c.[modifiedDate]\n"
                + "      ,c.[modifiedBy]\n"
                + "	  ,b.accName as modifier\n"
                + "FROM [dbo].[AutoPart] c\n"
                + "  LEFT JOIN Account a ON a.accID = c.createdBy\n"
                + "  LEFT JOIN Account b ON b.accID = c.modifiedBy\n"
                + "where c.autoPartID = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, autoPartID);
            rs = ps.executeQuery();
            while (rs.next()) {
                Account createdBy = new Account(rs.getInt("createdBy"), rs.getString("accName"));
                Account modifiedBy = new Account(rs.getInt("modifiedBy"), rs.getString("modifier"));
                AutoPart a = new AutoPart(
                        rs.getInt("autoPartID"),
                        rs.getString("partName"),
                        rs.getLong("price"),
                        rs.getString("partIMG"),
                        rs.getBoolean("status"),
                        rs.getString("description"),
                        createdBy,
                        rs.getTimestamp("createdDate"),
                        modifiedBy,
                        rs.getTimestamp("modifiedDate")
                );
                return a;
            }
        } catch (Exception e) {
            
        }
        return null;
    }
    
    public boolean isPartIDExist(int partID) {
        try {
            // SQL query to check if a service exists with the given parameters and is not valid
            String sql = "SELECT [autoPartID],[carID] FROM AutoPart\n"
                    + "WHERE autoPartID = ?  ";
            // Create a prepared statement
            ps = connection.prepareStatement(sql);
            ps.setInt(1, partID);
            // Execute the query
            rs = ps.executeQuery();
            //if rs has value
            if (rs.next()) {
                // Close resources
                rs.close();
                ps.close();
                return true;
            }
            // Close resources
            rs.close();
            ps.close();
            // No matching autoPartID found
            return false;
        } catch (SQLException e) {
            // Handle any errors that may occur
            e.printStackTrace();
            // Handle the error here and return false or throw an exception
            return false;
        }
    }
    
    public List<Car> getListCarbyAutoPart(int autoPartID) {
        List<Car> carList = new ArrayList<>();
        try {
            // SQL query to check if a service exists with the given parameters and is not valid
            String sql = "SELECT Distinct c.[carID],c.[carName] FROM Car c\n"
                    + "	LEFT JOIN CarAutoPart a on c.carID = a.carID\n"
                    + "	where a.autoPartID =  ?";
            // Create a prepared statement
            ps = connection.prepareStatement(sql);
            ps.setInt(1, autoPartID);
            // Execute the query
            rs = ps.executeQuery();
            //if rs has value
            while (rs.next()) {
                Car car = new Car(
                        rs.getInt(1),
                        rs.getString(2)
                );
                carList.add(car);
            }
        } catch (SQLException e) {
            // Handle any errors that may occur
            e.printStackTrace();
            // Handle the error here and return false or throw an exception
        }
        return carList;
    }
    
    public void deleteAllByautoPartID(ArrayList<String> list) {
        // Check if the list is empty or null, and handle it accordingly
        if (list == null || list.isEmpty()) {
            System.out.println("No autoPartID provided for deletion.");
            return;
        }

        // Create query
        StringBuilder query = new StringBuilder();
        query.append("DELETE FROM AutoPart WHERE autoPartID IN (");

        // Create placeholders for serviceID values
        for (int i = 0; i < list.size(); i++) {
            query.append("?");
            if (i < list.size() - 1) {
                query.append(",");
            }
        }
        query.append(")");
        
        try {
            // Prepare the statement and set the parameter values for the IN clause
            ps = con.prepareStatement(query.toString());
            for (int i = 0; i < list.size(); i++) {
                ps.setString(i + 1, list.get(i));
            }

            // Execute the DELETE statement
            int rowsDeleted = ps.executeUpdate();

            // Optionally, you can check the number of rows deleted
            System.out.println(rowsDeleted + " rows deleted.");

            // Close the PreparedStatement (optional but recommended)
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the SQLException appropriately
        }
    }
    
    public AutoPart getPart(int autoPartID) {
        AutoPart a = new AutoPart();
        String query = "SELECT c.[autoPartID]\n"
                + "      ,c.[partName]\n"
                + "      ,c.[price]\n"
                + "      ,c.[partIMG]\n"
                + "      ,c.[status]\n"
                + "      ,c.[description]\n"
                + "      ,c.[createdDate]\n"
                + "      ,c.[createdBy]\n"
                + "	  ,a.accName\n"
                + "      ,c.[modifiedDate]\n"
                + "      ,c.[modifiedBy]\n"
                + "	  ,b.accName as modifier\n"
                + "where autoPartID = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, autoPartID);
            rs = ps.executeQuery();
            while (rs.next()) {
                Account createdBy = new Account(rs.getInt("createdBy"), rs.getString("accName"));
                Account modifiedBy = new Account(rs.getInt("modifiedBy"), rs.getString("modifier"));
                a = new AutoPart(
                        rs.getInt("autoPartID"),
                        rs.getString("partName"),
                        rs.getLong("price"),
                        rs.getString("partIMG"),
                        rs.getBoolean("status"),
                        rs.getString("description"),
                        createdBy,
                        rs.getTimestamp("createdDate"),
                        modifiedBy,
                        rs.getTimestamp("modifiedDate")
                );
                con.close();
            }
        } catch (Exception e) {
            
        }
        return a;
    }
    
    public void updatePart(Integer autoPartID, String partName, float price, String partIMG, boolean status, String description, Account acc) {
        String sql = "UPDATE [dbo].[AutoPart]\n"
                + "   SET [partName] = ?\n"
                + "      ,[price] = ?\n"
                + "      ,[partIMG] = ?\n"
                + "      ,[status] = ?\n"
                + "      ,[description] = ?\n"
                + "      ,[modifiedDate] = GETDATE()\n"
                + "      ,[modifiedBy] = ?\n"
                + " WHERE autoPartID = ?";
        try {
            con = (new DBContext()).connection;
            ps = con.prepareStatement(sql);
            ps.setString(1, partName);
            ps.setFloat(2, price);
            ps.setString(3, partIMG);
            ps.setBoolean(4, status);
            ps.setString(5, description);
            ps.setInt(6, acc.getAccID());
            ps.setInt(7, autoPartID);
            ps.executeUpdate();
            con.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    
    public void updatePart(Integer autoPartID, float price, boolean status, Account acc) {
        String sql = "UPDATE [dbo].[AutoPart]\n"
                + "   SET [status] = ?\n"
                + "      ,[price] = ?\n"
                + "      ,[modifiedDate] = GETDATE()\n"
                + "      ,[modifiedBy] = ?"
                + " WHERE autoPartID = ?";
        try {
            con = (new DBContext()).connection;
            ps = con.prepareStatement(sql);
            ps.setBoolean(1, status);
            ps.setFloat(2, price);
            ps.setInt(3, acc.getAccID());
            ps.setInt(4, autoPartID);
            ps.executeUpdate();
            con.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    
    public void deleteCarAutoPart(Integer autoPartID) {
        String sql = "DELETE FROM [dbo].[CarAutoPart] WHERE autoPartID = ?";
        try {
            con = (new DBContext()).connection;
            ps = con.prepareStatement(sql);
            ps.setInt(1, autoPartID);
            ps.executeUpdate();
            con.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    
    
    public boolean partNameExist(String partName, int autoPartID) {
                String query = "SELECT COUNT(*) FROM AutoPart WHERE partName = ?";
        if (autoPartID != 0) {
            query += " and autoPartID != ?";
        }
        try {
            PreparedStatement stm = connection.prepareStatement(query);
            stm.setString(1, partName);
            if (autoPartID != 0) {
                stm.setInt(2, autoPartID);
            }
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                int count = rs.getInt(1);
                return (count > 0);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public static void main(String[] args) {
        AutoPartDAO dao = new AutoPartDAO();
        List<AutoPart> list = dao.getPaggingPartByName(1, "", 5, "");
        System.out.println(list);
        System.out.println(dao.getNewestPart());
        System.out.println(dao.getListCarbyAutoPart(3));
        dao.deleteCarAutoPart(3);
        System.out.println(dao.getPartByID(2));
        System.out.println(dao.partNameExist("Lốp xe", 1));
    }

}
