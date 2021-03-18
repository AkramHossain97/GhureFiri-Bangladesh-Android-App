package com.example.ghurefiribangladesh;

public class PackageView {

    private String packname , packdesc , pack_img , packprice  ;



    public PackageView(){



    }


    public PackageView(String packname, String packdesc, String pack_img, String packprice) {
        this.packname = packname;
        this.packdesc = packdesc;
        this.pack_img = pack_img;
        this.packprice = packprice;
    }

    public String getPackname() {
        return packname;
    }

    public void setPackname(String packname) {
        this.packname = packname;
    }

    public String getPackdesc() {
        return packdesc;
    }

    public void setPackdesc(String packdesc) {
        this.packdesc = packdesc;
    }

    public String getPack_img() {
        return pack_img;
    }

    public void setPack_img(String pack_img) {
        this.pack_img = pack_img;
    }

    public String getPackprice() {
        return packprice;
    }

    public void setPackprice(String packprice) {
        this.packprice = packprice;
    }
}
