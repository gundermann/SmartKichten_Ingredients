package de.nordakademie.smart_kitchen_ingredients.localdata;

import java.util.ArrayList;
import java.util.List;

import de.nordakademie.smart_kitchen_ingredients.businessobjects.ShoppingItem;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ShoppingData extends SQLiteOpenHelper {

	private static final String TAG = ShoppingData.class.getSimpleName();

	private static final int DATABASE_VERSION = 3;
	private static final String DATABASE_NAME = "shoppingDB";

	public static final String TABLE_SHOPPING = "shopping_table";
	public static final String COLUMN_ID = "id";
	public static final String COLUMN_INGREDIENT = "ingredient";
	public static final String COLUMN_BUYED = "buyed";

	private static final String DATABASE_CREATE = "create table "
			+ TABLE_SHOPPING + " (" + COLUMN_ID
			+ " integer primary key autoincrement, " + COLUMN_INGREDIENT
			+ " text, " + COLUMN_BUYED + " text)";

	public ShoppingData(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
				+ newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_SHOPPING);
		onCreate(db);
	}

	public void insertOrIgnore(ContentValues values) {
		SQLiteDatabase writableDatabase = getWritableDatabase();
		writableDatabase.insertWithOnConflict(TABLE_SHOPPING, null, values,
				SQLiteDatabase.CONFLICT_IGNORE);
		writableDatabase.close();
		Log.i(TAG, "database updated");
	}

	public String getIngredientById(long id) {
		SQLiteDatabase db = getReadableDatabase();
		try {

			Cursor cursor = db.query(TABLE_SHOPPING,
					new String[] { COLUMN_INGREDIENT }, COLUMN_ID + "=" + id,
					null, null, null, null);
			try {
				return cursor.moveToNext() ? cursor.getString(0) : null;
			} finally {
				cursor.close();
			}
		} finally {
			db.close();
		}
	}

	public boolean getBuyedById(long id) {
		SQLiteDatabase db = getReadableDatabase();
		try {

			Cursor cursor = db.query(TABLE_SHOPPING,
					new String[] { COLUMN_BUYED }, COLUMN_ID + "=" + id, null,
					null, null, null);
			try {
				return cursor.moveToNext() ? Boolean.valueOf(cursor
						.getString(0)) : null;
			} finally {
				cursor.close();
			}
		} finally {
			db.close();
		}
	}

	public List<ShoppingItem> getAllShoppingItems() {
		SQLiteDatabase db = getReadableDatabase();
		try {

			List<ShoppingItem> values = new ArrayList<ShoppingItem>();
			Cursor cursor = db.query(TABLE_SHOPPING, new String[] { COLUMN_ID,
					COLUMN_INGREDIENT, COLUMN_BUYED }, null, null, null, null,
					null);
			try {
				while (cursor.moveToNext()) {
					values.add(getShoppingItem(cursor));
				}
				return values;
			} finally {
				cursor.close();
			}
		} finally {
			db.close();
		}
	}

	private ShoppingItem getShoppingItem(Cursor cursor) {
		double id = cursor.getDouble(0);
		String title = cursor.getString(1);
		boolean buyed = Boolean.valueOf(cursor.getString(2));
		return new ShoppingItem(id, title, buyed);
	}

	public void updateShoppingItem(ShoppingItem item) {
		ContentValues value = new ContentValues();
		value.put(COLUMN_BUYED, String.valueOf(item.isBuyed()));
		SQLiteDatabase writableDatabase = getWritableDatabase();
		writableDatabase.update(TABLE_SHOPPING, value,
				COLUMN_ID + " = " + item.getId(), null);
		writableDatabase.close();
		Log.i(TAG, "database updated");
	}
}
