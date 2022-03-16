package com.a_team.mercury;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.util.Log;
import android.widget.ImageView;

import com.orhanobut.hawk.Hawk;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class SplashScreen extends AppCompatActivity {

    String url_request = "Enter Your URL";
    List<CardData> final_request_data = new ArrayList<CardData>();
    ImageView imageView;
    String thumbnail_url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        if (getSupportActionBar() != null)
            getSupportActionBar().hide();

        imageView = findViewById(R.id.splash_image);
        try {
            InputStream bitmap=getAssets().open("Image path from assets folder");
            Bitmap bit= BitmapFactory.decodeStream(bitmap);
            imageView.setImageBitmap(bit);
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        Hawk.init(this).build();

        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    try {
                        getAllCardData();
                    } catch (IOException | JSONException e) {
                        e.printStackTrace();
                    }
                    startActivity(new Intent(SplashScreen.this, MainActivity.class));
                    SplashScreen.this.finish();
                }
            }, 1000);
        }
    }

    private void getAllCardData() throws IOException, JSONException {
        //Get data from server and save to JSONObject
        okHttpParser httpParser = new okHttpParser();
        String response = httpParser.run(url_request);
        JSONObject all_data_object = new JSONObject(response);

        //Get relevant data and save it in CardData array
        JSONArray jsonarray = all_data_object.getJSONArray("data");
        Log.d("splashresponse", jsonarray.toString());
        for(int i = 0; i < jsonarray.length(); i++) {
            JSONObject temp_jsonObject = jsonarray.getJSONObject(i);
            int id = temp_jsonObject.getInt("id");
            String title = temp_jsonObject.getString("title");
            String url = temp_jsonObject.getString("url");
            String type_id = temp_jsonObject.getString("value_type");
            int watched = temp_jsonObject.getInt("watched");

            //get the thumbnail url from youtube
            if(url.contains("open.spotify.com")){
                thumbnail_url = "spotify";
            }
            else{
                getResourceData resourceData = new getResourceData(url);
                thumbnail_url = resourceData.responseJSON.getString("thumbnail_url");
                Log.d("thumb_splash", thumbnail_url);
            }
            //append new object to cardData array
            CardData cardData = new CardData(id, title, url, thumbnail_url, type_id, watched);
            final_request_data.add(cardData);
        }

        Hawk.put("all_data", final_request_data);
        Log.d("all_data_splash", final_request_data.get(0).toString());
    }
}