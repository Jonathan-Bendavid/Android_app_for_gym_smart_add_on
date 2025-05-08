package com.example.GymFiTech;

import java.util.List;

public class Global {

    // Static variables to store user data
    public static String currentUserEmail;
    public static String currentUsername;
    public static Integer currentHeight;
    public static String currentWorkoutLevel;
    public static Integer currentWeight;
    public static int currentWorkoutCount;
    public static double user_average_heartrate;
    public static double user_average_rom;
    public static List<String> Session_performance_scores;
    public static List<String> monthly_performance_scores;
    public static List<String> monthly_dates;
    private static List<WorkoutSession> workoutList;
    public static List<Integer> workoutsPerMonth;


    // Optional: Add methods to update these variables if needed
    public static void setUserData(String email, String username, Integer height, String workoutLevel, Integer weight) {
        currentUserEmail = email;
        currentUsername = username;
        currentHeight = height;
        currentWorkoutLevel = workoutLevel;
        currentWeight = weight;
    }

    // Set workout count
    public static void setWorkoutCount(int workoutCount) {
        currentWorkoutCount = workoutCount;
    }

    // Optional: Method to clear user data on logout
    public static void clearUserData() {
        currentUserEmail = null;
        currentUsername = null;
        currentHeight = null;
        currentWeight = null;
        currentWorkoutLevel = null;
        currentWorkoutCount = 0;
    }

    public static void setWorkoutList(List<WorkoutSession> workoutList) {
        Global.workoutList = workoutList;
    }

    public static List<WorkoutSession> getWorkoutList() {
        return workoutList;
    }

    public static void setWorkoutsPerMonth(List<Integer> workoutsPerMonth) {
        Global.workoutsPerMonth = workoutsPerMonth;
    }
    public static List<Integer> getWorkoutsPerMonth() {
        return workoutsPerMonth;
    }
}
