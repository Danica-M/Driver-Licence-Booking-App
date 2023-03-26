package com.example.nzta_booking_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Manage_Booking extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_booking);
    }
    public void bookTest(View view) {
        Intent bIntent = new Intent(this, Booking_Date_Selection.class);
        startActivity(bIntent);
    }
}