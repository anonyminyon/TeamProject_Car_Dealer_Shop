package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import model.Account;
import model.ObjectVoucher;
import model.Voucher;

public class VoucherDAO extends DBContext {

    public List<ObjectVoucher> getAllOV() {
        List<ObjectVoucher> list = new ArrayList<>();
        String query = "select * from ObjectVoucher\n";
        try {
            PreparedStatement stm = connection.prepareStatement(query);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                ObjectVoucher o = new ObjectVoucher(rs.getInt("objectVoucherID"), rs.getString("objectVoucher"));
                list.add(o);
            }
        } catch (Exception e) {

        }
        return list;
    }

    public boolean checkCodeExist(String voucherCode) {
        String query = "SELECT 1 FROM Voucher WHERE voucherCode = ?";
        try {
            PreparedStatement stm = connection.prepareStatement(query);
            stm.setString(1, voucherCode);
            ResultSet rs = stm.executeQuery();
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public Voucher getVoucherByVoucherCode(String voucherCode) {
        String query = "SELECT voucherID, objectVoucherID, discountType, discountValue, voucherCode, usedCount FROM Voucher WHERE voucherCode = ? And status = 1 And GETDATE() < endDate  AND (maxUsage - usedCount) > 0;";
        try {
            PreparedStatement stm = connection.prepareStatement(query);
            stm.setString(1, voucherCode);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                int objectVoucherID = rs.getInt("objectVoucherID");
                boolean discountType = rs.getBoolean("discountType");
                float discountValue = rs.getFloat("discountValue");
                int usedCount = rs.getInt("usedCount");
                int voucherID = rs.getInt("voucherID");
                Voucher a = new Voucher(voucherCode, new ObjectVoucher(objectVoucherID), discountType, discountValue, usedCount, voucherID);
                return a;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getUsedCountByVoucherCode(String voucherCode) {
        try {
            String sql = "SELECT usedCount\n"
                    + "FROM Voucher \n"
                    + "Where voucherCode = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, voucherCode); // Set the voucherCode parameter
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void updateUsageCount(String voucherCode, int usedCount) {
        try {
            // SQL insert statement
            String sql = "UPDATE Voucher\n"
                    + "SET usedCount = ?\n"
                    + "WHERE voucherCode = ?;";

            // Create a prepared statement
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, usedCount);
            stm.setString(2, voucherCode);

            // Execute the insert statement
            stm.executeUpdate();

            // Handle success or error here
        } catch (SQLException e) {
            // Handle any errors that may occur
            e.printStackTrace();
            // Handle the error here
        }
    }

    public void InsertVoucher(String voucherCode, String description, String objectVoucher, String discountType, String discountValue, int maxUsage, String createdOn, int createdBy, String startDate, String endDate) {
        try {
            // SQL insert statement
            String sql = "INSERT INTO [Voucher] ([voucherCode], [description], [status], [objectVoucherID], [discountType], [discountValue], [maxUsage], [usedCount], [createdOn], [createdBy], [startDate], [endDate]) VALUES (?, ?, 1, ?, ?, ?, ?, 0, ?, ?, ? , ?)";

            // Create a prepared statement
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, voucherCode);
            stm.setString(2, description);
            stm.setString(3, objectVoucher);
            stm.setString(4, discountType);
            stm.setString(5, discountValue);
            stm.setInt(6, maxUsage);
            stm.setString(7, createdOn);
            stm.setInt(8, createdBy);
            stm.setString(9, startDate);
            stm.setString(10, endDate);
            // Execute the insert statement
            stm.executeUpdate();

            // Xử lý thành công ở đây nếu cần
        } catch (SQLException e) {
            // Xử lý lỗi ở đây, ví dụ:
            e.printStackTrace();
            // Hoặc bạn có thể log lỗi hoặc thông báo cho người dùng
        }
    }

    public void updateVoucher(String modifiedOn, int modifiedBy, boolean status, int voucherID) {

        try {
            String query = "UPDATE Voucher\n"
                    + "SET modifiedOn = ?,\n"
                    + "    status = ?,\n"
                    + "    modifiedBy = ?\n"
                    + "WHERE voucherID = ?;";
            PreparedStatement stm = connection.prepareStatement(query);
            stm.setString(1, modifiedOn);
            stm.setBoolean(2, status);
            stm.setInt(3, modifiedBy);
            stm.setInt(4, voucherID);
            stm.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Voucher> getListVoucherBySearch(int index, String search, int quantityShow, String status, String object, String discountType, String start_Date, String end_Date) {
        List<Voucher> list = new ArrayList<>();
        String query = "SELECT v.*, ov.objectVoucher,\n"
                + "       ca.accName AS createdByAccName, \n"
                + "       ma.accName AS modifiedByAccName\n"
                + "FROM Voucher v\n"
                + "FULL JOIN Account ca ON v.createdBy = ca.accID\n"
                + "FULL JOIN Account ma ON v.modifiedBy = ma.accID\n"
                + "JOIN ObjectVoucher ov ON v.objectVoucherID = ov.objectVoucherID\n"
                + "WHERE (";

        String[] keywords = search.split("\\s+"); // Tách các từ khóa trong title

        for (int i = 0; i < keywords.length; i++) {
            query += " v.description LIKE ? OR v.voucherCode LIKE ?";
            if (i < keywords.length - 1) {
                query += " OR ";
            }
        }

        query += ")";
        if (!"".equals(status)) {
            query += " AND v.status = ?";
        }

        if (!"".equals(object)) {
            query += " AND v.objectVoucherID = ?";
        }

        if (!"".equals(discountType)) {
            query += " AND v.discountType = ?";
        }

        if (!"".equals(start_Date)) {
            query += " AND CONVERT(DATE, v.createdOn, 105) >= CONVERT(DATE, ?, 105)";
        }

        if (!"".equals(end_Date)) {
            query += " AND CONVERT(DATE, v.createdOn, 105) <= CONVERT(DATE, ?, 105)";
        }

        query += " ORDER BY v.voucherID OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

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
            if (!"".equals(object)) {
                position += 1;
                stm.setString(position, object);
            }
            if (!"".equals(discountType)) {
                position += 1;
                stm.setString(position, discountType);
            }
            if (!"".equals(start_Date)) {
                position += 1;
                stm.setString(position, start_Date);
            }
            if (!"".equals(end_Date)) {
                position += 1;
                stm.setString(position, end_Date);
            }

            stm.setInt(position + 1, (index - 1) * quantityShow);
            stm.setInt(position + 2, quantityShow);

            ResultSet rs = stm.executeQuery();
            while (rs.next()) {

                int createdBy = rs.getInt("createdBy");

                java.sql.Timestamp timestampstartDate = rs.getTimestamp("startDate");
                LocalDateTime startDate = timestampstartDate.toLocalDateTime();

                java.sql.Timestamp timestampCreatedOn = rs.getTimestamp("createdOn");
                LocalDateTime createdOn = timestampCreatedOn.toLocalDateTime();

                java.sql.Timestamp timestampendDate = rs.getTimestamp("endDate");
                LocalDateTime endDate = timestampendDate.toLocalDateTime();

                int modifiedBy = rs.getInt("modifiedBy");
                java.sql.Timestamp timestampModifiedOn = rs.getTimestamp("modifiedOn");

                String createdByAccName = rs.getString("createdByAccName");
                String modifiedByAccName = rs.getString("modifiedByAccName");

                if (timestampModifiedOn != null) {
                    int objectVoucherID = rs.getInt("objectVoucherID");
                    String objectVoucher = rs.getString("objectVoucher");
                    LocalDateTime modifiedOn = timestampModifiedOn.toLocalDateTime();
                    Voucher a = new Voucher(rs.getInt("voucherID"), rs.getString("voucherCode"), rs.getString("description"), new ObjectVoucher(objectVoucherID, objectVoucher), rs.getBoolean("discountType"), rs.getFloat("discountValue"), startDate, endDate, rs.getInt("maxUsage"), rs.getInt("usedCount"), new Account(createdBy, createdByAccName), createdOn, new Account(modifiedBy, modifiedByAccName), modifiedOn, rs.getBoolean("status"));
                    list.add(a);
                } else {
                    int objectVoucherID = rs.getInt("objectVoucherID");
                    String objectVoucher = rs.getString("objectVoucher");
                    Voucher a = new Voucher(rs.getInt("voucherID"), rs.getString("voucherCode"), rs.getString("description"), new ObjectVoucher(objectVoucherID, objectVoucher), rs.getBoolean("discountType"), rs.getFloat("discountValue"), startDate, endDate, rs.getInt("maxUsage"), rs.getInt("usedCount"), new Account(createdBy, createdByAccName), createdOn, new Account(modifiedBy, modifiedByAccName), rs.getBoolean("status"));
                    list.add(a);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Xử lý lỗi SQLException bằng cách in stack trace
        }
        return list;
    }

    public static void main(String[] args) {
        VoucherDAO v = new VoucherDAO();
//        v.updateUsageCount("FALL2023", 1);
//        int total = v.getTotalVoucherBySearch("", "", "", "", "", "");
//        System.out.println(total);
//        List<Voucher> l = v.getListVoucherBySearch(1, "", 5, "", "", "", "", "");
//        for (Voucher ls : l) {
//            System.out.println(ls);
//        }
    int u = v.getUsedCountByVoucherCode("FALL2023");
        System.out.println(u);
//    Voucher a = v.getVoucherByVoucherCode("FALL2023");
//         System.out.println(a);
    }

    public int getTotalVoucherBySearch(String search, String status, String object, String discountType, String start_Date, String end_Date) {
        String query = "SELECT COUNT(voucherID) FROM Voucher v WHERE (";

        String[] keywords = search.split("\\s+"); // Tách các từ khóa trong title

        for (int i = 0; i < keywords.length; i++) {
            query += " v.description LIKE ? OR v.voucherCode LIKE ?";
            if (i < keywords.length - 1) {
                query += " OR ";
            }
        }

        query += ")";
        if (!"".equals(status)) {
            query += " AND v.status = ?";
        }

        if (!"".equals(object)) {
            query += " AND v.objectVoucherID = ?";
        }

        if (!"".equals(discountType)) {
            query += " AND v.discountType = ?";
        }

        if (!"".equals(start_Date)) {
            query += " AND CONVERT(DATE, v.createdOn, 105) >= CONVERT(DATE, ?, 105)";
        }

        if (!"".equals(end_Date)) {
            query += " AND CONVERT(DATE, v.createdOn, 105) <= CONVERT(DATE, ?, 105)";
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
            if (!"".equals(discountType)) {
                if (!"".equals(object)) {
                    position += 1;
                    stm.setString(position, object);
                }
                position += 1;
                stm.setString(position, discountType);
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
            while (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

}
