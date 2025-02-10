package com.example.plateful.model;

import java.io.Serializable;
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
