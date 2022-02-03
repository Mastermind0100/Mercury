package com.a_team.mercury;

import android.graphics.Bitmap;
import android.os.StrictMode;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;


public class getResourceData {
    JSONObject responseJSON;

    public getResourceData(String url) throws IOException {
        String request_url = String.format("https://www.youtube.com/oembed?url=%s&format=json",url);
        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            responseJSON = getJsonFromUrl(request_url);
        }
    }

    public JSONObject getJsonFromUrl(String url_request) throws IOException {
        okHttpParser httpParser = new okHttpParser();
        String response = httpParser.run(url_request);
        Log.d("json_data", response);
        return  null;
    }
}
