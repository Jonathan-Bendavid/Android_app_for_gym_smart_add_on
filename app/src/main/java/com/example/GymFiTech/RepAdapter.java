package com.example.GymFiTech;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class RepAdapter extends RecyclerView.Adapter<RepAdapter.RepViewHolder> {

    private List<List<String>> repsList; // Each item: {ROM, Time, Heartrate, Power}

    public RepAdapter(List<List<String>> repsList) {
        this.repsList = repsList;
    }

    @NonNull
    @Override
    public RepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rep_item, parent, false);
        return new RepViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RepViewHolder holder, int position) {
        List<String> rep = repsList.get(position);
        holder.repNumberTextView.setText("Rep " + (position + 1));

        if (rep.size() == 4) {
            holder.romTextView.setText("ROM: " + rep.get(0));
            holder.timeTextView.setText("Time: " + rep.get(1) + " sec");
            holder.heartRateTextView.setText("Heart Rate: " + rep.get(2));
            holder.powerTextView.setText("Power: " + rep.get(3));
        }
    }

    @Override
    public int getItemCount() {
        return repsList.size();
    }

    public static class RepViewHolder extends RecyclerView.ViewHolder {
        TextView repNumberTextView, romTextView, timeTextView, heartRateTextView, powerTextView;

        public RepViewHolder(@NonNull View itemView) {
            super(itemView);
            repNumberTextView = itemView.findViewById(R.id.repNumberTextView);
            romTextView = itemView.findViewById(R.id.romTextView);
            timeTextView = itemView.findViewById(R.id.timeTextView);
            heartRateTextView = itemView.findViewById(R.id.heartRateTextView);
            powerTextView = itemView.findViewById(R.id.powerTextView);
        }
    }

}
