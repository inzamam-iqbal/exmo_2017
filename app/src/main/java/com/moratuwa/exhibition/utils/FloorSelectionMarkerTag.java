package com.moratuwa.exhibition.utils;

/**
 * Created by Inzimam on 4/27/2017.
 */

public class FloorSelectionMarkerTag {
    String floor;
    String departmentId;

    public FloorSelectionMarkerTag(String floor, String departmentId) {
        this.floor = floor;
        this.departmentId = departmentId;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }
}
