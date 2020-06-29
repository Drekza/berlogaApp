package com.example.cfberlogaapp;

import com.google.firebase.auth.FirebaseAuth;

public class Message {
    public String messageText;
    public String date;
    public String usersName;
    public String userID;

    public Message(String messageText, String date, String usersName, String userID) {
        this.messageText=messageText;
        this.date=date;
        this.usersName=usersName;
        this.userID=userID;
    }

    public Message(String messageText){
        this.messageText = messageText;
    }

    public Message() {
    }

    public boolean isMine(){
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        boolean isMine = false;
        isMine = (this.userID.equals(mAuth.getUid())) ? true : false;
        return isMine;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText=messageText;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date=date;
    }

    public String getUsersName() {
        return usersName;
    }

    public void setUsersName(String usersName) {
        this.usersName=usersName;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID=userID;
    }
}
