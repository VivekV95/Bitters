package com.vivekvishwanath.bitters.views;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.vivekvishwanath.bitters.R;
import com.vivekvishwanath.bitters.adapters.IngredientListAdapter;
import com.vivekvishwanath.bitters.models.Ingredient;
import com.vivekvishwanath.bitters.mvvm.CocktailViewModel;

import java.util.ArrayList;

public class CustomCocktailFragment extends Fragment {

    private EditText customCocktailName;

    private RecyclerView allIngredientsRecyclerView;
    private RecyclerView.LayoutManager allIngredientsLayoutManager;
    private IngredientListAdapter allIngredientsListAdapter;

    private RecyclerView selectedIngredientsRecyclerView;
    private RecyclerView.LayoutManager selectedIngredientsLayoutManager;
    public static IngredientListAdapter selectedIngredientsListAdapter;

    private CocktailViewModel viewModel;
    private Context context;

    public static ArrayList<Ingredient> selectedIngredients;
    public CustomCocktailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_custom_cocktail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        selectedIngredients = new ArrayList<>(15);
        context = getActivity();

        allIngredientsRecyclerView = getView().findViewById(R.id.custom_cocktail_all_ingredients);
        allIngredientsRecyclerView.setHasFixedSize(true);
        allIngredientsLayoutManager = new GridLayoutManager(getContext(), 5);
        allIngredientsRecyclerView.setLayoutManager(allIngredientsLayoutManager);

        selectedIngredientsRecyclerView = view.findViewById(R.id.custom_cocktail_selected_ingredients);
        selectedIngredientsRecyclerView.setHasFixedSize(true);
        selectedIngredientsLayoutManager = new GridLayoutManager(getContext(), 3);
        selectedIngredientsRecyclerView.setLayoutManager(selectedIngredientsLayoutManager);

        selectedIngredientsListAdapter = new IngredientListAdapter(selectedIngredients, context, viewModel, true);
        selectedIngredientsRecyclerView.setAdapter(selectedIngredientsListAdapter);

        viewModel = ViewModelProviders.of(getActivity()).get(CocktailViewModel.class);
        viewModel.getAllIngredients().observe(getActivity(), new Observer<ArrayList<Ingredient>>() {
            @Override
            public void onChanged(@Nullable ArrayList<Ingredient> ingredients) {
                allIngredientsListAdapter = new IngredientListAdapter(ingredients, context, viewModel, true);
                allIngredientsRecyclerView.setAdapter(allIngredientsListAdapter);
            }
        });
    }
}
