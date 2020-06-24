package com.example.cfberlogaapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class ResultDialog extends AppCompatDialogFragment {

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private EditText resultEditText;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.result_dialog_layout, null);
        resultEditText = view.findViewById(R.id.resultEditText);
        Bundle bundle = getArguments();
        final String exrsz = bundle.getString("exrsz");
        String exrszName = bundle.getString("exrszName");
        TextView textView = view.findViewById(R.id.textView2);
        textView.setText(exrszName);
        builder.setView(view)
                .setTitle("Результат")
                .setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Внести резуьтат", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(resultEditText.getText().toString() != ""){


                            mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    User user  = dataSnapshot.child("users").child(mAuth.getUid()).getValue(User.class);
                                    switch (exrsz) {
                                        case "backSquat":
                                            user.setBackSquat(resultEditText.getText().toString());
                                            break;
                                        case "frontSquat":
                                            user.setFrontSquat(resultEditText.getText().toString());
                                            break;
                                        case "overheadSquat":
                                            user.setOverheadSquat(resultEditText.getText().toString());
                                            break;
                                        case "deadlift":
                                            user.setDeadlift(resultEditText.getText().toString());
                                            break;
                                        case "press":
                                            user.setPress(resultEditText.getText().toString());
                                            break;
                                        case "benchPress":
                                            user.setBenchPress(resultEditText.getText().toString());
                                            break;
                                        case "pullUps":
                                            user.setPullUps(resultEditText.getText().toString());
                                            break;
                                        case "c2bPullUps":
                                            user.setC2bPullUps(resultEditText.getText().toString());
                                            break;
                                        case "hsPullUps":
                                            user.setHsPullUps(resultEditText.getText().toString());
                                            break;
                                        case "ringsDips":
                                            user.setRingsDips(resultEditText.getText().toString());
                                            break;
                                        case "t2b":
                                            user.setT2b(resultEditText.getText().toString());
                                            break;
                                        default:
                                            break;
                                    }
                                    User historyUser = new User(user.getWeight(),user.getShoulders(),user.getChest(),user.getBiceps(),
                                            user.getWaist(),user.getHip(),user.getHips(),user.getBackSquat(),user.getFrontSquat(),user.getOverheadSquat(),
                                            user.getDeadlift(),user.getBenchPress(),user.getPress(),user.getPullUps(),user.getC2bPullUps(),user.getHsPullUps(),
                                            user.getRingsDips(),user.getT2b());
                                    Date currentDate = new Date();
                                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
                                    String currentDateText = dateFormat.format(currentDate);
                                    mDatabase.child("history").child(mAuth.getUid()).child(currentDateText).setValue(historyUser);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });

                            mDatabase.child("users").child(mAuth.getUid()).child(exrsz).setValue(resultEditText.getText().toString());
                            Toast.makeText(getActivity(), "Информация успешно обновлена!", Toast.LENGTH_SHORT).show();
                        }

                    }
                });


        return  builder.create();
    }
}
