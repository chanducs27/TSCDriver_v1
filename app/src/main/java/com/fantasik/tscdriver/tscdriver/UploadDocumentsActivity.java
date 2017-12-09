package com.fantasik.tscdriver.tscdriver;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.fantasik.tscdriver.tscdriver.Agent.GsonRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import android.Manifest;
import static com.fantasik.tscdriver.tscdriver.Agent.AgentMnager.Base_URL;
import static com.fantasik.tscdriver.tscdriver.Agent.AgentMnager.MY_PREFS_NAME;

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
    private int RESULT;

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
    String[] mimetypes = {"application/vnd.openxmlformats-officedocument.wordprocessingml.document", "application/msword"};
    @OnClick({R.id.microcard, R.id.microcard2, R.id.microcard3, R.id.microcard4, R.id.butNext})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.microcard:
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("*/*");
                intent.putExtra(Intent.EXTRA_MIME_TYPES, mimetypes);
                startActivityForResult(intent, 1);

                if(!isCard1Upload)
                    imgcard1.setVisibility(View.VISIBLE);
                break;
            case R.id.microcard2:
                Intent intent2 = new Intent(Intent.ACTION_GET_CONTENT);
                intent2.addCategory(Intent.CATEGORY_OPENABLE);
                intent2.setType("*/*");

                intent2.putExtra(Intent.EXTRA_MIME_TYPES, mimetypes);
                startActivityForResult(intent2, 2);
                if(!isCard2Upload)
                    imgcard2.setVisibility(View.VISIBLE);
                break;
            case R.id.microcard3:
                Intent intent3 = new Intent(Intent.ACTION_GET_CONTENT);
                intent3.addCategory(Intent.CATEGORY_OPENABLE);
                intent3.setType("*/*");

                intent3.putExtra(Intent.EXTRA_MIME_TYPES, mimetypes);
                startActivityForResult(intent3, 3);
                if(!isCard3Upload)
                    imgcard3.setVisibility(View.VISIBLE);
                break;
            case R.id.microcard4:
                Intent intent4 = new Intent(Intent.ACTION_GET_CONTENT);
                intent4.addCategory(Intent.CATEGORY_OPENABLE);
                intent4.setType("*/*");

                intent4.putExtra(Intent.EXTRA_MIME_TYPES, mimetypes);
                startActivityForResult(intent4, 4);
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
    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Files.FileColumns.MIME_TYPE);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            // The document selected by the user won't be returned in the intent.
            // Instead, a URI to that document will be contained in the return intent
            // provided to this method as a parameter.
            // Pull that URI using resultData.getData().
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                    checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, RESULT);
            } else {

                String fext="";
                Uri uri = null;
                if (data != null) {
                    uri = data.getData();

                    String filestring="";
                    long fileSizeInBytes = 0;
                    String scheme = null;

                    if (uri != null) {
                        scheme = uri.getScheme();


                        if (scheme.equals(ContentResolver.SCHEME_CONTENT)) {
                            try {
                                InputStream fileInputStream = null;
                                try {
                                    fileInputStream = getApplicationContext().getContentResolver().openInputStream(uri);
                                    String mimeType = getContentResolver().getType(uri);
                                    fext = MimeTypeMap.getSingleton().getExtensionFromMimeType(mimeType);
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                }

                                if (fileInputStream != null) {
                                    try {
                                        fileSizeInBytes = fileInputStream.available();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }

                                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                    try {
                                        byte[] buf = new byte[1024];
                                        int n;
                                        while (-1 != (n = fileInputStream.read(buf)))
                                            baos.write(buf, 0, n);

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    byte[] bbytes = baos.toByteArray();
                                    filestring = Base64.encodeToString(bbytes, Base64.DEFAULT);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        } else if (scheme.equals(ContentResolver.SCHEME_FILE)) {
                            String path = uri.getPath();
                            fext = path.substring(path.lastIndexOf(".") + 1);
                            try {
                                File f;
                                f = new File(path);
                                filestring = GetBase64datafromFile(uri);
                                fileSizeInBytes = f.length();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        long fileSizeInKB = fileSizeInBytes / 1024;
                        long fileSizeInMB = fileSizeInKB / 1024;
                        if (fileSizeInMB > 2) {
                            Toast.makeText(UploadDocumentsActivity.this, "File size should not be greater than 2 MB.", Toast.LENGTH_LONG).show();
                        } else {
                            SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                            if (requestCode == 1) {
                                editor.putString("f1", filestring);
                                editor.putString("f1ext", fext);
                                editor.commit();
                            }
                            if (requestCode == 2) {
                                editor.putString("f2", filestring);
                                editor.putString("f2ext", fext);
                                editor.commit();
                            }
                            if (requestCode == 3) {
                                editor.putString("f3", filestring);
                                editor.putString("f3ext", fext);
                                editor.commit();
                            }
                            if (requestCode == 4) {
                                editor.putString("f4", filestring);
                                editor.putString("f4ext", fext);
                                editor.commit();
                            }
                        }

                    }
                }
            }
        }

    }

}
