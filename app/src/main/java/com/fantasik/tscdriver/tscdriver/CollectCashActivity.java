package com.fantasik.tscdriver.tscdriver;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CollectCashActivity extends AppCompatActivity {

    @BindView(R.id.txtFare)
    TextView txtFare;
    @BindView(R.id.txtDistance)
    TextView txtDistance;
    @BindView(R.id.txtFrom)
    TextView txtFrom;
    @BindView(R.id.txtToAddre)
    TextView txtToAddre;
    @BindView(R.id.txtTripFare)
    TextView txtTripFare;
    @BindView(R.id.txtTolls)
    TextView txtTolls;
    @BindView(R.id.txtRiderDisc)
    TextView txtRiderDisc;
    @BindView(R.id.txtoutstanding)
    TextView txtoutstanding;
    @BindView(R.id.txtTotal)
    TextView txtTotal;
    @BindView(R.id.butNext)
    Button butNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect_cash);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txtDistance.setText(getIntent().getStringExtra("distance") + " km");
        txtFare.setText(getIntent().getStringExtra("cost") + " $");
        txtFrom.setText(getIntent().getStringExtra("startaddr"));
        txtToAddre.setText(getIntent().getStringExtra("endaddr"));
        txtTripFare.setText(getIntent().getStringExtra("cost") + " $");
        txtTotal.setText(getIntent().getStringExtra("cost") + " $");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return (super.onOptionsItemSelected(item));
    }

    @OnClick(R.id.butNext)
    public void onViewClicked() {
        Intent intent = new Intent(CollectCashActivity.this, DriverMainActivity.class);

        startActivity(intent);
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }
}
