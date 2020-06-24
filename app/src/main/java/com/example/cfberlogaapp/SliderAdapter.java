package com.example.cfberlogaapp;

import android.content.Context;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager2.widget.ViewPager2;

import java.util.List;
import java.util.zip.Inflater;

public class SliderAdapter extends RecyclerView.Adapter<SliderAdapter.SliderHolder> {

    private List<TrainingProgramm> programms;
    private ViewPager2 viewPager;


    public SliderAdapter(List<TrainingProgramm> programms, ViewPager2 viewPager) {
        this.programms=programms;
        this.viewPager=viewPager;
    }

    @NonNull
    @Override
    public SliderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SliderHolder(LayoutInflater.from(parent.getContext()).inflate(
                R.layout.slider_layout, parent, false
        ));
    }

    @Override
    public void onBindViewHolder(@NonNull SliderHolder holder, int position) {
        holder.setText(programms.get(position));
    }

    @Override
    public int getItemCount() {
        return programms.size();
    }

    class SliderHolder extends RecyclerView.ViewHolder{

        private TextView dateTextView;
        private TextView progTextView;

        public SliderHolder(@NonNull View itemView) {
            super(itemView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
            progTextView = itemView.findViewById(R.id.progTextView);
            progTextView.setMovementMethod(LinkMovementMethod.getInstance());
        }

        void setText(TrainingProgramm trainingProgramm){
            dateTextView.setText(trainingProgramm.date);
            progTextView.setText(trainingProgramm.programm);
        }
    }
}
