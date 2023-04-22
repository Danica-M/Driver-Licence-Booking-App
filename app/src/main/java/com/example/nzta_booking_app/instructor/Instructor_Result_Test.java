package com.example.nzta_booking_app.instructor;

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
import android.widget.Toast;

import com.example.nzta_booking_app.R;
import com.example.nzta_booking_app.models.Booking;
import com.example.nzta_booking_app.models.Controller;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class Instructor_Result_Test extends AppCompatActivity {

    String bookingID, instructor;
    TextView testDate, testTime, testApplicant, testLicence;
    RadioGroup results;
    RadioButton pass, fail;
    Button saveBtn, cancelBtn;
    EditText testComment;

    Controller controller;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.instructor_result_test);
        controller = new Controller();

        Intent intent = getIntent();
        bookingID = intent.getStringExtra("bookingID");
        instructor = intent.getStringExtra("instructor");

        testDate = findViewById(R.id.resDate);
        testTime = findViewById(R.id.resTime);
        testApplicant = findViewById(R.id.resApplicant);
        testLicence = findViewById(R.id.resDl);
        results = findViewById(R.id.radioResult);
        saveBtn = findViewById(R.id.saveRes);
        cancelBtn = findViewById(R.id.cancelRes);
        testComment = findViewById(R.id.resComment);
        pass = findViewById(R.id.radioPass);
        fail = findViewById(R.id.radioFail);
        getBooking();
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (results.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(Instructor_Result_Test.this, "Please select test result.", Toast.LENGTH_SHORT).show();
                } else {
                    RadioButton radioButton = findViewById(results.getCheckedRadioButtonId());
                    String testResult = radioButton.getText().toString();
                    Booking booking = controller.resultTest(bookingID, testDate.getText().toString(), testTime.getText().toString(), testApplicant.getText().toString(), testLicence.getText().toString(), instructor, true, testResult, testComment.getText().toString());
                    if(booking!=null){
                        Toast.makeText(Instructor_Result_Test.this, "Test Resulted Successfully", Toast.LENGTH_SHORT).show();
                        Intent nlIntent = new Intent(Instructor_Result_Test.this, Instructor_Home.class);
                        startActivity(nlIntent);
                        finishAffinity();
                    }else{
                        Toast.makeText(Instructor_Result_Test.this, "Something went wrong while trying to save the result.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    public void getBooking() {
        DatabaseReference bookingsRef = Controller.getReference().child("bookings").child(bookingID);
        bookingsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Booking booking = snapshot.getValue(Booking.class);
                if (booking != null) {
                    testDate.setText(booking.getBookingDate());
                    testTime.setText(booking.getBookingTime());
                    testApplicant.setText(booking.getBookingUser());
                    testLicence.setText(booking.getBookingUserDL());
                    if (booking.getResulted()) {
                        if (booking.getResults().equals("passed")) {
                            pass.setChecked(true);
                        } else {
                            fail.setChecked(true);
                        }
                        testComment.setText(booking.getComments());
                    } else {
                        results.clearCheck();
                    }
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Error Occurred: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void CancelResult(View view) {
        new AlertDialog.Builder(Instructor_Result_Test.this)
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
                    public void onClick(DialogInterface dialogInterface, int i) {}
                }).show();

    }
}