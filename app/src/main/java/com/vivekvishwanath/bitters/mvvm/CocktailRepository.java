package com.vivekvishwanath.bitters.mvvm;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.support.annotation.NonNull;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.vivekvishwanath.bitters.apis.CocktailDbDao;
import com.vivekvishwanath.bitters.models.Cocktail;

import java.util.ArrayList;

public class CocktailRepository {

    public static LiveData<ArrayList<Cocktail>> getPopularCocktails() {
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

    public static LiveData<ArrayList<Cocktail>> getCustomCocktails() {
        final MutableLiveData<ArrayList<Cocktail>> cocktailsLiveData = new MutableLiveData<>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                ArrayList<Cocktail> customCocktails = CocktailDbDao.getPopularCocktails();
                cocktailsLiveData.postValue(customCocktails);
            }
        }).start();
        return cocktailsLiveData;
    }
}
