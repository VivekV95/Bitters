package com.vivekvishwanath.bitters.repository;

import androidx.lifecycle.MutableLiveData;

import com.vivekvishwanath.bitters.apis.CocktailDbDao;
import com.vivekvishwanath.bitters.models.Cocktail;

import java.util.ArrayList;

public class FilterRepository {

    public MutableLiveData<Cocktail> getRandomCocktail() {
        final MutableLiveData<Cocktail> randomLiveCocktail = new MutableLiveData<>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Cocktail randomCocktail = CocktailDbDao.getRandomCocktail();
                randomLiveCocktail.postValue(randomCocktail);
            }
        }).start();
        return randomLiveCocktail;
    }

    public MutableLiveData<ArrayList<Cocktail>> getCocktailsByName(final String name) {
        final MutableLiveData<ArrayList<Cocktail>> liveCocktails = new MutableLiveData<>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                ArrayList<Cocktail> cocktails = CocktailDbDao.getCocktailsByName(name);
                liveCocktails.postValue(cocktails);
            }
        }).start();
        return liveCocktails;
    }

    public MutableLiveData<ArrayList<Cocktail>> getCocktailsByIngredients(final String name) {
        final MutableLiveData<ArrayList<Cocktail>> liveCocktails = new MutableLiveData<>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                ArrayList<Cocktail> cocktails = new ArrayList<>();
                ArrayList<String> cocktailIds = CocktailDbDao.getCocktailIdsByIngredient(name);
                liveCocktails.postValue(cocktails);
                for (int i = 0; i < cocktailIds.size(); i++) {
                    cocktails.add(CocktailDbDao.getCocktailById(cocktailIds.get(i)));
                }
            }
        }).start();
        return liveCocktails;
    }

    public MutableLiveData<ArrayList<Cocktail>> getCocktailsByNoAlcohol() {
        final MutableLiveData<ArrayList<Cocktail>> liveCocktails = new MutableLiveData<>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                ArrayList<Cocktail> cocktails = new ArrayList<>();
                liveCocktails.postValue(cocktails);
                ArrayList<String> cocktailIds = CocktailDbDao.getCocktailsByNoAlcohol();
                for (int i = 0; i < cocktailIds.size(); i++) {
                    cocktails.add(CocktailDbDao.getCocktailById(cocktailIds.get(i)));
                }
            }
        }).start();
        return liveCocktails;
    }
}
