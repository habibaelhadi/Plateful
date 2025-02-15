package com.example.plateful.views.favourites;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.plateful.R;
import com.example.plateful.databinding.FragmentFavouriteBinding;
import com.example.plateful.models.db.MealsDatabase;
import com.example.plateful.presenters.favourites.FavouritesPresenter;
import com.example.plateful.presenters.favourites.FavouritesPresenterImp;
import com.example.plateful.views.adapters.FavouritesAdapter;

import java.util.List;

public class FavouriteFragment extends Fragment implements FavouriteView{
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
            adapter = new FavouritesAdapter(favourites,requireContext());
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
        dialog.setTitle(errorMessage);
        dialog.show();
    }
}