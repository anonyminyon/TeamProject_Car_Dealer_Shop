
package model;
import java.sql.Timestamp;
import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Car {
    private int carID;
    private String carName;
    private long price;
    private CarBrand brandID;
    private StatusCategory status;
    private int createdBy; 
    private Timestamp createdOn; 
    private int modifiedBy; 
    private Timestamp modifiedOn;
    
    
    public Car(int carID) {
        this.carID = carID;
    }

    public Car(int carID, String carName) {
        this.carID = carID;
        this.carName = carName;
    }

    public Car(int carID, String carName, long price, CarBrand brandID) {
        this.carID = carID;
        this.carName = carName;
        this.price = price;
        this.brandID = brandID;
    }

    public Car(String carName) {
        this.carName = carName;
    }

    
    public Car(int carID, String carName, long price) {
        this.carID = carID;
        this.carName = carName;
        this.price = price;
    }

}
