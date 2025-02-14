package com.example.plateful.views.mealslist;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.plateful.R;
import com.example.plateful.databinding.FragmentCategoryAreaListBinding;

public class CategoryAreaListFragment extends Fragment {

    FragmentCategoryAreaListBinding binding;

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
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}