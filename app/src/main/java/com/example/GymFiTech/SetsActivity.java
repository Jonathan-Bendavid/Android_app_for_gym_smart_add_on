package com.example.GymFiTech;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SetsActivity extends AppCompatActivity {

    private RecyclerView setsRecyclerView;
    private TextView ExerciseNameTextView, ExerciseDateTextView;
    private SetAdapter setAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sets); // Ensure this layout exists

        // Get the selected Workout object passed from ExerciseAdapter
        Intent intent = getIntent();
        Workout selectedWorkout = intent.getParcelableExtra("selectedWorkout");

        setsRecyclerView = findViewById(R.id.setsRecyclerView);
        ExerciseNameTextView = findViewById(R.id.ExerciseNameTextView);
        ExerciseDateTextView = findViewById(R.id.ExerciseDateTextView);
        if (selectedWorkout != null) {
            ExerciseNameTextView.setText(selectedWorkout.getExercise());
            ExerciseDateTextView.setText(selectedWorkout.getDate());

            // Initialize and set the adapter for the RecyclerView
            setAdapter = new SetAdapter(selectedWorkout, this);
            setsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            setsRecyclerView.setAdapter(setAdapter);
        }
    }
    public void goToProfilePage(View view) {
        startActivity(new Intent(this, ProfilePage.class));
        finish();
    }

    public void goToMainActivity(View view) {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
    public void goToWorkoutDataActivity(View view) {
        startActivity(new Intent(this, WorkoutDataActivity.class));
        finish();
    }
    public void goToWorkoutDetailsActivity(View view) {
        startActivity(new Intent(this, WorkoutDetailsActivity.class));
        finish();
    }
}
