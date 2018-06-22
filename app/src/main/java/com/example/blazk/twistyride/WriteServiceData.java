package com.example.blazk.twistyride;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;

import java.util.Date;

public class WriteServiceData extends AppCompatActivity {

    Button bNext;
    CalendarView calendarView;
    ServiceItem serviceItem;
    /*
    int _year;
    int _month;
    int _day;
    */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_service_data);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        bNext = findViewById(R.id.bNext);
        calendarView = findViewById(R.id.calView);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                serviceItem = new ServiceItem(ServiceItem.DataType.TEXT);
                serviceItem.set_dateInt(year, month, dayOfMonth);
                /*
                _year = year;
                _month = month;
                _day = dayOfMonth;
                */
            }
        });
    }

    public void onClickButtonNext(View view) {
        Intent intent = new Intent(this, whatWasDone.class);
        /*
        intent.putExtra("ServiceItem_year", _year);
        intent.putExtra("ServiceItem_month", _month);
        intent.putExtra("ServiceItem_day", _day);
        */
        intent.putExtra("ServiceItem", serviceItem);
        startActivity(intent);
    }

}
