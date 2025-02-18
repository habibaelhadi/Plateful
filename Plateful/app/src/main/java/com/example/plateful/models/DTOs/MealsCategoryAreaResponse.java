package com.example.plateful.models.DTOs;

import java.util.List;

public class MealsCategoryAreaResponse {

    private List<MealCategoryAreaDTO> meals;

    public List<MealCategoryAreaDTO> getMeals() {
        return meals;
    }

    public void setMeals(List<MealCategoryAreaDTO> meals) {
        this.meals = meals;
    }

}
