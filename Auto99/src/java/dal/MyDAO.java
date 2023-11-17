package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Account;
import model.AutoPart;
import model.Role;

public class MyDAO extends DBContext {

    public Connection con = null;
    public PreparedStatement ps = null;
    public ResultSet rs = null;
    public String xSql = null;

    public MyDAO() {
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

    public Account checkLogin(String user, String pass) {
        String query = "select * from Account where accName=? "
                + "and password=?";
        try {
            con = (new DBContext()).connection;
            ps = con.prepareStatement(query);
            ps.setString(1, user);
            ps.setString(2, pass);
            rs = ps.executeQuery();
            if(rs.next()) {
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
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(2, a.getAccName());
            st.setString(1, a.getPassword());
            st.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    

}
