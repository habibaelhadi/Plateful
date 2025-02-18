package com.example.plateful.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.plateful.models.db.MealsDatabase;
import com.example.plateful.utils.Converter;

@Database(entities = {MealsDatabase.class}, version = 2)
@TypeConverters(Converter.class)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase database = null;
    public abstract FoodDAO getData();

    public static synchronized AppDatabase getInstance(Context context){
        if(database == null){
            database = Room.databaseBuilder(context, AppDatabase.class, "meals_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return database;
    }
}
