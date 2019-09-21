package com.vivekvishwanath.bitters.mvvm;

import androidx.lifecycle.MutableLiveData;
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

    public MutableLiveData<ArrayList<String>> getFavoriteIds() {
        final MutableLiveData<ArrayList<String>> idsLiveData = new MutableLiveData<>();
        FirebaseDatabaseDao.getFavoriteCocktailIds(new FirebaseDatabaseDao.FavoriteIdsCallback() {
            @Override
            public void onIdsObtained(ArrayList<String> ids) {
                if (ids == null) {
                    ArrayList<String> idList = new ArrayList<>();
                    idsLiveData.postValue(idList);
                } else {
                    idsLiveData.postValue(ids);
                }
            }
        });
        return idsLiveData;
    }


    public MutableLiveData<ArrayList<Cocktail>> getFavoriteCocktails() {
        final MutableLiveData<ArrayList<Cocktail>> cocktailsLiveData = new MutableLiveData<>();

        FirebaseDatabaseDao.getFavoriteCocktailIds(new FirebaseDatabaseDao.FavoriteIdsCallback() {
            @Override
            public void onIdsObtained(final ArrayList<String> ids) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        ArrayList<Cocktail> favoriteCocktails = new ArrayList<>();
                        cocktailsLiveData.postValue(favoriteCocktails);
                            for (int i = 0; i < ids.size(); i++) {
                                Cocktail cocktail = CocktailDbDao.getCocktailById(ids.get(i));
                                favoriteCocktails.add(cocktail);
                            }
                    }
                }).start();

            }
        });
        return cocktailsLiveData;
    }

    public void updateFavoriteIds(final ArrayList<String> ids) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                FirebaseDatabaseDao.updateFavoriteCocktailIds(ids);
            }
        }).start();
    }

    public void deleteFavoriteId(final String id) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                FirebaseDatabaseDao.deleteFavoriteCocktail(id);
            }
        }).start();
    }


    public void deleteCustomCocktail(final Cocktail cocktail) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                BittersSqlDbDao.deleteCocktail(cocktail);
            }
        }).start();
    }

}
