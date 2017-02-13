package com.fantasik.tscdriver.tscdriver;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener {
    private Animation animShow, animHide;
    ImageButton huyt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

       // animShow = AnimationUtils.loadAnimation( this, R.anim.view_show);
       // animHide = AnimationUtils.loadAnimation( this, R.anim.view_hide);

      //  RelativeLayout huyt = (RelativeLayout) findViewById(R.id.reltemp);

       // huyt.setVisibility(huyt.VISIBLE);
      //  huyt.startAnimation( animShow );

         huyt = (ImageButton) findViewById(R.id.imageButton5);
        huyt.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        if (v == huyt)
        {
            Intent myIntent = new Intent(WelcomeActivity.this, RegisterActivity.class);
            myIntent.putExtra("key", "demo"); //Optional parameters
            this.startActivity(myIntent);
            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
        }

        return;
    }
}
