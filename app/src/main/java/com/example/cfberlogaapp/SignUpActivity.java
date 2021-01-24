package com.example.cfberlogaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class SignUpActivity extends AppCompatActivity {


    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    EditText emailEditText, passEditText;
    ConstraintLayout mainConstraint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        mDatabase =FirebaseDatabase.getInstance().getReference();
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimaryDark)));

        mainConstraint = findViewById(R.id.mainConstraint);
        mainConstraint.setOnClickListener(onMainConstraintClicked);
    }

    public void onSignUpBtnClicked(View view){

        String email, password;

        emailEditText = (EditText)findViewById(R.id.emailEditText);
        passEditText = (EditText)findViewById(R.id.passEditText);
        email = emailEditText.getText().toString();
        password = passEditText.getText().toString();

        if(!emailEditText.getText().toString().equals("") && !passEditText.getText().toString().equals("")){
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                FirebaseUser user = mAuth.getCurrentUser();
                                User userInfo = new User("Ваше имя", "0", "0",
                                        "0", "0", "0", "0", "0","0",
                                        "0", "0", "0", "0", "0", "0",
                                        "0", "0", "0", "0", "0", "gs://cfberlogaapp.appspot.com/profilePictures/defaultProfilePic.jpg");
                                mDatabase.child("users").child(user.getUid()).setValue(userInfo);
                                user.sendEmailVerification().addOnCompleteListener(
                                        new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful()){
                                                    Toast.makeText(SignUpActivity.this, "Вы успешно зарегестрированы, письмо для подтверждения регистрации отправлено вам на почту.", Toast.LENGTH_SHORT).show();
                                                    SignUpActivity.this.finish();
                                                }
                                            }
                                        }
                                );
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.e("signUp", String.valueOf(task.getException()));

                                try {
                                    throw task.getException();
                                }catch(FirebaseAuthUserCollisionException e){
                                    Toast.makeText(SignUpActivity.this, "Пользователь с такой почтой уже существует", Toast.LENGTH_SHORT).show();
                                } catch(FirebaseAuthWeakPasswordException e){
                                    Toast.makeText(SignUpActivity.this, "Пароль слишком слабый. Он должен содержать не менее 6 символов", Toast.LENGTH_SHORT).show();
                                }

                                catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });
        }else{
            Toast.makeText(SignUpActivity.this, "Не все поля заполнены", Toast.LENGTH_SHORT).show();
        }


    }

    public void hideSoftKeyboard (Activity activity, View view)
    {
        InputMethodManager imm = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
    }

    private ConstraintLayout.OnClickListener onMainConstraintClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            hideSoftKeyboard(SignUpActivity.this, v);
        }
    };
}