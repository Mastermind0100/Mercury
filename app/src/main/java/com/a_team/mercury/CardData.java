package com.a_team.mercury;

public class CardData {
    private String title;
    private String url;
    private String body;

    public CardData(String title, String url) {
        this.title = title;
        this.url = url;
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

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
