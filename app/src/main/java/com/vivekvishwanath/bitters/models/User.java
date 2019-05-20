package com.vivekvishwanath.bitters.models;

import java.util.ArrayList;

public class User {
    private String userName;
    private String userEmail;
    private ArrayList<String> favoriteCocktails;
    private ArrayList<String> customCocktails;

    public User(String userName, String userEmail) {
        this.userName = userName;
        this.userEmail = userEmail;
        favoriteCocktails = new ArrayList<>();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public ArrayList<String> getFavoriteCocktails() {
        return favoriteCocktails;
    }

    public void setFavoriteCocktails(ArrayList<String> favoriteCocktails) {
        this.favoriteCocktails = favoriteCocktails;
    }

    public ArrayList<String> getCustomCocktails() {
        return customCocktails;
    }

    public void setCustomCocktails(ArrayList<String> customCocktails) {
        this.customCocktails = customCocktails;
    }
}
