package com.fantasik.tscdriver.tscdriver;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

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
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
