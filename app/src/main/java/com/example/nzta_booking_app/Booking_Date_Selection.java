package com.example.nzta_booking_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.Toast;

import com.example.nzta_booking_app.models.Controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class Booking_Date_Selection extends AppCompatActivity {

    CalendarView calendarView;
    String selectedDate;
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

        // Set the date format
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

        // Get the selected date in milliseconds
        long selectedDateInMillis = calendarView.getDate();

        // Convert the date to a string in the desired format
        selectedDate = sdf.format(new Date(selectedDateInMillis));


        calendarView.setOnDateChangeListener((calendarView, year, month, dayOfMonth) -> {
            // Retrieve the selected date and do something with it
            // Get the selected date in milliseconds
//            long selectedDateInMillis1 = calendarView.getDate();
        // Convert the date to a string in the desired format
//            selectedDate = sdf.format(new Date(selectedDateInMillis1));
                selectedDate= dayOfMonth + "/" + (month + 1) + "/" + year;
            Toast.makeText(getApplicationContext(),selectedDate,Toast.LENGTH_SHORT).show();
        });


    }
    public void bookingSession(View view) {

        Intent nlIntent = new Intent(this, Booking_Session_Selection.class);
        nlIntent.putExtra("date", selectedDate);

        startActivity(nlIntent);
    }
}