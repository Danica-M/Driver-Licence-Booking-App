package com.example.nzta_booking_app.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;


import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nzta_booking_app.ItemClickListener;
import com.example.nzta_booking_app.R;

import java.util.ArrayList;


public class SessionAdapter extends RecyclerView.Adapter<SessionAdapter.ViewHolder>{
    Context context;
    ArrayList<String>  slots;
    ArrayList<String> unavailable_slots;
    int selectedPosition = RecyclerView.NO_POSITION;
    ItemClickListener itemClickListener;

    public SessionAdapter( Context context,  ArrayList<String>  slots, ArrayList<String> unavailable_slots, ItemClickListener itemClickListener) {
        this.context = context;
        this.slots = slots;
        this.unavailable_slots = unavailable_slots;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.session_items,parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SessionAdapter.ViewHolder holder, int position) {
        holder.session_time.setText(slots.get(position));
        holder.session_time.setChecked(position == selectedPosition);
        if(!isAvailableTimeSlot(slots.get(position))) {
            holder.session_time.setBackground(ContextCompat.getDrawable(context, R.drawable.radio_unavailable));
            holder.session_time.setEnabled(false);
        }
        holder.session_time.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    selectedPosition = holder.getAdapterPosition();
                    holder.session_time.setBackground(ContextCompat.getDrawable(context,R.drawable.radio_selected));
                    itemClickListener.onClick(holder.session_time.getText().toString());
                }
                else {
                    holder.session_time.setBackground(ContextCompat.getDrawable(context, R.drawable.radio_normal));
                    holder.session_time.setEnabled(true);
                }
            }
        });
    }

    public long getItemId(int position){
        return position;
    }

    public int getItemViewType(int position){
        return position;
    }
    @Override
    public int getItemCount() {
        return slots.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        RadioButton session_time;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            session_time = itemView.findViewById(R.id.session_time);
        }
    }


//
//    public SessionAdapter(Context context, String[] slots, String[] unavailable_slots) {
//        this.context = context;
//        this.slots = slots;
//        this.unavailable_slots = unavailable_slots;
//
//    }
//
//
//    /**
//     * session view holder
//     */
//    public static class ViewHolder extends RecyclerView.ViewHolder{
//        RadioButton session_time;
//
//        public ViewHolder(@NonNull View itemView) {
//            super(itemView);
//            session_time = itemView.findViewById(R.id.session_time);
//        }
//    }
//
//    @NonNull
//    @Override
//    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        LayoutInflater inflater = LayoutInflater.from(context);
//        View view = inflater.inflate(R.layout.session_items,parent, false);
//        ViewHolder viewHolder = new ViewHolder(view);
//        return viewHolder;
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        holder.session_time.setText(slots[position]);
//
//        if (selectedPosition == position) {
//            holder.session_time.setBackground(ContextCompat.getDrawable(context,R.drawable.radio_selected));
//        } else if(!isAvailableTimeSlot(slots[position])) {
//            holder.session_time.setBackground(ContextCompat.getDrawable(context, R.drawable.radio_unavailable));
//            holder.session_time.setEnabled(false);
//        }
//        else {
//            holder.session_time.setBackground(ContextCompat.getDrawable(context, R.drawable.radio_normal));
//            holder.session_time.setEnabled(true);
//        }
//
//
//        holder.session_time.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(view.getContext(), "time: "+ holder.session_time.getText().toString(), Toast.LENGTH_SHORT).show();
//                // Update the selected position and notify the adapter of the change
//                notifyItemChanged(selectedPosition);
//                selectedPosition = holder.getAdapterPosition();
//                notifyItemChanged(selectedPosition);
//                // Perform any additional actions on item click here
//            }
//        });
//    }
//
    private boolean isAvailableTimeSlot(String timeSlot) {

        for (String item : unavailable_slots) {
            if (item.equals(timeSlot)) {
                return false;
            }
        }
        return true;
    }
//
//    @Override
//    public int getItemCount() {
//        return slots.length;
//    }
//
//    public int getSelectedPosition() {
//        return selectedPosition;
//    }
//    public String getSelectedItem() {
//        if (selectedPosition != RecyclerView.NO_POSITION) {
//            return slots[selectedPosition];
//        } else {
//            return null;
//        }
//    }

}
