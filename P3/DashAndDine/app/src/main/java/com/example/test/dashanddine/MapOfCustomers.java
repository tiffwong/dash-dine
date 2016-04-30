package com.example.test.dashanddine;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import com.android.volley.Response;
import com.cloudmine.api.CMApiCredentials;
import com.cloudmine.api.CMObject;
import com.cloudmine.api.SearchQuery;
import com.cloudmine.api.db.LocallySavableCMObject;
import com.cloudmine.api.rest.response.CMObjectResponse;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.List;

public class MapOfCustomers extends FragmentActivity {

    // Find this in your developer console
    private static final String APP_ID = "5abdb5e381484079acda4940f4243bda";
    // Find this in your developer console
    private static final String API_KEY = "e4dfb14d1d044d18ae1bddc7a30e6ccd";
    private GoogleMap map;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_of_customers);
        try {
            if (map == null) {
                // Try to obtain the map from the SupportMapFragment.
                map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
                        .getMap();
            }
            // Check if we were successful in obtaining the map.
            if (map != null)
            {

                //Sets camera update to center of Drexel at a reasonable zoom
                LatLng centerDrexel = new LatLng(39.959429, -75.18922);
                CameraUpdate center =
                        CameraUpdateFactory.newLatLng(centerDrexel);
                CameraUpdate zoom = CameraUpdateFactory.zoomTo(13);
                map.moveCamera(center);
                map.animateCamera(zoom);
                CMApiCredentials.initialize(APP_ID, API_KEY, getApplicationContext());
                Log.d("MapOfCustomers","Inside activity");
                setUpMap();
                markFoodCarts();
                markCustomers();

            }

        }
        catch(Exception e)
        {
           Log.e("MapOfCustomers", "Exception", e);
        }
    }
    public void setUpMap()
    {
        Log.d("MapOfCustomers","Inside setUpMap");
        //Sets up listener to info window that hides it when clicked
        map.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {

            @Override
            public void onInfoWindowClick(Marker marker) {

                marker.hideInfoWindow();
            }
        });
        //Customizes info window so all information can be seen
        map.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

            @Override
            public View getInfoWindow(Marker marker) {
                return null;
            }

            // Defines the contents of the InfoWindow
            @Override
            public View getInfoContents(Marker marker) {

                View v = getLayoutInflater().inflate(R.layout.info_window, null);

                String title = marker.getTitle();
                //Information is split by #
                String[] split=title.split("#");
                if(split.length==1)
                {
                    TextView tv = (TextView) v.findViewById(R.id.textView9);
                    tv.setText(split[0]);
                }
                else {
                    TextView tv = (TextView) v.findViewById(R.id.textView9);
                    tv.setText("From: " + split[0]);
                    tv = (TextView) v.findViewById(R.id.textView10);
                    tv.setText("To: " + split[1]);
                    tv = (TextView) v.findViewById(R.id.textView11);
                    String[] order=split[2].split(" ");
                    tv.setText("Order: " +order[0]);
                    tv = (TextView) v.findViewById(R.id.textView12);
                    tv.setText("Additional Notes: " + split[3]);
                }

                return v;

            }
        });

    }
    public void markFoodCarts()
    {
        Log.d("MapOfCustomers","Inside markFoodCarts");
        //Serches CloudMine for FoodCart Objects
        LocallySavableCMObject.searchObjects(this, SearchQuery.filter(FoodCart.class).searchQuery(),
                new Response.Listener<CMObjectResponse>() {
                    @Override
                    public void onResponse(CMObjectResponse response) {
                        //List of CloudMine Objects
                        List<CMObject> foodCarts = response.getObjects();

                        for (int x = 0; x < foodCarts.size(); x++) {
                            String text = foodCarts.get(x).toString();
                            String key = foodCarts.get(x).getObjectId();
                            JsonParser parser = new JsonParser();
                            //Get JsonObject associated with a foodCart object
                            JsonObject root = (JsonObject) parser.parse(text);
                            String foodCartName = root.get(key).getAsJsonObject().get("name").getAsString();
                            Double lat=root.get(key).getAsJsonObject().get("lat").getAsDouble();
                            Double lng=root.get(key).getAsJsonObject().get("lng").getAsDouble();
                            Log.d("DecisionActivity", foodCartName);
                            map.addMarker(new MarkerOptions().position(new LatLng(lat,lng))
                                    .title(foodCartName)
                                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));


                        }
                   }
                });
    }
    public void markCustomers()
    {
        Log.d("MapOfCustomers","Inside markCustomers");
        //Serches CloudMine for Customer Objects
        LocallySavableCMObject.searchObjects(this, SearchQuery.filter(Customer.class).searchQuery(),
                new Response.Listener<CMObjectResponse>() {
                    @Override
                    public void onResponse(CMObjectResponse response) {
                        Log.d("MapOfCustomers","Inside onResponse");
                        //Gets list of CloudMine objects
                        List<CMObject> customers = response.getObjects();
                        Log.d("MapOfCustomers","Number of Customers: "+customers.size());
                        for (int x = 0; x < customers.size(); x++) {

                            String text = customers.get(x).toString();
                            String key = customers.get(x).getObjectId();
                            JsonParser parser = new JsonParser();
                            //Gets JSON object associated with Customer object
                            JsonObject root = (JsonObject) parser.parse(text);
                            String address = root.get(key).getAsJsonObject().get("address").getAsString();
                            String foodCartName = root.get(key).getAsJsonObject().get("foodCartName").getAsString();
                            String order = root.get(key).getAsJsonObject().get("order").getAsString();
                            String addNotes = root.get(key).getAsJsonObject().get("additionalNotes").getAsString();
                            Double lat=root.get(key).getAsJsonObject().get("lat").getAsDouble();
                            Double lng=root.get(key).getAsJsonObject().get("lng").getAsDouble();
                            Log.d("DecisionActivity", foodCartName);
                            map.addMarker(new MarkerOptions().position(new LatLng(lat,lng))
                                    .title(foodCartName+"#"+address+"#"+order+"#"+addNotes)//Separated by # for convience of splitting later
                                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
                            Log.d("MapOfCustomers","Customer Order: "+order);

                        }
                    }
                });
    }


}
