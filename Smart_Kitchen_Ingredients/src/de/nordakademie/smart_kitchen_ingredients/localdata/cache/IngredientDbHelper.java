package de.nordakademie.smart_kitchen_ingredients.localdata.cache;

import java.util.List;

import de.nordakademie.smart_kitchen_ingredients.IngredientsApplication;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.IIngredient;

/**
 * 
 * @author niels
 * 
 */
public class IngredientDbHelper implements ICacheDbHelper<IIngredient> {

	private CacheData db;

	public IngredientDbHelper(IngredientsApplication app) {
		db = new CacheData(app);
	}

	@Override
	public List<IIngredient> getDatabaseEntries() {
		return db.getAllIngredients();
	}

	@Override
	public IIngredient getExplicitItem(String title) {
		return db.getIngredientByTitle(title);
	}
}
