package com.example.nzta_booking_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.nzta_booking_app.models.Controller;
import com.example.nzta_booking_app.models.Instructor;
import com.google.firebase.auth.FirebaseAuth;


public class Instructor_Home extends AppCompatActivity {
    TextView wMessage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.instructor_home);

        wMessage = findViewById(R.id.i_welMessage);
        wMessage.setText("Kia Ora, Instructor "+ Controller.getCurrentInstructor().instructorFullName());
    }
    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        Intent bIntent = new Intent(this, Landing_Page.class);
        startActivity(bIntent);
    }
}