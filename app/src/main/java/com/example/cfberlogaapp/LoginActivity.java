package com.example.cfberlogaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class LoginActivity extends AppCompatActivity {


    private FirebaseAuth mAuth;
    EditText emailEditText, passEditText;
    String email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

    }

    public void onLoginBtnClicked(View view){
        emailEditText = (EditText)findViewById(R.id.emailEditText);
        passEditText = (EditText)findViewById(R.id.passEditText);
        email = emailEditText.getText().toString();
        password = passEditText.getText().toString();


        if(!emailEditText.getText().toString().equals("") && !passEditText.getText().toString().equals("")){
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Toast.makeText(LoginActivity.this, "Authenticated successfully", Toast.LENGTH_LONG).show();
                                FirebaseUser user = mAuth.getCurrentUser();
                                if(user.getEmail().equals("paincsgo@yandex.ru") || user.getEmail().equals("nothingow69@gmail.com")){
                                    startActivity(new Intent(LoginActivity.this, AdminBottomNavActivity.class));
                                }else {
                                    startActivity(new Intent(LoginActivity.this, DefaultBottomNavActivity.class));
                                }

                            } else {
//                                 If sign in fails, display a message to the user.
                                Log.e("auth", String.valueOf(task.getException()));
                                if(String.valueOf(task.getException()).equals("FirebaseAuthInvalidCredentialsException")){
                                    Toast.makeText(LoginActivity.this, "Неверный email или пароль", Toast.LENGTH_LONG).show();
                                }
                                if(String.valueOf(task.getException()).equals("FirebaseAuthInvalidUserException")){
                                    Toast.makeText(LoginActivity.this, "Пользователя с такой почтой не существует", Toast.LENGTH_LONG).show();
                                }
                            }
                        }
                    });
        }else{
            Toast.makeText(LoginActivity.this, "Не все поля заполнены", Toast.LENGTH_SHORT).show();
        }


    }

    public void onSignUpBtnClicked(View view){
        startActivity(new Intent(this, SignUpActivity.class));
    }

    public void onForgotPassTextClicked(View view){
        startActivity(new Intent(this, ForgotPasswordActivity.class));
    }

}