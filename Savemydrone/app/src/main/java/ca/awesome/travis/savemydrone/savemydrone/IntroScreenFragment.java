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
public class IntroScreenFragment extends Fragment {

    public static final String TAG = "IntroScreenFragment";
    private Context context;


    public static IntroScreenFragment newInstance(){
        return new IntroScreenFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_intro_screen, container, false);

        TextView tempTextView = (TextView) view.findViewById(R.id.textview_temp);
        tempTextView.setText("TEST");

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
