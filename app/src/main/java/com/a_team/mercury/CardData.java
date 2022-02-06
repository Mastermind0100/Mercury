package com.a_team.mercury;

import android.graphics.Bitmap;

public class CardData {
    private String title;
    private String url;
    private String type_id;         //read as user_id from server


    public CardData(String title, String url, String type_id) {
        this.title = title;
        this.url = url;
        this.type_id = type_id;
    }

    public String getName() {
        return title;
    }

    public void setName(String name) {
        this.title = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType_id() {
        return type_id;
    }

    public void setType_id(String type_id) {
        this.type_id = type_id;
    }
}
