package model;

import java.time.LocalDateTime;
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
public class Voucher {
    private int voucherID;
    private String voucherCode;
    private String description;
    private ObjectVoucher objectVoucherID;
    private boolean discountType;
    private float discountValue;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private int maxUsage;
    private int usedCount;
    private Account createdBy;
    private LocalDateTime createdOn;
    private Account modifiedBy;
    private LocalDateTime modifiedOn;
    private boolean status;

    public Voucher(int voucherID, String voucherCode) {
        this.voucherID = voucherID;
        this.voucherCode = voucherCode;
    }

    

    public Voucher(String voucherCode, ObjectVoucher objectVoucherID, boolean discountType, float discountValue,int usedCount, int voucherID) {
        this.voucherCode = voucherCode;
        this.objectVoucherID = objectVoucherID;
        this.discountType = discountType;
        this.discountValue = discountValue;
        this.usedCount = usedCount;
        this.voucherID = voucherID;
    }

    public Voucher(int voucherID,String voucherCode, ObjectVoucher objectVoucherID, boolean discountType, float discountValue,int usedCount) {
        this.voucherID = voucherID;
        this.voucherCode = voucherCode;
        this.objectVoucherID = objectVoucherID;
        this.discountType = discountType;
        this.discountValue = discountValue;
        this.usedCount = usedCount;
    }
    
    public Voucher(int voucherID, String voucherCode, String description, ObjectVoucher objectVoucherID, boolean discountType, float discountValue, LocalDateTime startDate, LocalDateTime endDate, int maxUsage, int usedCount, Account createdBy, LocalDateTime createdOn, Account modifiedBy, boolean status) {
        this.voucherID = voucherID;
        this.voucherCode = voucherCode;
        this.description = description;
        this.objectVoucherID = objectVoucherID;
        this.discountType = discountType;
        this.discountValue = discountValue;
        this.startDate = startDate;
        this.endDate = endDate;
        this.maxUsage = maxUsage;
        this.usedCount = usedCount;
        this.createdBy = createdBy;
        this.createdOn = createdOn;
        this.modifiedBy = modifiedBy;
        this.status = status;
    }

    public Voucher(int voucherID) {
        this.voucherID = voucherID;
    }


    
    
}
