package com.example.plateful.network;

import com.example.plateful.models.DTOs.CategoryDTO;
import com.example.plateful.models.DTOs.CountryDTO;
import com.example.plateful.models.DTOs.DailyMealDTO;
import com.example.plateful.models.DTOs.MealDTO;
import com.example.plateful.models.DTOs.MealDetailsResponse;
import com.example.plateful.models.DTOs.MealsCategoryAreaResponse;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIService {

    @GET("random.php")
    Single<DailyMealDTO> getDailyMeal();

    @GET("categories.php")
    Single<CategoryDTO> getCategories();

    @GET("list.php?a=list")
    Single<CountryDTO> getCountry();

    @GET("filter.php")
    Single<MealsCategoryAreaResponse> getMealsByCategory(@Query("c") String category);

    @GET("filter.php")
    Single<MealsCategoryAreaResponse> getMealsByArea(@Query("a") String area);

    @GET("lookup.php")
    Single<MealDetailsResponse> getMealDetails(@Query("i") String id);
}
