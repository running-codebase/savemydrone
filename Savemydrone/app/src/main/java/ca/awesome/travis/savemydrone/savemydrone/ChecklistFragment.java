package ca.awesome.travis.savemydrone.savemydrone;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by tco on 16-04-23.
 */
public class ChecklistFragment  extends Fragment {

    public static final String TAG = "ChecklistFragment";
    private Context context;

//    private EditText flightRangeEditText;
//    private EditText flightTimeEditText;


    public static ChecklistFragment newInstance(){
        return new ChecklistFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_set_flight_details, container, false);

//        flightRangeEditText = (EditText) view.findViewById(R.id.flight_range_editText);
//        flightTimeEditText = (EditText) view.findViewById(R.id.flight_time_editText);


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
        ((MapsActivity)getActivity()).doneButtonPressed(this);
    }

}
