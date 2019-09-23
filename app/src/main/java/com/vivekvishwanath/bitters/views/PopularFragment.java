package com.vivekvishwanath.bitters.views;


import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.vivekvishwanath.bitters.R;
import com.vivekvishwanath.bitters.adapters.CocktailListAdapter;
import com.vivekvishwanath.bitters.models.Cocktail;
import com.vivekvishwanath.bitters.mvvm.CocktailViewModel;

import java.util.ArrayList;

public class PopularFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private CocktailListAdapter listAdapter;

    private CocktailViewModel viewModel;
    private ProgressBar progressBar;

    public PopularFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_popular, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = getView().findViewById(R.id.recycler_view_popular);
        recyclerView.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(layoutManager);
        progressBar = view.findViewById(R.id.popular_progress);

        viewModel = ViewModelProviders.of(getActivity()).get(CocktailViewModel.class);
        viewModel.getPopularCocktails().observe(this, new Observer<ArrayList<Cocktail>>() {
            @Override
            public void onChanged(@Nullable ArrayList<Cocktail> cocktails) {
                if (cocktails != null) {
                    progressBar.setVisibility(View.INVISIBLE);
                }
                listAdapter = new CocktailListAdapter(cocktails,viewModel, true);
                recyclerView.setAdapter(listAdapter);
            }
        });
    }
}
