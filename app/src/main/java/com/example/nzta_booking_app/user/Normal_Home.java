package com.example.nzta_booking_app.user;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
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

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.normal_home);
        wMessage = findViewById(R.id.welMessage);
        wMessage.setText("Kia Ora, " + Controller.getCurrentUser().userFullName());
    }

    //opens a manage booking intent
    public void mBooking(View view) {
        Intent bIntent = new Intent(this, Manage_Booking.class);
        startActivity(bIntent);
    }

    //opens up the graph and passes a usertype
    public void graph(View view) {
        Intent bIntent = new Intent(this, Histogram.class);
        bIntent.putExtra("userType", "user");
        startActivity(bIntent);
    }

    //signs out the user after confirming the alert dialog
    public void logout(View view) {
        new AlertDialog.Builder(Normal_Home.this)
                .setTitle("Exit Confirmation")
                .setMessage("Are you sure you want to log out?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent nlIntent = new Intent(Normal_Home.this, Landing_Page.class);
                        startActivity(nlIntent);
                        finishAffinity();
                        FirebaseAuth.getInstance().signOut();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).show();

    }
}