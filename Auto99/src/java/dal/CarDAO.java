package dal;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import model.Account;
import model.Car;
import model.CarBrand;
import model.CarDesign;
import model.CarIMG;
import model.CarStaffPage;
import model.StatusCategory;
import model.CarSubIMG;
import model.CarSubIMGType;
import model.CarsHomePage;
import model.EngineAndChassis;
import model.GeneralInfoCar;

/**
 *
 * @author Dao Anh Duc
 */
public class CarDAO extends DBContext {

    public Car getCarByCarID(int carID) {
        Car car = null;

        String sql = "SELECT carID, carName, price, brandID, statusID " +
                     "FROM Car WHERE carID = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, carID);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                car = new Car(
                        resultSet.getInt("carID"),
                        resultSet.getString("carName"),
                        resultSet.getLong("price"),
                        // Assuming you have a CarBrand class, adjust accordingly
                        new CarBrand(resultSet.getInt("brandID"))
                        
                );
            }
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception according to your needs
        }

        return car;
    }
    
    public List<CarBrand> getAllCarBrand() {
        List<CarBrand> list = new ArrayList<>();
            String query = "select * from CarBrand\n";
        try {
            PreparedStatement stm = connection.prepareStatement(query);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                CarBrand a = new CarBrand(rs.getInt(1), rs.getString(2));
                list.add(a);
            }
        } catch (Exception e) {

        }
        return list;
    }

    public List<CarDesign> getAllCarDesign() {
        List<CarDesign> list = new ArrayList<>();
        String query = "select * from CarDesign\n";
        try {
            PreparedStatement stm = connection.prepareStatement(query);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                CarDesign a = new CarDesign(rs.getInt(1), rs.getString(2));
                list.add(a);
            }
        } catch (Exception e) {

        }
        return list;
    }

    public List<Car> getAllCar() {
        List<Car> list = new ArrayList<>();
        String query = "select * from Car\n";
        try {
            PreparedStatement stm = connection.prepareStatement(query);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                int brandID = rs.getInt("brandID");
                Car a = new Car(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getLong(3),
                        new CarBrand(brandID));
                list.add(a);
            }
        } catch (Exception e) {

        }
        return list;
    }

    public List<GeneralInfoCar> getAllGeneralInfoCar() {
        List<GeneralInfoCar> list = new ArrayList<>();
        String query = "select * from GeneralInfoCar\n";
        try {
            PreparedStatement stm = connection.prepareStatement(query);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Car c = new Car(rs.getInt(1));
                CarDesign cd = new CarDesign(rs.getString(2));
                GeneralInfoCar a = new GeneralInfoCar(c, rs.getInt(2), cd, rs.getString(4), rs.getString(5));
                list.add(a);
            }
        } catch (Exception e) {

        }
        return list;
    }

    public List<CarIMG> getAllCarIMG() {
        List<CarIMG> list = new ArrayList<>();
        String query = "select * from CarIMG\n";
        try {
            PreparedStatement stm = connection.prepareStatement(query);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                int carID = rs.getInt("carID");
                CarIMG a = new CarIMG(new Car(carID), rs.getString("carIMG"));
                list.add(a);
            }
        } catch (Exception e) {

        }
        return list;
    }

    public List<CarSubIMGType> getAllCarSubIMGType() {
        List<CarSubIMGType> list = new ArrayList<>();
        String query = "select * from CarSubIMGType\n";
        try {
            PreparedStatement stm = connection.prepareStatement(query);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                CarSubIMGType a = new CarSubIMGType(rs.getInt("carSubIMGTypeID"), rs.getString("carSubIMGType"));
                list.add(a);
            }
        } catch (Exception e) {

        }
        return list;
    }

    public List<EngineAndChassis> getAllEngineAndChassis() {
        List<EngineAndChassis> list = new ArrayList<>();
        String query = "select * from EngineAndChassis\n";
        try {
            PreparedStatement stm = connection.prepareStatement(query);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Car c = new Car(rs.getInt(1));
                EngineAndChassis a = new EngineAndChassis(c, rs.getFloat(2), rs.getString(3), rs.getString(4), rs.getFloat(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10));
                list.add(a);
            }
        } catch (Exception e) {

        }
        return list;
    }

    public ArrayList<CarsHomePage> getCarsByDesignID(int designID) {
        ArrayList<CarsHomePage> list = new ArrayList<>();
        String query = "SELECT DISTINCT TOP 10 c.carID, c.carName, c.price, cd.design, gi.numberOfSeat, eac.engineType, eac.cylinderCapacity, eac.gear\n"
                + "FROM Car c\n"
                + "LEFT JOIN CarBrand cb ON c.brandID = cb.brandID\n"
                + "LEFT JOIN GeneralInfoCar gi ON c.carID = gi.carID\n"
                + "LEFT JOIN CarIMG ci ON c.carID = ci.carID\n"
                + "LEFT JOIN EngineAndChassis eac ON c.carID = eac.carID\n"
                + "LEFT JOIN CarDesign cd ON gi.designID = cd.designID\n"
                + "WHERE gi.designID = ?\n"
                + "ORDER BY c.carID DESC;";
        try {
            PreparedStatement stm = connection.prepareStatement(query);
            stm.setInt(1, designID);
            stm.setInt(1, designID);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                CarsHomePage a = new CarsHomePage(rs.getInt(1), rs.getString(2), rs.getLong(3), rs.getString(4), rs.getInt(5), rs.getString(6), rs.getFloat(7), rs.getString(8));
                list.add(a);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<CarIMG> getCarIMGByCarID(int carID) {
        List<CarIMG> list = new ArrayList<>();
        String query = "select * from CarIMG where carID=?\n";
        try {
            PreparedStatement stm = connection.prepareStatement(query);
            stm.setInt(1, carID);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                carID = rs.getInt("carID");
                CarIMG a = new CarIMG(new Car(carID), rs.getString("carIMG"));
                list.add(a);
            }
        } catch (Exception e) {

        }
        return list;
    }

    public List<CarSubIMG> getCarSubIMGByCarIDAndCarSubIMGTypeID(int carID, int carSubIMGTypeID) {
        List<CarSubIMG> list = new ArrayList<>();
        String query = "select * from CarSubIMG where carID=? and carSubIMGTypeID = ?";
        try {
            PreparedStatement stm = connection.prepareStatement(query);
            stm.setInt(1, carID);
            stm.setInt(2, carSubIMGTypeID);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                carID = rs.getInt("carID");
                carSubIMGTypeID = rs.getInt("carSubIMGTypeID");
                CarSubIMG a = new CarSubIMG(new Car(carID), new CarSubIMGType(carSubIMGTypeID), rs.getString("carSubIMG"));
                list.add(a);
            }
        } catch (Exception e) {

        }
        return list;
    }


    /*----- Thinh ----------------------------------------------------------------*/
 /*----- Thinh ----------------------------------------------------------------*/
 /*----- Thinh ----------------------------------------------------------------*/
 /*----- Thinh ----------------------------------------------------------------*/
 /*----- Thinh ----------------------------------------------------------------*/
