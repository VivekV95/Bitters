package com.vivekvishwanath.bitters.models;

public class Ingredient {
    private String name;
    private String measurement;
    private String photoUrl;

    public Ingredient(String name, String measurement, String photoUrl) {
        this.name = name;
        this.measurement = measurement;
        this.photoUrl = photoUrl;
    }

    public Ingredient() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMeasurement() {
        return measurement;
    }

    public void setMeasurement(String measurement) {
        this.measurement = measurement;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
}
