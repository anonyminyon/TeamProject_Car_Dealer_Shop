package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Account;
import model.Location;

public class LocationDAO extends DBContext {

    public List<Location> getListLocation() {
        List<Location> list = new ArrayList<>();
        String query = "select locationID, locationName, preRegFee, regFee from Location";
        try {
            PreparedStatement stm = connection.prepareStatement(query);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Location l = new Location(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getLong(3),
                        rs.getLong(4));
                list.add(l);
            }
            return list;
        } catch (Exception e) {
            System.out.println("checkBrand: " + e.getMessage());
        }
        return null;
    }

    public List<Location> getListLocation(int index, int quantity, String locationName) {
        List<Location> list = new ArrayList<>();
        String query = "select l.locationID, l.locationName, l.preRegFee, l.regFee, l.createdBy, a.accName,  l.createdOn, l.modifiedBy, b.accName, l.modifiedOn from Location l\n"
                + "LEFT JOIN Account a ON a.accID = l.createdBy\n"
                + "LEFT JOIN Account b ON b.accID = l.modifiedBy\n"
                + "WHERE l.locationName LIKE ?\n"
                + "ORDER BY l.locationID\n"
                + "OFFSET ? ROWS FETCH NEXT ? ROW ONLY;";
        try {
            PreparedStatement stm = connection.prepareStatement(query);
            stm.setString(1, "%" + locationName + "%");
            stm.setInt(2, (index - 1) * quantity);
            stm.setInt(3, quantity);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Location l = new Location(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getLong(3),
                        rs.getLong(4),
                        new Account(rs.getInt(5), rs.getString(6)),
                        rs.getTimestamp(7),
                        new Account(rs.getInt(8), rs.getString(9)),
                        rs.getTimestamp(10));
                list.add(l);
            }
            return list;
        } catch (Exception e) {
            System.out.println("checkBrand: " + e.getMessage());
        }
        return null;
    }

    public Location getLocationByID(int locationID) {
        String query = "select locationID, locationName, preRegFee, regFee from Location\n"
                + "where locationID = ?";
        try {
            PreparedStatement stm = connection.prepareStatement(query);
            stm.setInt(1, locationID);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Location l = new Location(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getLong(3),
                        rs.getLong(4));
                return l;
            }
        } catch (Exception e) {
            System.out.println("checkBrand: " + e.getMessage());
        }
        return null;
    }

    public int getTotalLocation(String locationName) {
        try {
            String query = "select count(locationID) from Location \n"
                    + "where locationName like ?";
            PreparedStatement stm = connection.prepareStatement(query);
            stm.setString(1, "%" + locationName + "%");
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            System.out.println("checkBrand: " + e.getMessage());
        }
        return 0;
    }

    public void addLocation(String locationName, String preRegFee, String regFee, int createdBy) {
        try {
            // SQL insert statement
            String sql = "Insert into Location (locationName, preRegFee, regFee, createdBy, createdOn) values (?, ?, ?, ?, GETDATE())";

            // Create a prepared statement
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, locationName);
            stm.setString(2, preRegFee);
            stm.setString(3, regFee);
            stm.setInt(4, createdBy);

            // Execute the insert statement
            stm.executeUpdate();

            // Handle success or error here
        } catch (SQLException e) {
            // Handle any errors that may occur
            e.printStackTrace();
            // Handle the error here
        }
    }

    public void updateLocation(String locationName, String preRegFee, String regFee, int modifiedBy, int locationID) {
        try {
            // SQL insert statement
            String sql = "update Location set locationName = ?, preRegFee = ?, regFee = ?, modifiedBy = ?, modifiedOn = GETDATE() where locationID = ?";

            // Create a prepared statement
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, locationName);
            stm.setString(2, preRegFee);
            stm.setString(3, regFee);
            stm.setInt(4, modifiedBy);
            stm.setInt(5, locationID);

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
        LocationDAO dao = new LocationDAO();
//        System.out.println(dao.getLocationByID(1));
//        System.out.println(dao.getListLocation(1, 5, ""));
//        System.out.println(dao.getTotalLocation(""));
//        dao.updateLocation("TP HCM", "10", "1000", 1, 2);
//        System.out.println(dao.getLocationByID(2));
        dao.addLocation("HCM", "10", "10", 1);
    }
}
