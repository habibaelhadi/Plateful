package com.example.plateful.views.mealslist;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
    String ingredientName;
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
        ingredientName = args.getIngredientName();

        presenter = new MealsListPresenterImp(this,requireContext());

        if(categoryName != null){
            presenter.getMealsByCategory(categoryName);
        }else if(areaName != null){
            presenter.getMealsByArea(areaName);
        }else{
            presenter.getMealsByIngredients(ingredientName);
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
        }else if(areaName != null){
            adapter = new CategoryAreaMealsAdapter(requireContext(),meals,areaName,this);
            binding.recyclerMealList.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }else{
            adapter = new CategoryAreaMealsAdapter(requireContext(),meals,ingredientName,this);
            binding.recyclerMealList.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void showError(String errorMessage) {
//        Dialog dialog = new Dialog(getContext());
//        dialog.setContentView(R.layout.alert_dialog);
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        TextView message = dialog.findViewById(R.id.tv_alert_message);
//        message.setText(errorMessage);
//        dialog.show();
//        dialog.findViewById(R.id.btn_dismiss).setOnClickListener(vw1 -> {
//            dialog.dismiss();
//        });
        Log.i("TAG", "showError: "+errorMessage);
    }

    @Override
    public void addToFavourites() {

    }

    @Override
    public void navigateToDetails(int id, MealDTO meal) {
        NavigateToFragments.super.navigateToDetails(id, meal);
        Navigation.findNavController(requireView()).navigate(CategoryAreaListFragmentDirections.actionCategoryAreaListFragmentToMealDetailsFragment(id,meal,false,false));
    }
}