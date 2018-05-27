package com.example.blazk.twistyride;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickLaunchMaps(View view) {
        Intent intent = new Intent(this, TrackMapsActivity.class);
        startActivity(intent);
    }

    public void onClickLaunchService(View view) {
        Intent intent = new Intent(this, serviceHistory.class);
        startActivity(intent);
    }
}
