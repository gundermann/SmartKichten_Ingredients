package de.nordakademie.smart_kitchen_ingredients.localdata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import de.nordakademie.smart_kitchen_ingredients.IngredientsApplication;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.IIngredient;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.IIngredientFactory;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.IRecipe;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.IRecipeFactory;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.Unit;
import de.nordakademie.smart_kitchen_ingredients.localdata.tables.IngredientsTable;
import de.nordakademie.smart_kitchen_ingredients.localdata.tables.IngredientsToRecipeTable;
import de.nordakademie.smart_kitchen_ingredients.localdata.tables.RecipesTable;

/**
 * 
 * @author Kathrin Kurtz
 * 
 */
public class CacheData extends AbstractData implements ICacheData {

	private static final String TAG = CacheData.class.getSimpleName();
	private static final int DATABASE_VERSION = 3;
	private static final String DATABASE_NAME = "cache.db";

	public CacheData(IngredientsApplication application) {
		super(application, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		Log.w(TAG, "Tabellen in der Datenbank angelegt.");
		database.execSQL(RecipesTable.getTableCreation());
		database.execSQL(IngredientsTable.getTableCreation());
		database.execSQL(IngredientsToRecipeTable.getTableCreation());
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
				+ newVersion + ", which will destroy all old data");
		db.execSQL(RecipesTable.getDrop());
		db.execSQL(IngredientsTable.getDrop());
		db.execSQL(IngredientsToRecipeTable.getDrop());
		onCreate(db);
	}

	public List<IRecipe> getAllRecipes() {
		List<IRecipe> recipes = new ArrayList<IRecipe>();
		updateIfNecessary();
		openResoures();
		setCursor(RecipesTable.TABLE_NAME, RecipesTable.selectAllColunms());
		Map<String, String> recipeMap = new HashMap<String, String>();
		while (cursor.moveToNext()) {
			recipeMap.put(cursor.getString(0), cursor.getString(1));
		}
		for (String id : recipeMap.keySet()) {
			// // TODO sehr inperformant --> nur Rezeptnamen der AsyncTask
			// // zurückliefern.
			IRecipeFactory recipeFactory = app.getRecipeFactory();
			recipes.add(recipeFactory.createRecipe(recipeMap.get(id), null));
		}
		closeResources();
		return recipes;
	}

	private void updateIfNecessary() {
		if (isCachedDataAvailable()) {
			app.updateCache();
		}
	}

	private boolean isCachedDataAvailable() {
		if (getReadableDatabase() == null) {
			return false;
		}
		return true;
	}

	private Map<IIngredient, Integer> getIngredientsForRecipeID(String recipeID) {
		Map<IIngredient, Integer> ingredientsList = new HashMap<IIngredient, Integer>();
		IIngredientFactory ingredientFactory = app.getIngredientFactory();
		openResoures();
		setCursor(IngredientsToRecipeTable.TABLE_NAME,
				IngredientsToRecipeTable.selectIngredientIdAndQuantity(),
				getWhere(IngredientsToRecipeTable.RECIPE_ID, recipeID));

		Map<String, Integer> quantityMap = new HashMap<String, Integer>();
		while (cursor.moveToNext()) {
			quantityMap.put(cursor.getString(0), cursor.getInt(1));
		}

		for (String id : quantityMap.keySet()) {
			ingredientsList.put(ingredientFactory.createIngredient(
					getIngredientNameByID(id), getIngredientUnitByID(id)),
					quantityMap.get(id));
		}
		closeResources();
		return ingredientsList;
	}

	private Unit getIngredientUnitByID(String id) {
		setCursor(IngredientsTable.TABLE_NAME,
				IngredientsTable.selectUnitColumn(),
				getWhere(IngredientsTable.ID, id));
		cursor.moveToNext();
		return Unit.valueOfFromShortening(cursor.getString(0));
	}

	private String getIngredientNameByID(String id) {
		setCursor(IngredientsTable.TABLE_NAME,
				IngredientsTable.selectNameColumn(),
				getWhere(IngredientsTable.ID, id));
		cursor.moveToNext();
		String ingredientId = cursor.getString(0);
		return ingredientId;
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

	private IIngredient insertIngredientsForRecipe(String recipeID,
			String[] ingredient) {
		openResoures();
		insert(IngredientsToRecipeTable.TABLE_NAME,
				IngredientsToRecipeTable.getContentValuesForAll(recipeID,
						ingredient));
		closeResources();
		Log.i(TAG, "database of INDIGRENTS_TO_RECIPES updated");

		return app.getIngredientFactory().createIngredient(ingredient[1],
				Unit.valueOfFromShortening(ingredient[2]));
	}

	private void insertRecipe(String id, String title) {
		openResoures();
		insert(RecipesTable.TABLE_NAME,
				RecipesTable.getContentValuesForAll(id, title));
		closeResources();
		Log.i(TAG, "database of RECIPES updated");
	}

	@Override
	public List<IIngredient> insertOrUpdateAllIngredientsFromServer(
			List<String[]> ingredients) {
		List<IIngredient> ingredientList = new ArrayList<IIngredient>();
		openResoures();
		for (String[] ingredient : ingredients) {
			writeIngredientToDB(IngredientsTable
					.getContenValuesForAll(ingredient));
			ingredientList.add(app.getIngredientFactory().createIngredient(
					ingredient[1], Unit.valueOfFromShortening(ingredient[2])));
		}
		closeResources();
		return ingredientList;

	}

	private void writeIngredientToDB(ContentValues values) {
		insert(IngredientsTable.TABLE_NAME, values);
		Log.i(TAG, "database of Indigrents updated");
	}

	public List<IIngredient> getAllIngredients() {
		List<IIngredient> ingredientsList = new ArrayList<IIngredient>();
		updateIfNecessary();
		IIngredientFactory ingredientFactory = app.getIngredientFactory();
		openResoures();
		setCursor(IngredientsTable.TABLE_NAME, IngredientsTable.getAllColunms());
		while (cursor.moveToNext()) {
			ingredientsList.add(ingredientFactory.createIngredient(
					cursor.getString(1),
					Unit.valueOfFromShortening(cursor.getString(2))));
		}
		closeResources();
		return ingredientsList;
	}

	@Override
	public boolean itemExists(String itemTitle) {
		openResoures();
		setCursor(IngredientsTable.TABLE_NAME,
				IngredientsTable.selectNameColumn(),
				getWhere(IngredientsTable.NAME, itemTitle));
		int count = cursor.getCount();
		closeResources();
		return count > 0;
	}
}
