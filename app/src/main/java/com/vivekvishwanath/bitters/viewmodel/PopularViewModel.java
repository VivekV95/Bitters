package com.vivekvishwanath.bitters.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.vivekvishwanath.bitters.models.Cocktail;
import com.vivekvishwanath.bitters.repository.PopularRepository;

import java.util.ArrayList;

public class PopularViewModel extends ViewModel {

    private MutableLiveData<ArrayList<Cocktail>> popularCocktails = new MutableLiveData<>();
    private PopularRepository popularRepository;

    public MutableLiveData<ArrayList<Cocktail>> getPopularCocktails() {
        if (popularRepository == null) {
            popularRepository = new PopularRepository();
            popularCocktails = popularRepository.getPopularCocktails();
        }
        return popularCocktails;
    }
}
