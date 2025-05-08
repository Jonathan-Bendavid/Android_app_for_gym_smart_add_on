package com.example.GymFiTech;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class UserInfoActivity extends AppCompatActivity {

    private TextView emailTextView, usernameTextView, heightTextView, weightTextView, levelTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        // Initialize TextViews with ids
        emailTextView = findViewById(R.id.emailTextView);
        usernameTextView = findViewById(R.id.usernameTextView);
        heightTextView = findViewById(R.id.heightTextView);
        weightTextView = findViewById(R.id.weightTextView);
        levelTextView = findViewById(R.id.levelTextView);

        // Set user info from Global class
        emailTextView.setText("Email: " + Global.currentUserEmail);
        usernameTextView.setText("Username: " + Global.currentUsername);
        heightTextView.setText("Height: " + Global.currentHeight + " cm");
        weightTextView.setText("Weight: " + Global.currentWeight + " kg");
        levelTextView.setText("Workout Level: " + Global.currentWorkoutLevel);

    }

    public void goToProfilePage(View view) {
        Intent intent = new Intent(UserInfoActivity.this, ProfilePage.class);
        startActivity(intent);
        finish();
    }

    // Method to navigate to MainActivity
    public void goToMainActivity(View view) {
        Intent intent = new Intent(UserInfoActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    // Method to navigate to WorkoutDataActivity
    public void goToWorkoutDataActivity(View view) {
        Intent intent = new Intent(UserInfoActivity.this, WorkoutDataActivity.class);
        startActivity(intent); // Start the WorkoutDataActivity
        finish();
    }
}
