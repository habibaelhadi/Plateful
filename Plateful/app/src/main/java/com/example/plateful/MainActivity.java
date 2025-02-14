package com.example.plateful;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.example.plateful.databinding.ActivityMainBinding;
import com.example.plateful.models.firebase.Firebase;
import com.google.android.material.navigation.NavigationBarView;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(binding.fragmentContainerView2.getId());
        navController = navHostFragment.getNavController();
        NavigationUI.setupWithNavController(binding.bottomNavigation, navController);

        binding.bottomNavigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                return handleBottomNavigation(item);
            }
        });

        Set<Integer> visibleFragments = new HashSet<>(Arrays.asList(
                R.id.homeFragment,
                R.id.favouriteFragment,
                R.id.planFragment2
        ));

        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            if (visibleFragments.contains(destination.getId())) {
                binding.bottomNavigation.setVisibility(View.VISIBLE);
            } else {
                binding.bottomNavigation.setVisibility(View.GONE);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

       //firebase.handleGoogleSignInResult(requestCode, data);
    }


    private boolean handleBottomNavigation(MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.homeFragment) {
            navController.navigate(R.id.homeFragment);
            return true;
        } else if (itemId == R.id.favouriteFragment) {
            navController.navigate(R.id.favouriteFragment);
            return true;
        } else if (itemId == R.id.planFragment2) {
            navController.navigate(R.id.planFragment2);
            return true;
        }
        return false;
    }
}