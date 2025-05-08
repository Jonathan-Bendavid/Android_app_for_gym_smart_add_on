package com.example.GymFiTech;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ProfilePage extends AppCompatActivity {

    private TextView usernameTextView, emailTextView, levelTextView, workoutCountTextView;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private BarChart barChart;
    private WorkoutChartHelper workoutChartHelper;
    private DatabaseReference workoutRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Initialize Firebase Authentication
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        // Initialize UI elements
        usernameTextView = findViewById(R.id.username);
        emailTextView = findViewById(R.id.email);
        levelTextView = findViewById(R.id.level);
        workoutCountTextView = findViewById(R.id.workout_count);
        barChart = findViewById(R.id.idBarChart);

        // Initialize the WorkoutChartHelper with the BarChart and context
        workoutChartHelper = new WorkoutChartHelper(barChart, this);

        // Check if user is logged in
        if (user == null) {
            Log.e("ProfilePage", "User is not logged in.");
            redirectToLogin();
            return;
        }

        // Display email from Firebase
        if (user.getEmail() != null) {
            emailTextView.setText(user.getEmail());
            displayUserData();
        } else {
            Log.e("ProfilePage", "User email is null");
        }
        // Display email from Firebase
        if (user.getEmail() != null) {
            emailTextView.setText(user.getEmail());
            displayUserData();
        } else {
            Log.e("ProfilePage", "User email is null");
        }
    }

    @SuppressLint("SetTextI18n")
    private void displayUserData() {
        // Access user data from Global class
        String username = Global.currentUsername;
        String level = Global.currentWorkoutLevel;
        int workoutCount = Global.currentWorkoutCount;

        // Display the workout count in the UI
        workoutCountTextView.setText("Workouts: " + workoutCount);

        // Set user data into UI
        if (username != null) {
            usernameTextView.setText(username);
        }

        if (level != null) {
            levelTextView.setText(level);
        }

        // Get the sanitized email to fetch workout data from Firebase
        String sanitizedEmail = sanitizeEmail(user.getEmail());

        // Display the workout data on the chart
        getWorkoutDates(sanitizedEmail, new WorkoutDataCallback() {
            @Override
            public void onDataFetched(List<Integer> workoutsPerMonth) {
                workoutChartHelper.displayWorkoutBarChart(workoutsPerMonth);
                Global.setWorkoutsPerMonth(workoutsPerMonth);
            }
        });
    }

    private void getWorkoutDates(String sanitizedEmail, final WorkoutDataCallback callback) {
        workoutRef = FirebaseDatabase.getInstance().getReference("workoutData").child(sanitizedEmail);
        final List<Integer> workoutsPerMonth = new ArrayList<>(12);

        // Initialize the list with 12 zeroes (one for each month)
        for (int i = 0; i < 12; i++) {
            workoutsPerMonth.add(0);
        }

        workoutRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot workoutSnapshot : dataSnapshot.getChildren()) {
                    String date = workoutSnapshot.getKey(); // Get the date ("30-01-2025")
                    if (date != null) {
                        date = date.substring(9);
                        String[] dateParts = date.split("-"); // Split date into [day, month, year]
                        if (dateParts.length == 3) {
                            int month = Integer.parseInt(dateParts[1]) - 1; // Convert month to array idx (0 = January)
                            workoutsPerMonth.set(month, workoutsPerMonth.get(month) + 1); }
                    }
                }
                // Use the callback to return the result after data is fetched
                callback.onDataFetched(workoutsPerMonth);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Firebase", "Failed to read value.", databaseError.toException());
            }
        });
    }

    // Define the callback interface
    public interface WorkoutDataCallback {
        void onDataFetched(List<Integer> workoutsPerMonth);
    }

    private String sanitizeEmail(String email) {
        return email.replace(".", "_").replace("@", "_");
    }

    // Method to navigate to LoginActivity
    public void redirectToLogin() {
        Intent intent = new Intent(ProfilePage.this, Login.class);
        startActivity(intent);
        finish();
    }

    // Method to navigate to MainActivity
    public void goToMainActivity(View view) {
        Intent intent = new Intent(ProfilePage.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    // Method to navigate to WorkoutDataActivity
    public void goToWorkoutDataActivity(View view) {
        Intent intent = new Intent(ProfilePage.this, WorkoutDataActivity.class);
        startActivity(intent);
        finish();
    }

    // Method to navigate to User Info
    public void goToUserInfoActivity(View view) {
        Intent intent = new Intent(ProfilePage.this, UserInfoActivity.class);
        startActivity(intent);
        finish();
    }
    // Method to refresh ProfilePage (prevents duplicate activities)
    public void goToProfilePage(View view) {
        recreate();
    }
}
