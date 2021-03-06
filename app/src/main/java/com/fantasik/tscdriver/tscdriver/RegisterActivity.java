package com.fantasik.tscdriver.tscdriver;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TableLayout;

import com.fantasik.tscdriver.tscdriver.Agent.SPreferences;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.fantasik.tscdriver.tscdriver.Agent.AgentMnager.MY_PREFS_NAME;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnext;
    @BindView(R.id.imageButton)
    ImageButton imageButton;
    @BindView(R.id.imageButton2)
    ImageButton imageButton2;
    @BindView(R.id.imageButton3)
    ImageButton imageButton3;
    @BindView(R.id.tFname)
    EditText tFname;
    @BindView(R.id.tLname)
    EditText tLname;
    @BindView(R.id.tEmail)
    EditText tEmail;
    @BindView(R.id.tphone)
    EditText tphone;
    @BindView(R.id.tPass)
    EditText tPass;
    @BindView(R.id.butNext)
    Button butNext;
    @BindView(R.id.hs)
    TableLayout hs;
    @BindView(R.id.activity_register)
    RelativeLayout activityRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        SPreferences.ClearPreferences(this);
        
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnext = (Button) findViewById(R.id.butNext);
        btnext.setOnClickListener(this);
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
        if (v == btnext) {

            if (isValidText()) {


                SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();

                editor.putString("fname", tFname.getText().toString());
                editor.putString("lname", tLname.getText().toString());
                editor.putString("email", tEmail.getText().toString());
                editor.putString("phone", tphone.getText().toString());
                editor.putString("passw", tPass.getText().toString());
                editor.apply();
                Intent myIntent = new Intent(RegisterActivity.this, ProfilePictureActivity.class);
                this.startActivity(myIntent);
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
            }
        }
    }

    private boolean isValidText() {
        if(tFname.getText().toString().trim().equals("")) {
            tFname.setError("This shouldn't be empty");
            return  false;
        }
        if(tEmail.getText().toString().trim().equals("")) {
            tEmail.setError("This shouldn't be empty");
            return  false;
        }
        if(tPass.getText().toString().trim().equals("")) {
            tPass.setError("This shouldn't be empty");
            return  false;
        }
        if(tphone.getText().toString().trim().equals("")) {
            tphone.setError("This shouldn't be empty");
            return  false;
        }
        return true;
    }
}
