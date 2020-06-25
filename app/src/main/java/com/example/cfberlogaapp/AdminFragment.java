package com.example.cfberlogaapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import jp.wasabeef.richeditor.RichEditor;

public class AdminFragment extends Fragment {

    private RichEditor richEditor, richEditor2, richEditor3, richEditor4,
                    richEditor5, richEditor6, richEditor7;
    private Button saveProgBtn, saveProgBtn2, saveProgBtn3, saveProgBtn4, saveProgBtn5,
                 saveProgBtn6, saveProgBtn7;
    private TextView dateTextView, dateTextView2, dateTextView3, dateTextView4, dateTextView5,
            dateTextView6, dateTextView7;


    public AdminFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_admin, container, false);

        Button saveProgBtn = rootView.findViewById(R.id.saveProgBtn);
        saveProgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RichEditor mEditor = rootView.findViewById(R.id.richEditor);
                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                Date date = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
                String currentDate = sdf.format(date);
                mDatabase.child("trainingProgramms").child(currentDate).setValue(mEditor.getHtml());
                Toast.makeText(getActivity(), "Сохранено", Toast.LENGTH_SHORT).show();
                mEditor.undo();
            }
        });

        ImageButton adminInfoBtn = rootView.findViewById(R.id.adminInfoBtn);
        adminInfoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AdminInfoActivity.class));
            }
        });
        initView(rootView);
        loadDataIntoRichEdits();

        return rootView;
    }

    private void initView(View view){
        richEditor = view.findViewById(R.id.richEditor);
        richEditor2 = view.findViewById(R.id.richEditor2);
        richEditor3 = view.findViewById(R.id.richEditor3);
        richEditor4 = view.findViewById(R.id.richEditor4);
        richEditor5 = view.findViewById(R.id.richEditor5);
        richEditor6 = view.findViewById(R.id.richEditor6);
        richEditor7 = view.findViewById(R.id.richEditor7);

        saveProgBtn = view.findViewById(R.id.saveProgBtn);
        saveProgBtn2 = view.findViewById(R.id.saveProgBtn2);
        saveProgBtn3 = view.findViewById(R.id.saveProgBtn3);
        saveProgBtn4 = view.findViewById(R.id.saveProgBtn4);
        saveProgBtn5 = view.findViewById(R.id.saveProgBtn5);
        saveProgBtn6 = view.findViewById(R.id.saveProgBtn6);
        saveProgBtn7 = view.findViewById(R.id.saveProgBtn7);

        dateTextView = view.findViewById(R.id.dateTextView);
        dateTextView2 = view.findViewById(R.id.dateTextView2);
        dateTextView3 = view.findViewById(R.id.dateTextView3);
        dateTextView4 = view.findViewById(R.id.dateTextView4);
        dateTextView5 = view.findViewById(R.id.dateTextView5);
        dateTextView6 = view.findViewById(R.id.dateTextView6);
        dateTextView7 = view.findViewById(R.id.dateTextView7);
    }

    private void loadDataIntoRichEdits(){
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        SimpleDateFormat databaseSdf = new SimpleDateFormat("yyyyMMdd");
        List<String> dates = new ArrayList<>();
        List<String> databaseDates = new ArrayList<>();

        for(int i = 1; i <= 8; i++) {
            calendar.set(Calendar.DAY_OF_WEEK, i);
            dates.add(sdf.format(calendar.getTime()));
            databaseDates.add(databaseSdf.format(calendar.getTime()));
        }
        dateTextView.setText(dates.get(0));
        dateTextView2.setText(dates.get(1));
        dateTextView3.setText(dates.get(2));
        dateTextView4.setText(dates.get(3));
        dateTextView5.setText(dates.get(4));
        dateTextView6.setText(dates.get(5));
        dateTextView7.setText(dates.get(6));
    }



}