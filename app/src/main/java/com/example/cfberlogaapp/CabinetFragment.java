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
import com.google.android.material.textfield.TextInputLayout;
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
    private ImageButton editBtn;

    private Uri imageUri;
    private FirebaseStorage mStorage;
    private StorageReference mStorageReference;
    private ProgressBar progressBar;



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

        progressBar = rootView.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        mainConstraint = rootView.findViewById(R.id.mainConstraint);
        mainConstraint.setOnClickListener(onMainConstraintClicked);

        saveChangesBtn = rootView.findViewById(R.id.saveChangesBtn);
        saveChangesBtn.setOnClickListener(onSaveChangesBtnClicked);



        profilePictureView = rootView.findViewById(R.id.profilePictureView);
        nameEditText = rootView.findViewById(R.id.nameEditText);
        weightEditText = rootView.findViewById(R.id.weightEditText);
        TextInputLayout weightEditTextLayout = (TextInputLayout)rootView.findViewById(R.id.weightEditTextLayout);

        heightEditText = rootView.findViewById(R.id.heightEditText);
        TextInputLayout heightEditTextLayout = (TextInputLayout)rootView.findViewById(R.id.heightEditTextLayout);

        shouldersEditText = rootView.findViewById(R.id.shouldersEditText);
        TextInputLayout shouldersEditTextLayout = (TextInputLayout)rootView.findViewById(R.id.shouldersEditTextLayout);

        chestEditText = rootView.findViewById(R.id.chestEditText);
        TextInputLayout chestEditTextLayout = (TextInputLayout)rootView.findViewById(R.id.chestEditTextLayout);

        bicepsEditText = rootView.findViewById(R.id.bicepsEditText);
        TextInputLayout bicepsEditTextLayout = (TextInputLayout)rootView.findViewById(R.id.bicepsEditTextLayout);

        waistEditText = rootView.findViewById(R.id.waistEditText);
        TextInputLayout waistEditTextLayout = (TextInputLayout)rootView.findViewById(R.id.waistEditTextLayout);

        hipEditText = rootView.findViewById(R.id.hipEditText);
        TextInputLayout hipEditTextLayout = (TextInputLayout)rootView.findViewById(R.id.hipEditTextLayout);

        hipsEditText = rootView.findViewById(R.id.hipsEditText);
        TextInputLayout hipsEditTextLayout = (TextInputLayout)rootView.findViewById(R.id.hipsEditTextLayout);

        backSquatEditText = rootView.findViewById(R.id.backSquatEditText);
        TextInputLayout backSquatEditTextLayout = (TextInputLayout)rootView.findViewById(R.id.backSquatEditTextLayout);

        frontSquatEditText = rootView.findViewById(R.id.frontSquatEditText);
        TextInputLayout frontSquatEditTextLayout = (TextInputLayout)rootView.findViewById(R.id.frontSquatEditTextLayout);

        overheadEditText = rootView.findViewById(R.id.overheadSquat);
        TextInputLayout overheadEditTextLayout = (TextInputLayout)rootView.findViewById(R.id.overheadSquatLayout);

        deadliftEditText = rootView.findViewById(R.id.deadliftEditText);
        TextInputLayout deadliftEditTextLayout = (TextInputLayout)rootView.findViewById(R.id.deadliftEditTextLayout);

        pressEditText = rootView.findViewById(R.id.pressEditText);
        TextInputLayout pressEditTextLayout = (TextInputLayout)rootView.findViewById(R.id.pressEditTextLayout);

        benchPressEditText = rootView.findViewById(R.id.benchPressEditText);
        TextInputLayout benchPressEditTextLayout = (TextInputLayout)rootView.findViewById(R.id.benchPressEditTextLayout);

        pullupsEditText = rootView.findViewById(R.id.pullupsEditText);
        TextInputLayout pullupsEditTextLayout = (TextInputLayout)rootView.findViewById(R.id.pullupsEditTextLayout);

        c2bPullUpsEditText = rootView.findViewById(R.id.c2bPullupsEditText);
        TextInputLayout c2bPullUpsEditTextLayout = (TextInputLayout)rootView.findViewById(R.id.c2bPullupsEditTextLayout);

        hsPullUpsEditText = rootView.findViewById(R.id.hsPullupsEditText);
        TextInputLayout hsPullUpsEditTextLayout = (TextInputLayout)rootView.findViewById(R.id.hsPullupsEditTextLayout);

        ringsDipsEditText = rootView.findViewById(R.id.ringsDipsEditText);
        TextInputLayout ringsDipsEditTextLayout = (TextInputLayout)rootView.findViewById(R.id.ringsDipsEditTextLayout);

        t2bEditText = rootView.findViewById(R.id.t2bEditText);
        TextInputLayout t2bEditTextLayout = (TextInputLayout)rootView.findViewById(R.id.t2bEditTextLayout);


        weightEditTextLayout.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), HistoryActivity.class);
                intent.putExtra("Exercise", "weight");
                startActivity(intent);
            }
        });


        shouldersEditTextLayout.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), HistoryActivity.class);
                intent.putExtra("Exercise", "shoulders");
                startActivity(intent);
            }
        });

        chestEditTextLayout.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), HistoryActivity.class);
                intent.putExtra("Exercise", "chest");
                startActivity(intent);
            }
        });

        bicepsEditTextLayout.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), HistoryActivity.class);
                intent.putExtra("Exercise", "biceps");
                startActivity(intent);
            }
        });

        waistEditTextLayout.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), HistoryActivity.class);
                intent.putExtra("Exercise", "waist");
                startActivity(intent);
            }
        });

        hipEditTextLayout.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), HistoryActivity.class);
                intent.putExtra("Exercise", "hip");
                startActivity(intent);
            }
        });

        hipsEditTextLayout.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), HistoryActivity.class);
                intent.putExtra("Exercise", "hips");
                startActivity(intent);
            }
        });

        backSquatEditTextLayout.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), HistoryActivity.class);
                intent.putExtra("Exercise", "backSquat");
                startActivity(intent);
            }
        });

        frontSquatEditTextLayout.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), HistoryActivity.class);
                intent.putExtra("Exercise", "frontSquat");
                startActivity(intent);
            }
        });

        overheadEditTextLayout.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), HistoryActivity.class);
                intent.putExtra("Exercise", "overheadSquat");
                startActivity(intent);
            }
        });

        deadliftEditTextLayout.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), HistoryActivity.class);
                intent.putExtra("Exercise", "deadlift");
                startActivity(intent);
            }
        });

        pressEditTextLayout.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), HistoryActivity.class);
                intent.putExtra("Exercise", "press");
                startActivity(intent);
            }
        });

        benchPressEditTextLayout.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), HistoryActivity.class);
                intent.putExtra("Exercise", "benchPress");
                startActivity(intent);
            }
        });

        pullupsEditTextLayout.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), HistoryActivity.class);
                intent.putExtra("Exercise", "pullUps");
                startActivity(intent);
            }
        });

        c2bPullUpsEditTextLayout.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), HistoryActivity.class);
                intent.putExtra("Exercise", "c2b");
                startActivity(intent);
            }
        });

        hsPullUpsEditTextLayout.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), HistoryActivity.class);
                intent.putExtra("Exercise", "hsPullUps");
                startActivity(intent);
            }
        });

        ringsDipsEditTextLayout.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), HistoryActivity.class);
                intent.putExtra("Exercise", "ringsDips");
                startActivity(intent);
            }
        });

        t2bEditTextLayout.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), HistoryActivity.class);
                intent.putExtra("Exercise", "t2b");
                startActivity(intent);
            }
        });


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
                        ringsDipsEditText.getText().toString(), t2bEditText.getText().toString());

                mDatabase.child("users").child(userID).child("name").setValue(user.getName());
                mDatabase.child("users").child(userID).child("backSquat").setValue(user.getBackSquat());
                mDatabase.child("users").child(userID).child("benchPress").setValue(user.getBenchPress());
                mDatabase.child("users").child(userID).child("biceps").setValue(user.getBiceps());
                mDatabase.child("users").child(userID).child("c2bPullUps").setValue(user.getC2bPullUps());
                mDatabase.child("users").child(userID).child("chest").setValue(user.getChest());
                mDatabase.child("users").child(userID).child("deadlift").setValue(user.getDeadlift());
                mDatabase.child("users").child(userID).child("frontSquat").setValue(user.getFrontSquat());
                mDatabase.child("users").child(userID).child("height").setValue(user.getHeight());
                mDatabase.child("users").child(userID).child("hip").setValue(user.getHip());
                mDatabase.child("users").child(userID).child("hips").setValue(user.getHips());
                mDatabase.child("users").child(userID).child("hsPullUps").setValue(user.getHsPullUps());
                mDatabase.child("users").child(userID).child("overheadSquat").setValue(user.getOverheadSquat());
                mDatabase.child("users").child(userID).child("press").setValue(user.getPress());
                mDatabase.child("users").child(userID).child("pullUps").setValue(user.getPullUps());
                mDatabase.child("users").child(userID).child("ringsDips").setValue(user.getRingsDips());
                mDatabase.child("users").child(userID).child("shoulders").setValue(user.getShoulders());
                mDatabase.child("users").child(userID).child("t2b").setValue(user.getT2b());
                mDatabase.child("users").child(userID).child("waist").setValue(user.getWaist());
                mDatabase.child("users").child(userID).child("weight").setValue(user.getWeight());
                saveHistory();

            }catch (Exception ex){
                Toast.makeText(getActivity(), ex.getMessage().toString(), Toast.LENGTH_LONG).show();
            }
            hideSoftKeyboard(getActivity(), v);
        }
    };


    private void setOnHistoryBtnClickListener(final Button btn, final String whatExercise){
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
                                    progressBar.setVisibility(View.INVISIBLE);
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