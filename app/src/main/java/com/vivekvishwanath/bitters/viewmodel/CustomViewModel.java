package com.vivekvishwanath.bitters.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import android.graphics.Bitmap;

import com.vivekvishwanath.bitters.models.Cocktail;
import com.vivekvishwanath.bitters.models.Ingredient;
import com.vivekvishwanath.bitters.models.User;
import com.vivekvishwanath.bitters.repository.CustomRepository;

import java.util.ArrayList;

public class CustomViewModel extends ViewModel {

    private MutableLiveData<Bitmap> cocktailImage = new MutableLiveData<>();
    private MutableLiveData<ArrayList<Ingredient>> allIngredients = new MutableLiveData<>();
    private MutableLiveData<ArrayList<Ingredient>> selectedIngredients = new MutableLiveData<>();
    private MutableLiveData<ArrayList<Integer>> customIds = new MutableLiveData<>();
    private CustomRepository customRepository;

    public void setCustomCocktailImage(Bitmap bitmap) {
        cocktailImage.setValue(bitmap);
    }

    public MutableLiveData<Bitmap> getCocktailImage() {
        return cocktailImage;
    }

    public MutableLiveData<ArrayList<Ingredient>> getAllIngredients() {
        if (customRepository == null) {
            customRepository = new CustomRepository();
        }
        allIngredients = customRepository.getAllIngredients();
        return allIngredients;
    }

    public MutableLiveData<ArrayList<Ingredient>> getSelectedIngredients() {
        return selectedIngredients;
    }

    public void updateSelectedIngredientMeasurement(int position, String measurement) {
        if (position < selectedIngredients.getValue().size()) {
            selectedIngredients.getValue().get(position).setMeasurement(measurement);
        }
    }

    public MutableLiveData<ArrayList<Integer>> getCustomIds() {
        if (customRepository == null) {
            customRepository = new CustomRepository();
        }
        customIds = customRepository.getCustomIds();
        return customIds;
    }

}
