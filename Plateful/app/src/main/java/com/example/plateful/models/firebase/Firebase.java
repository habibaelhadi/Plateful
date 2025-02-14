package com.example.plateful.models.firebase;



import static androidx.core.app.ActivityCompat.startActivityForResult;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.plateful.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

public class Firebase {
    public static FirebaseAuth auth;
    private GoogleSignInClient googleSignInClient;
    private static Firebase firebase;
    private FirebaseResponse firebaseResponse;
    String message;

    private Firebase() {
        auth = FirebaseAuth.getInstance();
    }

    public void setFirebaseResponse(FirebaseResponse firebaseResponse) {
        this.firebaseResponse = firebaseResponse;
    }

    public static Firebase getInstance() {
        if (firebase == null) {
            firebase = new Firebase();
        }
        return firebase;
    }

    public void connectToGoogle(Context context) {
        Log.i("TAG", "connectToGoogle: ");
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(context.getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(context, gso);
    }


    public void signup(String email, String password) {

        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        message = "success";
                       firebaseResponse.onResponseSuccess(message);
                    } else {
                        message = task.getException().getMessage();
                        firebaseResponse.onResponseFailure(message);
                    }
                });

    }

    public void login(String email, String password) {
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        message = "success";
                        firebaseResponse.onResponseSuccess(message);
                    } else {
                        message = task.getException().getMessage();
                        firebaseResponse.onResponseFailure(message);
                    }
                });
    }

    private void logout() {
        auth.signOut();
    }

    public Intent getGoogleSignInIntent() {
        if (googleSignInClient != null) {
            Log.d("TAG", "Returning Google Sign-In intent.");
            return googleSignInClient.getSignInIntent();
        } else {
            Log.e("TAG", "googleSignInClient is null. Did you call connectToGoogle()?");
            return null;
        }
    }

}