//    public List<Car> getAllCarAdmin() {
//        List<Car> list = new ArrayList<>();
//        String query = "SELECT DISTINCT c.carID, c.carName, c.price, cb.brandName  \n"
//                + "FROM Car c\n"
//                + "LEFT JOIN CarBrand cb ON c.brandID = cb.brandID\n";
//        try {
//            PreparedStatement stm = connection.prepareStatement(query);
//            ResultSet rs = stm.executeQuery();
//            while (rs.next()) {
//                String brandName = rs.getString("brandName");
//                Car a = new Car(
//                        rs.getInt(1),
//                        rs.getString(2),
//                        rs.getLong(3),
//                        new CarBrand(brandName));
//                list.add(a);
//            }
//        } catch (Exception e) {
//
//        }
//        return list;
//    }
//    public List<Car> getPaggingCarAdmin(int index) {
//        List<Car> list = new ArrayList<>();
//        String query = "SELECT DISTINCT c.carID, c.carName, c.price, cb.brandName  \n"
//                + "FROM Car c\n"
//                + "LEFT JOIN CarBrand cb ON c.brandID = cb.brandID\n"
//                + "ORDER BY c.carID\n"
//                + "OFFSET ? ROW FETCH NEXT 5 ROW ONLY";
//        try {
//            PreparedStatement stm = connection.prepareStatement(query);
//            stm.setInt(1, (index - 1) * 5);
//            ResultSet rs = stm.executeQuery();
//            while (rs.next()) {
//                String brandName = rs.getString("brandName");
//                Car a = new Car(
//                        rs.getInt(1),
//                        rs.getString(2),
//                        rs.getLong(3),
//                        new CarBrand(brandName));
//                list.add(a);
//            }
//        } catch (Exception e) {
//
//        }
//        return list;
//    }
//    public List<CarStaffPage> getListCarByName(int index, String name, int quantityShow) {
//        List<CarStaffPage> list = new ArrayList<>();
//        String query = "SELECT DISTINCT c.carID, c.carName, c.price, c.brandID, c.statusID, cb.brandName, cs.statusName, gi.designID, cd.design\n"
//                + "FROM Car c\n"
//                + "LEFT JOIN CarBrand cb ON c.brandID = cb.brandID\n"
//                + "LEFT JOIN StatusCategory cs ON c.statusID = cs.statusID\n"
//                +"LEFT JOIN GeneralInfoCar gi ON c.carID = gi.carID\n"
//		+"LEFT JOIN CarDesign cd ON gi.designID = cd.designID\n"
//                + "where CarName like ?\n"
//                + "ORDER BY c.carID\n"
//                + "OFFSET ? ROW FETCH NEXT ? ROW ONLY";
//        try {
//            PreparedStatement stm = connection.prepareStatement(query);
//            stm.setString(1, "%" + name + "%");
//            stm.setInt(2, (index - 1) * quantityShow);
//            stm.setInt(3, quantityShow);
//            ResultSet rs = stm.executeQuery();
//            while (rs.next()) {
//                int brandID = rs.getInt("brandID");
//                String brandName = rs.getString("brandName");
//                int statusID = rs.getInt("statusID");
//                String statusName = rs.getString("statusName");
//                int designID = rs.getInt("designID");
//                String design = rs.getString("design");
//                CarStaffPage a = new CarStaffPage(
//                        rs.getInt(1),
//                        rs.getString(2),
//                        rs.getLong(3),
//                        new CarBrand(brandID, brandName),
//                        new StatusCategory(statusID, statusName),
//                        new CarDesign(designID, design));
//                list.add(a);
//            }
//        } catch (Exception e) {
//
//        }
//        return list;
//    }
//    public List<CarStaffPage> getListCarAdmin(int index, String name, int quantityShow, int brandID, int designID, int statusID) {
//        List<CarStaffPage> list = new ArrayList<>();
//        String query = "SELECT DISTINCT c.carID, c.carName, c.price, c.brandID, c.statusID, cb.brandName, cs.statusName, gi.designID, cd.design\n"
//                + "FROM Car c\n"
//                + "LEFT JOIN CarBrand cb ON c.brandID = cb.brandID\n"
//                + "LEFT JOIN StatusCategory cs ON c.statusID = cs.statusID\n"
//                + "LEFT JOIN GeneralInfoCar gi ON c.carID = gi.carID\n"
//                + "LEFT JOIN CarDesign cd ON gi.designID = cd.designID\n"
//                + "where ";
//
//        String[] keywords = name.split("\\s+"); // Tách các từ khóa trong title
//
//        for (int i = 0; i < keywords.length; i++) {
//            if (i == 0) {
//                query += "(";
//            }
//            query += "c.carName LIKE ? OR cb.brandName LIKE ? OR cd.design LIKE ?";
//            if (i < keywords.length - 1) {
//                query += " OR ";
//            }
//            if (i == keywords.length - 1) {
//                query += ")";
//            }
//        }
//
//        if (brandID != 0) {
//            query += " and c.brandID = ? ";
//        }
//
//        if (designID != 0) {
//            query += " and cd.designID = ? ";
//        }
//
//        if (statusID != 0) {
//            query += " and cs.statusID = ? ";
//        }
//
//        query += "\nORDER BY c.carID\n"
//                + "OFFSET ? ROW FETCH NEXT ? ROW ONLY";
//        try {
//            PreparedStatement stm = connection.prepareStatement(query);
//            for (int i = 0; i < keywords.length; i++) {
//                stm.setString(i * 3 + 1, "%" + keywords[i] + "%");
//                stm.setString(i * 3 + 2, "%" + keywords[i] + "%");
//                stm.setString(i * 3 + 3, "%" + keywords[i] + "%");
//            }
//
//            int position = keywords.length * 3;
//
//            if (brandID != 0) {
//                position += 1;
//                stm.setInt(position, brandID);
//            }
//
//            if (designID != 0) {
//                position += 1;
//                stm.setInt(position, designID);
//            }
//
//            if (statusID != 0) {
//                position += 1;
//                stm.setInt(position, statusID);
//            }
//
//            stm.setInt(position + 1, (index - 1) * quantityShow);
//            stm.setInt(position + 2, quantityShow);
//            ResultSet rs = stm.executeQuery();
//            while (rs.next()) {
//                brandID = rs.getInt("brandID");
//                String brandName = rs.getString("brandName");
//                statusID = rs.getInt("statusID");
//                String statusName = rs.getString("statusName");
//                designID = rs.getInt("designID");
//                String design = rs.getString("design");
//                CarStaffPage a = new CarStaffPage(
//                        rs.getInt(1),
//                        rs.getString(2),
//                        rs.getLong(3),
//                        new CarBrand(brandID, brandName),
//                        new StatusCategory(statusID, statusName),
//                        new CarDesign(designID, design));
//                list.add(a);
//            }
//        } catch (Exception e) {
//
//        }
//        return list;
//    }
    public List<CarStaffPage> getListCarAdmin(int index, String name, int quantityShow, int brandID, int designID, int statusID, double minPrice, double maxPrice) {
        List<CarStaffPage> list = new ArrayList<>();
        String query = "SELECT DISTINCT c.carID, c.carName, c.price, c.brandID, c.statusID, cb.brandName, cs.statusName, gi.designID, cd.design\n"
                + ", c.createdBy, a.accName, c.createdOn, c.modifiedBy, b.accName as modName, c.modifiedOn\n"
                + "FROM Car c\n"
                + "LEFT JOIN CarBrand cb ON c.brandID = cb.brandID\n"
                + "LEFT JOIN StatusCategory cs ON c.statusID = cs.statusID\n"
                + "LEFT JOIN GeneralInfoCar gi ON c.carID = gi.carID\n"
                + "LEFT JOIN CarDesign cd ON gi.designID = cd.designID\n"
                + "LEFT JOIN Account a ON a.accID = c.createdBy\n"
                + "LEFT JOIN Account b ON b.accID = c.modifiedBy\n"
                + "where ";

        String[] keywords = name.split("\\s+"); // Tách các từ khóa trong title

        for (int i = 0; i < keywords.length; i++) {
            if (i == 0) {
                query += "(";
            }
            query += "c.carName LIKE ? OR cb.brandName LIKE ? OR cd.design LIKE ?";
            if (i < keywords.length - 1) {
                query += " OR ";
            }
            if (i == keywords.length - 1) {
                query += ")";
            }
        }

        if (brandID != 0) {
            query += " and c.brandID = ? ";
        }

        if (designID != 0) {
            query += " and cd.designID = ? ";
        }

        if (statusID != 0) {
            query += " and cs.statusID = ? ";
        }

        query += "\nand c.price >= ? and c.price <= ?\n"
                + "ORDER BY c.carID\n"
                + "OFFSET ? ROW FETCH NEXT ? ROW ONLY";
        try {
            PreparedStatement stm = connection.prepareStatement(query);
            for (int i = 0; i < keywords.length; i++) {
                stm.setString(i * 3 + 1, "%" + keywords[i] + "%");
                stm.setString(i * 3 + 2, "%" + keywords[i] + "%");
                stm.setString(i * 3 + 3, "%" + keywords[i] + "%");
            }

            int position = keywords.length * 3;

            if (brandID != 0) {
                position += 1;
                stm.setInt(position, brandID);
            }

            if (designID != 0) {
                position += 1;
                stm.setInt(position, designID);
            }

            if (statusID != 0) {
                position += 1;
                stm.setInt(position, statusID);
            }

            stm.setDouble(position + 1, minPrice);
            stm.setDouble(position + 2, maxPrice);
            stm.setInt(position + 3, (index - 1) * quantityShow);
            stm.setInt(position + 4, quantityShow);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                brandID = rs.getInt("brandID");
                String brandName = rs.getString("brandName");
                statusID = rs.getInt("statusID");
                String statusName = rs.getString("statusName");
                designID = rs.getInt("designID");
                String design = rs.getString("design");
                CarStaffPage a = new CarStaffPage(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getLong(3),
                        new CarBrand(brandID, brandName),
                        new StatusCategory(statusID, statusName),
                        new CarDesign(designID, design),
                        new Account(rs.getInt("createdBy"), rs.getString("accName")),
                        rs.getTimestamp("createdOn"),
                        new Account(rs.getInt("modifiedBy"), rs.getString("modName")),
                        rs.getTimestamp("modifiedOn")
                );
                list.add(a);
            }
        } catch (Exception e) {

        }
        return list;
    }

    public double getMaxPriceCar(String name, int brandID, int designID, int statusID) {
        String query = "SELECT Max(c.price) from Car c "
                + "LEFT JOIN CarBrand cb ON c.brandID = cb.brandID\n"
                + "LEFT JOIN StatusCategory cs ON c.statusID = cs.statusID\n"
                + "LEFT JOIN GeneralInfoCar gi ON c.carID = gi.carID\n"
                + "LEFT JOIN CarDesign cd ON gi.designID = cd.designID\n"
                + "where ";

        String[] keywords = name.split("\\s+"); // Tách các từ khóa trong title

        for (int i = 0; i < keywords.length; i++) {
            if (i == 0) {
                query += "(";
            }
            query += "c.carName LIKE ? OR cb.brandName LIKE ? OR cd.design LIKE ?";
            if (i < keywords.length - 1) {
                query += " OR ";
            }
            if (i == keywords.length - 1) {
                query += ")";
            }
        }

        if (brandID != 0) {
            query += " and c.brandID = ? ";
        }

        if (designID != 0) {
            query += " and cd.designID = ? ";
        }

        if (statusID != 0) {
            query += " and cs.statusID = ? ";
        }

        try {
            PreparedStatement stm = connection.prepareStatement(query);
            for (int i = 0; i < keywords.length; i++) {
                stm.setString(i * 3 + 1, "%" + keywords[i] + "%");
                stm.setString(i * 3 + 2, "%" + keywords[i] + "%");
                stm.setString(i * 3 + 3, "%" + keywords[i] + "%");
            }

            int position = keywords.length * 3;
            if (brandID != 0) {
                position += 1;
                stm.setInt(position, brandID);
            }

            if (designID != 0) {
                position += 1;
                stm.setInt(position, designID);
            }

            if (statusID != 0) {
                position += 1;
                stm.setInt(position, statusID);
            }
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                return rs.getDouble(1);
            }
        } catch (Exception e) {

        }
        return 0;
    }

    public List<CarStaffPage> getListCarHome(int index, String name, int quantityShow, int brandID, int designID, int statusID) {
        List<CarStaffPage> list = new ArrayList<>();
        String query = "SELECT DISTINCT c.carID, c.carName, c.price,\n"
                + "gi.madeIn, gi.numberOfSeat, gi.fuel, ec.engineType, ec.gear, ec.cylinderCapacity,\n"
                + "c.brandID, c.statusID, cb.brandName, cs.statusName, gi.designID, cd.design,\n"
                + "ci.carIMG, csi.carSubIMGTypeID, csi.carSubIMG\n"
                + "FROM Car c\n"
                + "LEFT JOIN CarBrand cb ON c.brandID = cb.brandID\n"
                + "LEFT JOIN StatusCategory cs ON c.statusID = cs.statusID\n"
                + "LEFT JOIN GeneralInfoCar gi ON c.carID = gi.carID\n"
                + "LEFT JOIN CarDesign cd ON gi.designID = cd.designID\n"
                + "LEFT JOIN EngineAndChassis ec ON c.carID = ec.carID\n"
                + "LEFT JOIN CarIMG ci ON c.carID = ci.carID\n"
                + "LEFT JOIN CarSubIMG csi ON c.carID = csi.carID\n"
                + "where ";

        String[] keywords = name.split("\\s+"); // Tách các từ khóa trong title

        for (int i = 0; i < keywords.length; i++) {
            if (i == 0) {
                query += "(";
            }
            query += "c.carName LIKE ? OR cb.brandName LIKE ? OR cd.design LIKE ?";
            if (i < keywords.length - 1) {
                query += " OR ";
            }
            if (i == keywords.length - 1) {
                query += ")";
            }
        }

        if (brandID != 0) {
            query += " and c.brandID = ? ";
        }

        if (designID != 0) {
            query += " and cd.designID = ? ";
        }

        if (statusID != 0) {
            query += " and cs.statusID = ? ";
        }

        query += "\nAND csi.carSubIMGTypeID = 1"
                + "\nORDER BY c.carID\n"
                + "OFFSET ? ROW FETCH NEXT ? ROW ONLY";
        try {
            PreparedStatement stm = connection.prepareStatement(query);
            for (int i = 0; i < keywords.length; i++) {
                stm.setString(i * 3 + 1, "%" + keywords[i] + "%");
                stm.setString(i * 3 + 2, "%" + keywords[i] + "%");
                stm.setString(i * 3 + 3, "%" + keywords[i] + "%");
            }

            int position = keywords.length * 3;

            if (brandID != 0) {
                position += 1;
                stm.setInt(position, brandID);
            }

            if (designID != 0) {
                position += 1;
                stm.setInt(position, designID);
            }

            if (statusID != 0) {
                position += 1;
                stm.setInt(position, statusID);
            }

            stm.setInt(position + 1, (index - 1) * quantityShow);
            stm.setInt(position + 2, quantityShow);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                brandID = rs.getInt("brandID");
                String brandName = rs.getString("brandName");
                statusID = rs.getInt("statusID");
                String statusName = rs.getString("statusName");
                designID = rs.getInt("designID");
                String design = rs.getString("design");
                String carIMG = rs.getString("carIMG");
                String carSubIMG = rs.getString("carSubIMG");
                CarStaffPage a = new CarStaffPage(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getLong(3),
                        new CarBrand(brandID, brandName),
                        new StatusCategory(statusID, statusName),
                        new CarDesign(designID, design),
                        new CarIMG(carIMG),
                        new CarSubIMG(carSubIMG),
                        rs.getString(4),
                        rs.getInt(5),
                        rs.getString(6),
                        rs.getString(7),
                        rs.getString(8),
                        rs.getFloat(9));
                list.add(a);
            }
        } catch (Exception e) {

        }
        return list;
    }

    public List<Car> getListCarCE(int brandID, int designID) {
        List<Car> list = new ArrayList<>();
        String query = "select c.carID, c.carName, c.price from Car c\n"
                + "left join GeneralInfoCar gi on c.carID = gi.carID\n"
                + "left join CarDesign cd on gi.designID = cd.designID\n"
                + "where statusID = 1\n"
                + "and c.brandID = ?\n"
                + "and cd.designID = ?";

        try {
            PreparedStatement stm = connection.prepareStatement(query);
            stm.setInt(1, brandID);
            stm.setInt(2, designID);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Car a = new Car(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getLong(3));
                list.add(a);
            }
        } catch (Exception e) {

        }
        return list;
    }

    public List<CarStaffPage> getListCarHomeAjax(int amount, String name, int quantityShow,
            int brandID, int designID, int statusID, String fuel, double minPrice, double maxPrice,
            String gear, int seat) {
        List<CarStaffPage> list = new ArrayList<>();
        String query = "SELECT DISTINCT c.carID, c.carName, c.price,\n"
                + "gi.madeIn, gi.numberOfSeat, gi.fuel, ec.engineType, ec.gear, ec.cylinderCapacity,\n"
                + "c.brandID, c.statusID, cb.brandName, cs.statusName, gi.designID, cd.design,\n"
                + "ci.carIMG, csi.carSubIMGTypeID, csi.carSubIMG\n"
                + "FROM Car c\n"
                + "LEFT JOIN CarBrand cb ON c.brandID = cb.brandID\n"
                + "LEFT JOIN StatusCategory cs ON c.statusID = cs.statusID\n"
                + "LEFT JOIN GeneralInfoCar gi ON c.carID = gi.carID\n"
                + "LEFT JOIN CarDesign cd ON gi.designID = cd.designID\n"
                + "LEFT JOIN EngineAndChassis ec ON c.carID = ec.carID\n"
                + "LEFT JOIN CarIMG ci ON c.carID = ci.carID\n"
                + "LEFT JOIN CarSubIMG csi ON c.carID = csi.carID\n"
                + "where ";

        String[] keywords = name.split("\\s+"); // Tách các từ khóa trong title

        for (int i = 0; i < keywords.length; i++) {
            if (i == 0) {
                query += "(";
            }
            query += "c.carName LIKE ? OR cb.brandName LIKE ? OR cd.design LIKE ?";
            if (i < keywords.length - 1) {
                query += " OR ";
            }
            if (i == keywords.length - 1) {
                query += ")";
            }
        }

        if (brandID != 0) {
            query += " and c.brandID = ? ";
        }

        if (designID != 0) {
            query += " and cd.designID = ? ";
        }

        if (statusID != 0) {
            query += " and cs.statusID = ? ";
        }

        if (seat != 0) {
            query += " and gi.numberOfSeat = ? ";
        }

        if (!(fuel == null || fuel.equals(""))) {
            query += " and gi.fuel = ? ";
        }

        if (!(gear == null || gear.equals(""))) {
            query += " and ec.gear = ? ";
        }

        query += " and c.price >= ? and c.price <= ?";

        query += "\nAND csi.carSubIMGTypeID = 1"
                + "\nORDER BY c.carID\n"
                + "OFFSET ? ROW FETCH NEXT ? ROW ONLY";
        try {
            PreparedStatement stm = connection.prepareStatement(query);
            for (int i = 0; i < keywords.length; i++) {
                stm.setString(i * 3 + 1, "%" + keywords[i] + "%");
                stm.setString(i * 3 + 2, "%" + keywords[i] + "%");
                stm.setString(i * 3 + 3, "%" + keywords[i] + "%");
            }

            int position = keywords.length * 3;

            if (brandID != 0) {
                position += 1;
                stm.setInt(position, brandID);
            }

            if (designID != 0) {
                position += 1;
                stm.setInt(position, designID);
            }

            if (statusID != 0) {
                position += 1;
                stm.setInt(position, statusID);
            }

            if (seat != 0) {
                position += 1;
                stm.setInt(position, seat);
            }

            if (!(fuel == null || fuel.equals(""))) {
                position += 1;
                stm.setString(position, fuel);
            }

            if (!(gear == null || gear.equals(""))) {
                position += 1;
                stm.setString(position, gear);
            }

            position += 1;
            stm.setDouble(position, minPrice);

            position += 1;
            stm.setDouble(position, maxPrice);

            position += 1;
            stm.setInt(position, amount);

            position += 1;
            stm.setInt(position, quantityShow);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                brandID = rs.getInt("brandID");
                String brandName = rs.getString("brandName");
                statusID = rs.getInt("statusID");
                String statusName = rs.getString("statusName");
                designID = rs.getInt("designID");
                String design = rs.getString("design");
                String carIMG = rs.getString("carIMG");
                String carSubIMG = rs.getString("carSubIMG");
                CarStaffPage a = new CarStaffPage(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getLong(3),
                        new CarBrand(brandID, brandName),
                        new StatusCategory(statusID, statusName),
                        new CarDesign(designID, design),
                        new CarIMG(carIMG),
                        new CarSubIMG(carSubIMG),
                        rs.getString(4),
                        rs.getInt(5),
                        rs.getString(6),
                        rs.getString(7),
                        rs.getString(8),
                        rs.getFloat(9));
                list.add(a);
            }
        } catch (Exception e) {

        }
        return list;
    }

    public String getQuery(int amount, String name, int quantityShow,
            int brandID, int designID, int statusID, String fuel, double minPrice, double maxPrice,
            String gear, int seat) {
        String query = "SELECT DISTINCT c.carID, c.carName, c.price,\n"
                + "gi.madeIn, gi.numberOfSeat, gi.fuel, ec.engineType, ec.gear, ec.cylinderCapacity,\n"
                + "c.brandID, c.statusID, cb.brandName, cs.statusName, gi.designID, cd.design,\n"
                + "ci.carIMG, csi.carSubIMGTypeID, csi.carSubIMG\n"
                + "FROM Car c\n"
                + "LEFT JOIN CarBrand cb ON c.brandID = cb.brandID\n"
                + "LEFT JOIN StatusCategory cs ON c.statusID = cs.statusID\n"
                + "LEFT JOIN GeneralInfoCar gi ON c.carID = gi.carID\n"
                + "LEFT JOIN CarDesign cd ON gi.designID = cd.designID\n"
                + "LEFT JOIN EngineAndChassis ec ON c.carID = ec.carID\n"
                + "LEFT JOIN CarIMG ci ON c.carID = ci.carID\n"
                + "LEFT JOIN CarSubIMG csi ON c.carID = csi.carID\n"
                + "where ";

        String[] keywords = name.split("\\s+"); // Tách các từ khóa trong title

        for (int i = 0; i < keywords.length; i++) {
            if (i == 0) {
                query += "(";
            }
            query += "c.carName LIKE ? OR cb.brandName LIKE ? OR cd.design LIKE ?";
            if (i < keywords.length - 1) {
                query += " OR ";
            }
            if (i == keywords.length - 1) {
                query += ")";
            }
        }

        if (brandID != 0) {
            query += " and c.brandID = ? ";
        }

        if (designID != 0) {
            query += " and cd.designID = ? ";
        }

        if (statusID != 0) {
            query += " and cs.statusID = ? ";
        }

        if (seat != 0) {
            query += " and gi.numberOfSeat = ? ";
        }

        if (!(fuel.equals(null) || fuel.equals(""))) {
            query += " and gi.fuel like ? ";
        }

        if (!(gear.equals(null) || gear.equals(""))) {
            query += " and ec.gear like ? ";
        }

        query += " and c.price >= ? and c.price <= ?";

        query += "\nAND csi.carSubIMGTypeID = 1"
                + "\nORDER BY c.carID\n"
                + "OFFSET ? ROW FETCH NEXT ? ROW ONLY";
        try {
            PreparedStatement stm = connection.prepareStatement(query);
            for (int i = 0; i < keywords.length; i++) {
                stm.setString(i * 3 + 1, "%" + keywords[i] + "%");
                stm.setString(i * 3 + 2, "%" + keywords[i] + "%");
                stm.setString(i * 3 + 3, "%" + keywords[i] + "%");
            }

            int position = keywords.length * 3;

            if (brandID != 0) {
                position += 1;
                stm.setInt(position, brandID);
            }

            if (designID != 0) {
                position += 1;
                stm.setInt(position, designID);
            }

            if (statusID != 0) {
                position += 1;
                stm.setInt(position, statusID);
            }

            if (seat != 0) {
                position += 1;
                stm.setInt(position, seat);
            }

            if (fuel.equals(null) || fuel.equals("")) {
                position += 1;
                stm.setString(position, fuel);
            }

            if (gear.equals(null) || gear.equals("")) {
                position += 1;
                stm.setString(position, gear);
            }

            position += 1;
            stm.setDouble(position, minPrice);

            position += 1;
            stm.setDouble(position, maxPrice);

            position += 1;
            stm.setInt(position, amount);

            position += 1;
            stm.setInt(position, quantityShow);
        } catch (Exception e) {

        }
        return query;
    }

    public CarStaffPage getCarByIDHome(int carID) {
        String query = "SELECT DISTINCT c.carID, c.carName, c.price,\n"
                + "gi.madeIn, gi.numberOfSeat, gi.fuel, gi.description, ec.engineType, ec.gear, ec.cylinderCapacity,\n"
                + "c.brandID, c.statusID, cb.brandName, cs.statusName, gi.designID, cd.design,\n"
                + "ci.carIMG, csi.carSubIMGTypeID, csi.carSubIMG\n"
                + "FROM Car c\n"
                + "LEFT JOIN CarBrand cb ON c.brandID = cb.brandID\n"
                + "LEFT JOIN StatusCategory cs ON c.statusID = cs.statusID\n"
                + "LEFT JOIN GeneralInfoCar gi ON c.carID = gi.carID\n"
                + "LEFT JOIN CarDesign cd ON gi.designID = cd.designID\n"
                + "LEFT JOIN EngineAndChassis ec ON c.carID = ec.carID\n"
                + "LEFT JOIN CarIMG ci ON c.carID = ci.carID\n"
                + "LEFT JOIN CarSubIMG csi ON c.carID = csi.carID\n"
                + "where c.carID = ?";

        // Create a prepared statement
        try {
            PreparedStatement stm = connection.prepareStatement(query);
            stm.setInt(1, carID);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                int brandID = rs.getInt("brandID");
                String brandName = rs.getString("brandName");
                int statusID = rs.getInt("statusID");
                String statusName = rs.getString("statusName");
                int designID = rs.getInt("designID");
                String design = rs.getString("design");
                String carIMG = rs.getString("carIMG");
                String carSubIMG = rs.getString("carSubIMG");
                CarStaffPage car = new CarStaffPage(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getLong(3),
                        new CarBrand(brandID, brandName),
                        new StatusCategory(statusID, statusName),
                        new CarDesign(designID, design),
                        new CarIMG(carIMG),
                        new CarSubIMG(carSubIMG),
                        rs.getString(4),
                        rs.getInt(5),
                        rs.getString(6),
                        rs.getString(7),
                        rs.getString(8),
                        rs.getString(9),
                        rs.getFloat(10));
                return (car);
            }
        } catch (Exception e) {

        }
        return null;
    }

    public Car getCarbyID(int carID) {
        String query = "SELECT [carID],[carName] FROM Car where carID = ?";

        // Create a prepared statement
        try {
            PreparedStatement stm = connection.prepareStatement(query);
            stm.setInt(1, carID);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Car car = new Car(
                        rs.getInt(1),
                        rs.getString(2)
                );
                return (car);
            }
        } catch (Exception e) {

        }
        return null;
    }

