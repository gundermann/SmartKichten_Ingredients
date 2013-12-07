package de.nordakademie.smart_kitchen_ingredients.localdata;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.ShoppingListItem;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.ShoppingListItemFactory;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.ShoppingListItemFactoryImpl;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.Unit;

public class ShoppingDataImpl extends SQLiteOpenHelper implements ShoppingData {

	private static final String TAG = ShoppingDataImpl.class.getSimpleName();

	private static final int DATABASE_VERSION = 4;
	private static final String DATABASE_NAME = "shoppingDB";

	public static final String TABLE_SHOPPING = "shopping_table";
	public static final String COLUMN_ID = "id";
	public static final String COLUMN_INGREDIENT = "ingredient";
	public static final String COLUMN_AMOUNT = "amount";
	public static final String COLUMN_UNIT = "unit";
	public static final String COLUMN_BUYED = "bourght";

	private static final String DATABASE_CREATE = "create table "
			+ TABLE_SHOPPING + " (" + COLUMN_ID
			+ " integer primary key autoincrement, " + COLUMN_INGREDIENT
			+ " text, " + COLUMN_AMOUNT + " interger, " + COLUMN_UNIT
			+ " text, " + COLUMN_BUYED + " text)";

	public ShoppingDataImpl(Context context) {
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

	@Override
	public void insertOrIgnore(List<String> ingredientList) {
		ContentValues values = new ContentValues();
		for (String ingredient : ingredientList) {
			values.put(COLUMN_INGREDIENT, ingredient);
			values.put(COLUMN_BUYED, String.valueOf(false));
		}

		SQLiteDatabase writableDatabase = getWritableDatabase();
		writableDatabase.insertWithOnConflict(TABLE_SHOPPING, null, values,
				SQLiteDatabase.CONFLICT_IGNORE);
		writableDatabase.close();
		Log.i(TAG, "database updated");
	}

	@Override
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

	@Override
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

	@Override
	public List<ShoppingListItem> getAllShoppingItems() {
		SQLiteDatabase db = getReadableDatabase();
		try {

			List<ShoppingListItem> values = new ArrayList<ShoppingListItem>();
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

	private ShoppingListItem getShoppingItem(Cursor cursor) {
		String title = cursor.getString(1);
		int amount = cursor.getInt(2);
		Unit unit = Unit.valueOf(cursor.getString(3));
		boolean bought = Boolean.valueOf(cursor.getString(4));
		ShoppingListItemFactory factory = new ShoppingListItemFactoryImpl();
		return factory.createShoppingListItem(title, amount, unit, bought);
	}

	@Override
	public void updateShoppingItem(ShoppingListItem item) {
		ContentValues value = new ContentValues();
		value.put(COLUMN_BUYED, String.valueOf(item.isBought()));
		SQLiteDatabase writableDatabase = getWritableDatabase();
		writableDatabase.update(TABLE_SHOPPING, value, COLUMN_INGREDIENT
				+ " = " + item.getTitle(), null);
		writableDatabase.close();
		Log.i(TAG, "database updated");
	}
}
