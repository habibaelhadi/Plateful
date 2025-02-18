package com.example.plateful.views.intro;

import android.app.Activity;
import android.content.Context;

public interface IntroView {
    public void loginToGoogleSuccess();
    public void loginToGoogleFailure(String message);
}
