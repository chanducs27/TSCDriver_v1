package com.fantasik.tscdriver.tscdriver;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.fantasik.tscdriver.tscdriver.Agent.DriverDetails;
import com.fantasik.tscdriver.tscdriver.Agent.GsonRequest;
import com.fantasik.tscdriver.tscdriver.Agent.SPreferences;
import com.fantasik.tscdriver.tscdriver.Agent.UserDetails;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.fantasik.tscdriver.tscdriver.Agent.AgentMnager.Base_URL;
import static com.fantasik.tscdriver.tscdriver.Agent.AgentMnager.MY_PREFS_NAME;

public class LoginActivity extends AppCompatActivity {


    @BindView(R.id.glogin)
    ImageButton glogin;
    @BindView(R.id.flogin)
    ImageButton flogin;
    @BindView(R.id.txtusername)
    EditText txtusername;
    @BindView(R.id.tPass)
    EditText tPass;
    @BindView(R.id.butNext)
    Button butNext;
    @BindView(R.id.txtForgetPass)
    TextView txtForgetPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Login");
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        SPreferences.ClearPreferences(this);
    }

    @OnClick({R.id.glogin, R.id.flogin, R.id.butNext})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.glogin:
                break;
            case R.id.flogin:
                break;
            case R.id.butNext:
                final ProgressDialog pd = new ProgressDialog(LoginActivity.this);
                pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                pd.setMessage("Loading.........");
                pd.setCancelable(false);
                pd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFD4D9D0")));
                pd.setIndeterminate(true);
                pd.show();

                RequestQueue requestQueue = Volley.newRequestQueue(this);
                String url = Base_URL + "/driverlogin";
                final JSONObject GH =new JSONObject();
                try {
                    GH.put("username",txtusername.getText());
                    GH.put("pass",tPass.getText());

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
                            editor.putString("username", String.valueOf(txtusername.getText()));
                            editor.putString("pass", String.valueOf(tPass.getText()));

                            editor.apply();

                            Intent intent = new Intent(LoginActivity.this, DriverMainActivity.class);
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
                break;
        }
    }
}
