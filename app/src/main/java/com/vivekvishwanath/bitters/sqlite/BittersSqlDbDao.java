package com.vivekvishwanath.bitters.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.vivekvishwanath.bitters.models.Cocktail;
import com.vivekvishwanath.bitters.models.Ingredients;

import java.util.ArrayList;

public class BittersSqlDbDao {
    private static SQLiteDatabase db;

    public static void initializeInstance(Context context) {
        BittersSqlDbHelper dbHelper = new BittersSqlDbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public static void createCocktail(Cocktail cocktail) {
        if (db != null) {
            ContentValues cocktailValues = insertCocktailValues(cocktail);
            ContentValues ingredientsValues = insertIngredients(cocktail.getIngredients());
            db.insert(BittersSqlDbContract.CocktailEntry.TABLE_NAME, null, cocktailValues);
            db.insert(BittersSqlDbContract.IngredientEntry.TABLE_NAME, null, ingredientsValues);
        }
    }

    public static Cocktail readCocktail(int id) {
        String entryQuery = String.format("SELECT * FROM %s " +
                        "JOIN %s ON %s.%s = %s.%s " +
                        "WHERE %s.%s = %s",
                BittersSqlDbContract.CocktailEntry.TABLE_NAME
                , BittersSqlDbContract.IngredientEntry.TABLE_NAME
                , BittersSqlDbContract.IngredientEntry.TABLE_NAME
                , BittersSqlDbContract.IngredientEntry._ID
                , BittersSqlDbContract.CocktailEntry.TABLE_NAME
                , BittersSqlDbContract.CocktailEntry.COLUMN_NAME_INGREDIENTS_ID
                , BittersSqlDbContract.CocktailEntry.TABLE_NAME
                , BittersSqlDbContract.CocktailEntry._ID, id);
        Cocktail cocktail;
        int index;
        if (db != null) {
            Cursor cursor = db.rawQuery(entryQuery, null);
            if (cursor.moveToNext() && cursor.getCount() == 1) {
                cocktail = new Cocktail();
                index = cursor.getColumnIndexOrThrow(BittersSqlDbContract.CocktailEntry._ID);
                cocktail.setDrinkId(Integer.toString(cursor.getInt(index)));

                index = cursor.getColumnIndexOrThrow(BittersSqlDbContract.CocktailEntry.COLUMN_NAME_DRINK_NAME);
                cocktail.setDrinkName(cursor.getString(index));

                index = cursor.getColumnIndexOrThrow(BittersSqlDbContract.CocktailEntry.COLUMN_NAME_TAGS);
                cocktail.setTags(cursor.getString(index));

                index = cursor.getColumnIndexOrThrow(BittersSqlDbContract.CocktailEntry.COLUMN_NAME_IS_ALCOHOLIC);
                cocktail.setIsAlcoholic(cursor.getString(index));

                index = cursor.getColumnIndexOrThrow(BittersSqlDbContract.CocktailEntry.COLUMN_NAME_CATEGORY);
                cocktail.setCategory(cursor.getString(index));

                index = cursor.getColumnIndexOrThrow(BittersSqlDbContract.CocktailEntry.COLUMN_NAME_GLASS);
                cocktail.setGlass(cursor.getString(index));

                index = cursor.getColumnIndexOrThrow(BittersSqlDbContract.CocktailEntry.COLUMN_NAME_INSTRUCTIONS);
                cocktail.setInstructions(cursor.getString(index));

                index = cursor.getColumnIndexOrThrow(BittersSqlDbContract.CocktailEntry.COLUMN_NAME_PHOTO_URL);
                cocktail.setPhotoUrl(cursor.getString(index));

                index = cursor.getColumnIndexOrThrow(BittersSqlDbContract.CocktailEntry.COLUMN_NAME_DRINK_NAME);
                cocktail.setDrinkName(cursor.getString(index));

                index = cursor.getColumnIndexOrThrow(BittersSqlDbContract.IngredientEntry._ID);
                cocktail.getIngredients().setIngredientsId(cursor.getInt(index));

                index = cursor.getColumnIndexOrThrow(BittersSqlDbContract.IngredientEntry.COLUMN_NAME_INGREDIENT_1);
                cocktail.getIngredients().setStrIngredient1(cursor.getString(index));

                index = cursor.getColumnIndexOrThrow(BittersSqlDbContract.IngredientEntry.COLUMN_NAME_INGREDIENT_2);
                cocktail.getIngredients().setStrIngredient2(cursor.getString(index));

                index = cursor.getColumnIndexOrThrow(BittersSqlDbContract.IngredientEntry.COLUMN_NAME_INGREDIENT_3);
                cocktail.getIngredients().setStrIngredient3(cursor.getString(index));

                index = cursor.getColumnIndexOrThrow(BittersSqlDbContract.IngredientEntry.COLUMN_NAME_INGREDIENT_4);
                cocktail.getIngredients().setStrIngredient4(cursor.getString(index));

                index = cursor.getColumnIndexOrThrow(BittersSqlDbContract.IngredientEntry.COLUMN_NAME_INGREDIENT_5);
                cocktail.getIngredients().setStrIngredient5(cursor.getString(index));

                index = cursor.getColumnIndexOrThrow(BittersSqlDbContract.IngredientEntry.COLUMN_NAME_INGREDIENT_6);
                cocktail.getIngredients().setStrIngredient6(cursor.getString(index));

                index = cursor.getColumnIndexOrThrow(BittersSqlDbContract.IngredientEntry.COLUMN_NAME_INGREDIENT_7);
                cocktail.getIngredients().setStrIngredient7(cursor.getString(index));

                index = cursor.getColumnIndexOrThrow(BittersSqlDbContract.IngredientEntry.COLUMN_NAME_INGREDIENT_8);
                cocktail.getIngredients().setStrIngredient8(cursor.getString(index));

                index = cursor.getColumnIndexOrThrow(BittersSqlDbContract.IngredientEntry.COLUMN_NAME_INGREDIENT_9);
                cocktail.getIngredients().setStrIngredient9(cursor.getString(index));

                index = cursor.getColumnIndexOrThrow(BittersSqlDbContract.IngredientEntry.COLUMN_NAME_INGREDIENT_10);
                cocktail.getIngredients().setStrIngredient10(cursor.getString(index));

                index = cursor.getColumnIndexOrThrow(BittersSqlDbContract.IngredientEntry.COLUMN_NAME_INGREDIENT_11);
                cocktail.getIngredients().setStrIngredient11(cursor.getString(index));

                index = cursor.getColumnIndexOrThrow(BittersSqlDbContract.IngredientEntry.COLUMN_NAME_INGREDIENT_12);
                cocktail.getIngredients().setStrIngredient12(cursor.getString(index));

                index = cursor.getColumnIndexOrThrow(BittersSqlDbContract.IngredientEntry.COLUMN_NAME_INGREDIENT_13);
                cocktail.getIngredients().setStrIngredient13(cursor.getString(index));

                index = cursor.getColumnIndexOrThrow(BittersSqlDbContract.IngredientEntry.COLUMN_NAME_INGREDIENT_14);
                cocktail.getIngredients().setStrIngredient14(cursor.getString(index));

                index = cursor.getColumnIndexOrThrow(BittersSqlDbContract.IngredientEntry.COLUMN_NAME_INGREDIENT_15);
                cocktail.getIngredients().setStrIngredient15(cursor.getString(index));

                index = cursor.getColumnIndexOrThrow(BittersSqlDbContract.IngredientEntry.COLUMN_NAME_MEASURMENT_1);
                cocktail.getIngredients().setStrMeasure1(cursor.getString(index));

                index = cursor.getColumnIndexOrThrow(BittersSqlDbContract.IngredientEntry.COLUMN_NAME_MEASURMENT_2);
                cocktail.getIngredients().setStrMeasure2(cursor.getString(index));

                index = cursor.getColumnIndexOrThrow(BittersSqlDbContract.IngredientEntry.COLUMN_NAME_MEASURMENT_3);
                cocktail.getIngredients().setStrMeasure3(cursor.getString(index));

                index = cursor.getColumnIndexOrThrow(BittersSqlDbContract.IngredientEntry.COLUMN_NAME_MEASURMENT_4);
                cocktail.getIngredients().setStrMeasure4(cursor.getString(index));

                index = cursor.getColumnIndexOrThrow(BittersSqlDbContract.IngredientEntry.COLUMN_NAME_MEASURMENT_5);
                cocktail.getIngredients().setStrMeasure5(cursor.getString(index));

                index = cursor.getColumnIndexOrThrow(BittersSqlDbContract.IngredientEntry.COLUMN_NAME_MEASURMENT_6);
                cocktail.getIngredients().setStrMeasure6(cursor.getString(index));

                index = cursor.getColumnIndexOrThrow(BittersSqlDbContract.IngredientEntry.COLUMN_NAME_MEASURMENT_7);
                cocktail.getIngredients().setStrMeasure7(cursor.getString(index));

                index = cursor.getColumnIndexOrThrow(BittersSqlDbContract.IngredientEntry.COLUMN_NAME_MEASURMENT_8);
                cocktail.getIngredients().setStrMeasure8(cursor.getString(index));

                index = cursor.getColumnIndexOrThrow(BittersSqlDbContract.IngredientEntry.COLUMN_NAME_MEASURMENT_9);
                cocktail.getIngredients().setStrMeasure9(cursor.getString(index));

                index = cursor.getColumnIndexOrThrow(BittersSqlDbContract.IngredientEntry.COLUMN_NAME_MEASURMENT_10);
                cocktail.getIngredients().setStrMeasure10(cursor.getString(index));

                index = cursor.getColumnIndexOrThrow(BittersSqlDbContract.IngredientEntry.COLUMN_NAME_MEASURMENT_11);
                cocktail.getIngredients().setStrMeasure11(cursor.getString(index));

                index = cursor.getColumnIndexOrThrow(BittersSqlDbContract.IngredientEntry.COLUMN_NAME_MEASURMENT_12);
                cocktail.getIngredients().setStrMeasure12(cursor.getString(index));

                index = cursor.getColumnIndexOrThrow(BittersSqlDbContract.IngredientEntry.COLUMN_NAME_MEASURMENT_13);
                cocktail.getIngredients().setStrMeasure13(cursor.getString(index));

                index = cursor.getColumnIndexOrThrow(BittersSqlDbContract.IngredientEntry.COLUMN_NAME_MEASURMENT_14);
                cocktail.getIngredients().setStrMeasure14(cursor.getString(index));

                index = cursor.getColumnIndexOrThrow(BittersSqlDbContract.IngredientEntry.COLUMN_NAME_MEASURMENT_15);
                cocktail.getIngredients().setStrMeasure15(cursor.getString(index));
            } else {
                cocktail = null;
            }
            return cocktail;
        } else {
            return null;
        }
    }

    public static ArrayList<Integer> readAllIds() {
        ArrayList<Integer> ids = new ArrayList<>();
        if(db != null) {
            String query = String.format("SELECT %s FROM %s"
                    , BittersSqlDbContract.CocktailEntry._ID
                    , BittersSqlDbContract.CocktailEntry.TABLE_NAME);
            Cursor cursor = db.rawQuery(query, null);
            int index;
            while (cursor.moveToNext()) {
                index = cursor.getColumnIndexOrThrow(BittersSqlDbContract.CocktailEntry._ID);
                int id = cursor.getInt(index);
                ids.add(id);
            }
            cursor.close();
        }
        return ids;
    }

    public static ArrayList<Cocktail> readAllCocktails() {
        ArrayList<Cocktail> cocktails = new ArrayList<>();
        if (db != null) {
            String query = String.format("SELECT * FROM %s " +
                    "JOIN %s ON %s.%s = %s.%s",
                    BittersSqlDbContract.CocktailEntry.TABLE_NAME
                    , BittersSqlDbContract.IngredientEntry.TABLE_NAME
                    , BittersSqlDbContract.IngredientEntry.TABLE_NAME
                    ,BittersSqlDbContract.IngredientEntry._ID
                    , BittersSqlDbContract.CocktailEntry.TABLE_NAME
                    ,BittersSqlDbContract.CocktailEntry.COLUMN_NAME_INGREDIENTS_ID);
            Cursor cursor = db.rawQuery(query, null);
            int index;
            while (cursor.moveToNext()) {
                Cocktail cocktail = new Cocktail();
                index = cursor.getColumnIndexOrThrow(BittersSqlDbContract.CocktailEntry._ID);
                cocktail.setDrinkId(Integer.toString(cursor.getInt(index)));

                index = cursor.getColumnIndexOrThrow(BittersSqlDbContract.CocktailEntry.COLUMN_NAME_DRINK_NAME);
                cocktail.setDrinkName(cursor.getString(index));

                index = cursor.getColumnIndexOrThrow(BittersSqlDbContract.CocktailEntry.COLUMN_NAME_TAGS);
                cocktail.setTags(cursor.getString(index));

                index = cursor.getColumnIndexOrThrow(BittersSqlDbContract.CocktailEntry.COLUMN_NAME_IS_ALCOHOLIC);
                cocktail.setIsAlcoholic(cursor.getString(index));

                index = cursor.getColumnIndexOrThrow(BittersSqlDbContract.CocktailEntry.COLUMN_NAME_CATEGORY);
                cocktail.setCategory(cursor.getString(index));

                index = cursor.getColumnIndexOrThrow(BittersSqlDbContract.CocktailEntry.COLUMN_NAME_GLASS);
                cocktail.setGlass(cursor.getString(index));

                index = cursor.getColumnIndexOrThrow(BittersSqlDbContract.CocktailEntry.COLUMN_NAME_INSTRUCTIONS);
                cocktail.setInstructions(cursor.getString(index));

                index = cursor.getColumnIndexOrThrow(BittersSqlDbContract.CocktailEntry.COLUMN_NAME_PHOTO_URL);
                cocktail.setPhotoUrl(cursor.getString(index));

                index = cursor.getColumnIndexOrThrow(BittersSqlDbContract.CocktailEntry.COLUMN_NAME_DRINK_NAME);
                cocktail.setDrinkName(cursor.getString(index));

                index = cursor.getColumnIndexOrThrow(BittersSqlDbContract.IngredientEntry._ID);
                cocktail.getIngredients().setIngredientsId(cursor.getInt(index));

                index = cursor.getColumnIndexOrThrow(BittersSqlDbContract.IngredientEntry.COLUMN_NAME_INGREDIENT_1);
                cocktail.getIngredients().setStrIngredient1(cursor.getString(index));

                index = cursor.getColumnIndexOrThrow(BittersSqlDbContract.IngredientEntry.COLUMN_NAME_INGREDIENT_2);
                cocktail.getIngredients().setStrIngredient2(cursor.getString(index));

                index = cursor.getColumnIndexOrThrow(BittersSqlDbContract.IngredientEntry.COLUMN_NAME_INGREDIENT_3);
                cocktail.getIngredients().setStrIngredient3(cursor.getString(index));

                index = cursor.getColumnIndexOrThrow(BittersSqlDbContract.IngredientEntry.COLUMN_NAME_INGREDIENT_4);
                cocktail.getIngredients().setStrIngredient4(cursor.getString(index));

                index = cursor.getColumnIndexOrThrow(BittersSqlDbContract.IngredientEntry.COLUMN_NAME_INGREDIENT_5);
                cocktail.getIngredients().setStrIngredient5(cursor.getString(index));

                index = cursor.getColumnIndexOrThrow(BittersSqlDbContract.IngredientEntry.COLUMN_NAME_INGREDIENT_6);
                cocktail.getIngredients().setStrIngredient6(cursor.getString(index));

                index = cursor.getColumnIndexOrThrow(BittersSqlDbContract.IngredientEntry.COLUMN_NAME_INGREDIENT_7);
                cocktail.getIngredients().setStrIngredient7(cursor.getString(index));

                index = cursor.getColumnIndexOrThrow(BittersSqlDbContract.IngredientEntry.COLUMN_NAME_INGREDIENT_8);
                cocktail.getIngredients().setStrIngredient8(cursor.getString(index));

                index = cursor.getColumnIndexOrThrow(BittersSqlDbContract.IngredientEntry.COLUMN_NAME_INGREDIENT_9);
                cocktail.getIngredients().setStrIngredient9(cursor.getString(index));

                index = cursor.getColumnIndexOrThrow(BittersSqlDbContract.IngredientEntry.COLUMN_NAME_INGREDIENT_10);
                cocktail.getIngredients().setStrIngredient10(cursor.getString(index));

                index = cursor.getColumnIndexOrThrow(BittersSqlDbContract.IngredientEntry.COLUMN_NAME_INGREDIENT_11);
                cocktail.getIngredients().setStrIngredient11(cursor.getString(index));

                index = cursor.getColumnIndexOrThrow(BittersSqlDbContract.IngredientEntry.COLUMN_NAME_INGREDIENT_12);
                cocktail.getIngredients().setStrIngredient12(cursor.getString(index));

                index = cursor.getColumnIndexOrThrow(BittersSqlDbContract.IngredientEntry.COLUMN_NAME_INGREDIENT_13);
                cocktail.getIngredients().setStrIngredient13(cursor.getString(index));

                index = cursor.getColumnIndexOrThrow(BittersSqlDbContract.IngredientEntry.COLUMN_NAME_INGREDIENT_14);
                cocktail.getIngredients().setStrIngredient14(cursor.getString(index));

                index = cursor.getColumnIndexOrThrow(BittersSqlDbContract.IngredientEntry.COLUMN_NAME_INGREDIENT_15);
                cocktail.getIngredients().setStrIngredient15(cursor.getString(index));

                index = cursor.getColumnIndexOrThrow(BittersSqlDbContract.IngredientEntry.COLUMN_NAME_MEASURMENT_1);
                cocktail.getIngredients().setStrMeasure1(cursor.getString(index));

                index = cursor.getColumnIndexOrThrow(BittersSqlDbContract.IngredientEntry.COLUMN_NAME_MEASURMENT_2);
                cocktail.getIngredients().setStrMeasure2(cursor.getString(index));

                index = cursor.getColumnIndexOrThrow(BittersSqlDbContract.IngredientEntry.COLUMN_NAME_MEASURMENT_3);
                cocktail.getIngredients().setStrMeasure3(cursor.getString(index));

                index = cursor.getColumnIndexOrThrow(BittersSqlDbContract.IngredientEntry.COLUMN_NAME_MEASURMENT_4);
                cocktail.getIngredients().setStrMeasure4(cursor.getString(index));

                index = cursor.getColumnIndexOrThrow(BittersSqlDbContract.IngredientEntry.COLUMN_NAME_MEASURMENT_5);
                cocktail.getIngredients().setStrMeasure5(cursor.getString(index));

                index = cursor.getColumnIndexOrThrow(BittersSqlDbContract.IngredientEntry.COLUMN_NAME_MEASURMENT_6);
                cocktail.getIngredients().setStrMeasure6(cursor.getString(index));

                index = cursor.getColumnIndexOrThrow(BittersSqlDbContract.IngredientEntry.COLUMN_NAME_MEASURMENT_7);
                cocktail.getIngredients().setStrMeasure7(cursor.getString(index));

                index = cursor.getColumnIndexOrThrow(BittersSqlDbContract.IngredientEntry.COLUMN_NAME_MEASURMENT_8);
                cocktail.getIngredients().setStrMeasure8(cursor.getString(index));

                index = cursor.getColumnIndexOrThrow(BittersSqlDbContract.IngredientEntry.COLUMN_NAME_MEASURMENT_9);
                cocktail.getIngredients().setStrMeasure9(cursor.getString(index));

                index = cursor.getColumnIndexOrThrow(BittersSqlDbContract.IngredientEntry.COLUMN_NAME_MEASURMENT_10);
                cocktail.getIngredients().setStrMeasure10(cursor.getString(index));

                index = cursor.getColumnIndexOrThrow(BittersSqlDbContract.IngredientEntry.COLUMN_NAME_MEASURMENT_11);
                cocktail.getIngredients().setStrMeasure11(cursor.getString(index));

                index = cursor.getColumnIndexOrThrow(BittersSqlDbContract.IngredientEntry.COLUMN_NAME_MEASURMENT_12);
                cocktail.getIngredients().setStrMeasure12(cursor.getString(index));

                index = cursor.getColumnIndexOrThrow(BittersSqlDbContract.IngredientEntry.COLUMN_NAME_MEASURMENT_13);
                cocktail.getIngredients().setStrMeasure13(cursor.getString(index));

                index = cursor.getColumnIndexOrThrow(BittersSqlDbContract.IngredientEntry.COLUMN_NAME_MEASURMENT_14);
                cocktail.getIngredients().setStrMeasure14(cursor.getString(index));

                index = cursor.getColumnIndexOrThrow(BittersSqlDbContract.IngredientEntry.COLUMN_NAME_MEASURMENT_15);
                cocktail.getIngredients().setStrMeasure15(cursor.getString(index));

                cocktails.add(cocktail);
            }
            cursor.close();
        }
        return cocktails;
    }

    public static void updateCocktail(Cocktail cocktail) {
        if (db != null) {
            String whereClause = String.format("%s = %s", BittersSqlDbContract.CocktailEntry._ID,
                    cocktail.getDrinkId());
            String query = String.format("SELECT * FROM %s WHERE %s",
                    BittersSqlDbContract.CocktailEntry.TABLE_NAME, whereClause);
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.getCount() == 1) {
                ContentValues contentValues = insertCocktailValues(cocktail);
                db.update(BittersSqlDbContract.CocktailEntry.TABLE_NAME, contentValues
                        , whereClause, null);
            }
            whereClause = String.format("%s = %s", BittersSqlDbContract.IngredientEntry._ID,
                    cocktail.getIngredients().getIngredientsId());
            query = String.format("SELECT * FROM %s WHERE %s",
                    BittersSqlDbContract.IngredientEntry.TABLE_NAME, whereClause);
            cursor = db.rawQuery(query, null);
            if (cursor.getCount() == 1) {
                ContentValues contentValues = insertIngredients(cocktail.getIngredients());
                db.update(BittersSqlDbContract.IngredientEntry.TABLE_NAME, contentValues
                        , whereClause,null);
            }
            cursor.close();
        }
    }

