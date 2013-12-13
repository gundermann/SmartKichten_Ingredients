package de.nordakademie.smart_kitchen_ingredients.localdata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import de.nordakademie.smart_kitchen_ingredients.IngredientsApplication;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.IIngredient;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.IIngredientFactory;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.IRecipe;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.Ingredient;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.IngredientFactory;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.RecipeFactory;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.RecipeFactoryImpl;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.Unit;
/**
 * 
 * @author Kathrin Kurtz
 * 
 */
public class CacheData extends SQLiteOpenHelper implements IRecipeData,
		ICacheRecipes, IIngredientData {

	private static final String TAG = CacheData.class.getSimpleName();

	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "recipeDB";

	public static final String TABLE_RECIPES = "recipes_table";
	public static final String COLUMN_RECIPE_ID = "id";
	public static final String COLUMN_TITLE = "title";

	public static final String TABLE_INGRDIENTS = "ingredients_table";
	public static final String COLUMN_INGRDIENTS_ID = "id";
	public static final String COLUMN_NAME = "name";
	public static final String COLUMN_UNIT = "unit";

	public static final String TABLE_INGREDIENTS_TO_RECIPES = "indigrentToRecipe";
	public static final String COLUMN_RECIPES = "recipe_id";
	public static final String COLUMN_INGRDIENTS = "indigrent_id";
	public static final String COLUMN_AMOUNT = "amount";
	
	IngredientsApplication app;

	public CacheData(IngredientsApplication application) {
		super(application.getApplicationContext(), DATABASE_NAME, null, DATABASE_VERSION);
		app = application;
	}

	private static final String DATABASE_CREATE_RECIPES = "create table "
			+ TABLE_RECIPES + " (" 
			+ COLUMN_RECIPE_ID + " text primary key not null, " 
			+ COLUMN_TITLE + " text)";

	private static final String DATABASE_CREATE_INDIGRENTS = "create table "
			+ TABLE_INGRDIENTS + " (" + COLUMN_INGRDIENTS_ID
			+ " text primary key not null, " + COLUMN_NAME + " text, "
			+ COLUMN_UNIT + " text)";

	private static final String DATABASE_CREATE_INDIGRENTS_TO_RECIPES = 
			"create table " + TABLE_INGREDIENTS_TO_RECIPES + " ("
			+ COLUMN_RECIPES + " text "
			+ COLUMN_INGRDIENTS + " text "
			+ COLUMN_AMOUNT + " Integer)";

	@Override
	public void onCreate(SQLiteDatabase database) {
		Log.w(TAG, "Tabellen in der Datenbank angelegt.");
		database.execSQL(DATABASE_CREATE_RECIPES);
		database.execSQL(DATABASE_CREATE_INDIGRENTS);
		database.execSQL(DATABASE_CREATE_INDIGRENTS_TO_RECIPES);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
				+ newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECIPES);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_INGRDIENTS);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_INGREDIENTS_TO_RECIPES);
		onCreate(db);
	}

	@Override
	public List<IRecipe> getAllRecipes() {
		List<IRecipe> recipes = new ArrayList<IRecipe>();
		 SQLiteDatabase db = getReadableDatabase();
		 String sql = "SELECT * FROM " + TABLE_RECIPES;
		 Cursor cursor = db.rawQuery(sql, null);
		
		 while (cursor.moveToNext()) { 
			 RecipeFactory recipeFactory = app.getRecipeFactory();
			 recipes.add(recipeFactory.createRecipe(cursor.getString(1),
					 getIngredientsForRecipeID(cursor.getString(0))));
		 }
		return recipes;
	}

	private List<IIngredient> getIngredientsForRecipeID(String recipeID) {
		List<IIngredient> ingredientsList = new ArrayList<IIngredient>();
		IIngredientFactory ingredientFactory = app.getIngredientFactory();
		
		SQLiteDatabase db = getReadableDatabase();
		String sql = "SELECT * FROM" + TABLE_INGREDIENTS_TO_RECIPES;
		Cursor cursor = db.rawQuery(sql, null);
		
		while(cursor.moveToNext()){
			ingredientsList.add(ingredientFactory.
					createIngredient(
							getIngredientNameByID(cursor.getString(1)), 
							cursor.getInt(2), 
							getIngredientUnitByID(cursor.getString(1))));
		}
		return ingredientsList;
	}

	private Unit getIngredientUnitByID(String id) {
		SQLiteDatabase db = getReadableDatabase();
		String sql = "SELECT " + COLUMN_UNIT + " FROM "
				+ TABLE_INGRDIENTS + " WHERE " + COLUMN_INGRDIENTS_ID
				+ "=" + id ;
		Cursor cursor = db.rawQuery(sql, null);
		cursor.moveToNext();
		return Unit.valueOf(cursor.getString(0));
	}

	private String getIngredientNameByID(String id) {
		SQLiteDatabase db = getReadableDatabase();
		String sql = "SELECT " + COLUMN_NAME + " FROM "
				+ TABLE_INGRDIENTS + " WHERE " + COLUMN_INGRDIENTS_ID
				+ "=" + id ;
		Cursor cursor = db.rawQuery(sql, null);
		cursor.moveToNext();
		return cursor.getString(0);
	}

	@Override
	public void cacheAllRecipes(Map<String[], List<String[]>> recipes) {
		// KEY: String Array - Value: String Array
		// rezept zutaten
		// 0=id 1=titel 0=id 1=titel 2=einheit 3=menge

		Iterator<String[]> iterator = recipes.keySet().iterator();
		if (iterator.hasNext()) {
			String[] currentRecipe = iterator.next();
			String currentRecipeID = currentRecipe[0];
			String currentRecipeTitle = currentRecipe[1];
			writeRecipeToDB(currentRecipeID, currentRecipeTitle);

			List<String[]> ingredientList = recipes.get(currentRecipe);
			for (String[] currentIngredient : ingredientList) {
				writeConectionToRecipeToDB(currentRecipeID, currentIngredient);
				System.out.println("Tabelle funktioniert");
				Log.w(TAG, "die Tabelle mit rezeptID, ZutatenID und amount wurde erstellt.");
			}

		}
	}

	private void writeConectionToRecipeToDB(String recipeID, String[] ingredient) {
		ContentValues values = new ContentValues();
		values.put(COLUMN_RECIPES, recipeID);
		values.put(COLUMN_INGRDIENTS, ingredient[0]);
		values.put(COLUMN_AMOUNT, ingredient[3]);

		SQLiteDatabase writableDatabase = getWritableDatabase();
		writableDatabase.insertWithOnConflict(TABLE_INGREDIENTS_TO_RECIPES,
				null, values, SQLiteDatabase.CONFLICT_IGNORE);
		writableDatabase.close();
		Log.i(TAG, "database of INDIGRENTS_TO_RECIPES updated");
	}

	private void writeRecipeToDB(String currentID, String currentTitle) {
		ContentValues values = new ContentValues();
		values.put(COLUMN_RECIPE_ID, currentID);
		values.put(COLUMN_TITLE, currentTitle);

		SQLiteDatabase writableDatabase = getWritableDatabase();
		writableDatabase.insertWithOnConflict(TABLE_RECIPES, null, values,
				SQLiteDatabase.CONFLICT_IGNORE);
		writableDatabase.close();
		Log.i(TAG, "database of RECIPES updated");
	}

	@Override
	public void cacheAllIngredients(List<String[]> ingredients) {
		for (String[] currentIngredient : ingredients) {
			writeIngredientToDB(currentIngredient);
		}

	}

	private void writeIngredientToDB(String[] ingredient) {
		ContentValues values = new ContentValues();
		values.put(COLUMN_INGRDIENTS_ID, ingredient[0]);
		values.put(COLUMN_NAME, ingredient[1]);
		values.put(COLUMN_UNIT, ingredient[2]);

		SQLiteDatabase writableDatabase = getWritableDatabase();
		writableDatabase.insertWithOnConflict(TABLE_INGRDIENTS, null, values,
				SQLiteDatabase.CONFLICT_IGNORE);
		writableDatabase.close();
		Log.i(TAG, "database of Indigrents updated");
	}

	@Override
	public List<IIngredient> getAllIngredients() {
		List<IIngredient> ingredientsList = new ArrayList<IIngredient>();
		IIngredientFactory ingredientFactory = app.getIngredientFactory();
		
		SQLiteDatabase db = getReadableDatabase();
		String sql = "SELECT * FROM" + TABLE_INGRDIENTS;
		Cursor cursor = db.rawQuery(sql, null);
		
		while(cursor.moveToNext()){
			ingredientsList.add(
					ingredientFactory.createIngredient
					(cursor.getString(1), 0, Unit.valueOf(cursor.getString(2))));
		}
		
		return ingredientsList;
	}

}
