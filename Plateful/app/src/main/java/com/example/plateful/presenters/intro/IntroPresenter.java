package com.example.plateful.presenters.intro;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import androidx.activity.result.ActivityResultLauncher;

public interface IntroPresenter {
    public void loginToGoogle(Activity activity, Context context, ActivityResultLauncher<Intent> launcher);
    public void handleGoogleSignInResult(int requestCode, Intent data);
}
