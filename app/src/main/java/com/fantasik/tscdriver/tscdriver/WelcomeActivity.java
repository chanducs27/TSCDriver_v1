package com.fantasik.tscdriver.tscdriver;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.btnlogin)
    ImageButton btnlogin;
    @BindView(R.id.btnRegister)
    ImageButton btnRegister;
    private Animation animShow, animHide;
    ImageButton huyt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);



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
