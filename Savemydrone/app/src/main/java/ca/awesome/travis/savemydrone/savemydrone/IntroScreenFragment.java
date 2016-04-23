package ca.awesome.travis.savemydrone.savemydrone;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by tco on 16-04-23.
 */
public class IntroScreenFragment extends Fragment implements View.OnClickListener {

    public static final String TAG = "IntroScreenFragment";
    private Context context;

    private Button btnPlanFlight;
    private Button btnFlyNow;
    private Button btnDebrief;

    private View.OnClickListener onClickListener;



    public static IntroScreenFragment newInstance(){
        return new IntroScreenFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_intro_screen, container, false);

        btnPlanFlight = (Button) view.findViewById(R.id.btn_plan_flight);
        btnFlyNow = (Button) view.findViewById(R.id.btn_fly_now);
        btnDebrief = (Button) view.findViewById(R.id.btn_debrief);

        btnPlanFlight.setOnClickListener(this);
        btnFlyNow.setOnClickListener(this);
        btnDebrief.setOnClickListener(this);

        return view;

    }


    @Override
    public void onClick(View v) {

        switch(v.getId()){
            case(R.id.btn_plan_flight):
                ((MapsActivity)getActivity()).planFlightPressed();
                break;
            case (R.id.btn_fly_now):
                ((MapsActivity)getActivity()).currentState = MapsActivity.AppState.FLIGHT_CHECK;
                ((MapsActivity)getActivity()).flyNowButtonPressed();
                break;
            case(R.id.btn_debrief):
                ((MapsActivity)getActivity()).debriefButtonPressed();
                break;
        }

    }
}
