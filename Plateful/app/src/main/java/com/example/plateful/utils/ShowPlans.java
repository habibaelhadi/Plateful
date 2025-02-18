package com.example.plateful.utils;

import com.example.plateful.models.DTOs.MealDTO;

public interface ShowPlans {
    public void saveMealToPlan(MealDTO mealDTO,String date);
}
