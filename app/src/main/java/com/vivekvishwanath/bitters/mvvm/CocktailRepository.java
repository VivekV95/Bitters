package com.vivekvishwanath.bitters.mvvm;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import com.google.firebase.auth.FirebaseUser;
import com.vivekvishwanath.bitters.apis.CocktailDbDao;
import com.vivekvishwanath.bitters.apis.FirebaseDatabaseDao;
import com.vivekvishwanath.bitters.models.Cocktail;
import com.vivekvishwanath.bitters.sqlite.BittersSqlDbDao;

import java.util.ArrayList;

public class CocktailRepository {


    public CocktailRepository(Context context, FirebaseUser user) {
        CocktailDbDao.initializeInstance();
        FirebaseDatabaseDao.initializeInstance(user);
        BittersSqlDbDao.initializeInstance(context);
    }

    public  MutableLiveData<ArrayList<Cocktail>> getPopularCocktails() {
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


    public MutableLiveData<ArrayList<Cocktail>> getFavoriteCocktails() {
        final MutableLiveData<ArrayList<Cocktail>> cocktailsLiveData = new MutableLiveData<>();

        FirebaseDatabaseDao.getFavoriteCocktailIds(new FirebaseDatabaseDao.FavoriteIdsCallback() {
            @Override
            public void onIdsObtained(final ArrayList<Integer> ids) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        ArrayList<Cocktail> favoriteCocktails = new ArrayList<>();
                        for (int i = 0; i < ids.size(); i++ ) {
                            Cocktail cocktail = CocktailDbDao.getCocktailById(Integer.toString(ids.get(i)));
                            favoriteCocktails.add(cocktail);
                        }
                        cocktailsLiveData.postValue(favoriteCocktails);
                    }
                }).start();

            }
        });
        return cocktailsLiveData;
    }

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

}
