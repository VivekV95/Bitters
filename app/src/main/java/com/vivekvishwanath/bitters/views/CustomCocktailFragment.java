package com.vivekvishwanath.bitters.views;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vivekvishwanath.bitters.R;
import com.vivekvishwanath.bitters.models.Ingredient;

import java.util.ArrayList;

public class CustomCocktailFragment extends Fragment {


    public CustomCocktailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_custom_cocktail, container, false);
    }

}
