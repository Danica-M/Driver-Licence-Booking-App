package com.example.nzta_booking_app.user;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;

import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;

import com.example.nzta_booking_app.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class Booking_Date_Selection extends AppCompatActivity {

    CalendarView calendarView;
    String selectedDate;
    Button nextBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.booking_date_selection);
        calendarView = findViewById(R.id.calendarView);
        nextBtn = findViewById(R.id.nextBtn);
        setUpCalendar();

        // setting the selectedDate in case user clicks next without changing the default selected date
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        // Get the selected date in milliseconds
        long selectedDateInMillis = calendarView.getDate();
        // Convert the date to a string in the desired format
        selectedDate = sdf.format(new Date(selectedDateInMillis));

        calendarView.setOnDateChangeListener((calendarView, year, month, dayOfMonth) -> {
            Calendar calendar2 = Calendar.getInstance();
            calendar2.set(year, month, dayOfMonth);
            // checking if date is weekday, if not it will show a message
            if (calendar2.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || calendar2.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
                Toast.makeText(getApplicationContext(), "Select weekdays only", Toast.LENGTH_SHORT).show();
                nextBtn.setEnabled(false);
            } else {
                // Convert the calendar date to a string
                selectedDate = sdf.format(calendar2.getTime());
                nextBtn.setEnabled(true);
            }

        });
    }


    //shows an alert dialog if user clicks cancel
    public void cancelBooking(View view) {
        AlertDialog builder = new AlertDialog.Builder(Booking_Date_Selection.this)
                .setTitle("Booking Cancellation Confirmation")
                .setMessage("Are you sure you want to cancel booking process?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent nlIntent = new Intent(Booking_Date_Selection.this, Manage_Booking.class);
                        startActivity(nlIntent);
                        finishAffinity();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {}
                }).show();

    }

    //opens the second part of the booking process and passes the selected date
    public void bookingSession(View view) {
        Intent nlIntent = new Intent(this, Booking_Session_Selection.class);
        nlIntent.putExtra("date", selectedDate);
        startActivity(nlIntent);
    }


    //sets up the calendar
    public void setUpCalendar() {
        Calendar calendar = Calendar.getInstance();
        // Check if selected date is a weekend day (Saturday or Sunday)
        if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
            // Reset selected date to previous weekday (Friday)
            calendar.add(Calendar.DAY_OF_MONTH, 2);
        } else if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        } else {
            calendar.add(Calendar.DAY_OF_MONTH, 0);
        }
        long minD = calendar.getTimeInMillis();
        calendar.add(Calendar.YEAR, 2);
        long maxDate = calendar.getTimeInMillis();
        calendarView.setMinDate(minD);
        calendarView.setMaxDate(maxDate);
    }
}
