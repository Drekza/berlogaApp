package com.example.cfberlogaapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.github.mikephil.charting.data.Entry;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;
import java.util.Map;

public class HistoryActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private String Exercise;

    private Double findMin(List<String> array){
        double min = Double.parseDouble(array.get(0));
        for(String num : array){
            if(Double.parseDouble(num) < min){
                min = Double.parseDouble(num);
            }
        }
        return min;
    }

    private Double findMax(List<String> array){
        Double max = Double.parseDouble(array.get(0));
        for(String num : array){
            if(Double.parseDouble(num) > max){
                max = Double.parseDouble(num);
            }
        }
        return max;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        Exercise = getIntent().getStringExtra("Exercise");
        setData();

    }

    public void setData(){

        Query myQuery = mDatabase;
        String userID = mAuth.getUid();



        mDatabase.child("history").child(mAuth.getUid()).orderByValue().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<String> info = new ArrayList<>();
                final List<Date> dates = new ArrayList<>();
                final List<String> dateStrings = new ArrayList<>();
                SimpleDateFormat sdf = new SimpleDateFormat("dd.MM", Locale.getDefault());
                for(DataSnapshot child : dataSnapshot.getChildren()){
                    try {
                        info.add(dataSnapshot.child(child.getKey()).child(Exercise).getValue(String.class));
                        DateFormat format = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
                        Date date = format.parse(child.getKey());
                        dates.add(date);
                        dateStrings.add(sdf.format(date));

                    }catch(Exception ex){
                        new AlertDialog.Builder(HistoryActivity.this)
                                .setTitle("Error")
                                .setMessage(ex.getMessage()).show();
                    }
                }


                try{
                    LineChart mChart = (LineChart) findViewById(R.id.lineChart);
                    List<Entry> entries = new ArrayList<>();
                    int i = 0;
                    float previous = 0;
                    for(String line : info){
                        if(Float.parseFloat(line) != previous){
                            entries.add(new Entry(i, Float.parseFloat(line)));
                            i++;
                            previous = Float.parseFloat(line);
                        }
                    }
                    mChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
                    mChart.getLegend().setEnabled(false);
                    mChart.setGridBackgroundColor(getResources().getColor(R.color.colorDetailsText));
                    mChart.getXAxis().setTextColor(getResources().getColor(R.color.colorDetailsText));
                    mChart.getAxisLeft().setTextColor(getResources().getColor(R.color.colorDetailsText));
                    mChart.getAxisRight().setTextColor(getResources().getColor(R.color.colorDetailsText));


                    if(entries.size() > 1){
                        LineDataSet dataSet = new LineDataSet(entries, "");
                        LineData data = new LineData(dataSet);
                        mChart.getXAxis().setValueFormatter(new ValueFormatter() {
                            @Override
                            public String getFormattedValue(float value) {
                                return dateStrings.get((int) value);
                            }
                        });
                        mChart.getXAxis().setTextSize(12);
                        mChart.getDescription().setEnabled(false);
                        dataSet.setValueTextSize(12);
                        data.setValueTextColor(getResources().getColor(R.color.colorDetailsText));
                        mChart.setData(data);
                        mChart.invalidate();
                    }
                    else{
                        mChart.setNoDataText("Нехватает информации");
                        mChart.setNoDataTextColor(Color.RED);
                        mChart.setNoDataTextTypeface(Typeface.MONOSPACE);
                        mChart.invalidate();
                    }


                }catch (Exception ex){
                    throw ex;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void onBackBtnClicked(View view){
        finish();
    }
}