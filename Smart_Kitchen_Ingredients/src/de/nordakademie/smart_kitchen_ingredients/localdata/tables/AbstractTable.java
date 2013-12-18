package de.nordakademie.smart_kitchen_ingredients.localdata.tables;

public abstract class AbstractTable {

	private static final String TABLE_NAME = "";

	public static String getDrop() {
		StringBuilder sb = new StringBuilder();
		sb.append("DROP TABLE IF EXISTS ").append(TABLE_NAME);
		return sb.toString();
	}

}
