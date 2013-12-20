package de.nordakademie.smart_kitchen_ingredients.localdata.cache;

/**
 * 
 * @author Kathrin Kurtz
 *
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import android.content.ContentValues;
import android.util.Log;
import de.nordakademie.smart_kitchen_ingredients.IngredientsApplication;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.IIngredient;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.IRecipe;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.Unit;
import de.nordakademie.smart_kitchen_ingredients.factories.IngredientFactory;
import de.nordakademie.smart_kitchen_ingredients.localdata.cache.tables.IngredientsTable;
import de.nordakademie.smart_kitchen_ingredients.localdata.cache.tables.IngredientsToRecipeTable;
import de.nordakademie.smart_kitchen_ingredients.localdata.cache.tables.RecipesTable;

public class CacheUpdateDbHelper extends AbstractCacheData implements
		ICacheDbUpdateHelper {

	public CacheUpdateDbHelper(IngredientsApplication application) {
		super(application);
		TAG = CacheUpdateDbHelper.class.getSimpleName();
	}

	@Override
	public List<IRecipe> insertOrUpdateAllRecipesFromServer(
			Map<String[], List<String[]>> recipes) {
		List<IRecipe> recipeList = new ArrayList<IRecipe>();
		Iterator<String[]> iterator = recipes.keySet().iterator();
		while (iterator.hasNext()) {
			String[] currentRecipe = iterator.next();
			insertRecipe(currentRecipe[0], currentRecipe[1]);

			Map<IIngredient, Integer> currentIngredientList = new HashMap<IIngredient, Integer>();
			List<String[]> ingredientList = recipes.get(currentRecipe);
			for (String[] currentRecipeIngredient : ingredientList) {
				IIngredient ingredient = insertIngredientsForRecipe(
						currentRecipe[0], currentRecipeIngredient);
				currentIngredientList.put(ingredient,
						Integer.valueOf(currentRecipeIngredient[3]));
			}
			recipeList.add(app.getRecipeFactory().createRecipe(
					currentRecipe[1], currentIngredientList));
		}

		return recipeList;
	}

	@Override
	public List<IIngredient> insertOrUpdateAllIngredientsFromServer(
			List<String[]> ingredients) {
		List<IIngredient> ingredientList = new ArrayList<IIngredient>();
		for (String[] ingredient : ingredients) {
			writeIngredientToDB(IngredientsTable
					.getContenValuesForAll(ingredient));
			ingredientList.add(IngredientFactory.createIngredient(
					ingredient[1], Unit.valueOfFromShortening(ingredient[2])));
		}
		return ingredientList;

	}

	private void writeIngredientToDB(ContentValues values) {
		insert(IngredientsTable.TABLE_NAME, values);
		Log.i(TAG, "database of Indigrents updated");
	}

	protected IIngredient insertIngredientsForRecipe(String recipeID,
			String[] ingredient) {
		insert(IngredientsToRecipeTable.TABLE_NAME,
				IngredientsToRecipeTable.getContentValuesForAll(recipeID,
						ingredient));
		Log.i(TAG, "database of INDIGRENTS_TO_RECIPES updated");

		return IngredientFactory.createIngredient(ingredient[1],
				Unit.valueOfFromShortening(ingredient[2]));
	}

	@Override
	public boolean itemExists(String itemTitle) {
		openCursorResoures();
		setCursor(IngredientsTable.TABLE_NAME,
				IngredientsTable.selectNameColumn(),
				getWhere(IngredientsTable.NAME, itemTitle));
		int count = cursor.getCount();
		closeCursorResources();
		return count > 0;
	}

	private void insertRecipe(String id, String title) {
		insert(RecipesTable.TABLE_NAME,
				RecipesTable.getContentValuesForAll(id, title));
		Log.i(TAG, "database of RECIPES updated");
	}
}
