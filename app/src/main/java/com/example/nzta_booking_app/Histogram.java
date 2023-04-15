package com.example.nzta_booking_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.nzta_booking_app.instructor.Instructor_Home;
import com.example.nzta_booking_app.models.Booking;
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

public class Histogram extends AppCompatActivity {
    private BarChart barChart;
    String userType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.histogram);

        Intent intent = getIntent();
        userType = intent.getStringExtra("userType");


        // Initialize BarChart view
        barChart = findViewById(R.id.bar_chart);

        // Get weekly data from Firebase
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("bookings");
        databaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<BarEntry> entries = new ArrayList<>();

                int[] weeklyBookings = new int[5];
                Calendar calendar = Calendar.getInstance();
                int currentWeek = calendar.get(Calendar.WEEK_OF_YEAR);
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    Booking booking = childSnapshot.getValue(Booking.class);
                    if (booking != null) {
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

                        try {
                            String bookingDate = booking.getBookingDate();
                            Date date = sdf.parse(bookingDate);
                            long dateInMillis = date.getTime();
                            calendar.setTimeInMillis(dateInMillis);

                            int bookingWeekNumber = calendar.get(Calendar.WEEK_OF_YEAR);
                            if (bookingWeekNumber == currentWeek) {
                                int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
                                if (dayOfWeek == 2 || dayOfWeek == 3 ||dayOfWeek == 4 ||dayOfWeek == 5 ||dayOfWeek == 6) {
                                    weeklyBookings[dayOfWeek - 2]++;
                                }
                            }
                            // do something with the timeInMillis value
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
                xAxis.setValueFormatter(new ValueFormatter() {
                    @Override
                    public String getFormattedValue(float value) {
                        switch ((int) value) {
                            case 0:
                                return "Mon";
                            case 1:
                                return "Tue";
                            case 2:
                                return "Wed";
                            case 3:
                                return "Thu";
                            case 4:
                                return "Fri";
                            default:
                                return "";
                        }
                    }
                });

            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Histogram.this, "Failed to read data from Firebase", Toast.LENGTH_SHORT).show();
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
}