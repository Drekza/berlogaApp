package com.example.cfberlogaapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;

public class CabinetFragment extends Fragment {



    public CabinetFragment() {
        // Required empty public constructor
    }

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private ConstraintLayout mainConstraint;
    private Button saveChangesBtn;
    private EditText nameEditText, weightEditText, heightEditText, shouldersEditText, chestEditText, bicepsEditText, waistEditText,
            hipEditText, hipsEditText, backSquatEditText, frontSquatEditText, overheadEditText, deadliftEditText,
            pressEditText, benchPressEditText, pullupsEditText, c2bPullUpsEditText, hsPullUpsEditText, ringsDipsEditText, t2bEditText;
    private ImageView profilePictureView;
    private ImageButton editBtn, weightHistoryBtn, shouldersHistoryBtn, chestHistoryBtn, bicepsHistoryBtn, waistHistoryBtn,
            hipHistoryBtn, hipsHistoryBtn, backSquatHistoryBtn, frontSquatHistoryBtn, overheadHistoryBtn,
            deadliftHistoryBtn, pressHistoryBtn, benchPressHistoryBtn, pullUpsHistoryBtn, c2bHistoryBtn,
            hsPullUpsHistoryBtn, ringsDipsHistoryBtn, t2bHistoryBtn;

    private Uri imageUri;
    private FirebaseStorage mStorage;
    private StorageReference mStorageReference;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mStorage = FirebaseStorage.getInstance();
        mStorageReference = mStorage.getReference();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_cabinet, container, false);

        mainConstraint = rootView.findViewById(R.id.mainConstraint);
        mainConstraint.setOnClickListener(onMainConstraintClicked);

        saveChangesBtn = rootView.findViewById(R.id.saveChangesBtn);
        saveChangesBtn.setOnClickListener(onSaveChangesBtnClicked);


        profilePictureView = rootView.findViewById(R.id.profilePictureView);
        nameEditText = rootView.findViewById(R.id.nameEditText);
        weightEditText = rootView.findViewById(R.id.weightEditText);
        heightEditText = rootView.findViewById(R.id.heightEditText);
        shouldersEditText = rootView.findViewById(R.id.shouldersEditText);
        chestEditText = rootView.findViewById(R.id.chestEditText);
        bicepsEditText = rootView.findViewById(R.id.bicepsEditText);
        waistEditText = rootView.findViewById(R.id.waistEditText);
        hipEditText = rootView.findViewById(R.id.hipEditText);
        hipsEditText = rootView.findViewById(R.id.hipsEditText);
        backSquatEditText = rootView.findViewById(R.id.backSquatEditText);
        frontSquatEditText = rootView.findViewById(R.id.frontSquatEditText);
        overheadEditText = rootView.findViewById(R.id.overheadSquat);
        deadliftEditText = rootView.findViewById(R.id.deadliftEditText);
        pressEditText = rootView.findViewById(R.id.pressEditText);
        benchPressEditText = rootView.findViewById(R.id.benchPressEditText);
        pullupsEditText = rootView.findViewById(R.id.pullupsEditText);
        c2bPullUpsEditText = rootView.findViewById(R.id.c2bPullupsEditText);
        hsPullUpsEditText = rootView.findViewById(R.id.hsPullupsEditText);
        ringsDipsEditText = rootView.findViewById(R.id.ringsDipsEditText);
        t2bEditText = rootView.findViewById(R.id.t2bEditText);

        weightHistoryBtn = rootView.findViewById(R.id.weightHistoryBtn);
        setOnHistoryBtnClickListener(weightHistoryBtn, "weight");
        shouldersHistoryBtn = rootView.findViewById(R.id.shouldersHistoryBtn);
        setOnHistoryBtnClickListener(shouldersHistoryBtn,"shoulders");
        chestHistoryBtn = rootView.findViewById(R.id.chestHistoryBtn);
        setOnHistoryBtnClickListener(chestHistoryBtn, "chest");
        bicepsHistoryBtn = rootView.findViewById(R.id.bicepsHistoryBtn);
        setOnHistoryBtnClickListener(bicepsHistoryBtn, "biceps");
        waistHistoryBtn = rootView.findViewById(R.id.waistHistoryBtn);
        setOnHistoryBtnClickListener(waistHistoryBtn, "waist");
        hipHistoryBtn = rootView.findViewById(R.id.hipHistoryBtn);
        setOnHistoryBtnClickListener(hipHistoryBtn, "hip");
        hipsHistoryBtn = rootView.findViewById(R.id.hipsHistoryBtn);
        setOnHistoryBtnClickListener(hipsHistoryBtn, "hips");
        backSquatHistoryBtn = rootView.findViewById(R.id.backSquatHistoryBtn);
        setOnHistoryBtnClickListener(backSquatHistoryBtn, "backSquat");
        frontSquatHistoryBtn = rootView.findViewById(R.id.frontSquatHistoryBtn);
        setOnHistoryBtnClickListener(frontSquatHistoryBtn, "frontSquat");
        overheadHistoryBtn = rootView.findViewById(R.id.overheadHistoryBtn);
        setOnHistoryBtnClickListener(overheadHistoryBtn, "overheadSquat");
        deadliftHistoryBtn = rootView.findViewById(R.id.deadliftHistoryBtn);
        setOnHistoryBtnClickListener(deadliftHistoryBtn, "deadlift");
        pressHistoryBtn = rootView.findViewById(R.id.pressHistoryBtn);
        setOnHistoryBtnClickListener(pressHistoryBtn, "press");
        benchPressHistoryBtn = rootView.findViewById(R.id.benchPressHistoryBtn);
        setOnHistoryBtnClickListener(benchPressHistoryBtn, "benchPress");
        pullUpsHistoryBtn = rootView.findViewById(R.id.pullUpsHistoryBtn);
        setOnHistoryBtnClickListener(pullUpsHistoryBtn, "pullUps");
        c2bHistoryBtn = rootView.findViewById(R.id.c2bHistoryBtn);
        setOnHistoryBtnClickListener(c2bHistoryBtn, "c2b");
        hsPullUpsHistoryBtn = rootView.findViewById(R.id.hsPullupsHistoryBtn);
        setOnHistoryBtnClickListener(hsPullUpsHistoryBtn, "hsPullUps");
        ringsDipsHistoryBtn = rootView.findViewById(R.id.ringsDipsHistoryBtn);
        setOnHistoryBtnClickListener(ringsDipsHistoryBtn, "ringsDips");
        t2bHistoryBtn = rootView.findViewById(R.id.t2bHistoryBtn);
        setOnHistoryBtnClickListener(t2bHistoryBtn, "t2b");


        editBtn = rootView.findViewById(R.id.editBtn);
        editBtn.setOnClickListener(onEditBtnClicked);

        loadData();


        return rootView;
    }

    public ImageButton.OnClickListener onEditBtnClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            choosePicture();
        }
    };

    public void choosePicture(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null){
            imageUri = data.getData();
            profilePictureView.setImageURI(imageUri);
            uploadPicture();
        }
    }

    public void uploadPicture(){
        final String uId = mAuth.getUid();
        final StorageReference profilePicturesStorageReference = mStorageReference.child("profilePictures/" + uId);
        final ProgressBar progressBar = new ProgressBar(getContext());
        progressBar.setVisibility(View.VISIBLE);
        profilePicturesStorageReference.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(getContext(), "Фотография успешно обновлена", Toast.LENGTH_LONG).show();
                        mDatabase.child("users").child(uId).child("profilePictureUrl").setValue("gs://cfberlogaapp.appspot.com" + profilePicturesStorageReference.getPath());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "Ошибка при загрузке фотографии", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                        double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                        progressBar.setProgress((int)progress);
                    }
                });
    }

    public void hideSoftKeyboard (Activity activity, View view)
    {
        InputMethodManager imm = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
    }

    private ConstraintLayout.OnClickListener onMainConstraintClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            hideSoftKeyboard(getActivity(), v);
        }
    };

    private Button.OnClickListener onSaveChangesBtnClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                String userID = mAuth.getUid().toString();
                User user= new User(nameEditText.getText().toString(), weightEditText.getText().toString(), heightEditText.getText().toString(),
                        shouldersEditText.getText().toString(), chestEditText.getText().toString(), bicepsEditText.getText().toString(),
                        waistEditText.getText().toString(), hipEditText.getText().toString(), hipsEditText.getText().toString(),
                        backSquatEditText.getText().toString(), frontSquatEditText.getText().toString(), overheadEditText.getText().toString(),
                        deadliftEditText.getText().toString(), benchPressEditText.getText().toString(), pressEditText.getText().toString(),
                        pullupsEditText.getText().toString(), c2bPullUpsEditText.getText().toString(), hsPullUpsEditText.getText().toString(),
                        ringsDipsEditText.getText().toString(), t2bEditText.getText().toString(), "gs://cfberlogaapp.appspot.com/profilePictures/defaultProfilePic.jpg");

                mDatabase.child("users").child(userID).setValue(user);
                saveHistory();

            }catch (Exception ex){
                Toast.makeText(getActivity(), ex.getMessage().toString(), Toast.LENGTH_LONG).show();
            }
            hideSoftKeyboard(getActivity(), v);
        }
    };


    private void setOnHistoryBtnClickListener(final ImageButton btn, final String whatExercise){
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), HistoryActivity.class);
                intent.putExtra("Exercise", whatExercise);
                startActivity(intent);
            }
        });
    }


    public void saveHistory(){
        try{
            Date currentDate = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
            String currentDateText = dateFormat.format(currentDate);


            String userID = mAuth.getUid();
            User user = new User( weightEditText.getText().toString(), shouldersEditText.getText().toString(), chestEditText.getText().toString(), bicepsEditText.getText().toString(),
                    waistEditText.getText().toString(), hipEditText.getText().toString(), hipsEditText.getText().toString(),
                    backSquatEditText.getText().toString(), frontSquatEditText.getText().toString(), overheadEditText.getText().toString(),
                    deadliftEditText.getText().toString(), benchPressEditText.getText().toString(), pressEditText.getText().toString(),
                    pullupsEditText.getText().toString(), c2bPullUpsEditText.getText().toString(), hsPullUpsEditText.getText().toString(),
                    ringsDipsEditText.getText().toString(), t2bEditText.getText().toString());


            mDatabase.child("history").child(userID).child(currentDateText).setValue(user);
            Toast.makeText(getActivity(), "Данные успешно обновлены!", Toast.LENGTH_SHORT).show();
        }catch (Exception ex){
            new AlertDialog.Builder(getActivity())
                    .setTitle("Error")
                    .setMessage(ex.getMessage()).show();
        }
    }


    public void loadData (){
        try {
            ValueEventListener dbListener = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String userID = mAuth.getUid().toString();
                    User user= dataSnapshot.child("users").child(userID).getValue(User.class);
                    if(user != null){
                        nameEditText.setText(user.name);
                        weightEditText.setText(user.weight);
                        heightEditText.setText(user.height);
                        shouldersEditText.setText(user.shoulders);
                        chestEditText.setText(user.chest);
                        bicepsEditText.setText(user.biceps);
                        waistEditText.setText(user.waist);
                        hipEditText.setText(user.hip);
                        hipsEditText.setText(user.hips);
                        backSquatEditText.setText(user.backSquat);
                        frontSquatEditText.setText(user.frontSquat);
                        overheadEditText.setText(user.overheadSquat);
                        deadliftEditText.setText(user.deadlift);
                        pressEditText.setText(user.press);
                        benchPressEditText.setText(user.benchPress);
                        pullupsEditText.setText(user.pullUps);
                        c2bPullUpsEditText.setText(user.c2bPullUps);
                        hsPullUpsEditText.setText(user.hsPullUps);
                        ringsDipsEditText.setText(user.ringsDips);
                        t2bEditText.setText(user.t2b);
                        try {
                            StorageReference storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(user.profilePictureUrl);

                            storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    RequestOptions options = new RequestOptions()
                                            .centerCrop()
                                            .error(R.drawable.ic_exit)
                                            .priority(Priority.HIGH);
                                    Glide.with(getActivity()).load(uri).apply(options).into(profilePictureView);
                                }
                            });

                        }catch (Exception ex){
                            Toast.makeText(getActivity(), ex.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Toast.makeText(getActivity(), "Object is null", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            };
            mDatabase.addListenerForSingleValueEvent(dbListener);
        }catch(Exception ex){
            Toast.makeText(getActivity(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }
}