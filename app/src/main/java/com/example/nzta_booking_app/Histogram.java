package com.example.nzta_booking_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.nzta_booking_app.instructor.Instructor_Home;
import com.example.nzta_booking_app.models.Booking;
import com.example.nzta_booking_app.user.Normal_Home;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Histogram extends AppCompatActivity implements View.OnClickListener {
    private BarChart barChart;
    PieChart pieChart;
    String userType;
    ArrayList<String> labels, pieLabels;
    SimpleDateFormat sdf;
    Calendar calendar;
    int currentWeek;
    Button button1, button2 ,button3 ,button4 ,button5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.histogram);

        Intent intent = getIntent();
        userType = intent.getStringExtra("userType");

        button1 = findViewById(R.id.btn0);
        button2 = findViewById(R.id.btn1);
        button3 = findViewById(R.id.btn2);
        button4 = findViewById(R.id.btn3);
        button5 = findViewById(R.id.btn4);

        // set onClickListener for each button
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);


        // Initialize BarChart view
        barChart = findViewById(R.id.bar_chart);
        pieChart = findViewById(R.id.pie_chart);
        labels = new ArrayList<>();
        pieLabels = new ArrayList<>();
        calendar = Calendar.getInstance();
        currentWeek = calendar.get(Calendar.WEEK_OF_YEAR);
        sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

        loadLabel();
        loadBarChart();

        barChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                // Get the index of the selected bar
                int index = (int) e.getX();
                // Get the date from the labels array using the index
                String date = labels.get(index);
                // Do something with the selected date
                Toast.makeText(getApplicationContext(), "Selected date: " + date, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected() {
                // Do nothing
            }
        });
    }

    public void nhome(View view) {
        if(userType.equals("user")){
            Intent bIntent = new Intent(this, Normal_Home.class);
            startActivity(bIntent);
        }else{
            Intent bIntent = new Intent(this, Instructor_Home.class);
            startActivity(bIntent);
        }

    }


    public void loadBarChart(){
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("bookings");
        databaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<BarEntry> entries = new ArrayList<>();
                Calendar calendar2 = Calendar.getInstance();

                int[] weeklyBookings = new int[5];


                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    Booking booking = childSnapshot.getValue(Booking.class);
                    if (booking != null) {
//                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM", Locale.getDefault());

                        try {
                            String bookingDate = booking.getBookingDate();
                            Date date1 = sdf.parse(bookingDate);
                            calendar.setTime(date1);

                            int bookingWeekNumber = calendar.get(Calendar.WEEK_OF_YEAR);
                            Log.d("TAG", "c_week: "+calendar.get(Calendar.WEEK_OF_YEAR));
                            if (bookingWeekNumber == currentWeek) {
                                int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

                                if (dayOfWeek == 2 || dayOfWeek == 3 ||dayOfWeek == 4 ||dayOfWeek == 5 ||dayOfWeek == 6) {
                                    weeklyBookings[dayOfWeek - 2]++;
                                  Log.d("TAG", "week length: "+weeklyBookings.length);
                                }
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                } for (int i = 0; i < 5; i++) {
                    entries.add(new BarEntry(i, weeklyBookings[i]));
                }

                // Create a dataset and set it to the BarChart
                BarDataSet dataSet = new BarDataSet(entries, "Weekly Booking");
                BarData barData = new BarData(dataSet);
                barChart.setData(barData);
                barChart.getLegend().setEnabled(false);

                // Customize the chart
                dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                dataSet.setValueTextColor(Color.WHITE);
                barChart.getDescription().setEnabled(false);
                barChart.setFitBars(true);
                barChart.animateY(1000);

                // Set the X-axis labels to show weekdays
                XAxis xAxis = barChart.getXAxis();
                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                xAxis.setGranularity(1f);
                xAxis.setTextColor(Color.WHITE);
                xAxis.setValueFormatter(new ValueFormatter() {
                    @Override

                    public String getFormattedValue(float value) {
                        return labels.get((int)value);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Histogram.this, "Failed to read data from Firebase", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void loadLabel(){
        for (int dayOfWeek = Calendar.MONDAY; dayOfWeek <= Calendar.FRIDAY; dayOfWeek++) {
            calendar.set(Calendar.DAY_OF_WEEK, dayOfWeek);
            int weekNumber = calendar.get(Calendar.WEEK_OF_YEAR);
            if (weekNumber == currentWeek) {

                String date = sdf.format(calendar.getTime());
                Log.d("TAG", "date: "+date);
                labels.add(date);
            }
        }
    }

    public void onClick(View v) {
        if (v instanceof Button) {
            String buttonText = ((Button) v).getText().toString();
            String date;
            switch (buttonText) {
                case "Mon":
                    date = labels.get(0);
                    break;
                case "Tue":
                    date = labels.get(1);
                    break;
                case "Wed":
                    date = labels.get(2);
                    break;
                case "Thu":
                    date = labels.get(3);
                    break;
                case "Fri":
                    date = labels.get(4);
                    break;
                default:
                    date = "";
                    break;
            }
            loadPieLabel();
            loadPieChart(date);
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

    public void loadPieLabel(){
        for(int i=9; i<17; i++){
            String time = String.format(Locale.getDefault(),"%02d:00", i);
            String time1 = String.format(Locale.getDefault(), "%02d:30", i);
            pieLabels.add(time);
            pieLabels.add(time1);
        }
        Log.d("TAG","pieL: "+pieLabels.size());
    }


    public void loadPieChart(String date){
        Map<String, Integer> bookingData = new HashMap<>();


        // Query the Firebase database for the booking data
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference bookingsRef = database.getReference("bookings");
        Query query = bookingsRef.orderByChild("bookingDate");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Iterate through the bookings for the specified date and populate the booking data HashMap
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    Booking booking = childSnapshot.getValue(Booking.class);
                    if (booking != null && booking.getBookingDate().equals(date)) {
                        String bookingTime = booking.getBookingTime();
                        int count = bookingData.getOrDefault(bookingTime, 0);
                        bookingData.put(bookingTime, count + 1);
                    }
                }

                // Create a list of PieEntries to hold the booking data
                List<PieEntry> entries = new ArrayList<>();
                for (String bookingTime : pieLabels) {
                    int count = bookingData.getOrDefault(bookingTime, 0);
                    entries.add(new PieEntry(count, bookingTime));
                }

                // Create a PieDataSet and set its properties
                PieDataSet dataSet = new PieDataSet(entries, "Booking Data");
                dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                dataSet.setValueTextSize(14f);
//                pieChart.getLegend().setEnabled(false);

                // Create a PieData and set it to the PieChart
                PieData data = new PieData(dataSet);
                pieChart.setData(data);

                // Customize the PieChart
                pieChart.getDescription().setEnabled(false);
                pieChart.setUsePercentValues(true);
                pieChart.animateY(1000);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle the error
            }
        });
    }


}