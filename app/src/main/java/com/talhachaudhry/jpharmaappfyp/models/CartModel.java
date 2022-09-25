package com.talhachaudhry.jpharmaappfyp.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class CartModel implements Parcelable {
    private ManageMedicineModel model;
    private int quantity;
    public CartModel(ManageMedicineModel model, int quantity) {
        this.model = model;
        this.quantity = quantity;
    }
    public CartModel() {
        // required for Firebase
    }
    protected CartModel(Parcel in) {
        model = in.readParcelable(ManageMedicineModel.class.getClassLoader());
        quantity = in.readInt();
    }

    public static final Creator<CartModel> CREATOR = new Creator<CartModel>() {
        @Override
        public CartModel createFromParcel(Parcel in) {
            return new CartModel(in);
        }

        @Override
        public CartModel[] newArray(int size) {
            return new CartModel[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(model, i);
        parcel.writeInt(quantity);
    }
}
