package com.example.nzta_booking_app.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.nzta_booking_app.R;
import com.example.nzta_booking_app.adapters.ItemClickListener;
import com.example.nzta_booking_app.adapters.SessionAdapter;
import com.example.nzta_booking_app.models.Instructor;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Locale;

public class Booking_Session_Selection extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    Spinner spin;
    String selectedDate, selectedTime, selectedInstructor, bookingUser;
    ArrayList<String> instructorNames;
    RecyclerView rv;
    ItemClickListener itemClickListener;
    SessionAdapter adapter;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.booking_session_selection);

        instructorNames = new ArrayList<>();
        instructorNames.add("Select Instructor");

        FirebaseDatabase firebaseDB = FirebaseDatabase.getInstance();
        DatabaseReference reference = firebaseDB.getReference();

        Intent intent = getIntent();
        selectedDate = intent.getStringExtra("date");

        spin=findViewById(R.id.spinner);
        getInstructorsNames(reference);
        spin.setOnItemSelectedListener(this);




        rv = findViewById(R.id.recyclerSession);
//        rv.setHasFixedSize(true);
//        rv.setLayoutManager(new LinearLayoutManager(this));
//        adapter = new SessionAdapter(this,  getBookingSlots(),getTakenSlots(selectedDate," "), itemClickListener);
//        rv.setAdapter(adapter);
//        rv.setVisibility(View.INVISIBLE);
        Log.d("TAG", "Received message: " + selectedDate);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        selectedInstructor = instructorNames.get(i);
        if(instructorNames.get(i).equals("Select Instructor")){
//            rv.setVisibility(View.INVISIBLE);
            selectedTime = null;
        }else {
            setRecyclerview();


            Log.d("TAG","taken slots: "+ getTakenSlots(selectedDate,selectedInstructor).toString());
        }
        Toast.makeText(this,instructorNames.get(i),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void nextReview(View view) {

        if(selectedTime == null){
            Toast.makeText(this,"Please select instructor and time slot to continue.",Toast.LENGTH_SHORT).show();
        }else{
            Intent reviewIntent =new Intent(Booking_Session_Selection.this, Booking_Review.class);
            reviewIntent.putExtra("date",selectedDate);
            reviewIntent.putExtra("time",selectedTime);
            reviewIntent.putExtra("instructor",selectedInstructor);
            startActivity(reviewIntent);
        }

    }

    public ArrayList<String> getBookingSlots(){
        ArrayList<String> bookingSlots = new ArrayList<>();
        for(int i=9; i<=17; i++){
            String time = String.format(Locale.getDefault(),"%02d:00", i);
            String time1 = String.format(Locale.getDefault(), "%02d:30", i);
            bookingSlots.add(time);
            bookingSlots.add(time1);
        }
        return bookingSlots;
    }

    public ArrayList<String> getTakenSlots(String date, String instructor) {
        DatabaseReference bookingsRef = FirebaseDatabase.getInstance().getReference().child("bookings");
        final ArrayList<String> takenSlots = new ArrayList<>();
        bookingsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot bookingSnapshot : dataSnapshot.getChildren()) {
                    if(bookingSnapshot.child("bookingDate").getValue(String.class).equals(date)
                            && bookingSnapshot.child("bookingInstructor").getValue(String.class).equals(instructor)){
                        String booking = bookingSnapshot.child("bookingTime").getValue(String.class);
                        takenSlots.add(booking);
                        Log.d("TAG","taken slots: "+ takenSlots.size());
                    }

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {Toast.makeText(getApplicationContext(),"Error Occurred: "+error.getMessage(),Toast.LENGTH_SHORT).show();}
        });
        return takenSlots;
    }

    public void getInstructorsNames(DatabaseReference databaseReference) {
        databaseReference.child("instructors").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot instructorSnapshot : dataSnapshot.getChildren()) {
                    Instructor instructor = instructorSnapshot.getValue(Instructor.class);
                    instructorNames.add(instructor.instructorFullName());
                    Log.d("TAG","instructor name:" +instructor.instructorFullName());
                }
                ArrayAdapter<String> aa = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, instructorNames);
                aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spin.setAdapter(aa);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "Error Occurred: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setRecyclerview(){
//        rv = findViewById(R.id.recyclerSession);
        rv.setHasFixedSize(true);
        itemClickListener = s -> {
            rv.post(() -> adapter.notifyDataSetChanged() );
            selectedTime = s;
            Log.d("TAG", "Session: " + s);
            Log.d("TAG","taken slots: "+ getTakenSlots(selectedDate,"John Doe").size());
        };
        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SessionAdapter(this,  getBookingSlots(),getTakenSlots(selectedDate,selectedInstructor), itemClickListener);
        rv.setAdapter(adapter);
        rv.setVisibility(View.VISIBLE);
    }

}