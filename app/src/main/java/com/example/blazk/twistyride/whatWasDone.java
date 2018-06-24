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
import android.widget.Toast;

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

        FloatingActionButton myFab = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        myFab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onClickButtonFinish();
            }
        });

    }

    public void onClickButtonFinish() {

        boolean CONTINUE = false;

        Log.d("whatWasDone", "Date selected was: " + serviceItem.get_date());

        if (cbBrakeDisk.isChecked()) {
            serviceItem.put_service("BRAKE_DISK");
            CONTINUE = true;
        }
        if (cbAirFilter.isChecked()) {
            serviceItem.put_service("AIR_FILTER");
            CONTINUE = true;
        }
        if (cbBrakePads.isChecked()) {
            serviceItem.put_service("BRAKE_PADS");
            CONTINUE = true;
        }
        if (cbBattery.isChecked()) {
            serviceItem.put_service("BATTERY");
            CONTINUE = true;
        }
        if (cbTyres.isChecked()) {
            serviceItem.put_service("TYRES");
            CONTINUE = true;
        }
        if (cbChain.isChecked()) {
            serviceItem.put_service("CHAIN");
            CONTINUE = true;
        }
        if (cbSprocket.isChecked()) {
            serviceItem.put_service("SPROCKET");
            CONTINUE = true;
        }
        if (cbCoolant.isChecked()) {
            serviceItem.put_service("COOLANT");
            CONTINUE = true;
        }
        if (cbOil.isChecked()) {
            serviceItem.put_service("OIL");
            CONTINUE = true;
        }

        if (CONTINUE == false) {
            CharSequence text = "Check what was done!";
            int duration = Toast.LENGTH_SHORT;
            CONTINUE = false;
            Toast toast = Toast.makeText(this, text, duration);
            toast.show();
        }

        if (edCost.getText().toString() == "") {
            CharSequence text = "Enter cost of service!";
            int duration = Toast.LENGTH_SHORT;
            CONTINUE = false;
            Toast toast = Toast.makeText(this, text, duration);
            toast.show();
        }

        if (edServiceShop.getText().toString().isEmpty()) {
            CharSequence text = "Enter service shop!";
            int duration = Toast.LENGTH_SHORT;
            CONTINUE = false;
            Toast toast = Toast.makeText(this, text, duration);
            toast.show();
        }

        if (CONTINUE == true){
            serviceItem.put_shopAndPrice(edServiceShop.getText().toString(), edCost.getText().toString());

            Log.d("whatWasDone", "Returning");
            Intent returnIntent = new Intent(this, ServiceHistoryMainWindow.class);
            returnIntent.putExtra("ServiceItem", serviceItem);
            setResult(Activity.RESULT_OK, returnIntent);
            finish();
    }

    }

}
