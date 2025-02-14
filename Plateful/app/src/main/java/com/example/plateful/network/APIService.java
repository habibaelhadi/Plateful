package com.example.plateful.network;

import com.example.plateful.models.DTOs.CategoryDTO;
import com.example.plateful.models.DTOs.CountryDTO;
import com.example.plateful.models.DTOs.DailyMealDTO;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Call;
import retrofit2.http.GET;

public interface APIService {

    @GET("random.php")
    Single<DailyMealDTO> getDailyMeal();

    @GET("categories.php")
    Single<CategoryDTO> getCategories();

    @GET("list.php?a=list")
    Single<CountryDTO> getCountry();
}
