package com.example.cfberlogaapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.res.Configuration;
import android.media.Image;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.makeramen.roundedimageview.RoundedImageView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private EditText messageEditText;
    private ListView listView;
    private RecyclerView chatRecycleView;
    private RecyclerView.LayoutManager layoutManager;
    private ImageButton sendMessageBtn;
    private MessageAdapter messageAdapter;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        messageEditText = findViewById(R.id.messageEditText);
//        listView = findViewById(R.id.chatListView);

        chatRecycleView = findViewById(R.id.chatRecyclerView);
        layoutManager = new LinearLayoutManager(this);
        chatRecycleView.setLayoutManager(layoutManager);

        sendMessageBtn = findViewById(R.id.sendMessageBtn);
        sendMessageBtn.setOnClickListener(onSendMessageClicked);


        messageAdapter = new MessageAdapter(this);
        chatRecycleView.setAdapter(messageAdapter);

        chatRecycleView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                if(bottom < oldBottom){
                    chatRecycleView.smoothScrollToPosition(messageAdapter.getItemCount());
                }
            }
        });

        ImageButton backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(onBackBtnClicked);
        loadChat();
    }

    public ImageButton.OnClickListener onBackBtnClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };



    public ImageButton.OnClickListener onSendMessageClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
            if(!messageEditText.getText().toString().trim().equals("")){
                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                String userID = mAuth.getUid();

                String stringDate = getIntent().getStringExtra("date");
                DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
                Date date =null;
                try {
                    date=dateFormat.parse(stringDate);
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(date);
                    int dayOfWeek = calendar.get(calendar.DAY_OF_WEEK);

                    Message message = new Message(messageEditText.getText().toString().trim(), stringDate, userID);

                    SimpleDateFormat databaseDateFormat = new SimpleDateFormat("yyyyMMdd");
                    String databaseDate = databaseDateFormat.format(date);
                    String course = getIntent().getStringExtra("course");
                    mDatabase.child("chats").child(course).child(databaseDate).push().setValue(message);
                    messageEditText.setText("");
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
    };



    public void loadChat(){
        String stringDate = getIntent().getStringExtra("date");
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        try{
            Date date = dateFormat.parse(stringDate);
            SimpleDateFormat databaseDateFormat = new SimpleDateFormat("yyyyMMdd");
            String databaseDate = databaseDateFormat.format(date);

            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
            String course = getIntent().getStringExtra("course");
            Query mQuery = mDatabase.child("chats").child(course).child(databaseDate);
            mQuery.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    Message message = dataSnapshot.getValue(Message.class);
                    messageAdapter.addMessage(message);
                    chatRecycleView.scrollToPosition(messageAdapter.messages.size() - 1);
                    progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }catch (ParseException e){
            e.printStackTrace();
        }

    }

}