package com.example.plateful.presenters.mealslist;

import com.example.plateful.models.db.MealsDatabase;

public interface MealsListPresenter {
    public void getMealsByCategory(String categoryName);
    public void getMealsByArea(String areaName);
    public void addToFavourites(MealsDatabase mealsDatabase);
}
