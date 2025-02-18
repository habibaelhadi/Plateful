package com.example.plateful.views.home;

import com.example.plateful.models.DTOs.AllIngredients;
import com.example.plateful.models.DTOs.CategoryDTO;
import com.example.plateful.models.DTOs.CountryDTO;
import com.example.plateful.models.DTOs.MealDTO;
import java.util.List;

public interface HomeView {
    public void showDailyMeal(MealDTO meal);
    public void showCategories(List<CategoryDTO.CategoryMealDTO> categories);
    public void showCountries(List<CountryDTO.MealsDTO> countries);
    public void showIngredients(List<AllIngredients.Ingredients> ingredients);
    public void showError(String errorMessage);
    public void logout();
    public void addToFavourites();
    public void removeFromFavourites();
    public void addToPlan();
    public void showProgress();
    public void hideProgress();
}
