package com.example.zhangzeyao.monashfriender;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.zhangzeyao.monashfriender.models.Student;
import android.text.method.LinkMovementMethod;

public class CreateUserActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText studIdBox;
    private EditText monEmailBox;
    private EditText passwordBox;
    private EditText confirmBox;
    private Button nextStepButton;

    private String studId;
    private String monEmail;
    private String password;
    private String confirmpwd;

    private Student student;

    public static Activity c1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);

        c1 = this;
        studIdBox = (EditText)findViewById(R.id.studIdBox);
        monEmailBox = (EditText)findViewById(R.id.monEmailBox);
        passwordBox = (EditText)findViewById(R.id.passwordBox);
        confirmBox = (EditText)findViewById(R.id.confirmBox);
        nextStepButton = (Button)findViewById(R.id.nextStepButton);

        studId = "";
        monEmail = "";
        password = "";
        confirmpwd = "";
        student = new Student();

        nextStepButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        studId = studIdBox.getText().toString().trim();
        monEmail = monEmailBox.getText().toString().trim();
        password = passwordBox.getText().toString();
        confirmpwd = confirmBox.getText().toString();
        if(studId.equals("") || monEmail.equals("") || password.equals("") || confirmpwd.equals("")) {
            if(studId.equals("")){
                studIdBox.setError("Student ID is not filled in.");
            }
            if(monEmail.equals("") ){
                monEmailBox.setError("Email address is not filled in.");
            }
            if(password.equals("")){
                passwordBox.setError("Password is not filled in.");
            }
            if(confirmpwd.equals("")){
                confirmBox.setError("Password Confirmation is not filled in.");
            }
        }
        else{
            RegisterCheck l = new RegisterCheck();
            l.execute(new String[]{studId, monEmail});
        }


    }




    private class RegisterCheck extends AsyncTask<String, Void, String[]> {

        @Override
        protected String[] doInBackground(String... params) {

            RestClient rstClient = new RestClient();
            String emailResult = rstClient.findByMonEmail(monEmail);
            String studidResult = rstClient.findByStudID(studId);;
            String[] result = new String[]{studidResult,emailResult};
            Log.i("Myapp",emailResult);
            Log.i("Myapp",studidResult);
            return result;
        }

        @Override
        protected void onPostExecute(String[] result){


            if(result[0].equals("[]") && result[1].equals("[]")){

                if(password.equals(confirmpwd) && emailCheck(monEmail)){
                    Integer studentIDInteger = new Integer(studId);
                    student.setStudentid(studentIDInteger);
                    student.setMonemail(monEmail);
                    student.setPassword(student.md5(password));

                    Intent newIntentNextStep = new Intent(CreateUserActivity.this,CreateUserStep2.class);
                    newIntentNextStep.putExtra("newStudent", student);
                    startActivity(newIntentNextStep);
                } else{

                    if(!password.equals(confirmpwd)){
                        confirmBox.setError("Password is not confirmed. Please check.");
                    }

                    if(!emailCheck(monEmail)){
                        monEmailBox.setError("Email address is not valid.");
                    }
                }
            }
            else {
                if(!result[0].equals("[]")) {
                    studIdBox.setError("Student ID exists. Please login");
                }
                if(!result[1].equals("[]")) {
                    monEmailBox.setError("Email Address exists. Please login.");
                }
            }


        }
    }

    public Boolean emailCheck(String inputEmail){
            boolean hasAt = false;
            boolean hasDot = false;
            String address = "";
            for(int i=0;i<inputEmail.length() && !hasAt;i++)
            {
                if(inputEmail.charAt(i) == '@')
                {
                    hasAt = true;
                    address = inputEmail.substring(i);
                }

        }

        if(hasAt){
            for(int i=0; i<address.length() && !hasDot;i++){

                if(address.charAt(i) == '.'){
                    hasDot = true;
                }
            }
            if(hasDot) {
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }


    }
}
