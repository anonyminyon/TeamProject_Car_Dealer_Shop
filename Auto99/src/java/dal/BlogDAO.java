package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import model.Account;
import model.Blog;
import model.BlogCategory;
import model.CompanyInfo;

public class BlogDAO extends DBContext {

    public List<BlogCategory> getAllBlogCategory() {
        List<BlogCategory> list = new ArrayList<>();
        String query = "select * from BlogCategory\n";
        try {
            PreparedStatement stm = connection.prepareStatement(query);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                BlogCategory a = new BlogCategory(rs.getInt(1), rs.getString(2));
                list.add(a);
            }
        } catch (Exception e) {

        }
        return list;
    }

    public List<Blog> getAllBlog() {
        List<Blog> list = new ArrayList<>();
        String query = "select * from Blog b left join BlogCategory bc on b.blogCategoryID=bc.blogCategoryID \n"
                + "ORDER BY b.createdOn DESC;";
        try {
            PreparedStatement stm = connection.prepareStatement(query);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                int blogCategoryID = rs.getInt("blogCategoryID");
                int createdBy = rs.getInt("createdBy");
                java.sql.Timestamp timestampCreatedOn = rs.getTimestamp("createdOn");
                LocalDateTime createdOn = timestampCreatedOn.toLocalDateTime();
                int modifiedBy = rs.getInt("modifiedBy");
                java.sql.Timestamp timestampModifiedOn = rs.getTimestamp("modifiedOn");
                if (timestampModifiedOn != null) {
                    LocalDateTime modifiedOn = timestampModifiedOn.toLocalDateTime();
                    Blog a = new Blog(rs.getInt("blogID"), new BlogCategory(blogCategoryID), rs.getString("title"), rs.getString("description"), rs.getString("content"), createdOn, new Account(createdBy), modifiedOn, new Account(modifiedBy), rs.getBoolean("status"), rs.getString("blogIMG"), rs.getInt("parentID"), rs.getString("url"));
                    list.add(a);
                } else {
                    Blog a = new Blog(rs.getInt("blogID"), new BlogCategory(blogCategoryID), rs.getString("title"), rs.getString("description"), rs.getString("content"), createdOn, new Account(createdBy), new Account(modifiedBy), rs.getBoolean("status"), rs.getString("blogIMG"), rs.getInt("parentID"), rs.getString("url"));
                    list.add(a);
                }
            }
        } catch (Exception e) {

        }
        return list;
    }

    public List<Blog> getAllTTaKm() {
        List<Blog> list = new ArrayList<>();
        String query = "select * from Blog b left join BlogCategory bc on b.blogCategoryID=bc.blogCategoryID\n"
                + "where b.blogCategoryID=3 \n"
                + "ORDER BY b.createdOn DESC";
        try {
            PreparedStatement stm = connection.prepareStatement(query);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                int blogCategoryID = rs.getInt("blogCategoryID");
                String blogCategory = rs.getString("blogCategory");
                int createdBy = rs.getInt("createdBy");
                java.sql.Timestamp timestampCreatedOn = rs.getTimestamp("createdOn");
                LocalDateTime createdOn = timestampCreatedOn.toLocalDateTime();
                int modifiedBy = rs.getInt("modifiedBy");
                java.sql.Timestamp timestampModifiedOn = rs.getTimestamp("modifiedOn");
                if (timestampModifiedOn != null) {
                    LocalDateTime modifiedOn = timestampModifiedOn.toLocalDateTime();
                    Blog a = new Blog(rs.getInt("blogID"), new BlogCategory(blogCategoryID, blogCategory), rs.getString("title"), rs.getString("description"), rs.getString("content"), createdOn, new Account(createdBy), modifiedOn, new Account(modifiedBy), rs.getBoolean("status"), rs.getString("blogIMG"), rs.getInt("parentID"), rs.getString("url"));
                    list.add(a);
                } else {
                    Blog a = new Blog(rs.getInt("blogID"), new BlogCategory(blogCategoryID, blogCategory), rs.getString("title"), rs.getString("description"), rs.getString("content"), createdOn, new Account(createdBy), new Account(modifiedBy), rs.getBoolean("status"), rs.getString("blogIMG"), rs.getInt("parentID"), rs.getString("url"));
                    list.add(a);
                }
            }
        } catch (Exception e) {

        }
        return list;
    }

    public List<Blog> getAllDV() {
        List<Blog> list = new ArrayList<>();
        String query = "select * from Blog b left join BlogCategory bc on b.blogCategoryID=bc.blogCategoryID\n"
                + "where b.blogCategoryID=2\n"
                + "ORDER BY b.createdOn DESC";
        try {
            PreparedStatement stm = connection.prepareStatement(query);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                int blogCategoryID = rs.getInt("blogCategoryID");
                String blogCategory = rs.getString("blogCategory");
                int createdBy = rs.getInt("createdBy");
                java.sql.Timestamp timestampCreatedOn = rs.getTimestamp("createdOn");
                LocalDateTime createdOn = timestampCreatedOn.toLocalDateTime();
                int modifiedBy = rs.getInt("modifiedBy");
                java.sql.Timestamp timestampModifiedOn = rs.getTimestamp("modifiedOn");
                if (timestampModifiedOn != null) {
                    LocalDateTime modifiedOn = timestampModifiedOn.toLocalDateTime();
                    Blog a = new Blog(rs.getInt("blogID"), new BlogCategory(blogCategoryID, blogCategory), rs.getString("title"), rs.getString("description"), rs.getString("content"), createdOn, new Account(createdBy), modifiedOn, new Account(modifiedBy), rs.getBoolean("status"), rs.getString("blogIMG"), rs.getInt("parentID"), rs.getString("url"));
                    list.add(a);
                } else {
                    Blog a = new Blog(rs.getInt("blogID"), new BlogCategory(blogCategoryID, blogCategory), rs.getString("title"), rs.getString("description"), rs.getString("content"), createdOn, new Account(createdBy), new Account(modifiedBy), rs.getBoolean("status"), rs.getString("blogIMG"), rs.getInt("parentID"), rs.getString("url"));
                    list.add(a);
                }
            }
        } catch (Exception e) {

        }
        return list;
    }
    
    public List<Blog> getAllTrueBlog() {
        List<Blog> list = new ArrayList<>();
        String query = "select * from Blog\n"
                + "where status = 1";
        try {
            PreparedStatement stm = connection.prepareStatement(query);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                int blogCategoryID = rs.getInt("blogCategoryID");
                int createdBy = rs.getInt("createdBy");
                java.sql.Timestamp timestampCreatedOn = rs.getTimestamp("createdOn");
                LocalDateTime createdOn = timestampCreatedOn.toLocalDateTime();
                int modifiedBy = rs.getInt("modifiedBy");
                java.sql.Timestamp timestampModifiedOn = rs.getTimestamp("modifiedOn");
                if (timestampModifiedOn != null) {
                    LocalDateTime modifiedOn = timestampModifiedOn.toLocalDateTime();
                    Blog a = new Blog(rs.getInt("blogID"), new BlogCategory(blogCategoryID), rs.getString("title"), rs.getString("description"), rs.getString("content"), createdOn, new Account(createdBy), modifiedOn, new Account(modifiedBy), rs.getBoolean("status"), rs.getString("blogIMG"), rs.getInt("parentID"), rs.getString("url"));
                    list.add(a);
                } else {
                    Blog a = new Blog(rs.getInt("blogID"), new BlogCategory(blogCategoryID), rs.getString("title"), rs.getString("description"), rs.getString("content"), createdOn, new Account(createdBy), new Account(modifiedBy), rs.getBoolean("status"), rs.getString("blogIMG"), rs.getInt("parentID"), rs.getString("url"));
                    list.add(a);
                }
            }
        } catch (Exception e) {

        }
        return list;
    }

    public List<Blog> getAllMenu() {
        List<Blog> list = new ArrayList<>();
        String query = "select * from Blog\n"
                + "where blogCategoryID=1";
        try {
            PreparedStatement stm = connection.prepareStatement(query);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                int blogCategoryID = rs.getInt("blogCategoryID");
                int createdBy = rs.getInt("createdBy");
                java.sql.Timestamp timestampCreatedOn = rs.getTimestamp("createdOn");
                LocalDateTime createdOn = timestampCreatedOn.toLocalDateTime();
                int modifiedBy = rs.getInt("modifiedBy");
                java.sql.Timestamp timestampModifiedOn = rs.getTimestamp("modifiedOn");
                if (timestampModifiedOn != null) {
                    LocalDateTime modifiedOn = timestampModifiedOn.toLocalDateTime();
                    Blog a = new Blog(rs.getInt("blogID"), new BlogCategory(blogCategoryID), rs.getString("title"), rs.getString("description"), rs.getString("content"), createdOn, new Account(createdBy), modifiedOn, new Account(modifiedBy), rs.getBoolean("status"), rs.getString("blogIMG"), rs.getInt("parentID"), rs.getString("url"));
                    list.add(a);
                } else {
                    Blog a = new Blog(rs.getInt("blogID"), new BlogCategory(blogCategoryID), rs.getString("title"), rs.getString("description"), rs.getString("content"), createdOn, new Account(createdBy), new Account(modifiedBy), rs.getBoolean("status"), rs.getString("blogIMG"), rs.getInt("parentID"), rs.getString("url"));
                    list.add(a);
                }
            }
        } catch (Exception e) {

        }
        return list;
    }

    public Blog getBlogByBlogID(int blogID) {
        String query = "SELECT * FROM Blog WHERE blogID = ?";
        try {
            PreparedStatement stm = connection.prepareStatement(query);
            stm.setInt(1, blogID);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                int blogCategoryID = rs.getInt("blogCategoryID");
                int createdBy = rs.getInt("createdBy");
                java.sql.Timestamp timestampCreatedOn = rs.getTimestamp("createdOn");
                LocalDateTime createdOn = timestampCreatedOn.toLocalDateTime();
                int modifiedBy = rs.getInt("modifiedBy");
                java.sql.Timestamp timestampModifiedOn = rs.getTimestamp("modifiedOn");
                if (timestampModifiedOn != null) {
                    LocalDateTime modifiedOn = timestampModifiedOn.toLocalDateTime();
                    Blog a = new Blog(rs.getInt("blogID"), new BlogCategory(blogCategoryID), rs.getString("title"), rs.getString("description"), rs.getString("content"), createdOn, new Account(createdBy), modifiedOn, new Account(modifiedBy), rs.getBoolean("status"), rs.getString("blogIMG"), rs.getInt("parentID"), rs.getString("url"));
                    return a;
                } else {
                    Blog a = new Blog(rs.getInt("blogID"), new BlogCategory(blogCategoryID), rs.getString("title"), rs.getString("description"), rs.getString("content"), createdOn, new Account(createdBy), new Account(modifiedBy), rs.getBoolean("status"), rs.getString("blogIMG"), rs.getInt("parentID"), rs.getString("url"));
                    return a;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Blog> getAllSameParentIDBlogByBlogID(int blogID) {
        List<Blog> list = new ArrayList<>();
        String query = "SELECT * FROM Blog\n"
                + "WHERE parentID = (SELECT parentID FROM Blog WHERE blogID = ?)\n"
                + "AND blogCategoryID is null\n"
                + "AND status = 1 \n"
                + "Order by createdOn DESC;";
        try {
            PreparedStatement stm = connection.prepareStatement(query);
            stm.setInt(1, blogID);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                int blogCategoryID = rs.getInt("blogCategoryID");
                int createdBy = rs.getInt("createdBy");
                java.sql.Timestamp timestampCreatedOn = rs.getTimestamp("createdOn");
                LocalDateTime createdOn = timestampCreatedOn.toLocalDateTime();
                int modifiedBy = rs.getInt("modifiedBy");
                java.sql.Timestamp timestampModifiedOn = rs.getTimestamp("modifiedOn");
                if (timestampModifiedOn != null) {
                    LocalDateTime modifiedOn = timestampModifiedOn.toLocalDateTime();
                    Blog a = new Blog(rs.getInt("blogID"), new BlogCategory(blogCategoryID), rs.getString("title"), rs.getString("description"), rs.getString("content"), createdOn, new Account(createdBy), modifiedOn, new Account(modifiedBy), rs.getBoolean("status"), rs.getString("blogIMG"), rs.getInt("parentID"), rs.getString("url"));
                    list.add(a);
                } else {
                    Blog a = new Blog(rs.getInt("blogID"), new BlogCategory(blogCategoryID), rs.getString("title"), rs.getString("description"), rs.getString("content"), createdOn, new Account(createdBy), new Account(modifiedBy), rs.getBoolean("status"), rs.getString("blogIMG"), rs.getInt("parentID"), rs.getString("url"));
                    list.add(a);
                }
            }
        } catch (Exception e) {
            e.printStackTrace(); // In ra lỗi nếu có lỗi xảy ra
        }
        return list;
    }

    public int getTotalAllSameParentIDBlogByBlogID(int blogID) {
        List<Blog> list = new ArrayList<>();
        String query = "SELECT Count(blogID) FROM Blog\n"
                + "WHERE parentID = (SELECT parentID FROM Blog WHERE blogID = ?)\n"
                + "AND blogCategoryID is null\n";
        try {
            PreparedStatement stm = connection.prepareStatement(query);
            stm.setInt(1, blogID);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) { // Sử dụng vòng lặp while để duyệt qua tất cả các bản ghi trả về
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace(); // In ra lỗi nếu có lỗi xảy ra
        }
        return 0;
    }

    public List<Blog> getListBlogTTaKM(int index, ArrayList<String> listParentID) {
        List<Blog> list = new ArrayList<>();
        String query = "SELECT * FROM Blog WHERE parentID IN (";

        for (int i = 0; i < listParentID.size(); i++) {
            query += "?";
            if (i < listParentID.size() - 1) {
                query += ",";
            }
        }

        query += ") AND blogCategoryID IS NULL AND status = 1 ORDER BY createdOn DESC"
                + " OFFSET ? ROWS FETCH NEXT 6 ROWS ONLY";
        try {
            PreparedStatement stm = connection.prepareStatement(query);

            for (int i = 0; i < listParentID.size(); i++) {
                stm.setString(i + 1, listParentID.get(i));
            }

            stm.setInt(listParentID.size() + 1, (index - 1) * 6);
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                int blogCategoryID = rs.getInt("blogCategoryID");
                int createdBy = rs.getInt("createdBy");
                java.sql.Timestamp timestampCreatedOn = rs.getTimestamp("createdOn");
                LocalDateTime createdOn = timestampCreatedOn.toLocalDateTime();
                int modifiedBy = rs.getInt("modifiedBy");
                java.sql.Timestamp timestampModifiedOn = rs.getTimestamp("modifiedOn");
                if (timestampModifiedOn != null) {
                    LocalDateTime modifiedOn = timestampModifiedOn.toLocalDateTime();
                    Blog a = new Blog(rs.getInt("blogID"), new BlogCategory(blogCategoryID), rs.getString("title"), rs.getString("description"), rs.getString("content"), createdOn, new Account(createdBy), modifiedOn, new Account(modifiedBy), rs.getBoolean("status"), rs.getString("blogIMG"), rs.getInt("parentID"), rs.getString("url"));
                    list.add(a);
                } else {
                    Blog a = new Blog(rs.getInt("blogID"), new BlogCategory(blogCategoryID), rs.getString("title"), rs.getString("description"), rs.getString("content"), createdOn, new Account(createdBy), new Account(modifiedBy), rs.getBoolean("status"), rs.getString("blogIMG"), rs.getInt("parentID"), rs.getString("url"));
                    list.add(a);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public int getTotalBlogTTaKM(ArrayList<String> listParentID) {
        String query = "SELECT Count(blogID) FROM Blog WHERE parentID IN (";

        for (int i = 0; i < listParentID.size(); i++) {
            query += "?";
            if (i < listParentID.size() - 1) {
                query += ",";
            }
        }

        query += ") AND blogCategoryID IS NULL";
        try {
            PreparedStatement stm = connection.prepareStatement(query);

            for (int i = 0; i < listParentID.size(); i++) {
                stm.setString(i + 1, listParentID.get(i));
            }

            ResultSet rs = stm.executeQuery();

            while (rs.next()) { // Sử dụng vòng lặp while để duyệt qua tất cả các bản ghi trả về
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<Blog> getBlogByParentID(int parentID) {
        List<Blog> list = new ArrayList<>();
        String query = "SELECT TOP 5 b.blogID, b.blogCategoryID, b.title, b.description, b.createdOn ,b.blogIMG, b.parentID\n"
                + "FROM Blog b\n"
                + "WHERE b.parentID = ? and b.status= 1\n"
                + "ORDER BY b.createdOn DESC;";
        try {
            PreparedStatement stm = connection.prepareStatement(query);
            stm.setInt(1, parentID);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                int blogCategoryID = rs.getInt("blogCategoryID");
                java.sql.Timestamp timestampCreatedOn = rs.getTimestamp("createdOn");
                LocalDateTime createdOn = timestampCreatedOn.toLocalDateTime();
                Blog a = new Blog(rs.getInt("blogID"), new BlogCategory(blogCategoryID), rs.getString("title"), rs.getString("description"), createdOn, rs.getString(6), rs.getInt("parentID"));
                list.add(a);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Blog> getListBlogBySearch(int index, String title, int quantityShow, String status, int parentId, String startDate, String endDate) {
        List<Blog> list = new ArrayList<>();
        String query = "SELECT b.*, \n"
                + "       ca.accName AS createdByAccName, \n"
                + "       ma.accName AS modifiedByAccName\n"
                + "FROM Blog b\n"
                + "FULL JOIN Account ca ON b.createdBy = ca.accID\n"
                + "FULL JOIN Account ma ON b.modifiedBy = ma.accID\n"
                + "WHERE (";

        String[] keywords = title.split("\\s+"); // Tách các từ khóa trong title

        for (int i = 0; i < keywords.length; i++) {
            query += " b.title LIKE ? OR b.createdOn LIKE ? OR b.blogID LIKE ?";
            if (i < keywords.length - 1) {
                query += " OR ";
            }
        }

        query += ")";
        if (!"".equals(status)) {
            query += " AND b.status = ?";
        }

        if (parentId != 0) {
            query += " AND b.parentID = ?";
        }

        if (!"".equals(startDate)) {
            query += " AND CONVERT(DATE, b.createdOn, 105) >= CONVERT(DATE, ?, 105)";
        }

        if (!"".equals(endDate)) {
            query += " AND CONVERT(DATE, b.createdOn, 105) <= CONVERT(DATE, ?, 105)";
        }

        query += " ORDER BY b.blogID OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        try {
            PreparedStatement stm = connection.prepareStatement(query);
            for (int i = 0; i < keywords.length; i++) {
                stm.setString(i * 3 + 1, "%" + keywords[i] + "%");
                stm.setString(i * 3 + 2, "%" + keywords[i] + "%");
                stm.setString(i * 3 + 3, "%" + keywords[i] + "%");
            }
            int position = keywords.length * 3;

            if (!"".equals(status)) {
                position += 1;
                stm.setString(position, status);
            }
            if (parentId != 0) {
                position += 1;
                stm.setInt(position, parentId);
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
                int blogCategoryID = rs.getInt("blogCategoryID");
                int createdBy = rs.getInt("createdBy");
                java.sql.Timestamp timestampCreatedOn = rs.getTimestamp("createdOn");
                LocalDateTime createdOn = timestampCreatedOn.toLocalDateTime();
                int modifiedBy = rs.getInt("modifiedBy");
                java.sql.Timestamp timestampModifiedOn = rs.getTimestamp("modifiedOn");
                String createdByAccName = rs.getString("createdByAccName");
                String modifiedByAccName = rs.getString("modifiedByAccName");
                if (timestampModifiedOn != null) {
                    LocalDateTime modifiedOn = timestampModifiedOn.toLocalDateTime();
                    Blog a = new Blog(rs.getInt("blogID"), new BlogCategory(blogCategoryID), rs.getString("title"), rs.getString("description"), rs.getString("content"), createdOn, new Account(createdBy, createdByAccName), modifiedOn, new Account(modifiedBy, modifiedByAccName), rs.getBoolean("status"), rs.getString("blogIMG"), rs.getInt("parentID"), rs.getString("url"));
                    list.add(a);
                } else {
                    Blog a = new Blog(rs.getInt("blogID"), new BlogCategory(blogCategoryID), rs.getString("title"), rs.getString("description"), rs.getString("content"), createdOn, new Account(createdBy, createdByAccName), new Account(modifiedBy, modifiedByAccName), rs.getBoolean("status"), rs.getString("blogIMG"), rs.getInt("parentID"), rs.getString("url"));
                    list.add(a);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Xử lý lỗi SQLException bằng cách in stack trace
        }
        return list;
    }

//    public static void main(String[] args) {
//        BlogDAO dao = new BlogDAO();
//        List<Blog> b = dao.getListBlogBySearch(1, "", 6, "true", 1, "", "");
//        for (Blog blog : b) {
//            System.out.println(blog);
//        }
//    }
    public List<Blog> getAllBlogSubCategory() {
        List<Blog> list = new ArrayList<>();
        String query = "SELECT b.title, b.parentID\n"
                + "FROM Blog b\n"
                + "WHERE b.parentID IN (\n"
                + "    SELECT parentID\n"
                + "    FROM Blog\n"
                + "    GROUP BY parentID\n"
                + ") and b.blogCategoryID is not null\n";
        try {
            PreparedStatement stm = connection.prepareStatement(query);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Blog a = new Blog(rs.getString("title"), rs.getInt("parentID"));
                list.add(a);
            }
        } catch (Exception e) {
            // Xử lý ngoại lệ
            // Xử lý ngoại lệ
        }
        return list;
    }

    public String getMainImageName(String blogID) {
        String query = "SELECT blogIMG from Blog \n"
                + "where blogID = ? \n";
        try {
            PreparedStatement stm = connection.prepareStatement(query);
            stm.setString(1, blogID);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                return rs.getString(1);
            }
        } catch (Exception e) {

        }
        return "";
    }

    public void removeImage(String blogID) {
        try {
            // SQL insert statement
            String sql = "UPDATE Blog\n"
                    + "SET blogIMG = NULL\n"
                    + "WHERE blogID = ?;";

            // Create a prepared statement
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, blogID);

            // Execute the insert statement
            stm.executeUpdate();

            // Handle success or error here
        } catch (SQLException e) {
            // Handle any errors that may occur
            e.printStackTrace();
            // Handle the error here
        }
    }

    public int getTotalBlogBySearch(String title, String status, int parentId, String startDate, String endDate) {
        String query = "SELECT COUNT(blogID) FROM Blog b WHERE (";

        String[] keywords = title.split("\\s+");

        for (int i = 0; i < keywords.length; i++) {
            query += "b.title LIKE ? OR b.createdOn LIKE ? OR b.blogID LIKE ?";
            if (i < keywords.length - 1) {
                query += " OR ";
            }
        }

        query += ")\n";

        if (!"".equals(status)) {
            query += " AND b.status = ?\n";
        }

        if (parentId != 0) {
            query += " AND b.parentID = ?\n";
        }

        if (!"".equals(startDate)) {
            query += " AND CONVERT(DATE, b.createdOn, 105) >= CONVERT(DATE, ?, 105)";
        }

        if (!"".equals(endDate)) {
            query += " AND CONVERT(DATE, b.createdOn, 105) <= CONVERT(DATE, ?, 105)";
        }
        try {
            PreparedStatement stm = connection.prepareStatement(query);
            int position = 1;

            for (int i = 0; i < keywords.length; i++) {
                stm.setString(position++, "%" + keywords[i] + "%");
                stm.setString(position++, "%" + keywords[i] + "%");
                stm.setString(position++, "%" + keywords[i] + "%");
            }

            if (!"".equals(status)) {
                stm.setString(position++, status);
            }

            if (parentId != 0) {
                stm.setInt(position++, parentId);
            }

            if (!"".equals(startDate)) {
                stm.setString(position++, startDate);
            }

            if (!"".equals(endDate)) {
                stm.setString(position++, endDate);
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

    public static void main(String[] args) {
        BlogDAO dao = new BlogDAO();
        int total = dao.getTotalBlogBySearch("", "true", 11, "", "");
        System.out.println(total);
    }

    public int getTotalBlog() {
        try {
            String sql = "SELECT count(*)\n"
                    + "FROM Blog b;";
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public boolean isBlogExistAndNotValid(int blogID, int parentID) {
        try {

            // SQL query to check if a blog exists with the given parameters and is not valid
            String query = "SELECT [blogID],[parentID] FROM Blog "
                    + "WHERE blogID = ? OR parentID = ? ";

            // Create a prepared statement
            PreparedStatement stm = connection.prepareStatement(query);

            stm.setInt(1, blogID);
            stm.setInt(2, parentID);

            // Execute the query
            ResultSet rs = stm.executeQuery();

            //if rs has value
            if (rs.next()) {
                // Close resources
                rs.close();
                rs.close();

                return true;
            }
            // Close resources
            rs.close();
            stm.close();

            // No matching blog found or it is valid
            return false;

        } catch (SQLException e) {
            // Handle any errors that may occur
            e.printStackTrace();
            // Handle the error here and return false or throw an exception
            return false;
        }
    }

    public boolean checkTitleExist(String title) {
        String query = "SELECT 1 FROM Blog WHERE title = ?";
        try {
            PreparedStatement stm = connection.prepareStatement(query);
            stm.setString(1, title);
            ResultSet rs = stm.executeQuery();
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void InsertBlogSubCategory(String blogCategoryID, String title, String createdOn, String createdBy) {
        try {
            // SQL insert statement
            String sql = "INSERT INTO [Blog] ([blogCategoryID], [title],[status], [createdOn], [createdBy],[parentID])\n"
                    + "VALUES (?, ?, 1, ?, ?, (SELECT ISNULL(MAX(parentID), 0) + 1 FROM [Blog]))";

            // Create a prepared statement
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, blogCategoryID);
            stm.setString(2, title);
            stm.setString(3, createdOn);
            stm.setString(4, createdBy);

            // Execute the insert statement
            stm.executeUpdate();
            stm.close();
            connection.close();

            // Xử lý thành công ở đây nếu cần
        } catch (SQLException e) {
            // Xử lý lỗi ở đây, ví dụ:
            e.printStackTrace();
            // Hoặc bạn có thể log lỗi hoặc thông báo cho người dùng
        }
    }

    public void InsertBlog(String parentID, String title, String description, String content, String createdOn, int createdBy, String blogIMG) {
        try {
            // SQL insert statement
            String sql = "INSERT INTO [Blog] ([parentID], [title],[status], [description], [content], [createdOn], [createdBy], [blogIMG]) VALUES (?, ?, 1, ?, ?, ?, ?, ?)";

            // Create a prepared statement
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, parentID);
            stm.setString(2, title);
            stm.setString(3, description);
            stm.setString(4, content);
            stm.setString(5, createdOn);
            stm.setInt(6, createdBy);
            stm.setString(7, blogIMG);

            // Execute the insert statement
            stm.executeUpdate();
            stm.close();
            connection.close();

            // Xử lý thành công ở đây nếu cần
        } catch (SQLException e) {
            // Xử lý lỗi ở đây, ví dụ:
            e.printStackTrace();
            // Hoặc bạn có thể log lỗi hoặc thông báo cho người dùng
        }
    }

    public void updateBlog(String modifiedOn, int modifiedBy, boolean status, int blogID) {

        try {
            String query = "UPDATE Blog\n"
                    + "SET modifiedOn = ?,\n"
                    + "    status = ?,\n"
                    + "    modifiedBy = ?\n"
                    + "WHERE blogID = ?;";
            PreparedStatement stm = connection.prepareStatement(query);
            stm.setString(1, modifiedOn);
            stm.setBoolean(2, status);
            stm.setInt(3, modifiedBy);
            stm.setInt(4, blogID);
            stm.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//    public static void main(String[] args) {
//        BlogDAO dao = new BlogDAO();
//        dao.updateBlog("2023-10-19 00:00:00.000",2, true, 1);
//    }

    public void advancedUpdateBlog(String parentID, String title, String description, String content, String modifiedOn, int modifiedBy, String blogIMG, String blogID) {
        try {
            // SQL insert statement
            String sql = "UPDATE [Blog]\n"
                    + "SET [parentID] = ?, [title] = ?, [description] = ?, [content] = ?, [modifiedOn] = ?, [modifiedBy] = ?, [blogIMG] = ?\n"
                    + "WHERE [blogID] = ?;";

            // Create a prepared statement
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, parentID);
            stm.setString(2, title);
            stm.setString(3, description);
            stm.setString(4, content);
            stm.setString(5, modifiedOn);
            stm.setInt(6, modifiedBy);
            stm.setString(7, blogIMG);
            stm.setString(8, blogID);

            // Execute the insert statement
            stm.executeUpdate();
            stm.close();
            connection.close();

            // Xử lý thành công ở đây nếu cần
            // Xử lý thành công ở đây nếu cần
        } catch (SQLException e) {
            // Xử lý lỗi ở đây, ví dụ:
            e.printStackTrace();
            // Hoặc bạn có thể log lỗi hoặc thông báo cho người dùng
        }
    }

    public Blog getAd(int parentID) {
        List<Blog> list = new ArrayList<>();
        String query = "SELECT top 1 b.blogID, b.blogCategoryID, b.title, b.description, b.createdOn ,b.blogIMG, b.parentID\n"
                + "FROM Blog b\n"
                + "WHERE b.parentID = ? and b.status= 1 and b.blogCategoryID is null\n"
                + "ORDER BY b.createdOn DESC;";
        try {
            PreparedStatement stm = connection.prepareStatement(query);
            stm.setInt(1, parentID);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                int blogCategoryID = rs.getInt("blogCategoryID");
                java.sql.Timestamp timestampCreatedOn = rs.getTimestamp("createdOn");
                LocalDateTime createdOn = timestampCreatedOn.toLocalDateTime();
                Blog blog = new Blog(rs.getInt("blogID"), new BlogCategory(blogCategoryID), rs.getString("title"), createdOn, rs.getInt("parentID"), rs.getString("blogIMG"));
                return blog;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Blog> getFooter() {
        List<Blog> list = new ArrayList<>();
        String query = "SELECT * FROM Blog\n"
                + "where parentID=15 and blogCategoryID is null";
        try {
            PreparedStatement stm = connection.prepareStatement(query);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Blog blog = new Blog(rs.getString("content"), rs.getString("title"), rs.getInt("parentID"));
                list.add(blog);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Blog> getListAds() {
        List<Blog> list = new ArrayList<>();
        String query = "SELECT TOP 10 * FROM Blog b\n"
                + "where b.blogCategoryID is null and createdBy is not null\n"
                + "order by b.createdOn DESC";
        try {
            PreparedStatement stm = connection.prepareStatement(query);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                int blogCategoryID = rs.getInt("blogCategoryID");
                java.sql.Timestamp timestampCreatedOn = rs.getTimestamp("createdOn");
                LocalDateTime createdOn = timestampCreatedOn.toLocalDateTime();
                Blog a = new Blog(rs.getInt("blogID"), new BlogCategory(blogCategoryID), rs.getString("title"), rs.getString("description"), createdOn, rs.getString(6), rs.getInt("parentID"));
                list.add(a);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
