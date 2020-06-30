package com.example.cfberlogaapp;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.makeramen.roundedimageview.RoundedImageView;

public class Message {
    public String messageText;
    public String date;
    public String userID;

    public Message(String messageText, String date, String userID) {
        this.messageText=messageText;
        this.date=date;
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


    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID=userID;
    }

    public void setTextViewWithUserName(final TextView nameTextView){
        DatabaseReference mDatabase =FirebaseDatabase.getInstance().getReference();
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String userName = dataSnapshot.child("users").child(userID).child("name").getValue(String.class);
                nameTextView.setText(userName);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void setImageViewWithProfilePicture(final Context context, final RoundedImageView imageView){
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String profilePictureUrl = dataSnapshot.child("users").child(userID).child("profilePictureUrl").getValue(String.class);
                StorageReference storageReference =FirebaseStorage.getInstance().getReferenceFromUrl(profilePictureUrl);
                storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Glide.with(context).load(uri).into(imageView);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
