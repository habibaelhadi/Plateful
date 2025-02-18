package com.example.plateful.views.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.plateful.R;
import com.example.plateful.models.DTOs.CountryDTO;
import com.example.plateful.models.flag.FlagHelper;
import com.example.plateful.views.home.NavigateToFragments;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.CountryHolder>{

    List<CountryDTO.MealsDTO> countries;
    List<CountryDTO.MealsDTO> fullList;
    Context context;
    List<Integer> flags;
    NavigateToFragments navigateToFragments;

    public void setCountries(List<CountryDTO.MealsDTO> countries) {
        this.countries = countries;
        this.fullList = new ArrayList<>(countries);
        notifyDataSetChanged();
    }

    public CountryAdapter(Context context, List<CountryDTO.MealsDTO> countries,NavigateToFragments navigateToFragments) {
        this.context = context;
        this.countries = countries;
        this.fullList = new ArrayList<>(countries);
        this.navigateToFragments = navigateToFragments;
    }

    @NonNull
    @Override
    public CountryAdapter.CountryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.countries_item, parent, false);
        CountryAdapter.CountryHolder holder = new CountryAdapter.CountryHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CountryAdapter.CountryHolder holder, int position) {
        CountryDTO.MealsDTO country = countries.get(position);
        holder.onBind(countries.get(position));
        holder.card.setOnClickListener(vw -> {
            navigateToFragments.navigateToMeals(null,country.getStrArea(),null);
        });
    }

    @Override
    public int getItemCount() {return countries!=null?countries.size():0;}

    public class CountryHolder extends RecyclerView.ViewHolder{

        TextView title;
        TextView flag;
        ConstraintLayout card;


        public CountryHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.area_title);
            flag = itemView.findViewById(R.id.area_image);
            card = itemView.findViewById(R.id.area_card);

        }

        public void onBind(CountryDTO.MealsDTO country){
            title.setText(country.getStrArea());
            flag.setText(FlagHelper.getFlagEmoji(country.getStrArea()));
        }
    }

    public void filter(String query) {
        List<CountryDTO.MealsDTO> filteredList = new ArrayList<>();
        for (CountryDTO.MealsDTO item : fullList) {
            if (item.getStrArea().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(item);
            }
        }
        countries.clear();
        countries.addAll(filteredList);
        notifyDataSetChanged();
    }

}
