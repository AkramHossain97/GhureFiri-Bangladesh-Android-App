package com.example.ghurefiribangladesh;

public class Bpost {

//for getting the post information
    private String postname , posttext , post_image , username  ;


    public Bpost(){


    }

    public Bpost(String postname, String posttext, String post_image) {
        this.postname = postname;
        this.posttext = posttext;
        this.post_image = post_image;
        this.username = username;

    }

    public String getPostname() {
        return postname;
    }

    public void setPostname(String postname) {
        this.postname = postname;
    }

    public String getPosttext() {
        return posttext;
    }

    public void setPosttext(String posttext) {
        this.posttext = posttext;
    }

    public String getPost_image() {
        return post_image;
    }

    public void setPost_image(String post_image) {
        this.post_image = post_image;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }



}
