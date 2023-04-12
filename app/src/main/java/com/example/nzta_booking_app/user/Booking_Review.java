package com.example.nzta_booking_app.user;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nzta_booking_app.R;
import com.example.nzta_booking_app.models.Booking;
import com.example.nzta_booking_app.models.Controller;
import com.example.nzta_booking_app.models.User;


public class Booking_Review extends AppCompatActivity {

    TextView tx_date, tx_time, tx_instructor;
    String bookingUser,date,time,instructor;
    Button bookButton, backButton;
    Intent rIntent;
    Controller controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.booking_review);
        controller = new Controller();
        tx_date = findViewById(R.id.reviewDate);
        tx_time = findViewById(R.id.reviewTime);
        tx_instructor = findViewById(R.id.reviewInstruct);
        backButton = findViewById(R.id.backBtn);
        bookButton =findViewById(R.id.bookBtn);

        User bUser= Controller.getCurrentUser();
        rIntent = getIntent();
        date=rIntent.getStringExtra("date");
        time=rIntent.getStringExtra("time");
        instructor=rIntent.getStringExtra("instructor");
        bookingUser = bUser.userFullName();



        tx_date.setText(date);
        tx_time.setText(time);
        tx_instructor.setText(instructor);


        bookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    Log.d("TAG", "user: " + Controller.getCurrentUser().userFullName());
                    Booking booking = controller.bookTest(date, time, bookingUser, instructor);
                    if (booking != null) {
                        AlertDialog builder = new AlertDialog.Builder(Booking_Review.this)
                                .setTitle("Booking Confirmation")
                                .setMessage("Your test has been booked successfully!")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent nlIntent = new Intent(Booking_Review.this, Manage_Booking.class);
                                        startActivity(nlIntent);
                                        finishAffinity();
                                    }
                                }).show();


                    } else {
                        Toast.makeText(Booking_Review.this, "Something went wrong will booking the test", Toast.LENGTH_SHORT).show();
                    }

                }catch (Exception ex){
                    Toast.makeText(Booking_Review.this, "Error Occurred: "+ ex.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.d("TAG", "error: " +ex.getMessage());
                }
            }
        });
    }




}