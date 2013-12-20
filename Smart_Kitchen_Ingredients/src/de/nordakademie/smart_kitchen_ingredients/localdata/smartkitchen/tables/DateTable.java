package de.nordakademie.smart_kitchen_ingredients.localdata.smartkitchen.tables;

/**
 * @author Niels Gundermann
 */

import android.content.ContentValues;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.IDate;

public class DateTable {

	public static final String TABLE_NAME = "date_table";
	public static final String INTENTFLAG = "flag";
	public static final String TITLE = "title";
	public static final String TIMESTAMP = "timestamp";

	public static String getTableCreation() {
		StringBuilder sb = new StringBuilder();
		sb.append("create table ").append(TABLE_NAME).append(" (")
				.append(INTENTFLAG).append(" integer primary key, ")
				.append(TITLE).append(" text, ").append(TIMESTAMP)
				.append(" numeric);");
		return sb.toString();
	}

	public static String getDrop() {
		StringBuilder sb = new StringBuilder();
		sb.append("DROP TABLE IF EXISTS ").append(TABLE_NAME);
		return sb.toString();
	}

	public static ContentValues getContentValuesForAll(IDate date) {
		ContentValues values = new ContentValues();
		values.put(TITLE, date.getTitle());
		values.put(TIMESTAMP, date.getTimestamp());
		values.put(INTENTFLAG, date.getIntentFlag());
		return values;
	}

	public static String[] selectIntentFlag() {
		return new String[] { INTENTFLAG };
	}

	public static String[] selectAll() {
		return new String[] { TITLE, TIMESTAMP, INTENTFLAG };
	}

}
