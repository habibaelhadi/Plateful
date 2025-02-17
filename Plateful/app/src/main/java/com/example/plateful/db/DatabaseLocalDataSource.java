package com.example.plateful.db;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.plateful.models.db.MealsDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DatabaseLocalDataSource {
    private static DatabaseLocalDataSource databaseLocalDataSource = null;
    private FoodDAO foodDAO;
    private AppDatabase appDatabase;
    SharedPreferences sharedPreferences;
    FirebaseDatabase database;
    DatabaseReference myRef;

    private DatabaseLocalDataSource(Context context) {
        appDatabase = AppDatabase.getInstance(context);
        foodDAO = appDatabase.getData();
        sharedPreferences = context.getSharedPreferences("MyPrefs", 0);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("meals");
    }

    public static DatabaseLocalDataSource getInstance(Context context) {
        if (databaseLocalDataSource == null) {
            databaseLocalDataSource = new DatabaseLocalDataSource(context);
        }
        return databaseLocalDataSource;
    }

    public Single<List<MealsDatabase>> getAllFavourites() {
        return foodDAO.getAllFavourites();
    }

    public Single<List<MealsDatabase>> getAllPlans() {
        return foodDAO.getAllPlans();
    }

    public Completable insert(MealsDatabase mealsDatabase) {
        return foodDAO.insert(mealsDatabase);
    }

    public Completable delete(MealsDatabase mealsDatabase) {
        return foodDAO.delete(mealsDatabase);
    }

    public Completable deleteAll(){return foodDAO.deleteAllMeals();}

    public void fetchDataFromFirebase() {
        String id = sharedPreferences.getString("id", null);
        if (id != null) {
            myRef.child("Users").child(id).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Log.i("MainActivity", "onDataChange: " + dataSnapshot.getChildren());
                    for (DataSnapshot mealSnapshot : dataSnapshot.getChildren()) {
                        for (DataSnapshot dateSnapshot : mealSnapshot.getChildren()) {
                            MealsDatabase mealsDatabase = dateSnapshot.getValue(MealsDatabase.class);
                            if (mealsDatabase != null) {
                                // Insert the meal into the Room database
                                foodDAO.insert(mealsDatabase).subscribeOn(Schedulers.io()).subscribe();
                            }
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.e("SavedMealsRepository", "Error fetching data from Firebase", error.toException());
                }
            });
        }
    }


}
