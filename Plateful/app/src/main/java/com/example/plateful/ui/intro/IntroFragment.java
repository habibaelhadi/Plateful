package com.example.plateful.ui.intro;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.plateful.R;
import com.example.plateful.firebase.Firebase;


public class IntroFragment extends Fragment {

    Button signUp;
    Button login;
    Firebase firebase;
    CardView google;

    public IntroFragment() {
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
        return inflater.inflate(R.layout.fragment_intro, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        signUp = view.findViewById(R.id.signUpBtn_intro);
        login = view.findViewById(R.id.loginBtn_intro);
        google = view.findViewById(R.id.googleBtn);

        firebase = Firebase.getInstance();
        firebase.connectToGoogle(requireContext());

        google.setOnClickListener(v -> {
            firebase.signIn(requireActivity(), null);
        });

        signUp.setOnClickListener(vw -> {
            Navigation.findNavController(view).navigate(R.id.action_introFragment_to_signUpFragment);
        });

        login.setOnClickListener(vw -> {
            Navigation.findNavController(view).navigate(R.id.action_introFragment_to_loginFragment);
        });
    }
}