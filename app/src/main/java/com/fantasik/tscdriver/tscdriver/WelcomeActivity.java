package com.fantasik.tscdriver.tscdriver;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageButton;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.fantasik.tscdriver.tscdriver.Agent.DriverDetails;
import com.fantasik.tscdriver.tscdriver.Agent.GsonRequest;
import com.fantasik.tscdriver.tscdriver.Agent.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.fantasik.tscdriver.tscdriver.Agent.AgentMnager.Base_URL;
import static com.fantasik.tscdriver.tscdriver.Agent.AgentMnager.MY_PREFS_NAME;

public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.btnlogin)
    ImageButton btnlogin;
    @BindView(R.id.btnRegister)
    ImageButton btnRegister;
    private Animation animShow, animHide;
    ImageButton huyt;
    SessionManager session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);

        // Session class instance
        session = new SessionManager(getApplicationContext());

        if(session.isLoggedIn()) {
            RedirecttoMain_ifLogged();
        }

    }

    private void RedirecttoMain_ifLogged() {
        final ProgressDialog pd = new ProgressDialog(WelcomeActivity.this);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setMessage("Loading.........");
        pd.setCancelable(false);
        pd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFD4D9D0")));
        pd.setIndeterminate(true);
        pd.show();

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = Base_URL + "/GetDriverDetailsById";
        final JSONObject GH =new JSONObject();
        try {
            GH.put("driverid",session.getDriverDetails().driverid);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        GsonRequest<DriverDetails> getRequest = new GsonRequest<DriverDetails>(Request.Method.POST, url,DriverDetails.class, null, new Response.Listener<DriverDetails>() {
            @Override
            public void onResponse(DriverDetails response)
            {
                pd.dismiss();
                if(response != null) {
                    DriverDetails dd = response;

                    SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                    editor.putString("driverid", dd.driverid);
                    editor.putString("mobile", dd.mobile);
                    editor.putString("name", dd.name);
                    editor.putString("username", dd.username);

                    editor.apply();
                    session.createLoginSession(dd.driverid, dd.name,dd.username,dd.mobile,dd.pass,dd.imgdriver,dd.rate,dd.vehbrand,dd.vehcolor,dd.vehtypeid,dd.vehyear);


                    Intent intent = new Intent(WelcomeActivity.this, DriverMainActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pd.dismiss();
                SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                editor.clear();

                editor.apply();
            }
        }, GH);

        getRequest.setShouldCache(false);
        requestQueue.add(getRequest);
    }

    @OnClick({R.id.btnlogin, R.id.btnRegister})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnlogin:
                Intent myIntent = new Intent(WelcomeActivity.this, LoginActivity.class);
                this.startActivity(myIntent);
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                break;
            case R.id.btnRegister:
                Intent myIntent1 = new Intent(WelcomeActivity.this, RegisterActivity.class);
                this.startActivity(myIntent1);
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                break;
        }
    }

    @Override
    public void onClick(View view) {

    }
}
