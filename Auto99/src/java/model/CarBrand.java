package model;

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
public class CarBrand {

    private int brandID;
    private String brandName;
    private String brandIMG;
    private Account createdBy;
    private Timestamp createdOn;
    private Account modifiedBy;
    private Timestamp modifiedOn;

    public CarBrand(int brandID) {
        this.brandID = brandID;
    }

    public CarBrand(String brandName) {
        this.brandName = brandName;
    }

    public CarBrand(int brandID, String brandName) {
        this.brandID = brandID;
        this.brandName = brandName;
    }

    public CarBrand(int brandID, String brandName, String brandIMG) {
        this.brandID = brandID;
        this.brandName = brandName;
        this.brandIMG = brandIMG;
    }
    
    

}
