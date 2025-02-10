package com.example.plateful.network;


import android.util.Log;

import com.example.plateful.model.CountryDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CountryClient {
    public static final String TAG = "ALL_PRODUCTS_ACTIVITY";
    public static final String URL = "https://www.themealdb.com/api/json/v1/1/";
    private static CountryClient countryClient = null;
    private CountryService countryService;
    private CountryClient.NetworkCallBack networkCallBack;

    private CountryClient(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        countryService = retrofit.create(CountryService.class);
    }

    public void setNetworkCallBack(CountryClient.NetworkCallBack networkCallBack) {
        this.networkCallBack = networkCallBack;

    }

    public void getCountry(){
        countryService.getCountry().enqueue(new Callback<CountryDTO>() {
            @Override
            public void onResponse(Call<CountryDTO> call, Response<CountryDTO> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<CountryDTO.MealsDTO> meals = response.body().getMeals();
                    if (meals != null && !meals.isEmpty()) {
                        networkCallBack.onCountrySuccess(meals);
                    } else {
                        Log.e(TAG, "Meals list is null or empty.");
                        networkCallBack.onCountryFailure("No meals found in the response.");
                    }
                } else {
                    Log.e(TAG, "API call failed: " + response.message());
                    networkCallBack.onCountryFailure("Failed to fetch data: " + response.message());
                }
    }

            @Override
            public void onFailure(Call<CountryDTO> call, Throwable t) {

            }
        });
    }

    public static CountryClient getInstance(){
        if(countryClient == null ){
            countryClient = new CountryClient();
        }
        return countryClient;
    }

    public interface NetworkCallBack {
        public void onCountrySuccess(List<CountryDTO.MealsDTO> countries);
        public void onCountryFailure(String errorMessage);
    }
}
