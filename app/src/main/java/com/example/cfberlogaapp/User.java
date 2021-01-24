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

    public User(String name, String weight, String height, String shoulders, String chest, String biceps, String waist, String hip, String hips, String backSquat, String frontSquat, String overheadSquat, String deadlift, String benchPress, String press, String pullUps, String c2bPullUps, String hsPullUps, String ringsDips, String t2b) {
        this.name=name;
        this.weight=weight;
        this.height=height;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getShoulders() {
        return shoulders;
    }

    public void setShoulders(String shoulders) {
        this.shoulders = shoulders;
    }

    public String getChest() {
        return chest;
    }

    public void setChest(String chest) {
        this.chest = chest;
    }

    public String getBiceps() {
        return biceps;
    }

    public void setBiceps(String biceps) {
        this.biceps = biceps;
    }

    public String getWaist() {
        return waist;
    }

    public void setWaist(String waist) {
        this.waist = waist;
    }

    public String getHip() {
        return hip;
    }

    public void setHip(String hip) {
        this.hip = hip;
    }

    public String getHips() {
        return hips;
    }

    public void setHips(String hips) {
        this.hips = hips;
    }

    public String getBackSquat() {
        return backSquat;
    }

    public void setBackSquat(String backSquat) {
        this.backSquat = backSquat;
    }

    public String getFrontSquat() {
        return frontSquat;
    }

    public void setFrontSquat(String frontSquat) {
        this.frontSquat = frontSquat;
    }

    public String getOverheadSquat() {
        return overheadSquat;
    }

    public void setOverheadSquat(String overheadSquat) {
        this.overheadSquat = overheadSquat;
    }

    public String getDeadlift() {
        return deadlift;
    }

    public void setDeadlift(String deadlift) {
        this.deadlift = deadlift;
    }

    public String getBenchPress() {
        return benchPress;
    }

    public void setBenchPress(String benchPress) {
        this.benchPress = benchPress;
    }

    public String getPress() {
        return press;
    }

    public void setPress(String press) {
        this.press = press;
    }

    public String getPullUps() {
        return pullUps;
    }

    public void setPullUps(String pullUps) {
        this.pullUps = pullUps;
    }

    public String getC2bPullUps() {
        return c2bPullUps;
    }

    public void setC2bPullUps(String c2bPullUps) {
        this.c2bPullUps = c2bPullUps;
    }

    public String getHsPullUps() {
        return hsPullUps;
    }

    public void setHsPullUps(String hsPullUps) {
        this.hsPullUps = hsPullUps;
    }

    public String getRingsDips() {
        return ringsDips;
    }

    public void setRingsDips(String ringsDips) {
        this.ringsDips = ringsDips;
    }

    public String getT2b() {
        return t2b;
    }

    public void setT2b(String t2b) {
        this.t2b = t2b;
    }

    public String getProfilePictureUrl() {
        return profilePictureUrl;
    }

    public void setProfilePictureUrl(String profilePictureUrl) {
        this.profilePictureUrl = profilePictureUrl;
    }
}
