package com.example.cfberlogaapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
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


public class MassGainFragment extends Fragment {

    private ListView listView;
    private ProgressBar progressBar;
    private DatabaseReference mDatabaseReference;
    private FirebaseAuth mAuth;
    private ImageButton backBtn;



    public MassGainFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_mass_gain, container, false);

        mDatabaseReference=FirebaseDatabase.getInstance().getReference();
        mAuth=FirebaseAuth.getInstance();
        listView = rootView.findViewById(R.id.listView);
        backBtn = rootView.findViewById(R.id.backBtn);
        backBtn.setOnClickListener(onBackBtnClicked);
        progressBar = rootView.findViewById(R.id.progressBar);

        progressBar.setVisibility(View.VISIBLE);
        setProgrammList();

        return rootView;
    }

    private ImageButton.OnClickListener onBackBtnClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new CoursesFragment()).commit();
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

                MyAdapter adapter = new MyAdapter(getContext(), dates, daysOfWeek);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        TextView dateTextView = view.findViewById(R.id.dateTextView);
                        String date = dateTextView.getText().toString();
                        Intent intent = new Intent(getContext(), TrainingActivity.class);
                        intent.putExtra("date", date);
                        intent.putExtra("course", "MassGain");
                        startActivity(intent);
                    }
                });
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    class MyAdapter extends ArrayAdapter<String> {

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
            LayoutInflater layoutInflater =(LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.row, parent, false);
            TextView dateTextView = row.findViewById(R.id.dateTextView);
            TextView dayOfWeekTextView = row.findViewById(R.id.dayOfWeekTextView);

            dateTextView.setText(dates.get(position));
            dayOfWeekTextView.setText(daysOfWeek.get(position));

            return row;
        }
    }
}