package ca.awesome.travis.savemydrone.savemydrone;

import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;

import java.util.Calendar;

/**
 * Created by tco on 16-04-23.
 */
public class FlightDetailsFragment extends Fragment {

    public static final String TAG = "FlightDetailsFragment";
    private Context context;

    private EditText flightRangeEditText;
    private EditText flightTimeEditText;


    public static FlightDetailsFragment newInstance(){
        return new FlightDetailsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_set_flight_details, container, false);

        flightRangeEditText = (EditText) view.findViewById(R.id.flight_range_editText);
        flightTimeEditText = (EditText) view.findViewById(R.id.flight_time_editText);


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
        if (!flightRangeEditText.getText().equals("")){
            ((MapsActivity)getActivity()).sharedPreferences.setFlightRange(Float.parseFloat(flightRangeEditText.getText().toString()));
        }

        if (!flightTimeEditText.getText().equals("")){
            ((MapsActivity)getActivity()).sharedPreferences.setFlightTime(Integer.parseInt(flightTimeEditText.getText().toString()));
        }

        ((MapsActivity)getActivity()).doneButtonPressed(this);
    }
}
