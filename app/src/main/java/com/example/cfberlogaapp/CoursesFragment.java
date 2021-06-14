package com.example.cfberlogaapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.print.PrinterId;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;


public class CoursesFragment extends Fragment {

    private ImageView massGainImageView, weightLossImageView;
    private ProgressBar progressBar;


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
        massGainImageView = rootView.findViewById(R.id.naborMassiImg);
        weightLossImageView = rootView.findViewById(R.id.sbrosVesaImg);
        weightLossImageView.setOnClickListener(onLosingWeightImageViewClicked);
        progressBar = rootView.findViewById(R.id.progressBar);
        massGainImageView.setOnClickListener(onMassGainImageViewClicked);
        progressBar.setVisibility(View.VISIBLE);
        loadPictures();
        return rootView;
    }

    private ImageView.OnClickListener onMassGainImageViewClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new MassGainFragment()).addToBackStack(null).commit();
        }
    };

    private ImageView.OnClickListener onLosingWeightImageViewClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new LosingWeightFragment()).addToBackStack(null).commit();
        }
    };

    public void loadPictures(){
        massGainImageView.setImageDrawable(getResources().getDrawable(R.drawable.nabor_massi));
        weightLossImageView.setImageDrawable(getResources().getDrawable(R.drawable.sbros_vesa));
        progressBar.setVisibility(View.INVISIBLE);
    }
}