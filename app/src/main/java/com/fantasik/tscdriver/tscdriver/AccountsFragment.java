package com.fantasik.tscdriver.tscdriver;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.fantasik.tscdriver.tscdriver.Agent.DriverDetails;
import com.fantasik.tscdriver.tscdriver.Agent.GsonRequest;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.R.attr.data;
import static android.content.Context.MODE_PRIVATE;
import static com.fantasik.tscdriver.tscdriver.Agent.AgentMnager.Base_URL;
import static com.fantasik.tscdriver.tscdriver.Agent.AgentMnager.MY_PREFS_NAME;

public class AccountsFragment extends Fragment {


    @BindView(R.id.imgdriver)
    CircleImageView imgdriver;
    @BindView(R.id.txtDriverName)
    TextView txtDriverName;
    @BindView(R.id.txtEditProfile)
    TextView txtEditProfile;
    @BindView(R.id.lnrwaybill)
    LinearLayout lnrwaybill;
    @BindView(R.id.lnrsettings)
    LinearLayout lnrsettings;
    @BindView(R.id.lnrabout)
    LinearLayout lnrabout;
    @BindView(R.id.lnrhelp)
    LinearLayout lnrhelp;
    @BindView(R.id.lnrlogout)
    LinearLayout lnrlogout;
    Unbinder unbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_accounts_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        GetDriverInfo();
    }

    private void GetDriverInfo() {

        final ProgressDialog pd = new ProgressDialog(getActivity());
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setMessage("Loading.........");
        pd.setCancelable(false);
        pd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFD4D9D0")));
        pd.setIndeterminate(true);
        pd.show();


        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        String url = Base_URL + "/GetDriverDetailsById";

        final SharedPreferences editor = getActivity().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);

        final JSONObject GH = new JSONObject();
        try {
            GH.put("driverid", editor.getString("driverid", ""));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        final GsonRequest<DriverDetails> getRequest = new GsonRequest<DriverDetails>(Request.Method.POST, url, DriverDetails.class, null, new Response.Listener<DriverDetails>() {
            @Override
            public void onResponse(DriverDetails response) {
                pd.dismiss();
                if (response != null) {

                    Bitmap bitmap = BitmapFactory.decodeByteArray(response.imgdriver, 0, response.imgdriver.length);
                    imgdriver.setImageBitmap(bitmap);
                    txtDriverName.setText(response.name);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pd.dismiss();
            }
        }, GH);

        getRequest.setShouldCache(false);
        requestQueue.add(getRequest);

    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.lnrwaybill, R.id.lnrsettings, R.id.lnrabout, R.id.lnrhelp, R.id.lnrlogout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lnrwaybill:
                break;
            case R.id.lnrsettings:
                break;
            case R.id.lnrabout:
                break;
            case R.id.lnrhelp:
                break;
            case R.id.lnrlogout:
                break;
        }
    }
}
