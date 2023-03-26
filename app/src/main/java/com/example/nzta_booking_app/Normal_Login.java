package com.example.nzta_booking_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Normal_Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.normal_login);
    }

    public void landingPage(View view) {
        Intent lpIntent = new Intent(this, Landing_Page.class);
        startActivity(lpIntent);
    }

    public void normalHome(View view) {
        Intent nhIntent = new Intent(this, Normal_Home.class);
        startActivity(nhIntent);
    }

    public void normalRegister(View view) {
        Intent nrIntent = new Intent(this, Normal_Registration.class);
        startActivity(nrIntent);
    }

    public void instructorLogin(View view) {
        Intent ilIntent = new Intent(this, Instructor_Login.class);
        startActivity(ilIntent);
    }

    public void instructorRegister(View view) {
        Intent irIntent = new Intent(this, Instructor_Registration.class);
        startActivity(irIntent);
    }
}