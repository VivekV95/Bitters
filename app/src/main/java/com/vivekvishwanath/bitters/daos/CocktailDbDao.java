package com.vivekvishwanath.bitters.daos;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.vivekvishwanath.bitters.models.Cocktail;
import com.vivekvishwanath.bitters.models.Ingredients;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CocktailDbDao {

    private Retrofit retrofit;
    private CocktailDbInterface cocktailDbInterface;
    private Gson gson;

    public CocktailDbDao() {
        retrofit = new Retrofit.Builder()
                .baseUrl("https://www.thecocktaildb.com/api/json/v2/8673533/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        cocktailDbInterface = retrofit.create(CocktailDbInterface.class);
        gson = new Gson();
    }

    public ArrayList<Cocktail> getPopularCocktails() {
        Call<JsonElement> call = cocktailDbInterface.getPopularCocktails();
        ArrayList<Cocktail> cocktails = new ArrayList<>();
        try {
            JsonElement jsonElement = call.execute().body();
            JsonArray jsonArray = jsonElement.getAsJsonObject()
                    .getAsJsonArray("drinks");
            for (int i = 0; i < jsonArray.size(); i++) {
                Cocktail cocktail = gson.fromJson(jsonArray.get(i), Cocktail.class);
                Ingredients ingredients = gson.fromJson(jsonArray.get(i), Ingredients.class);
                cocktail.setIngredients(ingredients);
                cocktails.add(cocktail);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cocktails;
    }

    public Cocktail getRandomCocktail() {
        Call<JsonElement> call = cocktailDbInterface.getRandomCocktail();
        Cocktail cocktail = new Cocktail();
        try {
            JsonElement jsonElement = call.execute().body();
            cocktail = gson.fromJson(jsonElement
                    .getAsJsonObject().getAsJsonArray("drinks")
                    .get(0), Cocktail.class);
            Ingredients ingredients = gson.fromJson(jsonElement
                    .getAsJsonObject().getAsJsonArray("drinks")
                    .get(0), Ingredients.class);
            cocktail.setIngredients(ingredients);
            return cocktail;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cocktail;
    }
}
