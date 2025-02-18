package com.example.plateful.views.adapters;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.plateful.R;
import com.example.plateful.models.DTOs.MealCategoryAreaDTO;
import com.example.plateful.views.home.NavigateToFragments;
import com.google.android.material.card.MaterialCardView;

import java.util.List;

public class CategoryAreaMealsAdapter extends RecyclerView.Adapter<CategoryAreaMealsAdapter.CategoryMealsHolder> {
    private List<MealCategoryAreaDTO> meals;
    private Context context;
    public String name;
    NavigateToFragments navigateToFragments;
    SharedPreferences sharedPreferences;

    public CategoryAreaMealsAdapter(Context context, List<MealCategoryAreaDTO> meals, String categoryAreaName, NavigateToFragments navigateToFragments) {
        this.context = context;
        this.meals = meals;
        name = categoryAreaName;
        this.navigateToFragments = navigateToFragments;
        sharedPreferences = context.getSharedPreferences("MyPrefs",0);
    }

    @NonNull
    @Override
    public CategoryMealsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.meals_page_items, parent, false);
        CategoryMealsHolder holder = new CategoryMealsHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryMealsHolder holder, int position) {
        MealCategoryAreaDTO meal = meals.get(position);
        holder.onBind(meals.get(position));
        holder.cardView.setOnClickListener(v -> {
            if(sharedPreferences.getString("id","").equals("guest")){
                Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.alert_dialog);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                TextView message = dialog.findViewById(R.id.tv_alert_message);
                message.setText(R.string.sign_up_for_more_features);
                dialog.show();
                dialog.findViewById(R.id.btn_dismiss).setOnClickListener(vw1 -> {
                    dialog.dismiss();
                });
            }else{
                navigateToFragments.navigateToDetails(Integer.parseInt(meal.getIdMeal()),null);
            }
        });

    }

    @Override
    public int getItemCount() {return meals!=null?meals.size():0;}

    class CategoryMealsHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView image;
        TextView categoryAreaName;
        MaterialCardView cardView;

        CategoryMealsHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.meal_title_list_page);
            image = itemView.findViewById(R.id.meal_image_list_page);
            categoryAreaName = itemView.findViewById(R.id.name_of_area_or_category);
            cardView = itemView.findViewById(R.id.card_list);
        }

        public void onBind(MealCategoryAreaDTO meal){
            title.setText(meal.getStrMeal());
            Glide.with(itemView.getContext())
                    .load(meal.getStrMealThumb())
                    .into(image);
            categoryAreaName.setText(name);
        }
    }
}
