package com.example.cfberlogaapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class PersonalRecordDialog extends AppCompatDialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.ResultDialogStyle);
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.personal_record_layout, null);
        Bundle bundle = getArguments();
        int oldValue = bundle.getInt("OldValue");
        int newValue = bundle.getInt("NewValue");
        String exerciseName = bundle.getString("ExerciseName");
        TextView oldValueTextView = view.findViewById(R.id.oldValueTextView);
        TextView newValueTextView = view.findViewById(R.id.newValueTextView);
        TextView exerciseNameTextView = view.findViewById(R.id.exerciseNameTextView);

        oldValueTextView.setText(String.valueOf(oldValue));
        newValueTextView.setText(String.valueOf(newValue));
        exerciseNameTextView.setText(exerciseName);
        builder.setView(view)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });


        return builder.create();
    }
}
