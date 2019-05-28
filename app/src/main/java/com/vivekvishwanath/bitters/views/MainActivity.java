package com.vivekvishwanath.bitters.views;

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

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.vivekvishwanath.bitters.R;
import com.vivekvishwanath.bitters.apis.CocktailDbDao;
import com.vivekvishwanath.bitters.apis.FirebaseAuthDao;
import com.vivekvishwanath.bitters.models.Ingredient;
import com.vivekvishwanath.bitters.mvvm.CocktailViewModel;

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
                        viewModel.setSelectedFragment(0);
                        PopularFragment fragment = new PopularFragment();
                        fragmentManager.beginTransaction()
                                .replace(R.id.choice_fragment_container, fragment)
                                .commit();
                    }
                    item.setChecked(!item.isChecked());
                }
                case R.id.navigation_search: {
                    if (!item.isChecked()) {
                        viewModel.setSelectedFragment(1);
                        FilterFragment fragment = new FilterFragment();
                        fragmentManager.beginTransaction()
                                .replace(R.id.choice_fragment_container, fragment)
                                .commit();
                    }
                    item.setChecked(!item.isChecked());
                }
                case R.id.navigation_create: {
                    if (!item.isChecked()) {
                        viewModel.setSelectedFragment(2);
                        CustomCocktailFragment fragment = new CustomCocktailFragment();
                        fragmentManager.beginTransaction()
                                .replace(R.id.choice_fragment_container, fragment)
                                .commit();
                    }
                    item.setChecked(!item.isChecked());
                }
                case R.id.navigation_maps: {
                    if (!item.isChecked()) {
                        viewModel.setSelectedFragment(3);
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

    BottomNavigationView navView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.app_bar_title);

        navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navView.setItemIconTintList(null);
        firebaseUser = FirebaseAuthDao.getCurrentUser();
        context = this;

        viewModel = ViewModelProviders.of(this).get(CocktailViewModel.class);
        viewModel.loadData(this, firebaseUser);
        viewModel.setSelectedFragment(0);

        fragmentManager = getSupportFragmentManager();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (viewModel.getCurrentFragment() == 0) {
            PopularFragment f = new PopularFragment();
            fragmentManager.beginTransaction()
                    .replace(R.id.choice_fragment_container, f)
                    .commit();
        } else if (viewModel.getCurrentFragment() == 1) {
            FilterFragment fragment = new FilterFragment();
            fragmentManager.beginTransaction()
                    .replace(R.id.choice_fragment_container, fragment)
                    .commit();
        } else if (viewModel.getCurrentFragment() == 2) {
            CustomCocktailFragment fragment = new CustomCocktailFragment();
            fragmentManager.beginTransaction()
                    .replace(R.id.choice_fragment_container, fragment)
                    .commit();
        } else if (viewModel.getCurrentFragment() == 3) {
            CustomCocktailFragment fragment = new CustomCocktailFragment();
            fragmentManager.beginTransaction()
                    .replace(R.id.choice_fragment_container, fragment)
                    .commit();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK && requestCode == CustomCocktailFragment.IMAGE_REQUEST_CODE) {
            Uri imageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), imageUri);
                viewModel.setSelectedFragment(2);
                viewModel.setCustomCocktailImage(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_logout) {
            FirebaseAuth.getInstance().signOut();
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
