package com.example.GymFiTech;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class WorkoutDetailsActivity extends AppCompatActivity {

    private TextView workoutDateTextView;
    private RecyclerView exerciseRecyclerView;
    private ExerciseAdapter exerciseAdapter;
    private List<Workout> exerciseList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_details);

        workoutDateTextView = findViewById(R.id.DateTextView);
        exerciseRecyclerView = findViewById(R.id.exerciseRecyclerView);

        // Check if the WorkoutSession is passed correctly
        WorkoutSession workoutSession = getIntent().getParcelableExtra("workoutSession");

        if (workoutSession == null) {
            Log.e("WorkoutDetailsActivity", "workoutSession is null!");
            Toast.makeText(this, "Workout session data missing!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Set workout date
        workoutDateTextView.setText(workoutSession.getDate());

        // Initialize exercise list safely
        if (workoutSession.getExercises() != null) {
            exerciseList = workoutSession.getExercises();
        }
        else {
            exerciseList = new ArrayList<>();
        }

        // Set up RecyclerView
        exerciseAdapter = new ExerciseAdapter(exerciseList, this);
        exerciseRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        exerciseRecyclerView.setAdapter(exerciseAdapter);
    }

    // Method to navigate to MainActivity
    public void goToMainActivity(View view) {
        Intent intent = new Intent(WorkoutDetailsActivity.this, MainActivity.class);
        startActivity(intent); // Start the MainActivity
        finish();
    }

    // Method to navigate to WorkoutDataActivity
    public void goToWorkoutDataActivity(View view) {
        Intent intent = new Intent(WorkoutDetailsActivity.this, WorkoutDataActivity.class);
        startActivity(intent);
        finish();
    }

    // Method to navigate to WorkoutDataActivity
    public void goToProfilePage(View view) {
        Intent intent = new Intent(WorkoutDetailsActivity.this, ProfilePage.class);
        startActivity(intent); // Start the ProfilePage
        finish();
    }




}
