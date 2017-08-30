package com.example.zhangzeyao.monashfriender;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhangzeyao.monashfriender.models.SpinnerHelper;
import com.example.zhangzeyao.monashfriender.models.Student;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class UpdateProfileActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView studIdText;
    private EditText studEmailBox,passwordBox,fnameBox,snameBox,dobBox,addressBox,ufavSportBox,ufavUnitBox,ufavMovieBox,uCurrJobBox;
    private Spinner usurburbSpinner, unationSpinner,unatLanSpinner,ucourseSpinner, ustudymodeSpinner;
    private Button updateButton;
    private RadioButton ufemaleButton,umaleButton,uOtherButton;

    private Student student;
    private Date dob;
    private String password;
    private String emailbox;
    private Boolean emailRepeat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        Intent receiveIntent = getIntent();
        student = receiveIntent.getParcelableExtra("Student");

        studIdText = (TextView) findViewById(R.id.uIDTextBox);
        studEmailBox = (EditText)findViewById(R.id.uemailBox);
        passwordBox = (EditText)findViewById(R.id.upwdBox);
        fnameBox = (EditText)findViewById(R.id.ufnameBox);
        snameBox = (EditText)findViewById(R.id.usnameBox);
        dobBox = (EditText)findViewById(R.id.udobBox);
        addressBox = (EditText)findViewById(R.id.uaddressBox);
        ufavSportBox = (EditText)findViewById(R.id.ufSportBox);
        ufavUnitBox = (EditText)findViewById(R.id.ufUnitBox);
        ufavMovieBox = (EditText)findViewById(R.id.ufMovieBox);
        uCurrJobBox = (EditText)findViewById(R.id.ucurJobBox);

        usurburbSpinner = (Spinner)findViewById(R.id.usurburbSpinner);
        unationSpinner = (Spinner)findViewById(R.id.unationSpinner);
        unatLanSpinner = (Spinner)findViewById(R.id.unatLavSpinner);
        ucourseSpinner = (Spinner)findViewById(R.id.ucourseSpinner);
        ustudymodeSpinner = (Spinner)findViewById(R.id.ustmodeSpinner);

        loadDataToSpinner();

        ufemaleButton = (RadioButton)findViewById(R.id.ufeButton);
        umaleButton = (RadioButton)findViewById(R.id.umalButton);
        uOtherButton = (RadioButton)findViewById(R.id.uotherButton);

        updateButton = (Button)findViewById(R.id.updateButton);

        updateButton.setOnClickListener(this);


        studIdText.setText(String.valueOf(student.getStudentid()));
        studEmailBox.setText(student.getMonemail());
        //password will pop up a dialog, confirm old password and set new password and confrimation

        passwordBox.setFocusable(false);
        passwordBox.setClickable(true);
        passwordBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialog= new Dialog(v.getContext());
                dialog.setContentView(R.layout.dialog_edittext);
                dialog.setTitle("Reset Password");
                Log.i("MyApp","123");
                final EditText oldpwdTextBox= (EditText)dialog.findViewById(R.id.oldpwdbox);
                Log.i("MyApp","123");
                final EditText newpwdTextBox= (EditText)dialog.findViewById(R.id.newpwdBox);
                final EditText conpwdTextBox= (EditText)dialog.findViewById(R.id.conpwdBox);
                final Button resetButton = (Button)dialog.findViewById(R.id.resetButton);
                final Button cancelButton = (Button)dialog.findViewById(R.id.cancelButton);

                final View.OnClickListener positiveListener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.i("Password2:",student.md5(oldpwdTextBox.getText().toString()));
                        Log.i("Password1:","1"+student.getPassword());
                        if(student.md5(oldpwdTextBox.getText().toString()).equals(student.getPassword())){
                            if(!newpwdTextBox.getText().toString().equals("")){
                                if(newpwdTextBox.getText().toString().equals(conpwdTextBox.getText().toString()))
                                {
                                    passwordBox.setText(newpwdTextBox.getText().toString());
                                    password = student.md5(newpwdTextBox.getText().toString());
                                    dialog.dismiss();
                                }else{
                                    conpwdTextBox.setError("New Password is not confirmed");
                                }
                            } else{
                                newpwdTextBox.setError("New Password can not be empty");
                            }
                        }else{
                            oldpwdTextBox.setError("Your old password is not correct");
                        }

                    }
                };
                dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(final DialogInterface dialog) {
                        resetButton.setOnClickListener(positiveListener);
                        cancelButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.cancel();
                            }
                        });
                    }
                });


                dialog.show();


            }
        });





        fnameBox.setText(student.getFirstname());
        snameBox.setText(student.getSurname());
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        dobBox.setText(df.format(student.getDob()));

        dob = new Date();

        dobBox.setFocusable(false);
        dobBox.setClickable(true);
        dobBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(UpdateProfileActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        int monthSet = month+1;

                        String dobString = year +"-" + monthSet + "-" + dayOfMonth;
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        dobBox.setText(dobString);
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





        addressBox.setText(student.getAddress());
        ufavSportBox.setText(student.getFavsport());
        ufavUnitBox.setText(student.getFavunit());
        ufavMovieBox.setText(student.getFavmovie());
        uCurrJobBox.setText(student.getCurrjob());

        usurburbSpinner.setSelection(getIndex(usurburbSpinner,student.getSurburb()));
        unatLanSpinner.setSelection(getIndex(unatLanSpinner,student.getNavlanguage()));


        if(student.getGender() == 'F'){
            ufemaleButton.setChecked(true);
        } else if(student.getGender() == 'M'){
            umaleButton.setChecked(true);
        } else{
            uOtherButton.setChecked(true);
        }

        emailbox = studEmailBox.getText().toString();
        emailRepeat = false;

    }

    @Override
    public void onClick(View v) {
        if(checkForChanges() && !checkForEmptyBlanks()){
            new AsyncTask<String, Void, String>() {
                @Override
                protected String doInBackground(String... params) {
                    RestClient rstClient = new RestClient();
                    String result = rstClient.findByMonEmail(emailbox);
                    Log.i("Myapp",result);
                    return result;
                }
                @Override
                protected void onPostExecute(String response){
                    if(response.equals("[]")){
                        Log.i("Myresult","no email");
                        emailRepeat = true;
                    }
                    else if(!response.equals("") && response.contains("\"studentid\":" + String.valueOf(student.getStudentid()))){
                        emailRepeat = true;
                    }
                    else{
                        emailRepeat = false;
                    }
                }
            }.execute();

            if(!emailRepeat){


            AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
            builder.setTitle("Update Your Account?");
            builder.setMessage("Are you sure you wish to update your account?");
            builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Log.i("MyApp","Try2");
                    student.setPassword(password);
                    student.setSurname(snameBox.getText().toString().trim());
                    student.setFirstname(fnameBox.getText().toString().trim());
                    student.setMonemail(studEmailBox.getText().toString().trim());

                    student.setAddress(addressBox.getText().toString().trim());
                    student.setFavsport(ufavSportBox.getText().toString().trim());
                    student.setFavunit(ufavUnitBox.getText().toString().trim());
                    student.setFavmovie(ufavMovieBox.getText().toString().trim());
                    student.setCurrjob(uCurrJobBox.getText().toString().trim());
                    student.setSurburb(usurburbSpinner.getSelectedItem().toString());
                    student.setNavlanguage(unatLanSpinner.getSelectedItem().toString());
                    student.setNationality(nationalityChange(unationSpinner.getSelectedItem().toString()));
                    student.setCourse(courseChange(ucourseSpinner.getSelectedItem().toString()));
                    student.setStudymode(studyModeChange(ustudymodeSpinner.getSelectedItem().toString()).charAt(0));
                    if(ufemaleButton.isChecked()){
                        student.setGender('F');
                    }else if(umaleButton.isChecked()){
                        student.setGender('M');
                    }else{
                        student.setGender('O');
                    }

                    Date dobDate = new Date();

                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    try {
                        dobDate = dateFormat.parse(dobBox.getText().toString());
                        student.setDob(dobDate);
                        Log.i("MyApp","dob change success");
                    } catch (ParseException e) {
                        e.printStackTrace();
                        Log.i("MyApp",e.toString());
                    }


                    new AsyncTask<String, Void, String>() {

                        @Override
                        protected String doInBackground(String... params) {

                            RestClient.updateStudent(student);
                            return "Update is successfully Done";
                        }
                        @Override
                        protected void onPostExecute(String response){
                            Toast.makeText(getBaseContext(), response, Toast.LENGTH_SHORT).show();
                        }
                    }.execute();

                    // Change activity to main interface
                    Intent newIntent = new Intent(UpdateProfileActivity.this,MainInterfaceActivity.class);
                    newIntent.putExtra("Student",student);
                    startActivity(newIntent);
                    finish();
                }
            });

            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            builder.create().show();

            }else
            {
                studEmailBox.setError("Email has been used.");
            }

        }else{
            if(!checkForChanges()){
                Toast.makeText(UpdateProfileActivity.this,"There is no changes", Toast.LENGTH_SHORT).show();
            }
        }

    }

    private int getIndex(Spinner spinner, String myString)
    {
        int index = 0;

        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equals(myString)){
                index = i;
                break;
            }
        }
        return index;
    }

    public void loadDataToSpinner(){
        DatabaseHelper db = new DatabaseHelper(getApplicationContext());
        if(db.GetAllCourse().size() == 0){
            db.CreateDefaultCourse();
        }
        if(db.GetAllStudyMode().size() == 0){
            db.CreateDefaultStudyMode();
        }

        if(db.GetAllCountry().size() == 0){
            db.CreateDefaultCountry();
        }
        int position=0;
        int position1=0;
        int position2=0;
        ArrayList<SpinnerHelper> sp = new ArrayList<>(db.GetAllCountry().values());
        ArrayList<SpinnerHelper> sp1 = new ArrayList<>(db.GetAllCourse().values());
        ArrayList<SpinnerHelper> sp2 = new ArrayList<>(db.GetAllStudyMode().values());
        ArrayList<String> countries = new ArrayList<>();
        ArrayList<String> courses = new ArrayList<>();
        ArrayList<String> studymodes = new ArrayList<>();
        for(int i = 0;i< db.GetAllCountry().values().size();i++){
            countries.add(sp.get(i).getLongName());
            if(sp.get(i).getShortName().equals(student.getNationality())){
                position = i;
            }
        }
        for(int i = 0;i< sp1.size();i++){
            courses.add(sp1.get(i).getLongName());
            if(sp1.get(i).getShortName().equals(student.getCourse())){
                position1 = i;
            }
        }

        for(int i = 0;i< sp2.size();i++){
            studymodes.add(sp2.get(i).getLongName());
            if(sp2.get(i).getShortName().equals(student.getStudymode())){
                position2 = i;
            }
        }


        ArrayAdapter<String> countryAdapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item, countries);
        countryAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        unationSpinner.setAdapter(countryAdapter);

        ArrayAdapter<String> courseAdapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item, courses);
        courseAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        ucourseSpinner.setAdapter(courseAdapter);

        ArrayAdapter<String> studyModeAdapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item, studymodes);
        studyModeAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        ustudymodeSpinner.setAdapter(studyModeAdapter);

        unationSpinner.setSelection(position);
        ucourseSpinner.setSelection(position1);
        ustudymodeSpinner.setSelection(position2);
    }

    public boolean checkForChanges(){
        Boolean change = false;
        char gender = '?';
        if(ufemaleButton.isChecked()){
            gender = 'F';
        }else if(umaleButton.isChecked()){
            gender = 'M';
        }else{
            gender = 'O';
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        if(student.getGender()==gender&&student.getFirstname().equals(fnameBox.getText().toString().trim())
                &&student.getSurname().equals(snameBox.getText().toString().trim())&&student.getMonemail().equals(studEmailBox.getText().toString().trim())
                &&student.getAddress().equals(addressBox.getText().toString().trim())&&student.getPassword().equals(student.md5(password))
                &&student.getFavmovie().equals(ufavMovieBox.getText().toString().trim())&&student.getFavsport().equals(ufavSportBox.getText().toString().trim())
                &&student.getFavunit().equals(ufavUnitBox.getText().toString().trim())&&student.getCurrjob().equals(uCurrJobBox.getText().toString().trim())
                &&df.format(student.getDob()).equals(dobBox.getText().toString().trim())&&student.getNationality().equals(nationalityChange(unationSpinner.getSelectedItem().toString()))
                &&student.getNavlanguage().equals(unatLanSpinner.getSelectedItem().toString())&&student.getSurburb().equals(usurburbSpinner.getSelectedItem().toString())
                &&student.getCourse().equals(courseChange(ucourseSpinner.getSelectedItem().toString()))&&student.getStudymode().equals(ustudymodeSpinner.getSelectedItem().toString())
                ){
            change =false;

        } else{
            change = true;
        }
        return change;
    }
    public boolean checkForEmptyBlanks(){
        Boolean empty = true;
        Boolean checked = false;
        if(ufemaleButton.isChecked() || umaleButton.isChecked() ||uOtherButton.isChecked()){
            checked = true;
        } else{
            ufemaleButton.setError("No gender is selected");
            umaleButton.setError("No gender is selected");
            uOtherButton.setError("No gender is selected");
        }

        if(studEmailBox.getText().toString().trim().equals("") ||
            passwordBox.getText().toString().trim().equals("") ||
        fnameBox.getText().toString().trim().equals("") ||
        snameBox.getText().toString().trim().equals("") ||
        dobBox.getText().toString().trim().equals("") ||
        addressBox.getText().toString().trim().equals("") ||
        ufavSportBox.getText().toString().trim().equals("") ||
        ufavUnitBox.getText().toString().trim().equals("") ||
        ufavMovieBox.getText().toString().trim().equals("") ||
        uCurrJobBox.getText().toString().trim().equals("") || !checked){
            empty = true;
            if(studEmailBox.getText().toString().trim().equals(""))
            {
                studEmailBox.setError("Email Box should not be empty");
            }
            if(passwordBox.getText().toString().trim().equals(""))
            {
                passwordBox.setError("Email Box should not be empty");
            }
            if(fnameBox.getText().toString().trim().equals(""))
            {
                fnameBox.setError("Email Box should not be empty");
            }
            if(snameBox.getText().toString().trim().equals(""))
            {
                snameBox.setError("Email Box should not be empty");
            }
            if(dobBox.getText().toString().trim().equals(""))
            {
                dobBox.setError("Email Box should not be empty");
            }
            if(addressBox.getText().toString().trim().equals(""))
            {
                addressBox.setError("Email Box should not be empty");
            }
            if(ufavSportBox.getText().toString().trim().equals(""))
            {
                ufavSportBox.setError("Email Box should not be empty");
            }
            if(ufavUnitBox.getText().toString().trim().equals(""))
            {
                ufavUnitBox.setError("Email Box should not be empty");
            }
            if(ufavMovieBox.getText().toString().trim().equals(""))
            {
                ufavMovieBox.setError("Email Box should not be empty");
            }
            if(uCurrJobBox.getText().toString().trim().equals(""))
            {
                uCurrJobBox.setError("Email Box should not be empty");
            }

        }else{
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
                dobcheck = df.parse(dobBox.getText().toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if(currDay.before(dobcheck)){
                dobBox.setError("Your Birthday is invaild");
                empty = true;
            }else {
                empty = false;
            }
        }




        return empty;
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
}
