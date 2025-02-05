package com.example.plateful;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class MainActivity extends AppCompatActivity {

    Button button;
    private FirebaseAuth auth;
    private GoogleSignInClient googleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
//        auth = FirebaseAuth.getInstance();
//        button = findViewById(R.id.signupbtn);
//        FirebaseUser current = auth.getCurrentUser();
//        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestIdToken(getString(R.string.default_web_client_id))
//                .requestEmail()
//                .build();
//        googleSignInClient = GoogleSignIn.getClient(this,gso);
//
//
//        if(current != null){
//            Log.i("TAG", "onCreate: "+current.getEmail());
//        }else{
//            Log.i("TAG", "onCreate: no user");
//        }
//
//        button.setOnClickListener(view ->{
//            signIn();
//        });
//    }
//
//    private void signup(){
//        auth.createUserWithEmailAndPassword("habibaelhadi9@gmail.com","12345678")
//                .addOnCompleteListener(task ->{
//                    if(task.isSuccessful()){
//                        Toast.makeText(this,"success",Toast.LENGTH_LONG).show();
//                    }else{
//                        Toast.makeText(this,"fail"+task.getException().getMessage(),Toast.LENGTH_LONG).show();
//                    }
//                });
//    }
//
//    private void signIn(){
//        Intent signInIntent = googleSignInClient.getSignInIntent();
//        startActivityForResult(signInIntent,123);
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if(requestCode == 123){
//            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
//            try {
//                GoogleSignInAccount account = task.getResult(ApiException.class);
//                signInWithGoogle(account.getIdToken());
//            } catch (ApiException e) {
//                throw new RuntimeException(e);
//            }
//        }
//    }
//
//    private void signInWithGoogle(String idToken){
//        AuthCredential authCredential = GoogleAuthProvider.getCredential(idToken,null);
//        auth.signInWithCredential(authCredential)
//                .addOnCompleteListener(task ->{
//                    if(task.isSuccessful()){
//                        Toast.makeText(this,"success",Toast.LENGTH_LONG).show();
//                    }else{
//                        Toast.makeText(this,"fail"+task.getException().getMessage(),Toast.LENGTH_LONG).show();
//                    }
//        });
//    }
//    private void login(){
//        auth.signInWithEmailAndPassword("habibaelhadi9@gmail.com","12345679")
//                .addOnCompleteListener(task ->{
//                   if(task.isSuccessful()){
//                       Toast.makeText(this,"success",Toast.LENGTH_LONG).show();
//                   }else{
//                       Toast.makeText(this,"fail"+task.getException().getMessage(),Toast.LENGTH_LONG).show();
//                   }
//                });
//    }
//
//    private void logout(){
//        auth.signOut();
   }


}