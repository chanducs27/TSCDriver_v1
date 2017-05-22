package layout;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fantasik.tscdriver.tscdriver.R;
import com.google.android.gms.maps.MapView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class PickupRequestDialog extends DialogFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "startlat";
    private static final String ARG_PARAM2 = "startlng";
    OnTimePickedListener mCallback;

    @BindView(R.id.txtEstimatedTime)
    TextView txtEstimatedTime;
    @BindView(R.id.txtEstimateEarn)
    TextView txtEstimateEarn;
    @BindView(R.id.txtPickupAddress)
    TextView txtPickupAddress;
    @BindView(R.id.mapView)
    MapView mapView;
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
                if(mCallback != null)
                {
                    mCallback.onTimePicked(true);
                }
                dismiss();
                break;
            case R.id.txtReject:
                if(mCallback != null)
                {
                    mCallback.onTimePicked(false);
                }
                dismiss();
                break;
        }
    }

    /**
     * An interface containing onTimePicked() method signature.
     * Container Activity must implement this interface.
     */
    public interface OnTimePickedListener {
        public void onTimePicked(boolean isaccepted);
    }


    // TODO: Rename and change types and number of parameters
    public static PickupRequestDialog newInstance(String startlat, String startlng) {
        PickupRequestDialog fragment = new PickupRequestDialog();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, startlat);
        args.putString(ARG_PARAM2, startlng);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        try {
            mCallback = (OnTimePickedListener) getTargetFragment();
        } catch (ClassCastException e) {
            throw new ClassCastException(getTargetFragment().toString() + " must implement OnTimePickedListener.");
        }

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
