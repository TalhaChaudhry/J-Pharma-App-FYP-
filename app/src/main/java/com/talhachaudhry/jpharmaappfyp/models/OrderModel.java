package com.talhachaudhry.jpharmaappfyp.models;

import androidx.annotation.NonNull;

import java.util.List;

public class OrderModel {
    private List<CartModel> ordersList;
    private String orderId;
    private String status;
    private String reason = "";
    private UserModel userModel;

    public OrderModel(List<CartModel> ordersList, String orderId, String status, UserModel userModel) {
        this.ordersList = ordersList;
        this.orderId = orderId;
        this.status = status;
        this.userModel = userModel;
    }

    public OrderModel() {
        // required for Firebase
    }


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
}
