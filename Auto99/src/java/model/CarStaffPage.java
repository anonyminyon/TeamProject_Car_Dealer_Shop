package model;

import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class CarStaffPage {

    private int carID;
    private String carName;
    private long price;
    private CarBrand brandID;
    private StatusCategory status;
    private CarDesign design;
    private CarIMG carIMG;
    private CarSubIMG carSubIMG;
    private String madeIn;
    private int numberOfSeat;
    private String fuel;
    private String engineType;
    private String gear;
    private float cylinderCapacity;
    private String description;
    private Account createdBy;
    private Timestamp createdOn;
    private Account modifiedBy;
    private Timestamp modifiedOn;

    public CarStaffPage(int carID, String carName, long price, CarBrand brandID, StatusCategory status, CarDesign design) {
        this.carID = carID;
        this.carName = carName;
        this.price = price;
        this.brandID = brandID;
        this.status = status;
        this.design = design;
    }

    public CarStaffPage(int carID, String carName, long price, CarBrand brandID, StatusCategory status, CarDesign design, CarIMG carIMG, CarSubIMG carSubIMG, String madeIn, int numberOfSeat, String fuel, String engineType, String gear, float cylinderCapacity) {
        this.carID = carID;
        this.carName = carName;
        this.price = price;
        this.brandID = brandID;
        this.status = status;
        this.design = design;
        this.carIMG = carIMG;
        this.carSubIMG = carSubIMG;
        this.madeIn = madeIn;
        this.numberOfSeat = numberOfSeat;
        this.fuel = fuel;
        this.engineType = engineType;
        this.gear = gear;
        this.cylinderCapacity = cylinderCapacity;
    }

    public CarStaffPage(int carID, String carName, long price, CarBrand brandID, StatusCategory status, CarDesign design, CarIMG carIMG, CarSubIMG carSubIMG, String madeIn, int numberOfSeat, String fuel, String description, String engineType, String gear, float cylinderCapacity) {
        this.carID = carID;
        this.carName = carName;
        this.price = price;
        this.brandID = brandID;
        this.status = status;
        this.design = design;
        this.carIMG = carIMG;
        this.carSubIMG = carSubIMG;
        this.madeIn = madeIn;
        this.numberOfSeat = numberOfSeat;
        this.fuel = fuel;
        this.description = description;
        this.engineType = engineType;
        this.gear = gear;
        this.cylinderCapacity = cylinderCapacity;
    }

    public CarStaffPage(int carID, String carName, long price, CarBrand brandID, StatusCategory status, CarDesign design, Account createdBy, Timestamp createdOn, Account modifiedBy, Timestamp modifiedOn) {
        this.carID = carID;
        this.carName = carName;
        this.price = price;
        this.brandID = brandID;
        this.status = status;
        this.design = design;
        this.createdBy = createdBy;
        this.createdOn = createdOn;
        this.modifiedBy = modifiedBy;
        this.modifiedOn = modifiedOn;
    }

}
