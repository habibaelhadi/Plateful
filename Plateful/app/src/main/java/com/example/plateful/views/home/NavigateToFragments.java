package com.example.plateful.views.home;

import com.example.plateful.models.DTOs.MealDTO;

public interface NavigateToFragments {
    default void navigateToDetails(MealDTO meal, int id){}
    default void navigateToMeals(String category, String area, String ingredient){}
    default void navigateToDetails(int id,MealDTO meal){}
}
