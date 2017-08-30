package com.example.zhangzeyao.monashfriender;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.codehaus.jettison.json.JSONObject;
import org.codehaus.jettison.json.JSONArray;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import java.io.InputStream;


public class MovieInfoActivity extends AppCompatActivity {
    private String movieName;

    private ImageView movieIcon;
    private TextView movieNameBox;
    private TextView descriptionBox;
    private RatingBar ratingBar;
    private TextView ratingText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_info);

        Intent intent = getIntent();
        movieName = intent.getStringExtra("Movie");

        movieIcon = (ImageView)findViewById(R.id.movieIcon);
        movieNameBox = (TextView)findViewById(R.id.movieNameText);
        descriptionBox = (TextView)findViewById(R.id.snippetText);
        ratingBar = (RatingBar)findViewById(R.id.ratingBar);
        ratingText = (TextView)findViewById(R.id.ratingText);


        new AsyncTask<String,Void,String>(){

            @Override
            protected String doInBackground(String... params) {

                String result = RestClient.searchGoogleAPI(movieName);


                return result;
            }

            @Override
            protected void onPostExecute(String response){
                String movieName1 = "";
                String movieSnippet ="";
                String movierating = "";
                String imageURL = "";


                try {
                    JSONObject json=new JSONObject(response);
                    JSONArray jsonarray = json.getJSONArray("items");
                    JSONObject json2 = jsonarray.getJSONObject(0).getJSONObject("pagemap");

                    JSONArray jsonarray3 = json2.getJSONArray("movie");
                    JSONArray jsonarray4 = json2.getJSONArray("aggregaterating");
                    if(jsonarray != null && jsonarray.length() > 0){
                        movieName1 = jsonarray3.getJSONObject(0).getString("name");
                        imageURL = jsonarray3.getJSONObject(0).getString("image");
                        movieSnippet = jsonarray3.getJSONObject(0).getString("description");
                        movierating = jsonarray4.getJSONObject(0).getString("ratingvalue");
                    }
                } catch(Exception e){
                    e.printStackTrace();
                    Log.i("ConvertJson",e.getMessage());
                }
                movieNameBox.setText(movieName1);
                descriptionBox.setText("Discription: "+ movieSnippet);
                new DownloadImageTask(movieIcon).execute(imageURL);
                ratingBar.setNumStars(5);
                ratingBar.setMax(5);
                ratingBar.setStepSize((float)0.5);
                ratingBar.setRating(Float.valueOf(movierating)/2);
                ratingText.setText("Rating: "+ movierating);


            }
        }.execute();
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}
