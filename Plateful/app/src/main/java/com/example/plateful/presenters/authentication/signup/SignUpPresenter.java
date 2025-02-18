package com.example.plateful.presenters.authentication.signup;

public interface SignUpPresenter {
    public void signUp(String name, String email, String password);
    public void updateUserProfile(String username);
}
