package model;

import java.sql.Date;
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
public class GeneralInfoCarContract {

    private int contractID;
    private Client clientID;
    private EmployeeProfile employeeID;
    private Car carID;
    private Date contractSigningDate;
}
