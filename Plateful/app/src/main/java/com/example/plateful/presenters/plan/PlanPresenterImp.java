package com.example.plateful.presenters.plan;

import android.content.Context;

import com.example.plateful.db.DatabaseLocalDataSource;
import com.example.plateful.models.repository.DataRepository;
import com.example.plateful.network.APIRemoteDataSource;
import com.example.plateful.views.plan.PlanView;

public class PlanPresenterImp implements PlanPresenter{
    private PlanView view;
    private DataRepository repository;

    public PlanPresenterImp(PlanView view, Context context) {
        this.view = view;
        repository = DataRepository.getInstance(context);
    }

}
