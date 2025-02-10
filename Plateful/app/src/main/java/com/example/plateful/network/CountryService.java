package com.example.plateful.network;

import com.example.plateful.model.CountryDTO;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CountryService {
    @GET("list.php?a=list")
    Call<CountryDTO> getCountry();
}
