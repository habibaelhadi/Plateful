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
import com.example.plateful.databinding.FragmentIntroBinding;
import com.example.plateful.firebase.Firebase;


public class IntroFragment extends Fragment {

    FragmentIntroBinding binding;
    Firebase firebase;

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


        firebase = Firebase.getInstance();
        firebase.connectToGoogle(requireContext());

        binding.googleBtn.setOnClickListener(v -> {
            firebase.signIn(requireActivity(), null);
        });

        binding.signUpBtnIntro.setOnClickListener(vw -> {
            Navigation.findNavController(view).navigate(R.id.action_introFragment_to_signUpFragment);
        });

        binding.loginBtnIntro.setOnClickListener(vw -> {
            Navigation.findNavController(view).navigate(R.id.action_introFragment_to_loginFragment);
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}