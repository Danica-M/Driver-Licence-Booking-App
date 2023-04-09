package com.example.nzta_booking_app;

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

import com.example.nzta_booking_app.adapters.SessionAdapter;

public class Booking_Session_Selection extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner spin;
//    RadioGroup radioGroup;
    private String[] samples = {"Danica", "Diane", "Jim"};

    RecyclerView rv;
    RecyclerView.Adapter rv_adapter;
    RecyclerView.LayoutManager rv_lm;


    String [] unavailable_slots= {"9:30", "10:00", "10:30", "11:00"};
    String [] slots= {"9:00", "9:30", "10:00", "10:30", "11:00", "11:30", "12:00", "12:30", "13:00", "13:30", "14:00", "14:30", "15:00", "15:30", "16:00", "16:30"};
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.booking_session_selection);

        spin=findViewById(R.id.spinner);
        spin.setOnItemSelectedListener(this);;
        ArrayAdapter<String> aa = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, samples);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(aa);

        rv = findViewById(R.id.recyclerSession);
        rv.setHasFixedSize(true);
        rv_lm = new LinearLayoutManager(this);
        rv.setLayoutManager(rv_lm);
        rv_adapter = new SessionAdapter(this, slots, unavailable_slots);
        rv.setAdapter(rv_adapter);

//        radioGroup = findViewById(R.id.slotsGroup);
//        radioGroup.setOnCheckedChangeListener(this);
//        String[] disabledOptions = {"9:00", "12:00", "14:00"};
//
//        for (int i = 0; i < radioGroup.getChildCount(); i++) {
//            View view = radioGroup.getChildAt(i);
//            if (view instanceof RadioButton) {
//                RadioButton radioButton = (RadioButton) view;
//                String radioText = radioButton.getText().toString();
//                if (Arrays.asList(disabledOptions).contains(radioText)) {
//                    radioButton.setEnabled(false);
//                }
//            }
//        }
//
//        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
//                // Get the selected radio button and do something with it
//                RadioButton radioButton = findViewById(checkedId);
//                Toast.makeText(getApplicationContext(), "Selected: " + radioButton.getText().toString(), Toast.LENGTH_SHORT).show();
//            }
//        });





    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Toast.makeText(this,samples[i],Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void bookingRev(View view) {
        Intent nlIntent = new Intent(this, Booking_Review.class);
        startActivity(nlIntent);
    }

//    @Override
//    public void onCheckedChanged(RadioGroup radioGroup, int i) {
//        RadioButton radioButton = findViewById(i);
//        Toast.makeText(getApplicationContext(), "Selected: "+ radioButton.getText(), Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    public void onPointerCaptureChanged(boolean hasCapture) {
//        super.onPointerCaptureChanged(hasCapture);
//    }
}