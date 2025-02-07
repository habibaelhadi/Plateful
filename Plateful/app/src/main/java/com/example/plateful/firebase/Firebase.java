package com.example.plateful.firebase;



import static androidx.core.app.ActivityCompat.startActivityForResult;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
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
    private static FirebaseAuth auth;
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
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(context.getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(context, gso);
    }

    public void handleGoogleSignInResult(int requestCode, Intent data) {
        if (requestCode == 123) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                signInWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void signInWithGoogle(String idToken) {
        AuthCredential authCredential = GoogleAuthProvider.getCredential(idToken, null);
        auth.signInWithCredential(authCredential)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Handle successful sign-in
                    } else {
                        // Handle failed sign-in
                    }
                });
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

    public void signIn(Activity activity, Bundle bundle) {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(activity, signInIntent, 123, bundle);
    }

    public interface FirebaseResponse{
        public void onResponseSuccess(String message);
        public void onResponseFailure(String message);
    }
}
