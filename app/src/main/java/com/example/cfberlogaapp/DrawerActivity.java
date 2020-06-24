package com.example.cfberlogaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DrawerActivity extends AppCompatActivity {


    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);

        final DrawerLayout drawerLayout = findViewById(R.id.drawerLayout);

        findViewById(R.id.menuImage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        NavigationView navigationView = findViewById(R.id.navigationView);
        navigationView.setItemIconTintList(null);
        NavController navController = Navigation.findNavController(this, R.id.navHostFragment);
        NavigationUI.setupWithNavController(navigationView, navController);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        View header = navigationView.getHeaderView(0);
        final ImageView profileImageView = (ImageView) header.findViewById(R.id.profilePictureView);
        final TextView nameTextView = header.findViewById(R.id.nameView);
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String userID = mAuth.getUid();
                nameTextView.setText(dataSnapshot.child("users").child(userID).child("name").getValue(String.class));
                String profilePictureUrl = dataSnapshot.child("users").child(userID).child("profilePictureUrl").getValue(String.class);
                StorageReference storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(profilePictureUrl);
                storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Glide
                                .with(DrawerActivity.this)
                                .load(uri)
                                .into(profileImageView);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        mDatabase.addListenerForSingleValueEvent(eventListener);

    }





    public void setActionBarTitle(String title){
        TextView textTitle = findViewById(R.id.textTitle);
        textTitle.setText(title);
    }

    public void hideSoftKeyboard (Activity activity, View view)
    {
        InputMethodManager imm = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
    }

    public void onMainConstraintClicked(View view){
        hideSoftKeyboard(DrawerActivity.this, view);
    }

    public void onSaveChangesBtnClicked(View view){
        try {
            EditText nameEditText, weightEditText, heightEditText, shouldersEditText, chestEditText, bicepsEditText, waistEditText,
                    hipEditText, hipsEditText, backSquatEditText, frontSquatEditText, overheadEditText, deadliftEditText,
                    pressEditText, benchPressEditText, pullupsEditText, c2bPullUpsEditText, hsPullUpsEditText, ringsDipsEditText, t2bEditText;
            String userID = mAuth.getUid().toString();

            nameEditText = findViewById(R.id.nameEditText);
            weightEditText = findViewById(R.id.weightEditText);
            heightEditText = findViewById(R.id.heightEditText);
            shouldersEditText = findViewById(R.id.shouldersEditText);
            chestEditText = findViewById(R.id.chestEditText);
            bicepsEditText = findViewById(R.id.bicepsEditText);
            waistEditText = findViewById(R.id.waistEditText);
            hipEditText = findViewById(R.id.hipEditText);
            hipsEditText = findViewById(R.id.hipsEditText);
            backSquatEditText = findViewById(R.id.backSquatEditText);
            frontSquatEditText = findViewById(R.id.frontSquatEditText);
            overheadEditText = findViewById(R.id.overheadSquat);
            deadliftEditText = findViewById(R.id.deadliftEditText);
            pressEditText = findViewById(R.id.pressEditText);
            benchPressEditText = findViewById(R.id.benchPressEditText);
            pullupsEditText = findViewById(R.id.pullupsEditText);
            c2bPullUpsEditText = findViewById(R.id.c2bPullupsEditText);
            hsPullUpsEditText = findViewById(R.id.hsPullupsEditText);
            ringsDipsEditText = findViewById(R.id.ringsDipsEditText);
            t2bEditText = findViewById(R.id.t2bEditText);


            User user= new User(nameEditText.getText().toString(), weightEditText.getText().toString(), heightEditText.getText().toString(),
                    shouldersEditText.getText().toString(), chestEditText.getText().toString(), bicepsEditText.getText().toString(),
                    waistEditText.getText().toString(), hipEditText.getText().toString(), hipsEditText.getText().toString(),
                    backSquatEditText.getText().toString(), frontSquatEditText.getText().toString(), overheadEditText.getText().toString(),
                    deadliftEditText.getText().toString(), benchPressEditText.getText().toString(), pressEditText.getText().toString(),
                    pullupsEditText.getText().toString(), c2bPullUpsEditText.getText().toString(), hsPullUpsEditText.getText().toString(),
                    ringsDipsEditText.getText().toString(), t2bEditText.getText().toString(), "gs://cfberlogaapp.appspot.com/profilePictures/defaultProfilePic.jpg");

            mDatabase.child("users").child(userID).setValue(user);
            saveHistory(view);

        }catch (Exception ex){
            Toast.makeText(this, ex.getMessage().toString(), Toast.LENGTH_LONG).show();
        }
        hideSoftKeyboard(DrawerActivity.this, view);

    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }

    public void saveHistory(View view){
        try{
            Date currentDate = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
            String currentDateText = dateFormat.format(currentDate);

            EditText nameEditText = findViewById(R.id.nameEditText);
            EditText weightEditText = findViewById(R.id.weightEditText);
            EditText heightEditText = findViewById(R.id.heightEditText);
            EditText shouldersEditText = findViewById(R.id.shouldersEditText);
            EditText chestEditText = findViewById(R.id.chestEditText);
            EditText bicepsEditText = findViewById(R.id.bicepsEditText);
            EditText waistEditText = findViewById(R.id.waistEditText);
            EditText hipEditText = findViewById(R.id.hipEditText);
            EditText hipsEditText = findViewById(R.id.hipsEditText);
            EditText backSquatEditText = findViewById(R.id.backSquatEditText);
            EditText frontSquatEditText = findViewById(R.id.frontSquatEditText);
            EditText overheadEditText = findViewById(R.id.overheadSquat);
            EditText deadliftEditText = findViewById(R.id.deadliftEditText);
            EditText pressEditText = findViewById(R.id.pressEditText);
            EditText benchPressEditText = findViewById(R.id.benchPressEditText);
            EditText pullupsEditText = findViewById(R.id.pullupsEditText);
            EditText c2bPullUpsEditText = findViewById(R.id.c2bPullupsEditText);
            EditText hsPullUpsEditText = findViewById(R.id.hsPullupsEditText);
            EditText ringsDipsEditText = findViewById(R.id.ringsDipsEditText);
            EditText t2bEditText = findViewById(R.id.t2bEditText);

            String userID = mAuth.getUid();
            Date timeStamp = new Date();
            long lTimeStamp = timeStamp.getTime();
            String timeStampString = Long.toString(lTimeStamp);
            User user = new User(weightEditText.getText().toString(),
                    shouldersEditText.getText().toString(), chestEditText.getText().toString(), bicepsEditText.getText().toString(),
                    waistEditText.getText().toString(), hipEditText.getText().toString(), hipsEditText.getText().toString(),
                    backSquatEditText.getText().toString(), frontSquatEditText.getText().toString(), overheadEditText.getText().toString(),
                    deadliftEditText.getText().toString(), benchPressEditText.getText().toString(), pressEditText.getText().toString(),
                    pullupsEditText.getText().toString(), c2bPullUpsEditText.getText().toString(), hsPullUpsEditText.getText().toString(),
                    ringsDipsEditText.getText().toString(), t2bEditText.getText().toString());


            mDatabase.child("history").child(userID).child(currentDateText).setValue(user);
            Toast.makeText(this, "Данные успешно обновлены!", Toast.LENGTH_SHORT).show();
        }catch (Exception ex){
            new AlertDialog.Builder(DrawerActivity.this)
                    .setTitle("Error")
                    .setMessage(ex.getMessage()).show();
        }

    }

    public void onWeightHistoryBtnClicked(View view){
        Intent intent = new Intent(this, HistoryActivity.class);
        intent.putExtra("exrsz", "weight");
        startActivity(intent);
    }

    public void onShouldersHistoryBtnClicked(View view){
        Intent intent = new Intent(this, HistoryActivity.class);
        intent.putExtra("exrsz", "shoulders");
        startActivity(intent);
    }
    public void onChestHistoryBtnClicked(View view){
        Intent intent = new Intent(this, HistoryActivity.class);
        intent.putExtra("exrsz", "chest");
        startActivity(intent);
    }
    public void onBicepsHistoryBtnClicked(View view){
        Intent intent = new Intent(this, HistoryActivity.class);
        intent.putExtra("exrsz", "biceps");
        startActivity(intent);
    }
    public void onWaistHistoryBtnClicked(View view){
        Intent intent = new Intent(this, HistoryActivity.class);
        intent.putExtra("exrsz", "waist");
        startActivity(intent);
    }
    public void onHipHistoryBtnClicked(View view){
        Intent intent = new Intent(this, HistoryActivity.class);
        intent.putExtra("exrsz", "hip");
        startActivity(intent);
    }
    public void onHipsHistoryBtnClicked(View view){
        Intent intent = new Intent(this, HistoryActivity.class);
        intent.putExtra("exrsz", "hips");
        startActivity(intent);
    }
    public void onBackSquatHistoryBtnClicked(View view){
        Intent intent = new Intent(this, HistoryActivity.class);
        intent.putExtra("exrsz", "backSquat");
        startActivity(intent);
    }
    public void onFrontSquatHistoryBtnClicked(View view){
        Intent intent = new Intent(this, HistoryActivity.class);
        intent.putExtra("exrsz", "frontSquat");
        startActivity(intent);
    }
    public void onOverheadSquatHistoryBtnClicked(View view){
        Intent intent = new Intent(this, HistoryActivity.class);
        intent.putExtra("exrsz", "overheadSquat");
        startActivity(intent);
    }
    public void ondeadliftHistoryBtnClicked(View view){
        Intent intent = new Intent(this, HistoryActivity.class);
        intent.putExtra("exrsz", "deadlift");
        startActivity(intent);
    }
    public void onPressHistoryBtnClicked(View view){
        Intent intent = new Intent(this, HistoryActivity.class);
        intent.putExtra("exrsz", "press");
        startActivity(intent);
    }
    public void onBenchPressHistoryBtnClicked(View view){
        Intent intent = new Intent(this, HistoryActivity.class);
        intent.putExtra("exrsz", "benchPress");
        startActivity(intent);
    }
    public void onPullUpsHistoryBtnClicked(View view){
        Intent intent = new Intent(this, HistoryActivity.class);
        intent.putExtra("exrsz", "pullUps");
        startActivity(intent);
    }
    public void onC2bPullUpsHistoryBtnClicked(View view){
        Intent intent = new Intent(this, HistoryActivity.class);
        intent.putExtra("exrsz", "c2bPullUps");
        startActivity(intent);
    }
    public void onHsPullUpsHistoryBtnClicked(View view){
        Intent intent = new Intent(this, HistoryActivity.class);
        intent.putExtra("exrsz", "hsPullUps");
        startActivity(intent);
    }
    public void onRingsDipsHistoryBtnClicked(View view){
        Intent intent = new Intent(this, HistoryActivity.class);
        intent.putExtra("exrsz", "ringsDips");
        startActivity(intent);
    }
    public void onT2bHistoryBtnClicked(View view){
        Intent intent = new Intent(this, HistoryActivity.class);
        intent.putExtra("exrsz", "t2b");
        startActivity(intent);
    }


}