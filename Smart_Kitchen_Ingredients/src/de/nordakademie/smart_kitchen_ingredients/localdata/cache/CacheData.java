package de.nordakademie.smart_kitchen_ingredients.localdata.cache;

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
import de.nordakademie.smart_kitchen_ingredients.localdata.AbstractData;
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
		openCursorResoures();
		setCursor(RecipesTable.TABLE_NAME, RecipesTable.selectAllColunms());
		Map<String, String> recipeMap = new HashMap<String, String>();
		while (cursor.moveToNext()) {
			recipeMap.put(cursor.getString(0), cursor.getString(1));
		}
		for (String id : recipeMap.keySet()) {
			// TODO sehr inperformant --> nur Rezeptnamen der AsyncTask
			// zurückliefern. Ist abgeschlossen --> Refactoring im
			// IDatabaseHelper nötig
			IRecipeFactory recipeFactory = app.getRecipeFactory();
			recipes.add(recipeFactory.createRecipe(recipeMap.get(id), null));
		}
		closeCursorResources();
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
		openCursorResoures();
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
		closeCursorResources();
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
		openCursorResoures();
		insert(IngredientsToRecipeTable.TABLE_NAME,
				IngredientsToRecipeTable.getContentValuesForAll(recipeID,
						ingredient));
		closeCursorResources();
		Log.i(TAG, "database of INDIGRENTS_TO_RECIPES updated");

		return app.getIngredientFactory().createIngredient(ingredient[1],
				Unit.valueOfFromShortening(ingredient[2]));
	}

	private void insertRecipe(String id, String title) {
		openCursorResoures();
		insert(RecipesTable.TABLE_NAME,
				RecipesTable.getContentValuesForAll(id, title));
		closeCursorResources();
		Log.i(TAG, "database of RECIPES updated");
	}

	@Override
	public List<IIngredient> insertOrUpdateAllIngredientsFromServer(
			List<String[]> ingredients) {
		List<IIngredient> ingredientList = new ArrayList<IIngredient>();
		openCursorResoures();
		for (String[] ingredient : ingredients) {
			writeIngredientToDB(IngredientsTable
					.getContenValuesForAll(ingredient));
			ingredientList.add(app.getIngredientFactory().createIngredient(
					ingredient[1], Unit.valueOfFromShortening(ingredient[2])));
		}
		closeCursorResources();
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
	public boolean itemExists(String itemTitle) {
		openCursorResoures();
		setCursor(IngredientsTable.TABLE_NAME,
				IngredientsTable.selectNameColumn(),
				getWhere(IngredientsTable.NAME, itemTitle));
		int count = cursor.getCount();
		closeCursorResources();
		return count > 0;
	}

	public IRecipe getRecipeByTitle(String title) {
		openCursorResoures();
		setCursor(RecipesTable.TABLE_NAME, RecipesTable.selectAllColunms(),
				getWhere(RecipesTable.TITLE, title));
		cursor.moveToNext();
		IRecipe recipe = app.getRecipeFactory().createRecipe(title,
				getIngredientsForRecipeID(cursor.getString(0)));
		closeCursorResources();
		return recipe;
	}

	public IIngredient getIngredientByTitle(String title) {
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
