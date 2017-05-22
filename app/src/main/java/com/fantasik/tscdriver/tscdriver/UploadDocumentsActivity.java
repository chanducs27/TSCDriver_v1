package com.fantasik.tscdriver.tscdriver;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UploadDocumentsActivity extends AppCompatActivity {


    @BindView(R.id.microcard)
    CardView microcard;

    @BindView(R.id.microcard2)
    CardView microcard2;

    @BindView(R.id.microcard3)
    CardView microcard3;

    @BindView(R.id.microcard4)
    CardView microcard4;
    @BindView(R.id.imgcard1)
    ImageView imgcard1;
    @BindView(R.id.imgcard2)
    ImageView imgcard2;
    @BindView(R.id.imgcard3)
    ImageView imgcard3;
    @BindView(R.id.imgcard4)
    ImageView imgcard4;

    boolean isCard1Upload = false;
    boolean isCard2Upload = false;
    boolean isCard3Upload = false;
    boolean isCard4Upload = false;
    @BindView(R.id.butNext)
    Button butNext;

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

        return (super.onOptionsItemSelected(item));
    }

    @OnClick({R.id.microcard, R.id.microcard2, R.id.microcard3, R.id.microcard4, R.id.butNext})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.microcard:
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("file/*");
                startActivityForResult(intent, 67);
                if(!isCard1Upload)
                    imgcard1.setVisibility(View.VISIBLE);
                break;
            case R.id.microcard2:
                Intent intent2 = new Intent(Intent.ACTION_GET_CONTENT);
                intent2.setType("file/*");
                startActivityForResult(intent2, 67);
                if(!isCard2Upload)
                    imgcard2.setVisibility(View.VISIBLE);
                break;
            case R.id.microcard3:
                Intent intent3 = new Intent(Intent.ACTION_GET_CONTENT);
                intent3.setType("file/*");
                startActivityForResult(intent3, 67);
                if(!isCard1Upload)
                    imgcard3.setVisibility(View.VISIBLE);
                break;
            case R.id.microcard4:
                Intent intent4 = new Intent(Intent.ACTION_GET_CONTENT);
                intent4.setType("file/*");
                startActivityForResult(intent4, 67);
                if(!isCard4Upload)
                    imgcard4.setVisibility(View.VISIBLE);
                break;
            case R.id.butNext:
                Intent myIntent = new Intent(UploadDocumentsActivity.this, SelectVehicleActivity.class);
                this.startActivity(myIntent);
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                break;
        }
    }

    private String GetBase64datafromFile(Uri uri)
    {
        Uri data = uri;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        FileInputStream fis;
        try {
            fis = new FileInputStream(new File(data.getPath()));
            byte[] buf = new byte[1024];
            int n;
            while (-1 != (n = fis.read(buf)))
                baos.write(buf, 0, n);
        } catch (Exception e) {
            e.printStackTrace();
        }
        byte[] bbytes = baos.toByteArray();
        return Base64.encodeToString(bbytes, Base64.DEFAULT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            // The document selected by the user won't be returned in the intent.
            // Instead, a URI to that document will be contained in the return intent
            // provided to this method as a parameter.
            // Pull that URI using resultData.getData().
            Uri uri = null;
            if (data != null) {
                uri = data.getData();

            }
        }

    }

}
