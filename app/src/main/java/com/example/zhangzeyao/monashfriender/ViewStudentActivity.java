package com.example.zhangzeyao.monashfriender;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.zhangzeyao.monashfriender.models.SpinnerHelper;
import com.example.zhangzeyao.monashfriender.models.Student;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ViewStudentActivity extends AppCompatActivity {

    private Student student;

    private TextView fnameText;
    private TextView fidText;
    private TextView femailText;
    private TextView fGenderText;
    private TextView fdobText;
    private TextView faddressText;
    private TextView fnationText;
    private TextView fnatLanText;
    private TextView fCourseText;
    private TextView fStudyModeText;
    private TextView fFavMovieText;
    private TextView fFavSportText;
    private TextView fFavUnitText;
    private TextView fCurrJobText;

    private Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_student);

        Intent receiveIntent = getIntent();
        student = receiveIntent.getParcelableExtra("Student");

        fnameText = (TextView)findViewById(R.id.friendNameText);
        fidText = (TextView)findViewById(R.id.friendidText);
        femailText = (TextView)findViewById(R.id.friendEmailText);
        fGenderText = (TextView)findViewById(R.id.friendGenderText);
        fdobText = (TextView)findViewById(R.id.friendDobText);
        faddressText = (TextView)findViewById(R.id.friendAddressText);
        fnationText = (TextView)findViewById(R.id.friendNationText);
        fnatLanText = (TextView)findViewById(R.id.friendNatLanText);
        fCourseText = (TextView)findViewById(R.id.friendCourseText);
        fStudyModeText = (TextView)findViewById(R.id.friendStudyModeText);
        fFavMovieText = (TextView)findViewById(R.id.friendFavMovieText);
        fFavSportText = (TextView)findViewById(R.id.friendFavSportText);
        fFavUnitText = (TextView)findViewById(R.id.friendFavUnitText);
        fCurrJobText = (TextView)findViewById(R.id.friendCurJobText);

        fnameText.setText(student.getFirstname() + " "+ student.getSurname());
        fidText.setText(String.valueOf(student.getStudentid()));
        femailText.setText(student.getMonemail());
        if(student.getGender() == 'F'){
            fGenderText.setText("Female");
        } else if(student.getGender() == 'M'){
            fGenderText.setText("Male");
        }else{
            fGenderText.setText("Other");
        }

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        fdobText.setText(df.format(student.getDob()));
        faddressText.setText(student.getAddress()+", "+ student.getSurburb());
        fnationText.setText(nationalityChange(student.getNationality()));
        fnatLanText.setText(student.getNavlanguage());
        fCourseText.setText(courseChange(student.getCourse()));
        fStudyModeText.setText(studyModeChange(Character.toString(student.getStudymode())));
        fFavMovieText.setText(student.getFavmovie());
        fFavSportText.setText(student.getFavsport());
        fFavUnitText.setText(student.getFavunit());
        fCurrJobText.setText(student.getCurrjob());

        fFavMovieText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String moviename = student.getFavmovie();
                Intent movieIntent = new Intent(ViewStudentActivity.this,MovieInfoActivity.class);
                movieIntent.putExtra("Movie",moviename);
                startActivity(movieIntent);
            }
        });




        backButton = (Button)findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backIntent = new Intent(ViewStudentActivity.this,MainInterfaceActivity.class);
                backIntent.putExtra("Student",student);
                startActivity(backIntent);
                finish();
            }
        });
    }

    public String nationalityChange(String input){
        String output = "";
        DatabaseHelper db = new DatabaseHelper(getApplicationContext());
        ArrayList<SpinnerHelper> sp = new ArrayList<>(db.GetAllCountry().values());
        for(int i = 0;i<sp.size();i++){
            if(sp.get(i).getShortName().equals(input))
            {
                output = sp.get(i).getLongName();

            }
        }
        return output;
    }

    public String courseChange(String input){
        String output = "";
        DatabaseHelper db = new DatabaseHelper(getApplicationContext());
        ArrayList<SpinnerHelper> sp = new ArrayList<>(db.GetAllCourse().values());
        for(int i = 0;i<sp.size();i++){
            if(sp.get(i).getShortName().equals(input))
            {
                output = sp.get(i).getLongName();

            }
        }
        return output;
    }

    public String studyModeChange(String input){
        String output = "";
        DatabaseHelper db = new DatabaseHelper(getApplicationContext());
        ArrayList<SpinnerHelper> sp = new ArrayList<>(db.GetAllStudyMode().values());
        for(int i = 0;i<sp.size();i++){
            if(sp.get(i).getShortName().equals(input))
            {
                output = sp.get(i).getLongName();

            }
        }
        return output;
    }
}
