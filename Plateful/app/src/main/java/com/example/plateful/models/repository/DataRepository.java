package com.example.plateful.models.repository;

import com.example.plateful.db.DatabaseLocalDataSource;
import com.example.plateful.models.DTOs.CategoryDTO;
import com.example.plateful.models.DTOs.CountryDTO;
import com.example.plateful.models.DTOs.DailyMealDTO;
import com.example.plateful.models.DTOs.MealDetailsResponse;
import com.example.plateful.models.DTOs.MealsCategoryAreaResponse;
import com.example.plateful.network.APIRemoteDataSource;
import io.reactivex.rxjava3.core.Single;

public class DataRepository {
    private static DataRepository instance = null;
    private APIRemoteDataSource apiRemoteDataSource;
    private DatabaseLocalDataSource databaseLocalDataSource;

    private DataRepository() {
        apiRemoteDataSource = APIRemoteDataSource.getInstance();
        databaseLocalDataSource = new DatabaseLocalDataSource();
    }

    public static DataRepository getInstance() {
        if (instance == null) {
            instance = new DataRepository();
        }
        return instance;
    }

    public Single<DailyMealDTO> getDailyMeal() {return apiRemoteDataSource.getDailyMeal();}

    public Single<CategoryDTO> getCategories() {return apiRemoteDataSource.getCategories();}

    public Single<CountryDTO> getCountry() {return apiRemoteDataSource.getCountry();}

    public Single<MealsCategoryAreaResponse> getMealsByCategory(String category){
        return apiRemoteDataSource.getMealsByCategory(category);
    }

    public Single<MealsCategoryAreaResponse> getMealsByArea(String area){
        return apiRemoteDataSource.getMealsByArea(area);
    }

    public Single<MealDetailsResponse> getMealDetails(String id){
        return apiRemoteDataSource.getMealDetails(id);
    }

    public void getMealById(int id){
        apiRemoteDataSource.getMealById(id);
    }
}
