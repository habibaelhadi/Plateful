package com.example.plateful.presenters.authentication.login;

public interface LoginPresenter {
    public void login(String email, String password);
    public String getUserId();
}
