package dal;

import Message.ConstantMessages;
import dal.DBContext;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import model.EmployeeProfile;
import model.Role;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author HieuHT
 */
public class EmployeeProfileDAO extends DBContext {

    /**
     * *************************************************************Staff******************************************************************
     */
    //-----------------------------------------code-Hiếu-----------------------------------------------------------------
    public EmployeeProfile getEmployeeProfileByAccID(int accID) throws SQLException {

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        EmployeeProfile employeeProfile = null;

        try {
            String query = "SELECT "
                    + "[employeeID], "
                    + "[firstName], "
                    + "[lastName], "
                    + "[email], "
                    + "[DOB], "
                    + "[phoneNumber], "
                    + "[img], "
                    + "[gender], "
                    + "[IDNo], "
                    + "[address], "
                    + "[startDate], "
                    + "[createdBy], " //(id nhân viên)(int)
                    + "[createdOn], " //(ngày tạo)(datetime)
                    + "[modifiedBy], " //(id nhân viên)(int)
                    + "[modifiedOn] " //(ngày sửa cái dòng đó)(datetime)
                    + "FROM [EmployeeProfile] "
                    + "WHERE [employeeID] = ?";

            // Create a PreparedStatement
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, accID);

            // Execute the query
            resultSet = preparedStatement.executeQuery();

            // Check if a record was found
            if (resultSet.next()) {
                // Create an EmployeeProfile object and populate it with data from the result set
                employeeProfile = new EmployeeProfile();
                employeeProfile.setEmployeeID(resultSet.getInt("employeeID"));
                employeeProfile.setFirstName(resultSet.getString("firstName"));
                employeeProfile.setLastName(resultSet.getString("lastName"));
                employeeProfile.setEmail(resultSet.getString("email"));
                employeeProfile.setDOB(resultSet.getDate("DOB"));
                employeeProfile.setPhoneNumber(resultSet.getString("phoneNumber"));
                employeeProfile.setImg(resultSet.getString("img"));
                employeeProfile.setGender(resultSet.getBoolean("gender"));
                employeeProfile.setIDNo(resultSet.getString("IDNo"));
                employeeProfile.setAddress(resultSet.getString("address"));
                employeeProfile.setStartDate(resultSet.getDate("startDate"));
                employeeProfile.setCreatedBy(resultSet.getInt("createdBy")); // Set the new attribute
                employeeProfile.setCreatedOn(resultSet.getTimestamp("createdOn")); // Set the new attribute
                employeeProfile.setModifiedBy(resultSet.getInt("modifiedBy")); // Set the new attribute
                employeeProfile.setModifiedOn(resultSet.getTimestamp("modifiedOn")); // Set the new attribute
            }
        } catch (SQLException e) {
            // Handle any SQL exceptions
            e.printStackTrace();
        } finally {
            // Close the database resources (PreparedStatement, ResultSet, Connection)
            if (resultSet != null) {
                resultSet.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            // You should not close the connection here. It should be managed externally.
        }

        return employeeProfile;
    }

