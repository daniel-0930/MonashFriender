package com.example.zhangzeyao.monashfriender;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.widget.Spinner;

import com.example.zhangzeyao.monashfriender.models.SpinnerHelper;
import com.example.zhangzeyao.monashfriender.models.Student;


public class CreateUserStep2 extends AppCompatActivity implements View.OnClickListener{


    private EditText firstNameBox;
    private EditText surnameBox;
    private RadioButton femaleButton,maleButton,otherButton;
    private EditText addressBox;
    private EditText dobTextBox;
    private Spinner surburbSpinner;
    private Spinner nationalitySpinner;
    private Spinner natLanSpinner;
    private Button finalStepButton;

    private Student student;

    private Date dob;

    public static Activity c2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user_step2);

        c2 = this;


        firstNameBox = (EditText)findViewById(R.id.firstNameBox);
        surnameBox = (EditText)findViewById(R.id.surnameBox);
        femaleButton = (RadioButton)findViewById(R.id.femaleButton);
        maleButton = (RadioButton)findViewById(R.id.maleButton);
        otherButton = (RadioButton)findViewById(R.id.otherButton);
        addressBox = (EditText)findViewById(R.id.addressBox);
        surburbSpinner = (Spinner)findViewById(R.id.surburbSpinner);
        nationalitySpinner = (Spinner)findViewById(R.id.nationalitySpinner);
        loadDataToSpinner();
        natLanSpinner = (Spinner)findViewById(R.id.natLanSpinner);
        dobTextBox = (EditText)findViewById(R.id.dobTextBox);
        dob = new Date();

        dobTextBox.setFocusable(false);
        dobTextBox.setClickable(true);
        dobTextBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(CreateUserStep2.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        int monthSet = month+1;

                        String dobString = year +"-" + monthSet + "-" + dayOfMonth;
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        dobTextBox.setText(dobString);
                        Log.i("MyApp",dobString);
                        try {
                            dob = dateFormat.parse(dobString);
                            Log.i("MyApp","dob change success");
                        } catch (ParseException e) {
                            e.printStackTrace();
                            Log.i("MyApp",e.toString());
                        }
                    }

                }, 1990, 1, 1);

                datePickerDialog.show();
            }
        });


        Intent intent = getIntent();
        student = intent.getParcelableExtra("newStudent");

        Log.i("MyApp",student.getStudentid().toString());
        finalStepButton = (Button)findViewById(R.id.finalStepButton);

        finalStepButton.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        String firstName = firstNameBox.getText().toString();
        String surName = surnameBox.getText().toString();
        String address = addressBox.getText().toString();
        String dobString2 = dobTextBox.getText().toString();


        String gender = "";
        if(femaleButton.isChecked()){
            gender = "F";

        } else if(maleButton.isChecked()){
            gender = "M";

        } else {
            gender = "O";
        }

        String surburb = surburbSpinner.getSelectedItem().toString();
        String nationalityString = nationalitySpinner.getSelectedItem().toString();
        String nationality = nationalityChange(nationalityString);
        String nativeLanguage = natLanSpinner.getSelectedItem().toString();

        Boolean nextStepCheck = checkBlanks(firstName,surName,address,gender,dobString2);
        if(nextStepCheck) {


            student.setFirstname(firstName);
            student.setSurname(surName);
            student.setAddress(address);
            student.setSurburb(surburb);
            student.setGender(gender.charAt(0));
            student.setNationality(nationality);
            student.setNavlanguage(nativeLanguage);
            Date dobDate = new Date();

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Log.i("MyApp",dobString2);
            try {
                dobDate = dateFormat.parse(dobString2);
                student.setDob(dobDate);
                Log.i("MyApp","dob change success");
            } catch (ParseException e) {
                e.printStackTrace();
                Log.i("MyApp",e.toString());
            }


            Intent newIntentFinalStep = new Intent(CreateUserStep2.this, CreateUserStep3.class);
            newIntentFinalStep.putExtra("newStudentPart2",student);
            startActivity(newIntentFinalStep);
        }
    }

    public Boolean checkBlanks(String fname, String sname, String addr, String gend,String dateOfBirth){
        Boolean nextStepCheck = false;


        if(fname.trim().isEmpty() || sname.trim().isEmpty() || addr.trim().isEmpty() || gend.equals("") || dateOfBirth.equals("")){
            if(fname.trim().isEmpty()) {
                firstNameBox.setError("First Name is not filled in");
            }
            if(sname.trim().isEmpty()){
                surnameBox.setError("Surname is not filled in.");
            }
            if(addr.trim().isEmpty()){
                addressBox.setError("Address is not filled in");
            }
            if(gend.equals("")){
                femaleButton.setError("No gender is selected");
                maleButton.setError("No gender is selected");
                otherButton.setError("No gender is selected");
            }
            if(dateOfBirth.equals("")){
                dobTextBox.setError("Please select date of birth.");
            }
        } else {
            Date currDay = new Date();
            Date dobcheck = new Date();
            Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH)+1;
            int day = c.get(Calendar.DATE);
            String currdayTime = day + "/" + month + "/" +year;
           SimpleDateFormat df= new SimpleDateFormat("yyyy-MM-dd");
            try {
                currDay= df.parse(currdayTime);
                dobcheck = df.parse(dateOfBirth);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if(currDay.before(dobcheck)){
                dobTextBox.setError("Your Birthday is invaild");
                nextStepCheck = false;
            }else {
                nextStepCheck = true;
            }
        }
        return nextStepCheck;
    }

    public String nationalityChange(String input){
        String output = "";
        DatabaseHelper db = new DatabaseHelper(getApplicationContext());
        ArrayList<SpinnerHelper> sp = new ArrayList<>(db.GetAllCountry().values());
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
        if(db.GetAllCountry().size() == 0){
            db.CreateDefaultCountry();
        }

        ArrayList<SpinnerHelper> sp = new ArrayList<>(db.GetAllCountry().values());
        ArrayList<String> countries = new ArrayList<>();
        for(int i = 0;i< db.GetAllCountry().values().size();i++){
            countries.add(sp.get(i).getLongName());
        }


        ArrayAdapter<String> countryAdapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item, countries);
        countryAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        nationalitySpinner.setAdapter(countryAdapter);

    }


}
