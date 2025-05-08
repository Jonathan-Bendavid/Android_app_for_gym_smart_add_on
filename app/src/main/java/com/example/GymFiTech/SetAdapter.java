package com.example.GymFiTech;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.github.mikephil.charting.charts.BarChart;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SetAdapter extends RecyclerView.Adapter<SetAdapter.SetViewHolder> {

    private Workout workout;
    private Context context;

    public SetAdapter(Workout workout, Context context) {
        this.workout = workout;
        this.context = context;
    }

    @NonNull
    @Override
    public SetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.set_item, parent, false);
        return new SetViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SetViewHolder holder, int position) {
        List<String> totalReps = workout.getTotalReps();
        List<String> avgRom = workout.getAverageRom();
        List<String> setTimes = workout.getSetTimes();
        List<String> setTut = workout.getSetTut();
        List<String> setVariabilities = workout.getSetVariabilityScore();
        List<String> setPerformances = workout.getSetPerformanceScore();
        List<String> heartRates = workout.getHeartRate();
        List<String> restTimes = workout.getRestTimes();
        String weight = workout.getWeight();
        List<List<List<String>>> reps_data = workout.getRepsData();

        holder.setTextView.setText("Set " + (position + 1));
        holder.repsTextView.setText(totalReps.get(position));
        holder.avgROMTextView.setText(avgRom.get(position));
        holder.timeTextView.setText(setTimes.get(position) + " sec");
        holder.tutTextView.setText(setTut.get(position) + " sec");
        holder.setPerTextView.setText(setPerformances.get(position) + "%");
        holder.setVarTextView.setText(setVariabilities.get(position) + "%");
        holder.avgHRTextView.setText(heartRates.get(position));
        holder.RestTimeTextView.setText(restTimes.get(position) + " sec");
        holder.weightTextView.setText(weight);

        try {
            float score = Float.parseFloat(setPerformances.get(position));
            if (score < 55) {
                holder.setPerTextView.setTextColor(ContextCompat.getColor(context, R.color.red));
                holder.tipTextView.setText("Low Performance Score, Consider decreasing weight");
            }
            else if (score < 75){
                holder.setPerTextView.setTextColor(ContextCompat.getColor(context, R.color.yellow));
                holder.tipTextView.setText("Consider taking more rest time");
            }
            else if (score < 90){
                holder.tipTextView.setText("Solid performance, keep it up");
            }
            else {
                holder.tipTextView.setText("Excellent performance, Consider Increasing weight");
            }
        }

        catch (NumberFormatException e) {
            return;
        }

        try {
            float score = Float.parseFloat(setVariabilities.get(position));

            if (score < 50) {
                holder.setVarTextView.setTextColor(ContextCompat.getColor(context, R.color.red));
                holder.tipTextView.setText("Very Inconsistent reps, consider going to rest");
            }

            else if (score < 80) {
                holder.setVarTextView.setTextColor(ContextCompat.getColor(context, R.color.red));
                holder.tipTextView.setText("Reps Inconsistent, Consider decreasing weight");
            }
        }

        catch (NumberFormatException e) {
            return;
        }



        List<List<String>> repsData = reps_data.get(position);
        SetPowerChartHelper chartHelper = new SetPowerChartHelper(holder.setPowerChart, context);
        chartHelper.displayRepPowerPerSet(repsData);

        holder.goToRepsButton.setOnClickListener(v -> {
            Intent intent = new Intent(context, RepsActivity.class);
            intent.putExtra("workout", workout);
            intent.putExtra("setIndex", position);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return workout.getTotalReps().size();
    }

    public static class SetViewHolder extends RecyclerView.ViewHolder {
        TextView setTextView, repsTextView, weightTextView, timeTextView, tutTextView, setPerTextView, setVarTextView,
                avgROMTextView, avgHRTextView, RestTimeTextView, tipTextView;
        Button goToRepsButton;
        BarChart setPowerChart;

        public SetViewHolder(@NonNull View itemView) {
            super(itemView);
            setTextView = itemView.findViewById(R.id.setTextView);
            repsTextView = itemView.findViewById(R.id.repsTextView);
            weightTextView = itemView.findViewById(R.id.weightTextView);
            timeTextView = itemView.findViewById(R.id.timeTextView);
            tutTextView = itemView.findViewById(R.id.tutTextView);
            setPerTextView = itemView.findViewById(R.id.setPerTextView);
            setVarTextView = itemView.findViewById(R.id.setVarTextView);
            avgROMTextView = itemView.findViewById(R.id.avgROMTextView);
            avgHRTextView = itemView.findViewById(R.id.avgHRTextView);
            RestTimeTextView = itemView.findViewById(R.id.RestTimeTextView);
            setPowerChart = itemView.findViewById(R.id.setPowerChart);
            goToRepsButton = itemView.findViewById(R.id.goToRepsButton);
            tipTextView = itemView.findViewById(R.id.tipTextView);
        }
    }
}