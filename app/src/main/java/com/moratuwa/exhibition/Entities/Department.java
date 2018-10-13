package com.moratuwa.exhibition.Entities;


public class Department {
    String description;
    int rate;
    int numRate;
    Double lat;
    Double lang;

    public Double getLang() {
        return lang;
    }

    public void setLang(Double lang) {
        this.lang = lang;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public int getNumRate() {
        return numRate;
    }

    public void setNumRate(int numRate) {
        this.numRate = numRate;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }
}
