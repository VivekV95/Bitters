package com.vivekvishwanath.bitters.daos;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.vivekvishwanath.bitters.models.User;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class FirebaseAuthDao {

    private FirebaseAuth mAuth;
    private Context context;
    private DatabaseReference mDatabase;
    private AtomicBoolean accounCreated;

    public FirebaseAuthDao(Context context) {
        this.context = context;
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        accounCreated = new AtomicBoolean(false);
    }

    public void registerAccount(String email, String password, final String name) {
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
                                accounCreated.set(true);
                            }
                        } else {
                            accounCreated.set(false);
                        }
                    }
                });
    }

    private void updateUserRegistrationInfo(FirebaseUser user, String name) {
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(name)
                .build();

        user.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(context, "User name added", Toast.LENGTH_SHORT);
                        }
                    }
                });
    }

    public boolean getAccounCreated() {
        return accounCreated.get();
    }

}
