package com.example.nzta_booking_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Normal_Registration extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.normal_registration);
    }
    public void normalLogin(View view) {
        Intent nlIntent = new Intent(this, Normal_Login.class);
        startActivity(nlIntent);
    }
}