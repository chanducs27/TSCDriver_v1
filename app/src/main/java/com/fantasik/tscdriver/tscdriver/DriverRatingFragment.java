package com.fantasik.tscdriver.tscdriver;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.fantasik.tscdriver.tscdriver.Agent.GsonRequest;
import com.fantasik.tscdriver.tscdriver.Agent.RatingHistory;
import com.fantasik.tscdriver.tscdriver.Agent.RatingHistoryAdapter;
import com.fantasik.tscdriver.tscdriver.Agent.TripHistory;
import com.fantasik.tscdriver.tscdriver.Agent.TripHistoryAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static android.content.Context.MODE_PRIVATE;
import static com.fantasik.tscdriver.tscdriver.Agent.AgentMnager.Base_URL;
import static com.fantasik.tscdriver.tscdriver.Agent.AgentMnager.MY_PREFS_NAME;

public class DriverRatingFragment extends Fragment {


    @BindView(R.id.txttotlrating)
    TextView txttotlrating;
    @BindView(R.id.ratTotalBar)
    RatingBar ratTotalBar;
    @BindView(R.id.imageView8)
    ImageView imageView8;
    @BindView(R.id.txtTotalNo)
    TextView txtTotalNo;
    @BindView(R.id.textView20)
    TextView textView20;
    @BindView(R.id.pr5star)
    ProgressBar pr5star;
    @BindView(R.id.txtnoof5stars)
    TextView txtnoof5stars;
    @BindView(R.id.pr4star)
    ProgressBar pr4star;
    @BindView(R.id.txtnoof4stars)
    TextView txtnoof4stars;
    @BindView(R.id.pr3star)
    ProgressBar pr3star;
    @BindView(R.id.txtnoof3stars)
    TextView txtnoof3stars;
    @BindView(R.id.pr2star)
    ProgressBar pr2star;
    @BindView(R.id.txtnoof2stars)
    TextView txtnoof2stars;
    @BindView(R.id.imageView9)
    ImageView imageView9;
    @BindView(R.id.textView21)
    TextView textView21;
    @BindView(R.id.pr1star)
    ProgressBar pr1star;
    @BindView(R.id.txtnoof1stars)
    TextView txtnoof1stars;
    @BindView(R.id.lnrRatings)
    LinearLayout lnrRatings;
    Unbinder unbinder;
    @BindView(R.id.recycler_rating)
    RecyclerView rv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_driver_rating, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        GetRatingHistoryInfo();
    }

    private void GetRatingHistoryInfo() {

        final ProgressDialog pd = new ProgressDialog(getActivity());
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setMessage("Loading.........");
        pd.setCancelable(false);
        pd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFD4D9D0")));
        pd.setIndeterminate(true);
        pd.show();


        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        String url = Base_URL + "/GetDriverRatingHistory";

        final SharedPreferences editor = getActivity().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);

        final JSONObject GH = new JSONObject();
        try {
            GH.put("driverid", editor.getString("driverid", ""));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        final GsonRequest<RatingHistory[]> getRequest = new GsonRequest<RatingHistory[]>(Request.Method.POST, url, RatingHistory[].class, null, new Response.Listener<RatingHistory[]>() {
            @Override
            public void onResponse(RatingHistory[] response) {
                pd.dismiss();
                if (response != null && response.length > 0) {
                    RatingHistory[] tfg = response;


                    int totUsers = 0;
                    double totalRating = 0;
                    int star5users=0;
                    int star4users=0;
                    int star3users=0;
                    int star2users=0;
                    int star1users=0;

                    for(int i=0;i< tfg.length; i++)
                    {
                        totalRating += Double.parseDouble(tfg[i].rate);
                    }
                    if(totalRating > 0.0) {
                        totalRating = totalRating / 5.0;
                        totalRating = Math.round(totalRating * 10.0)/ 10.0;
                    }
                    txttotlrating.setText(String.valueOf(totalRating));
                    txtTotalNo.setText(String.valueOf(tfg.length));

                    ratTotalBar.setRating((float) totalRating);


                    rv.setHasFixedSize(true);
                    RatingHistoryAdapter adapter = new RatingHistoryAdapter(tfg);
                    rv.setAdapter(adapter);

                    LinearLayoutManager llm = new LinearLayoutManager(getActivity());
                    rv.setLayoutManager(llm);
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
}
