package com.example.cfberlogaapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import jp.wasabeef.richeditor.RichEditor;

public class AdminFragment<string> extends Fragment {

    private RichEditor richEditor, richEditor2, richEditor3, richEditor4,
                    richEditor5, richEditor6, richEditor7;
    private Button saveProgBtn, saveProgBtn2, saveProgBtn3, saveProgBtn4, saveProgBtn5,
                 saveProgBtn6, saveProgBtn7;
    private TextView dateTextView, dateTextView2, dateTextView3, dateTextView4, dateTextView5,
            dateTextView6, dateTextView7;
    private ImageButton boldTextBtn, italicTextBtn, fontSizeIncreaseBtn, fontSizeDecreaseBtn;
    private List<String> databaseDates;
    private Spinner spinner;
    private String[] option = {"Набор массы","Сброс веса"};
    private int currentFontSize = 3;
    private boolean isBold = false;
    private boolean isItalic = false;


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



        spinner = rootView.findViewById(R.id.spinner);
        ArrayAdapter aa = new ArrayAdapter(getContext(),R.layout.spinner_item,option);
        aa.setDropDownViewResource(R.layout.spinner_item);
        spinner.setAdapter(aa);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0) {
                    loadDataIntoRichEdits("MassGain");
                }
                if(position == 1){
                    loadDataIntoRichEdits("LosingWeight");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



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
        loadDataIntoRichEdits("MassGain");

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

        boldTextBtn = view.findViewById(R.id.boldTextBtn);
        italicTextBtn = view.findViewById(R.id.italicTextBtn);
        fontSizeIncreaseBtn = view.findViewById(R.id.fontSizeIncreaseBtn);
        fontSizeDecreaseBtn = view.findViewById(R.id.fontSizeDecreaseBtn);

        setOnBoldBtnClicked(boldTextBtn, richEditor);
        setOnItalicBtnClicked(italicTextBtn, richEditor);
        setOnFontSizeIncreaseClicked(fontSizeIncreaseBtn, richEditor);
        setOnFontSizeDecreaseClicked(fontSizeDecreaseBtn, richEditor);


    }

    private void loadDataIntoRichEdits(String course){
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        final SimpleDateFormat databaseSdf = new SimpleDateFormat("yyyyMMdd");
        List<String> dates = new ArrayList<>();
        databaseDates = new ArrayList<>();


        for(int i = 2; i <= 9; i++) {
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

        final List<RichEditor> editors = new ArrayList<>();
        editors.add(richEditor);
        editors.add(richEditor2);
        editors.add(richEditor3);
        editors.add(richEditor4);
        editors.add(richEditor5);
        editors.add(richEditor6);
        editors.add(richEditor7);
        for(RichEditor editor : editors) {
            editor.setHtml("");
        }

        Query mQuery = mDatabase.child("trainingProgramms").child(course).limitToLast(8);
        mQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    if(databaseDates.contains(snapshot.getKey())){
                        int index = databaseDates.indexOf(snapshot.getKey());
                        String prog = snapshot.getValue(String.class);
                        editors.get(index).setHtml(prog);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void setOnFontSizeIncreaseClicked(ImageButton increaseBtn, final RichEditor editor){
        increaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentFontSize < 7){
                    currentFontSize += 1;
                    editor.setFontSize(currentFontSize);
                }else{
                    Toast.makeText(getActivity(), "Достигнут максимальный размер текста", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void setOnBoldBtnClicked(ImageButton boldBtn, final RichEditor editor){
        boldBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isBold = !isBold;
                editor.setBold();
                if(isBold){
                    boldTextBtn.setBackgroundResource(R.color.buttonColor2);
                }else{
                    boldTextBtn.setBackgroundResource(R.color.buttonColor1);
                }
            }
        });
    }

    public void setOnItalicBtnClicked(ImageButton italicBtn, final RichEditor editor) {
        italicBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isItalic = !isItalic;
                editor.setItalic();
                if(isItalic){
                    italicTextBtn.setBackgroundResource(R.color.buttonColor2);
                }else{
                    italicTextBtn.setBackgroundResource(R.color.buttonColor1);
                }
            }
        });

    }

    public void setOnFontSizeDecreaseClicked(ImageButton fontDecreaseBtn, final RichEditor editor){
        fontDecreaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentFontSize > 1){
                    currentFontSize -= 1;
                    editor.setFontSize(currentFontSize);
                }else{
                    Toast.makeText(getActivity(), "Достигнут минимальный размер текста", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void onSaveProgClicked(Button saveProgBtn, RichEditor editor){
        saveProgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
            }
        });
    }
}