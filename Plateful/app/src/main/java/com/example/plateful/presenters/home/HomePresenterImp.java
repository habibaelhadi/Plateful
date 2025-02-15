package com.example.plateful.presenters.home;


import android.content.Context;
import android.util.Log;

import com.example.plateful.models.DTOs.AllIngredients;
import com.example.plateful.models.DTOs.CategoryDTO;
import com.example.plateful.models.DTOs.CountryDTO;
import com.example.plateful.models.db.MealsDatabase;
import com.example.plateful.models.firebase.Firebase;
import com.example.plateful.models.repository.DataRepository;
import com.example.plateful.views.home.HomeView;

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

    public HomePresenterImp(HomeView homeView, Context context) {
        this.homeView = homeView;
        dataRepository = DataRepository.getInstance(context);
        firebase = Firebase.getInstance();
    }

    @Override
    public void getDailyMeal() {
        dataRepository.getDailyMeal()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        success -> {
                            homeView.showDailyMeal(success.getMeals().get(0));
                        },
                        error -> {
                            homeView.showError(error.getMessage());
                        }
                );
    }

    @Override
    public void getCategories() {
        dataRepository.getCategories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        success -> {
                            homeView.showCategories(success.getCategories());
                        },
                        error -> {
                            homeView.showError(error.getMessage());
                        }
                );
    }

    @Override
    public void getCountry() {
        dataRepository.getCountry()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        success -> {
                            homeView.showCountries(success.getMeals());
                        },
                        error -> {
                            homeView.showError(error.getMessage());
                        }
                );
    }

    @Override
    public void getIngredients() {
        dataRepository.getAllIngredients()
                .subscribeOn(Schedulers.io())
                .debounce(300, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        success -> {
                            homeView.showIngredients(success.getMeals());
                        },
                        error -> {
                            homeView.showError(error.getMessage());
                        }
                );
    }

    @Override
    public void logout() {
        firebase.logout();
        homeView.logout();
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

}
