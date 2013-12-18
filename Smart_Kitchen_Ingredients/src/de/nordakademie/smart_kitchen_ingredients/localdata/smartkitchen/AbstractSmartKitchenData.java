package de.nordakademie.smart_kitchen_ingredients.localdata.smartkitchen;

import java.util.List;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import de.nordakademie.smart_kitchen_ingredients.IngredientsApplication;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.IIngredient;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.IIngredientFactory;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.IShoppingList;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.IShoppingListItem;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.IShoppingListItemFactory;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.ShoppingList;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.Unit;
import de.nordakademie.smart_kitchen_ingredients.localdata.AbstractData;
import de.nordakademie.smart_kitchen_ingredients.localdata.smartkitchen.tables.ShoppingListTable;
import de.nordakademie.smart_kitchen_ingredients.localdata.smartkitchen.tables.ShoppingTable;
import de.nordakademie.smart_kitchen_ingredients.localdata.smartkitchen.tables.StoredTable;

/**
 * Verarbeitung der Datenbankanfragen bzgl. der Einkaufsliste und der
 * Bestehenden Zutaten.
 * 
 * @author niels
 * 
 */
public class AbstractSmartKitchenData extends AbstractData {

	protected final IngredientsApplication app;

	protected static String TAG = AbstractSmartKitchenData.class
			.getSimpleName();
	private static final int DATABASE_VERSION = 16;
	private static final String DATABASE_NAME = "smartkitchen.db";

	public AbstractSmartKitchenData(IngredientsApplication app) {
		super(app, DATABASE_NAME, null, DATABASE_VERSION);
		this.app = app;
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(ShoppingTable.getTableCreation());
		database.execSQL(StoredTable.getTableCreation());
		database.execSQL(ShoppingListTable.getTableCreation());
		Log.i(TAG, "shoppingDB created");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
				+ newVersion + ", which will destroy all old data");
		db.execSQL(ShoppingTable.getDrop());
		db.execSQL(StoredTable.getDrop());
		db.execSQL(ShoppingListTable.getDrop());
		onCreate(db);
	}

	protected IShoppingListItem getShoppingItem() {
		Unit unit = Unit.valueOfFromShortening(cursor.getString(2));
		boolean bought = Boolean.valueOf(cursor.getString(3));
		IShoppingListItemFactory factory = app.getShoppingListItemFactory();
		return factory.createShoppingListItem(cursor.getString(0),
				cursor.getInt(1), unit, bought);
	}

	protected IShoppingList getShoppingListName() {
		String title = cursor.getString(0);
		IShoppingList list = new ShoppingList(title);
		return list;
	}

	protected IIngredient getStoredItem() {
		Unit unit = Unit.valueOfFromShortening(cursor.getString(2));
		IIngredientFactory factory = app.getIngredientFactory();
		return factory.createIngredient(cursor.getString(0), unit);
	}

	protected int updateShoppingItem(IShoppingListItem item) {
		ContentValues value = ShoppingTable.getContentValuesForQuantityBought(
				item.getQuantity(), item.isBought());
		int updatedRows = update(ShoppingTable.TABLE_NAME, value,
				getWhere(ShoppingTable.NAME, item.getName()));
		Log.i(TAG, "shopping_table updated");
		return updatedRows;
	}

	protected boolean insertItemsIntoDatabase(
			List<IShoppingListItem> shoppingItemList, String shoppingList) {
		for (IShoppingListItem shoppingItem : shoppingItemList) {
			if (updateShoppingItem(shoppingItem) == 0) {
				ContentValues values = ShoppingTable
						.getContentValuesForAllButId(shoppingItem, shoppingList);
				if (!insert(ShoppingTable.TABLE_NAME, values)) {
					return false;
				}
			}
		}
		return true;
	}

	protected boolean insertShoppingListIntoDatabase(
			IShoppingList shoppingListName) {
		ContentValues values = ShoppingListTable
				.getContentValues(shoppingListName.getName());
		boolean success = insert(ShoppingListTable.TABLE_NAME, values);
		Log.i(TAG, "inserted to table_shopping_list");
		return success;
	}

}
