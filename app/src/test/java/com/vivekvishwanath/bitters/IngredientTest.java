package com.vivekvishwanath.bitters;

import com.vivekvishwanath.bitters.models.Cocktail;
import com.vivekvishwanath.bitters.models.Ingredients;

import org.junit.Test;

import static org.junit.Assert.*;

public class IngredientTest {

    @Test
    public void createIngredientsObject_WithEmptyConstructor() {
        Ingredients ingredients = new Ingredients();
        assertEquals(ingredients.getIngredientsId(), 0);
    }

    @Test
    public void ingredientsIdCreated_WhenCocktailCreated() {
        String id = "123";
        Cocktail cocktail = new Cocktail(id);
        Ingredients ingredients = cocktail.getIngredients();
        assertEquals(ingredients.getIngredientsId(), Integer.parseInt(cocktail.getDrinkId()));
    }
}
