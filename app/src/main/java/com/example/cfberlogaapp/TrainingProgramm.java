package com.example.cfberlogaapp;

import android.text.SpannableString;
import android.text.SpannableStringBuilder;

public class TrainingProgramm {
    public SpannableStringBuilder programm;
    public String date;

    public TrainingProgramm(){};
    public TrainingProgramm(SpannableStringBuilder programm, String date){
        this.programm = programm;
        this.date = date;
    }

}
