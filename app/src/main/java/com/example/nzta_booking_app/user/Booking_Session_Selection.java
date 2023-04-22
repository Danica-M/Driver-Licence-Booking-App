package com.example.nzta_booking_app.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.AdapterView;

import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.nzta_booking_app.R;
import com.example.nzta_booking_app.adapters.ItemClickListener;
import com.example.nzta_booking_app.adapters.SessionAdapter;
import com.example.nzta_booking_app.models.Controller;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;


public class Booking_Session_Selection extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner spin;
    String selectedDate, selectedTime, selectedInstructor;
    ArrayList<String> instructorNames;
    RecyclerView rv;
    ItemClickListener itemClickListener;
    SessionAdapter adapter;
    DatabaseReference reference;
    Controller controller;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.booking_session_selection);

        controller = new Controller();

        instructorNames = Controller.getInstructorsNames();

        instructorNames.add("Select Instructor");

        reference = FirebaseDatabase.getInstance().getReference();

        Intent intent = getIntent();
        selectedDate = intent.getStringExtra("date");

        spin = findViewById(R.id.spinner);

        spin.setOnItemSelectedListener(this);
        ArrayAdapter<String> aa = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, instructorNames);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(aa);

        rv = findViewById(R.id.recyclerSession);
        selectedInstructor = " ";
        setRecyclerview();
    }


    //on item selected for spinner. set the session recycler view everytime selected instructor changes
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        selectedInstructor = instructorNames.get(i);
        if (instructorNames.get(i).equals("Select Instructor")) {
            selectedTime = null;
        } else {
            getTakenSlots(selectedDate, selectedInstructor);
            setRecyclerview();
        }
    }
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    // if instructor and session are not selected , it will show error message to the user
    public void nextReview(View view) {
        if (selectedTime == null || selectedInstructor.equals("Select Instructor")) {
            Toast.makeText(this, "Please select instructor and time slot to continue.", Toast.LENGTH_SHORT).show();
        } else {
            Intent reviewIntent = new Intent(Booking_Session_Selection.this, Booking_Review.class);
            reviewIntent.putExtra("date", selectedDate);
            reviewIntent.putExtra("time", selectedTime);
            reviewIntent.putExtra("instructor", selectedInstructor);
            startActivity(reviewIntent);
        }

    }


    //gets all the booking of the instructor and specific date
    public ArrayList<String> getTakenSlots(String date, String instructor) {
        DatabaseReference bookingsRef = reference.child("bookings");
        final ArrayList<String> takenSlots = new ArrayList<>();
        bookingsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot bookingSnapshot : dataSnapshot.getChildren()) {
                    if (Objects.requireNonNull(bookingSnapshot.child("bookingDate").getValue(String.class)).equals(date)
                            && Objects.equals(bookingSnapshot.child("bookingInstructor").getValue(String.class), instructor)) {
                        String booking = bookingSnapshot.child("bookingTime").getValue(String.class);
                        takenSlots.add(booking);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Error Occurred: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        return takenSlots;
    }



    //sets the session recycler view
    public void setRecyclerview() {
        rv.setHasFixedSize(true);
        itemClickListener = new ItemClickListener() {
            @Override
            public void onClick(String s) {
                rv.post(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                    }
                });
                selectedTime = s;
            }
        };
        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SessionAdapter(this, Controller.getBookingSlots(), getTakenSlots(selectedDate, selectedInstructor), itemClickListener);
        rv.setAdapter(adapter);
        rv.setVisibility(View.VISIBLE);

    }

    // opens the date selection activity
    public void backToDate(View view) {
        Intent intent = new Intent(Booking_Session_Selection.this, Booking_Date_Selection.class);
        startActivity(intent);

    }

}