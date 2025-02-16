package com.example.plateful.views.mealDetails;

import com.example.plateful.models.DTOs.MealDTO;

public interface MealDetailsView {
    public void showData(MealDTO meal);
    public void showError(String errorMessage);
    public void addToFavourites();
    public void removeFromFavourites();
    public void addToPlan();

}
