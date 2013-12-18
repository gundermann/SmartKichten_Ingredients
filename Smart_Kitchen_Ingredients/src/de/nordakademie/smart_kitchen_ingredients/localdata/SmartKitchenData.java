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
import de.nordakademie.smart_kitchen_ingredients.businessobjects.IShoppingList;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.IShoppingListItem;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.IShoppingListItemFactory;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.ShoppingList;
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

	private final IngredientsApplication app;

	private static final String TAG = SmartKitchenData.class.getSimpleName();

	private static final int DATABASE_VERSION = 7;
	private static final String DATABASE_NAME = "shoppingDB.db";

	public static final String TABLE_SHOPPING = "shopping_table";
	public static final String COLUMN_ID = "id";
	public static final String COLUMN_INGREDIENT = "ingredient";
	public static final String COLUMN_AMOUNT = "amount";
	public static final String COLUMN_UNIT = "unit";
	public static final String COLUMN_BOUGHT = "bourght";
	public static final String COLUMN_SHOPPING_LIST_NAME = "title";

	public static final String TABLE_STORED = "stored_table";

	private static final String SHOPPING_TABLE_CREATE = "create table "
			+ TABLE_SHOPPING + " (" + COLUMN_ID
			+ " integer primary key autoincrement, "
			+ COLUMN_SHOPPING_LIST_NAME + " text, " + COLUMN_INGREDIENT
			+ " text, " + COLUMN_AMOUNT + " interger, " + COLUMN_UNIT
			+ " text, " + COLUMN_BOUGHT + " text)";

	private static final String STORED_TABLE_CREATE = "create table "
			+ TABLE_STORED + " (" + COLUMN_ID
			+ " integer primary key autoincrement, " + COLUMN_INGREDIENT
			+ " text, " + COLUMN_AMOUNT + " interger, " + COLUMN_UNIT
			+ " text)";

	public static final String TABLE_SHOPPING_LIST = "shopping_list_table";

	private static final String TABLE_SHOPPING_LIST_CREATE = "create table "
			+ TABLE_SHOPPING_LIST + " (" + COLUMN_SHOPPING_LIST_NAME + " text)";

	public SmartKitchenData(IngredientsApplication app) {
		super(app.getApplicationContext(), DATABASE_NAME, null,
				DATABASE_VERSION);
		this.app = app;
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(SHOPPING_TABLE_CREATE);
		database.execSQL(STORED_TABLE_CREATE);
		database.execSQL(TABLE_SHOPPING_LIST_CREATE);
		Log.i(TAG, "shoppingDB created");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
				+ newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_SHOPPING);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_STORED);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_SHOPPING_LIST);
		onCreate(db);
	}

	@Override
	public List<IShoppingListItem> getAllShoppingItems(
			String currentShoppingListName) {
		SQLiteDatabase db = getReadableDatabase();
		try {

			List<IShoppingListItem> values = new ArrayList<IShoppingListItem>();
			Cursor cursor = db.query(TABLE_SHOPPING, new String[] {
					COLUMN_INGREDIENT, COLUMN_AMOUNT, COLUMN_UNIT,
					COLUMN_BOUGHT }, COLUMN_SHOPPING_LIST_NAME + " = '"
					+ currentShoppingListName + "'", null, null, null, null);
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

	@Override
	public List<IShoppingList> getAllShoppingLists() {
		SQLiteDatabase db = getReadableDatabase();
		try {
			List<IShoppingList> values = new ArrayList<IShoppingList>();
			Cursor cursor = db.query(TABLE_SHOPPING_LIST,
					new String[] { COLUMN_SHOPPING_LIST_NAME }, null, null,
					null, null, null);
			try {
				while (cursor.moveToNext()) {
					values.add(getShoppingListName(cursor));
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
		int quantity = cursor.getInt(1);
		Unit unit = Unit.valueOfFromShortening(cursor.getString(2));
		boolean bought = Boolean.valueOf(cursor.getString(3));
		IShoppingListItemFactory factory = app.getShoppingListItemFactory();
		return factory.createShoppingListItem(title, quantity, unit, bought);
	}

	private IShoppingList getShoppingListName(Cursor cursor) {
		String title = cursor.getString(0);
		IShoppingList list = new ShoppingList(title);
		return list;
	}

	@Override
	public int updateShoppingItem(IShoppingListItem item) {
		ContentValues value = new ContentValues();
		value.put(COLUMN_BOUGHT, String.valueOf(item.isBought()));
		value.put(COLUMN_AMOUNT, item.getQuantity() + getQuantityShopping(item));
		SQLiteDatabase writableDatabase = getWritableDatabase();
		int updatedRows = writableDatabase.update(TABLE_SHOPPING, value,
				COLUMN_INGREDIENT + " = '" + item.getName() + "'", null);
		writableDatabase.close();
		Log.i(TAG, "shopping_table updated");
		return updatedRows;
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
		Unit unit = Unit.valueOfFromShortening(cursor.getString(2));
		IIngredientFactory factory = app.getIngredientFactory();
		return factory.createIngredient(title, unit);
	}

	@Override
	public void insertOrUpdateIngredient(IIngredient boughtIngredient,
			int quantity) {
		int savedQuantity = getSavedQuantityOfIngredient(boughtIngredient);
		ContentValues values = new ContentValues();
		values.put(COLUMN_INGREDIENT, boughtIngredient.getName());
		values.put(COLUMN_AMOUNT, quantity + savedQuantity);
		values.put(COLUMN_UNIT, boughtIngredient.getUnit().toString());

		SQLiteDatabase writableDatabase = getWritableDatabase();
		int updatedRows = writableDatabase.update(TABLE_STORED, values,
				COLUMN_INGREDIENT + " = '" + boughtIngredient.getName() + "'",
				null);
		if (updatedRows == 0) {
			writableDatabase.insertOrThrow(TABLE_STORED, null, values);
		}
		writableDatabase.close();
		Log.i(TAG, "inserted into stored_table");
	}

	private int getSavedQuantityOfIngredient(IIngredient ingredient) {
		int savedQuantity = 0;

		SQLiteDatabase db = getReadableDatabase();
		Cursor cursor = db.query(TABLE_STORED, new String[] { COLUMN_AMOUNT },
				COLUMN_INGREDIENT + " = '" + ingredient.getName() + "'", null,
				null, null, null);
		if (cursor.moveToNext()) {
			savedQuantity = cursor.getInt(0);
		}
		cursor.close();
		db.close();
		return savedQuantity;
	}

	@Override
	public IIngredient getStoredIngredient(String title) {
		SQLiteDatabase db = getReadableDatabase();
		Cursor cursor = db.query(STORED_TABLE_CREATE,
				new String[] { COLUMN_UNIT }, COLUMN_INGREDIENT + "=" + "'"
						+ title + "'", null, null, null, null);

		cursor.moveToNext();
		Unit unit = Unit.valueOf(cursor.getString(0));
		IIngredient ingredient = app.getIngredientFactory().createIngredient(
				title, unit);
		cursor.close();
		db.close();

		return ingredient;

	}

	private boolean insertItemsIntoDatabase(
			List<IShoppingListItem> shoppingItemList) {
		boolean success = false;
		for (IShoppingListItem shoppingItem : shoppingItemList) {
			if (updateShoppingItem(shoppingItem) == 0) {
				ContentValues values = new ContentValues();
				values.put(COLUMN_INGREDIENT, shoppingItem.getName());
				values.put(COLUMN_AMOUNT, shoppingItem.getQuantity());
				values.put(COLUMN_UNIT, shoppingItem.getUnit().toString());
				values.put(COLUMN_BOUGHT, String.valueOf(false));

				SQLiteDatabase writableDatabase = getWritableDatabase();
				try {
					writableDatabase
							.insertOrThrow(TABLE_SHOPPING, null, values);
					Log.i(TAG, "inserted to shopping_table");

				} catch (SQLiteException sqle) {
					success = false;
					Log.i(TAG, "error while insert into shopping_table");
				} finally {
					writableDatabase.close();
				}
			}
		}
		return success;
	}

	private boolean insertShoppingListIntoDatabase(
			IShoppingList shoppingListName) {
		boolean success = false;

		ContentValues values = new ContentValues();
		values.put(COLUMN_SHOPPING_LIST_NAME, shoppingListName.getName());
		SQLiteDatabase writableDatabase = getWritableDatabase();
		try {
			writableDatabase.insertOrThrow(TABLE_SHOPPING_LIST, null, values);
			Log.i(TAG, "inserted to table_shopping_list");

		} catch (SQLiteException sqle) {
			success = false;
			Log.i(TAG, "error while insert into table_shopping_list");
		} finally {
			writableDatabase.close();

		}
		return success;
	}

	@Override
	public boolean addItem(IIngredient ingredient, int quantity) {
		List<IShoppingListItem> shoppingItemList = new ArrayList<IShoppingListItem>();
		IShoppingListItem shoppingListItem = app.getShoppingListItemFactory()
				.createShoppingListItem(ingredient.getName(), quantity,
						ingredient.getUnit(), false);
		shoppingItemList.add(shoppingListItem);

		return insertItemsIntoDatabase(shoppingItemList);
	}

	@Override
	public boolean addItem(IShoppingList shoppingList) {

		return insertShoppingListIntoDatabase(shoppingList);
	}

	@Override
	public boolean addItem(IRecipe recipe, int quantity) {
		List<IShoppingListItem> shoppingItemList = new ArrayList<IShoppingListItem>();
		for (IIngredient ingredient : recipe.getIngredients().keySet()) {
			IShoppingListItem shoppingListItem = app
					.getShoppingListItemFactory().createShoppingListItem(
							ingredient.getName(),
							recipe.getIngredients().get(ingredient) * quantity,
							ingredient.getUnit(), false);
			shoppingItemList.add(shoppingListItem);
		}

		return insertItemsIntoDatabase(shoppingItemList);
	}

	private int getQuantityShopping(IIngredient item) {
		int quantity = 0;
		SQLiteDatabase db = getReadableDatabase();
		Cursor cursor = db.query(TABLE_SHOPPING,
				new String[] { COLUMN_AMOUNT }, COLUMN_INGREDIENT + " = '"
						+ item.getName() + "'", null, null, null, null);
		if (cursor.moveToNext()) {
			quantity = cursor.getInt(0);
		}
		cursor.close();
		db.close();
		return quantity;
	}

	@Override
	public int getQuantity(IIngredient item) {
		int quantity = 0;
		SQLiteDatabase db = getReadableDatabase();
		Cursor cursor = db.query(TABLE_STORED, new String[] { COLUMN_AMOUNT },
				COLUMN_INGREDIENT + " = '" + item.getName() + "'", null, null,
				null, null);
		if (cursor.moveToNext()) {
			quantity = cursor.getInt(0);
		}
		cursor.close();
		db.close();
		return quantity;
	}

}
