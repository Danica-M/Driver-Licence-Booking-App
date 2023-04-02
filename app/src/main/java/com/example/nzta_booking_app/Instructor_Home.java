package com.example.nzta_booking_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Instructor_Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.instructor_home);
    }
    public void logout(View view) {
        Intent bIntent = new Intent(this, Landing_Page.class);
        startActivity(bIntent);
    }
}