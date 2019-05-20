package com.vivekvishwanath.bitters.login;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;

import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.vivekvishwanath.bitters.R;
import com.vivekvishwanath.bitters.apis.CocktailDbDao;
import com.vivekvishwanath.bitters.apis.FirebaseAuthDao;
import com.vivekvishwanath.bitters.apis.GoogleAuthDao;
import com.vivekvishwanath.bitters.models.Cocktail;
import com.vivekvishwanath.bitters.models.User;
import com.vivekvishwanath.bitters.views.MainActivity;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity
        implements RegisterFragment.OnFragmentInteractionListener {

    FirebaseAuth mAuth;
    FirebaseUser firebaseUser;
    User user;
    private Context context;
    private GoogleAuthDao googleAuthDao;
    public static final String REGISTER_FRAGMENT_TAG = "register_fragment";

    private SignInButton buttonGoogle;
    private TextView textViewRegister;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button buttonSignIn;

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
                , fragment , REGISTER_FRAGMENT_TAG);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private View.OnClickListener buttonSignInListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            buttonSignInClicked();
        }
    };

    private void buttonSignInClicked() {
        FirebaseAuthDao.signIn(editTextEmail.getText().toString()
                , editTextPassword.getText().toString());
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (FirebaseAuthDao.getAccountSignedIn()) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(context, MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            }
                        });
                        return;
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(context, "Sign in unsuccessful", Toast.LENGTH_LONG).show();
                            }
                        });
                        return;
                    }
                }
            }
        }).start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        context = this;


        textViewRegister = findViewById(R.id.text_view_register);
        textViewRegister.setOnClickListener(textViewRegisterListener);

        buttonGoogle = findViewById(R.id.button_google);
        buttonGoogle.setOnClickListener(buttonGoogleListener);

        editTextEmail = findViewById(R.id.edit_text_email);
        editTextPassword = findViewById(R.id.edit_text_password);

        buttonSignIn = findViewById(R.id.button_sign_in);
        buttonSignIn.setOnClickListener(buttonSignInListener);
        mAuth = FirebaseAuth.getInstance();
        googleAuthDao = new GoogleAuthDao(context);
        FirebaseAuthDao.initializeInstance(context);
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseUser = mAuth.getCurrentUser();
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

    private boolean checkFields() {
        if (TextUtils.isEmpty(editTextEmail.getText().toString())
                || TextUtils.isEmpty(editTextPassword.getText().toString())) {
            return false;
        }
        return true;
    }
}
