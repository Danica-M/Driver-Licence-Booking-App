package com.example.nzta_booking_app.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nzta_booking_app.R;
import com.example.nzta_booking_app.models.Booking;

import java.util.ArrayList;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder>{
    Context context;
    ArrayList<Booking> bookingList;


    public HistoryAdapter(Context context, ArrayList<Booking> bookingList) {
        this.context = context;
        this.bookingList = bookingList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView test_date;
        TextView instructor;
        ImageView status_img;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            test_date = itemView.findViewById(R.id.test_date);
            instructor = itemView.findViewById(R.id.txInstructor);
            status_img = itemView.findViewById(R.id.status_img);
        }
    }
    @NonNull
    @Override
    public HistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.booking_items,parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryAdapter.ViewHolder holder, int position) {
        holder.test_date.setText(bookingList.get(position).getBookingDate()+" - "+bookingList.get(position).getBookingTime());
        holder.instructor.setText("Instructor: "+ bookingList.get(position).getBookingInstructor());
        if(bookingList.get(position).getResults().equals("Passed")){holder.status_img.setImageResource(R.drawable.pass);}
        else{holder.status_img.setImageResource(R.drawable.fail);}
    }

    @Override
    public int getItemCount() {
        return bookingList.size();
    }



}
