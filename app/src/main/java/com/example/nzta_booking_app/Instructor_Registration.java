package com.example.nzta_booking_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Instructor_Registration extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.instructor_registration);
    }
    public void instructorLogin(View view) {
        Intent ilIntent = new Intent(this, Instructor_Login.class);
        startActivity(ilIntent);
    }
}