package com.a_team.mercury;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.util.Log;

import com.orhanobut.hawk.Hawk;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SplashScreen extends AppCompatActivity {

    String url_request = "https://mercury-list-api.herokuapp.com/api/v1/items";
    List<CardData> final_request_data = new ArrayList<CardData>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getSupportActionBar().hide();

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
            String type_id = temp_jsonObject.getString("user_id");

            //get the thumbnail url from youtube
            getResourceData resourceData = new getResourceData(url);
            String thumbnail_url = resourceData.responseJSON.getString("thumbnail_url");
            Log.d("thumb_splash", thumbnail_url);
            //append new object to cardData array
            CardData cardData = new CardData(id, title, url, thumbnail_url, type_id);
            final_request_data.add(cardData);
        }

        Hawk.put("all_data", final_request_data);
        Log.d("all_data_splash", final_request_data.get(0).toString());
    }
}