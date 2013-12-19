package de.nordakademie.smart_kitchen_ingredients.localdata.cache;

import java.util.List;

import de.nordakademie.smart_kitchen_ingredients.IngredientsApplication;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.IRecipe;

public class RecipeDbHelper implements ICacheDbHelper<IRecipe> {

	private CacheData db;

	public RecipeDbHelper(IngredientsApplication app) {
		db = new CacheData(app);
	}

	@Override
	public List<IRecipe> getDatabaseEntries() {
		return db.getAllRecipes();
	}

	@Override
	public IRecipe getExplicitItem(String title) {
		return db.getRecipeByTitle(title);
	}

}
