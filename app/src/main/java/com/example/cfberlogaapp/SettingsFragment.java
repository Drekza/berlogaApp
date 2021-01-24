package com.example.cfberlogaapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;


public class SettingsFragment extends Fragment {



    public SettingsFragment() {
        // Required empty public constructor
    }

    private FirebaseAuth mAuth;
    private Button quitBtn;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);

        quitBtn = rootView.findViewById(R.id.quitBtn);
        quitBtn.setOnClickListener(quitBtnOnClickListener);

        return rootView;
    }

    private Button.OnClickListener quitBtnOnClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            try {
                mAuth.signOut();
                Intent intent = new Intent(getContext(), LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

            }catch(Exception ex){
                Toast.makeText(getActivity(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        }
    };
}