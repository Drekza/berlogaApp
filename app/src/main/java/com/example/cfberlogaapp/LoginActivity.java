package com.example.cfberlogaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {


    private FirebaseAuth mAuth;
    EditText emailEditText, passEditText;
    String email, password;
    ProgressBar progressBar;
    ConstraintLayout mainConstraint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        mainConstraint = findViewById(R.id.mainConstraint);
        mainConstraint.setOnClickListener(onMainConstraintClicked);
    }

    public void onLoginBtnClicked(View view){
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        emailEditText = (EditText)findViewById(R.id.emailEditText);
        passEditText = (EditText)findViewById(R.id.passEditText);
        email = emailEditText.getText().toString();
        password = passEditText.getText().toString();

        progressBar.setVisibility(View.VISIBLE);

        if(!emailEditText.getText().toString().equals("") && !passEditText.getText().toString().equals("")){


            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                FirebaseUser user = mAuth.getCurrentUser();
                                if(user.getEmail().equals("paincsgo@yandex.ru") || user.getEmail().equals("nothingow69@gmail.com")){
                                    progressBar.setVisibility(View.INVISIBLE);
                                    startActivity(new Intent(LoginActivity.this, AdminBottomNavActivity.class));
                                }else {
                                    progressBar.setVisibility(View.INVISIBLE);
                                    startActivity(new Intent(LoginActivity.this, DefaultBottomNavActivity.class));
                                }

                            } else {
                                progressBar.setVisibility(View.INVISIBLE);
//                                 If sign in fails, display a message to the user.
                                Log.e("auth", String.valueOf(task.getException()));

                                try {
                                    throw task.getException();
                                }catch(FirebaseAuthInvalidCredentialsException e){
                                    Toast.makeText(LoginActivity.this, "Неверный email или пароль", Toast.LENGTH_LONG).show();
                                } catch(FirebaseAuthInvalidUserException e){
                                    Toast.makeText(LoginActivity.this, "Пользователя с такой почтой не существует", Toast.LENGTH_LONG).show();
                                }

                                catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });

        }else{
            progressBar.setVisibility(View.INVISIBLE);
            Toast.makeText(LoginActivity.this, "Не все поля заполнены", Toast.LENGTH_SHORT).show();
        }


    }

    public void onSignUpBtnClicked(View view){
        startActivity(new Intent(this, SignUpActivity.class));
    }

    public void onForgotPassTextClicked(View view){
        startActivity(new Intent(this, ForgotPasswordActivity.class));
    }


    public void hideSoftKeyboard (Activity activity, View view)
    {
        InputMethodManager imm = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
    }

    private ConstraintLayout.OnClickListener onMainConstraintClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            hideSoftKeyboard(LoginActivity.this, v);
        }
    };
}