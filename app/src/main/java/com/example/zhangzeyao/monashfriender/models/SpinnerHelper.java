package com.example.zhangzeyao.monashfriender.models;

/**
 * Created by zhangzeyao on 30/4/17.
 */

public class SpinnerHelper {
    public static final String TABLE_NAME = "country";
    public static final String TABLE_NAME2 = "course";
    public static final String TABLE_NAME3 = "studymode";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_LNAME = "longname";
    public static final String COLUMN_SName = "shortname";
    public static final String CREATE_STATEMENT = "CREATE TABLE " + TABLE_NAME + " ("+ COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
            COLUMN_LNAME + " TEXT NOT NULL, "+ COLUMN_SName + " TEXT NOT NULL " + ")";
    public static final String CREATE_STATEMENT2 = "CREATE TABLE " + TABLE_NAME2 + " ("+ COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
            COLUMN_LNAME + " TEXT NOT NULL, "+ COLUMN_SName + " TEXT NOT NULL " + ")";
    public static final String CREATE_STATEMENT3 = "CREATE TABLE " + TABLE_NAME3 + " ("+ COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
            COLUMN_LNAME + " TEXT NOT NULL, "+ COLUMN_SName + " TEXT NOT NULL " + ")";

    private long _id;
    private String longName;
    private String shortName;

    public SpinnerHelper() {
        longName = "";
        shortName = "";
    }

    public SpinnerHelper(long _id, String longName, String shortName) {
        this._id = _id;
        this.longName = longName;
        this.shortName = shortName;
    }

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public String getLongName() {
        return longName;
    }

    public void setLongName(String longName) {
        this.longName = longName;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }
}
