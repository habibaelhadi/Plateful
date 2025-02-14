package com.example.plateful.models.DTOs;

import java.util.List;


public class DailyMealDTO {

    private List<MealDTO> meals;

    public List<MealDTO> getMeals() {
        return meals;
    }

    public void setMeals(List<MealDTO> meals) {
        this.meals = meals;
    }


}
