package com.example.plateful.presenters.authentication.signup;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import com.example.plateful.models.firebase.Firebase;
import com.example.plateful.models.firebase.FirebaseResponse;
import com.example.plateful.views.authentication.signup.SignUpView;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class SignUpPresenterImp implements SignUpPresenter{
    private SignUpView view;
    private Firebase firebase;
    SharedPreferences sharedPreferences;

    public SignUpPresenterImp(SignUpView view, Context context) {
        this.view = view;
        firebase = Firebase.getInstance();
        sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
    }

    @Override
    public void signUp(String name, String email, String password) {
        firebase.signup(email, password);
        firebase.setFirebaseResponse(new FirebaseResponse() {
            @Override
            public void onResponseSuccess(String message) {
                view.signUpSuccess();
                updateUserProfile(name);
            }

            @Override
            public void onResponseFailure(String message) {
                view.signUpFailure(message);
            }
        });
    }

    @Override
    public void updateUserProfile(String username) {
        FirebaseUser user = Firebase.auth.getCurrentUser();
        sharedPreferences.edit().putString("username", username).apply();
        if (user != null) {
            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                    .setDisplayName(username)
                    .build();

            user.updateProfile(profileUpdates)
                    .addOnSuccessListener(unused -> Log.i("TAG", "updateUserProfile: "))
                    .addOnFailureListener(e -> Log.i("TAG", "updateUserProfile: "));
        }
    }
}
