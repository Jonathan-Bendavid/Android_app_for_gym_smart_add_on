package com.example.GymFiTech;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class WorkoutAdapter extends RecyclerView.Adapter<WorkoutAdapter.WorkoutViewHolder> {

    private List<WorkoutSession> workoutSessions;
    private Context context;

    public WorkoutAdapter(List<WorkoutSession> workoutSessions, Context context) {
        this.workoutSessions = workoutSessions;
        this.context = context;
    }

    @Override
    public WorkoutViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.workout_item, parent, false);
        return new WorkoutViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(WorkoutViewHolder holder, int position) {
        WorkoutSession workoutSession = workoutSessions.get(position);
        holder.dateTextView.setText(workoutSession.getDate());
        holder.avgPerTextView.setText("Avg Performance Score: " + workoutSession.getOverall_performance_score() + "%");
        holder.avgVarTextView.setText("Avg Variability Score: " + workoutSession.getOverall_variability_score() + "%");
        holder.avgHeartRateTextView.setText("Avg Heart Rate: " + workoutSession.getAverage_heartrate());

        holder.goToWorkoutButton.setOnClickListener(v -> {
            Intent intent = new Intent(context, WorkoutDetailsActivity.class);
            intent.putExtra("workoutSession", workoutSession);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {

        if (workoutSessions != null) {
            return workoutSessions.size();
        }
        return 0;

    }

    public static class WorkoutViewHolder extends RecyclerView.ViewHolder {
        public TextView dateTextView, avgPerTextView, avgHeartRateTextView, avgVarTextView;
        public Button goToWorkoutButton;

        public WorkoutViewHolder(View view) {
            super(view);
            dateTextView = view.findViewById(R.id.workoutDate);
            avgPerTextView = view.findViewById(R.id.avgPerTextView);
            avgVarTextView = view.findViewById(R.id.avgVarTextView);
            avgHeartRateTextView = view.findViewById(R.id.avgHeartRateTextView);
            goToWorkoutButton = view.findViewById(R.id.go_to_workout);
        }
    }
}

