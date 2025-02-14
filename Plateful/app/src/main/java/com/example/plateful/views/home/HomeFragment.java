package com.example.plateful.views.home;

import static java.lang.Integer.parseInt;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.example.plateful.R;
import com.example.plateful.databinding.AlertDialogBinding;
import com.example.plateful.presenters.home.HomePresenterImp;
import com.example.plateful.views.adapters.CategoryAdapter;
import com.example.plateful.views.adapters.CountryAdapter;
import com.example.plateful.databinding.FragmentHomeBinding;
import com.example.plateful.models.DTOs.CategoryDTO;
import com.example.plateful.models.DTOs.CountryDTO;
import com.example.plateful.models.DTOs.MealDTO;
import com.google.android.material.navigation.NavigationView;
import java.util.List;

public class HomeFragment extends Fragment implements HomeView {

    CategoryAdapter categoryAdapter;
    CountryAdapter countryAdapter;
    FragmentHomeBinding binding;
    HomePresenterImp homePresenterImp;
    AlertDialogBinding alertBinding;

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

        homePresenterImp = new HomePresenterImp(this);
        homePresenterImp.getDailyMeal();
        homePresenterImp.getCategories();
        homePresenterImp.getCountry();


        binding.avatar.setOnClickListener(vw -> {
            manageDrawer();
        });

        binding.nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.logout_drawable){
                    binding.drawer.close();
                    Toast.makeText(requireContext(), "logout", Toast.LENGTH_LONG).show();
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
    }

    @Override
    public void showCategories(List<CategoryDTO.CategoryMealDTO> categories) {
        categoryAdapter = new CategoryAdapter(requireContext(),categories);
        binding.recyclerViewCategoriesHome.setAdapter(categoryAdapter);
        categoryAdapter.notifyDataSetChanged();
    }

    @Override
    public void showCountries(List<CountryDTO.MealsDTO> countries) {
        countryAdapter = new CountryAdapter(requireContext(),countries);
        binding.recyclerViewAreasHome.setAdapter(countryAdapter);
        countryAdapter.notifyDataSetChanged();
    }

    @Override
    public void showError(String errorMessage) {
        Dialog dialog = new Dialog(requireContext());
        dialog.setContentView(R.layout.alert_dialog);
        alertBinding.tvAlertMessage.setText(errorMessage);
        dialog.show();
    }

    public void navigateToDetails(MealDTO meal, int id){
        Navigation.findNavController(requireView()).navigate(HomeFragmentDirections.actionHomeFragmentToMealDetailsFragment(id,meal));
    }

    private void manageDrawer(){
        if(binding.drawer.isOpen()) {binding.drawer.close();}
        else {binding.drawer.open();}
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