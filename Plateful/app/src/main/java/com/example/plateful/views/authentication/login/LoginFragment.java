package com.example.plateful.views.authentication.login;

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

import com.example.plateful.R;
import com.example.plateful.databinding.AlertDialogBinding;
import com.example.plateful.databinding.FragmentLoginBinding;
import com.example.plateful.presenters.authentication.login.LoginPresenter;
import com.example.plateful.presenters.authentication.login.LoginPresenterImp;


public class LoginFragment extends Fragment implements LoginView {

    LoginPresenter loginPresenter;
    FragmentLoginBinding binding;
    AlertDialogBinding alertBinding;
    SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding = FragmentLoginBinding.bind(view);
        loginPresenter = new LoginPresenterImp(this,requireContext());
        sharedPreferences = requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);


        binding.signUp.setOnClickListener(vw -> {
            Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_signUpFragment);
        });

        binding.loginBtn.setOnClickListener(vw -> {
            if(binding.emailLogin.getText().toString().isEmpty()
                    || binding.passwordLogin.getText().toString().isEmpty()){
                Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.alert_dialog);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
                dialog.findViewById(R.id.btn_dismiss).setOnClickListener(vw1 -> {
                    dialog.dismiss();
                });
            }
            else {
                loginPresenter.login(binding.emailLogin.getText().toString(), binding.passwordLogin.getText().toString());
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    @Override
    public void loginSuccess() {
        Navigation.findNavController(getView()).navigate(R.id.action_loginFragment_to_homeFragment);
        sharedPreferences.edit().putString("id", loginPresenter.getUserId()).apply();
        sharedPreferences.edit().putString("email", binding.emailLogin.getText().toString()).apply();
        sharedPreferences.edit().putBoolean("login", true).apply();
    }

    @Override
    public void loginFailure(String message) {
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.alert_dialog);
        alertBinding.tvAlertMessage.setText(message);
        dialog.show();
        dialog.findViewById(R.id.btn_dismiss).setOnClickListener(vw1 -> {
            dialog.dismiss();
        });
    }
}