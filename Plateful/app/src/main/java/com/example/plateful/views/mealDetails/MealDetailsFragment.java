package com.example.plateful.views.mealDetails;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;

import com.bumptech.glide.Glide;
import com.example.plateful.R;
import com.example.plateful.databinding.AlertDialogBinding;
import com.example.plateful.models.db.MealsDatabase;
import com.example.plateful.presenters.mealsdetails.MealsDetailsPresenter;
import com.example.plateful.presenters.mealsdetails.MealsDetailsPresenterImp;
import com.example.plateful.views.adapters.IngredientsAdapter;
import com.example.plateful.databinding.FragmentMealDetailsBinding;
import com.example.plateful.models.flag.FlagHelper;
import com.example.plateful.models.DTOs.MealDTO;

public class MealDetailsFragment extends Fragment implements MealDetailsView {

    FragmentMealDetailsBinding binding;
    IngredientsAdapter adapter;
    MealsDetailsPresenter presenter;
    boolean isSaved = false;
    SharedPreferences sharedPreferences;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_meal_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentMealDetailsBinding.bind(view);
        presenter = new MealsDetailsPresenterImp(this,requireContext());
        sharedPreferences = requireActivity().getSharedPreferences("MyPrefs",0);

        int id = MealDetailsFragmentArgs.fromBundle(getArguments()).getId();
        MealDTO meal = MealDetailsFragmentArgs.fromBundle(getArguments()).getMealDTO();
        if(meal != null){
            showData(meal);
        }else{
           presenter.getMealDetails(id);
        }

        binding.back.setOnClickListener(v -> {
            Navigation.findNavController(v).navigateUp();
        });
    }

    @Override
    public void showData(MealDTO meal) {
        showVideo(meal.getStrYoutube());
        binding.title.setText(meal.getStrMeal());
        Glide.with(this)
                .load(meal.getStrMealThumb())
                .into(binding.mainImage);
        if(meal.getStrTags() != null){
            binding.tags.setText(meal.getStrTags().toString());
        }else{
            binding.tags.setVisibility(View.GONE);
        }
        binding.categoryName.setText(FlagHelper.getFlagEmoji(meal.getStrArea())+" "+meal.getStrCategory());
        binding.areaName.setText(meal.getStrArea());
        Glide.with(this)
                .load("https://www.themealdb.com/images/category/"+meal.getStrCategory()+".png")
                .into(binding.categoryImage);
        binding.items.setText(meal.getIngredients().size()+" items");
        binding.actualSteps.setText(meal.getStrInstructions());
        adapter = new IngredientsAdapter(meal.getIngredients());
        binding.ingredientsRecyclerView.setAdapter(adapter);

        binding.save.setOnClickListener(vw -> {
            manageSaveButton(meal);
        });
    }

    private void manageSaveButton(MealDTO meal){
        if(isSaved){
            MealsDatabase mealsDatabase = new MealsDatabase(
                    meal.getIdMeal(),
                    sharedPreferences.getString("id",""),
                    "favourite",
                    meal,
                    true,
                    false
            );
            presenter.removeFromFavourites(mealsDatabase);
            isSaved = false;
        }else{
            MealsDatabase mealsDatabase = new MealsDatabase(
                    meal.getIdMeal(),
                    sharedPreferences.getString("id",""),
                    "favourite",
                    meal,
                    true,
                    false
            );
            presenter.addToFavourites(mealsDatabase);
            isSaved = true;
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
    public void addToFavourites() {
        binding.save.setImageResource(R.drawable.bookmark);
    }

    @Override
    public void removeFromFavourites() {
        binding.save.setImageResource(R.drawable.ic_baseline_bookmark_border_24);
    }

    private void showVideo(String youtubeUrl) {
        binding.webView.getSettings().setJavaScriptEnabled(true);
        binding.webView.getSettings().setMediaPlaybackRequiresUserGesture(false);
        binding.webView.setWebChromeClient(new WebChromeClient());

        Uri uri = Uri.parse(youtubeUrl);
        String videoId = uri.getQueryParameter("v");

        if (videoId != null) {
            String iframe = "<iframe width=\"100%\" height=\"100%\" " +
                    "src=\"https://www.youtube.com/embed/" + videoId + "?autoplay=0&mute=0\" " +
                    "frameborder=\"0\" allowfullscreen></iframe>";

            binding.webView.loadData(iframe, "text/html", "utf-8");
        } else {
            binding.webView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}