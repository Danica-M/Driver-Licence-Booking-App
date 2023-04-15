package com.example.nzta_booking_app.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.nzta_booking_app.Histogram;
import com.example.nzta_booking_app.Landing_Page;
import com.example.nzta_booking_app.R;
import com.example.nzta_booking_app.models.Controller;
import com.google.firebase.auth.FirebaseAuth;

public class Normal_Home extends AppCompatActivity {
    TextView wMessage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.normal_home);
        wMessage = findViewById(R.id.welMessage);
        wMessage.setText("Kia Ora, "+ Controller.getCurrentUser().userFullName());


    }
    public void mBooking(View view) {
        Intent bIntent = new Intent(this, Manage_Booking.class);
        startActivity(bIntent);
    }

    public void graph(View view) {
        Intent bIntent = new Intent(this, Histogram.class);
        bIntent.putExtra("userType","user");
        startActivity(bIntent);
    }
    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        Intent bIntent = new Intent(this, Landing_Page.class);
        startActivity(bIntent);
    }
}