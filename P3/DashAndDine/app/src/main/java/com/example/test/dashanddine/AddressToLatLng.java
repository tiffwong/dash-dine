package com.example.test.dashanddine;


import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.android.volley.Response;
import com.cloudmine.api.CMApiCredentials;
import com.cloudmine.api.db.LocallySavableCMObject;
import com.cloudmine.api.persistance.ClassNameRegistry;
import com.cloudmine.api.rest.response.ObjectModificationResponse;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
/*
    This class full instantiates the customer object and adds it to CloudMine, it also gets a latitude and longitude for the address entered
 */
public class AddressToLatLng extends AsyncTask<Void,Void,Void>
{
    // Find this in your developer console
    private static final String APP_ID = "5abdb5e381484079acda4940f4243bda";
    // Find this in your developer console
    private static final String API_KEY = "e4dfb14d1d044d18ae1bddc7a30e6ccd";
    private String foodCartName;
    private String order;
    private String additionalNotes;
    private String address;
    private double lat=0;
    private double lng=0;
    private Context context;
    AddressToLatLng(Context _context,String _foodCartName,String _order,String _addNotes,String _address)
    {
        foodCartName=_foodCartName;
        order=_order;
        //Sanitize user entries
        additionalNotes=_addNotes.replaceAll("[^\\w\\s]", "");
        address=_address.replaceAll("[^\\w\\s]", "");

        context=_context;
    }
    @Override
    protected Void doInBackground(Void... params) {
        try {


            //Get latitude and longitude of a customers address
            String sURL = "https://maps.googleapis.com/maps/api/geocode/json?address=" + address.replace(" ","%20") + "&key=AIzaSyCKVY-b1SlfA7-3bOKnVkW362eAOdOrPII";
            Log.d("HttpClass", sURL);
            URL url = new URL(sURL);
            HttpURLConnection request = (HttpURLConnection) url.openConnection();

            request.connect();
            Log.d("HttpClass", String.valueOf(request.getResponseCode()));

            JsonParser jp = new JsonParser();
            JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
            JsonObject rootobj = root.getAsJsonObject();
            //Get latitude and longitude from address
            lat = rootobj.get("results").getAsJsonArray().get(0).getAsJsonObject().get("geometry").getAsJsonObject().get("location").getAsJsonObject().get("lat").getAsDouble();
            lng = rootobj.get("results").getAsJsonArray().get(0).getAsJsonObject().get("geometry").getAsJsonObject().get("location").getAsJsonObject().get("lng").getAsDouble();

        }
        catch (Exception e)
        {
            e.printStackTrace();
            Log.e("AddressToLatLng", "Exception", e);
        }
        return null;
    }
    @Override
    protected void onPostExecute(Void result)
    {
        CMApiCredentials credentials=CMApiCredentials.initialize(APP_ID, API_KEY, context);
        ClassNameRegistry.register(Customer.CLASS_NAME, Customer.class);
        //Creates customer object
        Customer customer=new Customer(foodCartName,order,additionalNotes,address,lat,lng);
        //Saves customer object to CloudMine
        customer.save(context,credentials);


    }

}
