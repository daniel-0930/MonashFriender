package com.example.zhangzeyao.monashfriender.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * Created by zhangzeyao on 28/4/17.
 */

public class Student implements Parcelable {
    private Integer studentid;
    private String firstname;
    private String surname;
    private Character gender;
    private Date dob;
    private String course;
    private Character studymode;
    private String address;
    private String surburb;
    private String nationality;
    private String navlanguage;
    private String favsport;
    private String favmovie;
    private String favunit;
    private String currjob;
    private String monemail;
    private String password;
    private Date subdatetime;

    public Student() {
        studentid = 0;
        firstname = "";
        surname = "";
        gender = '?';
        try {
            dob = new SimpleDateFormat("yyyy-MM-dd").parse("1888-1-1");
            subdatetime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("1888-1-1 12:00:00");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        course = "";
        studymode = '?';
        address = "";
        surburb = "";
        nationality = "";
        navlanguage = "";
        favmovie = "";
        favunit = "";
        favmovie = "";
        currjob = "";
        monemail = "";
        password = "";
    }

    public Student(Integer studentid) {
        this.studentid = studentid;
    }

    public Student(Integer studentid, String firstname, String surname, Character gender, Date dob, String course, Character studymode, String address, String surburb, String nationality, String navlanguage, String currjob, String monemail, String password, Date subdatetime) {
        this.studentid = studentid;
        this.firstname = firstname;
        this.surname = surname;
        this.gender = gender;
        this.dob = dob;
        this.course = course;
        this.studymode = studymode;
        this.address = address;
        this.surburb = surburb;
        this.nationality = nationality;
        this.navlanguage = navlanguage;
        this.currjob = currjob;
        this.monemail = monemail;
        this.password = password;
        this.subdatetime = subdatetime;
    }

    protected Student(Parcel in) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        studentid = Integer.valueOf(in.readString());
        firstname = in.readString();
        surname = in.readString();
        gender = in.readString().charAt(0);
        try {
            dob = dateFormat.parse(in.readString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        course = in.readString();
        studymode = in.readString().charAt(0);
        address = in.readString();
        surburb = in.readString();
        nationality = in.readString();
        navlanguage = in.readString();
        favsport = in.readString();
        favmovie = in.readString();
        favunit = in.readString();
        currjob = in.readString();
        monemail = in.readString();
        password = in.readString();
        try {
            subdatetime = dateFormat2.parse(in.readString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        dest.writeString(String.valueOf(studentid));
        dest.writeString(firstname);
        dest.writeString(surname);
        dest.writeString(gender.toString());
        dest.writeString(dateFormat.format(dob));
        dest.writeString(course);
        dest.writeString(Character.toString(studymode));
        dest.writeString(address);
        dest.writeString(surburb);
        dest.writeString(nationality);
        dest.writeString(navlanguage);
        dest.writeString(favsport);
        dest.writeString(favmovie);
        dest.writeString(favunit);
        dest.writeString(currjob);
        dest.writeString(monemail);
        dest.writeString(password);
        dest.writeString(dateFormat2.format(subdatetime));

    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Student> CREATOR = new Creator<Student>() {
        @Override
        public Student createFromParcel(Parcel in) {
            return new Student(in);
        }

        @Override
        public Student[] newArray(int size) {
            return new Student[size];
        }
    };

    public Integer getStudentid() {
        return studentid;
    }

    public void setStudentid(Integer studentid) {
        this.studentid = studentid;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Character getGender() {
        return gender;
    }

    public void setGender(Character gender) {
        this.gender = gender;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public Character getStudymode() {
        return studymode;
    }

    public void setStudymode(Character studymode) {
        this.studymode = studymode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSurburb() {
        return surburb;
    }

    public void setSurburb(String surburb) {
        this.surburb = surburb;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getNavlanguage() {
        return navlanguage;
    }

    public void setNavlanguage(String navlanguage) {
        this.navlanguage = navlanguage;
    }

    public String getFavsport() {
        return favsport;
    }

    public void setFavsport(String favsport) {
        this.favsport = favsport;
    }

    public String getFavmovie() {
        return favmovie;
    }

    public void setFavmovie(String favmovie) {
        this.favmovie = favmovie;
    }

    public String getFavunit() {
        return favunit;
    }

    public void setFavunit(String favunit) {
        this.favunit = favunit;
    }

    public String getCurrjob() {
        return currjob;
    }

    public void setCurrjob(String currjob) {
        this.currjob = currjob;
    }

    public String getMonemail() {
        return monemail;
    }

    public void setMonemail(String monemail) {
        this.monemail = monemail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getSubdatetime() {
        return subdatetime;
    }

    public void setSubdatetime(Date subdatetime) {
        this.subdatetime = subdatetime;
    }

    public String md5(String input){
        try{
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(input.getBytes());
            byte messageDigest[] = digest.digest();

            StringBuffer hexString = new StringBuffer();
            for (int i=0; i<messageDigest.length; i++)
                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
            return hexString.toString();
        }catch(NoSuchAlgorithmException e){
            e.printStackTrace();
        }
        return "";
    }
}
