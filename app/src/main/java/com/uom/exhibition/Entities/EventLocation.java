package com.uom.exhibition.Entities;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Inzimam on 4/11/2017.
 */
public class EventLocation {
    String department;
    String time;
    String title;
    Integer day;
    Double lat;
    Double lang;
    String floor;

    public EventLocation(String department, String time, String title, Integer day, Double lat,
                         Double lang, String floor) {
        this.department = department;
        this.time = time;
        this.title = title;
        this.day = day;
        this.lat = lat;
        this.lang = lang;
        this.floor = floor;
    }

    public EventLocation() {
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
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

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("department", department);
        result.put("title", title);
        result.put("time",time);
        result.put("day",day);
        result.put("lat",lat);
        result.put("lang",lang);
        return result;
    }
}
