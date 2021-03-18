package com.example.ghurefiribangladesh;

public class PlaceView {

    private String place_name,place_img;

    public PlaceView(){


    }


    public PlaceView(String place_name, String place_img) {
        this.place_name = place_name;
        this.place_img = place_img;
    }


    public String getPlace_name() {
        return place_name;
    }

    public void setPlace_name(String place_name) {
        this.place_name = place_name;
    }

    public String getPlace_img() {
        return place_img;
    }

    public void setPlace_img(String place_img) {
        this.place_img = place_img;
    }
}
