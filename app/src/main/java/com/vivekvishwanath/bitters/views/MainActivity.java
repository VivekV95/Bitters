package com.vivekvishwanath.bitters.views;

import androidx.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;

import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.vivekvishwanath.bitters.R;
import com.vivekvishwanath.bitters.apis.FirebaseAuthDao;
import com.vivekvishwanath.bitters.mvvm.CocktailViewModel;
import com.vivekvishwanath.bitters.viewmodel.CustomViewModel;

import java.io.IOException;

public class MainActivity extends AppCompatActivity  {
    private FirebaseUser firebaseUser;
    public static CocktailViewModel viewModel;
    private Context context;
    private String imageUriString;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_popular: {
                    if (!item.isChecked()) {
                        getSupportFragmentManager().popBackStack();
                        viewModel.setSelectedFragment(0);
                        PopularFragment fragment = new PopularFragment();
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.choice_fragment_container, fragment)
                                .commit();
                    }
                    item.setChecked(!item.isChecked());
                }
                case R.id.navigation_search: {
                    if (!item.isChecked()) {
                        getSupportFragmentManager().popBackStack();
                        viewModel.setSelectedFragment(1);
                        FilterFragment fragment = new FilterFragment();
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.choice_fragment_container, fragment)
                                .commit();
                    }
                    item.setChecked(!item.isChecked());
                }
                case R.id.navigation_create: {
                    if (!item.isChecked()) {
                        getSupportFragmentManager().popBackStack();
                        viewModel.setSelectedFragment(2);
                        ViewCustomFragment fragment = new ViewCustomFragment();
                        getSupportFragmentManager().beginTransaction()
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
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (viewModel.getCurrentFragment() == 0) {
            PopularFragment f = new PopularFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.choice_fragment_container, f)
                    .commit();
        } else if (viewModel.getCurrentFragment() == 1) {
            FilterFragment fragment = new FilterFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.choice_fragment_container, fragment)
                    .commit();
        } else if (viewModel.getCurrentFragment() == 2) {
            ViewCustomFragment fragment = new ViewCustomFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.choice_fragment_container, fragment)
                    .commit();
        } else if (viewModel.getCurrentFragment() == 3) {
            CustomCocktailFragment fragment = new CustomCocktailFragment();
            Bundle bundle = new Bundle();
            bundle.putString("image", imageUriString);
            fragment.setArguments(bundle);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.choice_fragment_container, fragment)
                    .commit();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK && requestCode == CustomCocktailFragment.IMAGE_REQUEST_CODE) {
            imageUriString = data.getData().toString();
                //currentImage = MediaStore.Images.Media.getBitmap(context.getContentResolver(), imageUri);
                viewModel.setSelectedFragment(3);
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}
