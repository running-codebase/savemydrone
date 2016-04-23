package ca.awesome.travis.savemydrone.savemydrone;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by tco on 16-04-23.
 */
public class ChecklistFragment extends Fragment {

    public static final String TAG = "ChecklistFragment";
    private Context context;


    private ImageView airspaceImageView;
    private ImageView windImageView;
    private ImageView daylightImageView;

    private TextView flightAdviceTextView;

    private Checklist checklist;


    public static ChecklistFragment newInstance() {
        return new ChecklistFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_checklist, container, false);


        airspaceImageView = (ImageView) view.findViewById(R.id.airspace_imageView);
        windImageView = (ImageView) view.findViewById(R.id.wind_imageView);
        daylightImageView = (ImageView) view.findViewById(R.id.daylight_imageView);
        flightAdviceTextView = (TextView) view.findViewById(R.id.flightAdvice_TextView);

        Button doneButton = (Button) view.findViewById(R.id.done_button);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                donePressed();
            }
        });


        return view;
    }


    private void goThroughChecklist(){
        checklist = new Checklist();
        int time = 0;
        double windGust = 10;
        double windSteady = 10;


//        if (checklist.isWindy(){
//            windImageView.setBackground(getResources().getColor(android.R.color.holo_red_dark));
//        } else {
//            windImageView.setBackground(getResources().getColor(android.R.color.holo_green_dark));
//        }
//
//        if (checklist.isDark(){
//            windImageView.setBackground(getResources().getColor(android.R.color.holo_red_dark));
//        } else {
//            windImageView.setBackground(getResources().getColor(android.R.color.holo_green_dark));
//        }
//
//        if (checklist.isWindy(){
//            windImageView.setBackground(getResources().getColor(android.R.color.holo_red_dark));
//        } else {
//            windImageView.setBackground(getResources().getColor(android.R.color.holo_green_dark));
//        }


        if (checklist.isDark(time)) {
            daylightImageView.setBackgroundColor(Color.RED);
        } else {
            daylightImageView.setBackgroundColor(Color.GREEN);
        }

        if (checklist.isWindy(windGust, windSteady)) {
            windImageView.setBackgroundColor(Color.RED);
        } else {
            windImageView.setBackgroundColor(Color.GREEN);
        }

        if (checklist.isWindy(windGust, windSteady)) {
            flightAdviceTextView.setText("The weather isn't great today. Perhaps you should consider returning when it's better.");
            flightAdviceTextView.setTextColor(Color.RED);
        } else if (!checklist.isSafeAirspace()) {
            flightAdviceTextView.setText("It is not allowed to fly in this airspace. Try moving outside this restricted zone.");
            flightAdviceTextView.setTextColor(Color.RED);
        } else {
            flightAdviceTextView.setText("Checklist complete. You're cleared for take off!");
            flightAdviceTextView.setTextColor(Color.GREEN);

        }
    }


    private void donePressed() {
        ((MapsActivity)getActivity()).currentState = MapsActivity.AppState.START_FLIGHT;

        ((MapsActivity) getActivity()).doneButtonPressed(this);
    }

}
