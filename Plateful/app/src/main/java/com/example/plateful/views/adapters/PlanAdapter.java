package com.example.plateful.views.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.plateful.R;
import com.example.plateful.models.db.MealsDatabase;
import com.example.plateful.views.home.NavigateToFragments;
import com.google.android.material.card.MaterialCardView;

import java.util.List;

public class PlanAdapter extends RecyclerView.Adapter<PlanAdapter.PlanHolder>{
    List<MealsDatabase> mealsDatabases;
    Context context;
    NavigateToFragments navigateToFragments;

    public PlanAdapter(List<MealsDatabase> mealsDatabases, Context context,NavigateToFragments navigateToFragments) {
        this.mealsDatabases = mealsDatabases;
        this.context = context;
        this.navigateToFragments = navigateToFragments;
    }

    @NonNull
    @Override
    public PlanAdapter.PlanHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.plan_item_card ,parent, false);
        PlanAdapter.PlanHolder holder = new PlanAdapter.PlanHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull PlanAdapter.PlanHolder holder, int position) {
        holder.onBind(mealsDatabases.get(position));
    }

    @Override
    public int getItemCount() {
        return mealsDatabases.size();
    }

    public class PlanHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView title;
        MaterialCardView card;
        public PlanHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.meal_image);
            title = itemView.findViewById(R.id.meal_title);
            card = itemView.findViewById(R.id.plan_card);
        }

        public void onBind(MealsDatabase mealsDatabase){
            Glide.with(itemView.getContext())
                    .load(mealsDatabase.getMeal().getStrMealThumb())
                    .into(image);
            title.setText(mealsDatabase.getMeal().getStrMeal());
            card.setOnClickListener(vw -> {
                navigateToFragments.navigateToDetails(Integer.parseInt(mealsDatabase.getMeal().getIdMeal()),mealsDatabase.getMeal());
            });
        }
    }
}
