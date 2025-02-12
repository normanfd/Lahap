package com.lahaptech.lahap.model;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {
    private String name, username, phone, email, password, image, address;

    public User() {
    }

    public User(String name, String username, String phone, String email, String password, String image, String address) {
        this.name = name;
        this.username = username;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.image = image;
        this.address = address;
    }

    protected User(Parcel in) {
        name = in.readString();
        username = in.readString();
        phone = in.readString();
        email = in.readString();
        password = in.readString();
        image = in.readString();
        address = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(username);
        parcel.writeString(phone);
        parcel.writeString(email);
        parcel.writeString(password);
        parcel.writeString(image);
        parcel.writeString(address);
    }
}
