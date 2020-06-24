package com.example.cfberlogaapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends AppCompatActivity {

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();

        FirebaseUser user = mAuth.getCurrentUser();
        changePage(user);

    }
    private void changePage(FirebaseUser user){
        if(isConnectedToInternet()){
            if(user != null){
                if(isUserAdmin(user)){
                    startActivity(new Intent(this, AdminBottomNavActivity.class));
                }else{
                    startActivity(new Intent(this, DefaultBottomNavActivity.class));
                }
            }else{
                startActivity(new Intent(this, LoginActivity.class));
            }
        }else{
            new AlertDialog.Builder(this)
                    .setTitle("Ошибка подключения")
                    .setMessage("Отсутствует подключение к интернету, проверьте подключение и повторите попытку.")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    }).show();
        }

    }

    public boolean isConnectedToInternet(){
        ConnectivityManager cm =
                (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }

    public boolean isUserAdmin(FirebaseUser user){
        boolean isAdmin = false;
        if(user.getEmail().equals("paincsgo@yandex.ru") || user.getEmail().equals("nothingow69@gmail.com"))
            isAdmin = true;
        else
            isAdmin = false;
        return isAdmin;
    }
}