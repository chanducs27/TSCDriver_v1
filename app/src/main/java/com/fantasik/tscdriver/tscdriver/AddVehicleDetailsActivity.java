package com.fantasik.tscdriver.tscdriver;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
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
    private int progressStatus = 0;
    private Handler handler = new Handler();
    Gson gson;
  String userid="-1";

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
            final ProgressDialog pd = new ProgressDialog(AddVehicleDetailsActivity.this);
            pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pd.setMessage("Loading.........");
            pd.setCancelable(false);
            pd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFD4D9D0")));
            pd.setIndeterminate(true);
            pd.show();



           RequestQueue requestQueue = Volley.newRequestQueue(this);
            String url = "http://10.0.2.2:8076/Service1.svc/Driver/Register";

            SharedPreferences editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);

           final JSONObject GH =new JSONObject();
            try {
                GH.put("fname",editor.getString("fname", ""));
                GH.put("lname",editor.getString("lname", ""));
                GH.put("email",editor.getString("email", ""));
                GH.put("phone",editor.getString("phone", ""));
                GH.put("pass",editor.getString("passw", ""));


            } catch (JSONException e) {
                e.printStackTrace();
            }
           GsonRequest<String> getRequest = new GsonRequest<String>(Request.Method.POST, url,String.class, null, new Response.Listener<String>() {
                @Override
                public void onResponse(String response)
                {
                    pd.dismiss();
                    if(response != "-1") {
                      userid = response;
                    }
                }
            }, new com.android.volley.Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error)
                {
                    pd.dismiss();
                }
            }, GH);

            getRequest.setShouldCache(false);
            requestQueue.add(getRequest);


        }
    }

    @OnClick(R.id.butNext)
    public void onViewClicked() {
    }
}
