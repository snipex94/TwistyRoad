package com.example.blazk.twistyride;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

public class whatWasDone extends AppCompatActivity {

    ServiceItem serviceItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_what_was_done);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        serviceItem = getIntent().getParcelableExtra("ServiceItem");
    }

    public void onClickButtonFinish(View view) {
        Log.d("whatWasDone", serviceItem.get_date());
    }

}
