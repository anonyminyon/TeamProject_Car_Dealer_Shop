package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarsHomePage {

    private int carID;
    private String carName;
    private long price;
    private String design;
    private int numberOfSeat;
    private String engineType;
    private float cylinderCapacity;
    private String gear;
}
