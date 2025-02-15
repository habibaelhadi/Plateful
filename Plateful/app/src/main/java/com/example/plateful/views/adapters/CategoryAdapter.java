package com.example.plateful.views.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.plateful.R;
import com.example.plateful.models.DTOs.CategoryDTO;
import com.example.plateful.views.home.NavigateToFragments;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryHolder>{
    private List<CategoryDTO.CategoryMealDTO> categories;
    private List<CategoryDTO.CategoryMealDTO> fullList;
    Context context;
    NavigateToFragments navigateToFragments;

    public void setCategories(List<CategoryDTO.CategoryMealDTO> categories) {
        this.categories = categories;
        this.fullList = new ArrayList<>(categories);
        notifyDataSetChanged();
    }

    public CategoryAdapter(Context context, List<CategoryDTO.CategoryMealDTO> categories, NavigateToFragments navigateToFragments) {
        this.context = context;
        this.categories = categories;
        this.fullList = new ArrayList<>(categories);
        this.navigateToFragments = navigateToFragments;
    }

    @NonNull
    @Override
    public CategoryAdapter.CategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.categories_item, parent, false);
        CategoryAdapter.CategoryHolder holder = new CategoryAdapter.CategoryHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.CategoryHolder holder, int position) {
        CategoryDTO.CategoryMealDTO category = categories.get(position);
        holder.onBind(categories.get(position));
        holder.card.setOnClickListener(vw -> {
            navigateToFragments.navigateToMeals(category.getStrCategory(),null);
        });
    }

    @Override
    public int getItemCount() {return categories!=null?categories.size():0;}

    public class CategoryHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView title;
        MaterialCardView card;
        int randomColor;

        public CategoryHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.category_image);
            title = itemView.findViewById(R.id.category_item_name);
            card = itemView.findViewById(R.id.category_item_card);


            int[] colors = {
                    ContextCompat.getColor(itemView.getContext(), R.color.soft_red),
                    ContextCompat.getColor(itemView.getContext(), R.color.warm_orange),
                    ContextCompat.getColor(itemView.getContext(), R.color.soft_yellow),
                    ContextCompat.getColor(itemView.getContext(), R.color.soft_green),
                    ContextCompat.getColor(itemView.getContext(), R.color.light_blue),
                    ContextCompat.getColor(itemView.getContext(), R.color.sky_blue),
                    ContextCompat.getColor(itemView.getContext(), R.color.lavender),
                    ContextCompat.getColor(itemView.getContext(), R.color.soft_pink)
            };

            randomColor = colors[new Random().nextInt(colors.length)];
        }

        public void onBind(CategoryDTO.CategoryMealDTO category){
            title.setText(category.getStrCategory());
            Glide.with(itemView.getContext())
                    .load(category.getStrCategoryThumb())
                    .into(image);
            card.setCardBackgroundColor(randomColor);

        }
    }

    public void filter(String query) {
        List<CategoryDTO.CategoryMealDTO> filteredList = new ArrayList<>();
        for (CategoryDTO.CategoryMealDTO item : fullList) {
            if (item.getStrCategory().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(item);
            }
        }
        categories.clear();
        categories.addAll(filteredList);
        notifyDataSetChanged();
    }
}
