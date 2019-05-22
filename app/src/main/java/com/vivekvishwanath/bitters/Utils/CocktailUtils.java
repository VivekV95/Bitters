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
            if (!isNullOrEmpty(ingredients.getStrIngredient1())) {
                ingredient = new Ingredient(ingredients.getStrIngredient1()
                        , ingredients.getStrMeasure1()
                        , CocktailDbDao.getIngredientMediumUrl(ingredients.getStrIngredient1()));
                ingredientList.add(ingredient);
            } else {
                break;
            }
            if (!isNullOrEmpty(ingredients.getStrIngredient2())) {
                ingredient = new Ingredient(ingredients.getStrIngredient2()
                        , ingredients.getStrMeasure2()
                        , CocktailDbDao.getIngredientMediumUrl(ingredients.getStrIngredient2()));
                ingredientList.add(ingredient);
            } else {
                break;
            }
            if (!isNullOrEmpty(ingredients.getStrIngredient3())) {
                ingredient = new Ingredient(ingredients.getStrIngredient3()
                        , ingredients.getStrMeasure3()
                        , CocktailDbDao.getIngredientMediumUrl(ingredients.getStrIngredient3()));
                ingredientList.add(ingredient);
            } else {
                break;
            }
            if (!isNullOrEmpty(ingredients.getStrIngredient4())) {
                ingredient = new Ingredient(ingredients.getStrIngredient4()
                        , ingredients.getStrMeasure4()
                        , CocktailDbDao.getIngredientMediumUrl(ingredients.getStrIngredient4()));
                ingredientList.add(ingredient);
            } else {
                break;
            }
            if (!isNullOrEmpty(ingredients.getStrIngredient5())) {
                ingredient = new Ingredient(ingredients.getStrIngredient5()
                        , ingredients.getStrMeasure5()
                        , CocktailDbDao.getIngredientMediumUrl(ingredients.getStrIngredient5()));
                ingredientList.add(ingredient);
            } else {
                break;
            }
            if (!isNullOrEmpty(ingredients.getStrIngredient6())) {
                ingredient = new Ingredient(ingredients.getStrIngredient6()
                        , ingredients.getStrMeasure6()
                        , CocktailDbDao.getIngredientMediumUrl(ingredients.getStrIngredient6()));
                ingredientList.add(ingredient);
            } else {
                break;
            }
            if (!isNullOrEmpty(ingredients.getStrIngredient7())) {
                ingredient = new Ingredient(ingredients.getStrIngredient7()
                        , ingredients.getStrMeasure7()
                        , CocktailDbDao.getIngredientMediumUrl(ingredients.getStrIngredient7()));
                ingredientList.add(ingredient);
            } else {
                break;
            }
            if (!isNullOrEmpty(ingredients.getStrIngredient8())) {
                ingredient = new Ingredient(ingredients.getStrIngredient8()
                        , ingredients.getStrMeasure8()
                        , CocktailDbDao.getIngredientMediumUrl(ingredients.getStrIngredient8()));
                ingredientList.add(ingredient);
            } else {
                break;
            }
            if (!isNullOrEmpty(ingredients.getStrIngredient9())) {
                ingredient = new Ingredient(ingredients.getStrIngredient9()
                        , ingredients.getStrMeasure9()
                        , CocktailDbDao.getIngredientMediumUrl(ingredients.getStrIngredient9()));
                ingredientList.add(ingredient);
            } else {
                break;
            }
            if (!isNullOrEmpty(ingredients.getStrIngredient10())) {
                ingredient = new Ingredient(ingredients.getStrIngredient10()
                        , ingredients.getStrMeasure10()
                        , CocktailDbDao.getIngredientMediumUrl(ingredients.getStrIngredient10()));
                ingredientList.add(ingredient);
            } else {
                break;
            }
            if (!isNullOrEmpty(ingredients.getStrIngredient11())) {
                ingredient = new Ingredient(ingredients.getStrIngredient11()
                        , ingredients.getStrMeasure11()
                        , CocktailDbDao.getIngredientMediumUrl(ingredients.getStrIngredient11()));
                ingredientList.add(ingredient);
            } else {
                break;
            }
            if (!isNullOrEmpty(ingredients.getStrIngredient12())) {
                ingredient = new Ingredient(ingredients.getStrIngredient12()
                        , ingredients.getStrMeasure12()
                        , CocktailDbDao.getIngredientMediumUrl(ingredients.getStrIngredient12()));
                ingredientList.add(ingredient);
            } else {
                break;
            }
            if (!isNullOrEmpty(ingredients.getStrIngredient13())) {
                ingredient = new Ingredient(ingredients.getStrIngredient13()
                        , ingredients.getStrMeasure13()
                        , CocktailDbDao.getIngredientMediumUrl(ingredients.getStrIngredient13()));
                ingredientList.add(ingredient);
            } else {
                break;
            }
            if (!isNullOrEmpty(ingredients.getStrIngredient14())) {
                ingredient = new Ingredient(ingredients.getStrIngredient14()
                        , ingredients.getStrMeasure14()
                        , CocktailDbDao.getIngredientMediumUrl(ingredients.getStrIngredient14()));
                ingredientList.add(ingredient);
            } else {
                break;
            }
            if (!isNullOrEmpty(ingredients.getStrIngredient15())) {
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
        ArrayList<String> instructionsList = new ArrayList<>();
        if (cocktail.getInstructions() != null) {
            String[] instructionArray = cocktail.getInstructions().split("\\.");
            instructionsList = new ArrayList<>(Arrays.asList(instructionArray));
        }
        return instructionsList;
    }

    public static boolean isNullOrEmpty(String str) {
        if(str != null && !str.isEmpty())
            return false;
        return true;
    }
}