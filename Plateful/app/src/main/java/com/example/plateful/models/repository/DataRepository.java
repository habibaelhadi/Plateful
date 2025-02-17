package com.example.plateful.models.repository;

import android.content.Context;

import com.example.plateful.db.DatabaseLocalDataSource;
import com.example.plateful.models.DTOs.AllIngredients;
import com.example.plateful.models.DTOs.CategoryDTO;
import com.example.plateful.models.DTOs.CountryDTO;
import com.example.plateful.models.DTOs.DailyMealDTO;
import com.example.plateful.models.DTOs.MealDetailsResponse;
import com.example.plateful.models.DTOs.MealsCategoryAreaResponse;
import com.example.plateful.models.db.MealsDatabase;
import com.example.plateful.network.APIRemoteDataSource;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

public class DataRepository {
    private static DataRepository instance = null;
    private APIRemoteDataSource apiRemoteDataSource;
    private DatabaseLocalDataSource databaseLocalDataSource;

    private DataRepository(Context context) {
        apiRemoteDataSource = APIRemoteDataSource.getInstance();
        databaseLocalDataSource = DatabaseLocalDataSource.getInstance(context);
    }

    public static DataRepository getInstance(Context context) {
        if (instance == null) {
            instance = new DataRepository(context);
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

    public Observable<AllIngredients> getAllIngredients(){ return apiRemoteDataSource.getAllIngredients();}

    public Single<List<MealsDatabase>> getAllFavourites() {return databaseLocalDataSource.getAllFavourites();}

    public Single<List<MealsDatabase>> getAllPlans() {return databaseLocalDataSource.getAllPlans();}

    public Completable insert(MealsDatabase mealsDatabase) {return databaseLocalDataSource.insert(mealsDatabase);}

    public Completable delete(MealsDatabase mealsDatabase) {return databaseLocalDataSource.delete(mealsDatabase);}

    public Completable deleteAll(){return databaseLocalDataSource.deleteAll();}

    public void fetchDataFromFirebase() {
        databaseLocalDataSource.fetchDataFromFirebase();
    }
}
