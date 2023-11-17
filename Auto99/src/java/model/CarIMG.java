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
public class CarIMG {

    private Car carID;
    private String carIMG;

    public CarIMG(String carIMG) {
        this.carIMG = carIMG;
    }
    
}
