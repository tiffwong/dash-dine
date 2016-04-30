package com.example.test.dashanddine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;


import com.example.test.dashanddine.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.*;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.LatLngBounds.*;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by Eric on 3/16/2015.
 */
public class MapOfDeliverers extends FragmentActivity {

    private GoogleMap mMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        Intent intent = getIntent();
        double lat = intent.getDoubleExtra("lat", 39.956613);
        double lon = intent.getDoubleExtra("lon", -75.1911027);

        mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();

        Marker mark = mMap.addMarker(new MarkerOptions().position(new LatLng(lat, lon)));

        LatLng x;
        final Builder bounds = new LatLngBounds.Builder();

        bounds.include(new LatLng(39.956701, -75.224019));
        bounds.include(new LatLng(39.958149, -75.173465));

        final LatLngBounds boundsCreate = bounds.build();
        mMap.setOnMapLoadedCallback(new OnMapLoadedCallback() {

            @Override
            public void onMapLoaded() {
                mMap.animateCamera(
                        CameraUpdateFactory.newLatLngBounds(boundsCreate, 100), 1500,
                        null);
            }
        });


        //

        //   mark.setPosition(new LatLng(37, 79));
    }
}