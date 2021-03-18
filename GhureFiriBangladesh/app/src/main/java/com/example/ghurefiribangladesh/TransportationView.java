package com.example.ghurefiribangladesh;

public class TransportationView {

    private String seats,trans,trans_img,trans_name,type,ticket_price,time_one,time_two,time_three;

    public TransportationView(){

    }

    public TransportationView(String seats, String trans, String trans_img, String trans_name, String type, String ticket_price,String time_one,String time_two,String time_three) {
        this.seats = seats;
        this.trans = trans;
        this.trans_img = trans_img;
        this.trans_name = trans_name;
        this.type = type;
        this.ticket_price= ticket_price;
        this.time_one = time_one;
        this.time_two = time_two;
        this.time_three = time_three;
    }

    public String getSeats() {
        return seats;
    }

    public void setSeats(String seats) {
        this.seats = seats;
    }

    public String getTrans() {
        return trans;
    }

    public void setTrans(String trans) {
        this.trans = trans;
    }

    public String getTrans_img() {
        return trans_img;
    }

    public void setTrans_img(String trans_img) {
        this.trans_img = trans_img;
    }

    public String getTrans_name() {
        return trans_name;
    }

    public void setTrans_name(String trans_name) {
        this.trans_name = trans_name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTicket_price() {
        return ticket_price;
    }

    public void setTicket_price(String ticket_price) {
        this.ticket_price = ticket_price;
    }

    public String getTime_one() {
        return time_one;
    }

    public void setTime_one(String time_one) {
        this.time_one = time_one;
    }

    public String getTime_two() {
        return time_two;
    }

    public void setTime_two(String time_two) {
        this.time_two = time_two;
    }

    public String getTime_three() {
        return time_three;
    }

    public void setTime_three(String time_three) {
        this.time_three = time_three;
    }
}
