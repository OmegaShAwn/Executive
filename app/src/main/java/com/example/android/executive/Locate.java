package com.example.android.executive;

import android.app.Activity;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Locate extends Activity implements OnMapReadyCallback {

    MapView mapView;
    GoogleMap googleMap;
    String username;
    int firstTime=0;
    String distance;
    public LocationDetails loc;
    TextView distanceTextView;
    ChildEventListener clistener = null;
    LatLng destination = new LatLng(10.0876,76.3882);
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Emergencies");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locate);

        Bundle extras = getIntent().getExtras();
        username= extras.getString("user");
        distanceTextView = (TextView)findViewById(R.id.duration);

        mapView = (MapView) findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);

        mapView.getMapAsync(this);

        clistener = myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                if(dataSnapshot!=null) {
                    Emergencies user = dataSnapshot.getValue(Emergencies.class);
                    if (user.emergencyDetails.getUsername().equals(username)) {
                        if (dataSnapshot.child("locationDetails") != null) {
                            loc = dataSnapshot.child("locationDetails").getValue(LocationDetails.class);
                            setMap();
                        } else
                            Toast.makeText(Locate.this, "Location not received yet", Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String prevChildKey) {
                Emergencies user = dataSnapshot.getValue(Emergencies.class);
                if (user.emergencyDetails.getUsername().equals(username)) {
                    if(dataSnapshot.child("locationDetails")!=null) {
                        loc = dataSnapshot.child("locationDetails").getValue(LocationDetails.class);
                        setMap();
                    }
                    else
                        Toast.makeText(Locate.this,"Current Location not received",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Emergencies user = dataSnapshot.getValue(Emergencies.class);
                if (user.emergencyDetails.getUsername().equals(username)) {
                    Toast.makeText(getApplicationContext(), "User logged out", Toast.LENGTH_SHORT).show();
                    myRef.removeEventListener(clistener);
                    finish();
                }
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String prevChildKey) {}

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }

    public void setMap(){
        if(googleMap!=null){
            MapsInitializer.initialize(Locate.this);
            googleMap.clear();

            LatLng coordinate;
            if(loc!=null) {
                coordinate = new LatLng(loc.getLatitude(), loc.getLongitude());
                googleMap.addMarker(new MarkerOptions().position(coordinate));

                if (firstTime == 0) {
                    CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(coordinate, 17.0f);
                    googleMap.moveCamera(cameraUpdate);
                    firstTime++;
                }

                String url = getDirectionsUrl(coordinate, destination);

                DownloadTask downloadTask = new DownloadTask();

                // Start downloading json data from Google Directions API
                downloadTask.execute(url);
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap map) {
        googleMap=map;
        Log.v("main","googlemap set");
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        map.getUiSettings().setZoomControlsEnabled(true);


    }
    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
    }
    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();


    }

    private String getDirectionsUrl(LatLng origin,LatLng dest){

        // Origin of route
        String str_origin = "origin="+origin.latitude+","+origin.longitude;

        // Destination of route
        String str_dest = "destination="+dest.latitude+","+dest.longitude;

        // Sensor enabled
        String sensor = "sensor=false";

        // Building the parameters to the web service
        String parameters = str_origin+"&"+str_dest+"&"+sensor;

        // Output format
        String output = "json";

        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/"+output+"?"+parameters;
        Log.v("url",url);
        return url;
    }
    /** A method to download json data from url */
    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);

            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();

            // Connecting to url
            urlConnection.connect();

            // Reading data from url
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuilder sb = new StringBuilder();

            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            data = sb.toString();

            br.close();

        } catch (IOException e) {
            Log.d("Exception", e.toString());
        } finally {
            assert iStream != null;
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }

    // Fetches data from url passed
    private class DownloadTask extends AsyncTask<String, Void, String> {

        // Downloading data in non-ui thread
        @Override
        protected String doInBackground(String... url) {

            // For storing data from web service
            String data = "";

            try{
                // Fetching the data from web service
                data = downloadUrl(url[0]);
            }catch(Exception e){
                Log.d("Background Task",e.toString());
            }
            return data;
        }

        // Executes in UI thread, after the execution of
        // doInBackground()
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            ParserTask parserTask = new ParserTask();

            // Invokes the thread for parsing the JSON data
            parserTask.execute(result);
        }
    }

    /** A class to parse the Google Places in JSON format */
    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String,String>>> >{

        // Parsing the data in non-ui thread
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try{
                jObject = new JSONObject(jsonData[0]);
                DirectionsJSONParser parser = new DirectionsJSONParser();
                String s =jObject.toString();
                String parts[] = s.split(",");
                String parts2[] = parts[13].split(":");
                distance = parts2[2];

                Log.v("distance",distance);


                // Starts parsing data
                routes = parser.parse(jObject);
            }catch(Exception e){
                e.printStackTrace();
            }
            return routes;
        }

        // Executes in UI thread, after the parsing process
        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            ArrayList<LatLng> points;
            PolylineOptions lineOptions = null;

            // Traversing through all the routes
            if(result!=null)
                for(int i=0;i<result.size();i++){
                    points = new ArrayList<>();
                    lineOptions = new PolylineOptions();

                    // Fetching i-th route
                    List<HashMap<String, String>> path = result.get(i);

                    // Fetching all the points in i-th route
                    for(int j=0;j<path.size();j++){
                        HashMap<String,String> point = path.get(j);

                        double lat = Double.parseDouble(point.get("lat"));
                        double lng = Double.parseDouble(point.get("lng"));
                        LatLng position = new LatLng(lat, lng);

                        points.add(position);
                    }

                    // Adding all the points in the route to LineOptions
                    lineOptions.addAll(points);
                    lineOptions.width(8);
                    lineOptions.color(Color.BLUE);
                }

            // Drawing polyline in the Google Map for the i-th route
            if(lineOptions!=null) {
                googleMap.addPolyline(lineOptions);
                String s = "Estimated Time Taken :" + distance.substring(1, distance.length() - 1);
                distanceTextView.setText(s);
            }
        }
    }
    @Override
    public void onBackPressed() {
        myRef.removeEventListener(clistener);
        finish();
        super.onBackPressed();
    }
}
