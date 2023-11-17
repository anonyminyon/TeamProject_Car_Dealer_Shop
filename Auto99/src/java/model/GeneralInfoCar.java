package model;

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
public class GeneralInfoCar {

    private Car carID;
    private int numberOfSeat;
    private CarDesign design;
    private String madeIn;
    private String fuel;
    private String description;

    public GeneralInfoCar(int numberOfSeat, String madeIn, String fuel, String description) {
        this.numberOfSeat = numberOfSeat;
        this.madeIn = madeIn;
        this.fuel = fuel;
        this.description = description;
    }

    public GeneralInfoCar(Car carID, int numberOfSeat, CarDesign design, String madeIn, String fuel) {
        this.carID = carID;
        this.numberOfSeat = numberOfSeat;
        this.design = design;
        this.madeIn = madeIn;
        this.fuel = fuel;
    }

    
    
}
