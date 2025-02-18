package com.example.plateful.presenters.favourites;

import com.example.plateful.models.db.MealsDatabase;

public interface FavouritesPresenter {
    public void getFavourites();
    public void removeFromFavourites(MealsDatabase mealsDatabase);
}
