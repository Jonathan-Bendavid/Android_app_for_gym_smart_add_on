package com.example.GymFiTech;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import java.util.List;

public class WorkoutDataActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private WorkoutAdapter workoutAdapter;
    private List<WorkoutSession> workoutList;
    private FirebaseAuth auth;
    private TextView NoDataTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_data);
        recyclerView = findViewById(R.id.workoutDataRecyclerView);
        NoDataTextView = findViewById(R.id.NoDataTextView);
        recyclerView.setHasFixedSize(true);

        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();

        if (user == null) {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        workoutList = Global.getWorkoutList();
        workoutAdapter = new WorkoutAdapter(workoutList, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(workoutAdapter);

        if (workoutList == null || workoutList.isEmpty()){
            NoDataTextView.setVisibility(View.VISIBLE);
        }

    }


    public void goToMainActivity(View view) {
        Intent intent = new Intent(WorkoutDataActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void goToWorkoutDataActivity(View view) {
        recreate();
    }

    public void goToProfilePage(View view) {
        Intent intent = new Intent(WorkoutDataActivity.this, ProfilePage.class);
        startActivity(intent);
        finish();
    }

    private String sanitizeEmail(String email) {
        return email != null ? email.replace(".", "_").replace("@", "_") : "";
    }
}
