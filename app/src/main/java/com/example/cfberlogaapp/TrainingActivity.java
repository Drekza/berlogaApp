package com.example.cfberlogaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ImageSpan;
import android.view.View;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TrainingActivity extends AppCompatActivity {
    private TextView dateTextView;
    private TextView progTextView;
    private UserMaxes userMaxes;
    private String exrsz;
    private String exrszName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training);

        dateTextView = findViewById(R.id.dateTextView);
        progTextView = findViewById(R.id.progTextView);
        getTrainingProgramm();
    }


    private void getTrainingProgramm(){
        final String dateString = getIntent().getStringExtra("date");
        dateTextView.setText(dateString);



        final DatabaseReference mDatabase =FirebaseDatabase.getInstance().getReference();
        final FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user= dataSnapshot.child("users").child(mAuth.getUid()).getValue(User.class);
                if(user != null) {
                    userMaxes = new UserMaxes();
                    userMaxes.setBackSquat(user.backSquat);
                    userMaxes.setFrontSquat(user.frontSquat);
                    userMaxes.setOverheadSquat(user.overheadSquat);
                    userMaxes.setDeadlift(user.deadlift);
                    userMaxes.setPress(user.press);
                    userMaxes.setBenchPress(user.benchPress);
                    userMaxes.setC2bPullUps(user.c2bPullUps);
                    userMaxes.setPullUps(user.pullUps);
                    userMaxes.setHsPullUps(user.hsPullUps);
                    userMaxes.setRingsDips(user.ringsDips);
                    userMaxes.setT2b(user.t2b);
                    Query mQuery = mDatabase;
                    mQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            try {
                                Date date=new SimpleDateFormat("dd.MM.yyyy").parse(dateString);
                                SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
                                String newDate=sdf.format(date);
                                String trainingProgramm=dataSnapshot.child("trainingProgramms").child("MassGain").child(newDate).getValue(String.class);
                                progTextView.setMovementMethod(new LinkMovementMethod());
                                progTextView.setText(convertText(trainingProgramm, userMaxes));

                            } catch (ParseException e) {

                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public SpannableStringBuilder convertText(String prog, UserMaxes userMaxes){
        Spanned spannedProg = Html.fromHtml(prog);
        SpannableStringBuilder spannableProg = new SpannableStringBuilder(spannedProg);
        String[] lines;
        lines = spannableProg.toString().split("\n");

        for(String line : lines){
            final Matcher matcher =Pattern.compile("\\{(.*?)=(.*?)%\\}").matcher(line);
            final Matcher maxMatcher = Pattern.compile("\\{MAX=(.*?)\\}").matcher(line);
            final Matcher infoMatcher = Pattern.compile("\\{info_(.*?)\\}").matcher(line);
            if(userMaxes != null){
                if(matcher.find()){
                    String string = matcher.group(1);
                    int variable = 0;
                    switch (string){
                        case "back_squat": variable = Integer.parseInt(userMaxes.getBackSquat());
                            break;
                        case "front_squat": variable = Integer.parseInt(userMaxes.getFrontSquat());
                            break;
                        case "overhead_squat": variable = Integer.parseInt(userMaxes.getOverheadSquat());
                            break;
                        case "deadlift": variable = Integer.parseInt(userMaxes.getDeadlift());
                            break;
                        case "press": variable = Integer.parseInt(userMaxes.getPress());
                            break;
                        case "bench_press": variable = Integer.parseInt(userMaxes.getBenchPress());
                            break;
                        case "pull_ups": variable = Integer.parseInt(userMaxes.getPullUps());
                            break;
                        case "c2b_pull_ups":
                            variable = Integer.parseInt(userMaxes.getC2bPullUps());
                            break;
                        case "hs_push_ups":
                            variable = Integer.parseInt(userMaxes.getHsPullUps());
                            break;
                        case "rings_dips":
                            variable = Integer.parseInt(userMaxes.getRingsDips());
                            break;
                        case "t2b":
                            variable = Integer.parseInt(userMaxes.getT2b());
                            break;
                        default:
                            variable = 0;
                            break;
                    }
                    if(variable != 0){
                        int startIndex = spannableProg.toString().indexOf("{" + matcher.group(1) + "=" + matcher.group(2)+"%}");
                        int endIndex = spannableProg.toString().indexOf("{" + matcher.group(1) + "=" + matcher.group(2)+"%}") + ("{" + matcher.group(1) + "=" + matcher.group(2)+"%}").length();
                        int current = (int)(Float.parseFloat(matcher.group(2))/100*variable);
                        String currentString = String.valueOf(current);
                        spannableProg.replace(startIndex, endIndex, currentString);
                    }else{
                        int startIndex = spannableProg.toString().indexOf("{" + matcher.group(1) + "=" + matcher.group(2)+"%}");
                        int endIndex = spannableProg.toString().indexOf("{" + matcher.group(1) + "=" + matcher.group(2)+"%}") + ("{" + matcher.group(1) + "=" + matcher.group(2)+"%}").length();
                        spannableProg.replace(startIndex, endIndex, matcher.group(2));
                    }
                }

                if(maxMatcher.find()) {
                    String string=maxMatcher.group(1);
                    switch (string) {
                        case "back_squat":
                            exrsz = "backSquat";
                            exrszName = "Присед со штангой на спине";
                            break;
                        case "front_squat":
                            exrsz = "frontSquat";
                            exrszName = "Присед со штангой на груди";
                            break;
                        case "overhead_squat":
                            exrsz = "overheadSquat";
                            exrszName = "Присед оверхед";
                            break;
                        case "deadlift":
                            exrsz = "deadlift";
                            exrszName = "Становая тяга";
                            break;
                        case "press":
                            exrsz = "press";
                            exrszName = "Жим стоя";
                            break;
                        case "bench_press":
                            exrsz = "benchPress";
                            exrszName = "Жим лежа";
                            break;
                        case "pull_ups":
                            exrsz = "pullUps";
                            exrszName = "Подтягивания";
                            break;
                        case "c2b_pull_ups":
                            exrsz = "c2bPullUps";
                            exrszName = "Подтягивания до груди";
                            break;
                        case "hs_push_ups":
                            exrsz = "hsPullUps";
                            exrszName = "Отжимания в стойке на руках";
                            break;
                        case "rings_dips":
                            exrsz = "ringsDips";
                            exrszName = "Отжимания на кольцах";
                            break;
                        case "t2b":
                            exrsz = "t2b";
                            exrszName = "Носки к перекладине";
                            break;
                        default:
                            break;
                    }
                    int startIndex = spannableProg.toString().indexOf("{MAX=" + maxMatcher.group(1) + "}");
                    int endIndex = spannableProg.toString().indexOf("{MAX=" + maxMatcher.group(1) + "}") + ("{MAX=" + maxMatcher.group(1) + "}").length();
                    ImageSpan resultImageSpan = new ImageSpan(this, R.drawable.ic_baseline_input_24, ImageSpan.ALIGN_BOTTOM);
                    ClickableSpan resultClickableSpan = new ClickableSpan() {
                        @Override
                        public void onClick(@NonNull View widget) {
                            ResultDialog resultDialog = new ResultDialog();
                            Bundle bundle = new Bundle();
                            bundle.putString("exrsz", exrsz);
                            bundle.putString("exrszName", exrszName);
                            resultDialog.setArguments(bundle);
                            resultDialog.show(getSupportFragmentManager(), "Result dialog");
                        }
                    };
                    spannableProg.setSpan(resultImageSpan, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    spannableProg.setSpan(resultClickableSpan, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
                if(infoMatcher.find()){
                    Matcher textToLinkMathcer = Pattern.compile("#(.*?)#").matcher(line);
                    int startIndex = spannableProg.toString().indexOf("{info_"+infoMatcher.group(1)+"}");
                    int endIndex = startIndex + ("{info_"+infoMatcher.group(1)+"}").length();
                    spannableProg.replace(startIndex, endIndex, "");
                    if(textToLinkMathcer.find()){
                        int startIndexOfTextToReplace = spannableProg.toString().indexOf("#"+textToLinkMathcer.group(1)+"#");
                        int endIndexOfTextToReplace = startIndexOfTextToReplace + ("#"+textToLinkMathcer.group(1)+"#").length();
                        spannableProg.replace(startIndexOfTextToReplace, endIndexOfTextToReplace, textToLinkMathcer.group(1));
                        int startIndexOfTextToLink = spannableProg.toString().indexOf(textToLinkMathcer.group(1));
                        int endIndexOfTextToLink = startIndexOfTextToLink + textToLinkMathcer.group(1).length();
                        ClickableSpan infoClickableSpan = new ClickableSpan() {
                            @Override
                            public void onClick(@NonNull View widget) {
                                Intent intent = new Intent(TrainingActivity.this, InfoVideoActivity.class);
                                intent.putExtra("exrsz", infoMatcher.group(1));
                                startActivity(intent);
                            }
                        };
                        spannableProg.setSpan(infoClickableSpan, startIndexOfTextToLink, endIndexOfTextToLink, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    }
                }
            }else{
                if(matcher.find()){
                    int startIndex = spannableProg.toString().indexOf("{" + matcher.group(1) + "=" + matcher.group(2)+"%}");
                    int endIndex = spannableProg.toString().indexOf("{" + matcher.group(1) + "=" + matcher.group(2)+"%}") + ("{" + matcher.group(1) + "=" + matcher.group(2)+"%}").length();
                    spannableProg.replace(startIndex, endIndex, matcher.group(2));
                    //                        Toast.makeText(getActivity(), "{" + matcher.group(1) + "=" + matcher.group(2)+"%}", Toast.LENGTH_SHORT).show();
                }
            }
        }

        return spannableProg;
    }

}