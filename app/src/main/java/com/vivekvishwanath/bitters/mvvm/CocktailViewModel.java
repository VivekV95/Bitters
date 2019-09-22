package com.vivekvishwanath.bitters.mvvm;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import android.content.Context;

import com.google.firebase.auth.FirebaseUser;
import com.vivekvishwanath.bitters.models.Cocktail;

import java.util.ArrayList;

public class CocktailViewModel extends ViewModel {
    private MutableLiveData<ArrayList<Cocktail>> favoriteCocktails;
    private MutableLiveData<ArrayList<String>> favoriteIds;
    private MutableLiveData<Integer> currentFragment;
    private CocktailRepository repository;

    public void loadData(Context context, FirebaseUser user) {
        repository = new CocktailRepository(context, user);
        favoriteIds = repository.getFavoriteIds();
        favoriteCocktails = repository.getFavoriteCocktails();
    }

    public MutableLiveData<ArrayList<String>> getFavoriteIds() {
        return favoriteIds;
    }

    public MutableLiveData<ArrayList<Cocktail>> getFavoriteCocktails() {
        return favoriteCocktails;
    }

    public void updateFavoriteIds(ArrayList<String> ids) {
        repository.updateFavoriteIds(ids);
    }

    public void deleteFavoriteId(String id) {
        repository.deleteFavoriteId(id);
    }

    public void deleteCustomCocktail(Cocktail cocktail) {
        repository.deleteCustomCocktail(cocktail);
    }

    public void setSelectedFragment(int fragment) {
        if (currentFragment == null) {
            currentFragment = new MutableLiveData<>();
        }
        currentFragment.setValue(fragment);
    }

    public int getCurrentFragment() {
        if (currentFragment == null) {
            currentFragment = new MutableLiveData<>();
            currentFragment.setValue(0);
            return currentFragment.getValue();
        } else {
            return currentFragment.getValue();
        }
    }

    public void addCustomCocktail(Cocktail cocktail) {
        repository.addCustomCocktail(cocktail);
    }

}
