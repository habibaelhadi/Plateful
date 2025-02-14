package com.example.plateful.presenters.authentication.signup;

import com.example.plateful.db.DatabaseLocalDataSource;
import com.example.plateful.models.firebase.Firebase;
import com.example.plateful.models.firebase.FirebaseResponse;
import com.example.plateful.models.repository.DataRepository;
import com.example.plateful.network.APIRemoteDataSource;
import com.example.plateful.views.authentication.signup.SignUpView;

public class SignUpPresenterImp implements SignUpPresenter{
    private SignUpView view;
    private DataRepository repository;
    private Firebase firebase;

    public SignUpPresenterImp(SignUpView view) {
        this.view = view;
        repository = DataRepository.getInstance();
        firebase = Firebase.getInstance();
    }

    @Override
    public void signUp(String name, String email, String password) {
        firebase.signup(email, password);
        firebase.setFirebaseResponse(new FirebaseResponse() {

            @Override
            public void onResponseSuccess(String message) {
                view.signUpSuccess();
            }

            @Override
            public void onResponseFailure(String message) {
                view.signUpFailure(message);
            }
        });
    }
}
