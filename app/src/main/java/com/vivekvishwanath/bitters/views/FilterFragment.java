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
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.vivekvishwanath.bitters.R;
import com.vivekvishwanath.bitters.adapters.CocktailListAdapter;
import com.vivekvishwanath.bitters.models.Cocktail;
import com.vivekvishwanath.bitters.mvvm.CocktailViewModel;

import java.util.ArrayList;

public class FilterFragment extends Fragment {

    private CocktailViewModel viewModel;
    private EditText searchBar;
    Spinner spinner;
    ArrayList<String> filterChoices;

    RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private CocktailListAdapter listAdapter;

    Context context;

    public FilterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_filter, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = view.getContext();

        viewModel = ViewModelProviders.of(getActivity()).get(CocktailViewModel.class);

        recyclerView = getView().findViewById(R.id.recycler_view_filter);
        recyclerView.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(layoutManager);

        spinner = view.findViewById(R.id.filter_options);
        filterChoices = new ArrayList<>();

        filterChoices.add(getString(R.string.filter_name_title));
        filterChoices.add(getString(R.string.filter_ingredients_title));
        filterChoices.add(getString(R.string.filter_random_title));
        filterChoices.add(getString(R.string.filter_glass_title));
        filterChoices.add(getString(R.string.filter_alcoholic_title));

        ArrayAdapter<String> choicesAdapter =
                new ArrayAdapter<>(view.getContext(), R.layout.support_simple_spinner_dropdown_item
                        , filterChoices);
        spinner.setPrompt(getString(R.string.filter_prompt));
        spinner.setAdapter(choicesAdapter);
        spinner.setOnItemSelectedListener(spinnerListener);

        searchBar = view.findViewById(R.id.search_bar);
        searchBar.addTextChangedListener(searchBarWatcher);
    }

    private AdapterView.OnItemSelectedListener spinnerListener =
            new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if (spinner.getSelectedItem().equals(getString(R.string.filter_random_title))) {
                viewModel.getRandomCocktail().observe(getActivity()
                        , new Observer<Cocktail>() {
                    @Override
                    public void onChanged(@Nullable Cocktail cocktail) {
                        ArrayList<Cocktail> cocktails = new ArrayList<>();
                        cocktails.add(cocktail);
                        listAdapter = new CocktailListAdapter(cocktails, viewModel);
                        recyclerView.setAdapter(listAdapter);
                    }
                });
            }
            if (spinner.getSelectedItem().equals(getString(R.string.filter_alcoholic_title))) {
                viewModel.getCocktailsByNoAlcohol().observe(getActivity()
                        , new Observer<ArrayList<Cocktail>>() {
                    @Override
                    public void onChanged(@Nullable ArrayList<Cocktail> cocktails) {
                        listAdapter = new CocktailListAdapter(cocktails, viewModel);
                        recyclerView.setAdapter(listAdapter);
                    }
                });
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    private TextWatcher searchBarWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            String choice = (String) spinner.getSelectedItem();
            if (choice.equals(getString(R.string.filter_name_title))) {
                viewModel.getCocktailsByName(s.toString()).observe(getActivity(), new Observer<ArrayList<Cocktail>>() {
                    @Override
                    public void onChanged(@Nullable ArrayList<Cocktail> cocktails) {
                        listAdapter = new CocktailListAdapter(cocktails, viewModel);
                        recyclerView.setAdapter(listAdapter);
                    }
                });
            }
            if (choice.equals(getString(R.string.filter_ingredients_title))) {
                viewModel.getCocktailsByIngredients(s.toString()).observe(getActivity(), new Observer<ArrayList<Cocktail>>() {
                    @Override
                    public void onChanged(@Nullable ArrayList<Cocktail> cocktails) {
                        listAdapter = new CocktailListAdapter(cocktails, viewModel);
                        recyclerView.setAdapter(listAdapter);
                    }
                });
            }

        }
    };


}
