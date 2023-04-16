package com.example.nzta_booking_app.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nzta_booking_app.Histogram;
import com.example.nzta_booking_app.Instructor_Result_Test;
import com.example.nzta_booking_app.R;
import com.example.nzta_booking_app.models.Booking;

import java.util.ArrayList;

public class TestAdapter extends RecyclerView.Adapter<TestAdapter.ViewHolder> {

    Context context;
    ArrayList<Booking> testList;
    public TestAdapter(Context context, ArrayList<Booking> testList){
        this.context = context;
        this.testList = testList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView test_time, test_result, test_user;
        Button resultBtn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            test_time = itemView.findViewById(R.id.test_time);
            test_user = itemView.findViewById(R.id.test_user);
            test_result = itemView.findViewById(R.id.test_result);
            resultBtn = itemView.findViewById(R.id.resultBtn);
        }
    }

    @NonNull
    @Override
    public TestAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.test_items,parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TestAdapter.ViewHolder holder, int position) {
        Log.d("TAG","testlist: "+testList.size());
        holder.test_time.setText("Time: "+testList.get(position).getBookingTime());
        holder.test_user.setText("Applicant: "+ testList.get(position).getBookingUser());

        if(testList.get(position).getResulted()){
            holder.test_result.setText("Result: "+testList.get(position).getResults());
            holder.resultBtn.setBackgroundResource(R.drawable.plain_border);
            holder.resultBtn.setText("EDIT");

        } else{
            holder.resultBtn.setBackgroundResource(R.drawable.light_border);
            holder.test_result.setText("Result: Unresulted");
        }

        final int finalPosition = position;
        holder.resultBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent bIntent = new Intent(context, Instructor_Result_Test.class);
                bIntent.putExtra("bookingID",testList.get(finalPosition).getBookingID());
                bIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(bIntent);
            }
        });

    }

    @Override
    public int getItemCount() {return testList.size();}
}