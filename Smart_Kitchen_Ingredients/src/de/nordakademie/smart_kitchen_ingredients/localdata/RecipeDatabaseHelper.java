package de.nordakademie.smart_kitchen_ingredients.localdata;

import java.util.List;

import de.nordakademie.smart_kitchen_ingredients.IngredientsApplication;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.IRecipe;

public class RecipeDatabaseHelper implements IDatabaseHelper<IRecipe> {

	private CacheData db;

	public RecipeDatabaseHelper(IngredientsApplication app) {
		db = new CacheData(app);
	}

	@Override
	public List<IRecipe> getDatabaseEntries() {
		return db.getAllRecipes();
	}

}
