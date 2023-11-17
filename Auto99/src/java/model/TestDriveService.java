/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
public class TestDriveService {
    private int TestDriveServiceID;
    private Client clientID;
    private Car carID;
    private Timestamp dateService;    
    private String status;
    private EmployeeProfile employeeID;//dùng để lưu id nhân viên duyệt đơn
    
}
