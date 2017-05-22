package com.fantasik.tscdriver.tscdriver;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UploadDocumentsActivity extends AppCompatActivity {

    @BindView(R.id.img1)
    ImageView img1;
    @BindView(R.id.microcard)
    CardView microcard;
    @BindView(R.id.img2)
    ImageView img2;
    @BindView(R.id.microcard2)
    CardView microcard2;
    @BindView(R.id.img3)
    ImageView img3;
    @BindView(R.id.microcard3)
    CardView microcard3;
    @BindView(R.id.img4)
    ImageView img4;
    @BindView(R.id.microcard4)
    CardView microcard4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_documents);
        ButterKnife.bind(this);

        setTitle("Upload Documents");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


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

    @OnClick({R.id.microcard, R.id.microcard2, R.id.microcard3, R.id.microcard4})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.microcard:
                break;
            case R.id.microcard2:
                break;
            case R.id.microcard3:
                break;
            case R.id.microcard4:
                break;
        }
    }
}
