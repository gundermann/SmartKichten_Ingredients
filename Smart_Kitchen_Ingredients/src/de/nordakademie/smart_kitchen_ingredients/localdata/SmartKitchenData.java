package de.nordakademie.smart_kitchen_ingredients.localdata;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;
import de.nordakademie.smart_kitchen_ingredients.IngredientsApplication;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.IIngredient;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.IIngredientFactory;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.IRecipe;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.IShoppingListItem;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.IShoppingListItemFactory;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.Unit;
import de.nordakademie.smart_kitchen_ingredients.localdata.tables.ShoppingTable;
import de.nordakademie.smart_kitchen_ingredients.localdata.tables.StoredTable;

/**
 * Verarbeitung der Datenbankanfragen bzgl. der Einkaufsliste und der
 * Bestehenden Zutaten.
 * 
 * @author niels
 * 
 */
public class SmartKitchenData extends AbstractData implements IShoppingData,
		IStoredData {

	private static final String TAG = SmartKitchenData.class.getSimpleName();
	private static final int DATABASE_VERSION = 13;
	private static final String DATABASE_NAME = "smartkitchen.db";

	public SmartKitchenData(IngredientsApplication app) {
		super(app, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(ShoppingTable.getTableCreation());
		database.execSQL(StoredTable.getTableCreation());
		Log.i(TAG, "shoppingDB created");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
				+ newVersion + ", which will destroy all old data");
		db.execSQL(ShoppingTable.getDrop());
		db.execSQL(StoredTable.getDrop());
		onCreate(db);
	}

	@Override
	public List<IShoppingListItem> getAllShoppingItems() {
		openResoures();
		List<IShoppingListItem> values = new ArrayList<IShoppingListItem>();
		setCursor(ShoppingTable.TABLE_NAME, ShoppingTable.selectAllButId());
		while (cursor.moveToNext()) {
			values.add(getShoppingItem());
		}
		closeResources();
		return values;
	}

	private IShoppingListItem getShoppingItem() {
		Unit unit = Unit.valueOfFromShortening(cursor.getString(2));
		boolean bought = Boolean.valueOf(cursor.getString(3));
		IShoppingListItemFactory factory = app.getShoppingListItemFactory();
		return factory.createShoppingListItem(cursor.getString(0),
				cursor.getInt(1), unit, bought);
	}

	@Override
	public int updateShoppingItem(IShoppingListItem item) {
		ContentValues value = ShoppingTable.getContentValuesForQuantityBought(
				item.getQuantity(), item.isBought());
		openResoures();
		int updatedRows = writeableDb.update(ShoppingTable.TABLE_NAME, value,
				ShoppingTable.NAME + " = '" + item.getName() + "'", null);
		closeResources();
		Log.i(TAG, "shopping_table updated");
		return updatedRows;
	}

	@Override
	public void cleanShoppingIngredients() {
		openResoures();
		writeableDb.delete(ShoppingTable.TABLE_NAME, ShoppingTable.BOUGHT
				+ " = '" + String.valueOf(true) + "'", null);
		closeResources();
	}

	@Override
	public IShoppingListItem getShoppingItem(String title) {
		openResoures();
		setCursor(ShoppingTable.TABLE_NAME, ShoppingTable.selectAllButId(),
				getWhere(ShoppingTable.NAME, title));
		cursor.moveToNext();
		IShoppingListItem item = getShoppingItem();
		closeResources();
		return item;
	}

	@Override
	public List<IIngredient> getAllStoredIngredients() {
		openResoures();
		List<IIngredient> values = new ArrayList<IIngredient>();
		setCursor(StoredTable.TABLE_NAME, StoredTable.selectNameQuantityUnit());
		while (cursor.moveToNext()) {
			values.add(getStoredItem());
		}
		closeResources();
		return values;
	}

	private IIngredient getStoredItem() {
		Unit unit = Unit.valueOfFromShortening(cursor.getString(2));
		IIngredientFactory factory = app.getIngredientFactory();
		return factory.createIngredient(cursor.getString(0), unit);
	}

	@Override
	public void insertOrUpdateIngredient(IIngredient boughtIngredient,
			int quantity) {
		int savedQuantity = getSavedQuantityOfIngredient(boughtIngredient);
		ContentValues values = StoredTable.getContentValuesForNameQuantityUnit(
				boughtIngredient, quantity + savedQuantity);

		SQLiteDatabase writableDatabase = getWritableDatabase();
		int updatedRows = writableDatabase.update(StoredTable.TABLE_NAME,
				values, StoredTable.NAME + " = '" + boughtIngredient.getName()
						+ "'", null);
		if (updatedRows == 0) {
			writableDatabase
					.insertOrThrow(StoredTable.TABLE_NAME, null, values);
		}
		writableDatabase.close();
		Log.i(TAG, "inserted into stored_table");
	}

	private int getSavedQuantityOfIngredient(IIngredient ingredient) {
		int savedQuantity = 0;
		openResoures();
		setCursor(StoredTable.TABLE_NAME, StoredTable.selectQuantity(),
				getWhere(StoredTable.NAME, ingredient.getName()));
		if (cursor.moveToNext()) {
			savedQuantity = cursor.getInt(0);
		}
		closeResources();
		return savedQuantity;
	}

	@Override
	public IIngredient getStoredIngredient(String title) {
		openResoures();
		setCursor(StoredTable.TABLE_NAME, StoredTable.selectUnit(),
				getWhere(StoredTable.NAME, title));
		cursor.moveToNext();
		Unit unit = Unit.valueOfFromShortening(cursor.getString(0));
		IIngredient ingredient = app.getIngredientFactory().createIngredient(
				title, unit);
		closeResources();
		return ingredient;

	}

	private boolean insertItemsIntoDatabase(
			List<IShoppingListItem> shoppingItemList) {
		boolean success = false;
		for (IShoppingListItem shoppingItem : shoppingItemList) {
			if (updateShoppingItem(shoppingItem) == 0) {
				ContentValues values = ShoppingTable
						.getContentValuesForAllButId(shoppingItem);
				openResoures();
				try {
					writeableDb.insertOrThrow(ShoppingTable.TABLE_NAME, null,
							values);
					Log.i(TAG, "inserted to shopping_table");
				} catch (SQLiteException sqle) {
					success = false;
					Log.i(TAG, "error while insert into shopping_table");
				} finally {
					closeResources();
				}
			}
		}
		return success;
	}

	@Override
	public boolean addItem(IIngredient ingredient, int quantity) {
		List<IShoppingListItem> shoppingItemList = new ArrayList<IShoppingListItem>();
		IShoppingListItem shoppingListItem = app.getShoppingListItemFactory()
				.createShoppingListItem(ingredient.getName(),
						quantity + getQuantityShopping(ingredient),
						ingredient.getUnit(), false);
		shoppingItemList.add(shoppingListItem);

		return insertItemsIntoDatabase(shoppingItemList);
	}

	@Override
	public boolean addItem(IRecipe recipe, int quantity) {
		List<IShoppingListItem> shoppingItemList = new ArrayList<IShoppingListItem>();
		for (IIngredient ingredient : recipe.getIngredients().keySet()) {
			IShoppingListItem shoppingListItem = app
					.getShoppingListItemFactory().createShoppingListItem(
							ingredient.getName(),
							recipe.getIngredients().get(ingredient) * quantity
									+ getQuantityShopping(ingredient),
							ingredient.getUnit(), false);
			shoppingItemList.add(shoppingListItem);
		}

		return insertItemsIntoDatabase(shoppingItemList);
	}

	@Override
	public int getQuantityShopping(IIngredient item) {
		int quantity = 0;
		openResoures();
		setCursor(ShoppingTable.TABLE_NAME, ShoppingTable.selectQuantity(),
				getWhere(ShoppingTable.NAME, item.getName()));
		if (cursor.moveToNext()) {
			quantity = cursor.getInt(0);
		}
		closeResources();
		return quantity;
	}

	@Override
	public int getQuantity(IIngredient item) {
		int quantity = 0;
		openResoures();
		setCursor(StoredTable.TABLE_NAME, StoredTable.selectQuantity(),
				getWhere(StoredTable.NAME, item.getName()));
		if (cursor.moveToNext()) {
			quantity = cursor.getInt(0);
		}
		closeResources();
		return quantity;
	}

	@Override
	public void deleteAllShoppingItems() {
		SQLiteDatabase db = getWritableDatabase();
		db.delete(ShoppingTable.TABLE_NAME, null, null);
		db.close();
	}
}
