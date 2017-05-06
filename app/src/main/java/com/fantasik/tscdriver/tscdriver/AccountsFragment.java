package com.fantasik.tscdriver.tscdriver;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class AccountsFragment extends Fragment {


    @BindView(R.id.imgdriver)
    ImageView imgdriver;
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
