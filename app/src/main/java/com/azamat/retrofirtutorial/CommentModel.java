package com.azamat.retrofirtutorial;

import com.google.gson.annotations.SerializedName;

public class CommentModel {

    @SerializedName("postId")
    private int postId;

    @SerializedName("id")
    private Integer id;
    
    @SerializedName("name")
    private String name;

    @SerializedName("email")
    private String email;

    @SerializedName("body")
    private String text;

    public int getPostId() {
        return postId;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getText() {
        return text;
    }
}
