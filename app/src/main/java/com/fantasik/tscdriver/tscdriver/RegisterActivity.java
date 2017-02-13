package com.fantasik.tscdriver.tscdriver;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.TransitionManager;
import android.view.View;
import android.widget.Button;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnext = (Button) findViewById(R.id.butNext);
        btnext.setOnClickListener(this);
        btnext.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v == btnext)
        {
            Intent myIntent = new Intent(RegisterActivity.this, SelectVehicleActivity.class);
            myIntent.putExtra("key", "demo"); //Optional parameters
            this.startActivity(myIntent);
            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
        }

        return;
    }
}
