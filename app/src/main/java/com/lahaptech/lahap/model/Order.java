package com.lahaptech.lahap.model;

public class Order {

    private String usernameIPB, locationID, orderTime, orderTable,
            orderType, orderStatus, payMethod, transferProof, totalAmount;

    public Order(String usernameIPB, String locationID, String orderTime, String orderTable, String orderType, String orderStatus, String payMethod, String transferProof, String totalAmount) {
        this.usernameIPB = usernameIPB;
        this.locationID = locationID;
        this.orderTime = orderTime;
        this.orderTable = orderTable;
        this.orderType = orderType;
        this.orderStatus = orderStatus;
        this.payMethod = payMethod;
        this.transferProof = transferProof;
        this.totalAmount = totalAmount;
    }

    public String getUsernameIPB() {
        return usernameIPB;
    }

    public void setUsernameIPB(String usernameIPB) {
        this.usernameIPB = usernameIPB;
    }

    public String getLocationID() {
        return locationID;
    }

    public void setLocationID(String locationID) {
        this.locationID = locationID;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getOrderTable() {
        return orderTable;
    }

    public void setOrderTable(String orderTable) {
        this.orderTable = orderTable;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(String payMethod) {
        this.payMethod = payMethod;
    }

    public String getTransferProof() {
        return transferProof;
    }

    public void setTransferProof(String transferProof) {
        this.transferProof = transferProof;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }
}
