package com.example.zhangzeyao.monashfriender.models;

/**
 * Created by zhangzeyao on 4/5/17.
 */
import com.example.zhangzeyao.monashfriender.models.Student;

import java.util.Date;
public class Friendship {
    private Integer relationid;
    private Date startdate;
    private Date enddate;
    private Student friendid;
    private Student studentid;

    public Friendship() {
    }

    public Friendship(Integer relationid, Date startdate, Date enddate, Student friendid, Student studentid) {
        this.relationid = relationid;
        this.startdate = startdate;
        this.enddate = enddate;
        this.friendid = friendid;
        this.studentid = studentid;
    }

    public Integer getRelationid() {
        return relationid;
    }

    public void setRelationid(Integer relationid) {
        this.relationid = relationid;
    }

    public Date getStartdate() {
        return startdate;
    }

    public void setStartdate(Date startdate) {
        this.startdate = startdate;
    }

    public Date getEnddate() {
        return enddate;
    }

    public void setEnddate(Date enddate) {
        this.enddate = enddate;
    }

    public Student getFriendid() {
        return friendid;
    }

    public void setFriendid(Student friendid) {
        this.friendid = friendid;
    }

    public Student getStudentid() {
        return studentid;
    }

    public void setStudentid(Student studentid) {
        this.studentid = studentid;
    }
}
