/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import model.Client;

/**
 *
 * @author Admin
 */
public class ClientDAO extends DBContext {

    //-----------------------------------------code-Hiếu-----------------------------------------------------------------
    public ArrayList<Client> getListClient(String search, int pageSize, int quantityShow) {
        ArrayList<Client> list = new ArrayList<>();
        String sql = "SELECT *\n"
                + "FROM Client c\n"
                + "WHERE c.clientName LIKE ? OR c.clientID LIKE ? OR c.email LIKE ? OR c.phoneNumber LIKE ? OR c.gender LIKE ? OR c.dob LIKE ?\n"
                + "ORDER BY c.clientID\n"
                + "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY;";
        try {
            PreparedStatement statement = connection.prepareCall(sql);
            for (int i = 1; i < 7; i++) {
                statement.setString(i, "%" + search + "%");
            }
            statement.setInt(7, (pageSize - 1) * quantityShow);
            statement.setInt(8, quantityShow);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Client client = new Client(rs.getInt("clientID"), rs.getString("clientName"), rs.getString("email"), rs.getString("phoneNumber"), rs.getString("dob"), rs.getBoolean("gender"), rs.getString("noID"));
                list.add(client);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //-----------------------------------------code-Hiếu-----------------------------------------------------------------
    public int getTotalClient(String search) {
        try {
            String sql = "SELECT count(*)\n"
                    + "FROM Client c\n"
                    + "WHERE c.clientName LIKE ? OR c.clientID LIKE ? OR c.email LIKE ? OR c.phoneNumber LIKE ? OR c.gender LIKE ? OR c.dob LIKE ?;";
            PreparedStatement stm = connection.prepareStatement(sql);
            for (int i = 1; i < 7; i++) {
                stm.setString(i, "%" + search + "%");
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

    //----------------------------------phần-của-hiếu-làm-serviceForm-------------------------------------------------------
    //hàm này khi đưa string null hoặc empty vào param thì nó sẽ không tạo query để insert dữ liệu đó vào bang client
    public void AddANewClient(String clientName, String email, String phoneNumber, String dateOfBirth, String gender) {
        // Create a map to store the field names and their corresponding values
        LinkedHashMap<String, Object> data = new LinkedHashMap<>();

        // Check and convert each input parameter to the appropriate data type
        if (clientName != null && !clientName.isEmpty()) {
            data.put("clientName", clientName);
        }
        if (email != null && !email.isEmpty()) {
            data.put("email", email);
        }
        if (phoneNumber != null && !phoneNumber.isEmpty()) {
            data.put("phoneNumber", phoneNumber);
        }
        if (dateOfBirth != null && !dateOfBirth.isEmpty()) {
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // Adjust the date format as needed
                java.util.Date parsedDate = dateFormat.parse(dateOfBirth);
                data.put("dob", new Date(parsedDate.getTime()));
            } catch (ParseException e) {
                // Handle the date parsing error if necessary
            }
        }
        if (gender != null && !gender.isEmpty()) {
            data.put("gender", Boolean.parseBoolean(gender));
        }

        // Check if there is any valid data to insert
        if (data.isEmpty()) {
            // No valid data to insert, so return null
            return;
        }

        // Construct the SQL INSERT query using the data map
        StringBuilder insertQuery = new StringBuilder("INSERT INTO Client (");
        StringBuilder values = new StringBuilder(" VALUES (");

        for (String field : data.keySet()) {
            insertQuery.append(field).append(", ");
            values.append("?, ");
        }

        // Remove the trailing comma and space from the insertQuery and values
        //insertQuery.delete(startIndex, endIndex)
        insertQuery.delete(insertQuery.length() - 2, insertQuery.length());
        values.delete(values.length() - 2, values.length());

        insertQuery.append(")").append(values).append(")");

        //add value to "?"
        try ( PreparedStatement preparedStatement = connection.prepareStatement(insertQuery.toString())) {
            int parameterIndex = 1;
            for (Object value : data.values()) {
                if (value instanceof String) {
                    preparedStatement.setString(parameterIndex, (String) value);
                } else if (value instanceof Boolean) {
                    preparedStatement.setBoolean(parameterIndex, (Boolean) value);
                } else if (value instanceof Date) {
                    preparedStatement.setDate(parameterIndex, (Date) value);
                }
                parameterIndex++;
            }

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            // Handle any potential errors here
            e.printStackTrace();
        }

        //return insertQuery.toString();
    }

    //-----------------------------------------code-Hiếu-----------------------------------------------------------------
    public Client getClient(String clientName, String email, String phoneNumber, String dateOfBirth, String gender) {
        // Create a map to store the search criteria
        LinkedHashMap<String, Object> criteria = new LinkedHashMap<>();

        // Check and convert each input parameter to the appropriate data type
        if (clientName != null && !clientName.isEmpty()) {
            criteria.put("clientName", clientName);
        }
        if (email != null && !email.isEmpty()) {
            criteria.put("email", email);
        }
        if (phoneNumber != null && !phoneNumber.isEmpty()) {
            criteria.put("phoneNumber", phoneNumber);
        }
        if (dateOfBirth != null && !dateOfBirth.isEmpty()) {
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // Adjust the date format as needed
                java.util.Date parsedDate = dateFormat.parse(dateOfBirth);
                criteria.put("dob", new Date(parsedDate.getTime()));
            } catch (ParseException e) {
                // Handle the date parsing error if necessary
            }
        }
        if (gender != null && !gender.isEmpty()) {
            criteria.put("gender", Boolean.parseBoolean(gender));
        }

        // Check if there is any valid criteria to search for
        if (criteria.isEmpty()) {
            // No valid criteria to search for, so return null or an empty list, depending on your implementation
            return null;
        }

        // Construct the SQL SELECT query using the criteria map
        StringBuilder selectQuery = new StringBuilder("SELECT * FROM Client WHERE ");

        for (String field : criteria.keySet()) {
            selectQuery.append(field).append(" = ? AND ");
        }

        // Remove the trailing "AND " from the selectQuery
        selectQuery.delete(selectQuery.length() - 5, selectQuery.length());

        // Execute the SQL query to search for the client
        try ( PreparedStatement preparedStatement = connection.prepareStatement(selectQuery.toString())) {
            int parameterIndex = 1;
            for (Object value : criteria.values()) {
                if (value instanceof String) {
                    preparedStatement.setString(parameterIndex, (String) value);
                } else if (value instanceof Boolean) {
                    preparedStatement.setBoolean(parameterIndex, (Boolean) value);
                } else if (value instanceof Date) {
                    preparedStatement.setDate(parameterIndex, (Date) value);
                }
                parameterIndex++;
            }

            ResultSet resultSet = preparedStatement.executeQuery();

            Client client = null;

            if (resultSet.next()) {
                client = new Client();
                client.setClientID(resultSet.getInt("clientID"));
                client.setClientName(resultSet.getString("clientName"));
                client.setEmail(resultSet.getString("email"));
                client.setPhoneNumber(resultSet.getString("phoneNumber"));
                // Format the date as "yyyy-MM-dd"
                Date temp = resultSet.getDate("dob");
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String dateOfBirthDB = dateFormat.format(temp);
                client.setDateOfBrith(dateOfBirthDB);
                client.setGender(resultSet.getBoolean("gender"));
                return client;
            }

        } catch (SQLException e) {
            // Handle any potential errors here
            e.printStackTrace();
        }

        return null; // Return null if no matching client is found
    }

    //-----------------------------------------code-Hiếu-----------------------------------------------------------------
    public int getClientID(String clientName, String email, String phoneNumber, String dateOfBirth, String gender) {
        // Create a map to store the search criteria
        LinkedHashMap<String, Object> criteria = new LinkedHashMap<>();

        // Check and convert each input parameter to the appropriate data type
        if (clientName != null && !clientName.isEmpty()) {
            criteria.put("clientName", clientName);
        }
        if (email != null && !email.isEmpty()) {
            criteria.put("email", email);
        }
        if (phoneNumber != null && !phoneNumber.isEmpty()) {
            criteria.put("phoneNumber", phoneNumber);
        }
        if (dateOfBirth != null && !dateOfBirth.isEmpty()) {
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // Adjust the date format as needed
                java.util.Date parsedDate = dateFormat.parse(dateOfBirth);
                criteria.put("dob", new Date(parsedDate.getTime()));
            } catch (ParseException e) {
                // Handle the date parsing error if necessary
            }
        }
        if (gender != null && !gender.isEmpty()) {
            criteria.put("gender", Boolean.parseBoolean(gender));
        }

        // Check if there is any valid criteria to search for
        if (criteria.isEmpty()) {
            // No valid criteria to search for, so return -1 or another suitable value
            return -1;
        }

        // Construct the SQL SELECT query using the criteria map
        StringBuilder selectQuery = new StringBuilder("SELECT clientID FROM Client WHERE ");

        for (String field : criteria.keySet()) {
            selectQuery.append(field).append(" = ? AND ");
        }

        // Remove the trailing "AND " from the selectQuery
        selectQuery.delete(selectQuery.length() - 5, selectQuery.length());

        // Execute the SQL query to search for the clientID
        try ( PreparedStatement preparedStatement = connection.prepareStatement(selectQuery.toString())) {
            int parameterIndex = 1;
            for (Object value : criteria.values()) {
                if (value instanceof String) {
                    preparedStatement.setString(parameterIndex, (String) value);
                } else if (value instanceof Boolean) {
                    preparedStatement.setBoolean(parameterIndex, (Boolean) value);
                } else if (value instanceof Date) {
                    preparedStatement.setDate(parameterIndex, (Date) value);
                }
                parameterIndex++;
            }

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("clientID");
            }

        } catch (SQLException e) {
            // Handle any potential errors here
            e.printStackTrace();
        }

        return -1; // Return -1 if no matching clientID is found
    }

    //----------------------------------phần-của-hiếu-làm-serviceForm-------------------------------------------------------
    //<--------------------------------------------------  DUC LAM ------------------------------------------------------------------------->
    public boolean checkEmailExist(String email) {
        String query = "SELECT 1 FROM Client WHERE email = ?";
        try {
            PreparedStatement stm = connection.prepareStatement(query);
            stm.setString(1, email);
            ResultSet rs = stm.executeQuery();
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean checknoIDExist(String noID) {
        String query = "SELECT 1 FROM Client WHERE noID = ?";
        try {
            PreparedStatement stm = connection.prepareStatement(query);
            stm.setString(1, noID);
            ResultSet rs = stm.executeQuery();
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean checkPhoneExist(String phoneNumber) {
        String query = "SELECT 1 FROM Client WHERE phoneNumber = ?";
        try {
            PreparedStatement stm = connection.prepareStatement(query);
            stm.setString(1, phoneNumber);
            ResultSet rs = stm.executeQuery();
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public int getClientIDByEmail(String email) {
        String query = "SELECT clientID FROM Client WHERE email = ?";
        try {
            PreparedStatement stm = connection.prepareStatement(query);
            stm.setString(1, email);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getInt("clientID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public String getClientNameByClientID(int clientID) {
        String query = "SELECT clientName FROM Client WHERE clientID = ?";
        try {
            PreparedStatement stm = connection.prepareStatement(query);
            stm.setInt(1, clientID);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getString("clientName");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getEmailByClientID(int clientID) {
        String query = "SELECT email FROM Client WHERE clientID = ?";
        try {
            PreparedStatement stm = connection.prepareStatement(query);
            stm.setInt(1, clientID);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getString("email");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int insertClientAndGetID(String clientName, String email, String phoneNumber, String noID) {
        try {
            String insertSQL = "INSERT INTO [Client] ([clientName], [email],[phoneNumber], [noID]) VALUES (?, ?, ?, ?)";

            // Tạo một prepared statement để chèn thông tin khách hàng
            PreparedStatement insertStatement = connection.prepareStatement(insertSQL);
            insertStatement.setString(1, clientName);
            insertStatement.setString(2, email);
            insertStatement.setString(3, phoneNumber);
            insertStatement.setString(4, noID);

            // Thực hiện truy vấn chèn
            insertStatement.executeUpdate();

            // Sau khi chèn thông tin khách hàng thành công, lấy ID của khách hàng bằng email
            String selectSQL = "SELECT [clientID] FROM [Client] WHERE [email] = ?";
            PreparedStatement selectStatement = connection.prepareStatement(selectSQL);
            selectStatement.setString(1, email);

            ResultSet resultSet = selectStatement.executeQuery();
            if (resultSet.next()) {
                int clientID = resultSet.getInt("clientID");
                // Sử dụng clientID ở đây cho mục đích của bạn
                return clientID;
            }

        } catch (SQLException e) {
            // Xử lý lỗi ở đây, ví dụ:
            e.printStackTrace();
            // Hoặc bạn có thể log lỗi hoặc thông báo cho người dùng
        }
        return 0;
    }

    public int getClientID(String clientName, String email, String phoneNumber, String dateOfBirth, String gender, String noID) {
        // Create a map to store the search criteria
        LinkedHashMap<String, Object> criteria = new LinkedHashMap<>();

        // Check and convert each input parameter to the appropriate data type
        if (clientName != null && !clientName.isEmpty()) {
            criteria.put("clientName", clientName);
        }
        if (email != null && !email.isEmpty()) {
            criteria.put("email", email);
        }
        if (phoneNumber != null && !phoneNumber.isEmpty()) {
            criteria.put("phoneNumber", phoneNumber);
        }
        if (dateOfBirth != null && !dateOfBirth.isEmpty()) {
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // Adjust the date format as needed
                java.util.Date parsedDate = dateFormat.parse(dateOfBirth);
                criteria.put("dob", new Date(parsedDate.getTime()));
            } catch (ParseException e) {
                // Handle the date parsing error if necessary
            }
        }
        if (gender != null && !gender.isEmpty()) {
            criteria.put("gender", Boolean.parseBoolean(gender));
        }

        if (noID != null && !noID.isEmpty()) {
            criteria.put("noID", noID);
        }

        // Check if there is any valid criteria to search for
        if (criteria.isEmpty()) {
            // No valid criteria to search for, so return -1 or another suitable value
            return -1;
        }

        // Construct the SQL SELECT query using the criteria map
        StringBuilder selectQuery = new StringBuilder("SELECT clientID FROM Client WHERE ");

        for (String field : criteria.keySet()) {
            selectQuery.append(field).append(" = ? AND ");
        }

        // Remove the trailing "AND " from the selectQuery
        selectQuery.delete(selectQuery.length() - 5, selectQuery.length());

        // Execute the SQL query to search for the clientID
        try ( PreparedStatement preparedStatement = connection.prepareStatement(selectQuery.toString())) {
            int parameterIndex = 1;
            for (Object value : criteria.values()) {
                if (value instanceof String) {
                    preparedStatement.setString(parameterIndex, (String) value);
                } else if (value instanceof Boolean) {
                    preparedStatement.setBoolean(parameterIndex, (Boolean) value);
                } else if (value instanceof Date) {
                    preparedStatement.setDate(parameterIndex, (Date) value);
                }
                parameterIndex++;
            }

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("clientID");
            }

        } catch (SQLException e) {
            // Handle any potential errors here
            e.printStackTrace();
        }

        return -1; // Return -1 if no matching clientID is found
    }

    //----------------------------------phần-của-hiếu-làm-serviceForm-------------------------------------------------------
    public void AddANewClient(String clientName, String email, String phoneNumber, String dateOfBirth, String gender, String noID) {
        // Create a map to store the field names and their corresponding values
        LinkedHashMap<String, Object> data = new LinkedHashMap<>();

        // Check and convert each input parameter to the appropriate data type
        if (clientName != null && !clientName.isEmpty()) {
            data.put("clientName", clientName);
        }
        if (email != null && !email.isEmpty()) {
            data.put("email", email);
        }
        if (phoneNumber != null && !phoneNumber.isEmpty()) {
            data.put("phoneNumber", phoneNumber);
        }
        if (dateOfBirth != null && !dateOfBirth.isEmpty()) {
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // Adjust the date format as needed
                java.util.Date parsedDate = dateFormat.parse(dateOfBirth);
                data.put("dob", new Date(parsedDate.getTime()));
            } catch (ParseException e) {
                // Handle the date parsing error if necessary
            }
        }
        if (gender != null && !gender.isEmpty()) {
            data.put("gender", Boolean.parseBoolean(gender));
        }

        if (noID != null && !noID.isEmpty()) {
            data.put("noID", noID);
        }

        // Check if there is any valid data to insert
        if (data.isEmpty()) {
            // No valid data to insert, so return null
            return;
        }

        // Construct the SQL INSERT query using the data map
        StringBuilder insertQuery = new StringBuilder("INSERT INTO Client (");
        StringBuilder values = new StringBuilder(" VALUES (");

        for (String field : data.keySet()) {
            insertQuery.append(field).append(", ");
            values.append("?, ");
        }

        // Remove the trailing comma and space from the insertQuery and values
        //insertQuery.delete(startIndex, endIndex)
        insertQuery.delete(insertQuery.length() - 2, insertQuery.length());
        values.delete(values.length() - 2, values.length());

        insertQuery.append(")").append(values).append(")");

        //add value to "?"
        try ( PreparedStatement preparedStatement = connection.prepareStatement(insertQuery.toString())) {
            int parameterIndex = 1;
            for (Object value : data.values()) {
                if (value instanceof String) {
                    preparedStatement.setString(parameterIndex, (String) value);
                } else if (value instanceof Boolean) {
                    preparedStatement.setBoolean(parameterIndex, (Boolean) value);
                } else if (value instanceof Date) {
                    preparedStatement.setDate(parameterIndex, (Date) value);
                }
                parameterIndex++;
            }

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            // Handle any potential errors here
            e.printStackTrace();
        }

        //return insertQuery.toString();
    }

    public Client getClientByEmail(String email) {
        String query = "SELECT * FROM Client WHERE email = ?";
        try {
            PreparedStatement stm = connection.prepareStatement(query);
            stm.setString(1, email);
            ResultSet rs = stm.executeQuery();
            Client client = null;
            if (rs.next()) {
                client = new Client();
                Date temp = rs.getDate("dob");
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                if (temp!= null) {
                    String dateOfBirthDB = dateFormat.format(temp);

                    client.setClientID(rs.getInt("clientID"));
                    client.setClientName(rs.getString("clientName"));
                    client.setEmail(rs.getString("email"));
                    client.setPhoneNumber(rs.getString("phoneNumber"));
                    // Format the date as "yyyy-MM-dd"

                    client.setDateOfBrith(dateOfBirthDB);
                    client.setGender(rs.getBoolean("gender"));
                    client.setNoID(rs.getString("noID"));
                    return client;
                } else {
                    client.setClientID(rs.getInt("clientID"));
                    client.setClientName(rs.getString("clientName"));
                    client.setEmail(rs.getString("email"));
                    client.setPhoneNumber(rs.getString("phoneNumber"));
                    client.setGender(rs.getBoolean("gender"));
                    client.setNoID(rs.getString("noID"));
                    return client;
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateProfile(String email, String name, boolean gender , String dob ){
       try {
            String query = "UPDATE Client\n"
                    + "SET clientName = ?,\n"
                    + "    gender = ?,\n"
                    + "    dob = CONVERT(DATE, ?, 103)\n"
                    + "WHERE email = ?;";
            PreparedStatement stm = connection.prepareStatement(query);
            stm.setString(1, name);
            stm.setBoolean(2, gender);
            stm.setString(3, dob);
            stm.setString(4, email);
            stm.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {

        // Declare clientID here
        ClientDAO dao = new ClientDAO();
//        String email = "daoanhduc3000@gmail.com";
//        String email = "ducdahe171808@fpt.edu.vn";
        dao.updateProfile("daoanhduc3000@gmail.com", "Đào Anh Đức", false, "10-21-2003");
        
//        Client c = dao.getClientByEmail(email);
//        System.out.println(c);
        // Now you can use clientID here
//        ArrayList<Client> list = dao.getListClient("", 1, 10);
//        System.out.println(list);
//        System.out.println(dao.getTotalClient());

        //test AddANewClient
//        // Initialize your variables 
//        String clientName = "";
//        String email = "";
//        String phoneNumber = "";
//        String dateOfBirth = null; // Ensure it's in the correct date format
//        String gender = ""; // "true" or "false" as strings
//        ClientDAO cd = new ClientDAO();
//        System.out.println(cd.AddANewClient(clientName, email, phoneNumber, dateOfBirth, gender));
        //text AddClientNumberPlateByClientID
//        cd.AddClientNumberPlateByClientID(0, "numberPlate");
//        Client c = cd.getClient("John Doe", null, "123-456-7890", null, null);
//        System.out.println(c.toString());
    }

}
