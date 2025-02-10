package com.example.plateful.home;

import static java.lang.Integer.parseInt;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.plateful.R;
import com.example.plateful.adapters.CategoryAdapter;
import com.example.plateful.adapters.CountryAdapter;
import com.example.plateful.model.CategoryDTO;
import com.example.plateful.model.CountryDTO;
import com.example.plateful.model.MealDTO;
import com.example.plateful.network.CategoryClient;
import com.example.plateful.network.CountryClient;
import com.example.plateful.network.MealClient;
import com.google.android.material.card.MaterialCardView;

import java.util.List;

public class HomeFragment extends Fragment implements MealClient.NetworkCallBack, CategoryClient.NetworkCallBack, CountryClient.NetworkCallBack {

    ImageView image;
    TextView title;
    TextView area;
    TextView category;
    MealClient mealClient;
    CategoryClient categoryClient;
    CountryClient countryClient;
    MaterialCardView cardView;
    SearchView searchView;
    RecyclerView categoryRecyclerView;
    RecyclerView countryRecyclerView;
    CategoryAdapter categoryAdapter;
    CountryAdapter countryAdapter;

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

        searchView = view.findViewById(R.id.search_view);
        EditText searchEditText = searchView.findViewById(androidx.appcompat.R.id.search_src_text);
        searchEditText.setTextColor(Color.BLACK);

        categoryRecyclerView = view.findViewById(R.id.recycler_view_categories_home);
        countryRecyclerView = view.findViewById(R.id.recycler_view_areas_home);

        mealClient = MealClient.getInstance();
        mealClient.setNetworkCallBack(this);
        mealClient.getDailyMeal();

        categoryClient = CategoryClient.getInstance();
        categoryClient.setNetworkCallBack(this);
        categoryClient.getCategories();

        countryClient = CountryClient.getInstance();
        countryClient.setNetworkCallBack(this);
        countryClient.getCountry();

        title = view.findViewById(R.id.meal_title);
        area = view.findViewById(R.id.area_title);
        category = view.findViewById(R.id.category_title);
        image = view.findViewById(R.id.meal_image);
        cardView = view.findViewById(R.id.daily_meal);


    }

    @Override
    public void onDailyMealSuccess(MealDTO meal) {
       Glide.with(getContext()).load(meal.getStrMealThumb()).into(image);
       title.setText(meal.getStrMeal());
       area.setText(meal.getStrArea());
       category.setText(meal.getStrCategory());
       cardView.setOnClickListener(vw -> {
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
        categoryRecyclerView.setAdapter(categoryAdapter);
    }

    @Override
    public void onCategoryFailure(String errorMessage) {
        Log.i("TAG", "-------onDailyMealFailure: "+errorMessage);
    }

    @Override
    public void onCountrySuccess(List<CountryDTO.MealsDTO> countries) {
        Log.i("TAG", "**************onCountrySuccess: "+countries.size());
        countryAdapter = new CountryAdapter(requireContext(),countries);
        countryRecyclerView.setAdapter(countryAdapter);
    }

    @Override
    public void onCountryFailure(String errorMessage) {
        Log.i("TAG", "**************onCountryFailure: "+errorMessage);

    }

    public void navigateToDetails(MealDTO meal, int id){
        Navigation.findNavController(requireView()).navigate(HomeFragmentDirections.actionHomeFragmentToMealDetailsFragment(id,meal));
    }
}