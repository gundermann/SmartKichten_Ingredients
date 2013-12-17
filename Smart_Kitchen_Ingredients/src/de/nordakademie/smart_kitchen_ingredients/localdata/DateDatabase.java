package de.nordakademie.smart_kitchen_ingredients.localdata;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import de.nordakademie.smart_kitchen_ingredients.IngredientsApplication;
import de.nordakademie.smart_kitchen_ingredients.scheduling.IDate;

public class DateDatabase extends SQLiteOpenHelper implements IDateDbHelper {

	private IngredientsApplication app;

	private static final String TAG = SmartKitchenData.class.getSimpleName();

	private static final int DATABASE_VERSION = 3;
	private static final String DATABASE_NAME = "dateDB.db";

	public static final String TABLE_DATE = "date_table";
	public static final String COLUMN_INTENTFLAG = "flag";
	public static final String COLUMN_TITLE = "title";
	public static final String COLUMN_TIMESTAMP = "timestamp";

	private static final String DATE_TABLE_CREATE = "create table "
			+ TABLE_DATE + " (" + COLUMN_INTENTFLAG + " integer primary key, "
			+ COLUMN_TITLE + " text, " + COLUMN_TIMESTAMP + " numeric);";

	public DateDatabase(IngredientsApplication app) {
		super(app.getApplicationContext(), DATABASE_NAME, null,
				DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(DATE_TABLE_CREATE);
		Log.i(TAG, "shoppingDB created");
	}

	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVersion,
			int newVersion) {
		Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
				+ newVersion + ", which will destroy all old data");
		database.execSQL("DROP TABLE IF EXISTS " + DATE_TABLE_CREATE);
		onCreate(database);
	}

	@Override
	public boolean insertNewDate(IDate date) {
		boolean success = false;

		ContentValues values = new ContentValues();
		values.put(COLUMN_TITLE, date.getTitle());
		values.put(COLUMN_TIMESTAMP, date.getTimestamp());
		values.put(COLUMN_INTENTFLAG, date.getIntentFlag());

		SQLiteDatabase db = getWritableDatabase();
		try {
			db.insertOrThrow(TABLE_DATE, null, values);
			success = true;
			Log.i(TAG, "inserted into date_table");
		} catch (SQLiteException sqle) {
			Log.d(TAG, "error by insert into date_table");
		} finally {
			db.close();
		}
		return success;

	}

	@Override
	public int getIntentFlagByTime(long timestamp) {
		SQLiteDatabase db = getReadableDatabase();
		Cursor cursor = db.query(TABLE_DATE,
				new String[] { COLUMN_INTENTFLAG }, COLUMN_TIMESTAMP + " = "
						+ timestamp, null, null, null, null);

		cursor.moveToNext();
		int result = cursor.getInt(0);
		cursor.close();
		db.close();
		return result;
	}

	@Override
	public List<IDate> getAllDates() {
		List<IDate> dateList = new ArrayList<IDate>();

		SQLiteDatabase db = getReadableDatabase();
		Cursor cursor = db.query(TABLE_DATE, new String[] { COLUMN_TITLE,
				COLUMN_TIMESTAMP, COLUMN_INTENTFLAG }, null, null, null, null,
				null);

		while (cursor.moveToNext()) {
			String title = cursor.getString(0);
			long timestamp = cursor.getLong(1);
			int intentFlag = cursor.getInt(2);

			dateList.add(app.getDateFactory().createDate(title, timestamp,
					intentFlag));
		}
		cursor.close();
		db.close();
		return dateList;
	}

	@Override
	public int getNextFlag() {
		int flag = 0;
		SQLiteDatabase db = getReadableDatabase();
		Cursor cursor = db.query(TABLE_DATE,
				new String[] { COLUMN_INTENTFLAG }, null, null, null, null,
				null);
		while (cursor.moveToNext()) {
			if (cursor.getInt(0) == flag) {
				flag++;
			} else {
				break;
			}
		}
		return flag;
	}
}
