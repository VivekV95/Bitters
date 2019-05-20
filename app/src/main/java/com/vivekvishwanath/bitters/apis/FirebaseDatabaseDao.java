package com.vivekvishwanath.bitters.apis;

import com.google.firebase.auth.FirebaseUser;

public class FirebaseDatabaseDao {

    private static FirebaseUser firebaseUser;

    public static void initializeInstance(FirebaseUser user) {
        firebaseUser = user;
    }

}
