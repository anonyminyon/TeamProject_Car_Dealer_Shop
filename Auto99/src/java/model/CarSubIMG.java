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

public class CarSubIMG {

    private Car carID;
    private CarSubIMGType carSubIMGTypeID;
    private String carSubIMG;

    public CarSubIMG(String carSubIMG) {
        this.carSubIMG = carSubIMG;
    }
    
}
