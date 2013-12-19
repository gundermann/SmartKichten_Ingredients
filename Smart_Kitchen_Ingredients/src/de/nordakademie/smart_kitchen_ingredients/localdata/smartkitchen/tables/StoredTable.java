package de.nordakademie.smart_kitchen_ingredients.localdata.smartkitchen.tables;

import android.content.ContentValues;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.IIngredient;

public class StoredTable {

	public static final String TABLE_NAME = "stored_table";
	public static final String ID = "id";
	public static final String NAME = "ingredient";
	public static final String QUANTITY = "quantity";
	public static final String UNIT = "unit";
	public static final String BOUGHT = "bought";

	public static String getDrop() {
		StringBuilder sb = new StringBuilder();
		sb.append("DROP TABLE IF EXISTS ").append(TABLE_NAME);
		return sb.toString();
	}

	public static String getTableCreation() {
		StringBuilder sb = new StringBuilder();
		sb.append("create table ").append(TABLE_NAME).append(" (").append(ID)
				.append(" integer primary key autoincrement, ").append(NAME)
				.append(" text, ").append(QUANTITY).append(" interger, ")
				.append(UNIT).append(" text, ").append(BOUGHT).append(" text)");
		return sb.toString();
	}

	public static String[] selectQuantity() {
		return new String[] { QUANTITY };
	}

	public static String[] selectNameQuantityUnit() {
		return new String[] { NAME, QUANTITY, UNIT };
	}

	public static ContentValues getContentValuesForNameQuantityUnit(
			IIngredient ingredient, int quantity) {
		ContentValues values = new ContentValues();
		values.put(NAME, ingredient.getName());
		values.put(QUANTITY, quantity);
		values.put(UNIT, ingredient.getUnit().toString());
		return values;
	}

	public static String[] selectUnit() {
		return new String[] { UNIT };
	}
}
