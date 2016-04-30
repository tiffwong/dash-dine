package com.example.test.dashanddine;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Response;
import com.cloudmine.api.CMApiCredentials;
import com.cloudmine.api.CMObject;
import com.cloudmine.api.SearchQuery;
import com.cloudmine.api.db.LocallySavableCMObject;
import com.cloudmine.api.rest.response.CMObjectResponse;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/*
    For customers to place their order
 */
public class DecisionActivity extends ActionBarActivity {
    // Find this in your developer console
    private static final String APP_ID = "5abdb5e381484079acda4940f4243bda";
    // Find this in your developer console
    private static final String API_KEY = "e4dfb14d1d044d18ae1bddc7a30e6ccd";
    private static Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decision);
        DecisionActivity.context = getApplicationContext();
        showFoodCartInfo();
        //Sets up submit button listener
        Button b=(Button) findViewById(R.id.button3);
        b.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                EditText addNotes=(EditText)findViewById(R.id.editText);
                EditText address=(EditText)findViewById(R.id.editText2);
                Spinner name=(Spinner)findViewById(R.id.spinner);
                Spinner order=(Spinner)findViewById(R.id.spinner2);
                if(!address.getText().equals("")) {
                    AddressToLatLng atl = new AddressToLatLng(context, name.getSelectedItem().toString(), order.getSelectedItem().toString(), addNotes.getText().toString(), address.getText().toString());
                    atl.execute();

                    Intent i = new Intent(DecisionActivity.this, MapOfCustomers.class);

                    startActivity(i);

                }
            }
        });




    }
    public static Context getAppContext() {
        return DecisionActivity.context;
    }
    //Searches CloudMine for FoodCart objects and uses their information to populate spinners
    public void showFoodCartInfo()
    {
        CMApiCredentials.initialize(APP_ID, API_KEY, getApplicationContext());
        //Searches CloudMine for foodCart objects
        LocallySavableCMObject.searchObjects(this, SearchQuery.filter(FoodCart.class).searchQuery(),
                new Response.Listener<CMObjectResponse>() {
                    @Override
                    public void onResponse(CMObjectResponse response) {
                        //Gets List of FoodCarts
                        List<CMObject> foodCarts=response.getObjects();
                        ArrayList<String> list = new ArrayList<String>();
                        Spinner spinner = (Spinner) findViewById(R.id.spinner);
                        for(int x=0;x<foodCarts.size();x++) {
                            String text=foodCarts.get(x).toString();
                            String key=foodCarts.get(x).getObjectId();
                            JsonParser parser = new JsonParser();
                            JsonObject root = (JsonObject) parser.parse(text);
                            //Gets name of Food Cart
                            String foodCartName=root.get(key).getAsJsonObject().get("name").getAsString();

                            Log.d("DecisionActivity", foodCartName);
                            //Adds Food Cart to ArrayList
                            list.add(foodCartName);


                        }
                        //Creates an Adapter to attach to spinner that shows food cart names
                        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
                                android.R.layout.simple_spinner_item, list);
                        //Setting format of drop down list
                        adapter.setDropDownViewResource(R.layout.spinner_item);
                        //Set adapter to spinner
                        spinner.setAdapter(adapter);

                    }
                });
        //Sets on click Listener for menu button
        Button b=(Button) findViewById(R.id.button);
        b.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                Spinner spinner = (Spinner) findViewById(R.id.spinner);
                String name = spinner.getSelectedItem().toString();
                //Searches FoodCart objects on CloudMine with the name in the first spinner
                LocallySavableCMObject.searchObjects(context, SearchQuery.filter(FoodCart.class).and("name").equal(name).searchQuery(),
                        new Response.Listener<CMObjectResponse>() {
                            @Override
                            public void onResponse(CMObjectResponse response) {

                                List<CMObject> foodCart = response.getObjects();
                                ArrayList<String> list = new ArrayList<String>();
                                Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);

                                String text = foodCart.get(0).toString();
                                String key = foodCart.get(0).getObjectId();
                                JsonParser parser = new JsonParser();
                                JsonObject root = (JsonObject) parser.parse(text);
                                //Gets menu for food cart
                                JsonArray menu = root.get(key).getAsJsonObject().get("menu").getAsJsonArray();
                                for (int x = 0; x < menu.size(); x++) {
                                    String menuItem = menu.get(x).getAsString();
                                    Log.d("Decision menuItem", menuItem);
                                    //Adds menu item to ArrayList
                                    list.add(menuItem);
                                }
                                //Sets adapter up with menu items
                                final ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
                                        android.R.layout.simple_spinner_item, list);
                                //Setting format of drop down list
                                adapter.setDropDownViewResource(R.layout.spinner_item);
                                //Set adapter to menu spinner
                                spinner2.setAdapter(adapter);

                            }
                        });
            }
            });
        }
    }



