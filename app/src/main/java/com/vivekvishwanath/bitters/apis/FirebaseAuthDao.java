package com.vivekvishwanath.bitters.apis;

import android.content.Context;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.vivekvishwanath.bitters.models.User;

public class FirebaseAuthDao {

    private static FirebaseAuth mAuth;
    private static FirebaseUser firebaseUser;
    private static Context context;
    private static DatabaseReference mDatabase;



    public static void initializeInstance(Context c) {
        context = c;
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        firebaseUser = mAuth.getCurrentUser();
    }

    public interface RegistrationCallback {
        void onRegistrationResult(boolean result);
    }

    public static void registerAccount(String email, String password, final String name
            , final RegistrationCallback callback) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            boolean isNew = task.getResult().getAdditionalUserInfo().isNewUser();
                            if (isNew) {
                                FirebaseUser user = mAuth.getCurrentUser();
                                updateUserRegistrationInfo(user, name);
                                User newUser = new User(name, user.getEmail());
                                mDatabase.child("users").child(user.getUid()).setValue(newUser);
                                callback.onRegistrationResult(true);
                            }
                        } else {
                            callback.onRegistrationResult(false);
                        }
                    }
                });
    }

    private static void updateUserRegistrationInfo(FirebaseUser user, String name) {
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(name)
                .build();

        user.updateProfile(profileUpdates);
    }

    public interface SignInCallback {
        void onSignInResult(boolean result);
    }

    public static void signIn(String email, String password, final SignInCallback callback) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            firebaseUser = mAuth.getCurrentUser();
                            callback.onSignInResult(true);
                        } else {
                            callback.onSignInResult(false);
                        }
                    }
                });
    }

    public static FirebaseUser getCurrentUser() {
        return firebaseUser;
    }

}
