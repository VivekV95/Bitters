package com.vivekvishwanath.bitters;

import com.vivekvishwanath.bitters.models.Cocktail;
import com.vivekvishwanath.bitters.models.Ingredient;
import com.vivekvishwanath.bitters.models.Ingredients;

import static org.junit.Assert.*;

import org.junit.Test;

public class CocktailTest {

    @Test
    public void createCocktail_UsingEmptyConstructor_HasNullDrinkId() {
        Cocktail cocktail = new Cocktail();
        String id = null;
        assertEquals(cocktail.getDrinkId(), id);
    }

    @Test
    public void createCocktail_UsingCocktailIdConstructor_HasDrinkId() {
        Cocktail cocktail = new Cocktail("123");
        String id = "123";
        assertEquals(cocktail.getDrinkId(), id);
    }

    @Test
    public void createCocktail_UsingFullConstructor_ReturnsCorrectFieldValues() {
        String name = "Margarita";
        String tags = "IBA,ContemporaryClassic";
        String alcoholic = "Alcoholic";
        String category = "Contemporary Classics";
        String glass = "Margarita Glass";
        String instructions = "Mix everything together";
        String url = "url";
        Ingredients ingredients = new Ingredients();
        Cocktail cocktail = new Cocktail(name, tags, alcoholic
                , category, glass, instructions, url, ingredients);

        assertEquals(cocktail.getDrinkName(), name);
        assertEquals(cocktail.getTags(), tags);
        assertEquals(cocktail.getIsAlcoholic(), alcoholic);
        assertEquals(cocktail.getCategory(), category);
        assertEquals(cocktail.getGlass(), glass);
        assertEquals(cocktail.getInstructions(), instructions);
        assertEquals(cocktail.getPhotoUrl(), url);
    }

    @Test
    public void ingredientsIdCreated_WhenCocktailCreated() {
        String id = "12345";
        Cocktail cocktail = new Cocktail(id);
        assertEquals(cocktail.getIngredients().getIngredientsId(), Integer.parseInt(id));
    }

}
