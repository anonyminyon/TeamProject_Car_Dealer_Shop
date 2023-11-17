package model;

import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarDesign {

    private int designID;
    private String design;
    private Account createdBy;
    private Timestamp createdOn;
    private Account modifiedBy;
    private Timestamp modifiedOn;

    public CarDesign(String design) {
        this.design = design;
    }

    public CarDesign(int designID, String design) {
        this.designID = designID;
        this.design = design;
    }

    
}
