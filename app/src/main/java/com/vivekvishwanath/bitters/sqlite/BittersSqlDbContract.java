package com.vivekvishwanath.bitters.sqlite;

import android.provider.BaseColumns;

public class BittersSqlDbContract {
    public static class CocktailEntry implements BaseColumns {
        public static final String TABLE_NAME = "cocktails";
        public static final String COLUMN_NAME_DRINK_NAME = "strDrink";
        public static final String COLUMN_NAME_TAGS = "strTags";
        public static final String COLUMN_NAME_IS_ALCOHOLIC = "strAlcoholic";
        public static final String COLUMN_NAME_CATEGORY = "strCategory";
        public static final String COLUMN_NAME_GLASS = "strGlass";
        public static final String COLUMN_NAME_INSTRUCTIONS = "strInstructions";
        public static final String COLUMN_NAME_PHOTO_URL = "strDrinkThumb";
        public static final String COLUMN_NAME_INGREDIENTS_ID = "ingredientsId";

        public static final String INGREDIENTS_TABLE_NAME = "ingredients";

        public static final String SQL_CREATE_TABLE = "CREATE TABLE " + TABLE_NAME
                + " ( "
                + _ID + " INTEGER PRIMARY KEY, "
                + COLUMN_NAME_DRINK_NAME + " TEXT, "
                + COLUMN_NAME_TAGS + " TEXT, "
                + COLUMN_NAME_IS_ALCOHOLIC + " TEXT, "
                + COLUMN_NAME_CATEGORY + " TEXT, "
                + COLUMN_NAME_GLASS + " TEXT, "
                + COLUMN_NAME_INSTRUCTIONS + " TEXT, "
                + COLUMN_NAME_PHOTO_URL + " TEXT, "
                + COLUMN_NAME_INGREDIENTS_ID + " INGETER, "
                + "FOREIGN KEY (" + COLUMN_NAME_INGREDIENTS_ID + ") REFERENCES "
                + INGREDIENTS_TABLE_NAME + "(" + _ID + "));";

        public static final String SQL_DELETE_TABLE = "DROP TABLE IF EXISTS "
                + TABLE_NAME + ";";
    }

    public static class IngredientEntry implements BaseColumns {
        public static final String TABLE_NAME = "ingredients";
        public static final String COLUMN_NAME_INGREDIENT_1 = "strIngredient1";
        public static final String COLUMN_NAME_INGREDIENT_2 = "strIngredient2";
        public static final String COLUMN_NAME_INGREDIENT_3 = "strIngredient3";
        public static final String COLUMN_NAME_INGREDIENT_4 = "strIngredient4";
        public static final String COLUMN_NAME_INGREDIENT_5 = "strIngredient5";
        public static final String COLUMN_NAME_INGREDIENT_6 = "strIngredient6";
        public static final String COLUMN_NAME_INGREDIENT_7 = "strIngredient7";
        public static final String COLUMN_NAME_INGREDIENT_8 = "strIngredient8";
        public static final String COLUMN_NAME_INGREDIENT_9 = "strIngredient9";
        public static final String COLUMN_NAME_INGREDIENT_10 = "strIngredient10";
        public static final String COLUMN_NAME_INGREDIENT_11 = "strIngredient11";
        public static final String COLUMN_NAME_INGREDIENT_12 = "strIngredient12";
        public static final String COLUMN_NAME_INGREDIENT_13 = "strIngredient13";
        public static final String COLUMN_NAME_INGREDIENT_14 = "strIngredient14";
        public static final String COLUMN_NAME_INGREDIENT_15 = "strIngredient15";

        public static final String COLUMN_NAME_MEASURMENT_1 = "strMeasure1";
        public static final String COLUMN_NAME_MEASURMENT_2 = "strMeasure2";
        public static final String COLUMN_NAME_MEASURMENT_3 = "strMeasure3";
        public static final String COLUMN_NAME_MEASURMENT_4 = "strMeasure4";
        public static final String COLUMN_NAME_MEASURMENT_5 = "strMeasure5";
        public static final String COLUMN_NAME_MEASURMENT_6 = "strMeasure6";
        public static final String COLUMN_NAME_MEASURMENT_7 = "strMeasure7";
        public static final String COLUMN_NAME_MEASURMENT_8 = "strMeasure8";
        public static final String COLUMN_NAME_MEASURMENT_9 = "strMeasure9";
        public static final String COLUMN_NAME_MEASURMENT_10 = "strMeasure10";
        public static final String COLUMN_NAME_MEASURMENT_11 = "strMeasure11";
        public static final String COLUMN_NAME_MEASURMENT_12 = "strMeasure12";
        public static final String COLUMN_NAME_MEASURMENT_13 = "strMeasure13";
        public static final String COLUMN_NAME_MEASURMENT_14 = "strMeasure14";
        public static final String COLUMN_NAME_MEASURMENT_15 = "strMeasure15";

        public static final String SQL_CREATE_TABLE = "CREATE TABLE " + TABLE_NAME
                + " ( "
                + _ID + " INTEGER PRIMARY KEY, "
                + COLUMN_NAME_INGREDIENT_1 + " TEXT, "
                + COLUMN_NAME_INGREDIENT_2 + " TEXT, "
                + COLUMN_NAME_INGREDIENT_3 + " TEXT, "
                + COLUMN_NAME_INGREDIENT_4 + " TEXT, "
                + COLUMN_NAME_INGREDIENT_5 + " TEXT, "
                + COLUMN_NAME_INGREDIENT_6 + " TEXT, "
                + COLUMN_NAME_INGREDIENT_7 + " TEXT, "
                + COLUMN_NAME_INGREDIENT_8 + " TEXT, "
                + COLUMN_NAME_INGREDIENT_9 + " TEXT, "
                + COLUMN_NAME_INGREDIENT_10 + " TEXT, "
                + COLUMN_NAME_INGREDIENT_11 + " TEXT, "
                + COLUMN_NAME_INGREDIENT_12 + " TEXT, "
                + COLUMN_NAME_INGREDIENT_13 + " TEXT, "
                + COLUMN_NAME_INGREDIENT_14 + " TEXT, "
                + COLUMN_NAME_INGREDIENT_15 + " TEXT, "
                + COLUMN_NAME_MEASURMENT_1 + " TEXT, "
                + COLUMN_NAME_MEASURMENT_2 + " TEXT, "
                + COLUMN_NAME_MEASURMENT_3 + " TEXT, "
                + COLUMN_NAME_MEASURMENT_4 + " TEXT, "
                + COLUMN_NAME_MEASURMENT_5 + " TEXT, "
                + COLUMN_NAME_MEASURMENT_6 + " TEXT, "
                + COLUMN_NAME_MEASURMENT_7 + " TEXT, "
                + COLUMN_NAME_MEASURMENT_8 + " TEXT, "
                + COLUMN_NAME_MEASURMENT_9 + " TEXT, "
                + COLUMN_NAME_MEASURMENT_10 + " TEXT, "
                + COLUMN_NAME_MEASURMENT_11 + " TEXT, "
                + COLUMN_NAME_MEASURMENT_12 + " TEXT, "
                + COLUMN_NAME_MEASURMENT_13 + " TEXT, "
                + COLUMN_NAME_MEASURMENT_14 + " TEXT, "
                + COLUMN_NAME_MEASURMENT_15 + " TEXT);";

        public static final String SQL_DELETE_TABLE = "DROP TABLE IF EXISTS "
                + TABLE_NAME + ";";
    }
}
