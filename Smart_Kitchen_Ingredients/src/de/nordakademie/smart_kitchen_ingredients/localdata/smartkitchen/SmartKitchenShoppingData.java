package de.nordakademie.smart_kitchen_ingredients.localdata.smartkitchen;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import de.nordakademie.smart_kitchen_ingredients.IngredientsApplication;
import de.nordakademie.smart_kitchen_ingredients.ShoppingListItemFactory;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.IIngredient;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.IRecipe;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.IShoppingList;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.IShoppingListItem;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.ShoppingList;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.Unit;
import de.nordakademie.smart_kitchen_ingredients.localdata.smartkitchen.tables.ShoppingListTable;
import de.nordakademie.smart_kitchen_ingredients.localdata.smartkitchen.tables.ShoppingTable;

public class SmartKitchenShoppingData extends AbstractSmartKitchenData
		implements IShoppingDbHelper {

	public SmartKitchenShoppingData(IngredientsApplication app) {
		super(app);
		TAG = SmartKitchenShoppingData.class.getSimpleName();
	}

	@Override
	public List<IShoppingListItem> getAllShoppingItems(String shoppingList) {
		openCursorResoures();
		List<IShoppingListItem> values = new ArrayList<IShoppingListItem>();
		setCursor(ShoppingTable.TABLE_NAME, ShoppingTable.selectAllButId(),
				getWhere(ShoppingTable.SHOPPING_LIST, shoppingList));
		while (cursor.moveToNext()) {
			values.add(getShoppingItem());
		}
		closeCursorResources();
		return values;
	}

	@Override
	public int updateShoppingItem(IShoppingListItem item, String shoppinglist) {
		return super.updateShoppingItem(item, shoppinglist);
	}

	@Override
	public void cleanShoppingIngredients() {
		delete(ShoppingTable.TABLE_NAME, getWhere(ShoppingTable.BOUGHT, true));
	}

	@Override
	public IShoppingListItem getShoppingItem(String title, String shoppingList) {
		openCursorResoures();
		setCursor(
				ShoppingTable.TABLE_NAME,
				ShoppingTable.selectAllButId(),
				getWhere(ShoppingTable.selectNameAndShoppinglist(),
						new String[] { title, shoppingList }));
		IShoppingListItem item = null;
		if (cursor.moveToNext()) {
			item = getShoppingItem();
		}
		closeCursorResources();
		return item;
	}

	@Override
	public boolean addItem(IIngredient ingredient, int quantity,
			String shoppingList) {
		List<IShoppingListItem> shoppingItemList = new ArrayList<IShoppingListItem>();
		IShoppingListItem shoppingListItem = ShoppingListItemFactory
				.createShoppingListItem(ingredient.getName(), quantity
						+ getQuantityShopping(ingredient),
						ingredient.getUnit(), false);
		shoppingItemList.add(shoppingListItem);

		return insertItemsIntoDatabase(shoppingItemList, shoppingList);
	}

	@Override
	public boolean addItem(IShoppingList shoppingList) {
		return insertShoppingListIntoDatabase(shoppingList);
	}

	@Override
	public boolean addItem(IRecipe recipe, int quantity, String shoppingList) {
		List<IShoppingListItem> shoppingItemList = new ArrayList<IShoppingListItem>();
		for (IIngredient ingredient : recipe.getIngredients().keySet()) {
			IShoppingListItem shoppingListItem = ShoppingListItemFactory
					.createShoppingListItem(ingredient.getName(), recipe
							.getIngredients().get(ingredient)
							* quantity
							+ getQuantityShopping(ingredient), ingredient
							.getUnit(), false);
			shoppingItemList.add(shoppingListItem);
		}
		return insertItemsIntoDatabase(shoppingItemList, shoppingList);
	}

	@Override
	public int getQuantityShopping(IIngredient item) {
		int quantity = 0;
		openCursorResoures();
		setCursor(ShoppingTable.TABLE_NAME, ShoppingTable.selectQuantity(),
				getWhere(ShoppingTable.NAME, item.getName()));
		if (cursor.moveToNext()) {
			quantity = cursor.getInt(0);
		}
		closeCursorResources();
		return quantity;
	}

	@Override
	public void deleteAllShoppingItems() {
		delete(ShoppingTable.TABLE_NAME);
	}

	@Override
	public void delete(IShoppingList shoppingList) {
		SQLiteDatabase db = getWritableDatabase();
		db.delete(ShoppingListTable.TABLE_NAME,
				getWhere(ShoppingListTable.NAME, shoppingList.getName()), null);
		db.delete(ShoppingTable.TABLE_NAME,
				getWhere(ShoppingTable.SHOPPING_LIST, shoppingList.getName()),
				null);
		db.close();

	}

	@Override
	public List<IShoppingList> getAllShoppingLists() {
		openCursorResoures();
		List<IShoppingList> values = new ArrayList<IShoppingList>();
		setCursor(ShoppingListTable.TABLE_NAME, ShoppingListTable.selectName());
		while (cursor.moveToNext()) {
			values.add(getShoppingListName());
		}
		closeCursorResources();
		return values;
	}

	private IShoppingListItem getShoppingItem() {
		Unit unit = Unit.valueOfFromShortening(cursor.getString(2));
		boolean bought = Boolean.valueOf(cursor.getString(3));
		return ShoppingListItemFactory.createShoppingListItem(
				cursor.getString(0), cursor.getInt(1), unit, bought);
	}

	private IShoppingList getShoppingListName() {
		String title = cursor.getString(0);
		IShoppingList list = new ShoppingList(title);
		return list;
	}

	private boolean insertShoppingListIntoDatabase(
			IShoppingList shoppingListName) {
		ContentValues values = ShoppingListTable
				.getContentValues(shoppingListName.getName());
		boolean success = insert(ShoppingListTable.TABLE_NAME, values);
		Log.i(TAG, "inserted to table_shopping_list");
		return success;
	}

	@Override
	public IShoppingListItem getShoppingItem(String itemTitle) {
		openCursorResoures();
		setCursor(ShoppingTable.TABLE_NAME, ShoppingTable.selectAllButId(),
				getWhere(ShoppingTable.NAME, itemTitle));
		IShoppingListItem item = null;
		if (cursor.moveToNext()) {
			item = getShoppingItem();
		}
		closeCursorResources();
		return item;
	}
}
