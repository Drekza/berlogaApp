package com.example.cfberlogaapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.ViewUtils;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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

import static android.content.Context.LAYOUT_INFLATER_SERVICE;


public class MassGainFragment extends Fragment {

    private ListView listView;
    private ProgressBar progressBar;
    private DatabaseReference mDatabaseReference;
    private FirebaseAuth mAuth;
    private ImageButton backBtn;
    private MyAdapter adapter;



    public MassGainFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();

        progressBar.setVisibility(View.VISIBLE);
        listView.setVisibility(View.INVISIBLE);
        setProgrammList();
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

        final MyAdapter adapter = new MyAdapter(getContext(), new ArrayList<TrainingProgramm>());
        Query mQuery = mDatabaseReference.child("trainingProgramms").child("MassGain").limitToLast(7);
        mQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final List<TrainingProgramm> trainingProgramms = new ArrayList<>();
                DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
                SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
                Calendar calendar = Calendar.getInstance();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    try {
                        Date date = dateFormat.parse(snapshot.getKey());
                        final String dateString = sdf.format(date);

                        calendar.setTime(date);
                        String dayOfWeek = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, new Locale("ru", "RU"));
                        final String capitalizedDayOfWeek = dayOfWeek.substring(0, 1).toUpperCase() + dayOfWeek.substring(1);

                        final String databaseDate = dateFormat.format(date);

                        Query query = mDatabaseReference.child("users").child(mAuth.getUid()).child("CompletedExrs").child("MassGain");
                        query.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                TrainingProgramm trainingProgramm = new TrainingProgramm(dateString, capitalizedDayOfWeek);
                                if(dataSnapshot.hasChild(databaseDate)){
                                    trainingProgramm.setCompleted(true);
                                }else{
                                    trainingProgramm.setCompleted(false);
                                }
                                adapter.addTrainingProgramm(trainingProgramm);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });


                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }




                listView.setAdapter(adapter);
                progressBar.setVisibility(View.INVISIBLE);
                listView.setVisibility(View.VISIBLE);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        TextView dateTextView = view.findViewById(R.id.dateTextView);
                        String date = dateTextView.getText().toString();
                        Intent intent = new Intent(getContext(), TrainingActivity.class);
                        intent.putExtra("course", "MassGain");
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
        List<TrainingProgramm> trainingProgramms = new ArrayList<>();

        private void addTrainingProgramm(TrainingProgramm trainingProgramm){
            trainingProgramms.add(trainingProgramm);
            notifyDataSetChanged();
        }

        MyAdapter(Context context, List<TrainingProgramm> trainingProgramms){
            super(context, R.layout.completed_workout_layout);
            this.context = context;
            this.trainingProgramms = trainingProgramms;
        }

        @Override
        public int getCount() {
            return trainingProgramms.size();
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater =(LayoutInflater) getContext().getSystemService(LAYOUT_INFLATER_SERVICE);
            View view;

            if(trainingProgramms.get(position).isCompleted()){
                view = layoutInflater.inflate(R.layout.completed_workout_layout, parent, false);
                TextView dateTextView = view.findViewById(R.id.dateTextView);
                TextView dayOfWeekTextView = view.findViewById(R.id.dayOfWeekTextView);
                ImageView completedImageView = view.findViewById(R.id.doneImageView);
                dateTextView.setText(trainingProgramms.get(position).getDate());
                dayOfWeekTextView.setText(trainingProgramms.get(position).getDayOfWeek());
                completedImageView.setImageResource(R.drawable.ic_done);
            }else{
                view = layoutInflater.inflate(R.layout.completed_workout_layout, parent, false);
                TextView dateTextView = view.findViewById(R.id.dateTextView);
                TextView dayOfWeekTextView = view.findViewById(R.id.dayOfWeekTextView);
                ImageView completedImageView = view.findViewById(R.id.doneImageView);
                dateTextView.setText(trainingProgramms.get(position).getDate());
                dayOfWeekTextView.setText(trainingProgramms.get(position).getDayOfWeek());
                completedImageView.setImageResource(R.drawable.ic_outline_cancel_24);
            }
            return view;
        }
    }
}