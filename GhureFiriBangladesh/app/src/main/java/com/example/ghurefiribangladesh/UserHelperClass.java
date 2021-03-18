package com.example.ghurefiribangladesh;

public class UserHelperClass {
    String email,uid,Username;
    //for getting user login information



    public UserHelperClass() {

    }

    public UserHelperClass(String email,String uid,String Username) {
        this.email = email;
        this.uid=uid ;
        this.Username=Username ;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() { return Username; }

    public void setUsername(String username) { Username = username; }
}

