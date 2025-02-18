package com.example.plateful.presenters.intro;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import androidx.activity.result.ActivityResultLauncher;
import com.example.plateful.models.firebase.Firebase;
import com.example.plateful.models.repository.DataRepository;
import com.example.plateful.views.intro.IntroView;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.GoogleAuthProvider;

public class IntroPresenterImp implements IntroPresenter{
    private final IntroView view;
    private SharedPreferences sharedPreferences;
    private final Firebase firebase;

    public IntroPresenterImp(IntroView view,Context context) {
        this.view = view;
        sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        firebase = Firebase.getInstance();
    }


    @Override
    public void loginToGoogle(Activity activity, Context context, ActivityResultLauncher<Intent> launcher) {
        firebase.connectToGoogle(context);
        Intent signInIntent = firebase.getGoogleSignInIntent();
        if (signInIntent != null) {
            launcher.launch(signInIntent);
        } else {
            Log.e("GoogleSignIn", "Sign-in intent is null.");
        }
    }

    @Override
    public void handleGoogleSignInResult(int requestCode, Intent data) {
        if (requestCode == 123) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                if (account != null && account.getIdToken() != null) {
                    signInWithGoogle(account.getIdToken());
                } else {
                    view.loginToGoogleFailure("ID token is null");
                }
            } catch (ApiException e) {
                view.loginToGoogleFailure("Google Sign-In failed");
            }
        }
    }

    private void signInWithGoogle(String idToken) {
        AuthCredential authCredential = GoogleAuthProvider.getCredential(idToken, null);
        Firebase.auth.signInWithCredential(authCredential)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        sharedPreferences.edit().putBoolean("google", true).apply();
                        sharedPreferences.edit().putString("id", Firebase.auth.getCurrentUser().getUid()).apply();
                        sharedPreferences.edit().putString("username", Firebase.auth.getCurrentUser().getDisplayName()).apply();
                        sharedPreferences.edit().putString("email", Firebase.auth.getCurrentUser().getEmail()).apply();
                        sharedPreferences.edit().putString("photo", Firebase.auth.getCurrentUser().getPhotoUrl().toString()).apply();
                        view.loginToGoogleSuccess();
                    } else {
                        view.loginToGoogleFailure(task.getException().getMessage());
                    }
                });
    }
}
