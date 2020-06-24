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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
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
                            mDatabase.child("users").child(mAuth.getUid()).child(exrsz).setValue(resultEditText.getText().toString());
                            Toast.makeText(getActivity(), "Информация успешно обновлена!", Toast.LENGTH_SHORT).show();
                        }

                    }
                });


        return  builder.create();
    }
}
