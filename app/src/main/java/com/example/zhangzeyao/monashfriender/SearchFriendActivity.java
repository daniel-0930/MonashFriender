package com.example.zhangzeyao.monashfriender;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import com.example.zhangzeyao.monashfriender.models.Student;

public class SearchFriendActivity extends AppCompatActivity {

    private Student student;
    private CheckBox surburbBox;
    private CheckBox courseBox;
    private CheckBox genderBox;
    private CheckBox dobBox;
    private CheckBox studymodeBox;
    private CheckBox nationBox;
    private CheckBox natLanBox;
    private CheckBox favsportBox;
    private CheckBox favmovieBox;
    private CheckBox favunitBox;
    private CheckBox currJobBox;
    private Button searchButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_friend);

        Intent intent = getIntent();
        student = intent.getParcelableExtra("Student");
        surburbBox = (CheckBox)findViewById(R.id.surburbCheck);
        courseBox = (CheckBox)findViewById(R.id.courseCheck);
        genderBox = (CheckBox)findViewById(R.id.genderCheck);
        dobBox = (CheckBox)findViewById(R.id.dobCheck);
        studymodeBox = (CheckBox)findViewById(R.id.studymodeCheck);
        nationBox = (CheckBox)findViewById(R.id.nationCheck);
        natLanBox = (CheckBox)findViewById(R.id.natLanCheck);
        favsportBox = (CheckBox)findViewById(R.id.favSportCheck);
        favmovieBox = (CheckBox)findViewById(R.id.favmovieCheck);
        favunitBox =(CheckBox)findViewById(R.id.favunitCheck);
        currJobBox = (CheckBox)findViewById(R.id.currJobCheck);
        searchButton = (Button)findViewById(R.id.searchButton);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String keywords = "";
                if(surburbBox.isChecked()){
                    keywords +="surburb,";
                }
                if(courseBox.isChecked()){
                    keywords+="course,";
                }
                if(genderBox.isChecked()){
                    keywords+="gender,";
                }
                if(dobBox.isChecked()){
                    keywords+="dob,";
                }
                if(studymodeBox.isChecked()){
                    keywords+="studymode,";
                }
                if(natLanBox.isChecked()){
                    keywords+="navLanguage,";
                }
                if(favsportBox.isChecked()){
                    keywords+="favsport,";
                }
                if(favunitBox.isChecked()){
                    keywords+="favunit,";
                }
                if(favmovieBox.isChecked()){
                    keywords+="favmovie,";
                }
                if(currJobBox.isChecked()){
                    keywords+="currjob,";
                }

                if(!keywords.equals("")){
                    String result = keywords.substring(0,keywords.length()-1);
                    Intent searchIntent = new Intent(SearchFriendActivity.this,SearchResultActivity.class);
                    searchIntent.putExtra("Student",student);
                    searchIntent.putExtra("Keywords",result);
                    startActivity(searchIntent);
                }

                surburbBox.setChecked(false);
                courseBox.setChecked(false);
                genderBox.setChecked(false);
                dobBox.setChecked(false);
                currJobBox.setChecked(false);
                favmovieBox.setChecked(false);
                favunitBox.setChecked(false);
                favsportBox.setChecked(false);
                natLanBox.setChecked(false);
                nationBox.setChecked(false);
                studymodeBox.setChecked(false);





            }
        });

    }
}
