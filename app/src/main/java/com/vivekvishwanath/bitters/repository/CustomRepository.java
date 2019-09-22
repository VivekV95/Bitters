package com.vivekvishwanath.bitters.repository;

import androidx.lifecycle.MutableLiveData;

import com.vivekvishwanath.bitters.apis.CocktailDbDao;
import com.vivekvishwanath.bitters.models.Cocktail;
import com.vivekvishwanath.bitters.models.Ingredient;
import com.vivekvishwanath.bitters.sqlite.BittersSqlDbDao;

import java.util.ArrayList;

public class CustomRepository {

    public MutableLiveData<ArrayList<Ingredient>> getAllIngredients() {
        final MutableLiveData<ArrayList<Ingredient>> ingredients = new MutableLiveData<>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                ArrayList<Ingredient> ingredientList = new ArrayList<>();
                ingredientList = CocktailDbDao.getAllIngredients();
                ingredients.postValue(ingredientList);
            }
        }).start();
        return ingredients;
    }

    public MutableLiveData<ArrayList<Integer>> getCustomIds() {
        final MutableLiveData<ArrayList<Integer>> ids = new MutableLiveData<>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                ArrayList<Integer> idList = BittersSqlDbDao.readAllIds();
                ids.postValue(idList);
            }
        }).start();
        return ids;
    }

}
