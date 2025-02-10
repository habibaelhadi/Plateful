package com.example.plateful.network;

import com.example.plateful.model.DailyMealDTO;
import com.example.plateful.model.MealDTO;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MealClient {
    public static final String TAG = "ALL_PRODUCTS_ACTIVITY";
    public static final String URL = "https://www.themealdb.com/api/json/v1/1/";
    private static MealClient mealClient = null;
    private MealService mealService;
    private NetworkCallBack networkCallBack;

    private MealClient(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mealService = retrofit.create(MealService.class);
    }

    public void setNetworkCallBack(NetworkCallBack networkCallBack) {
        this.networkCallBack = networkCallBack;
    }

    public void getDailyMeal(){
        mealService.getDailyMeal().enqueue(new Callback<DailyMealDTO>() {
            @Override
            public void onResponse(Call<DailyMealDTO> call, Response<DailyMealDTO> response) {
                networkCallBack.onDailyMealSuccess(response.body().getMeals().get(0));
            }

            @Override
            public void onFailure(Call<DailyMealDTO> call, Throwable t) {
                networkCallBack.onDailyMealFailure(t.getMessage());
            }
        });

    }

    public static MealClient getInstance(){
        if(mealClient == null ){
            mealClient = new MealClient();
        }
        return mealClient;
    }

    public interface NetworkCallBack {
        public void onDailyMealSuccess(MealDTO meal);
        public void onDailyMealFailure(String errorMessage);
    }
}
