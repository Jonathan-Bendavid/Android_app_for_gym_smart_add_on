package com.example.GymFiTech;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Workout implements Parcelable {
    private String exercise;
    private String date;
    private String sets; // There will always be 3 sets
    private String weight;
    private String overall_average_power;
    private String overall_tut;
    private String overall_time;
    private String overall_average_rom; // Overall exercise average range of motion
    private String overall_performance_score; // Overall exercise performance score
    private String overall_variability_score; // Overall exercise variability score
    private String average_heartrate;
    private List<String> average_rom; // A list of the average range of motion in each set
    private List<String> set_times; // A list of the time in each set
    private List<String> set_tut; // Time under tension for each set
    private List<String> set_performance_score; // Performance score for each set
    private List<String> set_variability_score; // Variability score for each set
    private List<String> set_average_power; // Average power for each set
    private List<String> rest_times; // A list of the rest time in each set
    private List<String> heart_rate; // A list of the heart rate in each set
    private List<String> total_reps; // A list of the number of reps in each set
    private List<List<List<String>>> reps_data; // 3 lists, each having a list with all the reps
    // Each rep having its own range of motion and time

    public Workout(
            String exercise, String date, String sets, String weight, String overall_average_rom, String overall_average_power,
            String overall_tut, String overall_time, String overall_performance_score, String overall_variability_score,
            String average_heartrate, List<String> average_rom, List<String> set_times, List<String> set_tut,
            List<String> set_performance_score, List<String> set_variability_score, List<String> set_average_power,
            List<String> rest_times, List<String> heart_rate, List<String> total_reps, List<List<List<String>>> reps_data) {
        this.exercise = exercise;
        this.date = date;
        this.sets = sets;
        this.weight = weight;
        this.overall_average_rom = overall_average_rom;
        this.overall_average_power = overall_average_power;
        this.overall_tut = overall_tut;
        this.overall_time = overall_time;
        this.overall_performance_score = overall_performance_score;
        this.overall_variability_score = overall_variability_score;
        this.average_heartrate = average_heartrate;
        this.average_rom = average_rom;
        this.set_times = set_times;
        this.set_tut = set_tut;
        this.set_performance_score = set_performance_score;
        this.set_variability_score = set_variability_score;
        this.set_average_power = set_average_power;
        this.rest_times = rest_times;
        this.heart_rate = heart_rate;
        this.total_reps = total_reps;
        this.reps_data = reps_data;
    }


    protected Workout(Parcel in) {
        exercise = in.readString();
        date = in.readString();
        sets = in.readString();
        weight = in.readString();
        overall_average_rom = in.readString();
        overall_average_power = in.readString();
        overall_tut = in.readString();
        overall_time = in.readString();
        overall_performance_score = in.readString();
        overall_variability_score = in.readString();
        average_heartrate = in.readString();
        average_rom = in.createStringArrayList();
        set_times = in.createStringArrayList();
        set_tut = in.createStringArrayList();
        set_performance_score = in.createStringArrayList();
        set_variability_score = in.createStringArrayList();
        set_average_power = in.createStringArrayList();
        rest_times = in.createStringArrayList();
        heart_rate = in.createStringArrayList();
        total_reps = in.createStringArrayList();
        reps_data = (List<List<List<String>>>) in.readSerializable();
    }

    public static final Creator<Workout> CREATOR = new Creator<Workout>() {
        @Override
        public Workout createFromParcel(Parcel in) {
            return new Workout(in);
        }

        @Override
        public Workout[] newArray(int size) {
            return new Workout[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(exercise);
        dest.writeString(date);
        dest.writeString(sets);
        dest.writeString(weight);
        dest.writeString(overall_average_rom);
        dest.writeString(overall_average_power);
        dest.writeString(overall_tut);
        dest.writeString(overall_time);
        dest.writeString(overall_performance_score);
        dest.writeString(overall_variability_score);
        dest.writeString(average_heartrate);
        dest.writeStringList(average_rom);
        dest.writeStringList(set_times);
        dest.writeStringList(set_tut);
        dest.writeStringList(set_performance_score);
        dest.writeStringList(set_variability_score);
        dest.writeStringList(set_average_power);
        dest.writeStringList(rest_times);
        dest.writeStringList(heart_rate);
        dest.writeStringList(total_reps);
        dest.writeSerializable((java.io.Serializable) reps_data);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    // Getters
    public String getExercise() {
        return exercise;
    }

    public String getDate() {
        return date;
    }

    public String getSets() {
        return sets;
    }

    public String getWeight() {
        return weight;
    }

    public String getOverallAverageRom() {
        return overall_average_rom;
    }

    public String getOverallTut() {
        return overall_tut;
    }

    public String getOverallTime() {
        return overall_time;
    }

    public String getOverallPerformanceScore() {
        return overall_performance_score;
    }

    public String getOverallVariabilityScore() {
        return overall_variability_score;
    }

    public String getAverageHeartrate() {
        return average_heartrate;
    }

    public List<String> getAverageRom() {
        return average_rom;
    }

    public List<String> getSetTimes() {
        return set_times;
    }

    public List<String> getSetTut() {
        return set_tut;
    }

    public List<String> getSetPerformanceScore() {
        return set_performance_score;
    }

    public List<String> getSetVariabilityScore() {
        return set_variability_score;
    }

    public List<String> getSetAveragePower() {
        return set_average_power;
    }

    public List<String> getRestTimes() {
        return rest_times;
    }

    public List<String> getHeartRate() {
        return heart_rate;
    }

    public List<String> getTotalReps() {
        return total_reps;
    }

    public List<List<List<String>>> getRepsData() {
        return reps_data;
    }

    // Average Reps Calculation
    public String getAverageReps() {
        double sum = 0;
        int count = total_reps.size();

        // Go through each set's reps and sum them up
        for (String repCount : total_reps) {
            try {
                sum += Integer.parseInt(repCount); // Convert string to integer and add to sum
            }
            catch (NumberFormatException e) {
                break; // Handle cases where parsing fails
            }
        }

        // Calculate the average and return it as a string
        if (count > 0) {
            double average = sum / count;
            return String.format("%.2f", average); // Return average with 2 decimal places
        }

        return "0"; // Return 0 if no sets exist
    }
}