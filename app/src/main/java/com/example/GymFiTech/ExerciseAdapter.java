package com.example.GymFiTech;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.charts.BarChart;

import java.util.List;

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder> {

    private List<Workout> exerciseList;
    private Context context; // Add context as a member variable

    public ExerciseAdapter(List<Workout> exerciseList, Context context) {
        this.exerciseList = exerciseList;
        this.context = context;  // Initialize context
    }

    @Override
    public ExerciseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.exercise_item, parent, false);
        return new ExerciseViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ExerciseViewHolder holder, int position) {
        Workout workout = exerciseList.get(position);
        holder.exerciseTextView.setText(workout.getExercise());
        holder.setsTextView.setText(workout.getSets());
        holder.romTextView.setText(workout.getOverallAverageRom());
        holder.weightTextView.setText(workout.getWeight());
        holder.Avg_RepsTextView.setText(workout.getAverageReps());
        holder.OverallTimeTextView.setText(workout.getOverallTime()+ " Sec");
        holder.OverallTutTextView.setText(workout.getOverallTut() + " Sec");
        holder.OverallPerScoreTextView.setText(workout.getOverallPerformanceScore() + "%");
        holder.OverallVarScoreTextView.setText(workout.getOverallVariabilityScore() + "%");
        holder.HeartrateTextView.setText(workout.getAverageHeartrate() + " BPM");

        try {
            float score = Float.parseFloat(workout.getOverallPerformanceScore());
            if (score < 55) {
                holder.OverallPerScoreTextView.setTextColor(ContextCompat.getColor(context, R.color.red));
                holder.exerciseTipTextView.setText("Tip: Consider decreasing weight");
            }
            else if (score < 75) {
                holder.OverallPerScoreTextView.setTextColor(ContextCompat.getColor(context, R.color.yellow));
                holder.exerciseTipTextView.setText("Tip: Consider taking more rest weight");
            }
            else if (score < 90){
                holder.exerciseTipTextView.setText("Solid performance");
            }
            else{
                holder.exerciseTipTextView.setText("Great performance, Consider increasing weight");
            }

        }
        catch (NumberFormatException e) {
            holder.exerciseTipTextView.setText("Invalid performance score");
        }

        try {
            float score = Float.parseFloat(workout.getOverallVariabilityScore());
            if (score < 50) {
                holder.OverallVarScoreTextView.setTextColor(ContextCompat.getColor(context, R.color.red));
                holder.exerciseTipTextView.setText("Very Inconsistent reps, consider going to rest");
            }

            else if (score < 80) {
                holder.OverallVarScoreTextView.setTextColor(ContextCompat.getColor(context, R.color.red));
                holder.exerciseTipTextView.setText("Reps Inconsistent, Consider decreasing weight and resting");
            }

        }
        catch (NumberFormatException e) {
            holder.exerciseTipTextView.setText("Invalid performance score");
        }

        ExercisePerformanceChartHelper helper = new ExercisePerformanceChartHelper(holder.performanceChart, context);
        helper.displaySetPerformanceScores(workout.getSetPerformanceScore());

        holder.goToSetsButton.setOnClickListener(v -> {
            // Create an Intent to open SetsActivity
            Intent intent = new Intent(context, SetsActivity.class);

            // Pass the selected workout data to SetsActivity
            intent.putExtra("selectedWorkout", workout); // Pass the workout object

            // Start the activity
            context.startActivity(intent);

        });
    }


    @Override
    public int getItemCount() {
        return exerciseList.size();
    }

    public static class ExerciseViewHolder extends RecyclerView.ViewHolder {
        TextView exerciseTextView, setsTextView, romTextView, weightTextView, Avg_RepsTextView,
                OverallTimeTextView, OverallTutTextView, exerciseTipTextView,
                OverallPerScoreTextView, OverallVarScoreTextView, HeartrateTextView;
        Button goToSetsButton;
        BarChart performanceChart;

        public ExerciseViewHolder(View itemView) {
            super(itemView);
            exerciseTextView = itemView.findViewById(R.id.exerciseTextView);
            exerciseTipTextView = itemView.findViewById(R.id.exerciseTipTextView);
            setsTextView = itemView.findViewById(R.id.setsTextView);
            Avg_RepsTextView = itemView.findViewById(R.id.Avg_RepsTextView);
            romTextView = itemView.findViewById(R.id.ROMTextView);
            HeartrateTextView = itemView.findViewById(R.id.HeartRateTextView);
            weightTextView = itemView.findViewById(R.id.weightTextView);
            OverallTimeTextView = itemView.findViewById(R.id.OverallTimeTextView);
            OverallTutTextView = itemView.findViewById(R.id.OverallTutTextView);
            OverallPerScoreTextView = itemView.findViewById(R.id.OverallPerScoreTextView);
            OverallVarScoreTextView = itemView.findViewById(R.id.OverallVarScoreTextView);
            performanceChart = itemView.findViewById(R.id.performanceChart);
            goToSetsButton = itemView.findViewById(R.id.go_to_sets);
        }
    }
}
