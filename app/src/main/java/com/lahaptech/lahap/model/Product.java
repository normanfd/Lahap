package com.lahaptech.lahap.model;

public class Product {
    private String productID, sellerID, productName, category, date, description, image, price, time, isAvailable, locationID, menuDetail, nutritionDetail ;

    public Product() {
    }

    public Product(String productID, String sellerID, String category,
                   String productName, String date, String description,
                   String image, String price, String time, String isAvailable,
                   String locationID, String menuDetail, String nutritionDetail) {

        this.productID = productID;
        this.sellerID = sellerID;
        this.productName = productName;
        this.category = category;
        this.date = date;
        this.description = description;
        this.image = image;
        this.price = price;
        this.time = time;
        this.isAvailable = isAvailable;
        this.locationID = locationID;
        this.menuDetail = menuDetail;
        this.nutritionDetail = nutritionDetail;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getSellerID() {
        return sellerID;
    }

    public void setSellerID(String sellerID) {
        this.sellerID = sellerID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(String isAvailable) {
        this.isAvailable = isAvailable;
    }

    public String getLocationID() {
        return locationID;
    }

    public void setLocationID(String locationID) {
        this.locationID = locationID;
    }

    public String getMenuDetail() {
        return menuDetail;
    }

    public void setMenuDetail(String menuDetail) {
        this.menuDetail = menuDetail;
    }

    public String getNutritionDetail() {
        return nutritionDetail;
    }

    public void setNutritionDetail(String nutritionDetail) {
        this.nutritionDetail = nutritionDetail;
    }
}
