package com.example.plateful.views.favourites;

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

import com.example.plateful.R;
import com.example.plateful.databinding.FragmentFavouriteBinding;
import com.example.plateful.models.DTOs.MealDTO;
import com.example.plateful.models.db.MealsDatabase;
import com.example.plateful.presenters.favourites.FavouritesPresenter;
import com.example.plateful.presenters.favourites.FavouritesPresenterImp;
import com.example.plateful.views.adapters.FavouritesAdapter;
import com.example.plateful.views.home.NavigateToFragments;
import com.example.plateful.views.mealslist.CategoryAreaListFragmentDirections;

import java.util.List;

public class FavouriteFragment extends Fragment implements FavouriteView, NavigateToFragments {
    FragmentFavouriteBinding binding;
    FavouritesAdapter adapter;
    FavouritesPresenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favourite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding = FragmentFavouriteBinding.bind(view);
        presenter = new FavouritesPresenterImp(this,requireContext());
        presenter.getFavourites();


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    @Override
    public void showFavourites(List<MealsDatabase> favourites) {
        if(favourites.isEmpty()){
            binding.recyclerFav.setVisibility(View.GONE);
            binding.noFavGroup.setVisibility(View.VISIBLE);
        }else{
            adapter = new FavouritesAdapter(favourites,requireContext(),this);
            binding.noFavGroup.setVisibility(View.GONE);
            binding.recyclerFav.setVisibility(View.VISIBLE);
            binding.recyclerFav.setAdapter(adapter);
            adapter.setButtonOnClick(mealsDatabase -> {
                presenter.removeFromFavourites(mealsDatabase);
                presenter.getFavourites();
            });
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void showError(String errorMessage) {
        Dialog dialog = new Dialog(requireContext());
        dialog.setContentView(R.layout.alert_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setTitle(errorMessage);
        dialog.show();
    }

    @Override
    public void navigateToDetails(int id, MealDTO meal){
        NavigateToFragments.super.navigateToDetails(id, meal);
        Navigation.findNavController(requireView()).navigate(FavouriteFragmentDirections.actionFavouriteFragmentToMealDetailsFragment(id,meal));
    }
}