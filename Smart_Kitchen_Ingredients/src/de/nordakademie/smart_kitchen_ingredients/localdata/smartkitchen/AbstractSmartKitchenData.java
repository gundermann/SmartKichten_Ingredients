package de.nordakademie.smart_kitchen_ingredients.localdata.smartkitchen;

import java.util.List;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import de.nordakademie.smart_kitchen_ingredients.IngredientsApplication;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.IShoppingListItem;
import de.nordakademie.smart_kitchen_ingredients.localdata.AbstractData;
import de.nordakademie.smart_kitchen_ingredients.localdata.smartkitchen.tables.DateTable;
import de.nordakademie.smart_kitchen_ingredients.localdata.smartkitchen.tables.ShoppingListTable;
import de.nordakademie.smart_kitchen_ingredients.localdata.smartkitchen.tables.ShoppingTable;
import de.nordakademie.smart_kitchen_ingredients.localdata.smartkitchen.tables.StoredTable;

/**
 * Verarbeitung der Datenbankanfragen bzgl. der Einkaufsliste und der
 * Bestehenden Zutaten.
 * 
 * @author Niels Gundermann
 * 
 */
public abstract class AbstractSmartKitchenData extends AbstractData {

	protected final IngredientsApplication app;

	protected static String TAG = AbstractSmartKitchenData.class
			.getSimpleName();
	private static final int DATABASE_VERSION = 18;
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
		database.execSQL(DateTable.getTableCreation());
		Log.i(TAG, "shoppingDB created");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
				+ newVersion + ", which will destroy all old data");
		db.execSQL(ShoppingTable.getDrop());
		db.execSQL(StoredTable.getDrop());
		db.execSQL(ShoppingListTable.getDrop());
		db.execSQL(DateTable.getDrop());
		onCreate(db);
	}

	protected int updateShoppingItem(IShoppingListItem item, String shoppinglist) {
		ContentValues value = ShoppingTable.getContentValuesForQuantityBought(
				item.getQuantity(), item.isBought());
		int updatedRows = update(
				ShoppingTable.TABLE_NAME,
				value,
				getWhere(ShoppingTable.selectNameAndShoppinglist(),
						new String[] { item.getName(), shoppinglist }));
		Log.i(TAG, "shopping_table updated");
		return updatedRows;
	}

	protected boolean insertItemsIntoDatabase(
			List<IShoppingListItem> shoppingItemList, String shoppingList) {
		for (IShoppingListItem shoppingItem : shoppingItemList) {
			if (updateShoppingItem(shoppingItem, shoppingList) == 0) {
				ContentValues values = ShoppingTable
						.getContentValuesForAllButId(shoppingItem, shoppingList);
				if (!insert(ShoppingTable.TABLE_NAME, values)) {
					return false;
				}
			}
		}
		return true;
	}

}
