package com.example.plateful.models.db;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.TypeConverters;

import com.example.plateful.models.DTOs.MealDTO;
import com.example.plateful.utils.Converter;

import java.util.Date;


@Entity(
        tableName = "meals_table",
        primaryKeys = {"mealId","userId","date"})
@TypeConverters(Converter.class)
public class MealsDatabase {
    @NonNull
    String mealId;
    @NonNull
    String userId;
    @NonNull
    String date;
    MealDTO meal;
    boolean isFavourite;
    boolean isPlanned;

    public MealsDatabase() {
    }

    public MealsDatabase(@NonNull String mealId, @NonNull String userId, @NonNull String date, MealDTO meal, boolean isFavourite, boolean isPlanned) {
        this.mealId = mealId;
        this.userId = userId;
        this.date = date;
        this.meal = meal;
        this.isFavourite = isFavourite;
        this.isPlanned = isPlanned;
    }

    @NonNull
    public String getMealId() {
        return mealId;
    }

    public void setMealId(@NonNull String mealId) {
        this.mealId = mealId;
    }

    @NonNull
    public String getUserId() {
        return userId;
    }

    public void setUserId(@NonNull String userId) {
        this.userId = userId;
    }

    @NonNull
    public String getDate() {
        return date;
    }

    public void setDate(@NonNull String date) {
        this.date = date;
    }

    public MealDTO getMeal() {
        return meal;
    }

    public void setMeal(MealDTO meal) {
        this.meal = meal;
    }

    public boolean isFavourite() {
        return isFavourite;
    }

    public void setFavourite(boolean favourite) {
        isFavourite = favourite;
    }

    public boolean isPlanned() {
        return isPlanned;
    }

    public void setPlanned(boolean planned) {
        isPlanned = planned;
    }
}
