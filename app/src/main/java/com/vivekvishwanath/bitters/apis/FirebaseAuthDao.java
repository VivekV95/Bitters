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

import java.util.concurrent.atomic.AtomicBoolean;

public class FirebaseAuthDao {

    private static FirebaseAuth mAuth;
    private static FirebaseUser firebaseUser;
    private static Context context;
    private static DatabaseReference mDatabase;
    private static AtomicBoolean accountCreated;
    private static AtomicBoolean accountSignedIn;

    public static void initializeInstance(Context c) {
        context = c;
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        accountCreated = new AtomicBoolean(false);
        accountSignedIn = new AtomicBoolean(false);
        firebaseUser = mAuth.getCurrentUser();
    }

    public static void registerAccount(String email, String password, final String name) {
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
                                accountCreated.set(true);
                            }
                        } else {
                            accountCreated.set(false);
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

    public static void signIn(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            firebaseUser = mAuth.getCurrentUser();
                            FirebaseDatabaseDao.initializeInstance(firebaseUser);
                            accountSignedIn.set(true);
                        } else {
                            accountSignedIn.set(false);
                        }
                    }
                });
    }

    public static boolean getAccountCreated() {
        return accountCreated.get();
    }

    public static boolean getAccountSignedIn() {
        return accountSignedIn.get();
    }

}
