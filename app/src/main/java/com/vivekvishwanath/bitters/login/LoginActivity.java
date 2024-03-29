package com.vivekvishwanath.bitters.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.text.TextUtils;
import android.view.View;

import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.vivekvishwanath.bitters.R;
import com.vivekvishwanath.bitters.apis.FirebaseAuthDao;
import com.vivekvishwanath.bitters.views.MainActivity;

public class LoginActivity extends AppCompatActivity
        implements RegisterFragment.OnFragmentInteractionListener {

    FirebaseAuth mAuth;
    FirebaseUser firebaseUser;
    private Context context;
    public static final String REGISTER_FRAGMENT_TAG = "register_fragment";
    private static final String PREFS_NAME = "Bitters_prefs";
    private static final String PREFS_EMAIL_KEY = "Prefs_email";
    private static final String PREFS_PASSWORD_KEY = "Prefs_password";
    private static SharedPreferences preferences;
    private static final int LOGIN_REQUEST_CODE = 12;

    private TextView textViewRegister;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button buttonSignIn;
    private CheckBox checkBoxRemember;
    private ProgressBar loginProgressBar;

    private boolean startMain = true;

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
        transaction.replace(R.id.register_fragment_container
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
        if (checkFields()) {
            loginProgressBar.setVisibility(View.VISIBLE);
            handleLoginInfo();
                FirebaseAuthDao.signIn(editTextEmail.getText().toString()
                        , editTextPassword.getText().toString(), new FirebaseAuthDao.SignInCallback() {
                            @Override
                            public void onSignInResult(boolean result, String message) {
                                if (result) {
                                    Intent intent = new Intent(context, MainActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivityForResult(intent, LOGIN_REQUEST_CODE);
                                    finish();
                                    loginProgressBar.setVisibility(View.GONE);
                                } else {
                                    loginProgressBar.setVisibility(View.GONE);
                                    Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                                }
                            }
                        });
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.app_bar_title);
        context = this;
        preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        textViewRegister = findViewById(R.id.text_view_register);
        textViewRegister.setOnClickListener(textViewRegisterListener);

        editTextEmail = findViewById(R.id.edit_text_email);
        editTextPassword = findViewById(R.id.edit_text_password);

        loginProgressBar = findViewById(R.id.login_progress_bar);

        checkBoxRemember = findViewById(R.id.check_box_remember);

        buttonSignIn = findViewById(R.id.button_sign_in);
        buttonSignIn.setOnClickListener(buttonSignInListener);
        mAuth = FirebaseAuth.getInstance();
        FirebaseAuthDao.initializeInstance(context);

        if (!getSharedPreferences("bitters", Context.MODE_PRIVATE)
                .getBoolean("night_mode", false)) {

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseUser = mAuth.getCurrentUser();
        if (firebaseUser != null) {
            if (startMain) {
                Intent intent = new Intent(context, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivityForResult(intent, LOGIN_REQUEST_CODE);
            }
        }
        /* String email = preferences.getString(PREFS_EMAIL_KEY, null);
        String pass = preferences.getString(PREFS_PASSWORD_KEY, null);
        if (email != null && pass != null) {
            FirebaseAuthDao.signIn(email, pass, new FirebaseAuthDao.SignInCallback() {
                        @Override
                        public void onSignInResult(boolean result) {
                            if (result) {
                                Intent intent = new Intent(context, MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            } else {
                                Toast.makeText(context, "Sign in unsuccessful", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        } */
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

    private void handleLoginInfo() {
        SharedPreferences.Editor editor = preferences.edit();
        if (checkBoxRemember.isChecked()) {
            editor.putString(PREFS_EMAIL_KEY, editTextEmail.getText().toString());
            editor.putString(PREFS_PASSWORD_KEY, editTextPassword.getText().toString());
            editor.apply();
        } else {
            editor.clear();
            editor.apply();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LOGIN_REQUEST_CODE && resultCode == RESULT_OK) {
            startMain = false;
            onBackPressed();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