    public static void deleteCocktail(Cocktail cocktail) {
        if (db != null) {
            String whereClause = String.format("%s = %s"
                    , BittersSqlDbContract.CocktailEntry._ID, cocktail.getDrinkId());
            String query = String.format("SELECT * FROM %s WHERE %s"
                    , BittersSqlDbContract.CocktailEntry.TABLE_NAME, whereClause);
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.getCount() == 1) {
                db.delete(BittersSqlDbContract.CocktailEntry.TABLE_NAME, whereClause, null);
                whereClause = String.format("%s = %s"
                , BittersSqlDbContract.IngredientEntry._ID, cocktail.getIngredients().getIngredientsId());
                query = String.format("SELECT * FROM %s WHERE %s"
                , BittersSqlDbContract.IngredientEntry.TABLE_NAME, whereClause);
                cursor = db.rawQuery(query, null);
                if (cursor.getCount() == 1) {
                    db.delete(BittersSqlDbContract.IngredientEntry.TABLE_NAME, whereClause, null);
                }
            }
            cursor.close();
        }
    }


    public static ContentValues insertCocktailValues(Cocktail cocktail) {
        ContentValues cocktailValues = new ContentValues();

        cocktailValues.put(BittersSqlDbContract.CocktailEntry._ID
                , Integer.parseInt(cocktail.getDrinkId()));
        cocktailValues.put(BittersSqlDbContract.CocktailEntry.COLUMN_NAME_DRINK_NAME
                , cocktail.getDrinkName());
        cocktailValues.put(BittersSqlDbContract.CocktailEntry.COLUMN_NAME_TAGS
                , cocktail.getTags());
        cocktailValues.put(BittersSqlDbContract.CocktailEntry.COLUMN_NAME_IS_ALCOHOLIC
                , cocktail.getIsAlcoholic());
        cocktailValues.put(BittersSqlDbContract.CocktailEntry.COLUMN_NAME_CATEGORY
                , cocktail.getCategory());
        cocktailValues.put(BittersSqlDbContract.CocktailEntry.COLUMN_NAME_GLASS
                , cocktail.getGlass());
        cocktailValues.put(BittersSqlDbContract.CocktailEntry.COLUMN_NAME_INSTRUCTIONS
                , cocktail.getInstructions());
        cocktailValues.put(BittersSqlDbContract.CocktailEntry.COLUMN_NAME_PHOTO_URL
                , cocktail.getPhotoUrl());
        cocktailValues.put(BittersSqlDbContract.CocktailEntry.COLUMN_NAME_INGREDIENTS_ID
                , cocktail.getIngredients().getIngredientsId());

        return cocktailValues;
    }

    public static ContentValues insertIngredients(Ingredients ingredients) {
        ContentValues ingredientsValues = new ContentValues();
        ingredientsValues.put(BittersSqlDbContract.IngredientEntry._ID
                , ingredients.getIngredientsId());
        ingredientsValues.put(BittersSqlDbContract.IngredientEntry.COLUMN_NAME_INGREDIENT_1
                , ingredients.getStrIngredient1());
        ingredientsValues.put(BittersSqlDbContract.IngredientEntry.COLUMN_NAME_INGREDIENT_2
                , ingredients.getStrIngredient2());
        ingredientsValues.put(BittersSqlDbContract.IngredientEntry.COLUMN_NAME_INGREDIENT_3
                , ingredients.getStrIngredient3());
        ingredientsValues.put(BittersSqlDbContract.IngredientEntry.COLUMN_NAME_INGREDIENT_4
                , ingredients.getStrIngredient4());
        ingredientsValues.put(BittersSqlDbContract.IngredientEntry.COLUMN_NAME_INGREDIENT_5
                , ingredients.getStrIngredient5());
        ingredientsValues.put(BittersSqlDbContract.IngredientEntry.COLUMN_NAME_INGREDIENT_6
                , ingredients.getStrIngredient6());
        ingredientsValues.put(BittersSqlDbContract.IngredientEntry.COLUMN_NAME_INGREDIENT_7
                , ingredients.getStrIngredient7());
        ingredientsValues.put(BittersSqlDbContract.IngredientEntry.COLUMN_NAME_INGREDIENT_8
                , ingredients.getStrIngredient8());
        ingredientsValues.put(BittersSqlDbContract.IngredientEntry.COLUMN_NAME_INGREDIENT_9
                , ingredients.getStrIngredient9());
        ingredientsValues.put(BittersSqlDbContract.IngredientEntry.COLUMN_NAME_INGREDIENT_10
                , ingredients.getStrIngredient10());
        ingredientsValues.put(BittersSqlDbContract.IngredientEntry.COLUMN_NAME_INGREDIENT_11
                , ingredients.getStrIngredient11());
        ingredientsValues.put(BittersSqlDbContract.IngredientEntry.COLUMN_NAME_INGREDIENT_12
                , ingredients.getStrIngredient12());
        ingredientsValues.put(BittersSqlDbContract.IngredientEntry.COLUMN_NAME_INGREDIENT_13
                , ingredients.getStrIngredient13());
        ingredientsValues.put(BittersSqlDbContract.IngredientEntry.COLUMN_NAME_INGREDIENT_14
                , ingredients.getStrIngredient14());
        ingredientsValues.put(BittersSqlDbContract.IngredientEntry.COLUMN_NAME_INGREDIENT_15
                , ingredients.getStrIngredient15());

        ingredientsValues.put(BittersSqlDbContract.IngredientEntry.COLUMN_NAME_MEASURMENT_1
                , ingredients.getStrMeasure1());
        ingredientsValues.put(BittersSqlDbContract.IngredientEntry.COLUMN_NAME_MEASURMENT_2
                , ingredients.getStrMeasure2());
        ingredientsValues.put(BittersSqlDbContract.IngredientEntry.COLUMN_NAME_MEASURMENT_3
                , ingredients.getStrMeasure3());
        ingredientsValues.put(BittersSqlDbContract.IngredientEntry.COLUMN_NAME_MEASURMENT_4
                , ingredients.getStrMeasure4());
        ingredientsValues.put(BittersSqlDbContract.IngredientEntry.COLUMN_NAME_MEASURMENT_5
                , ingredients.getStrMeasure5());
        ingredientsValues.put(BittersSqlDbContract.IngredientEntry.COLUMN_NAME_MEASURMENT_6
                , ingredients.getStrMeasure6());
        ingredientsValues.put(BittersSqlDbContract.IngredientEntry.COLUMN_NAME_MEASURMENT_7
                , ingredients.getStrMeasure7());
        ingredientsValues.put(BittersSqlDbContract.IngredientEntry.COLUMN_NAME_MEASURMENT_8
                , ingredients.getStrMeasure8());
        ingredientsValues.put(BittersSqlDbContract.IngredientEntry.COLUMN_NAME_MEASURMENT_9
                , ingredients.getStrMeasure9());
        ingredientsValues.put(BittersSqlDbContract.IngredientEntry.COLUMN_NAME_MEASURMENT_10
                , ingredients.getStrMeasure10());
        ingredientsValues.put(BittersSqlDbContract.IngredientEntry.COLUMN_NAME_MEASURMENT_11
                , ingredients.getStrMeasure11());
        ingredientsValues.put(BittersSqlDbContract.IngredientEntry.COLUMN_NAME_MEASURMENT_12
                , ingredients.getStrMeasure12());
        ingredientsValues.put(BittersSqlDbContract.IngredientEntry.COLUMN_NAME_MEASURMENT_13
                , ingredients.getStrMeasure13());
        ingredientsValues.put(BittersSqlDbContract.IngredientEntry.COLUMN_NAME_MEASURMENT_14
                , ingredients.getStrMeasure14());
        ingredientsValues.put(BittersSqlDbContract.IngredientEntry.COLUMN_NAME_MEASURMENT_15
                , ingredients.getStrMeasure15());

        return ingredientsValues;
    }
}
