package com.example.nzta_booking_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.nzta_booking_app.models.User;
import com.google.firebase.auth.FirebaseAuth;

public class Normal_Home extends AppCompatActivity {
    TextView wMessage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.normal_home);
        User user = (User) getIntent().getSerializableExtra("user");
        String fName = user.getUserFName();
        String lName = user.getUserLName();
        String userID = user.getUserID();
        wMessage = findViewById(R.id.welMessage);
        wMessage.setText("Kia Ora, "+fName+" "+lName);


    }
    public void mBooking(View view) {
        Intent bIntent = new Intent(this, Manage_Booking.class);
        startActivity(bIntent);
    }

    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        Intent bIntent = new Intent(this, Landing_Page.class);
        startActivity(bIntent);
    }
}