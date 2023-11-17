package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Account;
import model.PolicyFee;

public class FeeDAO extends DBContext {

    public List<PolicyFee> getListFee() {
        List<PolicyFee> list = new ArrayList<>();
        String query = "select feeID, feeName, fee from PolicyFee";
        try {
            PreparedStatement stm = connection.prepareStatement(query);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                PolicyFee f= new PolicyFee(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getLong(3));
                list.add(f);
            }
            return list;
        } catch (Exception e) {
            System.out.println("checkBrand: " + e.getMessage());
        }
        return null;
    }

    

    public List<PolicyFee> getListFee(int index, int quantity, String feeName) {
        List<PolicyFee> list = new ArrayList<>();
        String query = "select f.feeID, f.feeName, f.fee, f.createdBy, a.accName,  f.createdOn, f.modifiedBy, b.accName, f.modifiedOn from PolicyFee f\n"
                + "LEFT JOIN Account a ON a.accID = f.createdBy\n"
                + "LEFT JOIN Account b ON b.accID = f.modifiedBy\n"
                + "WHERE f.feeName LIKE ?\n"
                + "ORDER BY f.feeID\n"
                + "OFFSET ? ROWS FETCH NEXT ? ROW ONLY;";
        try {
            PreparedStatement stm = connection.prepareStatement(query);
            stm.setString(1, "%" + feeName + "%");
            stm.setInt(2, (index - 1) * quantity);
            stm.setInt(3, quantity);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                PolicyFee f = new PolicyFee(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getLong(3),
                        new Account(rs.getInt(4), rs.getString(5)),
                        rs.getTimestamp(6),
                        new Account(rs.getInt(7), rs.getString(8)),
                        rs.getTimestamp(9));
                list.add(f);
            }
            return list;
        } catch (Exception e) {
            System.out.println("checkBrand: " + e.getMessage());
        }
        return null;
    }

    public int getTotalPolicyFeeByName(String feeName) {
        try {
            String query = "select count(feeID) from PolicyFee \n"
                    + "where feeName like ?";
            PreparedStatement stm = connection.prepareStatement(query);
            stm.setString(1, "%" + feeName + "%");
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            System.out.println("checkBrand: " + e.getMessage());
        }
        return 0;
    }

    public void addPolicyFee(String feeName, String fee, int createdBy) {
        try {
            // SQL insert statement
            String sql = "Insert into PolicyFee (feeName, fee, createdBy, createdOn) values (?, ?, ?, GETDATE())";

            // Create a prepared statement
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, feeName);
            stm.setString(2, fee);
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

    public void updatePolicyFee(String feeName, String fee, int feeID, int modifiedBy) {
        try {
            // SQL insert statement
            String sql = "update PolicyFee set feeName = ?, fee = ?, modifiedBy = ?, modifiedOn = GETDATE() where feeID = ?";

            // Create a prepared statement
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, feeName);
            stm.setString(2, fee);
            stm.setInt(3, modifiedBy);
            stm.setInt(4, feeID);

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
        FeeDAO dao = new FeeDAO();
        System.out.println(dao.getListFee(1, 5, ""));
        dao.addPolicyFee("Bảo hiểm", "300000", 1);
    }
}
