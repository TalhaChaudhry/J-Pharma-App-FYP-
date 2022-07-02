package com.talhachaudhry.jpharmaappfyp.Models;

import androidx.annotation.DrawableRes;

public class ItemsModel {
    String imagePath;
    String text;
    int imageId;

    public ItemsModel(String imagePath, String text) {
        this.imagePath = imagePath;
        this.text = text;
    }

    public ItemsModel(String text, @DrawableRes int imageId) {
        this.text = text;
        this.imageId = imageId;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
}