    //-----------------------------------------code-Hiếu-----------------------------------------------------------------
    public void updateEmployeeProfile(int employeeID, String firstName, String lastName, String email, String phoneNumber,
            String img, String IDNo, String address, String DOB, String gender, String startDate, int modifiedBy) throws SQLException, ParseException {
        PreparedStatement preparedStatement = null;

        try {
            // Bắt đầu câu lệnh UPDATE
            StringBuilder query = new StringBuilder("UPDATE [EmployeeProfile] SET ");

            // Khởi tạo một HashMap để lưu trữ ánh xạ giữa tên trường và giá trị
            HashMap<String, Object> fieldValues = new HashMap<>();

            // Ánh xạ các trường dữ liệu vào HashMap
            if (firstName != null && !firstName.isEmpty()) {
                fieldValues.put("[firstName] = ?", firstName);
            }
            if (lastName != null && !lastName.isEmpty()) {
                fieldValues.put("[lastName] = ?", lastName);
            }
            if (email != null && !email.isEmpty()) {
                fieldValues.put("[email] = ?", email);
            }
            if (phoneNumber != null && !phoneNumber.isEmpty()) {
                fieldValues.put("[phoneNumber] = ?", phoneNumber);
            }
            if (img != null && !img.isEmpty()) {
                fieldValues.put("[img] = ?", img);
            }
            if (IDNo != null && !IDNo.isEmpty()) {
                fieldValues.put("[IDNo] = ?", IDNo);
            }
            if (address != null && !address.isEmpty()) {
                fieldValues.put("[address] = ?", address);
            }

            // Convert and add DOB to the map
            if (DOB != null && !DOB.isEmpty()) {
                Date dobDate = new SimpleDateFormat("yyyy-MM-dd").parse(DOB);
                fieldValues.put("[DOB] = ?", dobDate);
            }

            // Convert and add startDate to the map
            if (startDate != null && !startDate.isEmpty()) {
                Date startDateDate = new SimpleDateFormat("yyyy-MM-dd").parse(startDate);
                fieldValues.put("[startDate] = ?", startDateDate);
            }

            // Convert and add gender to the map
            if (gender != null && !gender.isEmpty()) {
                boolean genderValue = Boolean.parseBoolean(gender);
                fieldValues.put("[gender] = ?", genderValue);
            }

            // Add the new attributes
            fieldValues.put("[modifiedBy] = ?", modifiedBy);
            fieldValues.put("[modifiedOn] = ?", new Timestamp(System.currentTimeMillis()));

            // Thêm các trường cần cập nhật vào câu lệnh SQL
            query.append(String.join(", ", fieldValues.keySet()));
            query.append(" WHERE [employeeID] = ?");

            // Create a PreparedStatement
            preparedStatement = connection.prepareStatement(query.toString());

            // Thiết lập giá trị cho các tham số
            int parameterIndex = 1;
            for (Object value : fieldValues.values()) {
                preparedStatement.setObject(parameterIndex++, value);
            }

            // Thiết lập giá trị cho tham số cuối cùng
            preparedStatement.setInt(parameterIndex, employeeID);

            // Execute the update
            preparedStatement.executeUpdate();
        } catch (SQLException | ParseException e) {
            // Handle any SQL exceptions or parsing exceptions
            e.printStackTrace();
        } finally {
            // Close the PreparedStatement (you should not close the connection here)
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        }
    }

    public ArrayList<EmployeeProfile> getAllMechanic() {
        // Bước 1: Khởi tạo danh sách để chứa thông tin thợ máy
        ArrayList<EmployeeProfile> listMechanicInfo = new ArrayList<>();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            // Bước 2: Mở và khởi tạo kết nối đến cơ sở dữ liệu
            // Khởi tạo và mở kết nối cơ sở dữ liệu (bạn cần thêm logic kết nối cơ sở dữ liệu ở đây).
            // Bước 3: Xây dựng câu truy vấn SQL để lấy thông tin của thợ máy
            String sql = "SELECT * FROM EmployeeProfile ep "
                    + "INNER JOIN Account acc ON ep.employeeID = acc.accID "
                    + "INNER JOIN Role r ON acc.roleID = r.roleID "
                    + "WHERE r.roleName = 'mechanic'";
            stmt = connection.prepareStatement(sql);
            rs = stmt.executeQuery();

            // Bước 4: Lặp qua kết quả truy vấn để lấy thông tin và thêm vào danh sách
            while (rs.next()) {
                EmployeeProfile mechanic = new EmployeeProfile();
                mechanic.setEmployeeID(rs.getInt("employeeID"));
                mechanic.setFirstName(rs.getString("firstName"));
                mechanic.setLastName(rs.getString("lastName"));
                mechanic.setEmail(rs.getString("email"));
                mechanic.setPhoneNumber(rs.getString("phoneNumber"));
                mechanic.setDOB(rs.getDate("DOB"));
                mechanic.setImg(rs.getString("img"));
                mechanic.setGender(rs.getBoolean("gender"));
                mechanic.setIDNo(rs.getString("IDNo"));
                mechanic.setAddress(rs.getString("address"));
                mechanic.setStartDate(rs.getDate("startDate"));
                mechanic.setCreatedBy(rs.getInt("createdBy"));
                mechanic.setCreatedOn(rs.getTimestamp("createdOn"));
                mechanic.setModifiedBy(rs.getInt("modifiedBy"));
                mechanic.setModifiedOn(rs.getTimestamp("modifiedOn"));

                listMechanicInfo.add(mechanic);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Bước 5: Xử lý lỗi cơ sở dữ liệu (nếu có)
        } finally {
            // Bước 6: Đóng tài nguyên cơ sở dữ liệu (kết quả, câu lệnh và kết nối)
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                // Xử lý lỗi khi đóng tài nguyên
            }
        }

