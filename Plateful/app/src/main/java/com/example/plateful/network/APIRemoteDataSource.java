package com.example.plateful.network;

import com.example.plateful.models.DTOs.CategoryDTO;
import com.example.plateful.models.DTOs.CountryDTO;
import com.example.plateful.models.DTOs.DailyMealDTO;
import io.reactivex.rxjava3.core.Single;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIRemoteDataSource {
    public static final String TAG = "ALL_PRODUCTS_ACTIVITY";
    public static final String URL = "https://www.themealdb.com/api/json/v1/1/";
    private static APIRemoteDataSource apiRemoteDataSource = null;
    private APIService apiService;

    private APIRemoteDataSource(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(APIService.class);
    }

    public static APIRemoteDataSource getInstance(){
        if(apiRemoteDataSource == null ){
            apiRemoteDataSource = new APIRemoteDataSource();
        }
        return apiRemoteDataSource;
    }


    public Single<DailyMealDTO> getDailyMeal() {return apiService.getDailyMeal();}

    public Single<CountryDTO> getCountry() {return apiService.getCountry();}

    public Single<CategoryDTO> getCategories() {return apiService.getCategories();}

    public void getMealById(int id){

    }
}
