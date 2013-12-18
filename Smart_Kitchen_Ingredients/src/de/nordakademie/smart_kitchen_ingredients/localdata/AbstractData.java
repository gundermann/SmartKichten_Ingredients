package de.nordakademie.smart_kitchen_ingredients.localdata;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import de.nordakademie.smart_kitchen_ingredients.IngredientsApplication;

public abstract class AbstractData extends SQLiteOpenHelper {

	SQLiteDatabase writeableDb;

	SQLiteDatabase readableDb;

	Cursor cursor;

	IngredientsApplication app;

	public AbstractData(IngredientsApplication application, String name,
			CursorFactory factory, int version) {
		super(application, name, factory, version);
		app = application;
	}

	protected void openResoures() {
		readableDb = getReadableDatabase();
		writeableDb = getWritableDatabase();
	}

	protected void closeResources() {
		writeableDb.close();
		readableDb.close();
		if (cursor != null && cursor.isClosed()) {
			cursor.close();
		}
	}

	protected void setCursor(String table, String[] selection, String where) {
		cursor = readableDb.query(table, selection, where, null, null, null,
				null);
	}

	protected void setCursor(String tableName, String[] allColunms) {
		setCursor(tableName, allColunms, null);
	}

	protected String getWhere(String table, String value) {
		StringBuilder sb = new StringBuilder();
		sb.append(table).append("='").append(value).append("'");
		return sb.toString();
	}

	protected void insert(String table, ContentValues values) {
		writeableDb.insertWithOnConflict(table, null, values,
				SQLiteDatabase.CONFLICT_IGNORE);
	}
}
