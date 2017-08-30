package com.example.zhangzeyao.monashfriender;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.zhangzeyao.monashfriender.models.Student;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import com.example.zhangzeyao.monashfriender.models.Friendship;
import android.widget.Button;

public class SearchResultActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private Button mapshowButton;
    private Student student;
    private String searchKeywords;
    private ListView searchView;
    private ArrayList<Student> friendList;
    private FriendSearchAdapter friendsearchAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        Intent intent = getIntent();
        student = intent.getParcelableExtra("Student");
        searchKeywords = intent.getStringExtra("Keywords");

        searchView = (ListView)findViewById(R.id.searchList);
        friendList = new ArrayList<>();

        mapshowButton = (Button)findViewById(R.id.mapshowButton2);
        mapshowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mapIntent = new Intent(SearchResultActivity.this,LocationActivity.class);
                mapIntent.putParcelableArrayListExtra("StudentList",friendList);
                mapIntent.putExtra("Student",student);
                startActivity(mapIntent);
            }
        });

        friendsearchAdapter = new FriendSearchAdapter(this,friendList);
        searchView.setAdapter(friendsearchAdapter);
        searchView.setOnItemClickListener(this);

        new AsyncTask<String,Void,String>(){
            @Override
            protected String doInBackground(String... params) {
                String searchResult = RestClient.findByKeywords(searchKeywords,student);
                return searchResult;
            }

            @Override
            protected void onPostExecute(String response) {
                if (response.equals("[]") || response.equals("")) {
                    Toast.makeText(getBaseContext(), "No user has been found", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    String resultbFormat = response.substring(2, response.length() - 2);
                    String resultaFormat = resultbFormat.replace("},{", ";");
                    String[] friendString = resultaFormat.split(";");
                    for (int a = 0; a < friendString.length; a++) {
                        String singleResult = friendString[a].replace("\":", "\",");
                        Log.i("Output", singleResult);
                        String[] propertyList = singleResult.split(",");
                        Student currStudent = new Student();
                        for (int i = 0; i < propertyList.length; i++) {
                            if (propertyList[i].contains("address")) {
                                currStudent.setAddress(propertyList[i + 1].substring(1, propertyList[i + 1].length() - 1));
                            }
                            if (propertyList[i].contains("password")) {
                                currStudent.setPassword(propertyList[i + 1].substring(1, propertyList[i + 1].length() - 1));
                            }
                            if (propertyList[i].contains("course")) {
                                currStudent.setCourse(propertyList[i + 1].substring(1, propertyList[i + 1].length() - 1));
                            }
                            if (propertyList[i].contains("currjob")) {
                                currStudent.setCurrjob(propertyList[i + 1].substring(1, propertyList[i + 1].length() - 1));
                            }
                            if (propertyList[i].contains("dob")) {
                                Date dob = null;
                                try {
                                    dob = new SimpleDateFormat("yyyy-MM-dd").parse(propertyList[i + 1].substring(1, propertyList[i + 1].length() - 1));
                                    currStudent.setDob(dob);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                            }
                            if (propertyList[i].contains("favmovie")) {
                                currStudent.setFavmovie(propertyList[i + 1].substring(1, propertyList[i + 1].length() - 1));
                            }
                            if (propertyList[i].contains("favsport")) {
                                currStudent.setFavsport(propertyList[i + 1].substring(1, propertyList[i + 1].length() - 1));
                            }
                            if (propertyList[i].contains("favunit")) {
                                currStudent.setFavunit(propertyList[i + 1].substring(1, propertyList[i + 1].length() - 1));
                            }
                            if (propertyList[i].contains("firstname")) {
                                currStudent.setFirstname(propertyList[i + 1].substring(1, propertyList[i + 1].length() - 1));
                            }
                            if (propertyList[i].contains("gender")) {
                                currStudent.setGender(propertyList[i + 1].substring(1, propertyList[i + 1].length() - 1).charAt(0));
                            }
                            if (propertyList[i].contains("monemail")) {
                                currStudent.setMonemail(propertyList[i + 1].substring(1, propertyList[i + 1].length() - 1));
                            }
                            if (propertyList[i].contains("nationality")) {
                                currStudent.setNationality(propertyList[i + 1].substring(1, propertyList[i + 1].length() - 1));
                            }
                            if (propertyList[i].contains("navlanguage")) {
                                currStudent.setNavlanguage(propertyList[i + 1].substring(1, propertyList[i + 1].length() - 1));
                            }
                            if (propertyList[i].contains("studentid")) {
                                currStudent.setStudentid(Integer.valueOf(propertyList[i + 1]));
                            }
                            if (propertyList[i].contains("studymode")) {
                                currStudent.setStudymode(propertyList[i + 1].substring(1, propertyList[i + 1].length() - 1).charAt(0));
                            }
                            if (propertyList[i].contains("surburb")) {
                                currStudent.setSurburb(propertyList[i + 1].substring(1, propertyList[i + 1].length() - 1));
                            }
                            if (propertyList[i].contains("surname")) {
                                currStudent.setSurname(propertyList[i + 1].substring(1, propertyList[i + 1].length() - 1));
                            }
                            if (propertyList[i].contains("subdatetime")) {
                                Date subdatetime = null;
                                try {
                                    subdatetime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(propertyList[i + 1].substring(1, 10) + " " + propertyList[i + 1].substring(12, 19));
                                    currStudent.setSubdatetime(subdatetime);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                            }


                        }
                        friendList.add(currStudent);
                        searchView.invalidateViews();

                    }
                }
            }
        }.execute();



    }

    @Override
    public void onItemClick(final AdapterView<?> parent, View view, final int position, long id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setTitle("Add Person?");
        builder.setMessage("Are you sure you wish to add this relationship?");
        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

               int index = friendList.indexOf(parent.getItemAtPosition(position));
                final Student friend = friendList.get(index);
                final String friendid =String.valueOf(friend.getStudentid()) ;
                String studentid = String.valueOf(student.getStudentid());
                new AsyncTask<String,Void,String>(){

                    @Override
                    protected String doInBackground(String... params) {
                        int index2 =1;
                        String output = "";
                        int relationid = 1;
                        while(!RestClient.findByRelationid(index2).equals("[]"))
                        {
                            index2++;
                        }
                        relationid = index2;
                        Boolean found = false;
                        String result1 = RestClient.findByStudentid(student);
                        String result2 = RestClient.findByFriendid(student);
                        if(result1.equals("[]") && result2.equals("[]")){
                            Friendship friendship = new Friendship();
                            if (friend.getStudentid() < student.getStudentid()) {
                                friendship.setStudentid(friend);
                                friendship.setFriendid(student);
                                friendship.setRelationid(relationid);
                            } else{
                                friendship.setStudentid(student);
                                friendship.setFriendid(friend);
                                friendship.setRelationid(relationid);
                            }

                            Calendar c = Calendar.getInstance();
                            int second = c.get(Calendar.SECOND);
                            int minute = c.get(Calendar.MINUTE);
                            int hour = c.get(Calendar.HOUR_OF_DAY);
                            int year = c.get(Calendar.YEAR);
                            int month = c.get(Calendar.MONTH)+1;
                            int day = c.get(Calendar.DATE);

                            String subscriptionTime = year+"-"+month+"-"+day+" "+hour+":"+minute+":"+second;
                            Date subscribTime = new Date();

                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                            try {
                                subscribTime = dateFormat.parse(subscriptionTime);
                                Log.i("MyApp","sub input success");
                            } catch (ParseException e) {
                                e.printStackTrace();
                                Log.i("MyApp",e.toString());
                            }
                            friendship.setStartdate(subscribTime);
                            RestClient.createFriendship(friendship);
                            output = "Relation has been set up.";
                        } else {
                            if (!result1.equals("[]")) {

                                String resultBeforeFormat = result1.replace("}},", ";").substring(1, result1.replace("}},{", ";").length() - 1);
                                String[] resultFormat = resultBeforeFormat.split(";");
                                Log.i("Friendship", resultFormat[1]);

                                for (int i = 0; i < resultFormat.length; i++) {
                                    if (resultFormat[i].substring(0, resultFormat[i].indexOf("\"friendid")).equals("{") && resultFormat[i].contains(friendid)) {

                                        found = true;
                                    }
                                }


                                if (found) {
                                    output = "Relationship is existed.Please Check.";
                                } else {

                                    Friendship friendship = new Friendship();
                                    if (friend.getStudentid() < student.getStudentid()) {
                                        friendship.setStudentid(friend);
                                        friendship.setFriendid(student);
                                        friendship.setRelationid(relationid);
                                    } else {
                                        friendship.setStudentid(student);
                                        friendship.setFriendid(friend);
                                        friendship.setRelationid(relationid);
                                    }

                                    Calendar c = Calendar.getInstance();
                                    int second = c.get(Calendar.SECOND);
                                    int minute = c.get(Calendar.MINUTE);
                                    int hour = c.get(Calendar.HOUR_OF_DAY);
                                    int year = c.get(Calendar.YEAR);
                                    int month = c.get(Calendar.MONTH) + 1;
                                    int day = c.get(Calendar.DATE);

                                    String subscriptionTime = year + "-" + month + "-" + day + " " + hour + ":" + minute + ":" + second;
                                    Date subscribTime = new Date();

                                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                                    try {
                                        subscribTime = dateFormat.parse(subscriptionTime);
                                        Log.i("MyApp", "sub input success");
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                        Log.i("MyApp", e.toString());
                                    }
                                    friendship.setStartdate(subscribTime);
                                    RestClient.createFriendship(friendship);
                                    output="Relation has been set up.";
                                }


                            }
                            if (!result2.equals("[]")) {
                                String resultBeforeFormat = result1.replace("}},", ";").substring(1, result1.replace("}},{", ";").length() - 1);
                                String[] resultFormat = resultBeforeFormat.split(";");
                                Log.i("Friendship", resultFormat[1]);

                                for (int i = 0; i < resultFormat.length; i++) {
                                    if (resultFormat[i].substring(0, resultFormat[i].indexOf("\"friendid")).equals("{") && resultFormat[i].contains(friendid)) {

                                        found = true;
                                    }
                                }


                                if (found) {
                                    output = "Relationship is existed.Please Check.";
                                } else {

                                    Friendship friendship = new Friendship();
                                    if (friend.getStudentid() < student.getStudentid()) {
                                        friendship.setStudentid(friend);
                                        friendship.setFriendid(student);
                                        friendship.setRelationid(relationid);
                                    } else {
                                        friendship.setStudentid(student);
                                        friendship.setFriendid(friend);
                                        friendship.setRelationid(relationid);
                                    }

                                    Calendar c = Calendar.getInstance();
                                    int second = c.get(Calendar.SECOND);
                                    int minute = c.get(Calendar.MINUTE);
                                    int hour = c.get(Calendar.HOUR_OF_DAY);
                                    int year = c.get(Calendar.YEAR);
                                    int month = c.get(Calendar.MONTH) + 1;
                                    int day = c.get(Calendar.DATE);

                                    String subscriptionTime = year + "-" + month + "-" + day + " " + hour + ":" + minute + ":" + second;
                                    Date subscribTime = new Date();

                                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                                    try {
                                        subscribTime = dateFormat.parse(subscriptionTime);
                                        Log.i("MyApp", "sub input success");
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                        Log.i("MyApp", e.toString());
                                    }
                                    friendship.setStartdate(subscribTime);
                                    RestClient.createFriendship(friendship);
                                    output="Relation has been set up.";
                                }


                            }
                        }

                        return output;
                    }
                    @Override
                    protected void onPostExecute(String response){


                        Toast.makeText(getBaseContext(),response , Toast.LENGTH_SHORT).show();
                    }

                }.execute();

            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.create().show();
    }
}

