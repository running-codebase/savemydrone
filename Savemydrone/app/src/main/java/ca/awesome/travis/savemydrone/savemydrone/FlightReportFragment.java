package ca.awesome.travis.savemydrone.savemydrone;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import ca.awesome.travis.savemydrone.savemydrone.clouddata.pojos.FlightReport;

/**
 * Created by tco on 16-04-24.
 */
public class FlightReportFragment  extends Fragment {

    public static final String TAG = "FlightReportFragment";
    private Context context;

    private EditText titleEditText;
    private EditText bodyEditText;


    public static FlightReportFragment newInstance(){
        return new FlightReportFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_debrief_create, container, false);

        titleEditText = (EditText) view.findViewById(R.id.title_edit_text);
        bodyEditText = (EditText) view.findViewById(R.id.body_edit_text);

        Button doneButton = (Button) view.findViewById(R.id.done_button);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                donePressed();
            }
        });


        return view;
    }



    private void donePressed() {

        FlightReport flightReport = new FlightReport(titleEditText.getText().toString(), bodyEditText.getText().toString());
        ((MapsActivity)getActivity()).debriefComplete(flightReport);



//        if (!flightRangeEditText.getText().toString().matches("")){
//            ((MapsActivity)getActivity()).sharedPreferences.setFlightRange(Float.parseFloat(flightRangeEditText.getText().toString()));
//        }
//
//        if (!flightTimeEditText.getText().toString().matches("")){
//            ((MapsActivity)getActivity()).sharedPreferences.setFlightTime(Integer.parseInt(flightTimeEditText.getText().toString()));
//        }

            ((MapsActivity) getActivity()).doneButtonPressed(this);
        ((MapsActivity) getActivity()).cardView.setVisibility(View.VISIBLE);
    }



}
