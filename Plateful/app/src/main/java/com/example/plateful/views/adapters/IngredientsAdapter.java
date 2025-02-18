package com.example.plateful.views.adapters;


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
import com.example.plateful.models.DTOs.IngredientDTO;
import com.google.android.material.card.MaterialCardView;
import java.util.ArrayList;

import java.util.Random;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.ViewHolder> {

    private ArrayList<IngredientDTO> ingredients;

    public IngredientsAdapter(ArrayList<IngredientDTO> ingredients) {
        this.ingredients = ingredients;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ingredient_item, parent, false);
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

        public void onBind(IngredientDTO ingredient){
            title.setText(ingredient.getTitle());
            measure.setText(ingredient.getMeasure());
            card.setCardBackgroundColor(randomColor);
            Glide.with(itemView.getContext())
                    .load("https://www.themealdb.com/images/ingredients/"+ingredient.getTitle()+".png")
                    .into(image);
        }
    }
}
