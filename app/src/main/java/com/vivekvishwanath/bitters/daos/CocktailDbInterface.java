package com.vivekvishwanath.bitters.daos;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.vivekvishwanath.bitters.models.Cocktail;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CocktailDbInterface {

    @GET("search.php")
    Call <JsonElement> getCocktailByName(@Query("s")String name);

    @GET("popular.php")
    Call<JsonElement> getPopularCocktails();

    @GET("random.php")
    Call<JsonElement> getRandomCocktail();




}
