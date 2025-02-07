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
import com.example.plateful.firebase.Firebase;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;


public class SignUpFragment extends Fragment {
    TextView login;
    Button signup;
    Firebase firebase;
    EditText email;
    TextInputEditText password;
    TextInputEditText confirmPassword;

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
        login = view.findViewById(R.id.login);
        signup = view.findViewById(R.id.signUpBtn);
        email = view.findViewById(R.id.email_sign_up);
        password = view.findViewById(R.id.password_sign_up);
        confirmPassword = view.findViewById(R.id.confirm_password);

        login.setOnClickListener(vw -> {
            Navigation.findNavController(view).navigate(R.id.action_signUpFragment_to_loginFragment);
        });

        signup.setOnClickListener(vw -> {
            firebase = Firebase.getInstance();
            if(password.getText().toString().equals(confirmPassword.getText().toString())){
                firebase.signup(email.getText().toString(),password.getText().toString());
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
}