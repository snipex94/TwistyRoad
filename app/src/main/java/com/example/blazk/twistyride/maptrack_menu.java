
package com.example.blazk.twistyride;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

public class maptrack_menu extends Fragment implements View.OnClickListener {

    boolean StartTracking = false;
    private ImageButton recordButton;

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
                break;
        }
    }
}