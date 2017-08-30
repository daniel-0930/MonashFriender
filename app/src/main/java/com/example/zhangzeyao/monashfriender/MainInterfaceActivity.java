package com.example.zhangzeyao.monashfriender;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zhangzeyao.monashfriender.models.Student;

import java.util.Calendar;

public class MainInterfaceActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Student student;
    private TextView studentnameTextBox;
    private TextView studentEmailTextBox;

    private TextView userNameTextView;
    private TextView currDayTextView;

    private TextView tempuratureTextView;
    private TextView descriptionTextView;
    private ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_interface);

        Intent receiveIntent = getIntent();
        student = receiveIntent.getParcelableExtra("Student");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Open my Maol Box?", Snackbar.LENGTH_LONG)
                        .setAction("Open", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent mailIntent = getPackageManager().getLaunchIntentForPackage("com.android.email");
                                startActivity(mailIntent);
                            }
                        }).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View header = navigationView.getHeaderView(0);
        studentnameTextBox = (TextView)header.findViewById(R.id.userNameText);
        studentEmailTextBox = (TextView)header.findViewById(R.id.userEmailText);

        tempuratureTextView = (TextView)findViewById(R.id.temperatureText);
        descriptionTextView = (TextView)findViewById(R.id.descriptionText);
        imageView = (ImageView)findViewById(R.id.weatherImage);

        studentEmailTextBox.setText(student.getMonemail());
        studentnameTextBox.setText(student.getSurname()+" "+student.getFirstname());

        userNameTextView = (TextView)findViewById(R.id.userNameTextView);
        currDayTextView = (TextView)findViewById(R.id.currentDayTextView);

        userNameTextView.setText(student.getSurname()+" "+student.getFirstname());

        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH)+1;
        int day = c.get(Calendar.DATE);
        String currdayTime = day + "/" + month + "/" +year;
        currDayTextView.setText(currdayTime);

        new AsyncTask<String,Void,String>(){

            @Override
            protected String doInBackground(String... params) {
                String weather = RestClient.weatherForcast(student);
                Log.i("MyApp",weather);
                return weather;
            }

            @Override
            protected void onPostExecute(String result){
                String[] resultFormat = result.split(",");
                String imageString_v1 = "";
                String tempurateString = "";
                for(int i=0;i<resultFormat.length;i++){
                    if(resultFormat[i].contains("icon"))
                    {
                        imageString_v1 = resultFormat[i].replace("\"icon\":\"","");
                        Log.i("Format",imageString_v1);
                        break;
                    }
                }

                String imageString= imageString_v1.replace("\"","");

                for(int i = 0;i<resultFormat.length;i++){
                    if(resultFormat[i].contains("temperature")){
                        tempurateString = resultFormat[i].replace("\"temperature\":","");
                        Log.i("Format",tempurateString);
                        break;
                    }
                }
                Float temp = (Float.valueOf(tempurateString) - 32)*5/9;
                tempuratureTextView.setText(String.format("%.2f", temp)+" \u2103");
                if(imageString.contains("-")) {
                    descriptionTextView.setText(imageString.replace("-", " ").substring(0,1).toUpperCase() + imageString.replace("-", " ").substring(1));
                } else{
                    descriptionTextView.setText(imageString.substring(0,1).toUpperCase() + imageString.substring(1));
                }

                if(imageString.equals("clear-night")){
                    imageView.setImageResource(R.drawable.moon);
                } else if(imageString.equals("clear-day")){
                    imageView.setImageResource(R.drawable.sun);
                } else if(imageString.equals("rain")){
                    imageView.setImageResource(R.drawable.rainsmall);
                }else if(imageString.equals("snow")){
                    imageView.setImageResource(R.drawable.snow);
                }else if(imageString.equals("sleet")){
                    imageView.setImageResource(R.drawable.rainhard);
                }else if(imageString.equals("wind")){
                    imageView.setImageResource(R.drawable.windy);
                }else if(imageString.equals("fog")){
                    imageView.setImageResource(R.drawable.cloudwind);
                }else if(imageString.equals("cloudy")){
                    imageView.setImageResource(R.drawable.cloud);
                }else if(imageString.equals("partly-cloudy-day")){
                    imageView.setImageResource(R.drawable.cloudsun);
                }else if(imageString.equals("partly-cloudy-night")){
                    imageView.setImageResource(R.drawable.cloudmoon);
                } else{
                    imageView.setImageResource(R.drawable.cloudwind);
                }


            }

        }.execute();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_friend) {
            Intent friendIntent = new Intent(MainInterfaceActivity.this,FriendListActivity.class);
            friendIntent.putExtra("Student",student);
            startActivity(friendIntent);
        } else if (id == R.id.nav_search) {
            Intent searchIntent = new Intent(MainInterfaceActivity.this,SearchFriendActivity.class);
            searchIntent.putExtra("Student",student);
            startActivity(searchIntent);

        } else if (id == R.id.nav_report) {
            Intent reportIntent = new Intent(MainInterfaceActivity.this,ReportActivity.class);
            reportIntent.putExtra("Student",student);
            startActivity(reportIntent);
        } else if (id == R.id.nav_logout) {
            getSharedPreferences("Student",MODE_PRIVATE).edit().remove("isLogin").commit();
            Intent logoutIntent = new Intent(MainInterfaceActivity.this,MainActivity.class);
            startActivity(logoutIntent);
            finish();

        } else if (id == R.id.nav_setting) {
            Intent settingIntent = new Intent(MainInterfaceActivity.this,UpdateProfileActivity.class);
            settingIntent.putExtra("Student",student);
            startActivity(settingIntent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
