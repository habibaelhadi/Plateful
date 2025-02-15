package com.example.plateful.presenters.mealsdetails;

import android.content.Context;

import com.example.plateful.db.DatabaseLocalDataSource;
import com.example.plateful.models.db.MealsDatabase;
import com.example.plateful.models.repository.DataRepository;
import com.example.plateful.network.APIRemoteDataSource;
import com.example.plateful.views.mealDetails.MealDetailsView;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MealsDetailsPresenterImp implements MealsDetailsPresenter{
    private MealDetailsView view;
    private DataRepository repository;

    public MealsDetailsPresenterImp(MealDetailsView view, Context context) {
        this.view = view;
        repository = DataRepository.getInstance(context);
    }

    @Override
    public void getMealDetails(int id) {
        repository.getMealDetails(String.valueOf(id))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        success -> {
                            view.showData(success.getMeals().get(0));
                        },
                        error -> view.showError(error.getMessage())
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
