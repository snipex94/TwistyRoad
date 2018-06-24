package com.example.blazk.twistyride;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.SphericalUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TrackMapsActivity extends FragmentActivity implements OnMapReadyCallback, maptrack_menu.OnRecordButtonClick{

    final static int PERMISSION_ALL = 1;
    final static String[] PERMISSIONS = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};

    private GoogleMap mMap;

 //   boolean StartTracking = false;

    MarkerOptions mo;
    Marker marker;
    LocationManager locationManager;
    LatLng latLng;

    TextView tv_distDriven;
    TextView tv_time;
    TextView tv_avgSpeed;

    Date startDate;
    Date endDate;
    private String defaultDistDriverString = "Distance driven : ";
    private String defaultTimeString = "Time : ";
    private String defaultavgSpeedString = "Average speed : ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        mo = new MarkerOptions().position(new LatLng(0, 0)).title("My current location");
        if (Build.VERSION.SDK_INT >= 23 && !isPermissionGranted()) {
            requestPermissions(PERMISSIONS, PERMISSION_ALL);

        } else requestLocation();
        if (!isLocationEnabled()) {
            showAlert(1);
        }

        tv_distDriven = findViewById(R.id.tv_distDriven);
        tv_time = findViewById(R.id.tv_time);
        tv_avgSpeed = findViewById(R.id.tv_AvgSpeed);

        tv_distDriven.setText(defaultDistDriverString);
        tv_time.setText(defaultTimeString);
        tv_avgSpeed.setText(defaultavgSpeedString);
    }

    private void computeAndShowTraceData() {
        double distance = SphericalUtil.computeLength(myCoordinates);
        Log.d("TrackMapsActivity", "Calculated distance is : " + distance + " m");
        tv_distDriven.setText(defaultDistDriverString + String.valueOf((int)distance) + " m");

        double time = (endDate.getTime() - startDate.getTime()) / 1000;
        Log.d("TrackMapsActivity", "Calculated time is : " + String.valueOf((int)time) + " s");
        tv_time.setText(defaultTimeString + String.valueOf(time) + " s");

        double avgSpeed = (3.6*distance)/time ;
        Log.d("TrackMapsActivity", "Calculated average speed is : " + String.valueOf(avgSpeed) + " km/h");
        tv_avgSpeed.setText(defaultavgSpeedString + String.valueOf((int)avgSpeed) + " km/h");
    }

    private void showAlert(final int status) {
        String message, title, btnText;
        if (status == 1) {
            message = "Your Locations Settings is set to 'Off'. \nPlease Enable Location to " +
                    "use this app";
            title = "Enable location";
            btnText = "Location Settings";
        }else {
            message = "Please allow this app to access location!";
            title = "Permission access";
            btnText = "Grant";
        }
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setCancelable(false);
        dialog.setTitle(title)
                            .setMessage(message)
                            .setPositiveButton(btnText, new DialogInterface.OnClickListener() {
                                @RequiresApi(api = Build.VERSION_CODES.M)
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (status == 1) {
                                        Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                        startActivity(myIntent);
                                    } else
                                        requestPermissions(PERMISSIONS, PERMISSION_ALL);
                                }
                            });
    }

    private boolean isLocationEnabled() {
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private boolean isPermissionGranted() {
        if (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED || checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
            Log.v("myLog", "Permission is granted");
            return true;
        }else {
            Log.v("myLog", "Permission denied");
            return false;
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        marker = mMap.addMarker(mo);
    }

    List<LatLng> myCoordinates = new ArrayList<LatLng>();

    private boolean RECORD_STATUS = false;

    LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            if(RECORD_STATUS == true) {
                myCoordinates.add(new LatLng(location.getLatitude(), location.getLongitude()));
                marker.setPosition(myCoordinates.get(myCoordinates.size() - 1));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myCoordinates.get(myCoordinates.size() - 1), 15));
                Log.w("TrackMapsActivity", "Location Changed");
            }
        }
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }
        @Override
        public void onProviderEnabled(String provider) {

        }
        @Override
        public void onProviderDisabled(String provider) {

        }
    };


    Polyline line;

    private void drawPolyline() {
        PolylineOptions options = new PolylineOptions().width(5).color(Color.BLUE).geodesic(true);
        for (int z = 0; z < myCoordinates.size(); z++) {
            LatLng point = myCoordinates.get(z);
            options.add(point);
        }
        line = mMap.addPolyline(options);
        Log.d("TrackMapsActivity", "Polyline added");
    }

    private void requestLocation() {
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setPowerRequirement(Criteria.POWER_HIGH);
        String provider = locationManager.getBestProvider(criteria, true);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 1, locationListener);
    }

    @Override
    public void onRecordButtonPressed(boolean status) {
        Log.w("TrackMapsActivity", Boolean.toString(status));
        if(status == true) {
            startDate = Calendar.getInstance().getTime();
            myCoordinates.clear();
        }
        else if((RECORD_STATUS == true) && (status == false)) {
            endDate = Calendar.getInstance().getTime();
            drawPolyline();
            computeAndShowTraceData();
        }
        RECORD_STATUS = status;
    }
}
