package com.talhachaudhry.jpharmaappfyp.models;

public class DateModel {
    int month;
    int year;
    String monthName;

    public DateModel(int month, int year, String monthName) {
        this.month = month;
        this.year = year;
        this.monthName = monthName;
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

    public String getMonthName() {
        return monthName;
    }

    public void setMonthName(String monthName) {
        this.monthName = monthName;
    }
}
