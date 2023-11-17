package model;

import java.sql.Date;
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
public class EmployeeProfile {

    private int employeeID;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private Date DOB;
    private String img;
    private boolean gender;
    private String IDNo;
    private String address;
    private Date startDate;
    private int createdBy;
    private Timestamp createdOn;
    private int modifiedBy;
    private Timestamp modifiedOn;
    private Role role;

    public EmployeeProfile(int employeeID, String firstName, String lastName, String email, String phoneNumber, Date DOB, String img, boolean gender, String IDNo, String address, Date startDate, Role role) {
        this.employeeID = employeeID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.DOB = DOB;
        this.img = img;
        this.gender = gender;
        this.IDNo = IDNo;
        this.address = address;
        this.startDate = startDate;
        this.role = role;
    }

}
