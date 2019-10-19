package com.lahaptech.lahap.model;

public class Order {

    private String orderID;
    private String usernameIPB;
    private String locationID;
    private String orderTime;
    private String orderDate;
    private String orderTable;
    private String orderType;
    private String orderStatus;
    private String payMethod;
    private String transferProof;
    private String totalAmount;
    private String productList;

    public Order() {
    }


    public Order(String orderID, String usernameIPB, String locationID, String orderTime, String orderDate, String orderTable, String orderType, String orderStatus, String payMethod, String transferProof, String totalAmount, String productList) {
        this.orderID = orderID;
        this.usernameIPB = usernameIPB;
        this.locationID = locationID;
        this.orderTime = orderTime;
        this.orderDate = orderDate;
        this.orderTable = orderTable;
        this.orderType = orderType;
        this.orderStatus = orderStatus;
        this.payMethod = payMethod;
        this.transferProof = transferProof;
        this.totalAmount = totalAmount;
        this.productList = productList;
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

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getProductList() {
        return productList;
    }

    public void setProductList(String productList) {
        this.productList = productList;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }
}
