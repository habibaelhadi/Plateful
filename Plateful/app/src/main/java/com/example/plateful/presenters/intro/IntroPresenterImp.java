package com.example.plateful.presenters.intro;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.activity.result.ActivityResultLauncher;
import androidx.fragment.app.FragmentActivity;

import com.example.plateful.db.DatabaseLocalDataSource;
import com.example.plateful.models.firebase.Firebase;
import com.example.plateful.models.firebase.FirebaseResponse;
import com.example.plateful.models.repository.DataRepository;
import com.example.plateful.network.APIRemoteDataSource;
import com.example.plateful.views.intro.IntroView;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.GoogleAuthProvider;

public class IntroPresenterImp implements IntroPresenter{
    private final IntroView view;
    private final DataRepository repository;
    private final Firebase firebase;

    public IntroPresenterImp(IntroView view) {
        this.view = view;
        repository = DataRepository.getInstance();
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
                    Log.i("TAG", "handleGoogleSignInResult: ");
                    view.loginToGoogleFailure("ID token is null");
                }
            } catch (ApiException e) {
                Log.e("TAG", "Google Sign-In failed", e);
                view.loginToGoogleFailure("Google Sign-In failed");
            }
        }
    }

    private void signInWithGoogle(String idToken) {
        AuthCredential authCredential = GoogleAuthProvider.getCredential(idToken, null);
        Firebase.auth.signInWithCredential(authCredential)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.i("TAG", "signInWithGoogle: ");
                        view.loginToGoogleSuccess();
                    } else {
                        view.loginToGoogleFailure(task.getException().getMessage());
                    }
                });
    }
}
