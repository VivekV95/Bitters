package com.vivekvishwanath.bitters.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.vivekvishwanath.bitters.models.Cocktail;
import com.vivekvishwanath.bitters.repository.ViewCustomRepository;

import java.util.ArrayList;

public class ViewCustomViewModel extends ViewModel {
    private ViewCustomRepository viewCustomRepository;
    private MutableLiveData<ArrayList<Cocktail>> customCocktails = new MutableLiveData<>();

    public MutableLiveData<ArrayList<Cocktail>> getCustomCocktails() {
        if (viewCustomRepository == null) {
            viewCustomRepository = new ViewCustomRepository();
        }
        customCocktails = viewCustomRepository.getCustomCocktails();
        return customCocktails;
    }
}
