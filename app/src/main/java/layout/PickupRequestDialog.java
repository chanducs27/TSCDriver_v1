package layout;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fantasik.tscdriver.tscdriver.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class PickupRequestDialog extends DialogFragment implements OnMapReadyCallback {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "startlat";
    private static final String ARG_PARAM2 = "startlng";
    private static final String ARG_PARAM3= "cost";
    private static final String ARG_PARAM4= "distance";
    private static final String ARG_PARAM5 = "addr";
    OnTimePickedListener mCallback;

    @BindView(R.id.txtEstimatedTime)
    TextView txtEstimatedTime;
    @BindView(R.id.txtEstimateEarn)
    TextView txtEstimateEarn;
    @BindView(R.id.txtPickupAddress)
    TextView txtPickupAddress;

    @BindView(R.id.txtAccept)
    TextView txtAccept;
    @BindView(R.id.txtReject)
    TextView txtReject;
    Unbinder unbinder;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public PickupRequestDialog() {
        // Required empty public constructor
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.txtAccept, R.id.txtReject})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.txtAccept:
                if (mCallback != null) {
                    mCallback.onTimePicked(true);
                }
                dismiss();
                break;
            case R.id.txtReject:
                if (mCallback != null) {
                    mCallback.onTimePicked(false);
                }
                dismiss();
                break;
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.getUiSettings().setZoomControlsEnabled(false);
        googleMap.getUiSettings().setMyLocationButtonEnabled(false);

        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(Double.parseDouble(getArguments().getString(ARG_PARAM1)), Double.parseDouble(getArguments().getString(ARG_PARAM2)))));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(Double.parseDouble(getArguments().getString(ARG_PARAM1)), Double.parseDouble(getArguments().getString(ARG_PARAM2)))));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(15));
    }

    /**
     * An interface containing onTimePicked() method signature.
     * Container Activity must implement this interface.
     */
    public interface OnTimePickedListener {
        public void onTimePicked(boolean isaccepted);
    }


    // TODO: Rename and change types and number of parameters
    public static PickupRequestDialog newInstance(String startlat, String startlng, String cost, String distance, String addr) {
        PickupRequestDialog fragment = new PickupRequestDialog();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, startlat);
        args.putString(ARG_PARAM2, startlng);
        args.putString(ARG_PARAM3, cost);
        args.putString(ARG_PARAM4, distance);
        args.putString(ARG_PARAM5, addr);

        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            mCallback = (OnTimePickedListener) getTargetFragment();
        } catch (ClassCastException e) {
            throw new ClassCastException(getTargetFragment().toString() + " must implement OnTimePickedListener.");
        }


    }

    SupportMapFragment supportMapFragment;
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            txtEstimateEarn.setText(getArguments().getString(ARG_PARAM3));
            txtPickupAddress.setText(getArguments().getString(ARG_PARAM5));
        }

        if (Build.VERSION.SDK_INT < 21) {
            supportMapFragment = (SupportMapFragment) getActivity()
                    .getSupportFragmentManager().findFragmentById(R.id.map_pickup);
        } else {
            supportMapFragment = (SupportMapFragment) getActivity().getSupportFragmentManager()
                    .findFragmentById(R.id.map_pickup);
        }
        supportMapFragment.getMapAsync(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pickup_request_dialog, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }


}
