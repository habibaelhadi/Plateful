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
import com.example.plateful.models.DTOs.AllIngredients;
import com.example.plateful.models.DTOs.IngredientDTO;
import com.example.plateful.views.home.NavigateToFragments;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AllIngredientsAdapter extends RecyclerView.Adapter<AllIngredientsAdapter.ViewHolder> {

    private List<AllIngredients.Ingredients> ingredients;
    private List<AllIngredients.Ingredients> fullList;
    Context context;
    NavigateToFragments navigateToFragments;

    public AllIngredientsAdapter(Context context, List<AllIngredients.Ingredients> ingredients, NavigateToFragments navigateToFragments) {
        this.context = context;
        this.ingredients = ingredients;
        this.fullList = new ArrayList<>(ingredients);
        this.navigateToFragments = navigateToFragments;
    }

    public void setIngredients(List<AllIngredients.Ingredients> ingredients) {
        this.ingredients = ingredients;
        this.fullList = new ArrayList<>(ingredients);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.ingredient_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.onBind(ingredients.get(position));
    }

    @Override
    public int getItemCount() {
        return ingredients!=null?ingredients.size():0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView title , measure;
        ImageView image;
        MaterialCardView card;

        int randomColor;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.ingredient_name);
            measure = itemView.findViewById(R.id.measure);
            image = itemView.findViewById(R.id.ingredient_image);
            card = itemView.findViewById(R.id.ingredient_card);


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

        public void onBind(AllIngredients.Ingredients ingredient){
            title.setText(ingredient.getStrIngredient());
            card.setCardBackgroundColor(randomColor);
            Glide.with(itemView.getContext())
                    .load("https://www.themealdb.com/images/ingredients/"+ingredient.getStrIngredient()+".png")
                    .into(image);

            card.setOnClickListener(vw -> {
                navigateToFragments.navigateToMeals(null,null,ingredient.getStrIngredient());
            });
        }
    }

    public void filter(String query) {
        List<AllIngredients.Ingredients> filteredList = new ArrayList<>();
        for (AllIngredients.Ingredients item : fullList) {
            if (item.getStrIngredient().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(item);
            }
        }
        ingredients.clear();
        ingredients.addAll(filteredList);
        notifyDataSetChanged();
    }
}
