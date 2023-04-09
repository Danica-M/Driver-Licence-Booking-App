package com.example.nzta_booking_app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nzta_booking_app.R;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder>{
    Context context;

    String[] dates;
    String[] time;
    String[] status;

    public HistoryAdapter(Context context, String[] dates, String[] time, String[] status) {
        this.context = context;
        this.dates = dates;
        this.time = time;
        this.status = status;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView test_date;
        TextView instructor;
        ImageView status_img;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            test_date = itemView.findViewById(R.id.test_date);
            instructor = itemView.findViewById(R.id.instructor);
            status_img = itemView.findViewById(R.id.status_img);
        }
    }
    @NonNull
    @Override
    public HistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.booking_items,parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryAdapter.ViewHolder holder, int position) {
        holder.test_date.setText(dates[position]);
        holder.instructor.setText(time[position]);
        if (status[position] == "Fail")
            holder.status_img.setImageResource(R.drawable.fail);
        else if (status[position] == "Booked") {
            holder.status_img.setImageResource(R.drawable.booked);
        } else {
            holder.status_img.setImageResource(R.drawable.pass);
        }
    }

    @Override
    public int getItemCount() {
        return dates.length;
    }


}
