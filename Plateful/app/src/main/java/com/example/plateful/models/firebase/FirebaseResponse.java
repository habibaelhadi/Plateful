package com.example.plateful.models.firebase;

public interface FirebaseResponse{
    public void onResponseSuccess(String message);
    public void onResponseFailure(String message);
}
