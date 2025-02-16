package com.example.plateful.presenters.plan;

import android.content.Context;

import com.example.plateful.db.DatabaseLocalDataSource;
import com.example.plateful.models.repository.DataRepository;
import com.example.plateful.network.APIRemoteDataSource;
import com.example.plateful.views.plan.PlanView;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class PlanPresenterImp implements PlanPresenter{
    private PlanView view;
    private DataRepository repository;

    public PlanPresenterImp(PlanView view, Context context) {
        this.view = view;
        repository = DataRepository.getInstance(context);
    }

    @Override
    public void getAllPlans() {
        repository.getAllPlans()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        list -> {
                            view.showPlans(list);
                        },
                        error -> {
                            view.showError(error.getMessage());
                        }
                );
    }
}
