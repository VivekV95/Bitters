package com.vivekvishwanath.bitters.mvvm;

import androidx.lifecycle.MutableLiveData;
import android.content.Context;
import com.google.firebase.auth.FirebaseUser;
import com.vivekvishwanath.bitters.apis.CocktailDbDao;
import com.vivekvishwanath.bitters.apis.FirebaseDatabaseDao;
import com.vivekvishwanath.bitters.models.Cocktail;
import com.vivekvishwanath.bitters.models.Ingredient;
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

    public void addCustomCocktail(final Cocktail cocktail) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                BittersSqlDbDao.createCocktail(cocktail);
            }
        }).start();
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

    public void deleteCustomCocktail(final Cocktail cocktail) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                BittersSqlDbDao.deleteCocktail(cocktail);
            }
        }).start();
    }

}
