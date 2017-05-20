package layout;


import android.Manifest;

import android.animation.ValueAnimator;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;


import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.fantasik.tscdriver.tscdriver.Agent.GsonRequest;
import com.fantasik.tscdriver.tscdriver.Agent.LatLngInterpolator;
import com.fantasik.tscdriver.tscdriver.Agent.PickupRequest;
import com.fantasik.tscdriver.tscdriver.Agent.UserDetails;
import com.fantasik.tscdriver.tscdriver.DriverMainActivity;
import com.fantasik.tscdriver.tscdriver.LoginActivity;
import com.fantasik.tscdriver.tscdriver.OnTripActivity;
import com.fantasik.tscdriver.tscdriver.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.SphericalUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;

import static android.content.Context.LOCATION_SERVICE;
import static android.content.Context.MODE_PRIVATE;
import static com.fantasik.tscdriver.tscdriver.Agent.AgentMnager.Base_URL;
import static com.fantasik.tscdriver.tscdriver.Agent.AgentMnager.MY_PREFS_NAME;

public class MapsFragment extends Fragment implements OnMapReadyCallback, LocationListener {

    Handler handlerDriverLocation;
    Handler handlerPickUpReuest;
    GoogleMap googleMap;
    String currentlat, currentlng, rideid;
    static List<String> ignorelist = new ArrayList<String>();
    boolean isConfirmShow = false;
    public MapsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_maps, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //setContentView(R.layout.content_user_map);
        SupportMapFragment fragment = ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map));
        fragment.getMapAsync(this);
        handlerDriverLocation = new Handler();
        handlerPickUpReuest = new Handler();
    }

    private final int TEN_SECONDS = 10000;
    public void scheduleUpdateDriverLocation() {
        handlerDriverLocation.postDelayed(new Runnable() {
            public void run() {
                UpdateDriverLocation();          // this method will contain your almost-finished HTTP calls
                handlerDriverLocation.postDelayed(this, TEN_SECONDS);
            }

        }, TEN_SECONDS);
    }

    private void UpdateDriverLocation() {
        //InsertDriverLocation
        if(currentlat != null && currentlng != null) {
            final SharedPreferences editorread = this.getActivity().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);

            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
            String url = Base_URL + "/InsertDriverLocation";
            final JSONObject GH = new JSONObject();
            try {
                GH.put("driverid", editorread.getString("driverid", ""));
                GH.put("lat", currentlat);
                GH.put("lng", currentlng);
                GH.put("rot", "0");
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
    }


    @Override
    public void onResume() {
        scheduleUpdateDriverLocation();
        scheduleCheckPickupRequest();
        super.onResume();
    }
    private final int FIVE_SECONDS = 5000;
    private void scheduleCheckPickupRequest() {
        handlerPickUpReuest.postDelayed(new Runnable() {
            public void run() {
                ChcekPickupRequest();          // this method will contain your almost-finished HTTP calls
                handlerPickUpReuest.postDelayed(this, FIVE_SECONDS);
            }

        }, FIVE_SECONDS);
    }

    private void ChcekPickupRequest() {
        final SharedPreferences editorread = this.getActivity().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        String url = Base_URL + "/GetPickupRequestforDriver";
        final JSONObject GH = new JSONObject();
        try {
            GH.put("driverid", editorread.getString("driverid", ""));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        GsonRequest<PickupRequest> getRequest = new GsonRequest<PickupRequest>(Request.Method.POST, url, PickupRequest.class, null, new Response.Listener<PickupRequest>() {
            @Override
            public void onResponse(PickupRequest response) {
                if (response != null && response.rideid != null && !ignorelist.contains(response.rideid))
                {if(!isConfirmShow) {
                    isConfirmShow = true;
                    final PickupRequest pd = response;
                    final Dialog dialog = new Dialog(getActivity());
                    dialog.setContentView(R.layout.pickup_request);

                    //  TextView text = (TextView) dialog.findViewById(R.id.text);
                    //  text.setText("Android custom dialog example!");

                    TextView txtAccept = (TextView) dialog.findViewById(R.id.txtAccept);
                    // if button is clicked, close the custom dialog
                    txtAccept.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            rideid = pd.rideid;
                            isConfirmShow = false;
                            dialog.dismiss();
                            AcceptPickupRequest(pd);

                        }
                    });


                    TextView txtReject = (TextView) dialog.findViewById(R.id.txtReject);
                    // if button is clicked, close the custom dialog
                    txtReject.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            isConfirmShow = false;
                            dialog.dismiss();
                            ignorelist.add(pd.rideid);
                        }
                    });

                    dialog.show();
                }
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }, GH);

        getRequest.setShouldCache(false);
        requestQueue.add(getRequest);
    }

    private void AcceptPickupRequest(PickupRequest pdfi) {

        final PickupRequest pd = pdfi;
        final SharedPreferences editorread = this.getActivity().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        String url = Base_URL + "/AcceptPickupRequest";
        final JSONObject GH = new JSONObject();
        try {
            GH.put("driverid", editorread.getString("driverid", ""));
            GH.put("rideid", rideid);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        GsonRequest<UserDetails> getRequest = new GsonRequest<UserDetails>(Request.Method.POST, url, UserDetails.class, null, new Response.Listener<UserDetails>() {
            @Override
            public void onResponse(UserDetails response) {
                if (response != null && response.name != null)
                {
                    Intent intent = new Intent(getActivity(), OnTripActivity.class);
                    intent.putExtra("uname", pd.udetails.name);
                    intent.putExtra("rideid", pd.rideid);

                    intent.putExtra("startlat", pd.startlat);
                    intent.putExtra("startlng", pd.startlng);
                    intent.putExtra("endlat", pd.endlat);
                    intent.putExtra("endlng", pd.endlng);
                    intent.putExtra("cost", pd.cost);
                    intent.putExtra("paymentmode", pd.paymentmode);
                    startActivity(intent);
                    getActivity().overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }, GH);

        getRequest.setShouldCache(false);
        requestQueue.add(getRequest);

    }

    @Override
    public void onPause() {
        handlerDriverLocation.removeCallbacksAndMessages(null);
        handlerPickUpReuest.removeCallbacksAndMessages(null);
        super.onPause();
    }

    @Override
    public void onStop() {
        handlerDriverLocation.removeCallbacksAndMessages(null);
        handlerPickUpReuest.removeCallbacksAndMessages(null);
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    Marker mr;
    LatLng oldloc = null, newloc = null;
    @Override
    public void onLocationChanged(Location location) {
        if (newloc != null) {
            oldloc = newloc;
        }
        newloc = new LatLng(location.getLatitude(), location.getLongitude());

        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        LatLng latLng = new LatLng(latitude, longitude);
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(15));
        currentlat = String.format("%.6f", newloc.latitude);
        currentlng = String.format("%.6f", newloc.longitude);

        if (mr == null) {
            int height = 100;
            int width = 100;
            BitmapDrawable bitmapdraw = (BitmapDrawable) ResourcesCompat.getDrawable(getResources(), R.drawable.car_top_view, null);
            if (bitmapdraw != null) {
                Bitmap b = bitmapdraw.getBitmap();
                Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);
                mr = googleMap.addMarker(new MarkerOptions()
                        .position(new LatLng(newloc.latitude, newloc.longitude))
                        .icon(BitmapDescriptorFactory.fromBitmap(smallMarker)));
            }
        } else {
            float rotation = (float) SphericalUtil.computeHeading(oldloc, newloc);
            rotateMarker(mr, newloc, rotation);
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

                    } catch (Exception e) {
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
    static AlertDialog alert;
    @Override
    public void onMapReady(GoogleMap gogleMap) {
        googleMap = gogleMap;

        if (ActivityCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getActivity(),
                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION},
                    200);
        }
        else
        {
        googleMap.setMyLocationEnabled(true);
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        googleMap.getUiSettings().setMyLocationButtonEnabled(true);

        LocationManager locationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String bestProvider = locationManager.getBestProvider(criteria, true);
        Location location = locationManager.getLastKnownLocation(bestProvider);
        if (location != null) {
            onLocationChanged(location);
        }
        locationManager.requestLocationUpdates(bestProvider, 5000, 0, this);

        if(!locationManager
                .isProviderEnabled(LocationManager.GPS_PROVIDER))
        {
            // show alert dialog if Internet is not connected
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

            builder.setMessage(
                    "Please activate location service GPS in location settings")
                    .setTitle("Alert")
                    .setCancelable(false)
                    .setPositiveButton("Settings",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Intent intent = new Intent(
                                            Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                    startActivity(intent);
                                    alert.dismiss();
                                }
                            })
                    .setNegativeButton("Cancel",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    alert.dismiss();
                                }
                            });
            alert = builder.create();
            alert.show();
        }

    }}


}

