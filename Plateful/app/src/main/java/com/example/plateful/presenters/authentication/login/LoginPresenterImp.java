package com.example.plateful.presenters.authentication.login;

import com.example.plateful.db.DatabaseLocalDataSource;
import com.example.plateful.models.firebase.Firebase;
import com.example.plateful.models.firebase.FirebaseResponse;
import com.example.plateful.models.repository.DataRepository;
import com.example.plateful.network.APIRemoteDataSource;
import com.example.plateful.views.authentication.login.LoginView;

public class LoginPresenterImp implements LoginPresenter {
    private LoginView view;
    private Firebase firebase;
    private DataRepository repository;

    public LoginPresenterImp(LoginView view) {
        this.view = view;
        repository = DataRepository.getInstance();
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
}
