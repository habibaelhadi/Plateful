package com.example.plateful.presenters.mealsdetails;

import com.example.plateful.models.db.MealsDatabase;

public interface MealsDetailsPresenter {
    public void getMealDetails(int id);
    public void addToFavourites(MealsDatabase mealsDatabase);
}
