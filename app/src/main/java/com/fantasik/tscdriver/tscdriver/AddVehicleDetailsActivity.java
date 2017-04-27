package com.fantasik.tscdriver.tscdriver;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fantasik.tscdriver.tscdriver.Agent.DriverDetails;
import com.fantasik.tscdriver.tscdriver.Agent.GsonPostRequest;
import com.fantasik.tscdriver.tscdriver.Agent.GsonRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.fantasik.tscdriver.tscdriver.Agent.AgentMnager.MY_PREFS_NAME;



public class AddVehicleDetailsActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.tbrand)
    EditText tbrand;
    @BindView(R.id.textView1)
    TextView textView1;
    @BindView(R.id.tmodel)
    EditText tmodel;
    @BindView(R.id.textView2)
    TextView textView2;
    @BindView(R.id.syear)
    Spinner syear;
    @BindView(R.id.butNext)
    Button butNext;
    @BindView(R.id.activity_add_vehicle_details)
    RelativeLayout activityAddVehicleDetails;
    private RequestQueue mRequestQueue;

    Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vehicle_details);
        ButterKnife.bind(this);


        //  Button btnext = (Button) findViewById(R.id.butNext);
        butNext.setOnClickListener(this);

        SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        editor.putString("vmodel", tmodel.getText().toString());
        editor.putString("vbrand", tbrand.getText().toString());
        editor.putString("vyear", syear.getSelectedItem().toString());
        editor.commit();


        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
        gson = gsonBuilder.create();
    }

    @Override
    public void onClick(View v) {
        if (v == butNext) {

            Intent intent = new Intent(AddVehicleDetailsActivity.this, DriverMainActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
           RequestQueue requestQueue = Volley.newRequestQueue(this);
            String url = "http://10.0.2.2:8076/Service1.svc/Driver/Register";

            SharedPreferences editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);

            editor.getString("fname", "");
            editor.getString("lname", "");
            editor.getString("email", "");
            editor.getString("phone", "");
            editor.getString("passw", "");

/*
           final JSONObject GH =new JSONObject();
            try {
                GH.put("fname",editor.getString("fname", ""));
                GH.put("lname",editor.getString("lname", ""));
                GH.put("email",editor.getString("email", ""));
                GH.put("phone",editor.getString("phone", ""));
                GH.put("passw",editor.getString("passw", ""));


            } catch (JSONException e) {
                e.printStackTrace();
            }
           GsonRequest<String> getRequest = new GsonRequest<String>(Request.Method.POST, url,String.class, null, new Response.Listener<String>() {
                @Override
                public void onResponse(String response)
                {

                }
            }, new com.android.volley.Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error)
                {
                }
            }, GH);*/


         /*   StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("Response", response);
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("Response", error.toString());
                }
            }){

                @Override
                protected Map<String, String> getParams()
                {
                    Map<String, String>  params = new HashMap<>();
                    params.put("dd","fdgv");
                    return params;
                }   @Override
                public String getBodyContentType() {
                    return "application/json";
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {

                    Map<String, String> param = new HashMap<String, String>();

                    return param;
                }

            };
*/

           // getRequest.setShouldCache(false);


          //  requestQueue.add(getRequest);
            //requestQueue.start();
        }
    }

    @OnClick(R.id.butNext)
    public void onViewClicked() {
    }
}
