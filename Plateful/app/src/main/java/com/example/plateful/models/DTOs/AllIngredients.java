package com.example.plateful.models.DTOs;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AllIngredients {

    @SerializedName("meals")
    private List<Ingredients> meals;

    public List<Ingredients> getMeals() {
        return meals;
    }

    public void setMeals(List<Ingredients> meals) {
        this.meals = meals;
    }

    public static class Ingredients {
        private String idIngredient;
        private String strIngredient;
        private String strDescription;
        private Object strType;

        public String getIdIngredient() {
            return idIngredient;
        }

        public void setIdIngredient(String idIngredient) {
            this.idIngredient = idIngredient;
        }

        public String getStrIngredient() {
            return strIngredient;
        }

        public void setStrIngredient(String strIngredient) {
            this.strIngredient = strIngredient;
        }

        public String getStrDescription() {
            return strDescription;
        }

        public void setStrDescription(String strDescription) {
            this.strDescription = strDescription;
        }

        public Object getStrType() {
            return strType;
        }

        public void setStrType(Object strType) {
            this.strType = strType;
        }
    }
}
