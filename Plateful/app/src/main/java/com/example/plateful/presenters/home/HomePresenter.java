package com.example.plateful.presenters.home;
import com.example.plateful.models.db.MealsDatabase;


public interface HomePresenter {
    public void getDailyMeal();
    public void getCategories();
    public void getCountry();
    public void getIngredients();
    public void logout();
    public void addToFavourites(MealsDatabase mealsDatabase);
    public void removeFromFavourites(MealsDatabase mealsDatabase);
    public void addToPlan(MealsDatabase mealsDatabase);
    public void getDataFromFirebase();
    public void backupData(MealsDatabase mealsDatabase);
    public void removeFromFirebase(MealsDatabase mealsDatabase);
}
