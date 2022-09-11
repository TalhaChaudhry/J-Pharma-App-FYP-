package com.talhachaudhry.jpharmaappfyp.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class UserModel implements Parcelable {

    String shopName;
    String userName;
    String address;
    String city;
    String contact;
    String email;
    String profilePic;
    String password;

    public UserModel(String shopName, String userName, String password, String address,
                     String city, String contact, String email, String profilePic) {
        this.shopName = shopName;
        this.userName = userName;
        this.address = address;
        this.city = city;
        this.password = password;
        this.contact = contact;
        this.profilePic = profilePic;
        this.email = email;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    protected UserModel(Parcel in) {
        shopName = in.readString();
        userName = in.readString();
        address = in.readString();
        city = in.readString();
        contact = in.readString();
    }

    public UserModel() {
        // Required for Firebase
    }

    public static final Creator<UserModel> CREATOR = new Creator<UserModel>() {
        @Override
        public UserModel createFromParcel(Parcel in) {
            return new UserModel(in);
        }

        @Override
        public UserModel[] newArray(int size) {
            return new UserModel[size];
        }
    };

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @NonNull
    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(shopName);
        parcel.writeString(userName);
        parcel.writeString(address);
        parcel.writeString(city);
        parcel.writeString(contact);
        parcel.writeString(password);
        parcel.writeString(profilePic);
        parcel.writeString(email);
    }
}
