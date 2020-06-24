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

        Query mQuery = mDatabaseReference.child("trainingProgramms").child("MassGain").limitToLast(7);
        mQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<String> dates = new ArrayList<>();
                List<String> daysOfWeek = new ArrayList<>();
                DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
                SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
                Calendar calendar = Calendar.getInstance();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    try {
                        Date date = dateFormat.parse(snapshot.getKey());
                        String dateString = sdf.format(date);
                        dates.add(dateString);
                        calendar.setTime(date);
                        String dayOfWeek = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, new Locale("ru", "RU"));
                        String capitalizedDayOfWeek = dayOfWeek.substring(0, 1).toUpperCase() + dayOfWeek.substring(1);
                        daysOfWeek.add(capitalizedDayOfWeek);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }

                MyAdapter adapter = new MyAdapter(MassGainActivity.this, dates, daysOfWeek);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        TextView dateTextView = view.findViewById(R.id.dateTextView);
                        String date = dateTextView.getText().toString();
                        Intent intent = new Intent(MassGainActivity.this, TrainingActivity.class);
                        intent.putExtra("date", date);
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    class MyAdapter extends ArrayAdapter<String>{

        Context context;
        List<String> dates;
        List<String> daysOfWeek;

        MyAdapter(Context context, List<String> dates, List<String> daysOfWeek){
            super(context, R.layout.row, R.id.dateTextView, dates);
            this.context = context;
            this.dates = dates;
            this.daysOfWeek = daysOfWeek;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater =(LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.row, parent, false);
            TextView dateTextView = row.findViewById(R.id.dateTextView);
            TextView dayOfWeekTextView = row.findViewById(R.id.dayOfWeekTextView);

            dateTextView.setText(dates.get(position));
            dayOfWeekTextView.setText(daysOfWeek.get(position));

            return row;
        }
    }
}