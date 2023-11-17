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
public class ClientService {
    private int clientServiceID;
    private Client clientID;
    private Service serviceID;
    private String numberPlate;
    private Date dateService;
    private CarBrand brandID;
    private String status;
    private EmployeeProfile employeeID;//dùng để lưu id nhân viên duyệt đơn
    private EmployeeProfile crewChiefID;//dùng để lưu id trưởng thợ máy làm cái dịch vụ này
}
