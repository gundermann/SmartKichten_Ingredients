package de.nordakademie.smart_kitchen_ingredients.localdata.tables;

import android.content.ContentValues;

public class IngredientsTable extends AbstractTable {
	public static final String TABLE_NAME = "ingredients_table";
	public static final String ID = "id";
	public static final String NAME = "name";
	public static final String UNIT = "unit";

	public static String getDrop() {
		StringBuilder sb = new StringBuilder();
		sb.append("DROP TABLE IF EXISTS ").append(TABLE_NAME);
		return sb.toString();
	}

	public final static String getTableCreation() {
		StringBuilder sb = new StringBuilder();
		sb.append("create table ").append(TABLE_NAME).append(" (").append(ID)
				.append(" text primary key not null, ").append(NAME)
				.append(" text, ").append(UNIT).append(" text)");
		return sb.toString();
	}

	public static String[] selectUnitColumn() {
		return new String[] { UNIT };
	}

	public static String[] selectNameColumn() {
		return new String[] { NAME };
	}

	public static ContentValues getContenValuesForAll(String[] ingredient) {
		ContentValues values = new ContentValues();
		values.put(ID, ingredient[0]);
		values.put(NAME, ingredient[1]);
		values.put(UNIT, ingredient[2]);
		return values;
	}

	public static String[] getAllColunms() {
		return new String[] { ID, NAME, UNIT };
	}

}
