package com.fantasik.tscdriver.tscdriver;

import android.Manifest;
import android.animation.ValueAnimator;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.fantasik.tscdriver.tscdriver.Agent.DataParser;
import com.fantasik.tscdriver.tscdriver.Agent.GsonRequest;
import com.fantasik.tscdriver.tscdriver.Agent.LatLngInterpolator;
import com.fantasik.tscdriver.tscdriver.Agent.PickupRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.SphericalUtil;

import org.json.JSONException;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.fantasik.tscdriver.tscdriver.Agent.AgentMnager.Base_URL;
import static com.fantasik.tscdriver.tscdriver.Agent.AgentMnager.MY_PREFS_NAME;


public class OnTripActivity extends AppCompatActivity implements OnMapReadyCallback, LocationListener {
    GoogleMap googleMap;
    String uname, rideid, startlat, startlng;
    @BindView(R.id.txtPickupAddr)
    TextView txtPickupAddr;
    @BindView(R.id.imgPickupUser)
    ImageView imgPickupUser;
    @BindView(R.id.txtPickName)
    TextView txtPickName;
    @BindView(R.id.txtPickCost)
    TextView txtPickCost;
    @BindView(R.id.txtPresenttimme)
    TextView txtPresenttimme;
    @BindView(R.id.txtPickModeCash)
    TextView txtPickModeCash;
    @BindView(R.id.butStartTrip)
    Button butStartTrip;
    @BindView(R.id.relRideDetails)
    RelativeLayout relRideDetails;
    String currentlat, currentlng, endlat, endlng;
    Marker mr = null;
    Boolean isTripStarted = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_trip);
        ButterKnife.bind(this);

        SupportMapFragment fragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        fragment.getMapAsync(this);

        uname = getIntent().getStringExtra("uname");
        rideid = getIntent().getStringExtra("rideid");
        startlat = getIntent().getStringExtra("startlat");
        startlng = getIntent().getStringExtra("startlng");
        endlat = getIntent().getStringExtra("endlat");
        endlng = getIntent().getStringExtra("endlng");

        txtPickName.setText(uname);
    }

    LatLng oldloc, newloc;
    @Override
    public void onLocationChanged(Location location) {

        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        LatLng latLng = new LatLng(latitude, longitude);

        if(newloc != null) {
         oldloc = newloc;
        }

        newloc =latLng;
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(newloc));

        currentlat = String.format("%.6f", newloc.latitude);
        currentlng = String.format("%.6f", newloc.longitude);

        if (mr == null) {
            googleMap.animateCamera(CameraUpdateFactory.zoomTo(15));
            int height = 100;
            int width = 100;
            BitmapDrawable bitmapdraw = (BitmapDrawable) ResourcesCompat.getDrawable(getResources(), R.drawable.car_top_view, null);
            if (bitmapdraw != null) {
                Bitmap b = bitmapdraw.getBitmap();
                Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);
                mr = googleMap.addMarker(new MarkerOptions()
                        .position(new LatLng(Double.parseDouble(currentlat), Double.parseDouble(currentlng)))
                        .icon(BitmapDescriptorFactory.fromBitmap(smallMarker)));
            }
        } else {
            float rotation = (float) SphericalUtil.computeHeading(oldloc, newloc);
            rotateMarker(mr, newloc, rotation);

          //  mr.setPosition(new LatLng(Double.parseDouble(currentlat), Double.parseDouble(currentlng)));
        }
    }


    private void rotateMarker(final Marker marker, final LatLng destination, final float rotation) {

        if (marker != null) {

            final LatLng startPosition = marker.getPosition();
            final float startRotation = marker.getRotation();

            final LatLngInterpolator latLngInterpolator = new LatLngInterpolator.Spherical();
            ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);
            valueAnimator.setDuration(3000); // duration 3 second
            valueAnimator.setInterpolator(new LinearInterpolator());
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {

                    try {
                        float v = animation.getAnimatedFraction();
                        LatLng newPosition = latLngInterpolator.interpolate(v, startPosition, destination);
                        float bearing = computeRotation(v, startRotation, rotation);

                        marker.setRotation(bearing);
                        marker.setPosition(newPosition);

                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });
            valueAnimator.start();
        }
    }
    private static float computeRotation(float fraction, float start, float end) {
        float normalizeEnd = end - start; // rotate start to 0
        float normalizedEndAbs = (normalizeEnd + 360) % 360;

        float direction = (normalizedEndAbs > 180) ? -1 : 1; // -1 = anticlockwise, 1 = clockwise
        float rotation;
        if (direction > 0) {
            rotation = normalizedEndAbs;
        } else {
            rotation = normalizedEndAbs - 360;
        }

        float result = fraction * rotation + start;
        return (result + 360) % 360;
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    @Override
    public void onMapReady(GoogleMap gogleMap) {
        googleMap = gogleMap;

        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION},
                    200);
        } else {
            googleMap.setMyLocationEnabled(true);
            googleMap.getUiSettings().setZoomControlsEnabled(true);
            googleMap.getUiSettings().setMyLocationButtonEnabled(true);

            LocationManager locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
            Criteria criteria = new Criteria();
            String bestProvider = locationManager.getBestProvider(criteria, true);
            Location location = locationManager.getLastKnownLocation(bestProvider);
            if (location != null) {
                onLocationChanged(location);
            }
            locationManager.requestLocationUpdates(bestProvider, 20000, 0, this);
        }
    }

    @OnClick(R.id.butStartTrip)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.butStartTrip:
                if(!isTripStarted) {
                    String url = getUrl(new LatLng(Double.parseDouble(startlat), Double.parseDouble(startlng)), new LatLng(Double.parseDouble(endlat), Double.parseDouble(endlng)));
                    FetchUrl FetchUrl = new FetchUrl();
                    FetchUrl.execute(url);


                    googleMap.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(startlat), Double.parseDouble(startlng))));
                    googleMap.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(endlat), Double.parseDouble(endlng))));
                    //move map camera
                    googleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(Double.parseDouble(startlat), Double.parseDouble(startlng))));
                    googleMap.animateCamera(CameraUpdateFactory.zoomTo(15));

                    butStartTrip.setText("COMPLETED TRIP");
                    isTripStarted = true;
                    startRideconfirm();
                }
                else
                {
                    completedRideconfirm();
                }
                break;
        }
    }

    private void startRideconfirm()
    {
        final SharedPreferences editorread = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = Base_URL + "/StartRideCOnfirmFromDriver";
        final JSONObject GH = new JSONObject();
        try {
            GH.put("driverid", editorread.getString("driverid", ""));
            GH.put("rideid", rideid);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        GsonRequest<String> getRequest = new GsonRequest<String>(Request.Method.POST, url, String.class, null, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }, GH);

        getRequest.setShouldCache(false);
        requestQueue.add(getRequest);
    }

    private void completedRideconfirm()
    {
        final SharedPreferences editorread = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = Base_URL + "/CompletedRideCOnfirmFromDriver";
        final JSONObject GH = new JSONObject();
        try {
            GH.put("driverid", editorread.getString("driverid", ""));
            GH.put("rideid", rideid);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        GsonRequest<String> getRequest = new GsonRequest<String>(Request.Method.POST, url, String.class, null, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }, GH);

        getRequest.setShouldCache(false);
        requestQueue.add(getRequest);
    }

    private String getUrl(LatLng origin, LatLng dest) {

        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;

        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;


        // Sensor enabled
        String sensor = "sensor=false";

        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + sensor;

        // Output format
        String output = "json";

        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters;


        return url;
    }

    /**
     * A method to download json data from url
     */
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

            StringBuffer sb = new StringBuffer();

            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            data = sb.toString();
            Log.d("downloadUrl", data.toString());
            br.close();

        } catch (Exception e) {
            Log.d("Exception", e.toString());
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }

    // Fetches data from url passed
    private class FetchUrl extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... url) {

            // For storing data from web service
            String data = "";

            try {
                // Fetching the data from web service
                data = downloadUrl(url[0]);
                Log.d("Background Task data", data.toString());
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            ParserTask parserTask = new ParserTask();

            // Invokes the thread for parsing the JSON data
            parserTask.execute(result);

        }
    }

    /**
     * A class to parse the Google Places in JSON format
     */
    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {

        // Parsing the data in non-ui thread
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try {
                jObject = new JSONObject(jsonData[0]);
                Log.d("ParserTask", jsonData[0].toString());
                DataParser parser = new DataParser();
                Log.d("ParserTask", parser.toString());

                // Starts parsing data
                routes = parser.parse(jObject);
                Log.d("ParserTask", "Executing routes");
                Log.d("ParserTask", routes.toString());

            } catch (Exception e) {
                Log.d("ParserTask", e.toString());
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
            for (int i = 0; i < result.size(); i++) {
                points = new ArrayList<>();
                lineOptions = new PolylineOptions();

                // Fetching i-th route
                List<HashMap<String, String>> path = result.get(i);

                // Fetching all the points in i-th route
                for (int j = 0; j < path.size(); j++) {
                    HashMap<String, String> point = path.get(j);

                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);

                    points.add(position);
                }

                // Adding all the points in the route to LineOptions
                lineOptions.addAll(points);
                lineOptions.width(10);
                lineOptions.color(Color.RED);

                Log.d("onPostExecute", "onPostExecute lineoptions decoded");

            }

            // Drawing polyline in the Google Map for the i-th route
            if (lineOptions != null) {
                googleMap.addPolyline(lineOptions);
            } else {
                Log.d("onPostExecute", "without Polylines drawn");
            }
        }
    }
}
