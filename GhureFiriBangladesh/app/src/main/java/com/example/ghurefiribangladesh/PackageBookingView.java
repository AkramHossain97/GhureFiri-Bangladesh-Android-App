package com.example.ghurefiribangladesh;

public class PackageBookingView {

    private String Package_Name,Package_Description,Hotel_name,Dining,Covering_Spots,Transportation,Journey_Date,Total_Amount;


    public PackageBookingView(){

    }

    public PackageBookingView(String package_Name, String package_Description, String hotel_name, String dining, String covering_Spots, String transportation, String journey_Date, String total_Amount) {
        Package_Name = package_Name;
        Package_Description = package_Description;
        Hotel_name = hotel_name;
        Dining = dining;
        Covering_Spots = covering_Spots;
        Transportation = transportation;
        Journey_Date = journey_Date;
        Total_Amount = total_Amount;
    }


    public String getPackage_Name() {
        return Package_Name;
    }

    public void setPackage_Name(String package_Name) {
        Package_Name = package_Name;
    }

    public String getPackage_Description() {
        return Package_Description;
    }

    public void setPackage_Description(String package_Description) {
        Package_Description = package_Description;
    }

    public String getHotel_name() {
        return Hotel_name;
    }

    public void setHotel_name(String hotel_name) {
        Hotel_name = hotel_name;
    }

    public String getDining() {
        return Dining;
    }

    public void setDining(String dining) {
        Dining = dining;
    }

    public String getCovering_Spots() {
        return Covering_Spots;
    }

    public void setCovering_Spots(String covering_Spots) {
        Covering_Spots = covering_Spots;
    }

    public String getTransportation() {
        return Transportation;
    }

    public void setTransportation(String transportation) {
        Transportation = transportation;
    }

    public String getJourney_Date() {
        return Journey_Date;
    }

    public void setJourney_Date(String journey_Date) {
        Journey_Date = journey_Date;
    }

    public String getTotal_Amount() {
        return Total_Amount;
    }

    public void setTotal_Amount(String total_Amount) {
        Total_Amount = total_Amount;
    }
}
