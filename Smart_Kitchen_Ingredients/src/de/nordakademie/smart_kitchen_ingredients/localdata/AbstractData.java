package de.nordakademie.smart_kitchen_ingredients.localdata;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import de.nordakademie.smart_kitchen_ingredients.IngredientsApplication;

public abstract class AbstractData extends SQLiteOpenHelper {

	SQLiteDatabase readableDb;

	protected Cursor cursor;

	protected IngredientsApplication app;

	public AbstractData(IngredientsApplication application, String name,
			CursorFactory factory, int version) {
		super(application, name, factory, version);
		app = application;
	}

	protected void openCursorResoures() {
		readableDb = getReadableDatabase();
	}

	protected void closeCursorResources() {
		readableDb.close();
		if (cursor != null && cursor.isClosed()) {
			cursor.close();
		}
	}

	protected void setCursor(String table, String[] selection, String where) {
		readableDb.query(table, selection, where, null, null, null, null);
	}

	protected void setCursor(String tableName, String[] allColunms) {
		setCursor(tableName, allColunms, null);
	}

	protected String getWhere(String column, Object value) {
		StringBuilder sb = new StringBuilder();
		sb.append(column).append("='").append(value).append("'");
		return sb.toString();
	}

	protected boolean insert(String table, ContentValues values) {
		boolean success;
		try {
			getWritableDatabase().insertOrThrow(table, null, values);
			success = true;
		} catch (SQLException sqle) {
			success = false;
		} finally {
			getWritableDatabase().close();
		}
		return success;
	}

	protected int update(String table, ContentValues values, String where) {
		int updatedItems = getWritableDatabase().update(table, values, where,
				null);
		getWritableDatabase().close();
		return updatedItems;
	}

	protected int delete(String table, String where) {
		int deletedItems = getWritableDatabase().delete(table, where, null);
		getWritableDatabase().close();
		return deletedItems;
	}

	protected int delete(String table) {
		return delete(table, null);
	}
}
