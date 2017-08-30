package com.example.zhangzeyao.monashfriender;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.zhangzeyao.monashfriender.models.Student;
import com.example.zhangzeyao.monashfriender.models.StudentLocation;
import com.mapbox.mapboxsdk.MapboxAccountManager;
import com.mapbox.mapboxsdk.annotations.Icon;
import com.mapbox.mapboxsdk.annotations.IconFactory;
import com.mapquest.mapping.maps.OnMapReadyCallback;
import com.mapquest.mapping.maps.MapboxMap;
import com.mapquest.mapping.maps.MapView;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class LocationActivity extends AppCompatActivity {

    private MapboxMap mMapboxMap;
    private MapView mMapView;

    private Student student;
    private ArrayList<Student> studentList;


    private final LatLng CAULFIELD = new LatLng(-37.8768, 145.0458);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MapboxAccountManager.start(getApplicationContext());
        setContentView(R.layout.activity_location);

        Intent mapIntent = getIntent();
        studentList=mapIntent.getParcelableArrayListExtra("StudentList");
        student = mapIntent.getParcelableExtra("Student");



        mMapView = (MapView) findViewById(R.id.mapquestMapView);
        mMapView.onCreate(savedInstanceState);
        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(final MapboxMap mapboxMap) {
                mMapboxMap = mapboxMap;
                mMapboxMap.moveCamera(CameraUpdateFactory.newLatLngZoom(CAULFIELD, 10));


                new AsyncTask<String,Void,ArrayList<StudentLocation>>(){

                    @Override
                    protected ArrayList<StudentLocation> doInBackground(String... params) {
                        studentList.add(student);
                        ArrayList<StudentLocation> studentLocations = new ArrayList<StudentLocation>();
                        for(Student astudent:studentList) {
                            String result = RestClient.findLocationByStudentid(astudent);
                            if (!result.equals("")&&!result.equals("[]")) {
                                String resultBFormat = result.replace("},{", ";");
                                String[] resultAFormat = resultBFormat.split(";");
                                ArrayList<StudentLocation> studLocForCompare = new ArrayList<StudentLocation>();
                                for (int i = 0; i < resultAFormat.length; i++) {
                                    String resultCFormat = resultAFormat[i].replace("\":", "\",");
                                    String[] resultDFormat = resultCFormat.split(",");
                                    StudentLocation s = new StudentLocation();
                                    for (int b = 0; b < resultDFormat.length; b++) {

                                        if (resultDFormat[b].contains("latitude")) {
                                            s.setLatitude(Double.valueOf(resultDFormat[b+1]));
                                        }
                                        if (resultDFormat[b].contains("longitude")) {
                                            s.setLongitude(Double.valueOf(resultDFormat[b+1]));
                                        }
                                        if(resultDFormat[b].contains("updatedatetime")){
                                            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                                            try {
                                                s.setUpdateTime(df.parse(resultDFormat[b+1].substring(1,10)+" "+resultDFormat[b+1].substring(12,19)));
                                            } catch (ParseException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }
                                    s.setStudent(astudent);
                                    studLocForCompare.add(s);
                                }
                                StudentLocation s = studLocForCompare.get(0);
                                for(int i =0;i<studLocForCompare.size();i++){

                                    StudentLocation s1 = studLocForCompare.get(i);
                                    if(s1.getUpdateTime().after(s.getUpdateTime())){
                                        s = s1;
                                    }

                                }
                                studentLocations.add(s);

                            }
                        }
                        return studentLocations;
                    }

                    @Override
                    protected void onPostExecute(ArrayList<StudentLocation> response){
                        for(StudentLocation s3: response){

                            if(s3.getStudent().getStudentid() == student.getStudentid()){
                                LatLng thisLocation = new LatLng(s3.getLatitude(),s3.getLongitude());
                                MarkerOptions markerOptions = new MarkerOptions();
                                markerOptions.position(thisLocation);
                                markerOptions.title(student.getFirstname()+" "+student.getSurname()+"\n"+"I am here!");
                                markerOptions.snippet("Favorite Movie: "+student.getFavmovie()+"\n"+"Favorite Sport"+student.getFavsport()+"\n"+"Favorite Unit"+student.getFavunit());
                                IconFactory iconFactory = IconFactory.getInstance(LocationActivity.this);
                                Icon icon = iconFactory.fromResource(R.drawable.newmarker2);
                                markerOptions.icon(icon);
                                mapboxMap.addMarker(markerOptions);
                            }else {
                                addMarker(mapboxMap, s3.getStudent(), s3.getLatitude(), s3.getLongitude());
                            }
                        }

                    }
                }.execute();
            }
        });
    }
    private void addMarker(MapboxMap mapboxMap) {
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(CAULFIELD);
        markerOptions.title("San Francisco");
        markerOptions.snippet("Welcome to San Fran!");
        mapboxMap.addMarker(markerOptions);
    }

    public void addMarker(MapboxMap mapboxMap,Student student, Double latitude, Double longitude){
        LatLng thisLocation = new LatLng(latitude,longitude);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(thisLocation);
        markerOptions.title(student.getFirstname()+" "+student.getSurname());
        markerOptions.snippet("Favorite Movie: "+student.getFavmovie()+"\n"+"Favorite Sport"+student.getFavsport()+"\n"+"Favorite Unit"+student.getFavunit());
        mapboxMap.addMarker(markerOptions);
    }
    @Override
    public void onResume()
    { super.onResume(); mMapView.onResume(); }
    @Override
    public void onPause()
    { super.onPause(); mMapView.onPause(); }
    @Override
    protected void onDestroy()
    { super.onDestroy(); mMapView.onDestroy(); }
    @Override
    protected void onSaveInstanceState(Bundle outState)
    { super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }
}
