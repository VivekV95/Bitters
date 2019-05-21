package com.vivekvishwanath.bitters.views;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.vivekvishwanath.bitters.R;
import com.vivekvishwanath.bitters.apis.CocktailDbDao;
import com.vivekvishwanath.bitters.apis.FirebaseAuthDao;
import com.vivekvishwanath.bitters.apis.FirebaseDatabaseDao;
import com.vivekvishwanath.bitters.models.Cocktail;
import com.vivekvishwanath.bitters.models.User;
import com.vivekvishwanath.bitters.mvvm.CocktailRepository;
import com.vivekvishwanath.bitters.mvvm.CocktailViewModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private TextView mTextMessage;
    private FirebaseUser firebaseUser;
    public static CocktailViewModel viewModel;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_popular: {
                    item.setChecked(!item.isChecked());
                    if (item.isChecked()) {

                    }
                }
                case R.id.navigation_search: {
                    item.setChecked(!item.isChecked());
                }
                case R.id.navigation_create: {
                    item.setChecked(!item.isChecked());
                }
                case R.id.navigation_maps: {
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
        mTextMessage = findViewById(R.id.message);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navView.setItemIconTintList(null);
        firebaseUser = FirebaseAuthDao.getCurrentUser();

        viewModel = ViewModelProviders.of(this).get(CocktailViewModel.class);
        viewModel.loadData(this, firebaseUser);
    }

}
