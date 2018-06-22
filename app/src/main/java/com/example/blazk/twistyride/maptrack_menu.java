
package com.example.blazk.twistyride;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

public class maptrack_menu extends Fragment implements View.OnClickListener {

    boolean StartTracking = false;
    private ImageButton recordButton;

    OnRecordButtonClick mCallback;

    // Container Activity must implement this interface
    public interface OnRecordButtonClick {
        public void onRecordButtonPressed(boolean status);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (OnRecordButtonClick) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_menu, container, false);
        Log.w("mapTrack", "inflate");

        recordButton = v.findViewById(R.id.record_button);
        recordButton.setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.record_button:
                if(StartTracking == false) {
                    recordButton.setImageResource(R.drawable.stop);
                    StartTracking = true;
                    Log.w("mapTrack_menu", "record button = true");
                }else{
                    recordButton.setImageResource(R.drawable.record);
                    StartTracking = false;
                    Log.w("mapTrack_menu", "record button = false");
                }
                mCallback.onRecordButtonPressed(StartTracking);
                break;
        }
    }
}