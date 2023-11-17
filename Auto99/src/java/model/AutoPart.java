package model;

import java.sql.Timestamp;
import java.util.List;
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
public class AutoPart {

    private int autoPartID;
    private List<Car> carID;
    private String partName;
    private long price;
    private String partIMG;
    private boolean status;
    private String description;
    private Account createdBy;
    private Timestamp createdOn;
    private Account modifiedBy;
    private Timestamp modifiedOn;

//    public AutoPart() {
//    }
//
//    public AutoPart(int autoPartID, List<Car> carID, String partName, long price, String partIMG, boolean status, String description, Account createdBy, Timestamp createdOn, Account modifiedBy, Timestamp modifiedOn) {
//        this.autoPartID = autoPartID;
//        this.carID = carID;
//        this.partName = partName;
//        this.price = price;
//        this.partIMG = partIMG;
//        this.status = status;
//        this.description = description;
//        this.createdBy = createdBy;
//        this.createdOn = createdOn;
//        this.modifiedBy = modifiedBy;
//        this.modifiedOn = modifiedOn;
//    }
//
//    public int getAutoPartID() {
//        return autoPartID;
//    }
//
//    public List<Car> getCarID() {
//        return carID;
//    }
//
//    public String getPartName() {
//        return partName;
//    }
//
//    public long getPrice() {
//        return price;
//    }
//
//    public String getPartIMG() {
//        return partIMG;
//    }
//
//    public boolean isStatus() {
//        return status;
//    }
//
//    public String getDescription() {
//        return description;
//    }
//
//    public Account getCreatedBy() {
//        return createdBy;
//    }
//
//    public Timestamp getCreatedOn() {
//        return createdOn;
//    }
//
//    public Account getModifiedBy() {
//        return modifiedBy;
//    }
//
//    public Timestamp getModifiedOn() {
//        return modifiedOn;
//    }
//
//    public void setAutoPartID(int autoPartID) {
//        this.autoPartID = autoPartID;
//    }
//
//    public void setCarID(List<Car> carID) {
//        this.carID = carID;
//    }
//
//    public void setPartName(String partName) {
//        this.partName = partName;
//    }
//
//    public void setPrice(long price) {
//        this.price = price;
//    }
//
//    public void setPartIMG(String partIMG) {
//        this.partIMG = partIMG;
//    }
//
//    public void setStatus(boolean status) {
//        this.status = status;
//    }
//
//    public void setDescription(String description) {
//        this.description = description;
//    }
//
//    public void setCreatedBy(Account createdBy) {
//        this.createdBy = createdBy;
//    }
//
//    public void setCreatedOn(Timestamp createdOn) {
//        this.createdOn = createdOn;
//    }
//
//    public void setModifiedBy(Account modifiedBy) {
//        this.modifiedBy = modifiedBy;
//    }
//
//    public void setModifiedOn(Timestamp modifiedOn) {
//        this.modifiedOn = modifiedOn;
//    }
//
//    @Override
//    public String toString() {
//        return "AutoPart{" + "autoPartID=" + autoPartID + ", carID=" + carID + ", partName=" + partName + ", price=" + price + ", partIMG=" + partIMG + ", status=" + status + ", description=" + description + ", createdBy=" + createdBy + ", createdOn=" + createdOn + ", modifiedBy=" + modifiedBy + ", modifiedOn=" + modifiedOn + '}';
//    }

    
    
    public AutoPart(int autoPartID, String partName, long price) {
        this.autoPartID = autoPartID;
        this.partName = partName;
        this.price = price;
    }

    public AutoPart(int autoPartID, String partName, long price, String partIMG, boolean status, String description, Account createdBy, Timestamp createdOn, Account modifiedBy, Timestamp modifiedOn) {
        this.autoPartID = autoPartID;
        this.partName = partName;
        this.price = price;
        this.partIMG = partIMG;
        this.status = status;
        this.description = description;
        this.createdBy = createdBy;
        this.createdOn = createdOn;
        this.modifiedBy = modifiedBy;
        this.modifiedOn = modifiedOn;
    }

    public AutoPart(int autoPartID, String partName, String partIMG) {
        this.autoPartID = autoPartID;
        this.partName = partName;
        this.partIMG = partIMG;
    }
    
    
    
    
}
