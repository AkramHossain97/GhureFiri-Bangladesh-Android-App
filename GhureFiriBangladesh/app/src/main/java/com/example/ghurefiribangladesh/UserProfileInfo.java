package com.example.ghurefiribangladesh;

public class UserProfileInfo {

String email,username,Image_Url;


    public UserProfileInfo(){

    }

    public UserProfileInfo(String email, String username, String image_Url) {
        this.email = email;
        this.username = username;
        Image_Url = image_Url;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getImage_Url() {
        return Image_Url;
    }

    public void setImage_Url(String image_Url) {
        Image_Url = image_Url;
    }
}
