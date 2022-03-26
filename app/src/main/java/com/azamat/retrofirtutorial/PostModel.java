package com.azamat.retrofirtutorial;

import com.google.gson.annotations.SerializedName;

public class PostModel {

    @SerializedName("userId")
    private int userId;

    @SerializedName("id")
    private Integer id;
    
    @SerializedName("title")
    private String title;

    @SerializedName("body")
    private String body;

    public PostModel(int userId, String title, String body) {
        this.userId = userId;
        this.title = title;
        this.body = body;
    }

    public int getUserId() {
        return userId;
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }
}
