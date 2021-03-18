package com.example.ghurefiribangladesh;

public class BookingView {

    private String Place_Name , Hotel_Name , Hotel_Price , Transportation_Name , Transportation_Company , Transportation_Type , Ticket_Price , Journey_Date , Pessenger ,Time_Slot , Total_Amount, Payment;


    public BookingView(){

    }

    public BookingView(String place_Name, String hotel_Name, String hotel_Price, String transportation_Name, String transportation_Company, String transportation_Type, String ticket_Price, String journey_Date, String pessenger, String time_Slot, String total_Amount, String payment) {
        Place_Name = place_Name;
        Hotel_Name = hotel_Name;
        Hotel_Price = hotel_Price;
        Transportation_Name = transportation_Name;
        Transportation_Company = transportation_Company;
        Transportation_Type = transportation_Type;
        Ticket_Price = ticket_Price;
        Journey_Date = journey_Date;
        Pessenger = pessenger;
        Time_Slot = time_Slot;
        Total_Amount = total_Amount;
        Payment = payment;
    }

    public String getPlace_Name() {
        return Place_Name;
    }

    public void setPlace_Name(String place_Name) {
        Place_Name = place_Name;
    }

    public String getHotel_Name() {
        return Hotel_Name;
    }

    public void setHotel_Name(String hotel_Name) {
        Hotel_Name = hotel_Name;
    }

    public String getHotel_Price() {
        return Hotel_Price;
    }

    public void setHotel_Price(String hotel_Price) {
        Hotel_Price = hotel_Price;
    }

    public String getTransportation_Name() {
        return Transportation_Name;
    }

    public void setTransportation_Name(String transportation_Name) {
        Transportation_Name = transportation_Name;
    }

    public String getTransportation_Company() {
        return Transportation_Company;
    }

    public void setTransportation_Company(String transportation_Company) {
        Transportation_Company = transportation_Company;
    }

    public String getTransportation_Type() {
        return Transportation_Type;
    }

    public void setTransportation_Type(String transportation_Type) {
        Transportation_Type = transportation_Type;
    }

    public String getTicket_Price() {
        return Ticket_Price;
    }

    public void setTicket_Price(String ticket_Price) {
        Ticket_Price = ticket_Price;
    }

    public String getJourney_Date() {
        return Journey_Date;
    }

    public void setJourney_Date(String journey_Date) {
        Journey_Date = journey_Date;
    }

    public String getPessenger() {
        return Pessenger;
    }

    public void setPessenger(String pessenger) {
        Pessenger = pessenger;
    }

    public String getTime_Slot() {
        return Time_Slot;
    }

    public void setTime_Slot(String time_Slot) {
        Time_Slot = time_Slot;
    }

    public String getTotal_Amount() {
        return Total_Amount;
    }

    public void setTotal_Amount(String total_Amount) {
        Total_Amount = total_Amount;
    }

    public String getPayment() {
        return Payment;
    }

    public void setPayment(String payment) {
        Payment = payment;
    }
}
