package com.talhachaudhry.jpharmaappfyp.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.List;

public class OrderModel implements Parcelable {
    private List<CartModel> ordersList;
    private String orderId;
    private String status;
    private String reason = "";
    private UserModel userModel;
    private String dateAndTime;
    private int month;
    private int year;

    public OrderModel(List<CartModel> ordersList, String orderId, String status,
                      UserModel userModel, String dateAndTime, int month, int year) {
        this.ordersList = ordersList;
        this.orderId = orderId;
        this.status = status;
        this.userModel = userModel;
        this.dateAndTime = dateAndTime;
        this.month = month;
        this.year = year;
    }

    public String getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(String dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public OrderModel() {
        // required for Firebase
    }


    protected OrderModel(Parcel in) {
        ordersList = in.createTypedArrayList(CartModel.CREATOR);
        orderId = in.readString();
        status = in.readString();
        reason = in.readString();
        userModel = in.readParcelable(UserModel.class.getClassLoader());
        dateAndTime = in.readString();
        month = in.readInt();
        year = in.readInt();
    }

    public static final Creator<OrderModel> CREATOR = new Creator<OrderModel>() {
        @Override
        public OrderModel createFromParcel(Parcel in) {
            return new OrderModel(in);
        }

        @Override
        public OrderModel[] newArray(int size) {
            return new OrderModel[size];
        }
    };

    public UserModel getUserModel() {
        return userModel;
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public List<CartModel> getOrdersList() {
        return ordersList;
    }

    public void setOrdersList(List<CartModel> modelList) {
        this.ordersList = modelList;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
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
        parcel.writeTypedList(ordersList);
        parcel.writeString(orderId);
        parcel.writeString(status);
        parcel.writeString(reason);
        parcel.writeParcelable(userModel, i);
        parcel.writeString(dateAndTime);
        parcel.writeInt(month);
        parcel.writeInt(year);
    }
}
