package com.vivekvishwanath.bitters.views;


import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vivekvishwanath.bitters.R;
import com.vivekvishwanath.bitters.adapters.CocktailListAdapter;
import com.vivekvishwanath.bitters.models.Cocktail;
import com.vivekvishwanath.bitters.mvvm.CocktailViewModel;
import com.vivekvishwanath.bitters.viewmodel.ViewCustomViewModel;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewCustomFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private CocktailListAdapter listAdapter;
    private FloatingActionButton addCocktailButton;

    private ViewCustomViewModel viewModel;
    private CocktailViewModel mainViewModel;

    public ViewCustomFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_view_custom, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        addCocktailButton = view.findViewById(R.id.add_cocktail_button);

        recyclerView = getView().findViewById(R.id.recycler_view_custom);
        recyclerView.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(layoutManager);

        viewModel = ViewModelProviders.of(this).get(ViewCustomViewModel.class);
        mainViewModel = ViewModelProviders.of(getActivity()).get(CocktailViewModel.class);
        viewModel.getCustomCocktails().observe(this, new Observer<ArrayList<Cocktail>>() {
            @Override
            public void onChanged(@Nullable ArrayList<Cocktail> cocktails) {
                listAdapter = new CocktailListAdapter(cocktails,mainViewModel, false);
                recyclerView.setAdapter(listAdapter);
            }
        });

        addCocktailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomCocktailFragment fragment = new CustomCocktailFragment();
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.choice_fragment_container, fragment)
                        .addToBackStack("custom")
                        .commit();
            }
        });
    }
}
