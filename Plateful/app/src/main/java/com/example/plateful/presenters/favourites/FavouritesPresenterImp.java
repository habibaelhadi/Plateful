package com.example.plateful.presenters.favourites;

import com.example.plateful.db.DatabaseLocalDataSource;
import com.example.plateful.models.repository.DataRepository;
import com.example.plateful.network.APIRemoteDataSource;
import com.example.plateful.views.favourites.FavouriteView;

public class FavouritesPresenterImp implements FavouritesPresenter{
    private FavouriteView view;
    private DataRepository repository;

    public FavouritesPresenterImp(FavouriteView view) {
        this.view = view;
        repository = DataRepository.getInstance();
    }

    @Override
    public void getFavourites() {

    }
}
