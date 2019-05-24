package com.vivekvishwanath.bitters;

import com.vivekvishwanath.bitters.Utils.CocktailUtils;
import com.vivekvishwanath.bitters.models.Cocktail;
import com.vivekvishwanath.bitters.models.Ingredient;

import org.junit.Test;

import static org.junit.Assert.*;

import java.util.ArrayList;

public class CocktailUtilsTest {

    @Test
    public void convertCocktailIngredients_GivenCocktailWithNoIngredients_IsEmpty() {
        Cocktail cocktail = new Cocktail();
        ArrayList<Ingredient> ingredientList = CocktailUtils.convertCocktailIngredients(cocktail);
        assertEquals(ingredientList.size(), 0);
    }

    @Test
    public void convertCocktailIngredients_GivenCocktailWithIngredients_WithoutMeasurements() {
        Cocktail cocktail = new Cocktail();
        cocktail.getIngredients().setStrIngredient1("Rum");
        cocktail.getIngredients().setStrIngredient2("Coffee");
        ArrayList<Ingredient> ingredientList = CocktailUtils.convertCocktailIngredients(cocktail);
        assertEquals(ingredientList.get(0).getName(), cocktail.getIngredients().getStrIngredient1());
        assertEquals(ingredientList.get(1).getName(), cocktail.getIngredients().getStrIngredient2());
        assertEquals(ingredientList.get(0).getMeasurement(), null);
        assertEquals(ingredientList.get(1).getMeasurement(), null);
    }

    @Test
    public void convertCocktailIngredients_GivenCocktailWithIngredients_WithMeasurements() {
        Cocktail cocktail = new Cocktail();
        cocktail.getIngredients().setStrIngredient1("Rum");
        cocktail.getIngredients().setStrIngredient2("Coffee");
        cocktail.getIngredients().setStrMeasure1("1oz");
        cocktail.getIngredients().setStrMeasure2("1 cup");
        ArrayList<Ingredient> ingredientList = CocktailUtils.convertCocktailIngredients(cocktail);
        assertEquals(ingredientList.get(0).getName(), cocktail.getIngredients().getStrIngredient1());
        assertEquals(ingredientList.get(1).getName(), cocktail.getIngredients().getStrIngredient2());
        assertEquals(ingredientList.get(0).getMeasurement(), cocktail.getIngredients().getStrMeasure1());
        assertEquals(ingredientList.get(1).getMeasurement(), cocktail.getIngredients().getStrMeasure2());
    }
}
