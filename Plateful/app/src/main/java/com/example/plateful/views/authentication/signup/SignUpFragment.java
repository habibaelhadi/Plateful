package com.example.plateful.views.authentication.signup;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.plateful.R;
import com.example.plateful.databinding.AlertDialogBinding;
import com.example.plateful.databinding.FragmentSignUpBinding;
import com.example.plateful.models.firebase.Firebase;
import com.example.plateful.models.firebase.FirebaseResponse;
import com.example.plateful.presenters.authentication.signup.SignUpPresenter;
import com.example.plateful.presenters.authentication.signup.SignUpPresenterImp;


public class SignUpFragment extends Fragment implements SignUpView {

    FragmentSignUpBinding binding;
    SignUpPresenter presenter;
    AlertDialogBinding alertBinding;
    SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding = FragmentSignUpBinding.bind(view);
        presenter = new SignUpPresenterImp(this,requireContext());
        sharedPreferences = requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        binding.login.setOnClickListener(vw -> {
            Navigation.findNavController(view).navigate(R.id.action_signUpFragment_to_loginFragment);
        });

        binding.signUpBtn.setOnClickListener(vw -> {
            if(binding.username.getText().toString().isEmpty()
                || (binding.emailSignUp.getText().toString().isEmpty()
                    || binding.passwordSignUp.getText().toString().isEmpty())){
                Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.alert_dialog);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
                dialog.findViewById(R.id.btn_dismiss).setOnClickListener(vw1 -> {
                    dialog.dismiss();
                });
            }

            else if(binding.passwordSignUp.getText().toString().equals(binding.confirmPassword.getText().toString())){
               presenter.signUp(binding.username.getText().toString(), binding.emailSignUp.getText().toString(), binding.passwordSignUp.getText().toString());
            }
        });
    }

    @Override
    public void signUpSuccess() {
        Navigation.findNavController(getView()).navigate(R.id.action_signUpFragment_to_homeFragment);
        sharedPreferences.edit().putString("username", binding.username.getText().toString()).apply();
        sharedPreferences.edit().putString("email", binding.emailSignUp.getText().toString()).apply();
        sharedPreferences.edit().putBoolean("login", true).apply();
    }

    @Override
    public void signUpFailure(String message) {
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.alert_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
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