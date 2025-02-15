package com.example.plateful.views.favourites;

import com.example.plateful.models.db.MealsDatabase;
import java.util.List;

public interface FavouriteView {
    public void showFavourites(List<MealsDatabase> favourites);
    public void showError(String errorMessage);
}
