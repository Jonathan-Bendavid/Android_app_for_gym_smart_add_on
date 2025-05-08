package com.example.GymFiTech;

import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class RepsActivity extends AppCompatActivity {

    private RecyclerView repsRecyclerView;
    private TextView setNumberTextView, ExerciseDateTextView, ExerciseNameTextView;
    private RepAdapter repAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reps);

        repsRecyclerView = findViewById(R.id.repsRecyclerView);
        setNumberTextView = findViewById(R.id.setNumberTextView);
        ExerciseNameTextView = findViewById(R.id.ExerciseNameTextView);
        ExerciseDateTextView = findViewById(R.id.ExerciseDateTextView);

        Intent intent = getIntent();
        Workout workout = intent.getParcelableExtra("workout");
        int setIndex = intent.getIntExtra("setIndex", -1);

        if (workout != null && setIndex != -1) {

            setNumberTextView.setText("Set " + (setIndex + 1));
            ExerciseNameTextView.setText(workout.getExercise());
            ExerciseDateTextView.setText(workout.getDate());

            List<List<String>> repsList = workout.getRepsData().get(setIndex);
            // Each rep: {ROM, Time, Heartrate, Power}
            repAdapter = new RepAdapter(repsList);
            repsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            repsRecyclerView.setAdapter(repAdapter);
        }
    }

    public void goToMainActivity(View view) {
        Intent intent = new Intent(RepsActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void goToWorkoutDataActivity(View view) {
        Intent intent = new Intent(RepsActivity.this, WorkoutDataActivity.class);
        startActivity(intent);
        finish();
    }

    public void goToWorkoutDetailsActivity(View view) {
        Intent intent = new Intent(RepsActivity.this, WorkoutDetailsActivity.class);
        startActivity(intent);
        finish();
    }

    public void goToProfilePage(View view) {
        Intent intent = new Intent(RepsActivity.this, ProfilePage.class);
        startActivity(intent);
        finish();
    }
}
