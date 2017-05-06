package layout;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fantasik.tscdriver.tscdriver.Agent.MyAdapter;
import com.fantasik.tscdriver.tscdriver.Agent.TripHistory;
import com.fantasik.tscdriver.tscdriver.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class EarningsTodayFragment extends Fragment {

    private static RecyclerView.Adapter adapter;
    @BindView(R.id.my_recycler_view)
     RecyclerView rv;
    Unbinder unbinder;
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

        TripHistory[] tfg = new TripHistory[3];
        tfg[0] = new TripHistory();
        tfg[0].uname = "ravu";
        tfg[0].cost = "200";
        tfg[0].paymentmode = "Credit";

        tfg[1] = new TripHistory();
        tfg[1].uname = "kiam";
        tfg[1].cost = "2030";
        tfg[1].paymentmode = "Cashh";

        tfg[2] = new TripHistory();
        tfg[2].uname = "babu";
        tfg[2].cost = "10";
        tfg[2].paymentmode = "Debit";

        rv.setHasFixedSize(true);
        MyAdapter adapter = new MyAdapter(tfg);
        rv.setAdapter(adapter);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);

        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


}
