package com.vivekvishwanath.bitters.views;

import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.vivekvishwanath.bitters.R;
import com.vivekvishwanath.bitters.models.Cocktail;

import java.util.ArrayList;

public class CocktailListAdapter extends RecyclerView.Adapter<CocktailListAdapter.ViewHolder> {

    private ArrayList<Cocktail> cocktailList;
    private Context context;

    public CocktailListAdapter(ArrayList<Cocktail> cocktailList) {
        this.cocktailList = cocktailList;
    }
    @NonNull
    @Override
    public CocktailListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cocktail_card_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CocktailListAdapter.ViewHolder holder, int position) {
        Cocktail cocktail = cocktailList.get(position);
        holder.cocktailName.setText(cocktail.getDrinkName());
        Picasso.get().load(cocktail.getPhotoUrl()).into(holder.cocktailImage);
    }

    @Override
    public int getItemCount() {
        return cocktailList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView cocktailName;
        private ImageView cocktailImage;

        public ViewHolder(View view) {
            super(view);
            cocktailName = view.findViewById(R.id.cocktail_card_name);
            cocktailImage = view.findViewById(R.id.cocktail_card_image);
        }
    }
}
