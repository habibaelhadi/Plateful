package com.example.plateful.home;

import static java.lang.Integer.parseInt;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.plateful.R;
import com.example.plateful.adapters.CategoryAdapter;
import com.example.plateful.adapters.CountryAdapter;
import com.example.plateful.databinding.FragmentHomeBinding;
import com.example.plateful.databinding.FragmentMealDetailsBinding;
import com.example.plateful.databinding.HeaderBinding;
import com.example.plateful.model.CategoryDTO;
import com.example.plateful.model.CountryDTO;
import com.example.plateful.model.MealDTO;
import com.example.plateful.network.CategoryClient;
import com.example.plateful.network.CountryClient;
import com.example.plateful.network.MealClient;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

public class HomeFragment extends Fragment implements MealClient.NetworkCallBack, CategoryClient.NetworkCallBack, CountryClient.NetworkCallBack {

    MealClient mealClient;
    CategoryClient categoryClient;
    CountryClient countryClient;
    CategoryAdapter categoryAdapter;
    CountryAdapter countryAdapter;
    FragmentHomeBinding binding;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

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

        mealClient = MealClient.getInstance();
        mealClient.setNetworkCallBack(this);
        mealClient.getDailyMeal();

        categoryClient = CategoryClient.getInstance();
        categoryClient.setNetworkCallBack(this);
        categoryClient.getCategories();

        countryClient = CountryClient.getInstance();
        countryClient.setNetworkCallBack(this);
        countryClient.getCountry();

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
    public void onDailyMealSuccess(MealDTO meal) {
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
    public void onDailyMealFailure(String errorMessage) {
        Log.i("TAG", "onDailyMealFailure: "+errorMessage);
    }

    @Override
    public void onCategorySuccess(List<CategoryDTO.CategoryMealDTO> category) {
        Log.i("TAG", "##############onCategorySuccess: "+category.size());
        categoryAdapter = new CategoryAdapter(requireContext(),category);
        binding.recyclerViewCategoriesHome.setAdapter(categoryAdapter);
    }

    @Override
    public void onCategoryFailure(String errorMessage) {
        Log.i("TAG", "-------onDailyMealFailure: "+errorMessage);
    }

    @Override
    public void onCountrySuccess(List<CountryDTO.MealsDTO> countries) {
        Log.i("TAG", "**************onCountrySuccess: "+countries.size());
        countryAdapter = new CountryAdapter(requireContext(),countries);
        binding.recyclerViewAreasHome.setAdapter(countryAdapter);
    }

    @Override
    public void onCountryFailure(String errorMessage) {
        Log.i("TAG", "**************onCountryFailure: "+errorMessage);

    }

    public void navigateToDetails(MealDTO meal, int id){
        Navigation.findNavController(requireView()).navigate(HomeFragmentDirections.actionHomeFragmentToMealDetailsFragment(id,meal));
    }

    private void manageDrawer(){
       if(binding.drawer.isOpen()){
           binding.drawer.close();
       }else{
           binding.drawer.open();
       }
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