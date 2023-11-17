package model;

import java.time.LocalDateTime;

public class CarOrder {

    private int carorderID;
    private String carorderCode;
    private Client clientID;
    private Car carID;
    private boolean paymentType;
    private long orderValue;
    private long paid;
    private long paymentRequired;
    private LocalDateTime createdOn;
    private LocalDateTime orderDate;
    private LocalDateTime modifiedOn;
    private Account modifiedBy;
    private String status;
    private Voucher voucherID;

  

   

    public int getCarorderID() {
        return carorderID;
    }

    public void setCarorderID(int carorderID) {
        this.carorderID = carorderID;
    }

    public String getCarorderCode() {
        return carorderCode;
    }

    public void setCarorderCode(String carorderCode) {
        this.carorderCode = carorderCode;
    }

    public Client getClientID() {
        return clientID;
    }

    public void setClientID(Client clientID) {
        this.clientID = clientID;
    }

    public Car getCarID() {
        return carID;
    }

    public void setCarID(Car carID) {
        this.carID = carID;
    }

    public boolean isPaymentType() {
        return paymentType;
    }

    public void setPaymentType(boolean paymentType) {
        this.paymentType = paymentType;
    }

    public long getOrderValue() {
        return orderValue;
    }

    public void setOrderValue(long orderValue) {
        this.orderValue = orderValue;
    }

    public long getPaid() {
        return paid;
    }

    public void setPaid(long paid) {
        this.paid = paid;
    }

    public long getPaymentRequired() {
        return paymentRequired;
    }

    public void setPaymentRequired(long paymentRequired) {
        this.paymentRequired = paymentRequired;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public LocalDateTime getModifiedOn() {
        return modifiedOn;
    }

    public void setModifiedOn(LocalDateTime modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

    public Account getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(Account modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Voucher getVoucherID() {
        return voucherID;
    }

    public void setVoucherID(Voucher voucherID) {
        this.voucherID = voucherID;
    }

    public CarOrder(int carorderID, String carorderCode, Client clientID, Car carID, boolean paymentType, long orderValue, long paid, long paymentRequired, LocalDateTime createdOn, LocalDateTime orderDate, LocalDateTime modifiedOn, Account modifiedBy, String status, Voucher voucherID) {
        this.carorderID = carorderID;
        this.carorderCode = carorderCode;
        this.clientID = clientID;
        this.carID = carID;
        this.paymentType = paymentType;
        this.orderValue = orderValue;
        this.paid = paid;
        this.paymentRequired = paymentRequired;
        this.createdOn = createdOn;
        this.orderDate = orderDate;
        this.modifiedOn = modifiedOn;
        this.modifiedBy = modifiedBy;
        this.status = status;
        this.voucherID = voucherID;
    }

    public CarOrder() {
    }

    public CarOrder(int carorderID, String carorderCode, Client clientID, Car carID, boolean paymentType, long orderValue, long paid, long paymentRequired, LocalDateTime createdOn, LocalDateTime orderDate, Account modifiedBy, String status, Voucher voucherID) {
        this.carorderID = carorderID;
        this.carorderCode = carorderCode;
        this.clientID = clientID;
        this.carID = carID;
        this.paymentType = paymentType;
        this.orderValue = orderValue;
        this.paid = paid;
        this.paymentRequired = paymentRequired;
        this.createdOn = createdOn;
        this.orderDate = orderDate;
        this.modifiedBy = modifiedBy;
        this.status = status;
        this.voucherID = voucherID;
    }

    public CarOrder(String carorderCode, Client clientID, Car carID, boolean paymentType, long orderValue, long paid, long paymentRequired, LocalDateTime createdOn, LocalDateTime orderDate, String status) {
        this.carorderCode = carorderCode;
        this.clientID = clientID;
        this.carID = carID;
        this.paymentType = paymentType;

        this.orderValue = orderValue;
        this.paid = paid;
        this.paymentRequired = paymentRequired;
        this.createdOn = createdOn;
        this.orderDate = orderDate;
        this.status = status;
    }

    public CarOrder(String carorderCode, Client clientID, Car carID, long orderValue, LocalDateTime createdOn, LocalDateTime orderDate, String status, Voucher voucherID) {
        this.carorderCode = carorderCode;
        this.clientID = clientID;
        this.carID = carID;
        this.orderValue = orderValue;
        this.createdOn = createdOn;
        this.orderDate = orderDate;
        this.status = status;
        this.voucherID = voucherID;
    }
    
      public CarOrder(int carorderID, String carorderCode, Client clientID, Car carID, boolean paymentType, long orderValue, long paid, long paymentRequired, LocalDateTime createdOn, String status) {
        this.carorderID = carorderID;
        this.carorderCode = carorderCode;
        this.clientID = clientID;
        this.carID = carID;
        this.paymentType = paymentType;
        this.orderValue = orderValue;
        this.paid = paid;
        this.paymentRequired = paymentRequired;
        this.createdOn = createdOn;
        this.status = status;
    }

    @Override
    public String toString() {
        return "CarOrder{" + "carorderID=" + carorderID + ", carorderCode=" + carorderCode + ", clientID=" + clientID + ", carID=" + carID + ", paymentType=" + paymentType + ", orderValue=" + orderValue + ", paid=" + paid + ", paymentRequired=" + paymentRequired + ", createdOn=" + createdOn + ", orderDate=" + orderDate + ", modifiedOn=" + modifiedOn + ", modifiedBy=" + modifiedBy + ", status=" + status + ", voucherID=" + voucherID + '}';
    }



}
