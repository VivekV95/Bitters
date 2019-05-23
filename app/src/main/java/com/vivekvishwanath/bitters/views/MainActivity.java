package com.vivekvishwanath.bitters.views;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;

import android.support.v4.app.FragmentManager;

import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseUser;
import com.vivekvishwanath.bitters.R;
import com.vivekvishwanath.bitters.apis.CocktailDbDao;
import com.vivekvishwanath.bitters.apis.FirebaseAuthDao;
import com.vivekvishwanath.bitters.models.Ingredient;
import com.vivekvishwanath.bitters.mvvm.CocktailViewModel;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private FirebaseUser firebaseUser;
    public static CocktailViewModel viewModel;
    private Context context;

    private FragmentManager fragmentManager;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_popular: {
                    if (!item.isChecked()) {
                        PopularFragment fragment = new PopularFragment();
                        fragmentManager.beginTransaction()
                                .replace(R.id.choice_fragment_container, fragment)
                                .commit();
                    }
                    item.setChecked(!item.isChecked());
                }
                case R.id.navigation_search: {
                    if (!item.isChecked()) {
                        FilterFragment fragment = new FilterFragment();
                        fragmentManager.beginTransaction()
                                .replace(R.id.choice_fragment_container, fragment)
                                .commit();
                    }
                    item.setChecked(!item.isChecked());
                }
                case R.id.navigation_create: {
                    if (!item.isChecked()) {
                        CustomCocktailFragment fragment = new CustomCocktailFragment();
                        fragmentManager.beginTransaction()
                                .replace(R.id.choice_fragment_container, fragment)
                                .addToBackStack(null)
                                .commit();
                    }
                    item.setChecked(!item.isChecked());
                }
                case R.id.navigation_maps: {
                    if (!item.isChecked()) {
                        ViewCustomFragment fragment = new ViewCustomFragment();
                        fragmentManager.beginTransaction()
                                .replace(R.id.choice_fragment_container, fragment)
                                .commit();
                    }
                    item.setChecked(!item.isChecked());
                }
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navView.setItemIconTintList(null);
        firebaseUser = FirebaseAuthDao.getCurrentUser();
        context = this;

        viewModel = ViewModelProviders.of(this).get(CocktailViewModel.class);
        viewModel.loadData(this, firebaseUser);

        fragmentManager = getSupportFragmentManager();
        new Thread(new Runnable() {
            @Override
            public void run() {
                ArrayList<Ingredient> ingredients = CocktailDbDao.getAllIngredients();
            }
        }).start();

        /* Cocktail cocktail = new Cocktail("12345");
        cocktail.setDrinkName("Best Cocktail Ever");
        cocktail.getIngredients().setStrIngredient1("Alcohol");
        cocktail.getIngredients().setStrMeasure1("A lot");
        cocktail.getIngredients().setStrIngredient2("Milk");
        cocktail.getIngredients().setStrMeasure2("Not too much");
        cocktail.setInstructions("Whatever you want");

        viewModel.addCustomCocktail(cocktail); */
    }

    @Override
    protected void onStart() {
        super.onStart();
        PopularFragment fragment = new PopularFragment();
        fragmentManager.beginTransaction()
                .replace(R.id.choice_fragment_container, fragment)
                .commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK && requestCode == CustomCocktailFragment.IMAGE_REQUEST_CODE) {
            Uri imageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), imageUri);
                viewModel.setCustomCocktailImage(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
