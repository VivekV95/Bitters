package com.vivekvishwanath.bitters.apis;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;


public class GoogleAuthDao {

    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;
    public static final int RC_SIGN_IN = 2;
    private Context context;

    public GoogleAuthDao(Context context) {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(context, gso);
        mAuth = FirebaseAuth.getInstance();
        this.context = context;
    }

    public void signIn() {
        Toast.makeText(context, "In signIN method", Toast.LENGTH_SHORT);
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        ((Activity)context).startActivityForResult(signInIntent, RC_SIGN_IN);
    }


    public void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        Toast.makeText(context, "In handleSignInResult method", Toast.LENGTH_SHORT).show();
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            firebaseAuthWithGoogle(account);
            // successful sign in, start maim activity
        } catch (ApiException e) {
            Log.i("error", e.getMessage());
            // handle unsuccessful sign in
        }
    }

    public void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        Toast.makeText(context, "In firebaseAuthWithGoogle method", Toast.LENGTH_SHORT).show();
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            int i = 0;
                        } else {
                            Toast.makeText(context, "Login Failed", Toast.LENGTH_SHORT);
                        }
                    }
                });
    }



}