//    public int getTotalCar() {
//        String query = "SELECT Count(carID) from Car";
//        try {
//            PreparedStatement stm = connection.prepareStatement(query);
//            ResultSet rs = stm.executeQuery();
//            while (rs.next()) {
//                return rs.getInt(1);
//            }
//        } catch (Exception e) {
//
//        }
//        return 0;
//    }
//    public int getTotalCarByName(String name) {
//        String query = "SELECT Count(carID) from Car "
//                + "where carName like ?";
//        try {
//            PreparedStatement stm = connection.prepareStatement(query);
//            stm.setString(1, "%" + name + "%");
//            ResultSet rs = stm.executeQuery();
//            while (rs.next()) {
//                return rs.getInt(1);
//            }
//        } catch (Exception e) {
//
//        }
//        return 0;
//    }
//    public int getTotalCarAdmin(String name, int brandID, int designID, int statusID) {
//        String query = "SELECT Count(c.carID) from Car c "
//                + "LEFT JOIN CarBrand cb ON c.brandID = cb.brandID\n"
//                + "LEFT JOIN StatusCategory cs ON c.statusID = cs.statusID\n"
//                + "LEFT JOIN GeneralInfoCar gi ON c.carID = gi.carID\n"
//                + "LEFT JOIN CarDesign cd ON gi.designID = cd.designID\n"
//                + "where CarName like ?\n";
//
//        if (brandID != 0) {
//            query += "and c.brandID = ?\n";
//        }
//
//        if (designID != 0) {
//            query += "and cd.designID = ?\n";
//        }
//
//        if (statusID != 0) {
//            query += "and cs.statusID = ?\n";
//        }
//        try {
//            PreparedStatement stm = connection.prepareStatement(query);
//            int position = 1;
//            stm.setString(position, "%" + name + "%");
//            if (brandID != 0) {
//                position += 1;
//                stm.setInt(position, brandID);
//            }
//
//            if (designID != 0) {
//                position += 1;
//                stm.setInt(position, designID);
//            }
//
//            if (statusID != 0) {
//                position += 1;
//                stm.setInt(position, statusID);
//            }
//            ResultSet rs = stm.executeQuery();
//            while (rs.next()) {
//                return rs.getInt(1);
//            }
//        } catch (Exception e) {
//
//        }
//        return 0;
//    }
//    public int getTotalCarAdmin(String name, int brandID, int designID, int statusID) {
//        String query = "SELECT Count(c.carID) from Car c "
//                + "LEFT JOIN CarBrand cb ON c.brandID = cb.brandID\n"
//                + "LEFT JOIN StatusCategory cs ON c.statusID = cs.statusID\n"
//                + "LEFT JOIN GeneralInfoCar gi ON c.carID = gi.carID\n"
//                + "LEFT JOIN CarDesign cd ON gi.designID = cd.designID\n"
//                + "where ";
//
//        String[] keywords = name.split("\\s+"); // Tách các từ khóa trong title
//
//        for (int i = 0; i < keywords.length; i++) {
//            if (i == 0) {
//                query += "(";
//            }
//            query += "c.carName LIKE ? OR cb.brandName LIKE ? OR cd.design LIKE ?";
//            if (i < keywords.length - 1) {
//                query += " OR ";
//            }
//            if (i == keywords.length - 1) {
//                query += ")";
//            }
//        }
//
//        if (brandID != 0) {
//            query += " and c.brandID = ? ";
//        }
//
//        if (designID != 0) {
//            query += " and cd.designID = ? ";
//        }
//
//        if (statusID != 0) {
//            query += " and cs.statusID = ? ";
//        }
//
//        try {
//            PreparedStatement stm = connection.prepareStatement(query);
//            for (int i = 0; i < keywords.length; i++) {
//                stm.setString(i * 3 + 1, "%" + keywords[i] + "%");
//                stm.setString(i * 3 + 2, "%" + keywords[i] + "%");
//                stm.setString(i * 3 + 3, "%" + keywords[i] + "%");
//            }
//
//            int position = keywords.length * 3;
//            if (brandID != 0) {
//                position += 1;
//                stm.setInt(position, brandID);
//            }
//
//            if (designID != 0) {
//                position += 1;
//                stm.setInt(position, designID);
//            }
//
//            if (statusID != 0) {
//                position += 1;
//                stm.setInt(position, statusID);
//            }
//            ResultSet rs = stm.executeQuery();
//            while (rs.next()) {
//                return rs.getInt(1);
//            }
//        } catch (Exception e) {
//
//        }
//        return 0;
//    }
    public int getTotalCarAdmin(String name, int brandID, int designID, int statusID, double minPrice, double maxPrice) {
        String query = "SELECT Count(c.carID) from Car c "
                + "LEFT JOIN CarBrand cb ON c.brandID = cb.brandID\n"
                + "LEFT JOIN StatusCategory cs ON c.statusID = cs.statusID\n"
                + "LEFT JOIN GeneralInfoCar gi ON c.carID = gi.carID\n"
                + "LEFT JOIN CarDesign cd ON gi.designID = cd.designID\n"
                + "where ";

        String[] keywords = name.split("\\s+"); // Tách các từ khóa trong title

        for (int i = 0; i < keywords.length; i++) {
            if (i == 0) {
                query += "(";
            }
            query += "c.carName LIKE ? OR cb.brandName LIKE ? OR cd.design LIKE ?";
            if (i < keywords.length - 1) {
                query += " OR ";
            }
            if (i == keywords.length - 1) {
                query += ")";
            }
        }

        if (brandID != 0) {
            query += " and c.brandID = ? ";
        }

        if (designID != 0) {
            query += " and cd.designID = ? ";
        }

        if (statusID != 0) {
            query += " and cs.statusID = ? ";
        }
        query += "\nand c.price >= ? and c.price <= ?\n";

        try {
            PreparedStatement stm = connection.prepareStatement(query);
            for (int i = 0; i < keywords.length; i++) {
                stm.setString(i * 3 + 1, "%" + keywords[i] + "%");
                stm.setString(i * 3 + 2, "%" + keywords[i] + "%");
                stm.setString(i * 3 + 3, "%" + keywords[i] + "%");
            }

            int position = keywords.length * 3;
            if (brandID != 0) {
                position += 1;
                stm.setInt(position, brandID);
            }

            if (designID != 0) {
                position += 1;
                stm.setInt(position, designID);
            }

            if (statusID != 0) {
                position += 1;
                stm.setInt(position, statusID);
            }
            stm.setDouble(position + 1, minPrice);
            stm.setDouble(position + 2, maxPrice);

            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {

        }
        return 0;
    }

    public int getTotalCarHome(String name, int brandID, int designID, int statusID) {
        String query = "SELECT Count(c.carID) from Car c "
                + "LEFT JOIN CarBrand cb ON c.brandID = cb.brandID\n"
                + "LEFT JOIN StatusCategory cs ON c.statusID = cs.statusID\n"
                + "LEFT JOIN GeneralInfoCar gi ON c.carID = gi.carID\n"
                + "LEFT JOIN CarDesign cd ON gi.designID = cd.designID\n"
                + "where ";

        String[] keywords = name.split("\\s+"); // Tách các từ khóa trong title

        for (int i = 0; i < keywords.length; i++) {
            if (i == 0) {
                query += "(";
            }
            query += "c.carName LIKE ? OR cb.brandName LIKE ? OR cd.design LIKE ?";
            if (i < keywords.length - 1) {
                query += " OR ";
            }
            if (i == keywords.length - 1) {
                query += ")";
            }
        }

        if (brandID != 0) {
            query += " and c.brandID = ? ";
        }

        if (designID != 0) {
            query += " and cd.designID = ? ";
        }

        if (statusID != 0) {
            query += " and cs.statusID = ? ";
        }

        try {
            PreparedStatement stm = connection.prepareStatement(query);
            for (int i = 0; i < keywords.length; i++) {
                stm.setString(i * 3 + 1, "%" + keywords[i] + "%");
                stm.setString(i * 3 + 2, "%" + keywords[i] + "%");
                stm.setString(i * 3 + 3, "%" + keywords[i] + "%");
            }

            int position = keywords.length * 3;
            if (brandID != 0) {
                position += 1;
                stm.setInt(position, brandID);
            }

            if (designID != 0) {
                position += 1;
                stm.setInt(position, designID);
            }

            if (statusID != 0) {
                position += 1;
                stm.setInt(position, statusID);
            }
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {

        }
        return 0;
    }

    public List<StatusCategory> getAllStatus() {
        List<StatusCategory> list = new ArrayList<>();
        String query = "select statusID, statusName from StatusCategory\n";
        try {
            PreparedStatement stm = connection.prepareStatement(query);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                StatusCategory s = new StatusCategory(rs.getInt(1), rs.getString(2));
                list.add(s);
            }
        } catch (Exception e) {

        }
        return list;
    }

    public List<String> getAllFuel() {
        List<String> list = new ArrayList<>();
        String query = "SELECT DISTINCT fuel FROM GeneralInfoCar\n";
        try {
            PreparedStatement stm = connection.prepareStatement(query);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                list.add(rs.getString(1));
            }
        } catch (Exception e) {

        }
        return list;
    }

    public List<String> getAllGear() {
        List<String> list = new ArrayList<>();
        String query = "SELECT DISTINCT gear FROM EngineAndChassis\n";
        try {
            PreparedStatement stm = connection.prepareStatement(query);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                list.add(rs.getString(1));
            }
        } catch (Exception e) {

        }
        return list;
    }

    public List<Integer> getAllNumberOfSeat() {
        List<Integer> list = new ArrayList<>();
        String query = "SELECT DISTINCT numberOfSeat FROM GeneralInfoCar\n";
        try {
            PreparedStatement stm = connection.prepareStatement(query);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                list.add(rs.getInt(1));
            }
        } catch (Exception e) {

        }
        return list;
    }

    public void addCar(String carName, String price, String brandID, String statusID, int createdBy) {
        try {
            // SQL insert statement
            String sql = "Insert into Car (carName, price, brandID, statusID, createdBy, createdOn) values (?, ?, ?, ?, ?, GETDATE())";

            // Create a prepared statement
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, carName);
            stm.setString(2, price);
            stm.setString(3, brandID);
            stm.setString(4, statusID);
            stm.setInt(5, createdBy);

            // Execute the insert statement
            stm.executeUpdate();

            // Handle success or error here
        } catch (SQLException e) {
            // Handle any errors that may occur
            e.printStackTrace();
            // Handle the error here
        }
    }

    public int getNewestCarID() {
        String query = "SELECT Top 1 carID from Car c \n"
                + "order by carID desc";

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

    public void addGeneralInfoCar(int carID, String numberOfSeat, String designID, String madeIn, String fuel, String description) {
        try {
            // SQL insert statement
            String sql = "Insert into GeneralInfoCar (carID, numberOfSeat, designID, madeIn, fuel, description) values (?, ?, ?, ?, ?, ?)";

            // Create a prepared statement
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, carID);
            stm.setString(2, numberOfSeat);
            stm.setString(3, designID);
            stm.setString(4, madeIn);
            stm.setString(5, fuel);
            stm.setString(6, description);

            // Execute the insert statement
            stm.executeUpdate();

            // Handle success or error here
        } catch (SQLException e) {
            // Handle any errors that may occur
            e.printStackTrace();
            // Handle the error here
        }
    }

    public void addEngine(int carID, String fuelTankCapacity, String engineType, String numberOfCylinder, String cylinderCapacity, String variableValveSystem, String fuelSystem, String maximumCapacity, String maximumTorque, String gear) {
        try {
            // SQL insert statement
            String sql = "Insert into EngineAndChassis (carID, fuelTankCapacity, engineType, numberOfCylinder, cylinderCapacity, variableValveSystem, fuelSystem, maximumCapacity, maximumTorque, gear) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            // Create a prepared statement
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, carID);
            stm.setString(2, fuelTankCapacity);
            stm.setString(3, engineType);
            stm.setString(4, numberOfCylinder);
            stm.setString(5, cylinderCapacity);
            stm.setString(6, variableValveSystem);
            stm.setString(7, fuelSystem);
            stm.setString(8, maximumCapacity);
            stm.setString(9, maximumTorque);
            stm.setString(10, gear);

            // Execute the insert statement
            stm.executeUpdate();

            // Handle success or error here
        } catch (SQLException e) {
            // Handle any errors that may occur
            e.printStackTrace();
            // Handle the error here
        }
    }

    public void addImage(int carID, String carIMG) {
        try {
            // SQL insert statement
            String sql = "Insert into CarIMG (carID, carIMG) values (?, ?)";

            // Create a prepared statement
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, carID);
            stm.setString(2, carIMG);

            // Execute the insert statement
            stm.executeUpdate();

            // Handle success or error here
        } catch (SQLException e) {
            // Handle any errors that may occur
            e.printStackTrace();
            // Handle the error here
        }
    }

    public void addSubImage(int carID, String carSubIMG) {
        try {
            // SQL insert statement
            String sql = "Insert into CarSubIMG (carID,carSubIMGTypeID, carSubIMG) values (?, 1, ?)";

            // Create a prepared statement
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, carID);
            stm.setString(2, carSubIMG);

            // Execute the insert statement
            stm.executeUpdate();

            // Handle success or error here
        } catch (SQLException e) {
            // Handle any errors that may occur
            e.printStackTrace();
            // Handle the error here
        }
    }

    public void removeImage(int carID) {
        try {
            // SQL insert statement
            String sql = "delete from CarIMG where carID = ?";

            // Create a prepared statement
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, carID);

            // Execute the insert statement
            stm.executeUpdate();

            // Handle success or error here
        } catch (SQLException e) {
            // Handle any errors that may occur
            e.printStackTrace();
            // Handle the error here
        }
    }

    public void removeSubImage(int carID) {
        try {
            // SQL insert statement
            String sql = "delete from CarSubIMG where carID = ? and carSubIMGTypeID = 1";

            // Create a prepared statement
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, carID);

            // Execute the insert statement
            stm.executeUpdate();

            // Handle success or error here
        } catch (SQLException e) {
            // Handle any errors that may occur
            e.printStackTrace();
            // Handle the error here
        }
    }

    public String getMainImageName(String carID) {
        String query = "SELECT Top 1 carIMG from CarIMG \n"
                + "where carID = ? \n"
                + "order by carID desc";

        try {
            PreparedStatement stm = connection.prepareStatement(query);
            stm.setString(1, carID);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                return rs.getString(1);
            }
        } catch (Exception e) {

        }
        return "";
    }

    public String getSubImageName(int carID) {
        String query = "SELECT Top 1 carSubIMG from CarSubIMG \n"
                + "where carID = ? \n"
                + "order by carID desc";

        try {
            PreparedStatement stm = connection.prepareStatement(query);
            stm.setInt(1, carID);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                return rs.getString(1);
            }
        } catch (Exception e) {

        }
        return "";
    }

    public void updateCar(String carName, String price, String statusID, int carID, int modifiedBy) {
        try {
            // SQL insert statement
            String sql = "Update Car set carName = ?, price = ? , statusID = ?, modifiedBy = ?, modifiedOn = GETDATE() \n"
                    + "where carID = ?";

            // Create a prepared statement
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, carName);
            stm.setString(2, price);
            stm.setString(3, statusID);
            stm.setInt(4, modifiedBy);
            stm.setInt(5, carID);

            // Execute the insert statement
            stm.executeUpdate();

            // Handle success or error here
        } catch (SQLException e) {
            // Handle any errors that may occur
            e.printStackTrace();
            // Handle the error here
        }
    }

    public void updateCar(String carName, String price, String brandID, String statusID, int carID, int modifiedBy) {
        try {
            // SQL insert statement
            String sql = "Update Car set carName = ?, price = ?, brandID = ? , statusID = ?, modifiedBy = ?, modifiedOn = GETDATE() \n"
                    + "where carID = ?";

            // Create a prepared statement
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, carName);
            stm.setString(2, price);
            stm.setString(3, brandID);
            stm.setString(4, statusID);
            stm.setInt(5, modifiedBy);
            stm.setInt(6, carID);

            // Execute the insert statement
            stm.executeUpdate();

            // Handle success or error here
        } catch (SQLException e) {
            // Handle any errors that may occur
            e.printStackTrace();
            // Handle the error here
        }
    }

    public void updateGeneralInfoCar(int carID, String numberOfSeat, String designID, String madeIn, String fuel, String description) {
        try {
            // SQL insert statement
            String sql = "Update GeneralInfoCar set carID = ?, numberOfSeat = ?, designID = ?, madeIn = ?, fuel = ?, description = ?\n"
                    + "where carID = ?";
            // Create a prepared statement
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, carID);
            stm.setString(2, numberOfSeat);
            stm.setString(3, designID);
            stm.setString(4, madeIn);
            stm.setString(5, fuel);
            stm.setString(6, description);
            stm.setInt(7, carID);

            // Execute the insert statement
            stm.executeUpdate();

            // Handle success or error here
        } catch (SQLException e) {
            // Handle any errors that may occur
            e.printStackTrace();
            // Handle the error here
        }
    }

    public void updateEngine(int carID, String fuelTankCapacity, String engineType, String numberOfCylinder, String cylinderCapacity, String variableValveSystem, String fuelSystem, String maximumCapacity, String maximumTorque, String gear) {
        try {
            // SQL insert statement
            String sql = "Update EngineAndChassis set carID = ?, fuelTankCapacity = ?, engineType = ?, numberOfCylinder = ?, cylinderCapacity = ?, variableValveSystem = ?, fuelSystem = ?, maximumCapacity = ?, maximumTorque = ?, gear = ?\n"
                    + "where carID = ?";
            // Create a prepared statement
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, carID);
            stm.setString(2, fuelTankCapacity);
            stm.setString(3, engineType);
            stm.setString(4, numberOfCylinder);
            stm.setString(5, cylinderCapacity);
            stm.setString(6, variableValveSystem);
            stm.setString(7, fuelSystem);
            stm.setString(8, maximumCapacity);
            stm.setString(9, maximumTorque);
            stm.setString(10, gear);
            stm.setInt(11, carID);

            // Execute the insert statement
            stm.executeUpdate();

            // Handle success or error here
        } catch (SQLException e) {
            // Handle any errors that may occur
            e.printStackTrace();
            // Handle the error here
        }
    }

    public CarStaffPage getCarByID(int carID) {
        String query = "SELECT c.carID, c.carName, c.price, c.brandID, c.statusID, cb.brandName, cs.statusName, gi.designID, cd.design, "
                + "c.createdBy, a.accName,  c.createdOn, c.modifiedBy, b.accName as modName, c.modifiedOn\n"
                + "FROM Car c\n"
                + "LEFT JOIN CarBrand cb ON c.brandID = cb.brandID\n"
                + "LEFT JOIN StatusCategory cs ON c.statusID = cs.statusID\n"
                + "LEFT JOIN GeneralInfoCar gi ON c.carID = gi.carID\n"
                + "LEFT JOIN CarDesign cd ON gi.designID = cd.designID\n"
                + "LEFT JOIN Account a ON a.accID = c.createdBy\n"
                + "LEFT JOIN Account b ON b.accID = c.modifiedBy\n"
                + "where c.carID = ?";

        // Create a prepared statement
        try {
            PreparedStatement stm = connection.prepareStatement(query);
            stm.setInt(1, carID);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                int brandID = rs.getInt("brandID");
                String brandName = rs.getString("brandName");
                int statusID = rs.getInt("statusID");
                String statusName = rs.getString("statusName");
                int designID = rs.getInt("designID");
                String design = rs.getString("design");
                CarStaffPage car = new CarStaffPage(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getLong(3),
                        new CarBrand(brandID, brandName),
                        new StatusCategory(statusID, statusName),
                        new CarDesign(designID, design),
                        new Account(rs.getInt("createdBy"), rs.getString("accName")),
                        rs.getTimestamp("createdOn"),
                        new Account(rs.getInt("modifiedBy"), rs.getString("modName")),
                        rs.getTimestamp("modifiedOn"));
                return (car);
            }
        } catch (Exception e) {

        }
        return null;
    }

    public GeneralInfoCar getGeneralInfoCarByID(int carID) {
        String query = "select numberOfSeat, madeIn, fuel, description from GeneralInfoCar\n"
                + "where carID = ?";
        try {
            PreparedStatement stm = connection.prepareStatement(query);
            stm.setInt(1, carID);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                GeneralInfoCar a = new GeneralInfoCar(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4));
                return a;
            }
        } catch (Exception e) {

        }
        return null;
    }

    public EngineAndChassis getEngineAndChassisByID(int carID) {
        String query = "select fuelTankCapacity, engineType, numberOfCylinder, cylinderCapacity, variableValveSystem, fuelSystem, maximumCapacity, maximumTorque, gear from EngineAndChassis\n"
                + "where carID = ?";
        try {
            PreparedStatement stm = connection.prepareStatement(query);
            stm.setInt(1, carID);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Car c = new Car(rs.getInt(1));
                EngineAndChassis a = new EngineAndChassis(rs.getFloat(1), rs.getString(2), rs.getString(3), rs.getFloat(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9));
                return a;
            }
        } catch (Exception e) {

        }
        return null;
    }

    public void updateDesign(String designName, int designID, int createdBy) {
        try {
            // SQL insert statement
            String sql = "update CarDesign set design = ?, createdBy = ?, createdOn = GETDATE()  where designID = ?";

            // Create a prepared statement
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, designName);
            stm.setInt(2, createdBy);
            stm.setInt(3, designID);

            // Execute the insert statement
            stm.executeUpdate();

            // Handle success or error here
        } catch (SQLException e) {
            // Handle any errors that may occur
            e.printStackTrace();
            // Handle the error here
        }
    }

    public void addDesign(String designName, int createdBy) {
        try {
            // SQL insert statement
            String sql = "Insert into CarDesign (design, createdBy, createdOn) values (?, ?, GETDATE())";

            // Create a prepared statement
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, designName);
            stm.setInt(2, createdBy);

            // Execute the insert statement
            stm.executeUpdate();

            // Handle success or error here
        } catch (SQLException e) {
            // Handle any errors that may occur
            e.printStackTrace();
            // Handle the error here
        }
    }

    public List<CarDesign> getAllDesignByName(int index, int quantity, String designName) {
        List<CarDesign> list = new ArrayList<>();
        try {
            String strSelect = "select designID, design from CarDesign \n"
                    + "where design like ?\n"
                    + "ORDER BY designID\n"
                    + "OFFSET ? ROW FETCH NEXT ? ROW ONLY";
            PreparedStatement ps = connection.prepareStatement(strSelect);
            ps.setString(1, "%" + designName + "%");
            ps.setInt(2, (index - 1) * quantity);
            ps.setInt(3, quantity);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                CarDesign cd = new CarDesign(
                        rs.getInt(1),
                        rs.getString(2));
                list.add(cd);
            }
            return list;
        } catch (Exception e) {
            System.out.println("checkBrand: " + e.getMessage());
        }
        return null;
    }

    public int getTotalDesignByName(String designName) {
        try {
            String strSelect = "select count(designID) from CarDesign \n"
                    + "where design like ?";
            PreparedStatement ps = connection.prepareStatement(strSelect);
            ps.setString(1, "%" + designName + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            System.out.println("checkBrand: " + e.getMessage());
        }
        return 0;
    }

    public String getCarNameByCarID(int carID) {
        String query = "SELECT carName FROM Car WHERE carID = ?";
        try {
            PreparedStatement stm = connection.prepareStatement(query);
            stm.setInt(1, carID);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                String name = rs.getString("carName");
                return name;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean carNameExist(String carName, int carID) {
        String query = "SELECT COUNT(*) FROM Car WHERE carName = ?";
        if (carID != 0) {
            query += " and carID != ?";
        }
        try {
            PreparedStatement stm = connection.prepareStatement(query);
            stm.setString(1, carName);
            if (carID != 0) {
                stm.setInt(2, carID);
            }
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                int count = rs.getInt(1);
                return (count > 0);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args) {
        CarDAO cdao = new CarDAO();
        List<CarIMG> carimg = cdao.getAllCarIMG();
        
        System.out.println(carimg);
//        List<CarStaffPage> CarStaffPage = cdao.getListCarAdmin(1, "a x d", 5, 0, 1, 2);
//        int count = cdao.getTotalCarAdmin("a x d", 0, 1, 2);
//        String ID = cdao.getNewestCarID();
//        String query = cdao.getQuery(1, "a x d", 5, 0, 1, 2);
//        CarStaffPage car = cdao.getCarByID("1");
//        GeneralInfoCar carGI = cdao.getGeneralInfoCarByID("1");
//        EngineAndChassis eac = cdao.getEngineAndChassisByID("1");
//        System.out.println(CarStaffPage.toString());
//        System.out.println(count);
//        System.out.println(ID);
//        System.out.println(query);
//        System.out.println(car);
//        System.out.println(carGI);
//        System.out.println(eac);
//        System.out.println(cdao.getMainImageName("1"));
//        System.out.println(cdao.getSubImageName("1"));
//        System.out.println(cdao.getAllDesignByName(1, 5, ""));
//        System.out.println(cdao.getTotalDesignByName(""));
//        System.out.println(cdao.getListCarHome(3, "", 4, 0, 0, 0));
//        System.out.println(cdao.getQuery(5, "", 4, 0, 0, 0));

//        cdao.updateGeneralInfoCar("15", "5", "1", "6", "6");
//        cdao.updateCar("15", "", "", "");
//        List<StatusCategory> status = cdao.getAllStatus();
//        System.out.println(status);
//        System.out.println(cdao.getMaxPriceCar("", 0, 0, 0));
//        System.out.println(cdao.getListCarAdmin(10, "", 1, 0, 0, 0, 0, cdao.getMaxPriceCar("", 0, 0, 0)));
//        System.out.println(cdao.getQuery(0, "", 0, 0, 0, 0, 0, cdao.getMaxPriceCar("", 0, 0, 0)));
//        System.out.println(cdao.getAllFuel());
//        System.out.println(cdao.getAllNumberOfSeat());
//        System.out.println(cdao.getAllGear());
//        System.out.println(cdao.getListCarHomeAjax(1, "", 4, 0, 0, 0, "Xăng", 0, cdao.getMaxPriceCar("", 0, 0, 0), "", 0));
//        System.out.println(cdao.getQuery(1, "", 4, 0, 0, 0, "Xăng", 0, cdao.getMaxPriceCar("", 0, 0, 0), "", 4));
//        System.out.println(cdao.getCarByID(1));
//        System.out.println(cdao.getCarByID(1));
//        System.out.println(cdao.getMaxPriceCar("", 0, 0, 0));
//        System.out.println(Double.toString(cdao.getMaxPriceCar("", 0, 0, 0)));
//        BigDecimal num1 = new BigDecimal (Double.toString(cdao.getMaxPriceCar("", 0, 0, 0)));
//        System.out.println(num1.toPlainString());
//        cdao.updateCar("c", "1000000000", "2", 1, 1);
//        cdao.addDesign("LBX", 1);
    }

}
