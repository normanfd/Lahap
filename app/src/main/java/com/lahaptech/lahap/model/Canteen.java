package com.lahaptech.lahap.model;

public class Canteen {
    private String canteenID, canteenName, canteenCode;

    public Canteen() {
    }

    public Canteen(String canteenID, String canteenName, String canteenCode) {
        this.canteenID = canteenID;
        this.canteenName = canteenName;
        this.canteenCode = canteenCode;
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

    public String getCanteenCode() {
        return canteenCode;
    }

    public void setCanteenCode(String canteenCode) {
        this.canteenCode = canteenCode;
    }
}
