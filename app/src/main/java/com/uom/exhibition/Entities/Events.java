package com.uom.exhibition.Entities;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Inzimam on 4/12/2017.
 */
public class Events {
    String department;
    String title;
    String time;
    String description;
    String imageUrl;
    String imageUrl1;
    String imageUrl2;
    String imageUrl3;
    String imageUrl4;
    Double lat;
    Double lang;
    String floor;
    Integer day;
    int rate;
    int numRate;

    public Events() {
    }

    public Events(String department, String title, String time, String description, String imageUrl, Double lat, Double lang, String floor, Integer day,int rate,int numRate) {
        this.department = department;
        this.title = title;
        this.time = time;
        this.description = description;
        this.imageUrl = imageUrl;
        this.lat = lat;
        this.lang = lang;
        this.floor = floor;
        this.day = day;
        this.rate=rate;
        this.numRate=numRate;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLang() {
        return lang;
    }

    public void setLang(Double lang) {
        this.lang = lang;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public int getNumRate() {
        return numRate;
    }

    public void setNumRate(int numRate) {
        this.numRate = numRate;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public String getImageUrl1() {
        return imageUrl1;
    }

    public void setImageUrl1(String imageUrl1) {
        this.imageUrl1 = imageUrl1;
    }

    public String getImageUrl2() {
        return imageUrl2;
    }

    public void setImageUrl2(String imageUrl2) {
        this.imageUrl2 = imageUrl2;
    }

    public String getImageUrl3() {
        return imageUrl3;
    }

    public void setImageUrl3(String imageUrl3) {
        this.imageUrl3 = imageUrl3;
    }

    public String getImageUrl4() {
        return imageUrl4;
    }

    public void setImageUrl4(String imageUrl4) {
        this.imageUrl4 = imageUrl4;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("department", department);
        result.put("title", title);
        result.put("time",time);
        result.put("day",day);
        result.put("lat",lat);
        result.put("lang",lang);
        result.put("floor",floor);
        result.put("imageUrl",imageUrl);
        result.put("description",description);
        return result;
    }
}
