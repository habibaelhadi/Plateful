package com.example.plateful.views.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.plateful.R;
import com.example.plateful.models.db.MealsDatabase;

import java.util.List;

public class FavouritesAdapter extends RecyclerView.Adapter<FavouritesAdapter.FavouriteHolder>{

    private List<MealsDatabase> meals;
    Context context;
    ButtonOnClick buttonOnClick;

    public void setButtonOnClick(ButtonOnClick buttonOnClick) {
        this.buttonOnClick = buttonOnClick;
    }

    public FavouritesAdapter(List<MealsDatabase> meals, Context context) {
        this.meals = meals;
        this.context = context;
    }

    @NonNull
    @Override
    public FavouriteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_page_items ,parent, false);
        FavouritesAdapter.FavouriteHolder holder = new FavouritesAdapter.FavouriteHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull FavouriteHolder holder, int position) {
        holder.onBind(meals.get(position));
    }

    @Override
    public int getItemCount() {
        return meals.size();
    }

    public class FavouriteHolder extends RecyclerView.ViewHolder{

        ImageView image;
        TextView title;
        TextView category;
        Button removeFromFav;
        Button addToPlan;

        public FavouriteHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.meal_image_list_page);
            title = itemView.findViewById(R.id.meal_title_list_page);
            category = itemView.findViewById(R.id.name_of_area_or_category);
            removeFromFav = itemView.findViewById(R.id.add_to_favourites_list_page);
            addToPlan = itemView.findViewById(R.id.add_to_plan_list_page);
        }

        public void onBind(MealsDatabase mealsDatabase){
            removeFromFav.setText(R.string.remove_from_favourites);
            title.setText(mealsDatabase.getMeal().getStrMeal());
            category.setText(mealsDatabase.getMeal().getStrArea());
            Glide.with(itemView.getContext())
                    .load(mealsDatabase.getMeal().getStrMealThumb())
                    .into(image);
            addToPlan.setVisibility(View.GONE);
            removeFromFav.setOnClickListener(vw -> {
                buttonOnClick.setOnClick(mealsDatabase);
            });
        }
    }

    public interface ButtonOnClick{
        public void setOnClick(MealsDatabase mealsDatabase);
    }
}
