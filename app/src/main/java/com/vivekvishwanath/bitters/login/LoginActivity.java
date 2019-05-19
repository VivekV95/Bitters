package com.vivekvishwanath.bitters.login;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.vivekvishwanath.bitters.R;
import com.vivekvishwanath.bitters.daos.GoogleAuthDao;

public class LoginActivity extends AppCompatActivity
        implements RegisterFragment.OnFragmentInteractionListener {

    SignInButton buttonGoogle;
    FirebaseAuth mAuth;
    private Context context;
    private GoogleAuthDao googleAuthDao;
    private TextView textViewRegister;
    public static final String REGISTER_FRAGMENT_TAG = "register_fragment";

    private View.OnClickListener buttonGoogleListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            buttonGoogleClicked();
        }
    };

    private void buttonGoogleClicked() {
        googleAuthDao.signIn();
    }

    private View.OnClickListener textViewRegisterListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            textViewRegisterClicked();
        }
    };

    private void textViewRegisterClicked() {
        RegisterFragment fragment = new RegisterFragment();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.register_fragment_container
                , fragment ,REGISTER_FRAGMENT_TAG);
        transaction.addToBackStack(null);
        transaction.commit();
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;


        textViewRegister = findViewById(R.id.text_view_register);
        textViewRegister.setOnClickListener(textViewRegisterListener);
        buttonGoogle = findViewById(R.id.button_google);
        buttonGoogle.setOnClickListener(buttonGoogleListener);

        mAuth = FirebaseAuth.getInstance();
        googleAuthDao = new GoogleAuthDao(context);

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
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

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
