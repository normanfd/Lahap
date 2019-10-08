package com.lahaptech.lahap.model;

public class Seller {
    private String email, isActive, locationID, noTelp, password, sellerID, storeAddress, storeImage, storeName;

    public Seller() {
    }

    public Seller(String email, String isActive, String locationID, String noTelp, String password, String sellerID, String storeAddress, String storeImage, String storeName) {
        this.email = email;
        this.isActive = isActive;
        this.locationID = locationID;
        this.noTelp = noTelp;
        this.password = password;
        this.sellerID = sellerID;
        this.storeAddress = storeAddress;
        this.storeImage = storeImage;
        this.storeName = storeName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public String getLocationID() {
        return locationID;
    }

    public void setLocationID(String locationID) {
        this.locationID = locationID;
    }

    public String getNoTelp() {
        return noTelp;
    }

    public void setNoTelp(String noTelp) {
        this.noTelp = noTelp;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSellerID() {
        return sellerID;
    }

    public void setSellerID(String sellerID) {
        this.sellerID = sellerID;
    }

    public String getStoreAddress() {
        return storeAddress;
    }

    public void setStoreAddress(String storeAddress) {
        this.storeAddress = storeAddress;
    }

    public String getStoreImage() {
        return storeImage;
    }

    public void setStoreImage(String storeImage) {
        this.storeImage = storeImage;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }
}