package com.example.plateful.utils;

import androidx.room.TypeConverter;

import com.example.plateful.models.DTOs.MealDTO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Date;

public class Converter {
    private static final Gson gson = new Gson();
    @TypeConverter
    public static String fromMeal(MealDTO meal) {
        return meal == null ? null : gson.toJson(meal);
    }

    @TypeConverter
    public static MealDTO toMeal(String mealJson) {
        if (mealJson == null) return null;
        Type type = new TypeToken<MealDTO>() {}.getType();
        return gson.fromJson(mealJson, type);
    }
}
