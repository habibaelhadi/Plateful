package com.example.plateful.models.DTOs;

import java.io.Serializable;
import java.util.List;

public class CountryDTO {

    private List<MealsDTO> meals;

    public List<MealsDTO> getMeals() {
        return meals;
    }

    public void setMeals(List<MealsDTO> meals) {
        this.meals = meals;
    }

    public static class MealsDTO implements Serializable {
        private String strArea;

        public String getStrArea() {
            return strArea;
        }

        public void setStrArea(String strArea) {
            this.strArea = strArea;
        }
    }
}
