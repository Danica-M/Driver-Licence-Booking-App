package com.example.nzta_booking_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Normal_Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.normal_home);
    }
    public void mBooking(View view) {
        Intent bIntent = new Intent(this, Manage_Booking.class);
        startActivity(bIntent);
    }
}