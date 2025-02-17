package com.example.plateful;

import static androidx.core.content.ContentProviderCompat.requireContext;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.example.plateful.databinding.ActivityMainBinding;
import com.example.plateful.models.firebase.Firebase;
import com.example.plateful.views.home.LoadingData;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.snackbar.Snackbar;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    NavController navController;
    private ConnectivityManager.NetworkCallback networkCallback;
    private ConnectivityManager connectivityManager;
    LoadingData loadingData;
    boolean isNetwork = true;


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

            if (destination.getId() != R.id.homeFragment) {
                binding.noInternet.setVisibility(View.GONE);
                binding.noInternet.pauseAnimation();
                binding.fragmentContainerView2.setVisibility(View.VISIBLE);
            }else{
                checkNetwork();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
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

    private boolean registerNetworkCallback() {
        connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        networkCallback = new ConnectivityManager.NetworkCallback() {
            @Override
            public void onAvailable(@NonNull Network network) {
                runOnUiThread(() ->{
                    showSuccessSnackBar("Network is available");
                    binding.noInternet.setVisibility(View.GONE);
                    binding.noInternet.pauseAnimation();
                    binding.fragmentContainerView2.setVisibility(View.VISIBLE);
                    if (loadingData != null) {
                        loadingData.loadData();
                    }
                });
                isNetwork = true;
            }

            @Override
            public void onLost(@NonNull Network network) {
                runOnUiThread(() ->
                {
                    showErrorSnackBar("Network is lost");
                    if(R.id.homeFragment == navController.getCurrentDestination().getId()){
                        binding.noInternet.setVisibility(View.VISIBLE);
                        binding.noInternet.playAnimation();
                        binding.fragmentContainerView2.setVisibility(View.GONE);
                    }else{
                        binding.noInternet.setVisibility(View.GONE);
                        binding.noInternet.pauseAnimation();
                        binding.fragmentContainerView2.setVisibility(View.VISIBLE);
                    }
                });
               isNetwork = false;
            }

        };

        NetworkRequest networkRequest = new NetworkRequest.Builder()
                .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                .build();

        connectivityManager.registerNetworkCallback(networkRequest, networkCallback);
        return isNetwork;
    }

    private void showErrorSnackBar(String message){
        Snackbar snackbar = Snackbar.make(getWindow().getDecorView(), message, Snackbar.LENGTH_SHORT);
        View snackbarView = snackbar.getView();
        int color = ContextCompat.getColor(this,R.color.red);
        snackbarView.setBackgroundTintList(ColorStateList.valueOf(color));
        TextView textView = snackbarView.findViewById(com.google.android.material.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);

        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) snackbarView.getLayoutParams();
        params.gravity = Gravity.TOP;
        snackbarView.setLayoutParams(params);

        snackbar.show();
    }

    private void showSuccessSnackBar(String message){
        Snackbar snackbar = Snackbar.make(getWindow().getDecorView(), message, Snackbar.LENGTH_SHORT);
        View snackbarView = snackbar.getView();
        int color = ContextCompat.getColor(this,R.color.green);
        snackbarView.setBackgroundTintList(ColorStateList.valueOf(color));
        TextView textView = snackbarView.findViewById(com.google.android.material.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);

        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) snackbarView.getLayoutParams();
        params.gravity = Gravity.TOP;
        snackbarView.setLayoutParams(params);

        snackbar.show();
    }

    private void checkNetwork(){
        registerNetworkCallback();
        if(isNetwork){
            binding.noInternet.setVisibility(View.GONE);
            binding.noInternet.pauseAnimation();
            binding.fragmentContainerView2.setVisibility(View.VISIBLE);
            if(loadingData != null){
                loadingData.loadData();
            }
        }else{
            binding.noInternet.setVisibility(View.VISIBLE);
            binding.noInternet.playAnimation();
            binding.fragmentContainerView2.setVisibility(View.GONE);
        }
    }

    public void setLoadingData(LoadingData loadingData){
        this.loadingData = loadingData;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (connectivityManager != null && networkCallback != null) {
            connectivityManager.unregisterNetworkCallback(networkCallback);
        }
        binding = null;
    }
}