package com.vivekvishwanath.bitters.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.vivekvishwanath.bitters.R;
import com.vivekvishwanath.bitters.daos.CocktailDbDao;
import com.vivekvishwanath.bitters.daos.GoogleAuthDao;
import com.vivekvishwanath.bitters.models.Cocktail;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    SignInButton buttonGoogle;
    FirebaseAuth mAuth;
    GoogleApiClient mGoogleApiClient;
    private static final int RC_SIGN_IN = 2;
    private Context context;
    private GoogleAuthDao googleAuthDao;

    private View.OnClickListener buttonGoogleListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            buttonGoogleClicked();
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

        buttonGoogle = findViewById(R.id.button_google);
        buttonGoogle.setOnClickListener(buttonGoogleListener);

        mAuth = FirebaseAuth.getInstance();
        googleAuthDao = new GoogleAuthDao(context);

    }

    @Override
    protected void onStart() {
        super.onStart();
     /*    GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(context);
        FirebaseUser currentUser = mAuth.getCurrentUser(); */
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    private void buttonGoogleClicked() {
        googleAuthDao.signIn();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GoogleAuthDao.RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                GoogleSignInAccount account = result.getSignInAccount();
            }
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            googleAuthDao.handleSignInResult(task);
        }
    }

}
