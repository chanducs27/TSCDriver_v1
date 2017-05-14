package layout;


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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.fantasik.tscdriver.tscdriver.Agent.GsonRequest;
import com.fantasik.tscdriver.tscdriver.Agent.MyAdapter;
import com.fantasik.tscdriver.tscdriver.Agent.TripHistory;
import com.fantasik.tscdriver.tscdriver.R;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static android.content.Context.MODE_PRIVATE;
import static com.fantasik.tscdriver.tscdriver.Agent.AgentMnager.Base_URL;
import static com.fantasik.tscdriver.tscdriver.Agent.AgentMnager.MY_PREFS_NAME;

/**
 * A simple {@link Fragment} subclass.
 */
public class EarningsTodayFragment extends Fragment {

    private static RecyclerView.Adapter adapter;
    @BindView(R.id.my_recycler_view)
    RecyclerView rv;
    Unbinder unbinder;
    @BindView(R.id.txtEarning)
    TextView txtEarning;
    @BindView(R.id.relearning)
    RelativeLayout relearning;
    @BindView(R.id.txtTime)
    TextView txtTime;
    @BindView(R.id.relspendtime)
    RelativeLayout relspendtime;
    @BindView(R.id.txtnooftrips)
    TextView txtnooftrips;
    @BindView(R.id.relnooftrips)
    RelativeLayout relnooftrips;

    private RecyclerView.LayoutManager layoutManager;


    public EarningsTodayFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_earnings_today, container, false);
        unbinder = ButterKnife.bind(this, view);


        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        GetTripHistoryInfo();
    }

    private void GetTripHistoryInfo() {

        final ProgressDialog pd = new ProgressDialog(getActivity());
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setMessage("Loading.........");
        pd.setCancelable(false);
        pd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFD4D9D0")));
        pd.setIndeterminate(true);
        pd.show();


        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        String url = Base_URL + "/GetTripHistoryToday";

        final SharedPreferences editor = getActivity().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);

        final JSONObject GH = new JSONObject();
        try {
            GH.put("driverid", editor.getString("driverid", ""));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        final GsonRequest<TripHistory[]> getRequest = new GsonRequest<TripHistory[]>(Request.Method.POST, url, TripHistory[].class, null, new Response.Listener<TripHistory[]>() {
            @Override
            public void onResponse(TripHistory[] response) {
                pd.dismiss();
                if (response != null && response.length > 0) {
                    TripHistory[] tfg = response;

                   txtnooftrips.setText(String.valueOf(tfg.length));

                    int totCost=0;
                    int totmins = 0;
                    for(int i=0;i<tfg.length;i++)
                    {
                        totCost += Integer.parseInt(tfg[i].cost);
                        totmins += Integer.parseInt(tfg[i].cost);
                    }

                     txtEarning.setText(String.valueOf(totCost) + " Rs");
                    txtTime.setText(String.valueOf(totmins)+ " mins");

                    rv.setHasFixedSize(true);
                    MyAdapter adapter = new MyAdapter(tfg);
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
