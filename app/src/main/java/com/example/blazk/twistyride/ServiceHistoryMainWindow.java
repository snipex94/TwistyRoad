package com.example.blazk.twistyride;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.FileProvider;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ServiceHistoryMainWindow extends AppCompatActivity implements View.OnClickListener{

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private Boolean isFabOpen = false;
    private FloatingActionButton fab,fab1,fab2;
    private Animation fab_open;
    private Animation fab_close;
    private Animation rotate_forward;
    private Animation rotate_backward;

    MyDatabaseHelper dbHelper;
    String[] myDataset = new String[5];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_history_main_window);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        myDataset[0] = "Hello0";
        myDataset[1] = "Hello1";
        myDataset[2] = "Hello2";
        myDataset[3] = "Hello3";
        myDataset[4] = "Hello4";

        dbHelper = new MyDatabaseHelper(this, null, null, 2);

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new MyAdapter(myDataset);
        mRecyclerView.setAdapter(mAdapter);

        fab = (FloatingActionButton)findViewById(R.id.fab);
        fab1 = (FloatingActionButton)findViewById(R.id.fab1);
        fab2 = (FloatingActionButton)findViewById(R.id.fab2);

        // XXX: Does not work if just 0. It calls `ImageView#setAlpha(int)` deprecated method.
        fab1.setAlpha(0.0f);
        fab2.setAlpha(0.0f);

        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_close);
        rotate_forward = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_forward);
        rotate_backward = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_backward);
        fab.setOnClickListener(this);
        fab1.setOnClickListener(this);
        fab2.setOnClickListener(this);
    }

    public void animateFAB(){
/*
        //Doesn't work well with other animations
        final Animation animation_close_fab = new TranslateAnimation(0, 100,0,0);
        animation_close_fab.setDuration(300);
        animation_close_fab.setFillAfter(true);

        final Animation animation_open_fab = new TranslateAnimation(100, 0,0,0);
        animation_open_fab.setDuration(300);
        animation_open_fab.setFillAfter(true);

        AlphaAnimation alphaUP = new AlphaAnimation(0f, 1.0f);
        alphaUP.setDuration(300);
        alphaUP.setFillAfter(true);

        AlphaAnimation alphaDOWN = new AlphaAnimation(1.0f, 0f);
        alphaDOWN.setDuration(300);
        alphaDOWN.setFillAfter(true);
*/
        if(isFabOpen){

            fab.startAnimation(rotate_backward);

            ViewCompat.animate(fab1).alpha(0.0f).setDuration(300);
            fab1.startAnimation(fab_close);

            ViewCompat.animate(fab2).alpha(0.0f).setDuration(300);
            fab2.startAnimation(fab_close);

            fab1.setClickable(false);
            fab2.setClickable(false);
            isFabOpen = false;
            Log.d("ServiceHistoryMain", "FAB close");

        } else {

            fab.startAnimation(rotate_forward);

            ViewCompat.animate(fab1).alpha(1).setDuration(300);
            fab1.startAnimation(fab_open);

            ViewCompat.animate(fab2).alpha(1).setDuration(500);
            fab2.startAnimation(fab_open);

            fab1.setClickable(true);
            fab2.setClickable(true);
            isFabOpen = true;
            Log.d("ServiceHistoryMain", "FAB open");

        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.fab:
                animateFAB();
                Log.d("ServiceHistoryMain", "Main FAB clicked. Animating...");
                break;
            case R.id.fab1:
                Log.d("ServiceHistoryMain", "Fab 1 Clicked");
                dispatchTakePictureIntent();
                break;
            case R.id.fab2:
                Log.d("ServiceHistoryMain", "Fab 2 Clicked");
                Intent intent = new Intent(this, WriteServiceData.class);
                startActivity(intent);
                break;
        }
    }

    String mCurrentPhotoPath;

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    static final int REQUEST_TAKE_PHOTO = 1;

    Uri photoURI = null;

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                Log.d("Picture", "Exception catched when trying to create a file");
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            Log.d("ServiceHistoryMain", "Finished with taking a picture. Now in onActivityResult.");
            Log.d("ServiceHistoryMain", "Photo URI: " + photoURI.toString());
            ServiceItem serviceItem = new ServiceItem(ServiceItem.DataType.IMAGE);
            serviceItem.set_photoUri(photoURI.toString());
            dbHelper.addImagePath(serviceItem);
            //Log.d("ServiceHistoryMain", "Database: " + dbHelper.databaseToString());
            //dbHelper.addType(serviceItem);
        }
    }
}
