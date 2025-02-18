package com.example.plateful.views.plan;

import com.example.plateful.models.db.MealsDatabase;
import java.util.List;

public interface PlanView {
    public void showPlans(List<MealsDatabase> mealsDatabases);
    public void showError(String message);

}
