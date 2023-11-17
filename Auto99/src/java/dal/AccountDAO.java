package dal;

import model.Account;
import model.Role;
import Service.Sercurity;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Feature;

public class AccountDAO extends MyDAO {

    /*------------------------------ ThinhNT -------------------------------------*/
 /* get user by account */
    public Account getAccountByEmail(String email) {
        try {
            String strSelect = "select accID, accName, password, roleID, email from Account \n"
                    + "where email = ?";
            ps = con.prepareStatement(strSelect);
            ps.setString(1, email);
            rs = ps.executeQuery();
            while (rs.next()) {
                Role r = new Role(rs.getInt(1));
                return new Account(rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        r,
                        rs.getString(5));
            }
        } catch (Exception e) {
            System.out.println("checkAccount: " + e.getMessage());
        }
        return null;
    }

    /* Update Password */
    public void updatePassword(String email, String password) {
        try {
            String strAdd = "update Account set password = ? where email = ?";
            ps = con.prepareStatement(strAdd);
            ps.setString(1, password);
            ps.setString(2, email);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("updatePassword: " + e.getMessage());
        }
    }
    
    
    public Account checkLogin(String user, String pass) {
        String query = "select * from Account where accName=? "
                + "and password=?";
        try {
            con = (new DBContext()).connection;
            ps = con.prepareStatement(query);
            ps.setString(1, user);
            ps.setString(2, pass);
            rs = ps.executeQuery();
            if (rs.next()) {
                Role r = new Role(rs.getInt("roleID"));
                Account a = new Account(rs.getInt("accID"), user, pass, r, rs.getString("email"));
                return a;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public void change(Account a) {
        String sql = "update account set password=? where accName=?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(2, a.getAccName());
            ps.setString(1, a.getPassword());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    /*------------------------------ ThinhNT -------------------------------------*/
//    Duc Manh
    public Account checkAccountLogin(String email, String password) {
        Sercurity se = new Sercurity();
        try {
            String strSelect = "select accID, accName, password, roleID, email from Account\n"
                    + "where email = ? and password = ? and status = 1";
            ps = con.prepareStatement(strSelect);
            ps.setString(1, email);
            ps.setString(2, se.MD5(password));
            rs = ps.executeQuery();
            while (rs.next()) {
                Role r = new Role(rs.getInt(1));
                return new Account(rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        r,
                        rs.getString(5));
            }
        } catch (Exception e) {
            System.out.println("checkAccount: " + e.getMessage());
        }
        return null;
    }
    
    public Account checkAccountByaccName(String accName, String password) {
        Sercurity se = new Sercurity();
        try {
            String strSelect = "select accID, accName, password, roleID, email from Account\n"
                    + "where accName = ? and password = ?";
            ps = con.prepareStatement(strSelect);
            ps.setString(1, accName);
            ps.setString(2, se.MD5(password));
            rs = ps.executeQuery();
            while (rs.next()) {
                Role r = new Role(rs.getInt(1));
                return new Account(rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        r,
                        rs.getString(5));
            }
        } catch (Exception e) {
            System.out.println("checkAccount: " + e.getMessage());
        }
        return null;
    }
  
    public List<Feature> getAllFeaturebyRole(int roleID) {
        List<Feature> list = new ArrayList<>();
        String query = "SELECT DISTINCT f.featureID, f.feature\n"
                + "FROM Feature f\n"
                + "INNER JOIN RoleFeature rf ON f.featureID = rf.featureID\n"
                + "INNER JOIN Role r ON r.roleID = rf.roleID\n"
                + "WHERE r.roleID = ?\n";
        try {
            PreparedStatement stm = connection.prepareStatement(query);
            stm.setInt(1, roleID);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Feature f = new Feature(
                        rs.getInt(1),
                        rs.getString(2));
                list.add(f);
            }
            return list;
        } catch (Exception e) {
        }
        return null;
    }

    public int getTotalActiveAcc() {
        String query = "SELECT Count(accID) from Account\n";
        try {
            PreparedStatement stm = connection.prepareStatement(query);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {

        }
        return 0;
    }
    
    public static void main(String[] args) {
        AccountDAO dao = new AccountDAO();
//        Account acc = dao.checkAccountLogin("user1@example.com", "Password1");
//        System.out.println(acc.toString());
        List<Feature> roleList = dao.getAllFeaturebyRole(2);
        System.out.println(roleList);
        System.out.println(dao.getTotalActiveAcc());
    }

}
