package com.talhachaudhry.jpharmaappfyp.models;
import java.util.List;

public class OrderModel {
    private List<CartModel> ordersList;
    private String orderId;
    private String status;

    public OrderModel(List<CartModel> ordersList, String orderId, String status) {
        this.ordersList = ordersList;
        this.orderId = orderId;
        this.status = status;
    }

    public OrderModel() {
        // required for Firebase
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
}
