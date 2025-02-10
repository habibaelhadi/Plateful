package com.example.plateful.network;

import android.util.Log;

import com.example.plateful.model.CategoryDTO;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CategoryClient {
    public static final String TAG = "ALL_PRODUCTS_ACTIVITY";
    public static final String URL = "https://www.themealdb.com/api/json/v1/1/";
    private static CategoryClient categoryClient = null;
    private MealService mealService;
    private CategoryClient.NetworkCallBack networkCallBack;

    private CategoryClient(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mealService = retrofit.create(MealService.class);
    }

    public void setNetworkCallBack(CategoryClient.NetworkCallBack networkCallBack) {
        this.networkCallBack = networkCallBack;
    }

    public void getCategories(){
        mealService.getCategories().enqueue(new Callback<CategoryDTO>() {

            @Override
            public void onResponse(Call<CategoryDTO> call, Response<CategoryDTO> response) {
                networkCallBack.onCategorySuccess(response.body().getCategories().get(2));
                Log.i(TAG, "------onResponse: "+response.body().getCategories().get(2).getStrCategory());
            }

            @Override
            public void onFailure(Call<CategoryDTO> call, Throwable t) {
                networkCallBack.onCategoryFailure(t.getMessage());
                Log.i(TAG, "-------onFailure: "+t.getMessage());
            }
        });
    }

    public static CategoryClient getInstance(){
        if(categoryClient == null ){
            categoryClient = new CategoryClient();
        }
        return categoryClient;
    }

    public interface NetworkCallBack {
        public void onCategorySuccess(CategoryDTO.CategoryMealDTO category);
        public void onCategoryFailure(String errorMessage);
    }
}
