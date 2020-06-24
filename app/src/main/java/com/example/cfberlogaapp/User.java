package com.example.cfberlogaapp;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class User {
    public String name;
    public String weight;
    public String height;
    public String shoulders;
    public String chest;
    public String biceps;
    public String waist;
    public String hip;
    public String hips;
    public String backSquat;
    public String frontSquat;
    public String overheadSquat;
    public String deadlift;
    public String benchPress;
    public String press;
    public String pullUps;
    public String c2bPullUps;
    public String hsPullUps;
    public String ringsDips;
    public String t2b;
    public String profilePictureUrl;


    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(UserInfo.class)
    }

    public User(String name, String weight, String height, String shoulders, String chest, String biceps, String waist, String hip, String hips,
                String backSquat, String frontSquat, String overheadSquat, String deadlift, String benchPress, String press, String pullUps,
                String c2bPullUps, String hsPullUps, String ringsDips, String t2b, String profilePictureUrl){
        this.name = name;
        this.weight = weight;
        this.height = height;
        this.shoulders = shoulders;
        this.chest = chest;
        this.biceps = biceps;
        this.waist = waist;
        this.hip = hip;
        this.hips = hips;
        this.backSquat = backSquat;
        this.frontSquat = frontSquat;
        this.overheadSquat = overheadSquat;
        this.deadlift = deadlift;
        this.benchPress = benchPress;
        this.press = press;
        this.pullUps = pullUps;
        this.c2bPullUps = c2bPullUps;
        this. hsPullUps = hsPullUps;
        this.ringsDips = ringsDips;
        this.t2b = t2b;
        this.profilePictureUrl = profilePictureUrl;
    }

    public User(String weight, String shoulders, String chest, String biceps, String waist, String hip, String hips, String backSquat, String frontSquat, String overheadSquat, String deadlift, String benchPress, String press, String pullUps, String c2bPullUps, String hsPullUps, String ringsDips, String t2b) {
        this.weight=weight;
        this.shoulders=shoulders;
        this.chest=chest;
        this.biceps=biceps;
        this.waist=waist;
        this.hip=hip;
        this.hips=hips;
        this.backSquat=backSquat;
        this.frontSquat=frontSquat;
        this.overheadSquat=overheadSquat;
        this.deadlift=deadlift;
        this.benchPress=benchPress;
        this.press=press;
        this.pullUps=pullUps;
        this.c2bPullUps=c2bPullUps;
        this.hsPullUps=hsPullUps;
        this.ringsDips=ringsDips;
        this.t2b=t2b;
    }
}
