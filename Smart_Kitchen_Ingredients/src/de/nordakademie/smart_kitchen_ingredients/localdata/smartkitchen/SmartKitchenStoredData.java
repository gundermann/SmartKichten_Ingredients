package de.nordakademie.smart_kitchen_ingredients.localdata.smartkitchen;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.util.Log;
import de.nordakademie.smart_kitchen_ingredients.IngredientsApplication;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.IIngredient;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.Unit;
import de.nordakademie.smart_kitchen_ingredients.localdata.smartkitchen.tables.StoredTable;

public class SmartKitchenStoredData extends AbstractSmartKitchenData implements
		IStoredDbHelper {

	public SmartKitchenStoredData(IngredientsApplication app) {
		super(app);
		TAG = SmartKitchenStoredData.class.getSimpleName();
	}

	@Override
	public void insertOrUpdateIngredient(IIngredient boughtIngredient,
			int quantity) {
		int savedQuantity = getSavedQuantityOfIngredient(boughtIngredient);
		ContentValues values = StoredTable.getContentValuesForNameQuantityUnit(
				boughtIngredient, quantity + savedQuantity);
		if (update(StoredTable.TABLE_NAME, values,
				getWhere(StoredTable.NAME, boughtIngredient.getName())) == 0) {
			insert(StoredTable.TABLE_NAME, values);
			Log.i(TAG, "inserted into stored_table");
		}
	}

	protected int getSavedQuantityOfIngredient(IIngredient ingredient) {
		int savedQuantity = 0;
		openCursorResoures();
		setCursor(StoredTable.TABLE_NAME, StoredTable.selectQuantity(),
				getWhere(StoredTable.NAME, ingredient.getName()));
		if (cursor.moveToNext()) {
			savedQuantity = cursor.getInt(0);
		}
		closeCursorResources();
		return savedQuantity;
	}

	@Override
	public IIngredient getStoredIngredient(String title) {
		openCursorResoures();
		setCursor(StoredTable.TABLE_NAME, StoredTable.selectUnit(),
				getWhere(StoredTable.NAME, title));
		cursor.moveToNext();
		Unit unit = Unit.valueOfFromShortening(cursor.getString(0));
		IIngredient ingredient = app.getIngredientFactory().createIngredient(
				title, unit);
		closeCursorResources();
		return ingredient;

	}

	@Override
	public int getQuantity(IIngredient item) {
		int quantity = 0;
		openCursorResoures();
		setCursor(StoredTable.TABLE_NAME, StoredTable.selectQuantity(),
				getWhere(StoredTable.NAME, item.getName()));
		if (cursor.moveToNext()) {
			quantity = cursor.getInt(0);
		}
		closeCursorResources();
		return quantity;
	}

	@Override
	public void deleteStoredIngredient(String title) {
		delete(StoredTable.TABLE_NAME, getWhere(StoredTable.NAME, title));
	}

	@Override
	public List<IIngredient> getAllStoredIngredients() {
		openCursorResoures();
		List<IIngredient> values = new ArrayList<IIngredient>();
		setCursor(StoredTable.TABLE_NAME, StoredTable.selectNameQuantityUnit());
		while (cursor.moveToNext()) {
			values.add(getStoredItem());
		}
		closeCursorResources();
		return values;
	}

}
