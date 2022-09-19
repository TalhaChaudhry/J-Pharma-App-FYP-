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
    String userId;
    byte[] imageArr;
    int isInStorage = 0;

    public UserModel(String shopName, String userName, String password, String address,
                     String city, String contact, String email, String profilePic, String userId) {
        this.shopName = shopName;
        this.userName = userName;
        this.address = address;
        this.city = city;
        this.password = password;
        this.contact = contact;
        this.profilePic = profilePic;
        this.email = email;
        this.userId = userId;
    }

    protected UserModel(Parcel in) {
        shopName = in.readString();
        userName = in.readString();
        address = in.readString();
        city = in.readString();
        contact = in.readString();
        email = in.readString();
        profilePic = in.readString();
        password = in.readString();
        userId = in.readString();
        imageArr = in.createByteArray();
        isInStorage = in.readInt();
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

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }


    public UserModel() {
        // Required for Firebase
    }

    public byte[] getImageArr() {
        return imageArr;
    }

    public void setImageArr(byte[] imageArr) {
        this.imageArr = imageArr;
    }

    public int getIsInStorage() {
        return isInStorage;
    }

    public void setIsInStorage(int isInStorage) {
        this.isInStorage = isInStorage;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

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
        parcel.writeString(email);
        parcel.writeString(profilePic);
        parcel.writeString(password);
        parcel.writeString(userId);
        parcel.writeByteArray(imageArr);
        parcel.writeInt(isInStorage);
    }
}
