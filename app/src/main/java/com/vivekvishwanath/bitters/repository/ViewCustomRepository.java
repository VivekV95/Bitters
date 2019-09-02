package com.vivekvishwanath.bitters.repository;

import android.arch.lifecycle.MutableLiveData;

import com.vivekvishwanath.bitters.models.Cocktail;
import com.vivekvishwanath.bitters.sqlite.BittersSqlDbDao;

import java.util.ArrayList;

public class ViewCustomRepository {

    public MutableLiveData<ArrayList<Cocktail>> getCustomCocktails() {
        final MutableLiveData<ArrayList<Cocktail>> cocktailsLiveData = new MutableLiveData<>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                ArrayList<Cocktail> customCocktails = BittersSqlDbDao.readAllCocktails();
                cocktailsLiveData.postValue(customCocktails);
            }
        }).start();
        return cocktailsLiveData;
    }
}
