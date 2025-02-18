package com.example.plateful.presenters.mealsdetails;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.plateful.models.db.MealsDatabase;
import com.example.plateful.models.repository.DataRepository;
import com.example.plateful.views.mealDetails.MealDetailsView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MealsDetailsPresenterImp implements MealsDetailsPresenter{
    private MealDetailsView view;
    private DataRepository repository;
    FirebaseDatabase database;
    DatabaseReference myRef;
    SharedPreferences sharedPreferences;

    public MealsDetailsPresenterImp(MealDetailsView view, Context context) {
        this.view = view;
        repository = DataRepository.getInstance(context);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("meals");
        sharedPreferences = context.getSharedPreferences("MyPrefs",0);
    }

    @Override
    public void getMealDetails(int id) {
        repository.getMealDetails(String.valueOf(id))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        success -> {
                            view.showData(success.getMeals().get(0));
                        },
                        error -> view.showError(error.getMessage())
                );

    }

    @Override
    public void addToFavourites(MealsDatabase mealsDatabase) {
        repository.insert(mealsDatabase)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> {
                            view.addToFavourites();
                        },
                        error -> {
                            view.showError(error.getMessage());
                        }
                );
    }

    @Override
    public void removeFromFavourites(MealsDatabase mealsDatabase) {
        repository.delete(mealsDatabase)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> {
                            view.removeFromFavourites();
                        },
                        error -> {
                            view.showError(error.getMessage());
                        }
                );
    }

    @Override
    public void addToPlan(MealsDatabase mealsDatabase) {
        repository.insert(mealsDatabase)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> {
                            view.addToPlan();
                        },
                        error -> {
                            view.showError(error.getMessage());
                        }
                );
    }

    @Override
    public void backupData(MealsDatabase mealsDatabase) {
        myRef.child("Users")
                .child(sharedPreferences.getString("id",""))
                .child(mealsDatabase.getMealId())
                .child(mealsDatabase.getDate())
                .setValue(mealsDatabase);
    }

    @Override
    public void removeFromFirebase(MealsDatabase mealsDatabase) {
            myRef.child("Users")
                    .child(sharedPreferences.getString("id",""))
                    .child(mealsDatabase.getMealId())
                    .child(mealsDatabase.getDate())
                    .removeValue();
    }
}
