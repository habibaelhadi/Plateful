package com.example.plateful.db;

import android.content.Context;
import com.example.plateful.models.db.MealsDatabase;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public class DatabaseLocalDataSource {
    private static DatabaseLocalDataSource databaseLocalDataSource = null;
    private FoodDAO foodDAO;
    private AppDatabase appDatabase;
    private DatabaseLocalDataSource(Context context) {
        appDatabase = AppDatabase.getInstance(context);
        foodDAO = appDatabase.getData();
    }

    public static DatabaseLocalDataSource getInstance(Context context) {
        if (databaseLocalDataSource == null) {
            databaseLocalDataSource = new DatabaseLocalDataSource(context);
        }
        return databaseLocalDataSource;
    }

    public Single<List<MealsDatabase>> getAllFavourites() {return foodDAO.getAllFavourites();}

    public Single<List<MealsDatabase>> getAllPlans() {return foodDAO.getAllPlans();}

    public Completable insert(MealsDatabase mealsDatabase) {return foodDAO.insert(mealsDatabase);}

    public Completable delete(MealsDatabase mealsDatabase) {return foodDAO.delete(mealsDatabase);}

}
