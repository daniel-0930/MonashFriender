package com.example.zhangzeyao.monashfriender.models;

/**
 * Created by zhangzeyao on 5/5/17.
 */
import java.util.Date;
public class StudentLocation {

    private Student student;
    private Date updateTime;
    private Double latitude;
    private Double longitude;

    public StudentLocation() {
    }

    public StudentLocation(Student student, Date updateTime, Double latitude, Double longitude) {
        this.student = student;
        this.updateTime = updateTime;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
