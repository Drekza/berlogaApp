package com.example.cfberlogaapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.print.PrinterId;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


public class CoursesFragment extends Fragment {




    public CoursesFragment() {
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
        View rootView = inflater.inflate(R.layout.fragment_courses, container, false);
        ImageView massGainImageView = rootView.findViewById(R.id.naborMassiImg);
        massGainImageView.setOnClickListener(onMassGainImageViewClicked);

        return rootView;
    }

    private ImageView.OnClickListener onMassGainImageViewClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new MassGainFragment()).commit();
        }
    };
}