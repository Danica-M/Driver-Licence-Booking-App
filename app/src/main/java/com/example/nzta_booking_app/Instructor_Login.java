package com.example.nzta_booking_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Instructor_Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.instructor_login);
    }

    public void normalRegister(View view) {
        Intent nrIntent = new Intent(this, Normal_Registration.class);
        startActivity(nrIntent);
    }
    public void instructorRegister(View view) {
        Intent irIntent = new Intent(this, Instructor_Registration.class);
        startActivity(irIntent);
    }

    public void instructorLogin(View view) {
        Intent nlIntent = new Intent(this, Instructor_Home.class);
        startActivity(nlIntent);
    }


}