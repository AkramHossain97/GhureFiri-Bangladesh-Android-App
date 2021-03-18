package com.example.ghurefiribangladesh;

public class HotelView {

    private String hotel_name,hotel_img,room_price,hotel_descr;

    public HotelView(){

    }
    public HotelView(String hotel_name, String hotel_img, String room_price,String hotel_descr) {
        this.hotel_name = hotel_name;
        this.hotel_img = hotel_img;
        this.room_price = room_price;
        this.hotel_descr= hotel_descr;
    }


    public String getHotel_name() {
        return hotel_name;
    }

    public void setHotel_name(String hotel_name) {
        this.hotel_name = hotel_name;
    }

    public String getHotel_img() {
        return hotel_img;
    }

    public void setHotel_img(String hotel_img) {
        this.hotel_img = hotel_img;
    }

    public String getRoom_price() {
        return room_price;
    }

    public void setRoom_price(String room_price) {
        this.room_price = room_price;
    }

    public String getHotel_descr() {
        return hotel_descr;
    }

    public void setHotel_descr(String hotel_descr) {
        this.hotel_descr = hotel_descr;
    }
}
