package com.talhachaudhry.jpharmaappfyp.models;

import androidx.annotation.NonNull;

public class CartModel {
    private ManageMedicineModel model;
    private int quantity;

    public CartModel(ManageMedicineModel model, int quantity) {
        this.model = model;
        this.quantity = quantity;
    }
    public CartModel(){
        // required for Firebase
    }

    public ManageMedicineModel getModel() {
        return model;
    }

    public void setModel(ManageMedicineModel model) {
        this.model = model;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @NonNull
    @Override
    public String toString() {
        return super.toString();
    }
}
