package com.example.cfberlogaapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import jp.wasabeef.richeditor.RichEditor;

public class AdminFragment extends Fragment {



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

        return rootView;
    }



}