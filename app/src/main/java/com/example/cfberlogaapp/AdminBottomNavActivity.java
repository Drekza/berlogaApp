package com.example.cfberlogaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import jp.wasabeef.richeditor.RichEditor;

public class AdminBottomNavActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private int currentFontSize = 3;
    private boolean isBold = false;
    private boolean isItalic = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_bottom_nav);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        BottomNavigationView bottomNavView = findViewById(R.id.bottomNavView);
        bottomNavView.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new CoursesFragment()).commit();

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment selectedFragment = null;

            switch (menuItem.getItemId()){
                case R.id.menuTraining:
                    selectedFragment = new CoursesFragment();
                    break;
                case R.id.menuCabinet:
                    selectedFragment = new CabinetFragment();
                    break;
                case R.id.menuAdmin:
                    selectedFragment = new AdminFragment();
                    break;
            }
            getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, selectedFragment).commit();

            return true;
        }
    };

    @Override
    public void onBackPressed() {
        int count = getSupportFragmentManager().getBackStackEntryCount();
        if (count == 0) {
            finishAffinity();
        } else {
            getSupportFragmentManager().popBackStack();
        }
    }

    public void onTextSizeAppendClicked(View view){
        RichEditor mEditor = (RichEditor)findViewById(R.id.richEditor);
        if(currentFontSize < 7){
            currentFontSize += 1;
            mEditor.setFontSize(currentFontSize);
        }else{
            Toast.makeText(this, "Достигнут максимальный размер текста", Toast.LENGTH_SHORT).show();
        }
    }

    public void onBoldTextClicked (View view){
        RichEditor mEditor = (RichEditor)findViewById(R.id.richEditor);
        isBold = !isBold;
        mEditor.setBold();
        ImageButton boldTextBtn = findViewById(R.id.boldTextBtn);
        if(isBold){
            boldTextBtn.setBackgroundResource(R.color.buttonColor2);
        }else{
            boldTextBtn.setBackgroundResource(R.color.buttonColor1);
        }

    }

    public void onItalicTextClicked(View view){
        RichEditor mEditor = (RichEditor)findViewById(R.id.richEditor);
        isItalic = !isItalic;
        mEditor.setItalic();
        ImageButton italicTextBtn = findViewById(R.id.italicTextBtn);
        if(isItalic){
            italicTextBtn.setBackgroundResource(R.color.buttonColor2);
        }else{
            italicTextBtn.setBackgroundResource(R.color.buttonColor1);
        }
    }

    public void onTextSizeDecreaseClicked(View view){
        RichEditor mEditor = (RichEditor)findViewById(R.id.richEditor);
        if(currentFontSize > 1){
            currentFontSize -= 1;
            mEditor.setFontSize(currentFontSize);
        }else{
            Toast.makeText(this, "Достигнут минимальный размер текста", Toast.LENGTH_SHORT).show();
        }
    }

}