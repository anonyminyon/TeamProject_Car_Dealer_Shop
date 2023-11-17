/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import Service.Sercurity;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.ClientAccount;

/**
 *
 * @author Dao Anh Duc
 */
public class ClientAccountDAO extends DBContext {

    public void insertNewAccount(int clientID, String email, String pass) {
        try {
            // SQL insert statement
            String sql = "INSERT INTO [ClientAccount] ([accID], [email],[pass], [status])\n"
                    + "VALUES (?, ?, ?, 1)";

            // Create a prepared statement
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, clientID);
            stm.setString(2, email);
            stm.setString(3, pass);

            // Execute the insert statement
            stm.executeUpdate();
            stm.close();

            // Xử lý thành công ở đây nếu cần
        } catch (SQLException e) {
            // Xử lý lỗi ở đây, ví dụ:
            e.printStackTrace();
            // Hoặc bạn có thể log lỗi hoặc thông báo cho người dùng
        }
    }

    public ClientAccount checkAccountLogin(String email, String pass) {
        Sercurity se = new Sercurity();
        try {
            String sql = "select accID , pass, email from ClientAccount\n"
                    + "where email = ? and pass = ? and status = 1";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, email);
            stm.setString(2, se.MD5(pass));
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                return new ClientAccount(rs.getInt("accID"), rs.getString("email"), rs.getString("pass"));
            }
        } catch (Exception e) {
            System.out.println("checkAccount: " + e.getMessage());
        }
        return null;
    }

    public String getPasswordByEmail(String email) {
        try {
            String sql = "SELECT [pass] FROM [ClientAccount]\n"
                    + "WHERE email LIKE ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, email);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                String pass = rs.getString(1);
                return pass;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public boolean checkEmail(String email) {
        try {
            String sql = "SELECT COUNT(*) FROM ClientAccount ca\n"
                    + "WHERE ca.email LIKE ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, email);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                int check = rs.getInt(1);
                //nếu check khac 0 thif cai email đấy tồn tại
                if (check != 0) {
                    return false;
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return true;
    }

    public void updatePasswordByEmail(String email, String password) {
        String sql = "UPDATE [dbo].[ClientAccount]\n"
                + "SET [pass] = ?\n"
                + "WHERE email LIKE ?";

        try {
            PreparedStatement statement = connection.prepareCall(sql);
            statement.setString(1, password);
            statement.setString(2, email);
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        ClientAccountDAO dao = new ClientAccountDAO();

        System.out.println(dao.getPasswordByEmail("nuyenhongdang@gmail.com"));
    }
}
