package com.lahaptech.lahap.model;

public class Canteen {
    private String canteenID, canteenName;

    public Canteen() {
    }

    public Canteen(String canteenID, String canteenName) {
        this.canteenID = canteenID;
        this.canteenName = canteenName;
    }

    public String getCanteenID() {
        return canteenID;
    }

    public void setCanteenID(String canteenID) {
        this.canteenID = canteenID;
    }

    public String getCanteenName() {
        return canteenName;
    }

    public void setCanteenName(String canteenName) {
        this.canteenName = canteenName;
    }
}
