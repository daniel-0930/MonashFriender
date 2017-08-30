package com.example.zhangzeyao.monashfriender;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.Toast;

import com.example.zhangzeyao.monashfriender.models.Student;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Date;
import java.util.Locale;


public class ReportActivity extends AppCompatActivity implements View.OnClickListener{

    private TabHost tabHost;

    private String unitresult;
    private String locationresult;

    private EditText startDateBox;

    private EditText endDateBox;

    private Date startDate;

    private Date endDate;

    private Button showButton;

    private Student student;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);


        Intent intent = getIntent();
        student = intent.getParcelableExtra("Student");

        tabHost = (TabHost)findViewById(R.id.tabhost);
        tabHost.setup();

        TabHost.TabSpec spec = tabHost.newTabSpec("Tab 1");
        spec.setContent(R.id.tab1);
        spec.setIndicator("Unit Frequency");
        tabHost.addTab(spec);

        unitresult = "";
        locationresult = "";

        new AsyncTask<String,Void,String>(){

            @Override
            protected String doInBackground(String... params) {
                String result = RestClient.findAllUnit();
                Log.i("MyApp","1"+result);
                return result;

            }
            @Override
            protected void onPostExecute(String response){
                unitresult = response.substring(1,response.length()-1).replace("},","};");
                PieChart unitChart = (PieChart)findViewById(R.id.chartUnit);
                List<PieEntry> entries = new ArrayList<PieEntry>();
                ArrayList<String> labels = new ArrayList<String>();
                Log.i("ThisApp","1"+unitresult);
                String[] newResult = unitresult.split(";");
                Float allfrequency = 0f;

                for (int i=0;i<newResult.length;i++){
                    String resultFormat = newResult[i].replace(':',',');
                    String[] resultUpdate = resultFormat.split(",");
                    allfrequency = allfrequency+Float.valueOf(resultUpdate[1]);
                }

                for(int i=0;i<newResult.length;i++)
                {
                    String resultFormat = newResult[i].replace(':',',');
                    String[] resultUpdate = resultFormat.split(",");
                    entries.add(new PieEntry(Float.valueOf(resultUpdate[1])*100/allfrequency,resultUpdate[3].substring(1,resultUpdate[3].length()-2)));
                }


                PieDataSet dataSet = new PieDataSet(entries,"Frequency");
                dataSet.setValueTextSize(20);
                PieData data = new PieData(dataSet);
                unitChart.setData(data);

                IValueFormatter iValueFormatter = new IValueFormatter() {
                    @Override
                    public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                        return String.format("%.2f",value)+"%";
                    }
                };

                dataSet.setValueFormatter(iValueFormatter);
                dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                unitChart.invalidate();
//              unitChart.notifyDataSetChanged();



            }
        }.execute();

        spec = tabHost.newTabSpec("Tab 2");
        spec.setContent(R.id.tab2);
        spec.setIndicator("Location Frequency");
        tabHost.addTab(spec);

        startDateBox = (EditText)findViewById(R.id.startDateTextBox);
        endDateBox = (EditText)findViewById(R.id.endDateTextBox);
        showButton = (Button)findViewById(R.id.showButton);

        startDateBox.setFocusable(false);
        startDateBox.setClickable(true);
        startDateBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(ReportActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        int monthSet = month+1;

                        String dobString = year +"-" + monthSet + "-" + dayOfMonth;
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        startDateBox.setText(dobString);
                        Log.i("MyApp",dobString);
                        try {
                            startDate = dateFormat.parse(dobString);
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

        endDateBox.setFocusable(false);
        endDateBox.setClickable(true);
        endDateBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(ReportActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        int monthSet = month+1;

                        String dobString = year +"-" + monthSet + "-" + dayOfMonth;
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        endDateBox.setText(dobString);
                        Log.i("MyApp",dobString);
                        try {
                            endDate = dateFormat.parse(dobString);
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

        showButton.setOnClickListener(this);




    }

    @Override
    public void onClick(View v) {
        if(startDateBox.getText().toString().equals("") || endDateBox.getText().toString().equals("")){
            if(startDateBox.getText().toString().equals("")){
                startDateBox.setError("Start Date can not be blank");
            }
            if(endDateBox.getText().toString().equals("")){
                endDateBox.setError("End Date can not be blank");
            }

        } else if(endDate.before(startDate)){
            endDateBox.setError("End date can not be early than start date");

        } else{
            Date currDay = new Date();
            Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH)+1;
            int day = c.get(Calendar.DATE);
            String currdayTime = year+ "-" + month + "-" +day;
            SimpleDateFormat df= new SimpleDateFormat("yyyy-MM-dd");
            try {
                currDay= df.parse(currdayTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if(currDay.before(startDate)){
                startDateBox.setError("Start Date can not be early than NOW");
            } else{

                new AsyncTask<String,Void,String>(){

                    @Override
                    protected String doInBackground(String... params) {
                        String result = RestClient.findByStudentidAndTime(student.getStudentid(),startDate,endDate);
                        Log.i("MyApp","1"+result);
                        return result;

                    }
                    @Override
                    protected void onPostExecute(String response) {
                        if (response.equals("[]")) {
                            Toast.makeText(ReportActivity.this,"No Location is found", Toast.LENGTH_SHORT).show();
                        } else {
                            locationresult = response.substring(1, response.length() - 1).replace("},", "};");
                            BarChart locationChart = (BarChart) findViewById(R.id.chartLocation);
                            List<BarEntry> entries = new ArrayList<BarEntry>();
                            ArrayList<String> labels = new ArrayList<String>();
                            Log.i("ThisApp", "1" + locationresult);
                            String[] newResult = locationresult.split(";");
                            float[] xAxis = new float[newResult.length];
                            final String[] locations = new String[newResult.length];


                            for (int i = 0; i < newResult.length; i++) {
                                String resultFormat = newResult[i].replace(':', ',');
                                String[] resultUpdate = resultFormat.split(",");
                                String latitudeString = resultUpdate[3];
                                String longitudeString = resultUpdate[5].substring(0, resultUpdate[5].length() - 1);
                                Float frequency = Float.valueOf(resultUpdate[1]);


                                Geocoder geocoder;
                                List<Address> addresses = new ArrayList<Address>();
                                geocoder = new Geocoder(ReportActivity.this, Locale.getDefault());

                                try {
                                    addresses = geocoder.getFromLocation(Double.valueOf(latitudeString), Double.valueOf(longitudeString), 1);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                String address = addresses.get(0).getAddressLine(0);
                                String city = addresses.get(0).getLocality();
                                String state = addresses.get(0).getAdminArea();
                                String country = addresses.get(0).getCountryName();
                                String postalCode = addresses.get(0).getPostalCode();
                                String knownName = addresses.get(0).getFeatureName();
                                String permises = addresses.get(0).getPremises();
                                Log.i("Addresss", "1+" + address + " " + " " + city + " " + permises);
                                Log.i("Address", "1" + knownName);
                                entries.add(new BarEntry((float) i, frequency));
                                xAxis[i] = (float) i;
                                locations[i] = address;


                            }
//
                            IAxisValueFormatter formatter = new IAxisValueFormatter() {
                                @Override
                                public String getFormattedValue(float value, AxisBase axis) {
                                    return locations[(int) value];
                                }
                            };

                            BarDataSet dataSet = new BarDataSet(entries, "Location Frequency");
                            dataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
                            BarData barData = new BarData(dataSet);
                            locationChart.setData(barData);
                            XAxis xAxis1 = locationChart.getXAxis();
                            xAxis1.setDrawAxisLine(true);
                            xAxis1.setValueFormatter(formatter);
                            xAxis1.setGranularity(1f);
                            xAxis1.setPosition(XAxis.XAxisPosition.BOTTOM);
                            dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                            locationChart.invalidate();


                        }
                    }
                }.execute();










            }
        }
    }



}
