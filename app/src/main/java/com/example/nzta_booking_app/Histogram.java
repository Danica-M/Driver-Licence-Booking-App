package com.example.nzta_booking_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nzta_booking_app.instructor.Instructor_Home;
import com.example.nzta_booking_app.models.Booking;
import com.example.nzta_booking_app.models.Instructor;
import com.example.nzta_booking_app.user.Normal_Home;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class Histogram extends AppCompatActivity implements View.OnClickListener {
    private BarChart weeklyBarChart, hourlyBarChart;

    private String userType;
    private ArrayList<String> weekLabels, hourLabels;
    private SimpleDateFormat sdf;
    private Calendar calendar;

    private TextView tvHour;
    private LinearLayout hourSum;
    int currentWeek, numberOfInstructor;

    private DatabaseReference reference;
    public Button button1, button2, button3, button4, button5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.histogram);
        reference = FirebaseDatabase.getInstance().getReference();

        Intent intent = getIntent();
        userType = intent.getStringExtra("userType");

        hourSum = findViewById(R.id.hourSummary);
        tvHour = findViewById(R.id.tvHourly);
        button1 = findViewById(R.id.btn0);
        button2 = findViewById(R.id.btn1);
        button3 = findViewById(R.id.btn2);
        button4 = findViewById(R.id.btn3);
        button5 = findViewById(R.id.btn4);

        // Initialize BarChart view
        weeklyBarChart = findViewById(R.id.weekBarChart);
        hourlyBarChart = findViewById(R.id.hourBarChart);

        // set onClickListener for each button
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);



        weekLabels = new ArrayList<>();

        calendar = Calendar.getInstance();
        currentWeek = calendar.get(Calendar.WEEK_OF_YEAR);
        sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

        loadWeekLabel();
        loadWeeklyBarChart();
    }

    public void nHome(View view) {
        Intent bIntent;
        if (userType.equals("user")) bIntent = new Intent(this, Normal_Home.class);
        else {bIntent = new Intent(this, Instructor_Home.class);}
        startActivity(bIntent);
    }


    //loads the total number of the booked test for each day in the current week into a barchart
    public void loadWeeklyBarChart() {
        getInstructorsNumber();
        DatabaseReference databaseRef = reference.child("bookings");
        databaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<BarEntry> entries = new ArrayList<>();
                int[] weeklyBookings = new int[5];
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    Booking booking = childSnapshot.getValue(Booking.class);
                    if (booking != null) {
                        try {
                            String bookingDate = booking.getBookingDate();
                            Date date1 = sdf.parse(bookingDate);
                            calendar.setTime(date1);
                            int bookingWeekNumber = calendar.get(Calendar.WEEK_OF_YEAR);
                            if (bookingWeekNumber == currentWeek) {
                                int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
                                if (dayOfWeek == 2 || dayOfWeek == 3 || dayOfWeek == 4 || dayOfWeek == 5 || dayOfWeek == 6) {
                                    weeklyBookings[dayOfWeek - 2]++;
                                }
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }
                for (int i = 0; i < 5; i++) {
                    entries.add(new BarEntry(i, weeklyBookings[i]));
                }

                // Create a dataset and set it to the BarChart
                BarDataSet dataSet = new BarDataSet(entries, "Weekly Booking");
                BarData barData = new BarData(dataSet);
                weeklyBarChart.setData(barData);
                weeklyBarChart.getLegend().setEnabled(false);


                // Customize the chart
                ArrayList<Integer> colorPalettes = new ArrayList<>();
                for (int bookings : weeklyBookings) {
                    int totalSlots = numberOfInstructor * 16;
                    if (bookings < (totalSlots / 4)) {
                        colorPalettes.add(Color.GREEN);
                    } else if (bookings >= (totalSlots / 4) && bookings < (totalSlots / 2)) {
                        colorPalettes.add(Color.YELLOW);
                    } else {
                        colorPalettes.add(Color.RED);
                    }
                }
                dataSet.setColors(colorPalettes);
                dataSet.setValueTextColor(Color.WHITE);
                weeklyBarChart.getDescription().setEnabled(false);
                weeklyBarChart.setFitBars(true);
                weeklyBarChart.animateY(1000);

                // Set the X-axis labels to show weekdays
                XAxis xAxis = weeklyBarChart.getXAxis();
                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                xAxis.setGranularity(1f);
                xAxis.setTextColor(Color.WHITE);
                xAxis.setValueFormatter(new ValueFormatter() {
                    @Override

                    public String getFormattedValue(float value) {
                        return weekLabels.get((int) value);
                    }
                });
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Error Occurred: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // date labels for the weekly barchart
    public void loadWeekLabel() {
        for (int dayOfWeek = Calendar.MONDAY; dayOfWeek <= Calendar.FRIDAY; dayOfWeek++) {
            calendar.set(Calendar.DAY_OF_WEEK, dayOfWeek);
            int weekNumber = calendar.get(Calendar.WEEK_OF_YEAR);
            if (weekNumber == currentWeek) {
                String date = sdf.format(calendar.getTime());
                weekLabels.add(date);
            }
        }
    }

    //adding on click event listerner to all the button in the histogram
    public void onClick(View v) {
        if (v instanceof Button) {
            String buttonText = ((Button) v).getText().toString();
            loadHourlyLabels();
            String date;
            switch (buttonText) {
                case "MON":
                    date = weekLabels.get(0);
                    break;
                case "TUE":
                    date = weekLabels.get(1);
                    break;
                case "WED":
                    date = weekLabels.get(2);
                    break;
                case "THU":
                    date = weekLabels.get(3);
                    break;
                case "FRI":
                    date = weekLabels.get(4);
                    break;
                default:
                    date = "";
                    break;
            }
            loadHourlyBarChart(date);
            hourSum.setVisibility(View.VISIBLE);
            tvHour.setText("Hourly Bookings - " + date);
        }
    }

    // gets the number of instructor to calculate the total number of possible test slots
    // calculation = number of instructor * 16 (daily total time slots)
    public void getInstructorsNumber() {
        numberOfInstructor = 0;
        reference.child("instructors").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot instructorSnapshot : dataSnapshot.getChildren()) {
                    Instructor instructor = instructorSnapshot.getValue(Instructor.class);
                    if (instructor != null) {
                        numberOfInstructor++;
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "Error Occurred: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    // loads the label for the timeslots
    //9-5 with 30 mins interval
    public void loadHourlyLabels() {
        hourLabels = new ArrayList<>();
        for (int i = 9; i < 17; i++) {
            String time = String.format(Locale.getDefault(), "%02d:00", i);
            String time1 = String.format(Locale.getDefault(), "%02d:30", i);
            hourLabels.add(time);
            hourLabels.add(time1);
        }
    }

    //loads the hourly number of booking of the specific date into a barchart.
    //triggered by a button
    public void loadHourlyBarChart(String date) {
        DatabaseReference databaseRef = reference.child("bookings");
        databaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<BarEntry> entries = new ArrayList<>();
                int[] hourlyBooking = new int[16];
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    Booking booking = childSnapshot.getValue(Booking.class);
                    if (booking != null && booking.getBookingDate().equals(date)) {
                        int index = hourLabels.indexOf(booking.getBookingTime());
                        hourlyBooking[index]++;
                    }
                }
                for (int i = 0; i < 16; i++) {
                    entries.add(new BarEntry(i, hourlyBooking[i]));
                }

                // Create a dataset and set it to the BarChart
                BarDataSet dataSet = new BarDataSet(entries, "Weekly Booking");
                BarData barData = new BarData(dataSet);
                hourlyBarChart.setData(barData);
                hourlyBarChart.getLegend().setEnabled(false);

                // Customize the chart

                dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                dataSet.setValueTextColor(Color.WHITE);
                hourlyBarChart.getDescription().setEnabled(false);
                hourlyBarChart.setFitBars(true);
                hourlyBarChart.animateY(1000);

                // Set the X-axis labels to show weekdays
                XAxis xAxis = hourlyBarChart.getXAxis();
                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                xAxis.setGranularity(1f);
                xAxis.setTextColor(Color.WHITE);
                xAxis.setValueFormatter(new ValueFormatter() {
                    @Override

                    public String getFormattedValue(float value) {
                        return hourLabels.get((int) value);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Error Occurred: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}