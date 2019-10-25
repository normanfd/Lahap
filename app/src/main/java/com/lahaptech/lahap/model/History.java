package com.lahaptech.lahap.model;

public class History {
    private String dateOrder, orderID, productList, totalAmount, usernameIPB, locationID;

    public History() {
    }

    public History(String dateOrder, String orderID, String productList, String totalAmount, String usernameIPB, String locationID) {
        this.dateOrder = dateOrder;
        this.orderID = orderID;
        this.productList = productList;
        this.totalAmount = totalAmount;
        this.usernameIPB = usernameIPB;
        this.locationID = locationID;
    }

    public String getDateOrder() {
        return dateOrder;
    }

    public void setDateOrder(String dateOrder) {
        this.dateOrder = dateOrder;
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

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
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
}
