package com.vivekvishwanath.bitters.mvvm;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;

import com.google.firebase.auth.FirebaseUser;
import com.vivekvishwanath.bitters.models.Cocktail;

import java.util.ArrayList;

public class CocktailViewModel extends ViewModel {
    private MutableLiveData<ArrayList<Cocktail>> popularCocktails;
    private MutableLiveData<ArrayList<Cocktail>> favoriteCocktails;
    private MutableLiveData<Cocktail> randomCocktail;
    private MutableLiveData<ArrayList<Cocktail>> customCocktails;
    private CocktailRepository repository;

    public void loadData(Context context, FirebaseUser user) {
        repository = new CocktailRepository(context, user);
        popularCocktails = repository.getPopularCocktails();
        favoriteCocktails = repository.getFavoriteCocktails();
        randomCocktail = repository.getRandomCocktail();
        customCocktails = repository.getCustomCocktails();
    }

    public MutableLiveData<ArrayList<Cocktail>> getPopularCocktails() {
        return popularCocktails;
    }

    public MutableLiveData<ArrayList<Cocktail>> getFavoriteCocktails() {
        return favoriteCocktails;
    }

    public MutableLiveData<ArrayList<Cocktail>> getCustomCocktails() {
        return customCocktails;
    }

    public MutableLiveData<Cocktail> getRandomCocktail() {
        return randomCocktail;
    }
}
