package com.example.plateful.views.mealslist;

import com.example.plateful.models.DTOs.MealCategoryAreaDTO;

import java.util.List;

public interface CategoryAreaListView {
    public void showMeals(List<MealCategoryAreaDTO> meals);
    public void showError(String errorMessage);
    public void addToFavourites();
}
