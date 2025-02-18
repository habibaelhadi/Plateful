package com.example.plateful.presenters.mealslist;

import android.content.Context;

import com.example.plateful.models.db.MealsDatabase;
import com.example.plateful.models.repository.DataRepository;
import com.example.plateful.views.mealslist.CategoryAreaListView;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MealsListPresenterImp implements MealsListPresenter{
    private CategoryAreaListView view;
    private DataRepository repository;

    public MealsListPresenterImp(CategoryAreaListView view, Context context) {
        this.view = view;
        repository = DataRepository.getInstance(context);
    }

    @Override
    public void getMealsByCategory(String categoryName) {
        repository.getMealsByCategory(categoryName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        success -> {
                            view.showMeals(success.getMeals());
                        },
                        error -> {
                            view.showError(error.getMessage());
                        }
                );
    }

    @Override
    public void getMealsByArea(String areaName) {
        repository.getMealsByArea(areaName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        success -> {
                            view.showMeals(success.getMeals());
                        },
                        error -> {
                            view.showError(error.getMessage());
                        }
                );
    }

    @Override
    public void getMealsByIngredients(String ingredient) {
        repository.getMealsByIngredients(ingredient)
                .subscribeOn(Schedulers.io())
                .map(mealsCategoryAreaResponse -> mealsCategoryAreaResponse.getMeals())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        list -> {
                            view.showMeals(list);
                        },
                        error -> {
                            view.showError(error.getMessage());
                        }
                );
    }

    @Override
    public void addToFavourites(MealsDatabase mealsDatabase) {
        repository.insert(mealsDatabase)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> {
                            view.addToFavourites();
                        },
                        error -> {
                            view.showError(error.getMessage());
                        }
                );
    }
}
