
package com.example.blazk.twistyride;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

public class maptrack_menu extends Fragment{

    boolean StartTracking = false;

    FloatingActionButton fabPlay;
    FloatingActionButton fabService;

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
        Log.d("mapTrack", "inflate");

        fabPlay = (FloatingActionButton) v.findViewById(R.id.fabPlay);
        fabPlay.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onClickButton(v);
            }
        });

        fabService = (FloatingActionButton) v.findViewById(R.id.fabService);
        fabService.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onClickButton(v);
            }
        });

        return v;
    }

    public void onClickButton(View v) {
        switch (v.getId()) {
            case R.id.fabPlay:
                if(StartTracking == false) {
                    fabPlay.setImageResource(R.drawable.ic_stop);
                    StartTracking = true;
                    Log.d("mapTrack_menu", "record button = true");
                }else{
                    fabPlay.setImageResource(R.drawable.ic_play_arrow);
                    StartTracking = false;
                    Log.d("mapTrack_menu", "record button = false");
                }
                mCallback.onRecordButtonPressed(StartTracking);
                break;
            case R.id.fabService:
                Log.w("mapTrack_menu", "Starting SeviceHistoryMainWindow");
                Intent intent = new Intent(v.getContext(), ServiceHistoryMainWindow.class);
                startActivity(intent);
                break;
        }
    }
}