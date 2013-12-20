package de.nordakademie.smart_kitchen_ingredients.localdata.cache;

import java.util.ArrayList;
import java.util.List;

import de.nordakademie.smart_kitchen_ingredients.IngredientsApplication;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.IIngredient;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.Unit;
import de.nordakademie.smart_kitchen_ingredients.factories.IngredientFactory;
import de.nordakademie.smart_kitchen_ingredients.localdata.cache.tables.IngredientsTable;

/**
 * 
 * @author Niels Gundermann
 * 
 */
public class IngredientDbHelper extends AbstractCacheData implements
		IAbstractCacheDbHelper<IIngredient> {

	public IngredientDbHelper(IngredientsApplication app) {
		super(app);
		TAG = IngredientDbHelper.class.getSimpleName();
	}

	@Override
	public List<IIngredient> getDatabaseEntries() {
		List<IIngredient> ingredientsList = new ArrayList<IIngredient>();
		updateIfNecessary();
		openCursorResoures();
		setCursor(IngredientsTable.TABLE_NAME, IngredientsTable.getAllColunms());
		while (cursor.moveToNext()) {
			ingredientsList.add(IngredientFactory.createIngredient(
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
		IIngredient ingredient = IngredientFactory.createIngredient(title,
				Unit.valueOfFromShortening(cursor.getString(0)));
		closeCursorResources();
		return ingredient;
	}
}
