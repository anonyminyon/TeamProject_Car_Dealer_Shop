/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import model.Account;
import model.Car;
import model.CarOrder;
import model.Client;
import model.ObjectVoucher;
import model.Voucher;

public class CarOrderDAO extends DBContext {

    public void InsertCarOrder(String carorderCode, int clientID, int carID, boolean paymentType, long orderValue, long paid, long paymentRequired, String createdOn, String orderDate, String status, int voucherID) {
        try {
            // SQL insert statement
            String sql = "INSERT INTO [CarOrder] ([carorderCode], [clientID],[carID], [orderValue], [paid], [paymentRequired], [createdOn], [orderDate], [status], [voucherID], [paymentType]) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?,?,?)";

            // Create a prepared statement
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, carorderCode);
            stm.setInt(2, clientID);
            stm.setInt(3, carID);
            stm.setLong(4, orderValue);
            stm.setLong(5, paid);
            stm.setLong(6, paymentRequired);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            LocalDateTime createdOnDateTime = LocalDateTime.parse(createdOn, formatter);
            LocalDateTime orderDateDateTime = LocalDateTime.parse(orderDate, formatter);

            // Convert LocalDateTime to java.sql.Timestamp
            Timestamp createdOnTimestamp = Timestamp.valueOf(createdOnDateTime);
            Timestamp orderDateTimestamp = Timestamp.valueOf(orderDateDateTime);

            stm.setTimestamp(7, createdOnTimestamp);
            stm.setTimestamp(8, orderDateTimestamp);
            stm.setString(9, status);
            stm.setInt(10, voucherID);
            stm.setBoolean(11, paymentType);

            // Execute the insert statement
            stm.executeUpdate();

            // Xử lý thành công ở đây nếu cần
        } catch (SQLException e) {
            // Xử lý lỗi ở đây, ví dụ:
            e.printStackTrace();
            // Hoặc bạn có thể log lỗi hoặc thông báo cho người dùng
        }
    }

    public void InsertCarOrder2(String carorderCode, int clientID, int carID, boolean paymentType, long orderValue, long paid, long paymentRequired, String createdOn, String orderDate, String status) {
        try {
            // SQL insert statement
            String sql = "INSERT INTO [CarOrder] ([carorderCode], [clientID],[carID], [orderValue], [paid], [paymentRequired], [createdOn], [orderDate], [status], [paymentType]) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?,?)";

            // Create a prepared statement
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, carorderCode);
            stm.setInt(2, clientID);
            stm.setInt(3, carID);
            stm.setLong(4, orderValue);
            stm.setLong(5, paid);
            stm.setLong(6, paymentRequired);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            LocalDateTime createdOnDateTime = LocalDateTime.parse(createdOn, formatter);
            LocalDateTime orderDateDateTime = LocalDateTime.parse(orderDate, formatter);

            // Convert LocalDateTime to java.sql.Timestamp
            Timestamp createdOnTimestamp = Timestamp.valueOf(createdOnDateTime);
            Timestamp orderDateTimestamp = Timestamp.valueOf(orderDateDateTime);

            stm.setTimestamp(7, createdOnTimestamp);
            stm.setTimestamp(8, orderDateTimestamp);
            stm.setString(9, status);
            stm.setBoolean(10, paymentType);

            // Execute the insert statement
            stm.executeUpdate();

            // Xử lý thành công ở đây nếu cần
        } catch (SQLException e) {
            // Xử lý lỗi ở đây, ví dụ:
            e.printStackTrace();
            // Hoặc bạn có thể log lỗi hoặc thông báo cho người dùng
        }
    }

    public List<CarOrder> getListCarOrderByEmail(String email) {
        List<CarOrder> list = new ArrayList<>();
        try {
            String query = "SELECT co.*, c.clientName, car.carName\n"
                    + "FROM CarOrder co\n"
                    + "JOIN Client c ON co.clientID = c.clientID\n"
                    + "JOIN Car car ON co.carID = car.carID\n"
                    + "WHERE email = ?\n"
                    + "ORDER BY co.carorderID DESC";
            PreparedStatement stm = connection.prepareStatement(query);
            stm.setString(1, email);
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                java.sql.Timestamp timestampCreatedOn = rs.getTimestamp("createdOn");
                LocalDateTime createdOn = timestampCreatedOn.toLocalDateTime();
                int carID = rs.getInt("carID");
                String carName = rs.getString("carName");
                int clientID = rs.getInt("clientID");
                String clientName = rs.getString("clientName");
                CarOrder carOrder = new CarOrder(rs.getInt("carorderID"), rs.getString("carorderCode"), new Client(clientID, clientName), new Car(carID, carName), rs.getBoolean("paymentType"), rs.getLong("orderValue"), rs.getLong("paid"), rs.getLong("paymentRequired"), createdOn,  rs.getString("status"));
                list.add(carOrder);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /*-------------------------------------------------------------------CarOderList-------------------------------------------------------------------------*/
    public List<CarOrder> getListCarOrderBySearch(int index, String search, int quantityShow, String status, String paymentType, String startDate, String endDate) {
        List<CarOrder> list = new ArrayList<>();
        String query = "SELECT co.*, c.clientName, car.carName, v.voucherCode,\n"
                + "        ma.accName AS modifiedByAccName\n"
                + "FROM CarOrder co\n"
                + "JOIN Client c ON co.clientID = c.clientID\n"
                + "JOIN Car car ON co.carID = car.carID\n"
                + "LEFT JOIN Voucher v ON co.voucherID = v.voucherID\n"
                + "LEFT JOIN Account ma ON co.modifiedBy = ma.accID\n"
                + "WHERE (";

        String[] keywords = search.split("\\s+"); // Tách các từ khóa trong title

        for (int i = 0; i < keywords.length; i++) {
            query += "c.clientName LIKE ? OR co.carorderCode LIKE ?";
            if (i < keywords.length - 1) {
                query += " OR ";
            }
        }

        query += ")";

        if (!"".equals(status)) {
            query += " AND co.status = ?";
        }

        if (!"".equals(paymentType)) {
            query += " AND co.paymentType = ?";
        }

        if (!"".equals(startDate)) {
            query += " AND CONVERT(DATE, co.orderDate, 105) >= CONVERT(DATE, ?, 105)";
        }

        if (!"".equals(endDate)) {
            query += " AND CONVERT(DATE, co.orderDate, 105) <= CONVERT(DATE, ?, 105)";
        }

        query += " ORDER BY co.carorderID DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        try {
            PreparedStatement stm = connection.prepareStatement(query);
            for (int i = 0; i < keywords.length; i++) {
                stm.setString(i * 2 + 1, "%" + keywords[i] + "%");
                stm.setString(i * 2 + 2, "%" + keywords[i] + "%");
            }
            int position = keywords.length * 2;

            if (!"".equals(status)) {
                position += 1;
                stm.setString(position, status);
            }

            if (!"".equals(paymentType)) {
                position += 1;
                stm.setString(position, paymentType);
            }

            if (!"".equals(startDate)) {
                position += 1;
                stm.setString(position, startDate);
            }
            if (!"".equals(endDate)) {
                position += 1;
                stm.setString(position, endDate);
            }

            stm.setInt(position + 1, (index - 1) * quantityShow);
            stm.setInt(position + 2, quantityShow);

            ResultSet rs = stm.executeQuery();
            while (rs.next()) {

                java.sql.Timestamp timestampCreatedOn = rs.getTimestamp("createdOn");
                LocalDateTime createdOn = timestampCreatedOn.toLocalDateTime();

                java.sql.Timestamp timestamporderDate = rs.getTimestamp("orderDate");
                LocalDateTime orderDate = timestamporderDate.toLocalDateTime();

                int modifiedBy = rs.getInt("modifiedBy");
                java.sql.Timestamp timestampModifiedOn = rs.getTimestamp("modifiedOn");

                String modifiedByAccName = rs.getString("modifiedByAccName");

                int carID = rs.getInt("carID");
                String carName = rs.getString("carName");
                int clientID = rs.getInt("clientID");
                String clientName = rs.getString("clientName");
                String voucherCode = rs.getString("voucherCode");
                if (timestampModifiedOn != null) {
                    LocalDateTime modifiedOn = timestampModifiedOn.toLocalDateTime();
                    CarOrder co = new CarOrder(rs.getInt("carorderID"), rs.getString("carorderCode"), new Client(clientID, clientName), new Car(carID, carName), rs.getBoolean("paymentType"), rs.getLong("orderValue"), rs.getLong("paid"), rs.getLong("paymentRequired"), createdOn, orderDate, modifiedOn, new Account(modifiedBy, modifiedByAccName), rs.getString("status"), new Voucher(rs.getInt("voucherID"), voucherCode));
                    list.add(co);
                } else {
                    CarOrder co = new CarOrder(rs.getInt("carorderID"), rs.getString("carorderCode"), new Client(clientID, clientName), new Car(carID, carName), rs.getBoolean("paymentType"), rs.getLong("orderValue"), rs.getLong("paid"), rs.getLong("paymentRequired"), createdOn, orderDate, new Account(modifiedBy, modifiedByAccName), rs.getString("status"), new Voucher(rs.getInt("voucherID"), voucherCode));
                    list.add(co);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Xử lý lỗi SQLException bằng cách in stack trace
        }
        return list;
    }

    public int getTotalCarOrderBySearch(String search, String status, String paymentType, String start_Date, String end_Date) {
        String query = "SELECT COUNT(co.carorderID) FROM CarOrder co "
                + "JOIN Client c ON co.clientID = c.clientID "
                + "WHERE (";

        String[] keywords = search.split("\\s+");

        for (int i = 0; i < keywords.length; i++) {
            query += "c.clientName LIKE ? OR co.carorderCode LIKE ?";
            if (i < keywords.length - 1) {
                query += " OR ";
            }
        }

        query += ")";

        if (!"".equals(status)) {
            query += " AND co.status = ?";
        }

        if (!"".equals(paymentType)) {
            query += " AND co.paymentType = ?";
        }

        if (!"".equals(start_Date)) {
            query += " AND CONVERT(DATE, co.createdOn, 105) >= CONVERT(DATE, ?, 105)";
        }

        if (!"".equals(end_Date)) {
            query += " AND CONVERT(DATE, co.createdOn, 105) <= CONVERT(DATE, ?, 105)";
        }

        try {
            PreparedStatement stm = connection.prepareStatement(query);
            for (int i = 0; i < keywords.length; i++) {
                stm.setString(i * 2 + 1, "%" + keywords[i] + "%");
                stm.setString(i * 2 + 2, "%" + keywords[i] + "%");
            }
            int position = keywords.length * 2;

            if (!"".equals(status)) {
                position += 1;
                stm.setString(position, status);
            }

            if (!"".equals(paymentType)) {
                position += 1;
                stm.setString(position, paymentType);
            }

            if (!"".equals(start_Date)) {
                position += 1;
                stm.setString(position, start_Date);
            }
            if (!"".equals(end_Date)) {
                position += 1;
                stm.setString(position, end_Date);
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

    public void updateCarOrder(String modifiedOn, int modifiedBy, int status, int carorderID) {

        try {
            String query = "UPDATE CarOrder\n"
                    + "SET modifiedOn = ?,\n"
                    + "    status = ?,\n"
                    + "    modifiedBy = ?\n"
                    + "WHERE carorderID = ?;";
            PreparedStatement stm = connection.prepareStatement(query);
            stm.setString(1, modifiedOn);
            stm.setInt(2, status);
            stm.setInt(3, modifiedBy);
            stm.setInt(4, carorderID);
            stm.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateCarOrder2(String modifiedOn, int modifiedBy, long paymentRequired, long paid, int status, int carorderID) {

        try {
            String query = "UPDATE CarOrder\n"
                    + "SET modifiedOn = ?,\n"
                    + "    status = ?,\n"
                    + "    modifiedBy = ?,\n"
                    + "    paid = ?,\n"
                    + "    paymentRequired = ?\n"
                    + "WHERE carorderID = ?;";
            PreparedStatement stm = connection.prepareStatement(query);
            stm.setString(1, modifiedOn);
            stm.setInt(2, status);
            stm.setInt(3, modifiedBy);
            stm.setLong(5, paymentRequired);
            stm.setLong(4, paid);
            stm.setInt(6, carorderID);
            stm.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        CarOrderDAO co = new CarOrderDAO();
        List<CarOrder> d = co.getListCarOrderByEmail("daoanhduc3000@gmail.com");
        System.out.println(d);
//        co.updateCarOrder2("", 2, 1103000000, 1, 12);
//        List<CarOrder> d = co.getListCarOrderBySearch(1, "", 20, "", "", "", "");
//        for (CarOrder c : d) {
//            System.out.println(c);
//
    }
//        int total = co.getTotalCarOrderBySearch("", "0", "", "");
//        System.out.println(total);
}
