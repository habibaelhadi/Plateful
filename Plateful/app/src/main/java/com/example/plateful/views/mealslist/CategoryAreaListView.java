package com.example.plateful.views.mealslist;

import com.example.plateful.models.DTOs.CategoryDTO;
import com.example.plateful.models.DTOs.CountryDTO;

import java.util.List;

public interface CategoryAreaListView {
    public void showCategories(List<CategoryDTO.CategoryMealDTO> categories);
    public void showCountries(List<CountryDTO.MealsDTO> countries);
}
