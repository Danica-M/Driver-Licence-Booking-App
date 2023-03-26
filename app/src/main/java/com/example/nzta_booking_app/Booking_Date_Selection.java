package com.example.nzta_booking_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;



public class Booking_Date_Selection extends AppCompatActivity {

    CalendarView calendarView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.booking_date_selection);


        calendarView = findViewById(R.id.calendarView);

        Calendar calendar = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();

        // Check if selected date is a weekend day (Saturday or Sunday)
        if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY){
            // Reset selected date to previous weekday (Friday)
            calendar.add(Calendar.DAY_OF_MONTH,2);

        } else if(calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            calendar.add(Calendar.DAY_OF_MONTH,1);

        }else{
            calendar.add(Calendar.DAY_OF_MONTH,0);
        }
        calendar2.add(Calendar.YEAR, 2);
        long minD = calendar.getTimeInMillis();
        long maxDate = calendar2.getTimeInMillis();

        calendarView.setMinDate(minD);
        calendarView.setMaxDate(maxDate);

    }
    public void bookingSession(View view) {
        Intent nlIntent = new Intent(this, Booking_Session_Selection.class);
        startActivity(nlIntent);
    }
}