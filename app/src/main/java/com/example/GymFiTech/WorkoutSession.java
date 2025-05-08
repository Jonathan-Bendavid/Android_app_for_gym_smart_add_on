package com.example.GymFiTech;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.List;

public class WorkoutSession implements Parcelable {
    private String date;
    private String overall_performance_score;
    private String overall_variability_score;
    private String average_heartrate;
    private List<Workout> exercises;

    public WorkoutSession(String date, String overall_performance_score, String overall_variability_score,
                          String average_heartrate, List<Workout> exercises) {
        this.date = date;
        this.overall_performance_score = overall_performance_score;
        this.overall_variability_score = overall_variability_score;
        this.average_heartrate = average_heartrate;
        this.exercises = exercises;
    }

    protected WorkoutSession(Parcel in) {
        date = in.readString();
        overall_performance_score = in.readString();
        overall_variability_score = in.readString();
        average_heartrate = in.readString();
        exercises = in.createTypedArrayList(Workout.CREATOR);
    }

    public static final Creator<WorkoutSession> CREATOR = new Creator<WorkoutSession>() {
        @Override
        public WorkoutSession createFromParcel(Parcel in) {
            return new WorkoutSession(in);
        }

        @Override
        public WorkoutSession[] newArray(int size) {
            return new WorkoutSession[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(date);
        dest.writeString(overall_performance_score);
        dest.writeString(overall_variability_score);
        dest.writeString(average_heartrate);
        dest.writeTypedList(exercises);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getDate() {
        return date;
    }

    public String getOverall_performance_score() {
        return overall_performance_score;
    }

    public String getAverage_heartrate() {
        return average_heartrate;
    }

    public String getOverall_variability_score() {
        return overall_variability_score;
    }

    public List<Workout> getExercises() {
        return exercises;
    }
}
