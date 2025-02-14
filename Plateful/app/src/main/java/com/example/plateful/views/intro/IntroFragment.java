package com.example.plateful.views.intro;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.plateful.R;
import com.example.plateful.databinding.AlertDialogBinding;
import com.example.plateful.databinding.FragmentIntroBinding;
import com.example.plateful.models.firebase.Firebase;
import com.example.plateful.presenters.intro.IntroPresenter;
import com.example.plateful.presenters.intro.IntroPresenterImp;


public class IntroFragment extends Fragment implements IntroView{

    FragmentIntroBinding binding;
    IntroPresenter introPresenter;
    AlertDialogBinding alertBinding;
    SharedPreferences sharedPreferences;
    private final ActivityResultLauncher<Intent> googleSignInLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    introPresenter.handleGoogleSignInResult(123, data);
                } else {
                    Log.e("GoogleSignIn", "Sign-in failed or canceled.");
                }
            });

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_intro, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding = FragmentIntroBinding.bind(view);
        sharedPreferences = requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        introPresenter = new IntroPresenterImp(this);

        binding.googleBtn.setOnClickListener(v -> {
            introPresenter.loginToGoogle(requireActivity(), requireContext(), googleSignInLauncher);
        });

        binding.signUpBtnIntro.setOnClickListener(vw -> {
            Navigation.findNavController(view).navigate(R.id.action_introFragment_to_signUpFragment);
        });

        binding.loginBtnIntro.setOnClickListener(vw -> {
            Navigation.findNavController(view).navigate(R.id.action_introFragment_to_loginFragment);
        });
    }

    @Override
    public void loginToGoogleSuccess() {
        Navigation.findNavController(requireView()).navigate(R.id.action_introFragment_to_homeFragment);
        sharedPreferences.edit().putBoolean("google", true).apply();
    }

    @Override
    public void loginToGoogleFailure(String message) {
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.alert_dialog);
        alertBinding.tvAlertMessage.setText(message);
        dialog.show();
        dialog.findViewById(R.id.btn_dismiss).setOnClickListener(vw1 -> {
            dialog.dismiss();
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}