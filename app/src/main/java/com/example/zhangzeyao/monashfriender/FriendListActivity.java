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

import com.example.zhangzeyao.monashfriender.models.Friendship;
import com.example.zhangzeyao.monashfriender.models.Student;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import android.widget.Button;

public class FriendListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private Student student;
    private ListView elistView;
    private ArrayList<Student> friendList;

    private ArrayList<Friendship> friendships;
    private Friendship friendship;
    private Button mapshowButton;

    private FriendExistAdapter friendExistAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_list);

        Intent intent = getIntent();
        student = intent.getParcelableExtra("Student");

        friendships = new ArrayList<>();
        friendship = new Friendship();
        friendList = new ArrayList<>();
        mapshowButton = (Button)findViewById(R.id.mapshow);
        mapshowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mapIntent = new Intent(FriendListActivity.this,LocationActivity.class);
                mapIntent.putParcelableArrayListExtra("StudentList",friendList);
                mapIntent.putExtra("Student",student);
                startActivity(mapIntent);
            }
        });
        new AsyncTask<String,Void,String[]>() {

            @Override
            protected String[] doInBackground(String... params) {

                String result1 = RestClient.findByStudentid(student);
                Log.i("Friend", result1);
                String result2 = RestClient.findByFriendid(student);
                Log.i("Friend", result2);
                String[] result = {result1, result2};

                return result;
            }

            @Override
            protected void onPostExecute(String[] response) {
                String resultInExecute1 = response[0];// Need to find friend
                String resultInExecute2 = response[1];// Need to find Student

                if (resultInExecute1.equals("[]") && resultInExecute2.equals("[]")) {
                    Toast.makeText(FriendListActivity.this, "No Friends exists right now", Toast.LENGTH_SHORT).show();
                } else {
                    if (!resultInExecute1.equals("[]")) {
                        String resultBeforeFormat = response[0].replace("}},", ";").substring(1, response[0].replace("}},{", ";").length() - 1);
                        String[] resultFormat = resultBeforeFormat.split(";");
                        Log.i("Friendship", resultFormat[1]);
                        for (int i = 0; i < resultFormat.length; i++) {
                            if (resultFormat[i].substring(0, resultFormat[i].indexOf("\"friendid")).equals("{")) {
                                String relationid1 = resultFormat[i].substring(resultFormat[i].indexOf(",\"relationid\":"),resultFormat[i].indexOf(",\"startdate\":"));
                                String relationid = relationid1.substring(14);
                                friendship.setRelationid(Integer.valueOf(relationid));

                                Log.i("Friendship123",relationid);
                                String startdate1 = resultFormat[i].substring(resultFormat[i].indexOf(",\"startdate\":"),resultFormat[i].indexOf(",\"studentid\":{"));
                                String startdate = startdate1.substring(14,23) + " " + startdate1.substring(25,33);

                                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                                try {
                                    friendship.setStartdate(df.parse(startdate));
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                                String friendProfile = resultFormat[i].substring(0, resultFormat[i].indexOf("},\"relationid"));
                                String friendProfileFormat = friendProfile.substring(12);
                                Log.i("Friendship1", friendProfileFormat);
                                Student currStudent = new Student();

                                String resultAfterFormate = friendProfileFormat.replace("\":", "\",").substring(1);
                                Log.i("Friendship2", resultAfterFormate);
                                String[] propertyList = resultAfterFormate.split(",");
                                for (int a = 0; a < propertyList.length; a++) {
                                    if (propertyList[a].contains("address")) {
                                        currStudent.setAddress(propertyList[a + 1].substring(1, propertyList[a + 1].length() - 1));
                                    }
                                    if (propertyList[a].contains("password")) {
                                        currStudent.setPassword(propertyList[a + 1].substring(1, propertyList[a + 1].length() - 1));
                                    }
                                    if (propertyList[a].contains("course")) {
                                        currStudent.setCourse(propertyList[a + 1].substring(1, propertyList[a + 1].length() - 1));
                                    }
                                    if (propertyList[a].contains("currjob")) {
                                        currStudent.setCurrjob(propertyList[a + 1].substring(1, propertyList[a + 1].length() - 1));
                                    }
                                    if (propertyList[a].contains("dob")) {
                                        Date dob = null;
                                        try {
                                            dob = new SimpleDateFormat("yyyy-MM-dd").parse(propertyList[a + 1].substring(1, 10));
                                            currStudent.setDob(dob);
                                        } catch (ParseException e) {
                                            e.printStackTrace();
                                        }

                                    }
                                    if (propertyList[a].contains("favmovie")) {
                                        currStudent.setFavmovie(propertyList[a + 1].substring(1, propertyList[a + 1].length() - 1));
                                    }
                                    if (propertyList[a].contains("favsport")) {
                                        currStudent.setFavsport(propertyList[a + 1].substring(1, propertyList[a + 1].length() - 1));
                                    }
                                    if (propertyList[a].contains("favunit")) {
                                        currStudent.setFavunit(propertyList[a + 1].substring(1, propertyList[a + 1].length() - 1));
                                    }
                                    if (propertyList[a].contains("firstname")) {
                                        currStudent.setFirstname(propertyList[a + 1].substring(1, propertyList[a + 1].length() - 1));
                                    }
                                    if (propertyList[a].contains("gender")) {
                                        currStudent.setGender(propertyList[a + 1].substring(1, propertyList[a + 1].length() - 1).charAt(0));
                                    }
                                    if (propertyList[a].contains("monemail")) {
                                        currStudent.setMonemail(propertyList[a + 1].substring(1, propertyList[a + 1].length() - 1));
                                    }
                                    if (propertyList[a].contains("nationality")) {
                                        currStudent.setNationality(propertyList[a + 1].substring(1, propertyList[a + 1].length() - 1));
                                    }
                                    if (propertyList[a].contains("navlanguage")) {
                                        currStudent.setNavlanguage(propertyList[a + 1].substring(1, propertyList[a + 1].length() - 1));
                                    }
                                    if (propertyList[a].contains("studentid")) {
                                        currStudent.setStudentid(Integer.valueOf(propertyList[a + 1]));
                                    }
                                    if (propertyList[a].contains("studymode")) {
                                        currStudent.setStudymode(propertyList[a + 1].substring(1, propertyList[a + 1].length() - 1).charAt(0));
                                    }
                                    if (propertyList[a].contains("surburb")) {
                                        currStudent.setSurburb(propertyList[a + 1].substring(1, propertyList[a + 1].length() - 1));
                                    }
                                    if (propertyList[a].contains("surname")) {
                                        currStudent.setSurname(propertyList[a + 1].substring(1, propertyList[a + 1].length() - 1));
                                    }
                                    if (propertyList[a].contains("subdatetime")) {
                                        Date subdatetime = null;
                                        try {
                                            subdatetime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(propertyList[a + 1].substring(1, 10) + " " + propertyList[a + 1].substring(12, 19));
                                            currStudent.setSubdatetime(subdatetime);
                                        } catch (ParseException e) {
                                            e.printStackTrace();
                                        }

                                    }
                                }
                                Log.i("Friendship", "Success");
                                friendList.add(currStudent);
                                friendship.setFriendid(student);
                                friendship.setStudentid(currStudent);
                                friendships.add(friendship);
                                elistView.invalidateViews();
                            }

                        }
                    }

                    if (!resultInExecute2.equals("[]")) {
                        String resultBeforeFormat = response[1].replace("}},", ";").substring(1, response[1].replace("}},{", ";").length() - 1);
                        String[] resultFormat = resultBeforeFormat.split(";");
                        Log.i("Friendship", resultFormat[0]);
                        for (int i = 0; i < resultFormat.length; i++) {
                            if (resultFormat[i].substring(0, resultFormat[i].indexOf("\"friendid")).equals("{")) {
                                friendship.setEnddate(null);
                                String relationid1 = resultFormat[i].substring(resultFormat[i].indexOf(",\"relationid\":"),resultFormat[i].indexOf(",\"startdate\":"));
                                String relationid = relationid1.substring(14);
                                friendship.setRelationid(Integer.valueOf(relationid));

                                Log.i("Friendship123",relationid);
                                String startdate1 = resultFormat[i].substring(resultFormat[i].indexOf(",\"startdate\":"),resultFormat[i].indexOf(",\"studentid\":{"));
                                String startdate = startdate1.substring(14,23) + " " + startdate1.substring(25,33);

                                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                                try {
                                    friendship.setStartdate(df.parse(startdate));
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }


                                Log.i("Friendship123",startdate);
                                String friendProfile = resultFormat[i].substring(resultFormat[i].indexOf("\"studentid\":{"));
                                String friendProfileFormat = friendProfile.substring(12);
                                Log.i("Friendship999", friendProfileFormat);
                                Student currStudent = new Student();

                                String resultAfterFormate = friendProfileFormat.replace("\":", "\",").substring(1);
                                Log.i("Friendship2", resultAfterFormate);
                                String[] propertyList = resultAfterFormate.split(",");
                                for (int a = 0; a < propertyList.length; a++) {
                                    if (propertyList[a].contains("address")) {
                                        currStudent.setAddress(propertyList[a + 1].substring(1, propertyList[a + 1].length() - 1));
                                    }
                                    if (propertyList[a].contains("password")) {
                                        currStudent.setPassword(propertyList[a + 1].substring(1, propertyList[a + 1].length() - 1));
                                    }
                                    if (propertyList[a].contains("course")) {
                                        currStudent.setCourse(propertyList[a + 1].substring(1, propertyList[a + 1].length() - 1));
                                    }
                                    if (propertyList[a].contains("currjob")) {
                                        currStudent.setCurrjob(propertyList[a + 1].substring(1, propertyList[a + 1].length() - 1));
                                    }
                                    if (propertyList[a].contains("dob")) {
                                        Date dob = null;
                                        try {
                                            dob = new SimpleDateFormat("yyyy-MM-dd").parse(propertyList[a + 1].substring(1, 10));
                                            currStudent.setDob(dob);
                                        } catch (ParseException e) {
                                            e.printStackTrace();
                                        }

                                    }
                                    if (propertyList[a].contains("favmovie")) {
                                        currStudent.setFavmovie(propertyList[a + 1].substring(1, propertyList[a + 1].length() - 1));
                                    }
                                    if (propertyList[a].contains("favsport")) {
                                        currStudent.setFavsport(propertyList[a + 1].substring(1, propertyList[a + 1].length() - 1));
                                    }
                                    if (propertyList[a].contains("favunit")) {
                                        currStudent.setFavunit(propertyList[a + 1].substring(1, propertyList[a + 1].length() - 1));
                                    }
                                    if (propertyList[a].contains("firstname")) {
                                        currStudent.setFirstname(propertyList[a + 1].substring(1, propertyList[a + 1].length() - 1));
                                    }
                                    if (propertyList[a].contains("gender")) {
                                        currStudent.setGender(propertyList[a + 1].substring(1, propertyList[a + 1].length() - 1).charAt(0));
                                    }
                                    if (propertyList[a].contains("monemail")) {
                                        currStudent.setMonemail(propertyList[a + 1].substring(1, propertyList[a + 1].length() - 1));
                                    }
                                    if (propertyList[a].contains("nationality")) {
                                        currStudent.setNationality(propertyList[a + 1].substring(1, propertyList[a + 1].length() - 1));
                                    }
                                    if (propertyList[a].contains("navlanguage")) {
                                        currStudent.setNavlanguage(propertyList[a + 1].substring(1, propertyList[a + 1].length() - 1));
                                    }
                                    if (propertyList[a].contains("studentid")) {
                                        currStudent.setStudentid(Integer.valueOf(propertyList[a + 1]));
                                    }
                                    if (propertyList[a].contains("studymode")) {
                                        currStudent.setStudymode(propertyList[a + 1].substring(1, propertyList[a + 1].length() - 1).charAt(0));
                                    }
                                    if (propertyList[a].contains("surburb")) {
                                        currStudent.setSurburb(propertyList[a + 1].substring(1, propertyList[a + 1].length() - 1));
                                    }
                                    if (propertyList[a].contains("surname")) {
                                        currStudent.setSurname(propertyList[a + 1].substring(1, propertyList[a + 1].length() - 3));
                                    }
                                    if (propertyList[a].contains("subdatetime")) {
                                        Date subdatetime = null;
                                        try {
                                            subdatetime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(propertyList[a + 1].substring(1, 10) + " " + propertyList[a + 1].substring(12, 19));
                                            currStudent.setSubdatetime(subdatetime);
                                        } catch (ParseException e) {
                                            e.printStackTrace();
                                        }

                                    }
                                }
                                Log.i("Friendship", "Success");
                                friendList.add(currStudent);
                                friendship.setFriendid(student);
                                friendship.setStudentid(currStudent);
                                friendships.add(friendship);
                                elistView.invalidateViews();

                            }
                        }
                    }


                }
            }
        }.execute();

        elistView = (ListView)findViewById(R.id.efriendListView);
        friendExistAdapter = new FriendExistAdapter(this,friendList);
        elistView.setAdapter(friendExistAdapter);
        elistView.setOnItemClickListener(this);

    }


    @Override
    public void onItemClick(final AdapterView<?> parent, View view, final int position, long id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setTitle("Remove Person?");
        builder.setMessage("Are you sure you wish to remove this friend?");
        builder.setPositiveButton("Remove", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                final int friendshipid = friendList.indexOf(parent.getItemAtPosition(position));


                new AsyncTask<String,Void,String>(){

                    @Override
                    protected String doInBackground(String... params) {
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
                            Log.i("MyApp", e.toString());
                        }
                        Log.i("This APP", String.valueOf(friendshipid));
                        friendships.get(friendshipid).setEnddate(subscribTime);

                        RestClient.updateFriendship(friendships.get(friendshipid));//need Edit to the related friendship;
                        return "Success";
                    }

                    @Override
                    protected void onPostExecute(String response){

                    }
                }.execute();
                friendList.remove(parent.getItemAtPosition(position));
                Toast.makeText(getBaseContext(), "Relationship has been deleted", Toast.LENGTH_SHORT).show();
                elistView.invalidateViews();
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
