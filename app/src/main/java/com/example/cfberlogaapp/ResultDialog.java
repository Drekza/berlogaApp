package com.example.cfberlogaapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.textfield.TextInputLayout;
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
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.ResultDialogStyle);

        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.result_dialog_layout, null);
        resultEditText = view.findViewById(R.id.exercizeEditText);
        Bundle bundle = getArguments();
        final String exrsz = bundle.getString("exrsz");
        String exrszName = bundle.getString("exrszName");
        TextInputLayout textInputLayout = view.findViewById(R.id.exercizeEditTextView);
        textInputLayout.setHint(exrszName);

        final FragmentManager fm = getParentFragmentManager();


        builder.setView(view)
                .setTitle("Результат")
                .setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Внести результат", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(!resultEditText.getText().toString().equals("")){
                            int newValue = Integer.parseInt(resultEditText.getText().toString());
                            uploadData(exrsz, newValue, fm);
                            Toast.makeText(getActivity(), "Информация успешно обновлена!", Toast.LENGTH_SHORT).show();
                        }

                    }
                });


        return  builder.create();
    }

    private void uploadData(final String exrsz, final int newValue, final FragmentManager fm){
        if(resultEditText.getText().toString() != ""){

            mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    User user  = dataSnapshot.child("users").child(mAuth.getUid()).getValue(User.class);
                    int oldValue = 0;
                    String exerciseName = "";
                    switch (exrsz) {
                        case "backSquat":
                            oldValue = Integer.parseInt(user.getBackSquat());
                            user.setBackSquat(resultEditText.getText().toString());
                            exerciseName = "Присед со штангой на спине";
                            break;
                        case "frontSquat":
                            oldValue = Integer.parseInt(user.getFrontSquat());
                            user.setFrontSquat(resultEditText.getText().toString());
                            exerciseName = "Присед со штангой на груди";
                            break;
                        case "overheadSquat":
                            oldValue = Integer.parseInt(user.getOverheadSquat());
                            user.setOverheadSquat(resultEditText.getText().toString());
                            exerciseName = "Присед оверхед";
                            break;
                        case "deadlift":
                            oldValue = Integer.parseInt(user.getDeadlift());
                            user.setDeadlift(resultEditText.getText().toString());
                            exerciseName = "Становая тяга";
                            break;
                        case "press":
                            oldValue = Integer.parseInt(user.getPress());
                            user.setPress(resultEditText.getText().toString());
                            exerciseName = "Жим стоя";
                            break;
                        case "benchPress":
                            oldValue = Integer.parseInt(user.getBenchPress());
                            user.setBenchPress(resultEditText.getText().toString());
                            exerciseName = "Жим лежа";
                            break;
                        case "pullUps":
                            oldValue = Integer.parseInt(user.getPullUps());
                            user.setPullUps(resultEditText.getText().toString());
                            exerciseName = "Подтягивания";
                            break;
                        case "c2bPullUps":
                            oldValue = Integer.parseInt(user.getC2bPullUps());
                            user.setC2bPullUps(resultEditText.getText().toString());
                            exerciseName = "Подтягивания до груди";
                            break;
                        case "hsPullUps":
                            oldValue = Integer.parseInt(user.getHsPullUps());
                            user.setHsPullUps(resultEditText.getText().toString());
                            exerciseName = "Отжимания в стойке на руках";
                            break;
                        case "ringsDips":
                            oldValue = Integer.parseInt(user.getRingsDips());
                            user.setRingsDips(resultEditText.getText().toString());
                            exerciseName = "Отжимания на кольцах";
                            break;
                        case "t2b":
                            oldValue = Integer.parseInt(user.getT2b());
                            user.setT2b(resultEditText.getText().toString());
                            exerciseName = "Носки к перекладине";
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

                    mDatabase.child("users").child(mAuth.getUid()).child(exrsz).setValue(String.valueOf(newValue));



                    if(oldValue < newValue){
                        Bundle bundle = new Bundle();
                        bundle.putInt("OldValue", oldValue);
                        bundle.putInt("NewValue", newValue);
                        bundle.putString("ExerciseName", exerciseName);
                        PersonalRecordDialog prDialog = new PersonalRecordDialog();
                        prDialog.setArguments(bundle);
                        prDialog.show(fm, "PR dialog");
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }
    }



}
