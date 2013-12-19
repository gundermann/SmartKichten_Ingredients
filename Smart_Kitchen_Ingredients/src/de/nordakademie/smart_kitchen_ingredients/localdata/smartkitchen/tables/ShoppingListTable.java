package de.nordakademie.smart_kitchen_ingredients.localdata.smartkitchen.tables;

import android.content.ContentValues;

public class ShoppingListTable {

	public static final String TABLE_NAME = "shopping_list_table";
	public static final String NAME = "name";

	public static String getTableCreation() {
		StringBuilder sb = new StringBuilder();
		sb.append("create table ").append(TABLE_NAME).append("(").append(NAME)
				.append(" text primary key)");
		return sb.toString();
	}

	public static ContentValues getContentValues(String value) {
		ContentValues values = new ContentValues();
		values.put(NAME, value);
		return values;
	}

	public static String getDrop() {
		StringBuilder sb = new StringBuilder();
		sb.append("DROP TABLE IF EXISTS ").append(TABLE_NAME);
		return sb.toString();
	}

	public static String[] selectName() {
		return new String[] { NAME };
	}

}
