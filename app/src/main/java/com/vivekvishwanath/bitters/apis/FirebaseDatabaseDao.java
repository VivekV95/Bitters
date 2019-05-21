package com.vivekvishwanath.bitters.apis;

import android.support.annotation.NonNull;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FirebaseDatabaseDao {

    private static FirebaseUser firebaseUser;
    private static DatabaseReference mDatabase;

    public static void initializeInstance(FirebaseUser user) {
        firebaseUser = user;
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    public interface FavoriteIdsCallback {
        void onIdsObtained(ArrayList<Integer> ids);
    }

    public static void getFavoriteCocktailIds(final FavoriteIdsCallback callback) {
        mDatabase.child("users")
                .child(firebaseUser.getUid()).child("favoriteCocktails")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        GenericTypeIndicator<ArrayList<Integer>> ids = new GenericTypeIndicator<ArrayList<Integer>>() {};
                        ArrayList<Integer> cocktailIds = dataSnapshot.getValue(ids);
                        callback.onIdsObtained(cocktailIds);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        ArrayList<Integer> cocktailIds = new ArrayList<>();
                        callback.onIdsObtained(cocktailIds);
                    }
                });
    }

}
