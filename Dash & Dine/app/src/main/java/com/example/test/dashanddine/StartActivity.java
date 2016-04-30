package com.example.test.dashanddine;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Response;
import com.cloudmine.api.CMApiCredentials;
import com.cloudmine.api.SimpleCMObject;
import com.cloudmine.api.db.LocallySavableCMObject;
import com.cloudmine.api.persistance.ClassNameRegistry;
import com.cloudmine.api.rest.callbacks.ObjectModificationResponseCallback;
import com.cloudmine.api.rest.response.ObjectModificationResponse;

import java.util.Arrays;

public class StartActivity extends ActionBarActivity
{
    // Find this in your developer console
    private static final String APP_ID = "5abdb5e381484079acda4940f4243bda";
    // Find this in your developer console
    private static final String API_KEY = "e4dfb14d1d044d18ae1bddc7a30e6ccd";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        //Was used to instaniate database, keep just in case of problems
        /*
        CMApiCredentials.initialize(APP_ID, API_KEY, getApplicationContext());
        FoodCart f=new FoodCart("Food Cart 1",39.955805,-75.191483);
        FoodCart f2=new FoodCart("Food Cart 2",39.955237,-75.187007);
        FoodCart f3=new FoodCart("Food Cart 3",39.952918,-75.192028);

        LocallySavableCMObject.saveObjects(StartActivity.this, Arrays.asList(f,f2,f3), new Response.Listener<ObjectModificationResponse>() {
            @Override
            public void onResponse(ObjectModificationResponse objectModificationResponse) {

            }
        });*/

        //Sets customer button listener
        Button b=(Button) findViewById(R.id.button);
        b.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v)
            {
                Button click = (Button)v;
                Intent i = new Intent(StartActivity.this, DecisionActivity.class);

                startActivity(i);
            }
        });
        //Sets deliverer button listener
        b=(Button) findViewById(R.id.button2);
        b.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v)
            {
                Button click = (Button)v;
                Intent i = new Intent(StartActivity.this, DeliverActivity.class);

                startActivity(i);
            }
        });
        //Sets up listener for customer map
        b=(Button) findViewById(R.id.button4);
        b.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v)
            {
                Button click = (Button)v;
                Intent i = new Intent(StartActivity.this, MapOfCustomers.class);

                startActivity(i);
            }
        });
        //Sets up listener for deliverer map
        b=(Button) findViewById(R.id.button5);
        b.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v)
            {
                Button click = (Button)v;
                Intent i = new Intent(StartActivity.this, MapOfDeliverers.class);

                startActivity(i);
            }
        });

    }
}
