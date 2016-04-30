package com.example.test.dashanddine;


import com.cloudmine.api.db.LocallySavableCMObject;
/*
    Customer object to be saved to CloudMine
 */
public class Customer extends LocallySavableCMObject {
    public static final String CLASS_NAME = "Customer";
    private String foodCartName;
    private String order;
    private String address;
    private String additionalNotes;
    private double lat;
    private double lng;

    Customer(){}
    Customer(String _name,String _order,String _addNotes, String _address,double _lat,double _lng)
    {
        foodCartName=_name;
        order=_order;
        additionalNotes=_addNotes;
        address=_address;
        lat=_lat;
        lng=_lng;
    }

    public String getFoodCartName() {
        return foodCartName;
    }

    public void setFoodCartName(String foodCartName) {
        this.foodCartName = foodCartName;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getAdditionalNotes() {
        return additionalNotes;
    }

    public void setAdditionalNotes(String additionalNotes) {
        this.additionalNotes = additionalNotes;
    }
}
