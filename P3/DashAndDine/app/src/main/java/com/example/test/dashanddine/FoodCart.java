package com.example.test.dashanddine;


import com.cloudmine.api.db.LocallySavableCMObject;
/*
    An object to represent a FoodCart, used to initialize CloudMine database
 */
public class FoodCart extends LocallySavableCMObject {
    public static final String CLASS_NAME = "FoodCart";
    private String name;
    private String[] menu;
    private double lat;
    private double lng;

    FoodCart(){}
    FoodCart(String _name,double _lat,double _lng)
    {
        name=_name;
        menu= new String[]{"beef $1.00", "chicken $2.00","steak $3.00"};
        lat=_lat;
        lng=_lng;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getMenu() {
        return menu;
    }

    public void setMenu(String[] menu) {
        this.menu = menu;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }
}
