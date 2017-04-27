package com.uom.exhibition.Entities;

import android.support.annotation.NonNull;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Inzimam on 4/11/2017.
 */
public class Schedule implements Comparable<Schedule> {
    String department;
    String title;
    String time;
    Integer day;
    @Exclude
    String key;

    public Schedule() {
    }

    public Schedule(String department, String title, String time, Integer day) {
        this.department = department;
        this.title = title;
        this.time = time;
        this.day = day;
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

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    @Exclude
    public String getKey() {
        return key;
    }
    @Exclude
    public void setKey(String key) {
        this.key = key;
    }

    @Exclude
    public String getProperTime(){
        return time.substring(0,2) + ":" + time.substring(2);
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("department", department);
         result.put("title", title);
        result.put("time",time);
        result.put("day",day);
        return result;
    }

    @Override
    public int compareTo(@NonNull Schedule schedule) {
        if (Integer.parseInt(time)>Integer.parseInt(schedule.getTime())){
            return 1;
        }else if (Integer.parseInt(time)<Integer.parseInt(schedule.getTime())){
            return -1;
        }else{
            return 0;
        }
    }
}
