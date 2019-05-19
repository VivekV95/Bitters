package com.vivekvishwanath.bitters.models;

import java.util.ArrayList;
import java.util.Set;

public class User {
    private String username;
    private String userEmail;
    private ArrayList<String> cocktailIds;

    public User(String username, String userEmail) {
        this.username = username;
        this.userEmail = userEmail;
        cocktailIds = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public ArrayList<String> getCocktailIds() {
        return cocktailIds;
    }

    public void setCocktailIds(ArrayList<String> cocktailIds) {
        this.cocktailIds = cocktailIds;
    }
}
