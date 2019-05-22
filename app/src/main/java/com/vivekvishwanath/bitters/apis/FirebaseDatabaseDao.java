package com.vivekvishwanath.bitters.apis;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

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
        void onIdsObtained(ArrayList<String> ids);
    }

    public static void getFavoriteCocktailIds(final FavoriteIdsCallback callback) {
        mDatabase.child("users")
                .child(firebaseUser.getUid()).child("favoriteCocktails")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        GenericTypeIndicator<ArrayList<String>> ids
                                = new GenericTypeIndicator<ArrayList<String>>() {};
                        ArrayList<String> cocktailIds = dataSnapshot.getValue(ids);
                        callback.onIdsObtained(cocktailIds);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        ArrayList<String> cocktailIds = new ArrayList<>();
                        callback.onIdsObtained(cocktailIds);
                    }
                });
    }

    public static void deleteFavoriteCocktail(String id) {
        if (firebaseUser != null) {
            mDatabase.child("users")
                    .child(firebaseUser.getUid())
                    .child("favoriteCocktails")
                    .child(id).removeValue(new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(@Nullable DatabaseError databaseError
                        , @NonNull DatabaseReference databaseReference) {
                    int i = 0;
                }
            });
        }
    }

    public static void updateFavoriteCocktailIds(ArrayList<String> ids) {
        if (firebaseUser != null) {
            mDatabase.child("users")
                    .child(firebaseUser.getUid())
                    .child("favoriteCocktails").setValue(ids,
                    new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(@Nullable DatabaseError databaseError
                        , @NonNull DatabaseReference databaseReference) {
                    int i = 0;
                }
            });
        }
    }

}
