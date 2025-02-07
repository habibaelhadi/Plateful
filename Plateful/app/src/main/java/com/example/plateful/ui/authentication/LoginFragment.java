package com.example.plateful.ui.authentication;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

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


public class LoginFragment extends Fragment {

    TextView signUp;
    Button login;
    Firebase firebase;
    EditText email;
    TextInputEditText password;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        signUp = view.findViewById(R.id.sign_up);
        login = view.findViewById(R.id.loginBtn);
        email = view.findViewById(R.id.email_login);
        password = view.findViewById(R.id.password_login);

        signUp.setOnClickListener(vw -> {
            Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_signUpFragment);
        });

        login.setOnClickListener(vw -> {
            firebase = Firebase.getInstance();
            firebase.login(email.getText().toString(), password.getText().toString());
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
        });
    }
}