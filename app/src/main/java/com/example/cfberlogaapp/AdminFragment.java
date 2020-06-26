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

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.ParseException;
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
    private ImageButton boldTextBtn, italicTextBtn, fontSizeIncreaseBtn, fontSizeDecreaseBtn,
            boldTextBtn2, italicTextBtn2, fontSizeIncreaseBtn2, fontSizeDecreaseBtn2,
            boldTextBtn3, italicTextBtn3, fontSizeIncreaseBtn3, fontSizeDecreaseBtn3,
            boldTextBtn4, italicTextBtn4, fontSizeIncreaseBtn4, fontSizeDecreaseBtn4,
            boldTextBtn5, italicTextBtn5, fontSizeIncreaseBtn5, fontSizeDecreaseBtn5,
            boldTextBtn6, italicTextBtn6, fontSizeIncreaseBtn6, fontSizeDecreaseBtn6,
            boldTextBtn7, italicTextBtn7, fontSizeIncreaseBtn7, fontSizeDecreaseBtn7;
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

        initView(rootView);


        return rootView;
    }

    private void initView(View view){
        ImageButton adminInfoBtn = view.findViewById(R.id.adminInfoBtn);
        adminInfoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AdminInfoActivity.class));
            }
        });

        spinner = view.findViewById(R.id.spinner);
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
        boldTextBtn2 = view.findViewById(R.id.boldTextBtn2);
        boldTextBtn3 = view.findViewById(R.id.boldTextBtn3);
        boldTextBtn4 = view.findViewById(R.id.boldTextBtn4);
        boldTextBtn5 = view.findViewById(R.id.boldTextBtn5);
        boldTextBtn6 = view.findViewById(R.id.boldTextBtn6);
        boldTextBtn7 = view.findViewById(R.id.boldTextBtn7);
        italicTextBtn = view.findViewById(R.id.italicTextBtn);
        italicTextBtn2 = view.findViewById(R.id.italicTextBtn2);
        italicTextBtn3 = view.findViewById(R.id.italicTextBtn3);
        italicTextBtn4 = view.findViewById(R.id.italicTextBtn4);
        italicTextBtn5 = view.findViewById(R.id.italicTextBtn5);
        italicTextBtn6 = view.findViewById(R.id.italicTextBtn6);
        italicTextBtn7 = view.findViewById(R.id.italicTextBtn7);
        fontSizeIncreaseBtn = view.findViewById(R.id.fontSizeIncreaseBtn);
        fontSizeIncreaseBtn2 = view.findViewById(R.id.fontSizeIncreaseBtn2);
        fontSizeIncreaseBtn3 = view.findViewById(R.id.fontSizeIncreaseBtn3);
        fontSizeIncreaseBtn4 = view.findViewById(R.id.fontSizeIncreaseBtn4);
        fontSizeIncreaseBtn5 = view.findViewById(R.id.fontSizeIncreaseBtn5);
        fontSizeIncreaseBtn6 = view.findViewById(R.id.fontSizeIncreaseBtn6);
        fontSizeIncreaseBtn7 = view.findViewById(R.id.fontSizeIncreaseBtn7);
        fontSizeDecreaseBtn = view.findViewById(R.id.fontSizeDecreaseBtn);
        fontSizeDecreaseBtn2 = view.findViewById(R.id.fontSizeDecreaseBtn2);
        fontSizeDecreaseBtn3 = view.findViewById(R.id.fontSizeDecreaseBtn3);
        fontSizeDecreaseBtn4 = view.findViewById(R.id.fontSizeDecreaseBtn4);
        fontSizeDecreaseBtn5 = view.findViewById(R.id.fontSizeDecreaseBtn5);
        fontSizeDecreaseBtn6 = view.findViewById(R.id.fontSizeDecreaseBtn6);
        fontSizeDecreaseBtn7 = view.findViewById(R.id.fontSizeDecreaseBtn7);

        setOnBoldBtnClicked(boldTextBtn, richEditor);
        setOnBoldBtnClicked(boldTextBtn2, richEditor2);
        setOnBoldBtnClicked(boldTextBtn3, richEditor3);
        setOnBoldBtnClicked(boldTextBtn4, richEditor4);
        setOnBoldBtnClicked(boldTextBtn5, richEditor5);
        setOnBoldBtnClicked(boldTextBtn6, richEditor6);
        setOnBoldBtnClicked(boldTextBtn7, richEditor7);
        setOnItalicBtnClicked(italicTextBtn, richEditor);
        setOnItalicBtnClicked(italicTextBtn2, richEditor2);
        setOnItalicBtnClicked(italicTextBtn3, richEditor3);
        setOnItalicBtnClicked(italicTextBtn4, richEditor4);
        setOnItalicBtnClicked(italicTextBtn5, richEditor5);
        setOnItalicBtnClicked(italicTextBtn6, richEditor6);
        setOnItalicBtnClicked(italicTextBtn7, richEditor7);
        setOnFontSizeIncreaseClicked(fontSizeIncreaseBtn, richEditor);
        setOnFontSizeIncreaseClicked(fontSizeIncreaseBtn2, richEditor2);
        setOnFontSizeIncreaseClicked(fontSizeIncreaseBtn3, richEditor3);
        setOnFontSizeIncreaseClicked(fontSizeIncreaseBtn4, richEditor4);
        setOnFontSizeIncreaseClicked(fontSizeIncreaseBtn5, richEditor5);
        setOnFontSizeIncreaseClicked(fontSizeIncreaseBtn6, richEditor6);
        setOnFontSizeIncreaseClicked(fontSizeIncreaseBtn7, richEditor7);
        setOnFontSizeDecreaseClicked(fontSizeDecreaseBtn, richEditor);
        setOnFontSizeDecreaseClicked(fontSizeDecreaseBtn2, richEditor2);
        setOnFontSizeDecreaseClicked(fontSizeDecreaseBtn3, richEditor3);
        setOnFontSizeDecreaseClicked(fontSizeDecreaseBtn4, richEditor4);
        setOnFontSizeDecreaseClicked(fontSizeDecreaseBtn5, richEditor5);
        setOnFontSizeDecreaseClicked(fontSizeDecreaseBtn6, richEditor6);
        setOnFontSizeDecreaseClicked(fontSizeDecreaseBtn7, richEditor7);


        setOnSaveProgClicked(saveProgBtn, richEditor, dateTextView);
        setOnSaveProgClicked(saveProgBtn2, richEditor2, dateTextView2);
        setOnSaveProgClicked(saveProgBtn3, richEditor3, dateTextView3);
        setOnSaveProgClicked(saveProgBtn4, richEditor4, dateTextView4);
        setOnSaveProgClicked(saveProgBtn5, richEditor5, dateTextView5);
        setOnSaveProgClicked(saveProgBtn6, richEditor6, dateTextView6);
        setOnSaveProgClicked(saveProgBtn7, richEditor7, dateTextView7);

        loadDataIntoRichEdits("MassGain");
    }



    private void loadDataIntoRichEdits(String course){
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        final SimpleDateFormat databaseSdf = new SimpleDateFormat("yyyyMMdd");
        List<String> dates = new ArrayList<>();
        databaseDates = new ArrayList<>();


        for(int i = 1; i <= 7; i++) {
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


        Query mQuery = mDatabase.child("trainingProgramms").child(course).limitToLast(8);
        mQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Integer> filledEditors = new ArrayList<>();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    if(databaseDates.contains(snapshot.getKey())){
                        int index = databaseDates.indexOf(snapshot.getKey());
                        String prog = snapshot.getValue(String.class);
                        editors.get(index).setHtml(prog);
                        filledEditors.add(index);
                    }
                }
                for(int i = 0; i<editors.size()-1; i++){
                    if(!filledEditors.contains(i)){
                        editors.get(i).setHtml("");
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

    public void setOnSaveProgClicked(Button saveProgBtn, final RichEditor editor, final TextView dateTextView){

        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        final DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

        saveProgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "PENS", Toast.LENGTH_SHORT).show();
                Date date =null;
                try {
                    date=dateFormat.parse(dateTextView.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                final String databaseString = sdf.format(date);
                ValueEventListener eventListener;
                int id =(int) spinner.getSelectedItemId();
                switch(id){
                    case 0:
                        eventListener = new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                mDatabase.child("trainingProgramms").child("MassGain").child(databaseString).setValue(editor.getHtml());
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        };
                        mDatabase.addListenerForSingleValueEvent(eventListener);
                        break;
                    case 1:
                        eventListener = new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                mDatabase.child("trainingProgramms").child("LosingWeight").child(databaseString).setValue(editor.getHtml());
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        };
                        mDatabase.addListenerForSingleValueEvent(eventListener);
                        break;
                }
            }
        });
    }
}