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

public class FirebaseAuthDao {

    private FirebaseAuth mAuth;
    private Context context;
    private DatabaseReference mDatabase;
    private boolean accounCreated;

    public FirebaseAuthDao(Context context) {
        this.context = context;
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        accounCreated = false;
    }

    public boolean registerAccount(String email, String password, final String name) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUserInfo(user, name);
                            User newUser = new User(user.getDisplayName(), user.getEmail());
                            mDatabase.child("users").child(user.getUid()).setValue(newUser);
                            accounCreated = true;
                        } else {
                            accounCreated = false;
                        }
                    }
                });

        return accounCreated;
    }

    private void updateUserInfo(FirebaseUser user, String name) {
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
}
