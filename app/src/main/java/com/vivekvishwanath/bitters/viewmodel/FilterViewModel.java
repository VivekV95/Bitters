package com.vivekvishwanath.bitters.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.vivekvishwanath.bitters.models.Cocktail;
import com.vivekvishwanath.bitters.repository.FilterRepository;

import java.util.ArrayList;

public class FilterViewModel extends ViewModel {

    private FilterRepository filterRepository;
    private MutableLiveData<Cocktail> randomCocktail = new MutableLiveData<>();
    private MutableLiveData<ArrayList<Cocktail>> cocktailsByName = new MutableLiveData<>();
    private MutableLiveData<ArrayList<Cocktail>> cocktailsByIngredients = new MutableLiveData<>();
    private MutableLiveData<ArrayList<Cocktail>> cocktailsByNoAlcohol = new MutableLiveData<>();

    public MutableLiveData<Cocktail> getRandomCocktail() {
        if (filterRepository == null) {
            filterRepository = new FilterRepository();
        }
        randomCocktail = filterRepository.getRandomCocktail();
        return randomCocktail;
    }

    public MutableLiveData<ArrayList<Cocktail>> getCocktailsByName(String name) {
        if (filterRepository == null) {
            filterRepository = new FilterRepository();
        }
        cocktailsByName = filterRepository.getCocktailsByName(name);
        return cocktailsByName;
    }

    public MutableLiveData<ArrayList<Cocktail>> getCocktailsByIngredients(String ingredients) {
        if (filterRepository == null) {
            filterRepository = new FilterRepository();
        }
        cocktailsByIngredients = filterRepository.getCocktailsByIngredients(ingredients);
        return cocktailsByIngredients;
    }

    public MutableLiveData<ArrayList<Cocktail>> getCocktailsByNoAlcohol() {
        if (filterRepository == null) {
            filterRepository = new FilterRepository();
        }
        cocktailsByNoAlcohol = filterRepository.getCocktailsByNoAlcohol();
        return cocktailsByNoAlcohol;
    }

}
