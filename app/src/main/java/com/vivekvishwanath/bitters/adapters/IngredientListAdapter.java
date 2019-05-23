package com.vivekvishwanath.bitters.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.vivekvishwanath.bitters.R;
import com.vivekvishwanath.bitters.models.Ingredient;
import com.vivekvishwanath.bitters.mvvm.CocktailViewModel;
import com.vivekvishwanath.bitters.views.CustomCocktailFragment;

import java.util.ArrayList;

public class IngredientListAdapter extends RecyclerView.Adapter<IngredientListAdapter.ViewHolder> {
    private ArrayList<Ingredient> ingredientList;
    private Context context;
    private CocktailViewModel viewModel;
    private boolean isClickable;

    public IngredientListAdapter(ArrayList<Ingredient> ingredientList
            , Context context, CocktailViewModel viewModel, boolean isClickable) {
        this.ingredientList = ingredientList;
        this.context = context;
        this.viewModel = viewModel;
        this.isClickable = isClickable;
    }

    @NonNull
    @Override
    public IngredientListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ingredient_card_layout, parent, false);
        return new IngredientListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final IngredientListAdapter.ViewHolder parent, final int position) {
        final Ingredient ingredient = ingredientList.get(position);
        parent.ingredientName.setText(ingredient.getName());
        parent.ingredientMeasurement.setText(ingredient.getMeasurement());
        Picasso.get().load(ingredient.getPhotoUrl()).into(parent.ingredientImage);

        if (isClickable) {
            parent.ingredientMeasurement.setFocusable(true);
            parent.ingredientCardParent.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    for (Ingredient selectedIngredient : CustomCocktailFragment.selectedIngredients) {
                        if (selectedIngredient.getName().equals(ingredient.getName())) {
                            CustomCocktailFragment.selectedIngredients.remove(selectedIngredient);
                            CustomCocktailFragment.selectedIngredientsListAdapter.notifyDataSetChanged();
                            return true;
                        }
                    }
                    CustomCocktailFragment.selectedIngredients.add(ingredient);
                    CustomCocktailFragment.selectedIngredientsListAdapter.notifyDataSetChanged();
                    return true;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return ingredientList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView ingredientName;
        private ImageView ingredientImage;
        private EditText ingredientMeasurement;
        private CardView ingredientCardParent;

        public ViewHolder(View view) {
            super(view);
            ingredientName = view.findViewById(R.id.ingredient_card_name);
            ingredientImage = view.findViewById(R.id.ingredient_card_image);
            ingredientMeasurement = view.findViewById(R.id.ingredient_card_measurement);
            ingredientCardParent = view.findViewById(R.id.ingredient_card_parent);
        }
    }
}
