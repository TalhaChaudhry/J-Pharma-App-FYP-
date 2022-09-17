package com.talhachaudhry.jpharmaappfyp.models;

public class AnalysisModel {
    String medicine_name;
    int medicineCount;

    public AnalysisModel(String medicine_name, int medicineCount) {
        this.medicine_name = medicine_name;
        this.medicineCount = medicineCount;
    }

    public String getMedicine_name() {
        return medicine_name;
    }

    public void setMedicine_name(String medicine_name) {
        this.medicine_name = medicine_name;
    }

    public int getMedicineCount() {
        return medicineCount;
    }

    public void setMedicineCount(int medicineCount) {
        this.medicineCount = medicineCount;
    }
}
