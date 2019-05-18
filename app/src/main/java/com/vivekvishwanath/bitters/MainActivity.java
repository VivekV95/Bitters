package com.vivekvishwanath.bitters;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.vivekvishwanath.bitters.daos.CocktailDbDao;
import com.vivekvishwanath.bitters.models.Cocktail;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final CocktailDbDao dao = new CocktailDbDao();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Cocktail cocktail = dao.getRandomCocktail();
                ArrayList<Cocktail> cocktails = dao.getPopularCocktails();
                int i = 0;
            }
        }).start();
    }

}
