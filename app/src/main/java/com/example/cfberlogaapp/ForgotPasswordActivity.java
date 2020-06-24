package com.example.cfberlogaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity {


    private FirebaseAuth mAuth;
    EditText emailEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Забыли пароль?");
        mAuth = FirebaseAuth.getInstance();
    }

    public void onSendEmailBtnClicked (View view){
        emailEditText = (EditText)findViewById(R.id.emailEditText);
        String email = emailEditText.getText().toString();
        try {
            mAuth.sendPasswordResetEmail(email).addOnCompleteListener(
                    new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(ForgotPasswordActivity.this, "Письмо для восстановления пароля отправлено вам на почту", Toast.LENGTH_SHORT).show();
                                ForgotPasswordActivity.this.finish();
                            }
                            else{
                                Toast.makeText(ForgotPasswordActivity.this, task.getException().getMessage().toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
            );
        }catch(Exception e){
            Toast.makeText(this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }

    }
}