package de.nordakademie.smart_kitchen_ingredients.localdata.cache.tables;

import android.content.ContentValues;
import android.util.Log;

public class IngredientsToRecipeTable {
	public static final String TABLE_NAME = "indigrentToRecipe";
	public static final String RECIPE_ID = "recipe_id";
	public static final String INGRDIENT_ID = "indigrent_id";
	public static final String QUANTITY = "quantity";

	public static String getDrop() {
		StringBuilder sb = new StringBuilder();
		sb.append("DROP TABLE IF EXISTS ").append(TABLE_NAME);
		return sb.toString();
	}

	public static String getTableCreation() {
		StringBuilder sb = new StringBuilder();
		sb.append("create table ").append(TABLE_NAME).append(" (")
				.append(RECIPE_ID).append(" text, ").append(INGRDIENT_ID)
				.append(" text, ").append(QUANTITY).append(" Integer)");
		return sb.toString();
	}

	public static String[] selectIngredientIdAndQuantity() {
		return new String[] { INGRDIENT_ID, QUANTITY };
	}

	public static ContentValues getContentValuesForAll(String recipeID,
			String[] ingredient) {
		ContentValues values = new ContentValues();
		values.put(RECIPE_ID, recipeID);
		values.put(INGRDIENT_ID, ingredient[0]);
		values.put(QUANTITY, ingredient[3]);
		return values;
	}
}
