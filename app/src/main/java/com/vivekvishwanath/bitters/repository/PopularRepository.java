package com.vivekvishwanath.bitters.repository;

import androidx.lifecycle.MutableLiveData;

import com.vivekvishwanath.bitters.apis.CocktailDbDao;
import com.vivekvishwanath.bitters.models.Cocktail;

import java.util.ArrayList;

public class PopularRepository {

    public MutableLiveData<ArrayList<Cocktail>> getPopularCocktails() {
        final MutableLiveData<ArrayList<Cocktail>> cocktailsLiveData = new MutableLiveData<>();

        new Thread(new Runnable() {
            @Override
            public void run() {
                ArrayList<Cocktail> popularCocktails = CocktailDbDao.getPopularCocktails();
                cocktailsLiveData.postValue(popularCocktails);
            }
        }).start();

        return cocktailsLiveData;
    }
}
