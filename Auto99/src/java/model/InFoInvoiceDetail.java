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
public class InFoInvoiceDetail {
    private int infoDetailID;
    private int serviceInvoiceID;
    private String productName;
    private String unitPrice;
    private String quantity;
}
