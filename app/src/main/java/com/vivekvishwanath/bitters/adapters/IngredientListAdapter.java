package com.vivekvishwanath.bitters.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Callback;
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
    private boolean isEditable;

    public IngredientListAdapter(ArrayList<Ingredient> ingredientList
            , Context context, CocktailViewModel viewModel, boolean isClickable
            , boolean isEditable) {
        this.ingredientList = ingredientList;
        this.context = context;
        this.viewModel = viewModel;
        this.isClickable = isClickable;
        this.isEditable = isEditable;
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
        if (isEditable) {
            parent.ingredientMeasurement.setFocusableInTouchMode(true);
            parent.ingredientMeasurement.setFocusable(true);
             parent.ingredientMeasurement.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    viewModel.updateSelectedIngredientMeasurement(position, s.toString());
                    /* viewModel.getSelectedIngredients()
                            .getValue().get(position)
                            .setMeasurement(s.toString()); */
                }
            });
        }
        Picasso.get()
                .load(ingredient.getPhotoUrl())
                .into(parent.ingredientImage, new Callback() {
                    @Override
                    public void onSuccess() {
                        parent.progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Exception e) {
                        parent.progressBar.setVisibility(View.GONE);
                    }
                });

        if (isClickable) {
            parent.ingredientCardParent.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    for (Ingredient selectedIngredient : viewModel.getSelectedIngredients().getValue()) {
                        if (selectedIngredient.getName().equals(ingredient.getName())) {
                            viewModel.getSelectedIngredients().getValue().remove(selectedIngredient);
                            CustomCocktailFragment.selectedIngredientsListAdapter.notifyDataSetChanged();
                            return true;
                        }
                    }
                    viewModel.getSelectedIngredients().getValue().add(ingredient);
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
        private ProgressBar progressBar;

        public ViewHolder(View view) {
            super(view);
            ingredientName = view.findViewById(R.id.ingredient_card_name);
            ingredientImage = view.findViewById(R.id.ingredient_card_image);
            ingredientMeasurement = view.findViewById(R.id.ingredient_card_measurement);
            ingredientCardParent = view.findViewById(R.id.ingredient_card_parent);
            progressBar = view.findViewById(R.id.ingredient_card_progress_bar);
        }
    }
}
