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
public class Service {

    private int serviceID;
    private String serviceType;
    private String serviceContent;
    private boolean serviceStatus;
    private float servicePrice;
    private int created_by; //(id nhân viên)(int)
    private Timestamp created_on; //(ngày tạo)(datetime)
    private int modified_by; //(id nhân viên)(int)
    private Timestamp modified_on;//(ngày sửa cái dòng đó)(datetime)

    public Service(int serviceID) {
        this.serviceID = serviceID;
    }

    public Service(int serviceID, String serviceType, String serviceContent, boolean serviceStatus) {
        this.serviceID = serviceID;
        this.serviceType = serviceType;
        this.serviceContent = serviceContent;
        this.serviceStatus = serviceStatus;
        // You should also initialize other attributes like created_by, created_on, modified_by, and modified_on if they exist.
    }

}
