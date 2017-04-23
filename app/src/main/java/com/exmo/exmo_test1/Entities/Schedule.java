package com.exmo.exmo_test1.Entities;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Inzimam on 4/11/2017.
 */
public class Schedule {
    String department;
    String title;
    Integer time;
    Integer day;

    public Schedule() {
    }

    public Schedule(String department, String title, Integer time, Integer day) {
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

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    @Exclude
    public String getProperTime(){
        if (time/100> 11){
            return time/100 + ":" + time%100 + " pm";
        }else{
            return time/100 + ":" + time%100 + " am";
        }
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
}
