package com.example.plateful.presenters.mealslist;

import com.example.plateful.db.DatabaseLocalDataSource;
import com.example.plateful.models.repository.DataRepository;
import com.example.plateful.network.APIRemoteDataSource;
import com.example.plateful.views.mealslist.CategoryAreaListView;

public class MealsListPresenterImp implements MealsListPresenter{
    private CategoryAreaListView view;
    private DataRepository repository;

    public MealsListPresenterImp(CategoryAreaListView view) {
        this.view = view;
        repository = DataRepository.getInstance();
    }

}
