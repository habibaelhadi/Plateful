package com.example.plateful.views.plan;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.plateful.R;
import com.example.plateful.databinding.AlertDialogBinding;
import com.example.plateful.databinding.FragmentPlanBinding;
import com.example.plateful.models.DTOs.MealDTO;
import com.example.plateful.models.db.MealsDatabase;
import com.example.plateful.presenters.plan.PlanPresenter;
import com.example.plateful.presenters.plan.PlanPresenterImp;
import com.example.plateful.views.adapters.PlanAdapter;
import com.example.plateful.views.home.NavigateToFragments;

import java.util.ArrayList;
import java.util.List;

public class PlanFragment extends Fragment implements PlanView, NavigateToFragments {

    FragmentPlanBinding binding;
    PlanPresenter planPresenter;
    List<MealsDatabase> saturdayMeals;
    List<MealsDatabase> sundayMeals;
    List<MealsDatabase> mondayMeals;
    List<MealsDatabase> tuesdayMeals;
    List<MealsDatabase> wednesdayMeals;
    List<MealsDatabase> thursdayMeals;
    List<MealsDatabase> fridayMeals;
    PlanAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_plan, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentPlanBinding.bind(view);
        planPresenter = new PlanPresenterImp(this,requireContext());
        planPresenter.getAllPlans();
        saturdayMeals = new ArrayList<>();
        sundayMeals = new ArrayList<>();
        mondayMeals = new ArrayList<>();
        tuesdayMeals = new ArrayList<>();
        wednesdayMeals = new ArrayList<>();
        thursdayMeals = new ArrayList<>();
        fridayMeals = new ArrayList<>();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    @Override
    public void showPlans(List<MealsDatabase> mealsDatabases) {
        for(MealsDatabase mealDatabase : mealsDatabases){
            switch (mealDatabase.getDate()){
                case "Saturday":{
                    saturdayMeals.add(mealDatabase);
                    adapter = new PlanAdapter(saturdayMeals,requireContext(),this);
                    binding.saturdayRecycler.setVisibility(View.VISIBLE);
                    binding.saturdayRecycler.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    break;
                }
                case "Sunday":{
                    sundayMeals.add(mealDatabase);
                    adapter = new PlanAdapter(sundayMeals,requireContext(),this);
                    binding.sundayRecycler.setVisibility(View.VISIBLE);
                    binding.sundayRecycler.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    break;
                }
                case "Monday":{
                    mondayMeals.add(mealDatabase);
                    adapter = new PlanAdapter(sundayMeals,requireContext(),this);
                    binding.mondayRecycler.setVisibility(View.VISIBLE);
                    binding.mondayRecycler.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    break;
                }
                case "Tuesday":{
                    tuesdayMeals.add(mealDatabase);
                    adapter = new PlanAdapter(sundayMeals,requireContext(),this);
                    binding.tuesdayRecycler.setVisibility(View.VISIBLE);
                    binding.tuesdayRecycler.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    break;
                }
                case "Wednesday":{
                    wednesdayMeals.add(mealDatabase);
                    adapter = new PlanAdapter(sundayMeals,requireContext(),this);
                    binding.wednesdayRecycler.setVisibility(View.VISIBLE);
                    binding.wednesdayRecycler.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    break;
                }
                case "Thursday":{
                    thursdayMeals.add(mealDatabase);
                    adapter = new PlanAdapter(sundayMeals,requireContext(),this);
                    binding.thursdayRecycler.setVisibility(View.VISIBLE);
                    binding.thursdayRecycler.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    break;
                }
                case "Friday":{
                    fridayMeals.add(mealDatabase);
                    adapter = new PlanAdapter(sundayMeals,requireContext(),this);
                    binding.fridayRecycler.setVisibility(View.VISIBLE);
                    binding.fridayRecycler.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    break;
                }
            }
        }
    }

    @Override
    public void showError(String message) {
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.alert_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        AlertDialogBinding binding = AlertDialogBinding.bind(dialog.getWindow().getDecorView());
        binding.tvAlertMessage.setText(message);
        dialog.show();
    }

    @Override
    public void navigateToDetails(int id, MealDTO meal){
        NavigateToFragments.super.navigateToDetails(id, meal);
        Navigation.findNavController(requireView()).navigate(PlanFragmentDirections.actionPlanFragment2ToMealDetailsFragment(id,meal));
    }

}