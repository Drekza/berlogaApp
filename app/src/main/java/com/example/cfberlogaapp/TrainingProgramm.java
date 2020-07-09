package com.example.cfberlogaapp;

import android.text.SpannableString;
import android.text.SpannableStringBuilder;

public class TrainingProgramm {
    public SpannableStringBuilder programm;
    public String date;
    private String dayOfWeek;
    private boolean isCompleted;

    public TrainingProgramm(){};
    public TrainingProgramm(SpannableStringBuilder programm, String date){
        this.programm = programm;
        this.date = date;
    }

    public TrainingProgramm(String date, String dayOfWeek) {
        this.date=date;
        this.dayOfWeek=dayOfWeek;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date=date;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek=dayOfWeek;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted=completed;
    }
}
