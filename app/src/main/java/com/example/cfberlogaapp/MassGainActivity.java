package com.example.cfberlogaapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import io.opencensus.internal.StringUtils;

public class MassGainActivity extends AppCompatActivity {

    private ListView listView;
    private DatabaseReference mDatabaseReference;
    private FirebaseAuth mAuth;
    private ImageButton backBtn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mass_gain);

        mDatabaseReference=FirebaseDatabase.getInstance().getReference();
        mAuth=FirebaseAuth.getInstance();
        listView = findViewById(R.id.listView);
        backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(onBackBtnClicked);
        setProgrammList();
    }

    private ImageButton.OnClickListener onBackBtnClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };

    private void setProgrammList(){


    }
}