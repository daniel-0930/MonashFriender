package com.example.zhangzeyao.monashfriender;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.zhangzeyao.monashfriender.models.Student;
import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    // Define widget in the screen
    private EditText emailBox;
    private EditText passwordBox;
    private Button signInButton;
    private Button createButton;
    private Button skipButton;

    private Student student;

    private SharedPreferences mPerference;
    private String emailString;
    private String passwordString;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Link widget with parameter
        emailBox = (EditText)findViewById(R.id.emailTextBox);
        passwordBox = (EditText)findViewById(R.id.passwordTextBox);

        signInButton = (Button)findViewById(R.id.loginButton);
        createButton = (Button)findViewById(R.id.createUserButton);
        skipButton = (Button)findViewById(R.id.skipLoginButton);

        signInButton.setOnClickListener(this);
        createButton.setOnClickListener(this);
        skipButton.setOnClickListener(this);

        emailString = "";
        passwordString = "";

        student = new Student();

        mPerference = getSharedPreferences("Student", MODE_PRIVATE);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.loginButton:
                emailString = emailBox.getText().toString().trim();
                passwordString = passwordBox.getText().toString();
                if(emailString.equals("") || passwordString.equals("")) {
                    if(emailString.equals("")){
                        emailBox.setError("Email address is not filled in");
                    }

                    if(passwordString.equals("")){
                        passwordBox.setError("Password is not filled in");
                    }
                }
                else{
                    LoginCheck l = new LoginCheck();
                    l.execute(new String[]{emailString, passwordString});
                }
                break;
            case R.id.createUserButton:
                Intent newIntent2 = new Intent(this,CreateUserActivity.class);
                startActivity(newIntent2);
                break;
            case R.id.skipLoginButton:
                if(getSharedPreferences("Student", MODE_PRIVATE).getBoolean("isLogin",false)){
                    Intent newIntent3 = new Intent(MainActivity.this,MainInterfaceActivity.class);
                    Gson gson = new Gson();
                    String json = getSharedPreferences("Student", MODE_PRIVATE).getString("currStudent","");
                    student = gson.fromJson(json,Student.class);
                    newIntent3.putExtra("Student",student);
                    startActivity(newIntent3);
                    finish();
                }
                else{
                    Toast.makeText(MainActivity.this, "You haven't login before.Please login.", Toast.LENGTH_SHORT).show();
                }
                break;

        }
    }

    private class LoginCheck extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... params) {

            RestClient rstClient = new RestClient();
            String result = rstClient.findByMonEmail(emailString);
            Log.i("Myapp",result);
            return result;
        }

        @Override
        protected void onPostExecute(String result){
            String passwordHashed = student.md5(passwordString);
           if(result.equals("[]")){
               emailBox.setError("Email address is not existed.");
               Log.i("Myresult","no email");
           }
           else if(!result.contains("\"password\":\""+passwordHashed+"\"")){
               passwordBox.setError("Wrong Password.");
           }
           else{

               String resultAfterFormate = result.replace("\":","\",").substring(2,result.replace("\":","\",").length()-2);
               Log.i("MyApp",resultAfterFormate);
               String[] propertyList = resultAfterFormate.split(",");
               for(int i=0;i<propertyList.length;i++){
                   if(propertyList[i].contains("address")){
                       student.setAddress(propertyList[i+1].substring(1,propertyList[i+1].length()-1));
                   }
                   if(propertyList[i].contains("password")){
                       student.setPassword(propertyList[i+1].substring(1,propertyList[i+1].length()-1));
                   }
                   if(propertyList[i].contains("course")){
                       student.setCourse(propertyList[i+1].substring(1,propertyList[i+1].length()-1));
                   }
                   if(propertyList[i].contains("currjob")){
                       student.setCurrjob(propertyList[i+1].substring(1,propertyList[i+1].length()-1));
                   }
                   if(propertyList[i].contains("dob")){
                       Date dob = null;
                       try {
                           dob = new SimpleDateFormat("yyyy-MM-dd").parse(propertyList[i+1].substring(1,propertyList[i+1].length()-1));
                           student.setDob(dob);
                       } catch (ParseException e) {
                           e.printStackTrace();
                       }

                   }
                   if(propertyList[i].contains("favmovie")){
                       student.setFavmovie(propertyList[i+1].substring(1,propertyList[i+1].length()-1));
                   }
                   if(propertyList[i].contains("favsport")){
                       student.setFavsport(propertyList[i+1].substring(1,propertyList[i+1].length()-1));
                   }
                   if(propertyList[i].contains("favunit")){
                       student.setFavunit(propertyList[i+1].substring(1,propertyList[i+1].length()-1));
                   }
                   if(propertyList[i].contains("firstname")){
                       student.setFirstname(propertyList[i+1].substring(1,propertyList[i+1].length()-1));
                   }
                   if(propertyList[i].contains("gender")){
                       student.setGender(propertyList[i+1].substring(1,propertyList[i+1].length()-1).charAt(0));
                   }
                   if(propertyList[i].contains("monemail")){
                       student.setMonemail(propertyList[i+1].substring(1,propertyList[i+1].length()-1));
                   }
                   if(propertyList[i].contains("nationality")){
                       student.setNationality(propertyList[i+1].substring(1,propertyList[i+1].length()-1));
                   }
                   if(propertyList[i].contains("navlanguage")){
                       student.setNavlanguage(propertyList[i+1].substring(1,propertyList[i+1].length()-1));
                   }
                   if(propertyList[i].contains("studentid")){
                       student.setStudentid(Integer.valueOf(propertyList[i+1]));
                   }
                   if(propertyList[i].contains("studymode")){
                       student.setStudymode(propertyList[i+1].substring(1,propertyList[i+1].length()-1).charAt(0));
                   }
                   if(propertyList[i].contains("surburb")){
                       student.setSurburb(propertyList[i+1].substring(1,propertyList[i+1].length()-1));
                   }
                   if(propertyList[i].contains("surname")){
                       student.setSurname(propertyList[i+1].substring(1,propertyList[i+1].length()-1));
                   }
                   if(propertyList[i].contains("subdatetime")){
                       Date subdatetime = null;
                       try {
                           subdatetime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(propertyList[i+1].substring(1,10)+" "+propertyList[i+1].substring(12,19));
                           student.setSubdatetime(subdatetime);
                       } catch (ParseException e) {
                           e.printStackTrace();
                       }

                   }


               }
               Log.i("Myapp",resultAfterFormate);
               Log.i("Myapp",student.getAddress());


               SharedPreferences.Editor editor = mPerference.edit();
               Gson gson = new Gson();
               String json = gson.toJson(student);
               editor.putBoolean("isLogin",true);
               editor.putString("currStudent",json);
               editor.commit();

                 Intent newIntent = new Intent(MainActivity.this,MainInterfaceActivity.class);
                 newIntent.putExtra("Student",student);
                startActivity(newIntent);
               finish();

           }

        }
    }
}





