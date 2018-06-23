package com.example.blazk.twistyride;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import static android.content.Intent.FLAG_ACTIVITY_FORWARD_RESULT;

public class whatWasDone extends AppCompatActivity {

    ServiceItem serviceItem;

    EditText edCost;
    EditText edServiceShop;

    CheckBox cbBrakeDisk;
    CheckBox cbAirFilter;
    CheckBox cbBrakePads;
    CheckBox cbBattery;
    CheckBox cbTyres;
    CheckBox cbChain;
    CheckBox cbSprocket;
    CheckBox cbCoolant;
    CheckBox cbOil;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_what_was_done);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        serviceItem = getIntent().getParcelableExtra("ServiceItem");

        edCost = findViewById(R.id.edCost);
        edServiceShop = findViewById(R.id.edServiceShop);

        cbBrakeDisk = findViewById(R.id.cbBrakeDisk);
        cbAirFilter = findViewById(R.id.cbAirFilter);
        cbBrakePads = findViewById(R.id.cbBrakePads);
        cbBattery = findViewById(R.id.cbBattery);
        cbTyres = findViewById(R.id.cbTyres);
        cbChain = findViewById(R.id.cbChain);
        cbSprocket = findViewById(R.id.cbSprocket);
        cbCoolant = findViewById(R.id.cbCoolant);
        cbOil = findViewById(R.id.cbOil);
    }

    public void onClickButtonFinish(View view) {
        Log.d("whatWasDone", "Date selected was: " + serviceItem.get_date());

        if(cbBrakeDisk.isChecked()) {
            serviceItem.put_service("BRAKE_DISK");
        }
        if(cbAirFilter.isChecked()) {
            serviceItem.put_service("AIR_FILTER");
        }
        if(cbBrakePads.isChecked()) {
            serviceItem.put_service("BRAKE_PADS");
        }
        if(cbBattery.isChecked()) {
            serviceItem.put_service("BATTERY");
        }
        if(cbTyres.isChecked()) {
            serviceItem.put_service("TYRES");
        }
        if(cbChain.isChecked()) {
            serviceItem.put_service("CHAIN");
        }
        if(cbSprocket.isChecked()) {
            serviceItem.put_service("SPROCKET");
        }
        if(cbCoolant.isChecked()) {
            serviceItem.put_service("COOLANT");
        }
        if(cbOil.isChecked()) {
            serviceItem.put_service("OIL");
        }

        serviceItem.put_shopAndPrice(edServiceShop.getText().toString(), edCost.getText().toString());

        Log.d("whatWasDone", "Returning");
        Intent returnIntent = new Intent(this, ServiceHistoryMainWindow.class);
        returnIntent.putExtra("ServiceItem", serviceItem);
        setResult(Activity.RESULT_OK,returnIntent);
        finish();

    }

}
