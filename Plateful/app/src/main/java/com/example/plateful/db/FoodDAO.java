package com.example.plateful.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.example.plateful.models.db.MealsDatabase;

import org.checkerframework.checker.units.qual.C;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface FoodDAO {
    @Query("SELECT * FROM meals_table WHERE isFavourite = 1")
    Single<List<MealsDatabase>> getAllFavourites();

    @Query("SELECT * FROM meals_table WHERE isPlanned = 1")
    Single<List<MealsDatabase>> getAllPlans();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    Completable insert(MealsDatabase mealsDatabase);

    @Delete
    Completable delete(MealsDatabase mealsDatabase);

    @Query("DELETE FROM meals_table")
    Completable deleteAllMeals();
}
