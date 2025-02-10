package com.example.plateful.network;

import com.example.plateful.model.CategoryDTO;
import com.example.plateful.model.CountryDTO;
import com.example.plateful.model.DailyMealDTO;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MealService {

    @GET("random.php")
    Call<DailyMealDTO> getDailyMeal();

    @GET("categories.php")
    Call<CategoryDTO> getCategories();

}
