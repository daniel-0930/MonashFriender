package com.example.zhangzeyao.monashfriender;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.zhangzeyao.monashfriender.models.SpinnerHelper;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Created by zhangzeyao on 30/4/17.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MonashFrienderDB";
    public static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SpinnerHelper.CREATE_STATEMENT);
        db.execSQL(SpinnerHelper.CREATE_STATEMENT2);
        db.execSQL(SpinnerHelper.CREATE_STATEMENT3);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + SpinnerHelper.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + SpinnerHelper.TABLE_NAME2);
        db.execSQL("DROP TABLE IF EXISTS " + SpinnerHelper.TABLE_NAME3);
        onCreate(db);
    }

    public void AddCountry(SpinnerHelper spinnerHelper){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SpinnerHelper.COLUMN_LNAME, spinnerHelper.getLongName());
        values.put(SpinnerHelper.COLUMN_SName, spinnerHelper.getShortName());
        db.insert(SpinnerHelper.TABLE_NAME,null,values);
        db.close();
    }

    public void AddCourse(SpinnerHelper spinnerHelper){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SpinnerHelper.COLUMN_LNAME, spinnerHelper.getLongName());
        values.put(SpinnerHelper.COLUMN_SName, spinnerHelper.getShortName());
        db.insert(SpinnerHelper.TABLE_NAME2,null,values);
        db.close();
    }

    public void AddStudyMode(SpinnerHelper spinnerHelper){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SpinnerHelper.COLUMN_LNAME, spinnerHelper.getLongName());
        values.put(SpinnerHelper.COLUMN_SName, spinnerHelper.getShortName());
        db.insert(SpinnerHelper.TABLE_NAME3,null,values);
        db.close();
    }

    public HashMap<Long, SpinnerHelper> GetAllCountry(){
        HashMap<Long, SpinnerHelper> countries = new LinkedHashMap<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + SpinnerHelper.TABLE_NAME, null);
        if(cursor.moveToFirst()){
            do{
                SpinnerHelper spinnerHelper = new SpinnerHelper(cursor.getLong(0),cursor.getString(1),cursor.getString(2));
                countries.put(spinnerHelper.get_id(),spinnerHelper);
            }while(cursor.moveToNext());
        }
        cursor.close();
        return countries;
    }
    public HashMap<Long, SpinnerHelper> GetAllCourse(){
        HashMap<Long, SpinnerHelper> courses = new LinkedHashMap<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + SpinnerHelper.TABLE_NAME2, null);
        if(cursor.moveToFirst()){
            do{
                SpinnerHelper spinnerHelper = new SpinnerHelper(cursor.getLong(0),cursor.getString(1),cursor.getString(2));
                courses.put(spinnerHelper.get_id(),spinnerHelper);
            }while(cursor.moveToNext());
        }
        cursor.close();
        return courses;
    }
    public HashMap<Long, SpinnerHelper> GetAllStudyMode(){
        HashMap<Long, SpinnerHelper> studyModes = new LinkedHashMap<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + SpinnerHelper.TABLE_NAME3, null);
        if(cursor.moveToFirst()){
            do{
                SpinnerHelper spinnerHelper = new SpinnerHelper(cursor.getLong(0),cursor.getString(1),cursor.getString(2));
                studyModes.put(spinnerHelper.get_id(),spinnerHelper);
            }while(cursor.moveToNext());
        }
        cursor.close();
        return studyModes;
    }
    public void CreateDefaultCountry(){
        AddCountry(new SpinnerHelper(0,"Australian","AUS"));
        AddCountry(new SpinnerHelper(1,"Brazilian","BRA"));
        AddCountry(new SpinnerHelper(2,"Chinese","CHN"));
        AddCountry(new SpinnerHelper(3,"Dutch","ANT"));
        AddCountry(new SpinnerHelper(4,"English","GBR"));
        AddCountry(new SpinnerHelper(5,"German","DEU"));
        AddCountry(new SpinnerHelper(6,"Greek","GRC"));
        AddCountry(new SpinnerHelper(7,"Indian","IND"));
        AddCountry(new SpinnerHelper(8,"Italian","ITA"));
        AddCountry(new SpinnerHelper(9,"Irish","IRL"));
        AddCountry(new SpinnerHelper(9,"Other","OTH"));

    }

    public void CreateDefaultCourse(){
        AddCourse(new SpinnerHelper(0,"Master of Information Technology","MIT"));
        AddCourse(new SpinnerHelper(1,"Master of Business Intelligent System","MBIS"));
        AddCourse(new SpinnerHelper(2,"Master of Professional Accounting","MPA"));
        AddCourse(new SpinnerHelper(3,"Master of Networks and Security","MNS"));
        AddCourse(new SpinnerHelper(4,"Master of Teaching","MT"));
        AddCourse(new SpinnerHelper(5,"Master of Banking and Finance","MBF"));
        AddCourse(new SpinnerHelper(6,"Master of Marketing","MM"));
        AddCourse(new SpinnerHelper(7,"Master of Laws","ML"));
        AddCourse(new SpinnerHelper(8,"Master of Business","MB"));
        AddCourse(new SpinnerHelper(9,"Master of Data Science","MDS"));
        AddCourse(new SpinnerHelper(9,"Other","OTH"));
    }

    public void CreateDefaultStudyMode(){
        AddStudyMode(new SpinnerHelper(0,"Full Time","F"));
        AddStudyMode(new SpinnerHelper(1,"Part Time","P"));
        AddStudyMode(new SpinnerHelper(2,"Distance Study","D"));
        AddStudyMode(new SpinnerHelper(3,"Other","O"));
    }
}
