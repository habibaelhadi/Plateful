package com.example.plateful.views.mealslist;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.plateful.R;
import com.example.plateful.databinding.AlertDialogBinding;
import com.example.plateful.databinding.FragmentCategoryAreaListBinding;
import com.example.plateful.models.DTOs.MealCategoryAreaDTO;
import com.example.plateful.models.DTOs.MealDTO;
import com.example.plateful.presenters.mealslist.MealsListPresenter;
import com.example.plateful.presenters.mealslist.MealsListPresenterImp;
import com.example.plateful.views.adapters.CategoryAreaMealsAdapter;
import com.example.plateful.views.home.NavigateToFragments;

import java.util.List;

public class CategoryAreaListFragment extends Fragment implements CategoryAreaListView, NavigateToFragments {

    FragmentCategoryAreaListBinding binding;
    MealsListPresenter presenter;
    CategoryAreaListFragmentArgs args;
    String categoryName;
    String areaName;
    CategoryAreaMealsAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_category_area_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding = FragmentCategoryAreaListBinding.bind(view);
        args = CategoryAreaListFragmentArgs.fromBundle(getArguments());
        categoryName = args.getCategoryName();
        areaName = args.getAreaName();
        presenter = new MealsListPresenterImp(this);

        if(categoryName != null){
            presenter.getMealsByCategory(categoryName);
        }else{
            presenter.getMealsByArea(areaName);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    @Override
    public void showMeals(List<MealCategoryAreaDTO> meals) {
        if(categoryName != null){
            adapter = new CategoryAreaMealsAdapter(requireContext(),meals,categoryName,this);
            binding.recyclerMealList.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }else{
            adapter = new CategoryAreaMealsAdapter(requireContext(),meals,areaName,this);
            binding.recyclerMealList.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void showError(String errorMessage) {
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.alert_dialog);
        AlertDialogBinding binding = AlertDialogBinding.bind(dialog.getWindow().getDecorView());
        binding.tvAlertMessage.setText(errorMessage);
        dialog.show();
    }

    @Override
    public void navigateToDetails(int id, MealDTO meal) {
        NavigateToFragments.super.navigateToDetails(id, meal);
        Navigation.findNavController(requireView()).navigate(CategoryAreaListFragmentDirections.actionCategoryAreaListFragmentToMealDetailsFragment(id,meal));
    }
}