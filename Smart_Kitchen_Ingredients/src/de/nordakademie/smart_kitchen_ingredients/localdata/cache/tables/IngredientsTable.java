package de.nordakademie.smart_kitchen_ingredients.localdata.cache.tables;

/**
 * 
 * @author Frederic Oppermann
 *
 */

import android.content.ContentValues;

public class IngredientsTable {
	private static final String TEXT_PRIMARY_KEY = " text primary key not null, ";
	private static final String CREATE_TABLE = "create table ";
	private static final int UNIT_INDEX = 2;
	private static final int NAME_INDEX = 1;
	private static final int INGREDIENT_INDEX = 0;
	public static final String TABLE_NAME = "ingredients_table";
	public static final String ID = "id";
	public static final String NAME = "name";
	public static final String UNIT = "unit";

	public static String getDrop() {
		StringBuilder sb = new StringBuilder();
		sb.append("DROP TABLE IF EXISTS ").append(TABLE_NAME);
		return sb.toString();
	}

	public static final String getTableCreation() {
		StringBuilder sb = new StringBuilder();
		return sb.append(CREATE_TABLE).append(TABLE_NAME).append(" (")
				.append(ID).append(TEXT_PRIMARY_KEY).append(NAME)
				.append(" text, ").append(UNIT).append(" text)").toString();
	}

	public static String[] selectUnitColumn() {
		return new String[] { UNIT };
	}

	public static String[] selectNameColumn() {
		return new String[] { NAME };
	}

	public static ContentValues getContenValuesForAll(String[] ingredient) {
		ContentValues values = new ContentValues();
		values.put(ID, ingredient[INGREDIENT_INDEX]);
		values.put(NAME, ingredient[NAME_INDEX]);
		values.put(UNIT, ingredient[UNIT_INDEX]);
		return values;
	}

	public static String[] getAllColunms() {
		return new String[] { ID, NAME, UNIT };
	}

}
