package com.example.plateful.presenters.favourites;

import android.content.Context;

import com.example.plateful.models.db.MealsDatabase;
import com.example.plateful.models.repository.DataRepository;
import com.example.plateful.views.favourites.FavouriteView;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FavouritesPresenterImp implements FavouritesPresenter{
    private FavouriteView view;
    private DataRepository repository;

    public FavouritesPresenterImp(FavouriteView view, Context context) {
        this.view = view;
        repository = DataRepository.getInstance(context);
    }

    @Override
    public void getFavourites() {
        repository.getAllFavourites()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        list -> {
                            view.showFavourites(list);
                        },
                        error -> {
                            view.showError(error.getMessage());
                        }
                );
    }

    @Override
    public void removeFromFavourites(MealsDatabase mealsDatabase) {
        repository.delete(mealsDatabase)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }
}
