package com.example.plateful;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.plateful.databinding.ActivityMainBinding;
import com.example.plateful.firebase.Firebase;


public class MainActivity extends AppCompatActivity {

    private Firebase firebase;
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebase = Firebase.getInstance();
        firebase.connectToGoogle(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        firebase.handleGoogleSignInResult(requestCode, data);
    }
}