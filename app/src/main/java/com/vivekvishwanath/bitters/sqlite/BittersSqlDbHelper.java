package com.vivekvishwanath.bitters.sqlite;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class BittersSqlDbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABSE_NAME = "BittersDatabase.db";

    public BittersSqlDbHelper(Context context) {
        super(context, DATABSE_NAME, null, DATABASE_VERSION);
    }
    public BittersSqlDbHelper(@Nullable Context context
            , @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory
            , int version) {
        this(context);
    }

    public BittersSqlDbHelper(@Nullable Context context, @Nullable String name
            , @Nullable SQLiteDatabase.CursorFactory factory, int version
            , @Nullable DatabaseErrorHandler errorHandler) {
        this(context);
    }

    public BittersSqlDbHelper(@Nullable Context context, @Nullable String name
            , int version, @NonNull SQLiteDatabase.OpenParams openParams) {
        this(context);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(BittersSqlDbContract.CocktailEntry.SQL_CREATE_TABLE);
        db.execSQL(BittersSqlDbContract.IngredientEntry.SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(BittersSqlDbContract.CocktailEntry.SQL_DELETE_TABLE);
        db.execSQL(BittersSqlDbContract.IngredientEntry.SQL_DELETE_TABLE);
    }
}
