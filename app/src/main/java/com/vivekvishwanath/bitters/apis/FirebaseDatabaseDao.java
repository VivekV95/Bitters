package com.vivekvishwanath.bitters.apis;

import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.vivekvishwanath.bitters.models.Cocktail;

import java.util.ArrayList;

public class FirebaseDatabaseDao {

    private static FirebaseUser firebaseUser;
    private static DatabaseReference mDatabase;

    public static void initializeInstance(FirebaseUser user) {
        firebaseUser = user;
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }


}
