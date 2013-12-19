package de.nordakademie.smart_kitchen_ingredients.localdata.cache;

import java.util.ArrayList;
import java.util.List;

import de.nordakademie.smart_kitchen_ingredients.IngredientsApplication;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.IIngredient;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.IIngredientFactory;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.Unit;
import de.nordakademie.smart_kitchen_ingredients.localdata.cache.tables.IngredientsTable;

/**
 * 
 * @author niels
 * 
 */
public class IngredientDbHelper extends AbstractCacheData implements
		ICacheDbHelper<IIngredient> {

	public IngredientDbHelper(IngredientsApplication app) {
		super(app);
		TAG = IngredientDbHelper.class.getSimpleName();
	}

	@Override
	public List<IIngredient> getDatabaseEntries() {
		List<IIngredient> ingredientsList = new ArrayList<IIngredient>();
		updateIfNecessary();
		IIngredientFactory ingredientFactory = app.getIngredientFactory();
		openCursorResoures();
		setCursor(IngredientsTable.TABLE_NAME, IngredientsTable.getAllColunms());
		while (cursor.moveToNext()) {
			ingredientsList.add(ingredientFactory.createIngredient(
					cursor.getString(1),
					Unit.valueOfFromShortening(cursor.getString(2))));
		}
		closeCursorResources();
		return ingredientsList;
	}

	@Override
	public IIngredient getExplicitItem(String title) {
		openCursorResoures();
		setCursor(IngredientsTable.TABLE_NAME,
				IngredientsTable.selectUnitColumn(),
				getWhere(IngredientsTable.NAME, title));
		cursor.moveToNext();
		IIngredient ingredient = app.getIngredientFactory().createIngredient(
				title, Unit.valueOfFromShortening(cursor.getString(0)));
		closeCursorResources();
		return ingredient;
	}
}
