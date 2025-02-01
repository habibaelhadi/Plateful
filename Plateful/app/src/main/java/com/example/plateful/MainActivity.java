package com.example.plateful;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    Button button;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        auth = FirebaseAuth.getInstance();
        button = findViewById(R.id.signupbtn);
        FirebaseUser current = auth.getCurrentUser();

        button.setOnClickListener(view ->{
            signup();
        });
    }

    public void signup(){
        auth.createUserWithEmailAndPassword("habibaelhadi9@gmail.com","12345")
                .addOnCompleteListener(task -> {
                    Toast.makeText(this,"success",Toast.LENGTH_LONG).show();
                }).addOnFailureListener(task ->{
                    Toast.makeText(this,"fail",Toast.LENGTH_LONG).show();

                });
    }


}