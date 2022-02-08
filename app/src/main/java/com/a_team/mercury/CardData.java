package com.a_team.mercury;

import android.graphics.Bitmap;

public class CardData {
    private String title;
    private String thumbnail_url;
    private String main_url;
    private String type_id;         //read as user_id from server


    public CardData(String title, String main_url, String thumbnail_url, String type_id) {
        this.title = title;
        this.main_url = main_url;
        this.thumbnail_url = thumbnail_url;
        this.type_id = type_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumbnail_url() {
        return thumbnail_url;
    }

    public void setThumbnail_url(String thumbnail_url) {
        this.thumbnail_url = thumbnail_url;
    }

    public String getMain_url() {
        return main_url;
    }

    public void setMain_url(String main_url) {
        this.main_url = main_url;
    }

    public String getType_id() {
        return type_id;
    }

    public void setType_id(String type_id) {
        this.type_id = type_id;
    }
}
