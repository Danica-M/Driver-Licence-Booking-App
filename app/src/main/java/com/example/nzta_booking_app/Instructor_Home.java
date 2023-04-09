package com.example.nzta_booking_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.nzta_booking_app.models.Instructor;


public class Instructor_Home extends AppCompatActivity {
    TextView wMessage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.instructor_home);
        Instructor instructor = (Instructor) getIntent().getSerializableExtra("instructor");
        String fName = instructor.getFirstName();
        String lName = instructor.getLastName();
        String instructorID = instructor.getInstructorID();
        wMessage = findViewById(R.id.i_welMessage);
        wMessage.setText("Kia Ora, Instructor "+instructor.getFirstName());
    }
    public void logout(View view) {
        Intent bIntent = new Intent(this, Landing_Page.class);
        startActivity(bIntent);
    }
}