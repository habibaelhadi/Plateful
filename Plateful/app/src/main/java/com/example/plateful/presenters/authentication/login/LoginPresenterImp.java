package com.example.plateful.presenters.authentication.login;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.plateful.db.DatabaseLocalDataSource;
import com.example.plateful.models.firebase.Firebase;
import com.example.plateful.models.firebase.FirebaseResponse;
import com.example.plateful.models.repository.DataRepository;
import com.example.plateful.network.APIRemoteDataSource;
import com.example.plateful.views.authentication.login.LoginView;
import com.google.firebase.auth.FirebaseUser;

public class LoginPresenterImp implements LoginPresenter {
    private LoginView view;
    private Firebase firebase;
    private SharedPreferences sharedPreferences;

    public LoginPresenterImp(LoginView view, Context context) {
        this.view = view;
        sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        firebase = Firebase.getInstance();
    }

    @Override
    public void login(String email, String password) {
        firebase.login(email, password);
        firebase.setFirebaseResponse(new FirebaseResponse() {
            @Override
            public void onResponseSuccess(String message) {
                view.loginSuccess();
            }

            @Override
            public void onResponseFailure(String message) {
                view.loginFailure(message);
            }
        });
    }


    @Override
    public void getUserName() {
        FirebaseUser currentUser = Firebase.auth.getCurrentUser();
        if (currentUser != null) {
            sharedPreferences.edit().putString("id", currentUser.getUid()).apply();
            sharedPreferences.edit().putString("username", currentUser.getDisplayName()).apply();
            sharedPreferences.edit().putString("email", currentUser.getEmail()).apply();
            sharedPreferences.edit().putBoolean("login", true).apply();
        }
    }
}
