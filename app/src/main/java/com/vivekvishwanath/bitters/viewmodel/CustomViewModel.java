package com.vivekvishwanath.bitters.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.graphics.Bitmap;

public class CustomViewModel extends ViewModel {
    private MutableLiveData<Bitmap> cocktailImage = new MutableLiveData<>();


    public void setCustomCocktailImage(Bitmap bitmap) {
        cocktailImage.setValue(bitmap);
    }

    public MutableLiveData<Bitmap> getCocktailImage() {
        return cocktailImage;
    }
}
