package com.example.blazk.twistyride;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

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

    public void onClickLaunchService(final View view) {

        Intent intent = new Intent(view.getContext(), ServiceHistoryMainWindow.class);
        startActivity(intent);
        // setup the alert builder
        /*
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose");
        builder.setMessage("How would you like to enter the data");

        // add the buttons
        builder.setPositiveButton("Take photo", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dispatchTakePictureIntent();
            }
        });
        builder.setNegativeButton("Write data", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(view.getContext(), WriteServiceData.class);
                startActivity(intent);
            }
        });
        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
        */
    }



}
