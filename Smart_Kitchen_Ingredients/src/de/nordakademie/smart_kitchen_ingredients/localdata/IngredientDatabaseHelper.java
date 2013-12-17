package de.nordakademie.smart_kitchen_ingredients.localdata;

import java.util.List;

import de.nordakademie.smart_kitchen_ingredients.IngredientsApplication;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.IIngredient;

/**
 * 
 * @author niels
 * 
 */
public class IngredientDatabaseHelper implements IDatabaseHelper<IIngredient> {

	private CacheData db;

	public IngredientDatabaseHelper(IngredientsApplication app) {
		db = new CacheData(app);
	}

	@Override
	public List<IIngredient> getDatabaseEntries() {
		return db.getAllIngredients();
	}
}
