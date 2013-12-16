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
import de.nordakademie.smart_kitchen_ingredients.businessobjects.IIngredient;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.IIngredientFactory;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.IRecipe;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.IShoppingListItem;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.IShoppingListItemFactory;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.Unit;

/**
 * Verarbeitung der Datenbankanfragen bzgl. der Einkaufsliste und der
 * Bestehenden Zutaten.
 * 
 * @author niels
 * 
 */
public class SmartKitchenData extends SQLiteOpenHelper implements
		IShoppingData, IStoredData {

	private IngredientsApplication app;

	private static final String TAG = SmartKitchenData.class.getSimpleName();

	private static final int DATABASE_VERSION = 7;
	private static final String DATABASE_NAME = "shoppingDB.db";

	public static final String TABLE_SHOPPING = "shopping_table";
	public static final String COLUMN_ID = "id";
	public static final String COLUMN_INGREDIENT = "ingredient";
	public static final String COLUMN_AMOUNT = "amount";
	public static final String COLUMN_UNIT = "unit";
	public static final String COLUMN_BOUGHT = "bourght";

	public static final String TABLE_STORED = "stored_table";

	private static final String SHOPPING_TABLE_CREATE = "create table "
			+ TABLE_SHOPPING + " (" + COLUMN_ID
			+ " integer primary key autoincrement, " + COLUMN_INGREDIENT
			+ " text, " + COLUMN_AMOUNT + " interger, " + COLUMN_UNIT
			+ " text, " + COLUMN_BOUGHT + " text)";

	private static final String STORED_TABLE_CREATE = "create table "
			+ TABLE_STORED + " (" + COLUMN_ID
			+ " integer primary key autoincrement, " + COLUMN_INGREDIENT
			+ " text, " + COLUMN_AMOUNT + " interger, " + COLUMN_UNIT
			+ " text)";

	public SmartKitchenData(IngredientsApplication app) {
		super(app.getApplicationContext(), DATABASE_NAME, null,
				DATABASE_VERSION);
		this.app = app;
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(SHOPPING_TABLE_CREATE);
		database.execSQL(STORED_TABLE_CREATE);
		Log.i(TAG, "shoppingDB created");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
				+ newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_SHOPPING);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_STORED);
		onCreate(db);
	}

	@Override
	public List<IShoppingListItem> getAllShoppingItems() {
		SQLiteDatabase db = getReadableDatabase();
		try {

			List<IShoppingListItem> values = new ArrayList<IShoppingListItem>();
			Cursor cursor = db.query(TABLE_SHOPPING, new String[] {
					COLUMN_INGREDIENT, COLUMN_AMOUNT, COLUMN_UNIT,
					COLUMN_BOUGHT }, null, null, null, null, null);
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

	private IShoppingListItem getShoppingItem(Cursor cursor) {
		String title = cursor.getString(0);
		int amount = cursor.getInt(1);
		Unit unit = Unit.valueOf(cursor.getString(2));
		boolean bought = Boolean.valueOf(cursor.getString(3));
		IShoppingListItemFactory factory = app.getShoppingListItemFactory();
		return factory.createShoppingListItem(title, amount, unit, bought);
	}

	@Override
	public void updateShoppingItem(IShoppingListItem item) {
		ContentValues value = new ContentValues();
		value.put(COLUMN_BOUGHT, String.valueOf(item.isBought()));
		SQLiteDatabase writableDatabase = getWritableDatabase();
		writableDatabase.update(TABLE_SHOPPING, value, COLUMN_INGREDIENT
				+ " = '" + item.getName() + "'", null);
		writableDatabase.close();
		Log.i(TAG, "shopping_table updated");
	}

	@Override
	public void cleanShoppingIngredients() {
		SQLiteDatabase db = getWritableDatabase();
		db.delete(TABLE_SHOPPING, COLUMN_BOUGHT + " = '" + String.valueOf(true)
				+ "'", null);
		db.close();
	}

	@Override
	public IShoppingListItem getShoppingItem(String title) {
		SQLiteDatabase db = getReadableDatabase();
		try {

			Cursor cursor = db.query(TABLE_SHOPPING, new String[] {
					COLUMN_INGREDIENT, COLUMN_AMOUNT, COLUMN_UNIT,
					COLUMN_BOUGHT }, COLUMN_INGREDIENT + " = '" + title + "'",
					null, null, null, null);
			try {
				cursor.moveToNext();
				return getShoppingItem(cursor);
			} finally {
				cursor.close();
			}
		} finally {
			db.close();
		}

	}

	@Override
	public List<IIngredient> getAllStoredIngredients() {
		SQLiteDatabase db = getReadableDatabase();
		try {

			List<IIngredient> values = new ArrayList<IIngredient>();
			Cursor cursor = db.query(TABLE_STORED, new String[] {
					COLUMN_INGREDIENT, COLUMN_AMOUNT, COLUMN_UNIT }, null,
					null, null, null, null);
			try {
				while (cursor.moveToNext()) {
					values.add(getStoredItem(cursor));
				}
				return values;
			} finally {
				cursor.close();
			}
		} finally {
			db.close();
		}
	}

	private IIngredient getStoredItem(Cursor cursor) {
		String title = cursor.getString(0);
		int amount = cursor.getInt(1);
		Unit unit = Unit.valueOf(cursor.getString(2));
		IIngredientFactory factory = app.getIngredientFactory();
		return factory.createIngredient(title, amount, unit);
	}

	@Override
	public void insertOrUpdateIngredient(IIngredient boughtIngredient) {
		ContentValues values = new ContentValues();
		values.put(COLUMN_INGREDIENT, boughtIngredient.getName());
		values.put(COLUMN_AMOUNT, boughtIngredient.getQuantity());

		values.put(COLUMN_UNIT, boughtIngredient.getUnit().toString());

		SQLiteDatabase writableDatabase = getWritableDatabase();
		writableDatabase.insertWithOnConflict(TABLE_STORED, null, values,
				SQLiteDatabase.CONFLICT_IGNORE);
		writableDatabase.close();
		Log.i(TAG, "inserted into stored_table");
	}

	@Override
	public IIngredient getStoredIngredient(String title) {
		SQLiteDatabase db = getReadableDatabase();
		Cursor cursor = db.query(STORED_TABLE_CREATE, new String[] {
				COLUMN_AMOUNT, COLUMN_UNIT }, COLUMN_INGREDIENT + "=" + "'"
				+ title + "'", null, null, null, null);

		cursor.moveToNext();
		int amount = cursor.getInt(0);
		Unit unit = Unit.valueOf(cursor.getString(1));
		IIngredient ingredient = app.getIngredientFactory().createIngredient(
				title, amount, unit);
		cursor.close();
		db.close();

		return ingredient;

	}

	private boolean insertItemsIntoDatabase(List<IIngredient> ingredients) {
		boolean success = false;
		ContentValues values = new ContentValues();
		for (IIngredient ingredient : ingredients) {
			values.put(COLUMN_INGREDIENT, ingredient.getName());
			values.put(COLUMN_AMOUNT, ingredient.getQuantity());
			values.put(COLUMN_UNIT, ingredient.getUnit().toString());
			values.put(COLUMN_BOUGHT, String.valueOf(false));
		}

		SQLiteDatabase writableDatabase = getWritableDatabase();
		writableDatabase.beginTransaction();
		try {
			writableDatabase.insertOrThrow(TABLE_SHOPPING, null, values);
			writableDatabase.setTransactionSuccessful();
			Log.i(TAG, "inserted to shopping_table");

		} catch (SQLiteException sqle) {
			success = false;
			Log.i(TAG, "error while insert into shopping_table");
		} finally {
			writableDatabase.endTransaction();
			writableDatabase.close();
		}
		return success;
	}

	@Override
	public boolean addItem(IIngredient ingredient) {
		List<IIngredient> ingredietList = new ArrayList<IIngredient>();
		return insertItemsIntoDatabase(ingredietList);
	}

	@Override
	public boolean addItem(IRecipe recipe) {
		return insertItemsIntoDatabase(recipe.getIngredients());
	}
}
