package de.nordakademie.smart_kitchen_ingredients.localdata.cache;

/**
 * @author Kathrin Kurtz
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.nordakademie.smart_kitchen_ingredients.IngredientsApplication;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.IIngredient;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.IRecipe;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.Unit;
import de.nordakademie.smart_kitchen_ingredients.factories.IngredientFactory;
import de.nordakademie.smart_kitchen_ingredients.factories.RecipeFactory;
import de.nordakademie.smart_kitchen_ingredients.localdata.cache.tables.IngredientsTable;
import de.nordakademie.smart_kitchen_ingredients.localdata.cache.tables.IngredientsToRecipeTable;
import de.nordakademie.smart_kitchen_ingredients.localdata.cache.tables.RecipesTable;

public class RecipeDbHelper extends AbstractCacheData implements
		ICacheDbHelper<IRecipe> {

	public RecipeDbHelper(IngredientsApplication app) {
		super(app);
		TAG = RecipeDbHelper.class.getSimpleName();
	}

	@Override
	public List<IRecipe> getDatabaseEntries() {
		List<IRecipe> recipes = new ArrayList<IRecipe>();
		updateIfNecessary();
		openCursorResoures();
		setCursor(RecipesTable.TABLE_NAME, RecipesTable.selectAllColunms());
		Map<String, String> recipeMap = new HashMap<String, String>();
		while (cursor.moveToNext()) {
			recipeMap.put(cursor.getString(0), cursor.getString(1));
		}
		for (String id : recipeMap.keySet()) {
			recipes.add(RecipeFactory.createRecipe(recipeMap.get(id), null));
		}
		closeCursorResources();
		return recipes;
	}

	@Override
	public IRecipe getExplicitItem(String title) {
		openCursorResoures();
		setCursor(RecipesTable.TABLE_NAME, RecipesTable.selectAllColunms(),
				getWhere(RecipesTable.TITLE, title));
		cursor.moveToNext();
		IRecipe recipe = RecipeFactory.createRecipe(title,
				getIngredientsForRecipeID(cursor.getString(0)));
		closeCursorResources();
		return recipe;
	}

	private Map<IIngredient, Integer> getIngredientsForRecipeID(String recipeID) {
		Map<IIngredient, Integer> ingredientsList = new HashMap<IIngredient, Integer>();
		openCursorResoures();
		setCursor(IngredientsToRecipeTable.TABLE_NAME,
				IngredientsToRecipeTable.selectIngredientIdAndQuantity(),
				getWhere(IngredientsToRecipeTable.RECIPE_ID, recipeID));

		Map<String, Integer> quantityMap = new HashMap<String, Integer>();
		while (cursor.moveToNext()) {
			quantityMap.put(cursor.getString(0), cursor.getInt(1));
		}

		for (String id : quantityMap.keySet()) {
			ingredientsList.put(IngredientFactory.createIngredient(
					getIngredientNameByID(id), getIngredientUnitByID(id)),
					quantityMap.get(id));
		}
		closeCursorResources();
		return ingredientsList;
	}

	private String getIngredientNameByID(String id) {
		setCursor(IngredientsTable.TABLE_NAME,
				IngredientsTable.selectNameColumn(),
				getWhere(IngredientsTable.ID, id));
		cursor.moveToNext();
		String ingredientId = cursor.getString(0);
		return ingredientId;
	}

	private Unit getIngredientUnitByID(String id) {
		setCursor(IngredientsTable.TABLE_NAME,
				IngredientsTable.selectUnitColumn(),
				getWhere(IngredientsTable.ID, id));
		cursor.moveToNext();
		return Unit.valueOfFromShortening(cursor.getString(0));
	}
}
