package com.vivekvishwanath.bitters.Utils;

import com.vivekvishwanath.bitters.apis.CocktailDbDao;
import com.vivekvishwanath.bitters.models.Cocktail;
import com.vivekvishwanath.bitters.models.Ingredient;
import com.vivekvishwanath.bitters.models.Ingredients;

import java.util.ArrayList;
import java.util.Arrays;

public class CocktailUtils {

    public static ArrayList<Ingredient> convertCocktailIngredients(Cocktail cocktail) {
        Ingredients ingredients = cocktail.getIngredients();
        Ingredient ingredient;
        ArrayList<Ingredient> ingredientList = new ArrayList<>();
        while(true) {
            if (!ingredients.getStrIngredient1().equals("")) {
                ingredient = new Ingredient(ingredients.getStrIngredient1()
                        , ingredients.getStrMeasure1()
                        , CocktailDbDao.getIngredientMediumUrl(ingredients.getStrIngredient1()));
                ingredientList.add(ingredient);
            } else {
                break;
            }
            if (!ingredients.getStrIngredient2().equals("")) {
                ingredient = new Ingredient(ingredients.getStrIngredient2()
                        , ingredients.getStrMeasure2()
                        , CocktailDbDao.getIngredientMediumUrl(ingredients.getStrIngredient2()));
                ingredientList.add(ingredient);
            } else {
                break;
            }
            if (!ingredients.getStrIngredient3().equals("")) {
                ingredient = new Ingredient(ingredients.getStrIngredient3()
                        , ingredients.getStrMeasure3()
                        , CocktailDbDao.getIngredientMediumUrl(ingredients.getStrIngredient3()));
                ingredientList.add(ingredient);
            } else {
                break;
            }
            if (!ingredients.getStrIngredient4().equals("")) {
                ingredient = new Ingredient(ingredients.getStrIngredient4()
                        , ingredients.getStrMeasure4()
                        , CocktailDbDao.getIngredientMediumUrl(ingredients.getStrIngredient4()));
                ingredientList.add(ingredient);
            } else {
                break;
            }
            if (!ingredients.getStrIngredient5().equals("")) {
                ingredient = new Ingredient(ingredients.getStrIngredient5()
                        , ingredients.getStrMeasure5()
                        , CocktailDbDao.getIngredientMediumUrl(ingredients.getStrIngredient5()));
                ingredientList.add(ingredient);
            } else {
                break;
            }
            if (!ingredients.getStrIngredient6().equals("")) {
                ingredient = new Ingredient(ingredients.getStrIngredient6()
                        , ingredients.getStrMeasure6()
                        , CocktailDbDao.getIngredientMediumUrl(ingredients.getStrIngredient6()));
                ingredientList.add(ingredient);
            } else {
                break;
            }
            if (!ingredients.getStrIngredient7().equals("")) {
                ingredient = new Ingredient(ingredients.getStrIngredient7()
                        , ingredients.getStrMeasure7()
                        , CocktailDbDao.getIngredientMediumUrl(ingredients.getStrIngredient7()));
                ingredientList.add(ingredient);
            } else {
                break;
            }
            if (!ingredients.getStrIngredient8().equals("")) {
                ingredient = new Ingredient(ingredients.getStrIngredient8()
                        , ingredients.getStrMeasure8()
                        , CocktailDbDao.getIngredientMediumUrl(ingredients.getStrIngredient8()));
                ingredientList.add(ingredient);
            } else {
                break;
            }
            if (!ingredients.getStrIngredient9().equals("")) {
                ingredient = new Ingredient(ingredients.getStrIngredient9()
                        , ingredients.getStrMeasure9()
                        , CocktailDbDao.getIngredientMediumUrl(ingredients.getStrIngredient9()));
                ingredientList.add(ingredient);
            } else {
                break;
            }
            if (!ingredients.getStrIngredient10().equals("")) {
                ingredient = new Ingredient(ingredients.getStrIngredient10()
                        , ingredients.getStrMeasure10()
                        , CocktailDbDao.getIngredientMediumUrl(ingredients.getStrIngredient10()));
                ingredientList.add(ingredient);
            } else {
                break;
            }
            if (!ingredients.getStrIngredient11().equals("")) {
                ingredient = new Ingredient(ingredients.getStrIngredient11()
                        , ingredients.getStrMeasure11()
                        , CocktailDbDao.getIngredientMediumUrl(ingredients.getStrIngredient11()));
                ingredientList.add(ingredient);
            } else {
                break;
            }
            if (!ingredients.getStrIngredient12().equals("")) {
                ingredient = new Ingredient(ingredients.getStrIngredient12()
                        , ingredients.getStrMeasure12()
                        , CocktailDbDao.getIngredientMediumUrl(ingredients.getStrIngredient12()));
                ingredientList.add(ingredient);
            } else {
                break;
            }
            if (!ingredients.getStrIngredient13().equals("")) {
                ingredient = new Ingredient(ingredients.getStrIngredient13()
                        , ingredients.getStrMeasure13()
                        , CocktailDbDao.getIngredientMediumUrl(ingredients.getStrIngredient13()));
                ingredientList.add(ingredient);
            } else {
                break;
            }
            if (!ingredients.getStrIngredient14().equals("")) {
                ingredient = new Ingredient(ingredients.getStrIngredient14()
                        , ingredients.getStrMeasure14()
                        , CocktailDbDao.getIngredientMediumUrl(ingredients.getStrIngredient14()));
                ingredientList.add(ingredient);
            } else {
                break;
            }
            if (!ingredients.getStrIngredient15().equals("")) {
                ingredient = new Ingredient(ingredients.getStrIngredient15()
                        , ingredients.getStrMeasure15()
                        , CocktailDbDao.getIngredientMediumUrl(ingredients.getStrIngredient15()));
                ingredientList.add(ingredient);
            } else {
                break;
            }
            break;
        }
        return ingredientList;
    }

    public static ArrayList<String> getInstructionList(Cocktail cocktail) {
        String[] instructionArray = cocktail.getInstructions().split(".");
        ArrayList<String> instructionList = new ArrayList<>(Arrays.asList(instructionArray));
        return instructionList; 
    }
}