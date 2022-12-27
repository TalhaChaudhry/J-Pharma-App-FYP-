package com.talhachaudhry.jpharmaappfyp.models;

import android.os.Parcel;
import android.os.Parcelable;

public class ManageMedicineModel implements Parcelable {
    String imagePath;
    String detail;
    int price;
    String mg;
    String name;
    int stock;

    public ManageMedicineModel(String imagePath, String detail, int price, String mg, String name, int stock) {
        this.imagePath = imagePath;
        this.detail = detail;
        this.price = price;
        this.mg = mg;
        this.name = name;
        this.stock = stock;
    }

    private ManageMedicineModel(Parcel in) {
        imagePath = in.readString();
        detail = in.readString();
        price = in.readInt();
        mg = in.readString();
        name = in.readString();
        stock=in.readInt();
    }

    public ManageMedicineModel() {
        // required for Firebase
    }


    public static final Creator<ManageMedicineModel> CREATOR = new Creator<ManageMedicineModel>() {
        @Override
        public ManageMedicineModel createFromParcel(Parcel in) {
            return new ManageMedicineModel(in);
        }

        @Override
        public ManageMedicineModel[] newArray(int size) {
            return new ManageMedicineModel[size];
        }
    };

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getMg() {
        return mg;
    }

    public void setMg(String mg) {
        this.mg = mg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(imagePath);
        parcel.writeString(detail);
        parcel.writeInt(price);
        parcel.writeString(mg);
        parcel.writeString(name);
        parcel.writeInt(stock);
    }

    @Override
    public String toString() {
        return "ManageMedicineModel{" +
                "imagePath='" + imagePath + '\'' +
                ", detail='" + detail + '\'' +
                ", price=" + price +
                ", mg='" + mg + '\'' +
                ", name='" + name + '\'' +
                ", stock=" + stock +
                '}';
    }
}
