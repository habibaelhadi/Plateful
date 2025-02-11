package com.example.plateful.ui.authentication;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.plateful.R;
import com.example.plateful.databinding.FragmentSignUpBinding;
import com.example.plateful.firebase.Firebase;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;


public class SignUpFragment extends Fragment {

    Firebase firebase;
    FragmentSignUpBinding binding;

    public SignUpFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

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

        binding.login.setOnClickListener(vw -> {
            Navigation.findNavController(view).navigate(R.id.action_signUpFragment_to_loginFragment);
        });

        binding.signUpBtn.setOnClickListener(vw -> {
            firebase = Firebase.getInstance();
            if(binding.passwordSignUp.getText().toString().equals(binding.confirmPassword.getText().toString())){
                firebase.signup(binding.emailSignUp.getText().toString(),binding.passwordSignUp.getText().toString());
                firebase.setFirebaseResponse(new Firebase.FirebaseResponse() {
                    @Override
                    public void onResponseSuccess(String message) {
                        Toast.makeText(getActivity(), message+" ", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponseFailure(String message) {
                        Toast.makeText(getActivity(), message+" ", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}