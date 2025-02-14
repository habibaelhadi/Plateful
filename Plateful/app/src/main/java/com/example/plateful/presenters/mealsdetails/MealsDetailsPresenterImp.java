package com.example.plateful.presenters.mealsdetails;

import com.example.plateful.db.DatabaseLocalDataSource;
import com.example.plateful.models.repository.DataRepository;
import com.example.plateful.network.APIRemoteDataSource;
import com.example.plateful.views.mealDetails.MealDetailsView;

public class MealsDetailsPresenterImp implements MealsDetailsPresenter{
    private MealDetailsView view;
    private DataRepository repository;

    public MealsDetailsPresenterImp(MealDetailsView view) {
        this.view = view;
        repository = DataRepository.getInstance();
    }

}
