package com.vivekvishwanath.bitters.apis;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonPrimitive;
import com.vivekvishwanath.bitters.Utils.CocktailUtils;
import com.vivekvishwanath.bitters.models.Cocktail;
import com.vivekvishwanath.bitters.models.Ingredient;
import com.vivekvishwanath.bitters.models.Ingredients;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CocktailDbDao {

    private static Retrofit retrofit;
    private static CocktailDbInterface cocktailDbInterface;
    private static Gson gson;

    private static final String COCKTAIL_DB_BASE_URL =
            "https://www.thecocktaildb.com/api/json/v2/8673533/";
    private static final String INGREDIENT_IMAGE_BASE_URL =
            "https://www.thecocktaildb.com/images/ingredients/";
    private static final String INGREDIENT_IMAGE_MEDIUM_ENDING = "-Medium.png";
    private static final String INGREDIENT_IMAGE_SMALL_ENDING = "-Small.png";
    private static final String DRINKS_MEMBER_NAME = "drinks";

    public static void initializeInstance() {
        retrofit = new Retrofit.Builder()
                .baseUrl(COCKTAIL_DB_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        cocktailDbInterface = retrofit.create(CocktailDbInterface.class);
        gson = new Gson();
    }

    public static ArrayList<Cocktail> getPopularCocktails() {
        if (retrofit != null && gson != null && cocktailDbInterface != null) {
            Call<JsonElement> call = cocktailDbInterface.getPopularCocktails();
            try {
                JsonElement jsonElement = call.execute().body();
                if (jsonElement != null) {
                    return getAllCocktailsFromJson(jsonElement);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static Cocktail getRandomCocktail() {
        if (retrofit != null && gson != null && cocktailDbInterface != null) {
            Call<JsonElement> call = cocktailDbInterface.getRandomCocktail();
            try {
                JsonElement jsonElement = call.execute().body();
                if (jsonElement != null) {
                    return getSingleCocktailFromJson(jsonElement);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static ArrayList<Cocktail> getCocktailsByName(String name) {
        if (retrofit != null && gson != null && cocktailDbInterface != null) {
            Call<JsonElement> call = cocktailDbInterface.getCocktailsByName(name);
            try {
                JsonElement jsonElement = call.execute().body();
                if (jsonElement != null) {
                    return getAllCocktailsFromJson(jsonElement);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static ArrayList<String> getCocktailIdsByIngredient(String ingredient) {
        ArrayList<String> cocktailIds = new ArrayList<>();
        if (retrofit != null && gson != null && cocktailDbInterface != null) {
            Call<JsonElement> call = cocktailDbInterface.getCocktailsByIngredient(ingredient);
            try {
                JsonElement jsonElement = call.execute().body();
                if (!(jsonElement.getAsJsonObject().get("drinks") instanceof JsonNull)
                        && !(jsonElement.getAsJsonObject().get("drinks") instanceof JsonPrimitive)) {
                    JsonArray jsonArray = jsonElement.getAsJsonObject()
                            .getAsJsonArray(DRINKS_MEMBER_NAME);
                    for (int i = 0; i < jsonArray.size(); i++) {
                        String id = jsonArray.get(i).getAsJsonObject().get("idDrink").getAsString();
                        cocktailIds.add(id);
                    }
                    return cocktailIds;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return cocktailIds;
    }

    public static ArrayList<Cocktail> getCocktailsbyGlass(String glass) {
        if (retrofit != null && gson != null && cocktailDbInterface != null) {
            Call<JsonElement> call = cocktailDbInterface.getCocktailsByGlass(glass);
            try {
                JsonElement jsonElement = call.execute().body();
                if (jsonElement != null) {
                    return getAllCocktailsFromJson(jsonElement);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static ArrayList<String> getCocktailsbyNoAlcohol() {
        ArrayList<String> cocktailIds = new ArrayList<>();
        if (retrofit != null && gson != null && cocktailDbInterface != null) {
            Call<JsonElement> call = cocktailDbInterface.getCocktailsByAlcoholic("Non_Alcoholic");
            try {
                JsonElement jsonElement = call.execute().body();
                if (!(jsonElement.getAsJsonObject().get("drinks") instanceof JsonNull)
                        && !(jsonElement.getAsJsonObject().get("drinks") instanceof JsonPrimitive)) {
                    JsonArray jsonArray = jsonElement.getAsJsonObject()
                            .getAsJsonArray(DRINKS_MEMBER_NAME);
                    for (int i = 0; i < jsonArray.size(); i++) {
                        String id = jsonArray.get(i).getAsJsonObject().get("idDrink").getAsString();
                        cocktailIds.add(id);
                    }
                    return cocktailIds;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return cocktailIds;
    }

    public static Cocktail getCocktailById(String id) {
        if (retrofit != null && gson != null && cocktailDbInterface != null) {
            Call<JsonElement> call = cocktailDbInterface.getCocktailById(id);
            try {
                JsonElement jsonElement = call.execute().body();
                if (jsonElement != null) {
                    return getSingleCocktailFromJson(jsonElement);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static ArrayList<Ingredient> getAllIngredients() {
        ArrayList<Ingredient> ingredients = new ArrayList<>();
        if (retrofit != null && gson != null && cocktailDbInterface != null) {
            Call<JsonElement> call = cocktailDbInterface.getAllIngredients();
            try {
                JsonElement jsonElement = call.execute().body();
                JsonArray jsonArray = jsonElement.getAsJsonObject()
                        .getAsJsonArray(DRINKS_MEMBER_NAME);
                for (int i = 0; i < jsonArray.size(); i++) {
                    Ingredient ingredient = new Ingredient();
                    ingredient.setName(jsonArray.get(i).getAsJsonObject().get("strIngredient1").getAsString());
                    ingredient.setPhotoUrl(getIngredientSmallUrl(ingredient.getName()));
                    ingredients.add(ingredient);
                }
                return CocktailUtils.sortIngredients(ingredients);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return ingredients;
    }


    private static Cocktail getSingleCocktailFromJson(JsonElement jsonElement) {
        Cocktail cocktail = gson.fromJson(jsonElement
                .getAsJsonObject().getAsJsonArray(DRINKS_MEMBER_NAME)
                .get(0), Cocktail.class);
        Ingredients ingredients = gson.fromJson(jsonElement
                .getAsJsonObject().getAsJsonArray(DRINKS_MEMBER_NAME)
                .get(0), Ingredients.class);
        cocktail.setIngredients(ingredients);
        return cocktail;
    }

    private static ArrayList<Cocktail> getAllCocktailsFromJson(JsonElement jsonElement) {
        ArrayList<Cocktail> cocktails = new ArrayList<>();
        if (!(jsonElement.getAsJsonObject().get("drinks") instanceof JsonNull)
                && !(jsonElement.getAsJsonObject().get("drinks") instanceof JsonPrimitive)) {
            JsonArray jsonArray = jsonElement.getAsJsonObject()
                    .getAsJsonArray(DRINKS_MEMBER_NAME);
            for (int i = 0; i < jsonArray.size(); i++) {
                Cocktail cocktail = gson.fromJson(jsonArray.get(i), Cocktail.class);
                Ingredients ingredients = gson.fromJson(jsonArray.get(i), Ingredients.class);
                cocktail.setIngredients(ingredients);
                cocktails.add(cocktail);
            }
        }
        return cocktails;
    }

    public static String getIngredientMediumUrl(String ingredientName) {
        String url = INGREDIENT_IMAGE_BASE_URL + ingredientName + INGREDIENT_IMAGE_MEDIUM_ENDING;
        return url;
    }

    public static String getIngredientSmallUrl(String ingredientName) {
        String url = INGREDIENT_IMAGE_BASE_URL + ingredientName + INGREDIENT_IMAGE_SMALL_ENDING;
        return url;
    }



}
