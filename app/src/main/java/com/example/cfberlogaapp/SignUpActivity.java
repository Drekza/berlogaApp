package com.example.cfberlogaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {


    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    EditText emailEditText, passEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        mDatabase =FirebaseDatabase.getInstance().getReference();
        getSupportActionBar().setDisplayShowHomeEnabled(true);
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
                                                    Toast.makeText(SignUpActivity.this, "Registered successfully, please verify your email.", Toast.LENGTH_SHORT).show();
                                                    SignUpActivity.this.finish();
                                                }
                                            }
                                        }
                                );
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.e("signUp", String.valueOf(task.getException()));
                                if(String.valueOf(task.getException()).equals("FirebaseAuthUserCollisionException")){
                                    Toast.makeText(SignUpActivity.this, "Пользователь с таким email уже существует", Toast.LENGTH_SHORT).show();
                                }
                                if(String.valueOf(task.getException()).equals("FirebaseAuthWeakPasswordException")){
                                    Toast.makeText(SignUpActivity.this, "Пароль слишком слабый. Он должен содержать не менее 6 символов", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });
        }else{
            Toast.makeText(SignUpActivity.this, "Не все поля заполнены", Toast.LENGTH_SHORT).show();
        }
    }
}