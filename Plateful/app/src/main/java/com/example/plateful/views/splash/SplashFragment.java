package com.example.plateful.views.splash;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.plateful.R;

public class SplashFragment extends Fragment {
    SharedPreferences sharedPreferences;

    public SplashFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_splash, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sharedPreferences = requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if((sharedPreferences.getBoolean("login", false)) ||
                        (sharedPreferences.getBoolean("google", false))){
                    Navigation.findNavController(view).navigate(R.id.action_splashFragment3_to_homeFragment);
                }else{
                Navigation.findNavController(view).navigate(R.id.action_splashFragment3_to_introFragment);
                }
            }
        },3000);
    }
}