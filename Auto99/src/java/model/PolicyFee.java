/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
public class PolicyFee {

    int feeID;
    String feeName;
    double fee;
    private Account createdBy;
    private Timestamp createdOn;
    private Account modifiedBy;
    private Timestamp modifiedOn;

    public PolicyFee(int feeID, String feeName, double fee) {
        this.feeID = feeID;
        this.feeName = feeName;
        this.fee = fee;
    }

    
}
