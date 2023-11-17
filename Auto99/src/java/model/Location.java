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
public class Location {

    int locationID;
    String locationName;
    Long preRegFee;
    Long regFee;
    private Account createdBy;
    private Timestamp createdOn;
    private Account modifiedBy;
    private Timestamp modifiedOn;

    public Location(int locationID, String locationName, Long preRegFee, Long regFee) {
        this.locationID = locationID;
        this.locationName = locationName;
        this.preRegFee = preRegFee;
        this.regFee = regFee;
    }
    
    

}
