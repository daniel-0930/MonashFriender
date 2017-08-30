package com.example.zhangzeyao.monashfriender;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.zhangzeyao.monashfriender.models.SpinnerHelper;
import com.example.zhangzeyao.monashfriender.models.Student;
import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CreateUserStep3 extends AppCompatActivity implements View.OnClickListener{

    private Spinner courseSpinner;
    private Spinner studyModeSpinner;
    private EditText favSportTextBox;
    private EditText favUnitTextBox;
    private EditText favMovieTextBox;
    private EditText currJobTextBox;
    private Button finishButton;

    private SharedPreferences mPerference;

    private Student student;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user_step3);

        courseSpinner = (Spinner)findViewById(R.id.courseSpinner);
        studyModeSpinner = (Spinner)findViewById(R.id.stuModeSpinner);
        favSportTextBox = (EditText)findViewById(R.id.favSportBox);
        favMovieTextBox = (EditText)findViewById(R.id.favMovieBox);
        favUnitTextBox = (EditText)findViewById(R.id.favUnitBox);
        currJobTextBox = (EditText)findViewById(R.id.currJobBox);

        loadDataToSpinner();
        mPerference = getSharedPreferences("Student", MODE_PRIVATE);

        Intent intent = getIntent();
        student = intent.getParcelableExtra("newStudentPart2");

        finishButton = (Button)findViewById(R.id.finishButton);
        finishButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        String favSport = favSportTextBox.getText().toString();
        String favMovie = favMovieTextBox.getText().toString();
        String favUnit = favUnitTextBox.getText().toString();
        String currJob = currJobTextBox.getText().toString();

        String courseString = courseSpinner.getSelectedItem().toString();
        String studyModeString = studyModeSpinner.getSelectedItem().toString();
        String course = courseChange(courseString);
        String studyMode = studyModeChange(studyModeString);

        if (favSport.trim().equals("") || favUnit.trim().equals("") || currJob.trim().equals("") || favMovie.trim().equals("")) {
            if(favSport.trim().equals("")){
                favSportTextBox.setError("Please Enter your favorite sport");
            }
            if(favUnit.trim().equals("")){
               favUnitTextBox.setError("Please Enter your Favorite Unit");
            }
            if(currJob.trim().equals("") ){
                currJobTextBox.setError("Please Enter your current Job");
            }
            if(favMovie.trim().equals("")){
                favMovieTextBox.setError("Please Enter your favorite Movie");
            }

        } else {
            student.setFavmovie(favMovie);
            student.setFavsport(favSport);
            student.setFavunit(favUnit);
            student.setCurrjob(currJob);

            student.setCourse(course);
            student.setStudymode(studyMode.charAt(0));

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

            student.setSubdatetime(subscribTime);

            new AsyncTask<String, Void, String>() {

                @Override
                protected String doInBackground(String... params) {
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                    SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    Log.i("MyApp", student.getStudentid().toString() + "/" + student.getMonemail()
                            + "/" + student.getPassword() + "/" + student.getFirstname() + "/"
                            + student.getSurname() + "/" + student.getAddress() + "/" + student.getGender().toString()
                            + "/" + student.getSurburb() + "/" + student.getNationality() + "/" + df.format(student.getDob())
                            + "/" + df2.format(student.getSubdatetime()) + "/" + student.getNavlanguage() + "/" + student.getCourse() + student.getStudymode().toString());
                    RestClient.createStudent(student);
                    Log.i("Import", "Import Succeed");
                    SharedPreferences.Editor editor = mPerference.edit();
                    Gson gson = new Gson();
                    String json = gson.toJson(student);
                    editor.putBoolean("isLogin", true);
                    editor.putString("currStudent", json);
                    editor.commit();
                    Intent mainInterfaceIntent = new Intent(CreateUserStep3.this, MainInterfaceActivity.class);
                    mainInterfaceIntent.putExtra("Student", student);
                    startActivity(mainInterfaceIntent);
                    CreateUserActivity.c1.finish();
                    CreateUserStep2.c2.finish();
                    finish();
                    return "Your registration is succeeded";
                }

                @Override
                protected void onPostExecute(String response) {
                    Toast.makeText(CreateUserStep3.this, response, Toast.LENGTH_SHORT).show();
                    Log.i("Myresult", "no email");
                }
            }.execute();
        }
    }

    public String courseChange(String input){
        String output = "";
        DatabaseHelper db = new DatabaseHelper(getApplicationContext());
        ArrayList<SpinnerHelper> sp = new ArrayList<>(db.GetAllCourse().values());
        for(int i = 0;i<sp.size();i++){
            if(sp.get(i).getLongName().equals(input))
            {
                output = sp.get(i).getShortName();

            }
        }
        return output;
    }

    public String studyModeChange(String input){
        String output = "";
        DatabaseHelper db = new DatabaseHelper(getApplicationContext());
        ArrayList<SpinnerHelper> sp = new ArrayList<>(db.GetAllStudyMode().values());
        for(int i = 0;i<sp.size();i++){
            if(sp.get(i).getLongName().equals(input))
            {
                output = sp.get(i).getShortName();

            }
        }
        return output;
    }

    public void loadDataToSpinner(){
        DatabaseHelper db = new DatabaseHelper(getApplicationContext());
        if(db.GetAllCourse().size() == 0){
            db.CreateDefaultCourse();
        }
        if(db.GetAllStudyMode().size() == 0){
            db.CreateDefaultStudyMode();
        }


        ArrayList<SpinnerHelper> sp1 = new ArrayList<>(db.GetAllCourse().values());
        ArrayList<SpinnerHelper> sp2 = new ArrayList<>(db.GetAllStudyMode().values());
        ArrayList<String> courses = new ArrayList<>();
        ArrayList<String> studymodes = new ArrayList<>();
        for(int i = 0;i< sp1.size();i++){
            courses.add(sp1.get(i).getLongName());
        }

        for(int i = 0;i< sp2.size();i++){
            studymodes.add(sp2.get(i).getLongName());
        }


        ArrayAdapter<String> courseAdapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item, courses);
        courseAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        courseSpinner.setAdapter(courseAdapter);

        ArrayAdapter<String> studyModeAdapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item, studymodes);
        studyModeAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        studyModeSpinner.setAdapter(studyModeAdapter);
    }
}
