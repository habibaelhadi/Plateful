package com.example.plateful.views.home;

import static java.lang.Integer.parseInt;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.plateful.R;
import com.example.plateful.databinding.AlertDialogBinding;
import com.example.plateful.models.DTOs.AllIngredients;
import com.example.plateful.models.db.MealsDatabase;
import com.example.plateful.models.enums.ChipsTypes;
import com.example.plateful.presenters.home.HomePresenterImp;
import com.example.plateful.views.adapters.AllIngredientsAdapter;
import com.example.plateful.views.adapters.CategoryAdapter;
import com.example.plateful.views.adapters.CountryAdapter;
import com.example.plateful.databinding.FragmentHomeBinding;
import com.example.plateful.models.DTOs.CategoryDTO;
import com.example.plateful.models.DTOs.CountryDTO;
import com.example.plateful.models.DTOs.MealDTO;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.navigation.NavigationView;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HomeFragment extends Fragment implements HomeView,NavigateToFragments{

    CategoryAdapter categoryAdapter;
    CountryAdapter countryAdapter;
    FragmentHomeBinding binding;
    HomePresenterImp homePresenterImp;
    AlertDialogBinding alertBinding;
    SharedPreferences sharedPreferences;
    ChipsTypes chipsTypes;
    AllIngredientsAdapter ingredientsAdapter;
    MealDTO meal;
    boolean isFavourite = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding = FragmentHomeBinding.bind(view);

        EditText searchEditText = binding.searchView.findViewById(androidx.appcompat.R.id.search_src_text);
        searchEditText.setTextColor(Color.BLACK);

        sharedPreferences = requireActivity().getSharedPreferences("MyPrefs", 0);

        categoryAdapter = new CategoryAdapter(requireContext(),new ArrayList<>(),this);
        binding.recyclerViewCategoriesHome.setAdapter(categoryAdapter);

        countryAdapter = new CountryAdapter(requireContext(),new ArrayList<>(),this);
        binding.recyclerViewAreasHome.setAdapter(countryAdapter);

        ingredientsAdapter = new AllIngredientsAdapter(requireContext(), new ArrayList<>(), this);
        binding.recyclerViewIngredientsHome.setAdapter(ingredientsAdapter);

        homePresenterImp = new HomePresenterImp(this,requireContext());
        homePresenterImp.getDailyMeal();
        homePresenterImp.getCategories();
        homePresenterImp.getCountry();
        homePresenterImp.getIngredients();


        binding.avatar.setOnClickListener(vw -> {
            manageDrawer();
        });

        binding.nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.logout_drawable){
                    binding.drawer.close();
                    homePresenterImp.logout();
                }
                return false;
            }
        });

        binding.hello.setText("Hello! Habiba");

        View header = binding.nav.getHeaderView(0);
        TextView name = header.findViewById(R.id.username_header);
        TextView email = header.findViewById(R.id.email_header);

        name.setText("Habiba Elhadi");
        email.setText("habibaelhadi9@gmail.com");

        binding.searchView.setOnSearchClickListener(vw -> {
            binding.chipGroup.setVisibility(View.VISIBLE);
        });

        binding.chipGroup.setOnCheckedStateChangeListener(new ChipGroup.OnCheckedStateChangeListener() {
            @Override
            public void onCheckedChanged(@NonNull ChipGroup group, @NonNull List<Integer> checkedIds) {
                    if (!checkedIds.isEmpty()) {
                        if (checkedIds.get(0) == R.id.chip_category) {
                            chipsTypes = ChipsTypes.CATEGORY;
                        } else if (checkedIds.get(0) == R.id.chip_country) {
                            chipsTypes = ChipsTypes.AREA;
                        } else if (checkedIds.get(0) == R.id.chip_ingredient) {
                            chipsTypes = ChipsTypes.INGREDIENT;
                        }
                    }
                    updateViewVisibility(chipsTypes);
                }
        });

        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(chipsTypes == ChipsTypes.CATEGORY){
                    categoryAdapter.filter(newText);
                }else if(chipsTypes == ChipsTypes.AREA){
                    countryAdapter.filter(newText);
                }else if (chipsTypes == ChipsTypes.INGREDIENT) {
                    ingredientsAdapter.filter(newText);
                }
                return false;
            }
        });

        binding.searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                binding.chipGroup.setVisibility(View.GONE);
                updateViewVisibility(null);
                return false;
            }
        });

        binding.addToFavourites.setOnClickListener(vw -> {
           if(!isFavourite){
               MealsDatabase mealsDatabase = new MealsDatabase(
                       meal.getIdMeal(),
                       sharedPreferences.getString("id",""),
                       "favourite",
                       meal,
                       true,
                       false
               );
               homePresenterImp.addToFavourites(mealsDatabase);
               isFavourite = true;
           }else{
               MealsDatabase mealsDatabase = new MealsDatabase(
                       meal.getIdMeal(),
                       sharedPreferences.getString("id",""),
                       "favourite",
                       meal,
                       true,
                       false
               );
               homePresenterImp.removeFromFavourites(mealsDatabase);
               isFavourite = false;
           }
        });
    }

    @Override
    public void showDailyMeal(MealDTO meal) {
        Glide.with(getContext()).load(meal.getStrMealThumb()).into(binding.mealImage);
        binding.mealTitle.setText(meal.getStrMeal());
        binding.areaTitle.setText(meal.getStrArea());
        binding.categoryTitle.setText(meal.getStrCategory());
        binding.dailyMeal.setOnClickListener(vw -> {
            int id = parseInt(meal.getIdMeal());
            navigateToDetails(meal,id);
        });
        this.meal = meal;
    }

    @Override
    public void showCategories(List<CategoryDTO.CategoryMealDTO> categories) {
        categoryAdapter.setCategories(categories);
        categoryAdapter.notifyDataSetChanged();
    }

    @Override
    public void showCountries(List<CountryDTO.MealsDTO> countries) {
        countryAdapter.setCountries(countries);
        countryAdapter.notifyDataSetChanged();
    }

    @Override
    public void showIngredients(List<AllIngredients.Ingredients> ingredients) {
        ingredientsAdapter.setIngredients(ingredients);
        ingredientsAdapter.notifyDataSetChanged();
    }

    @Override
    public void showError(String errorMessage) {
        Dialog dialog = new Dialog(requireContext());
        dialog.setContentView(R.layout.alert_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertBinding.tvAlertMessage.setText(errorMessage);
        dialog.show();
    }

    @Override
    public void navigateToDetails(MealDTO meal, int id){
        Navigation.findNavController(requireView()).navigate(HomeFragmentDirections.actionHomeFragmentToMealDetailsFragment(id,meal));
    }

    @Override
    public void navigateToMeals(String category, String area){
        Navigation.findNavController(requireView()).navigate(HomeFragmentDirections.actionHomeFragmentToCategoryAreaListFragment(category,area));
    }

    private void manageDrawer(){
        if(binding.drawer.isOpen()) {binding.drawer.close();}
        else {binding.drawer.open();}
    }

    @Override
    public void logout(){
        Navigation.findNavController(requireView()).navigate(HomeFragmentDirections.actionHomeFragmentToIntroFragment());
        sharedPreferences.edit().putBoolean("google",false).apply();
        sharedPreferences.edit().putBoolean("login",false).apply();
    }

    @Override
    public void addToFavourites() {binding.addToFavourites.setText(R.string.remove_from_favourites);}

    @Override
    public void removeFromFavourites() {binding.addToFavourites.setText(R.string.add_to_favourites);}

    private void updateViewVisibility(ChipsTypes type) {
        boolean showAll = (type == null);

        binding.daily.setVisibility(showAll ? View.VISIBLE : View.GONE);
        binding.dailyMeal.setVisibility(showAll ? View.VISIBLE : View.GONE);

        binding.categoryTitleHome.setVisibility(showAll || type == ChipsTypes.CATEGORY ? View.VISIBLE : View.GONE);
        binding.recyclerViewCategoriesHome.setVisibility(showAll || type == ChipsTypes.CATEGORY ? View.VISIBLE : View.GONE);

        binding.areasTitleHome.setVisibility(showAll || type == ChipsTypes.AREA ? View.VISIBLE : View.GONE);
        binding.recyclerViewAreasHome.setVisibility(showAll || type == ChipsTypes.AREA ? View.VISIBLE : View.GONE);

        binding.ingredientTitleHome.setVisibility(showAll || type == ChipsTypes.INGREDIENT ? View.VISIBLE : View.GONE);
        binding.recyclerViewIngredientsHome.setVisibility(showAll || type == ChipsTypes.INGREDIENT ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onPause() {
        super.onPause();
        binding.drawer.close();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}