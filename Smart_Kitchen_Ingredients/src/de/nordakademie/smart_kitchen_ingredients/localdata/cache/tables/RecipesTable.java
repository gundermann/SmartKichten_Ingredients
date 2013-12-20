package de.nordakademie.smart_kitchen_ingredients.localdata.cache.tables;

/**
 * 
 * @author Kathrin Kurtz
 *
 */

import android.content.ContentValues;

public class RecipesTable {

	private static final String PRIMARY_KEY = " text primary key not null, ";
	public static final String TABLE_NAME = "recipes_table";
	public static final String ID = "id";
	public static final String TITLE = "title";

	public static String getDrop() {
		StringBuilder sb = new StringBuilder();
		sb.append("DROP TABLE IF EXISTS ").append(TABLE_NAME);
		return sb.toString();
	}

	public static final String getTableCreation() {
		StringBuilder sb = new StringBuilder();
		sb.append("create table ").append(TABLE_NAME).append(" (").append(ID)
				.append(PRIMARY_KEY).append(TITLE)
				.append(" text)");
		return sb.toString();
	}

	public static String[] selectAllColunms() {
		return new String[] { ID, TITLE };
	}

	public static ContentValues getContentValuesForAll(String id, String title) {
		ContentValues values = new ContentValues();
		values.put(RecipesTable.ID, id);
		values.put(RecipesTable.TITLE, title);
		return values;
	}

}
