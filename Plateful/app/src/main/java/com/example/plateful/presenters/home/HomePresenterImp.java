package com.example.plateful.presenters.home;


import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.plateful.models.DTOs.AllIngredients;
import com.example.plateful.models.DTOs.CategoryDTO;
import com.example.plateful.models.DTOs.CountryDTO;
import com.example.plateful.models.db.MealsDatabase;
import com.example.plateful.models.firebase.Firebase;
import com.example.plateful.models.repository.DataRepository;
import com.example.plateful.views.home.HomeView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class HomePresenterImp implements HomePresenter {
    private HomeView homeView;
    private DataRepository dataRepository;
    private Firebase firebase;
    FirebaseDatabase database;
    DatabaseReference myRef;
    SharedPreferences sharedPreferences;
    Context context;

    public HomePresenterImp(HomeView homeView, Context context) {
        this.homeView = homeView;
        dataRepository = DataRepository.getInstance(context);
        firebase = Firebase.getInstance();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("meals");
        sharedPreferences = context.getSharedPreferences("MyPrefs",0);
        this.context = context;
    }

    @Override
    public void getDailyMeal() {
        homeView.showProgress();
        dataRepository.getDailyMeal()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        success -> {
                            homeView.hideProgress();
                            homeView.showDailyMeal(success.getMeals().get(0));
                        },
                        error -> {
                            homeView.hideProgress();
                            homeView.showError(error.getMessage());
                        }
                );
    }

    @Override
    public void getCategories() {
        homeView.showProgress();
        dataRepository.getCategories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        success -> {
                            homeView.hideProgress();
                            homeView.showCategories(success.getCategories());
                        },
                        error -> {
                            homeView.hideProgress();
                            homeView.showError(error.getMessage());
                        }
                );
    }

    @Override
    public void getCountry() {
        homeView.showProgress();
        dataRepository.getCountry()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        success -> {
                            homeView.hideProgress();
                            homeView.showCountries(success.getMeals());
                        },
                        error -> {
                            homeView.hideProgress();
                            homeView.showError(error.getMessage());
                        }
                );
    }

    @Override
    public void getIngredients() {
        homeView.showProgress();
        dataRepository.getAllIngredients()
                .subscribeOn(Schedulers.io())
                .debounce(300, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        success -> {
                            homeView.hideProgress();
                            homeView.showIngredients(success.getMeals());
                        },
                        error -> {
                            homeView.hideProgress();
                            homeView.showError(error.getMessage());
                        }
                );
    }

    @Override
    public void logout() {
        clearApplicationCache();
        sharedPreferences.edit().clear().apply();
        firebase.logout();
        homeView.logout();
    }

    private void clearApplicationCache() {
        try {
            File cacheDir = context.getCacheDir();
            if (cacheDir != null && cacheDir.isDirectory()) {
                deleteDir(cacheDir);
            }
        } catch (Exception e) {
            Log.e("CACHE_CLEAR", "Failed to clear cache", e);
        }
    }

    private boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (String child : children) {
                boolean success = deleteDir(new File(dir, child));
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }


    @Override
    public void addToFavourites(MealsDatabase mealsDatabase) {
        dataRepository.insert(mealsDatabase)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> {
                            homeView.addToFavourites();
                        },
                        error -> {
                            homeView.showError(error.getMessage());
                        }
                );
    }

    @Override
    public void removeFromFavourites(MealsDatabase mealsDatabase) {
        dataRepository.delete(mealsDatabase)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> {
                            homeView.removeFromFavourites();
                        },
                        error -> {
                            homeView.showError(error.getMessage());
                        }
                );
    }

    @Override
    public void addToPlan(MealsDatabase mealsDatabase) {
        dataRepository.insert(mealsDatabase)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> {
                            homeView.addToPlan();
                        },
                        error -> {
                            homeView.showError(error.getMessage());
                        }
                );
    }

    @Override
    public void getDataFromFirebase() {
        dataRepository.fetchDataFromFirebase();
    }

    @Override
    public void backupData(MealsDatabase mealsDatabase) {
        myRef.child("Users")
                .child(sharedPreferences.getString("id",""))
                .child(mealsDatabase.getMealId())
                .child(mealsDatabase.getDate())
                .setValue(mealsDatabase);
    }

    @Override
    public void removeFromFirebase(MealsDatabase mealsDatabase) {
        myRef.child("Users")
                .child(sharedPreferences.getString("id",""))
                .child(mealsDatabase.getMealId())
                .child(mealsDatabase.getDate())
                .removeValue();
    }

    @Override
    public void deleteAll() {
        dataRepository.deleteAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

}
