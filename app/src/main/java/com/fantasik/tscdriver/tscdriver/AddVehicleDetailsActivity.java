package com.fantasik.tscdriver.tscdriver;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.fantasik.tscdriver.tscdriver.Agent.AgentMnager.MY_PREFS_NAME;

public class AddVehicleDetailsActivity extends AppCompatActivity {

    @BindView(R.id.tbrand)
    EditText tbrand;
    @BindView(R.id.tmodel)
    EditText tmodel;
    @BindView(R.id.syear)
    Spinner syear;
    @BindView(R.id.butNext)
    Button butNext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vehicle_details);
        ButterKnife.bind(this);

        SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
         editor.putString("vmodel", tmodel.getText().toString());
        editor.putString("vbrand", tbrand.getText().toString());
        editor.putString("vyear", syear.getSelectedItem().toString());
        editor.commit();

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }
}
