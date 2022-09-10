package com.talhachaudhry.jpharmaappfyp.models;

public class MedicineModel {

    String imagePath;
    String medicineName;
    String medicineDetail;

    public MedicineModel(String imagePath, String medicineName, String medicineDetail) {
        this.imagePath = imagePath;
        this.medicineName = medicineName;
        this.medicineDetail = medicineDetail;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public String getMedicineDetail() {
        return medicineDetail;
    }

    public void setMedicineDetail(String medicineDetail) {
        this.medicineDetail = medicineDetail;
    }
}
