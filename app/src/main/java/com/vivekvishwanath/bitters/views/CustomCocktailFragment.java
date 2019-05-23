package com.vivekvishwanath.bitters.views;


import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.vivekvishwanath.bitters.R;
import com.vivekvishwanath.bitters.Utils.CocktailUtils;
import com.vivekvishwanath.bitters.adapters.IngredientListAdapter;
import com.vivekvishwanath.bitters.models.Cocktail;
import com.vivekvishwanath.bitters.models.Ingredient;
import com.vivekvishwanath.bitters.models.Ingredients;
import com.vivekvishwanath.bitters.mvvm.CocktailViewModel;

import java.security.SecureRandom;
import java.util.ArrayList;

public class CustomCocktailFragment extends Fragment {

    private EditText customCocktailName;
    private CheckBox showIngredientsBox;
    private EditText instructionsText;
    private Button saveButton;

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
        viewModel = ViewModelProviders.of(getActivity()).get(CocktailViewModel.class);

        showIngredientsBox = view.findViewById(R.id.show_ingredients_button);
        showIngredientsBox.setOnCheckedChangeListener(showBoxListener);

        customCocktailName = view.findViewById(R.id.custom_cocktail_name);
        instructionsText = view.findViewById(R.id.custom_cocktail_instructions_text);
        saveButton = view.findViewById(R.id.save_button);
        saveButton.setOnClickListener(saveButtonListener);

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

        viewModel.getAllIngredients().observe(getActivity(), new Observer<ArrayList<Ingredient>>() {
            @Override
            public void onChanged(@Nullable ArrayList<Ingredient> ingredients) {
                allIngredientsListAdapter = new IngredientListAdapter(ingredients, context, viewModel, true);
                allIngredientsRecyclerView.setAdapter(allIngredientsListAdapter);
            }
        });
    }

    CompoundButton.OnCheckedChangeListener showBoxListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                allIngredientsRecyclerView.setVisibility(View.VISIBLE);
            } else {
                allIngredientsRecyclerView.setVisibility(View.GONE);
            }
        }
    };

    View.OnClickListener saveButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (checkFields()) {
                Cocktail cocktail = new Cocktail(Integer.toString(createId()));
                cocktail.setDrinkName(customCocktailName.getText().toString());
                Ingredients ingredients = CocktailUtils.createIngredientsObject(selectedIngredients);
                cocktail.setIngredients(ingredients);
                cocktail.getIngredients().setIngredientsId(Integer.parseInt(cocktail.getDrinkId()));
                viewModel.addCustomCocktail(cocktail);
            }
        }
    };

    private boolean checkFields() {
        if (TextUtils.isEmpty(customCocktailName.getText().toString())) {
            return false;
        } else if (TextUtils.isEmpty(instructionsText.getText().toString())) {
            return false;
        } else if (selectedIngredients.size() < 2) {
            return false;
        }
        return true;
    }

    private int createId() {
        SecureRandom random = new SecureRandom();
        int num = random.nextInt(100000);
        if (viewModel.getCustomIds().getValue().contains(num)) {
            createId();
        }
        return num;
    }


}
