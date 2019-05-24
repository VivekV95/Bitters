package com.vivekvishwanath.bitters.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Cocktail implements Serializable {

    public static final String SERIALIZABLE_OCCKTAIL_KEY = "cocktail";

    @SerializedName("idDrink")
    private String drinkId;

    @SerializedName("strDrink")
    private String drinkName;

    @SerializedName("strTags")
    private String tags;

    @SerializedName("strAlcoholic")
    private String isAlcoholic;

    @SerializedName("strCategory")
    private String category;

    @SerializedName("strGlass")
    private String glass;

    @SerializedName("strInstructions")
    private String instructions;

    @SerializedName("strDrinkThumb")
    private String photoUrl;

    private Ingredients ingredients;

    public Cocktail(String drinkName, String tags,
                    String isAlcoholic, String category,
                    String glass, String instructions,
                    String photoUrl, Ingredients ingredients) {
        this.drinkName = drinkName;
        this.tags = tags;
        this.isAlcoholic = isAlcoholic;
        this.category = category;
        this.glass = glass;
        this.instructions = instructions;
        this.photoUrl = photoUrl;
        this.ingredients = ingredients;
    }

    public Cocktail() {
        ingredients = new Ingredients();
    }

    public Cocktail(String id) {
        this.drinkId = id;
        ingredients = new Ingredients();
        ingredients.setIngredientsId(Integer.parseInt(id));
    }

    public String getDrinkId() {
        return drinkId;
    }

    public String getDrinkName() {
        return drinkName;
    }

    public String getTags() {
        return tags;
    }

    public String getIsAlcoholic() {
        return isAlcoholic;
    }

    public String getCategory() {
        return category;
    }

    public String getGlass() {
        return glass;
    }

    public String getInstructions() {
        return instructions;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setDrinkId(String drinkId) {
        this.drinkId = drinkId;
    }

    public void setDrinkName(String drinkName) {
        this.drinkName = drinkName;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public void setIsAlcoholic(String isAlcoholic) {
        this.isAlcoholic = isAlcoholic;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setGlass(String glass) {
        this.glass = glass;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public Ingredients getIngredients() {
        return ingredients;
    }

    public void setIngredients(Ingredients ingredients) {
        this.ingredients = ingredients;
    }
}