        // Bước 7: Trả về danh sách thông tin thợ máy
        return listMechanicInfo;
    }

    /**
     * *************************************************************Client******************************************************************
     */
    //-----------------------------------------code-Hiếu-----------------------------------------------------------------
    //-----------------------------------------code-Hiếu-----------------------------------------------------------------
//    public static void main(String[] args){
//        EmployeeProfileDAO ed = new EmployeeProfileDAO();
//        EmployeeProfile ep = new EmployeeProfile();
//
//        try {
//            ep = ed.getEmployeeProfileByAccID(4);
//            ed.updateEmployeeProfile(4, null, null, null, null, null, null, null, null, null, null, 5);
//        } catch (SQLException ex) {
//            Logger.getLogger(EmployeeProfileDAO.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (ParseException ex) {
//            Logger.getLogger(EmployeeProfileDAO.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//        System.out.println(ep);
//    }

//<--------------------------------------------------  DUC MANH LAM ------------------------------------------------------------------------->
    public ArrayList<EmployeeProfile> getListEmployee(int pageSize, int quantityShow) {
        ArrayList<EmployeeProfile> list = new ArrayList<>();
        String sql = "SELECT ep.*, r.*\n"
                + "FROM EmployeeProfile ep, Account a, Role r\n"
                + "WHERE ep.employeeID = a.accID AND a.roleID = r.roleID\n"
                + "ORDER BY ep.employeeID\n"
                + "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY;";
        try {
            PreparedStatement statement = connection.prepareCall(sql);
            statement.setInt(1, (pageSize - 1) * quantityShow);
            statement.setInt(2, quantityShow);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Role role = new Role(rs.getInt("roleID"), rs.getString("roleName"));
                EmployeeProfile ep = new EmployeeProfile(rs.getInt("employeeID"), rs.getString("firstName"), rs.getString("lastName"), rs.getString("email"), rs.getString("phoneNumber"), rs.getDate("DOB"), rs.getString("img"), rs.getBoolean("gender"), rs.getString("IDNo"), rs.getString("address"), rs.getDate("startDate"), role);
                list.add(ep);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<EmployeeProfile> getListEmployeeSearch(String search, int pageSize, int quantityShow) {
        ArrayList<EmployeeProfile> list = new ArrayList<>();
        String sql = "SELECT ep.*, r.*\n"
                + "FROM EmployeeProfile ep, Account a, Role r\n"
                + "WHERE ep.employeeID = a.accID AND a.roleID = r.roleID\n"
                + "AND (ep.firstName LIKE ? OR ep.lastName LIKE ? OR ep.email LIKE ? OR ep.DOB LIKE ? "
                + "OR ep.phoneNumber LIKE ? OR ep.IDNo LIKE ? OR ep.address LIKE ?)\n"
                + "ORDER BY ep.employeeID\n"
                + "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY;";
        try {
            PreparedStatement statement = connection.prepareCall(sql);
            for (int i = 1; i < 8; i++) {
                statement.setString(i, "%" + search + "%");
            }
            statement.setInt(8, (pageSize - 1) * quantityShow);
            statement.setInt(9, quantityShow);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Role role = new Role(rs.getInt("roleID"), rs.getString("roleName"));
                EmployeeProfile ep = new EmployeeProfile(rs.getInt("employeeID"), rs.getString("firstName"), rs.getString("lastName"), rs.getString("email"), rs.getString("phoneNumber"), rs.getDate("DOB"), rs.getString("img"), rs.getBoolean("gender"), rs.getString("IDNo"), rs.getString("address"), rs.getDate("startDate"), role);
                list.add(ep);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<EmployeeProfile> getListEmpployeeFilter(String filterName, int pageSize, int quantityShow) {
        ArrayList<EmployeeProfile> list = new ArrayList<>();
        String sql = "SELECT ep.*, r.*\n"
                + "FROM EmployeeProfile ep, Account a, Role r\n"
                + "WHERE ep.employeeID = a.accID AND a.roleID = r.roleID AND ep.gender = ?\n"
                + "ORDER BY ep.employeeID\n"
                + "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY;";
        try {
            PreparedStatement statement = connection.prepareCall(sql);
            statement.setString(1, filterName);
            statement.setInt(2, (pageSize - 1) * quantityShow);
            statement.setInt(3, quantityShow);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Role role = new Role(rs.getInt("roleID"), rs.getString("roleName"));
                EmployeeProfile ep = new EmployeeProfile(rs.getInt("employeeID"), rs.getString("firstName"), rs.getString("lastName"), rs.getString("email"), rs.getString("phoneNumber"), rs.getDate("DOB"), rs.getString("img"), rs.getBoolean("gender"), rs.getString("IDNo"), rs.getString("address"), rs.getDate("startDate"), role);
                list.add(ep);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public int getTotalEmployee(String action, String value) {
        PreparedStatement stm = null;
        String sql = "SELECT count(*)\n"
                + "FROM EmployeeProfile ep\n";
        try {
            if (action.equals("search")) {
                sql += "WHERE ep.firstName LIKE ? OR ep.lastName LIKE ? OR ep.email LIKE ? OR ep.DOB LIKE ? OR ep.phoneNumber LIKE ? OR ep.IDNo LIKE ? OR ep.address LIKE ?;";
                stm = connection.prepareStatement(sql);
                for (int i = 1; i < 8; i++) {
                    stm.setString(i, "%" + value + "%");
                }
            } else if (action.equals("filter")) {
                sql += "WHERE ep.gender = ?;";
                stm = connection.prepareStatement(sql);
                stm.setString(1, value);
            } else {
                stm = connection.prepareStatement(sql);
            }
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void updateEmployeeById(int employeeID, String firstName, String lastName, String email, String phoneNumber, String DOB, String img, int gender, String IDNo, String address, String startDate, int modifiedBy, Timestamp modifiedOn, int roleId) {
        String sql = "UPDATE EmployeeProfile\n"
                + "SET firstName = ?,\n"
                + "    lastName = ?,\n"
                + "    email = ?,\n"
                + "    DOB = ?,\n"
                + "    phoneNumber = ?,\n"
                + "    img = ?,\n"
                + "    gender = ?,\n"
                + "    IDNo = ?,\n"
                + "    [address] = ?,\n"
                + "    startDate = ?,\n"
                + "    modifiedBy = ?,\n"
                + "    modifiedOn = ?\n"
                + "WHERE employeeID = ?;";

        String sql1 = "UPDATE [Account] \n"
                + "SET [roleID] = ?\n"
                + "WHERE accID = ?;";
        try {
            PreparedStatement statement = connection.prepareCall(sql);
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            statement.setString(3, email);
            statement.setString(4, DOB);
            statement.setString(5, phoneNumber);
            statement.setString(6, img);
            statement.setInt(7, gender);
            statement.setString(8, IDNo);
            statement.setString(9, address);
            statement.setString(10, startDate);
            statement.setInt(11, modifiedBy);
            statement.setTimestamp(12, modifiedOn);
            statement.setInt(13, employeeID);
            statement.executeUpdate();

            PreparedStatement statement1 = connection.prepareCall(sql1);
            statement1.setInt(1, roleId);
            statement1.setInt(2, employeeID);
            statement1.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public int insertEmployee(String firstName, String lastName, String email, String phoneNumber, String DOB, String img, int gender, String IDNo, String address, String startDate, int createdBy, String accountName, String password, int roleId) {
        String sql = " INSERT INTO [dbo].[Account]([accName],[password],[roleID],[email],[status]) VALUES(?,?,?,?,?)";
        int accID = 0;
        try {
            PreparedStatement statement = connection.prepareCall(sql);
            statement.setString(1, accountName);
            statement.setString(2, password);
            statement.setInt(3, roleId);
            statement.setString(4, email);
            statement.setInt(5, 0);
            statement.executeUpdate();

            String sql1 = "SELECT TOP 1 a.accID FROM Account a ORDER BY a.accID DESC;";
            PreparedStatement statement1 = connection.prepareCall(sql1);
            ResultSet rs = statement1.executeQuery();

            if (rs.next()) {
                accID = rs.getInt("accID");
                String sql2 = " SET IDENTITY_INSERT [EmployeeProfile] ON;"
                        + "     INSERT INTO [dbo].[EmployeeProfile]([employeeID],[firstName],[lastName],[email],[DOB],[phoneNumber],[img],[gender],[IDNo],[address],[startDate],[createdBy],[createdOn])\n"
                        + "     VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?);"
                        + "     SET IDENTITY_INSERT [EmployeeProfile] OFF;";
                PreparedStatement statement2 = connection.prepareCall(sql2);
                statement2.setInt(1, accID);
                statement2.setString(2, firstName);
                statement2.setString(3, lastName);
                statement2.setString(4, email);
                statement2.setString(5, DOB);
                statement2.setString(6, phoneNumber);
                statement2.setString(7, img);
                statement2.setInt(8, gender);
                statement2.setString(9, IDNo);
                statement2.setString(10, address);
                statement2.setString(11, startDate);
                statement2.setInt(12, createdBy);
                statement2.setTimestamp(13, new Timestamp(System.currentTimeMillis()));
                statement2.executeUpdate();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return accID;
    }

    public ArrayList<Role> getListRole() {
        ArrayList<Role> list = new ArrayList<>();
        try {
            String sql = "SELECT * FROM [Role]";
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Role role = new Role(rs.getInt("roleID"), rs.getString("roleName"));
                list.add(role);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String checkInsertEmployee(String accountName, String email, String phoneNumber, String idNo) {
        ConstantMessages constanMessage = new ConstantMessages();
        int check = 0;
        if (accountName == null || email == null || phoneNumber == null || idNo == null) {
            return constanMessage.EMPTY;
        }
        //check pattern email
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."
                + "[a-zA-Z0-9_+&*-]+)*@"
                + "(?:[a-zA-Z0-9-]+\\.)+[a-z"
                + "A-Z]{2,7}$";

        Pattern emailPatterm = Pattern.compile(emailRegex);

        if (!emailPatterm.matcher(email).matches()) {
            return constanMessage.PATTERN;
        }

        //check pattern phoneNumber
        if (!phoneNumber.matches("^0\\d{9}$")) {
            return constanMessage.PATTERN;
        }

        //check pattern CMND
        if (idNo.length() != 12) {
            return constanMessage.PATTERN;
        }

        for (char c : idNo.toCharArray()) {
            if (!Character.isDigit(c)) {
                return constanMessage.PATTERN;
            }
        }
        //check duplicate
        try {
            //check account
            String checkAccount = "SELECT Count(*) FROM Account a WHERE a.accName COLLATE Latin1_General_BIN LIKE ?;";
            PreparedStatement stm = connection.prepareStatement(checkAccount);
            stm.setString(1, accountName);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                check = rs.getInt(1);
            }
            if (check != 0) {
                return constanMessage.DUPALICATEACCOUNT;
            }

            String checkEmail = "SELECT Count(*) FROM EmployeeProfile ep WHERE ep.email COLLATE Latin1_General_BIN LIKE ?;";
            PreparedStatement stm1 = connection.prepareStatement(checkEmail);
            stm1.setString(1, email);
            ResultSet rs1 = stm1.executeQuery();
            if (rs1.next()) {
                check = rs1.getInt(1);
            }
            if (check != 0) {
                return constanMessage.DUPALICATEEMAIL;
            }

            String checkPhoneNumber = "SELECT Count(*) FROM EmployeeProfile ep WHERE ep.phoneNumber COLLATE Latin1_General_BIN LIKE ?;";
            PreparedStatement stm2 = connection.prepareStatement(checkPhoneNumber);
            stm2.setString(1, phoneNumber);
            ResultSet rs2 = stm2.executeQuery();
            if (rs2.next()) {
                check = rs2.getInt(1);
            }
            if (check != 0) {
                return constanMessage.DUPALICATEPHONE;
            }

            String checkIdNo = "SELECT Count(*) FROM EmployeeProfile ep WHERE ep.IDNo COLLATE Latin1_General_BIN LIKE ?;";
            PreparedStatement stm3 = connection.prepareStatement(checkIdNo);
            stm3.setString(1, idNo);
            ResultSet rs3 = stm3.executeQuery();
            if (rs3.next()) {
                check = rs3.getInt(1);
            }
            if (check != 0) {
                return constanMessage.DUPALICATEIDNO;
            }
        } catch (Exception e) {
        }
        return constanMessage.SUCCESS;
    }

//    public static void main(String[] args) {
//        EmployeeProfileDAO ed = new EmployeeProfileDAO();
//        System.out.println(ed.checkInsertEmployee("HongDang2", "Nuyenhongdang@gmail.com", "0123498348", "123456789012"));
//
//    }
}
