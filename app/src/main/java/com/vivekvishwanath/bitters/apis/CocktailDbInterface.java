package com.vivekvishwanath.bitters.apis;

import com.google.gson.JsonElement;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CocktailDbInterface {

    @GET("search.php")
    Call <JsonElement> getCocktailsByName(@Query("s")String name);

    @GET("filter.php")
    Call<JsonElement> getCocktailsByIngredient(@Query("i")String ingredient);

    @GET("filter.php")
    Call<JsonElement> getCocktailsByGlass(@Query("g")String glass);

    @GET("filter.php")
    Call<JsonElement> getCocktailsByAlcoholic(@Query("a")String isAlcoholic);

    @GET("lookup.php")
    Call<JsonElement> getCocktailById(@Query("i")String id);

    @GET("popular.php")
    Call<JsonElement> getPopularCocktails();

    @GET("random.php")
    Call<JsonElement> getRandomCocktail();

}
