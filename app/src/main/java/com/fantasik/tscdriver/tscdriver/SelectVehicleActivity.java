package com.fantasik.tscdriver.tscdriver;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.transition.TransitionManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import static com.fantasik.tscdriver.tscdriver.Agent.AgentMnager.MY_PREFS_NAME;

public class SelectVehicleActivity extends AppCompatActivity implements View.OnClickListener {
    CardView huyt, minicardview;
    LinearLayout microdet, minidet;
    ImageView microok, miniok;
    Button btnnext;
    boolean showmicrodet = false, showminidet = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_vehicle);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        huyt = (CardView) findViewById(R.id.microcard);
        minicardview = (CardView) findViewById(R.id.minicard);

        btnnext = (Button) findViewById(R.id.butNext);

        microdet = (LinearLayout) findViewById(R.id.microdetail);
        minidet = (LinearLayout) findViewById(R.id.minidetail);

        microok = (ImageView) findViewById(R.id.imgmicrook);
        miniok = (ImageView) findViewById(R.id.imgminiok);

        huyt.setOnClickListener(this);
        minicardview.setOnClickListener(this);
        btnnext.setOnClickListener(this);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return(super.onOptionsItemSelected(item));
    }

    @Override
    public void onClick(View v) {
        if (v == huyt)
        {
            if(!showmicrodet) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    TransitionManager.beginDelayedTransition(huyt);
                }
                microdet.setVisibility(View.VISIBLE);
                microok.setVisibility(View.VISIBLE);
                showmicrodet = true;
            }
            else
            {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    TransitionManager.beginDelayedTransition(huyt);
                }
                microdet.setVisibility(View.GONE);
                microok.setVisibility(View.GONE);
                showmicrodet = false;
            }

        }

        if (v == minicardview)
        {
            if(!showminidet) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    TransitionManager.beginDelayedTransition(minicardview);
                }
                minidet.setVisibility(View.VISIBLE);
                miniok.setVisibility(View.VISIBLE);
                showminidet = true;
            }
            else
            {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    TransitionManager.beginDelayedTransition(minicardview);
                }
                minidet.setVisibility(View.GONE);
                miniok.setVisibility(View.GONE);
                showminidet = false;
            }

        }
        if (v == btnnext) {
            SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
            if (showmicrodet) {
                editor.putString("vehtype", "1");
            } else if (showminidet) {
                editor.putString("vehtype", "2");
            } else {
                editor.putString("vehtype", "3");
            }
            editor.commit();

            Intent intent = new Intent(SelectVehicleActivity.this, AddVehicleDetailsActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
        }
        return;
    }
}
