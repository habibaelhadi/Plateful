package com.example.plateful.adapters;

import android.content.Context;
import android.util.Log;
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
import com.example.plateful.model.CountryDTO;
import com.example.plateful.model.FlagHelper;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.CountryHolder>{

    List<CountryDTO.MealsDTO> countries;
    Context context;
    List<Integer> flags;

    public CountryAdapter(Context context, List<CountryDTO.MealsDTO> countries) {
        this.context = context;
        this.countries = countries;
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
        holder.onBind(countries.get(position));
    }

    @Override
    public int getItemCount() {return countries!=null?countries.size():0;}

    public class CountryHolder extends RecyclerView.ViewHolder{

        TextView title;
        TextView flag;


        public CountryHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.area_title);
            flag = itemView.findViewById(R.id.area_image);


        }

        public void onBind(CountryDTO.MealsDTO country){
            title.setText(country.getStrArea());
            flag.setText(FlagHelper.getFlagEmoji(country.getStrArea()));
        }
    }

}
