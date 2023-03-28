package com.example.nzta_booking_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Manage_Booking extends AppCompatActivity {

    RecyclerView rv;
    RecyclerView.Adapter rv_adapter;
    RecyclerView.LayoutManager rv_lm;

    String [] dates= {"20/01/2023", "23/01/2023", "24/01/2023", "25/01/2023", "26/01/2023", "27/01/2023", "28/01/2023", "31/01/2023", "01/02/2023", "02/02/2023", "03/04/2023"};
    String [] time= {"9:00", "11:00", "9:00", "9:30", "9:00", "9:00", "12:00", "9:00", "13:00", "9:00", "9:00"};
    String [] status= {"Fail", "Fail", "Fail", "Fail", "Fail", "Fail", "Fail", "Fail", "Fail", "Fail", "Booked"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_booking);
        rv = findViewById(R.id.recyclerBook);
        rv.setHasFixedSize(true);
        rv_lm = new LinearLayoutManager(this);
        rv.setLayoutManager(rv_lm);
        rv_adapter = new HistoryAdapter(this, dates, time, status);
        rv.setAdapter(rv_adapter);

    }
    public void bookTest(View view) {
        Intent bIntent = new Intent(this, Booking_Date_Selection.class);
        startActivity(bIntent);
    }
}