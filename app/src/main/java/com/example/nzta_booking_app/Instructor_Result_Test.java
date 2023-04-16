package com.example.nzta_booking_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.nzta_booking_app.adapters.TestAdapter;
import com.example.nzta_booking_app.instructor.Instructor_Home;
import com.example.nzta_booking_app.models.Booking;
import com.example.nzta_booking_app.models.Controller;
import com.example.nzta_booking_app.user.Booking_Date_Selection;
import com.example.nzta_booking_app.user.Manage_Booking;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Instructor_Result_Test extends AppCompatActivity {

    String bookingID;
    TextView testDate, testTime, testApplicant;
    RadioGroup results;
    RadioButton pass,fail;
    Button savebtn, cancelBtn;
    EditText testComment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.instructor_result_test);
        Intent intent = getIntent();
        bookingID = intent.getStringExtra("bookingID");

        testDate = findViewById(R.id.resDate);
        testTime = findViewById(R.id.resTime);
        testApplicant = findViewById(R.id.resApplicant);
        results = findViewById(R.id.radioResult);
        savebtn = findViewById(R.id.saveRes);
        cancelBtn = findViewById(R.id.cancelRes);
        testComment = findViewById(R.id.resComment);
        pass = findViewById(R.id.radioPass);
        fail = findViewById(R.id.radioFail);
        getBooking();



    }


    public void getBooking(){

        DatabaseReference bookingsRef = FirebaseDatabase.getInstance().getReference().child("bookings").child(bookingID);
        bookingsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot!=null){
                    Booking booking = snapshot.getValue(Booking.class);
                    testDate.setText("Date: "+booking.getBookingDate());
                    testTime.setText("Time: "+booking.getBookingTime());
                    testApplicant.setText("Applicant: "+booking.getBookingUser());
                    if(booking.getResulted()){
                        if(booking.getResults().equals("passed")){pass.setChecked(true);}
                        else{fail.setChecked(true);}
                        testComment.setText(booking.getComments());
                    }else{
                        results.clearCheck();
                    }
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void CancelResult(View view){
        AlertDialog builder = new AlertDialog.Builder(Instructor_Result_Test.this)
                .setTitle("Result Cancellation Confirmation")
                .setMessage("Are you sure you want to cancel resulting this test?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent nlIntent = new Intent(Instructor_Result_Test.this, Instructor_Home.class);
                        startActivity(nlIntent);
                        finishAffinity();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).show();

    }
}