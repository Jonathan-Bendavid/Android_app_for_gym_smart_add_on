package com.example.GymFiTech;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth auth;
    Button button;
    TextView textView, Daily_TipTextView, workoutCountTextView, workoutLevelTextView,
            monthly_performance_score, ThisMonth, AmongTheTop;
    FirebaseUser user;
    String current_date = "";
    BarChart monthlyChart;
    private List<WorkoutSession> workoutList;
    private DatabaseReference workoutRef;
    private DatabaseReference userRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();
        button = findViewById(R.id.logout);
        textView = findViewById(R.id.user_details);
        Daily_TipTextView = findViewById(R.id.Daily_TipTextView);
        workoutCountTextView = findViewById(R.id.workout_count);
        workoutLevelTextView = findViewById(R.id.workout_level);
        ThisMonth = findViewById(R.id.ThisMonth);
        AmongTheTop = findViewById(R.id.AmongTheTop);
        monthly_performance_score = findViewById(R.id.monthly_performance_score);

        workoutList = new ArrayList<>();
        Global.Session_performance_scores = new ArrayList<>();
        Global.monthly_performance_scores = new ArrayList<>();
        Global.monthly_dates = new ArrayList<>();
        // Check if user is logged in
        user = auth.getCurrentUser();
        if (user == null) {
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
            finish();
        }

        button.setOnClickListener(view -> {
            auth.signOut();
            Global.clearUserData();  // Clear global data on logout
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
            finish();
        });
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        current_date = sdf.format(new Date());
        current_date.replace("/","");


        // Initialize Firebase and fetch user data
        fetchUserData(user.getEmail());
        Daily_TipTextView.setText("You're doing great! Keep it up");

        monthlyChart = findViewById(R.id.monthlyChart);

    }

    private void fetchUserData(String email) {
        String sanitizedEmail = sanitizeEmail(email);

        // Fetch user details from the "users" node
        userRef = FirebaseDatabase.getInstance().getReference("users").child(sanitizedEmail);

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String username = snapshot.child("username").getValue(String.class);
                    Integer height = snapshot.child("height").getValue(Integer.class);
                    Integer weight = snapshot.child("weight").getValue(Integer.class);
                    String workoutLevel = snapshot.child("level").getValue(String.class);

                    // Set user details in Global class
                    Global.setUserData(user.getEmail(), username, height, workoutLevel, weight);

                    // Set the user details in the UI
                    textView.setText(username);  // Display Username
                    workoutLevelTextView.setText(workoutLevel);
                    // Fetch workout data
                    fetchWorkoutData(sanitizedEmail);

                } else {
                    Log.e("MainActivity", "User data not found.");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Firebase", "Failed to read user data.", error.toException());
            }
        });
    }

    private void fetchWorkoutData(String sanitizedEmail) {
        workoutRef = FirebaseDatabase.getInstance().getReference("workoutData").child(sanitizedEmail);
        workoutRef.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                workoutList.clear();
                int workoutCount = 0;
                double totalHeartrate = 0;
                double totalROM = 0;
                double monthly_total = 0;
                int monthly_count = 0;

                for (DataSnapshot workoutSnapshot : dataSnapshot.getChildren()) {
                    String date = workoutSnapshot.getKey().substring(9); // Remove number
                    List<Workout> exercises = new ArrayList<>();
                    int sessionCount = 0;
                    double session_average_heartrate = 0;
                    double avgSessionROM = 0;
                    double avgSessionPerformanceScore = 0;
                    double avgSessionVariabilityScore = 0;
                    workoutCount++;

                    for (DataSnapshot exerciseSnapshot : workoutSnapshot.getChildren()) {
                        String name = exerciseSnapshot.getKey();
                        String sets = exerciseSnapshot.child("sets").getValue(String.class);
                        String weight = exerciseSnapshot.child("weight").getValue(String.class) + "kg";
                        String overallRom = exerciseSnapshot.child("overall_average_rom").getValue(String.class);
                        String average_heartrate = exerciseSnapshot.child("average_heartrate").getValue(String.class);
                        String overallAveragePower = exerciseSnapshot.child("overall_average_power").getValue(String.class);
                        String overallTime = exerciseSnapshot.child("overall_time").getValue(String.class);
                        String overallTut = exerciseSnapshot.child("overall_tut").getValue(String.class);
                        String overall_performance_score = exerciseSnapshot.child("overall_performance_score").getValue(String.class);
                        String overall_variability_score = exerciseSnapshot.child("overall_variability_score").getValue(String.class);

                        session_average_heartrate += Double.parseDouble(average_heartrate);
                        avgSessionROM += Double.parseDouble(overallRom.substring(0, overallRom.length() - 1));
                        avgSessionPerformanceScore += Double.parseDouble(overall_performance_score);
                        avgSessionVariabilityScore += Double.parseDouble(overall_variability_score);
                        sessionCount++;

                        List<String> setTut = new ArrayList<>();
                        List<String> setAveragePower = new ArrayList<>();
                        List<String> averageRomList = new ArrayList<>();
                        List<String> setTimesList = new ArrayList<>();
                        List<String> totalRepsList = new ArrayList<>();
                        List<String> restTimesList = new ArrayList<>();
                        List<String> heartRateList = new ArrayList<>();
                        List<String> set_performance_score = new ArrayList<>();
                        List<String> set_variability_score = new ArrayList<>();
                        List<List<List<String>>> repsDataList = new ArrayList<>();

                        // Handling sets and reps data
                        for (DataSnapshot setSnapshot : exerciseSnapshot.child("sets_data").getChildren()) {
                            String averageRom = setSnapshot.child("average_rom").getValue(String.class);
                            String setTime = setSnapshot.child("set_time").getValue(String.class);
                            String restSetTime = setSnapshot.child("rest_time").getValue(String.class);
                            String setTutValue = setSnapshot.child("set_tut").getValue(String.class);
                            String performance_score = setSnapshot.child("set_performance_score").getValue(String.class);
                            String variability_score = setSnapshot.child("set_variability_score").getValue(String.class);
                            String setAveragePowerValue = setSnapshot.child("set_average_power").getValue(String.class);
                            String set_heart_rate = setSnapshot.child("set_average_heartrate").getValue(String.class) + " BPM";

                            averageRomList.add(averageRom != null ? averageRom : "0.00%");
                            setTimesList.add(setTime != null ? setTime : "0");
                            restTimesList.add(restSetTime != null ? restSetTime : "0");
                            heartRateList.add(set_heart_rate != null ? set_heart_rate : "0");
                            setTut.add(setTutValue != null ? setTutValue : "0");
                            setAveragePower.add(setAveragePowerValue != null ? setAveragePowerValue : "0");
                            set_performance_score.add(performance_score != null ? performance_score : "0");
                            set_variability_score.add(variability_score != null ? variability_score : "0");
                            int repCount = 0;
                            List<List<String>> repsList = new ArrayList<>();
                            DataSnapshot repsSnapshot = setSnapshot.child("reps");
                            for (DataSnapshot repSnapshot : repsSnapshot.getChildren()) {
                                List<String> repData = new ArrayList<>();
                                repData.add(repSnapshot.child("range_of_motion").getValue(String.class));
                                repData.add(repSnapshot.child("time").getValue(String.class));
                                repData.add(repSnapshot.child("rep_heartrate").getValue(String.class) + " BPM");
                                repData.add(repSnapshot.child("rep_power").getValue(String.class) + " mW");
                                repsList.add(repData);
                                repCount++;
                            }
                            repsDataList.add(repsList);
                            totalRepsList.add(String.valueOf(repCount));
                        }


                        exercises.add(new Workout(
                                name, date, sets, weight, overallRom, overallAveragePower,overallTut, overallTime,
                                overall_performance_score, overall_variability_score, average_heartrate, averageRomList,
                                setTimesList, setTut, set_performance_score, set_variability_score, setAveragePower,
                                restTimesList, heartRateList, totalRepsList, repsDataList));

                    }

                    double sessionVariabilityScore = (sessionCount > 0) ? avgSessionROM / sessionCount : 0;
                    double SessionPerformanceScore = (sessionCount > 0) ? avgSessionPerformanceScore / sessionCount : 0;
                    double avgSessionHeartrate = (sessionCount > 0) ? session_average_heartrate / sessionCount : 0;

                    String avgSessionHeartrateString = String.format("%.2f", avgSessionHeartrate);
                    String avgSessionPerformanceScoreString = String.format("%.2f", SessionPerformanceScore);
                    String avgSessionVariabilityScoreString = String.format("%.2f", sessionVariabilityScore);

                    Log.d("GlobalData", "Session Date" + date.substring(3,5));
                    if (date.substring(3,5).equals(current_date.substring(3,5)) && date.substring(6,11).equals(current_date.substring(6,11))) {
                        Global.monthly_performance_scores.add(avgSessionPerformanceScoreString);
                        Global.monthly_dates.add(date.substring(0,2));
                        monthly_total += SessionPerformanceScore;
                        monthly_count += 1;
                    }

                    Global.Session_performance_scores.add(avgSessionPerformanceScoreString);
                    totalHeartrate += avgSessionHeartrate;
                    totalROM += avgSessionROM;


                    workoutList.add(new WorkoutSession(date, avgSessionPerformanceScoreString, avgSessionVariabilityScoreString, avgSessionHeartrateString, exercises));
                }

                if (workoutCount > 0) {
                    Global.setWorkoutCount(workoutCount);
                    workoutCountTextView.setText("Workouts: " + workoutCount);
                    Global.user_average_heartrate = totalHeartrate / workoutCount;
                    Global.user_average_rom = totalROM / workoutCount;
                }
                else {
                    workoutCountTextView.setText("Workouts: 0");
                }

                Collections.reverse(Global.monthly_performance_scores);
                Collections.reverse(Global.monthly_dates);
                MonthlyPerformanceChartHelper chartHelper = new MonthlyPerformanceChartHelper(monthlyChart, MainActivity.this);
                chartHelper.displayMonthlyPerformanceChart();
                double monthly_average = monthly_total / monthly_count;
                monthly_performance_score.setText("This Month's Performance: " +  String.format("%.0f", monthly_average) + "%");
                Global.setWorkoutList(workoutList);

                if (monthly_count == 0){
                    monthlyChart.setVisibility(View.GONE);
                    monthly_performance_score.setText("No Month Data");
                    ThisMonth.setText("");
                    AmongTheTop.setText("");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Firebase", "Failed to fetch workout data.", databaseError.toException());
                Toast.makeText(MainActivity.this, "Failed to load data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String sanitizeEmail(String email) {
        return email.replace(".", "_").replace("@", "_");
    }


    // Method to navigate to MainActivity
    public void goToMainActivity(View view) {
        recreate();
    }

    // Method to navigate to WorkoutDataActivity
    public void goToWorkoutDataActivity(View view) {
        Intent intent = new Intent(MainActivity.this, WorkoutDataActivity.class);
        startActivity(intent); // Start the WorkoutDataActivity
        finish();
    }

    // Method to navigate to ProfilePage
    public void goToProfilePage(View view) {
        Intent intent = new Intent(MainActivity.this, ProfilePage.class);
        startActivity(intent); // Start the ProfilePage
        finish();
    }
}
