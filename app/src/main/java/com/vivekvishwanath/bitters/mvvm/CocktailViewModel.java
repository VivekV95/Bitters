package com.vivekvishwanath.bitters.mvvm;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;

import com.google.firebase.auth.FirebaseUser;
import com.vivekvishwanath.bitters.models.Cocktail;
import com.vivekvishwanath.bitters.models.Ingredient;

import java.util.ArrayList;

public class CocktailViewModel extends ViewModel {
    private MutableLiveData<ArrayList<Cocktail>> popularCocktails;
    private MutableLiveData<ArrayList<Cocktail>> favoriteCocktails;
    private MutableLiveData<Cocktail> randomCocktail;
    private MutableLiveData<ArrayList<Cocktail>> customCocktails;
    private MutableLiveData<ArrayList<Cocktail>> cocktailsByName;
    private MutableLiveData<ArrayList<Cocktail>> cocktailsByIngredients;
    private MutableLiveData<ArrayList<Cocktail>> cocktailsByNoAlcohol;
    private MutableLiveData<ArrayList<String>> favoriteIds;
    private MutableLiveData<ArrayList<Integer>> customIds;
    private MutableLiveData<ArrayList<Ingredient>> allIngredients;
    private CocktailRepository repository;

    public void loadData(Context context, FirebaseUser user) {
        repository = new CocktailRepository(context, user);
        favoriteIds = repository.getFavoriteIds();
        customIds = repository.getCustomIds();
        popularCocktails = repository.getPopularCocktails();
        favoriteCocktails = repository.getFavoriteCocktails();
        randomCocktail = repository.getRandomCocktail();
        customCocktails = repository.getCustomCocktails();
        cocktailsByNoAlcohol = repository.getCocktailsByNoAlcohol();
        allIngredients = repository.getAllIngredients();
        cocktailsByName = new MutableLiveData<>();
        cocktailsByIngredients = new MutableLiveData<>();
    }

    public MutableLiveData<ArrayList<String>> getFavoriteIds() {
        return favoriteIds;
    }

    public MutableLiveData<ArrayList<Ingredient>> getAllIngredients() {
        return allIngredients;
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
        randomCocktail = repository.getRandomCocktail();
        return randomCocktail;
    }

    public MutableLiveData<ArrayList<Cocktail>> getCocktailsByName(String name) {
        cocktailsByName = repository.getCocktailsByName(name);
        return cocktailsByName;
    }

    public MutableLiveData<ArrayList<Cocktail>> getCocktailsByIngredients(String name) {
        cocktailsByIngredients = repository.getCocktailsByIngredients(name);
        return cocktailsByIngredients;
    }

    public MutableLiveData<ArrayList<Cocktail>> getCocktailsByNoAlcohol() {
        return cocktailsByNoAlcohol;
    }

    public void updateFavoriteIds(ArrayList<String> ids) {
        repository.updateFavoriteIds(ids);
    }

    public void deleteFavoriteId(String id) {
        repository.deleteFavoriteId(id);
    }

    public void addCustomCocktail(Cocktail cocktail) {
        repository.addCustomCocktail(cocktail);
    }

    public MutableLiveData<ArrayList<Integer>> getCustomIds() {
        return customIds;
    }

}
