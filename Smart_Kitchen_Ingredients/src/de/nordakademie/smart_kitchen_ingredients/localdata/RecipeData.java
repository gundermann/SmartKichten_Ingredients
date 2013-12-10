package de.nordakademie.smart_kitchen_ingredients.localdata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import de.nordakademie.smart_kitchen_ingredients.businessobjects.IIngredientFactory;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.Ingredient;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.IngredientFactory;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.Unit;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class RecipeData extends SQLiteOpenHelper implements IRecipeData, ICacheRecipes	{
	
	private static final String TAG = RecipeData.class.getSimpleName();
	
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "recipeDB";
	
	public static final String TABLE_RECIPES = "recipes_table";
	public static final String COLUMN_RECIPE_ID = "id";
	public static final String COLUMN_TITLE = "title";
	
	public static final String TABLE_INDIGRENTS = "recipes_table";
	public static final String COLUMN_INDIGRENTS_ID = "id";
	public static final String COLUMN_NAME = "name";
	public static final String COLUMN_UNIT = "unit";
	
	public static final String TABLE_INGREDIENTS_TO_RECIPES = "indigrentToRecipe";
	public static final String COLUMN_RECIPES = "recipe_id";
	public static final String COLUMN_INDIGRENTS = "indigrent_id";
	public static final String COLUMN_AMOUNT = "amount";

	public RecipeData(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	private static final String DATABASE_CREATE_RECIPES = "create table "
			+ TABLE_RECIPES + " (" + COLUMN_RECIPE_ID
			+ " text primary key not null, " + COLUMN_TITLE + " text)";
	
	private static final String DATABASE_CREATE_INDIGRENTS = "create table "
			+ TABLE_INDIGRENTS + " (" + COLUMN_INDIGRENTS_ID
			+ " text primary key not null, " + COLUMN_NAME
			+ " text, " + COLUMN_UNIT
			+ " text)";
	
	private static final String DATABASE_CREATE_INDIGRENTS_TO_RECIPES ="" +
			"create table" + TABLE_INGREDIENTS_TO_RECIPES + " ("
			+"FOREIGN KEY(" + COLUMN_RECIPES 
			+ ") REFERENCES "+ TABLE_RECIPES + "("+ COLUMN_RECIPE_ID +")"
			+"FOREIGN KEY(" + COLUMN_INDIGRENTS 
			+ ") REFERENCES "+ TABLE_INDIGRENTS + "("+ COLUMN_INDIGRENTS_ID +")"
			+ COLUMN_AMOUNT + " interger )" ;	

	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE_RECIPES);
		database.execSQL(DATABASE_CREATE_INDIGRENTS);
		database.execSQL(DATABASE_CREATE_INDIGRENTS_TO_RECIPES);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
				+ newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECIPES);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_INDIGRENTS);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_INGREDIENTS_TO_RECIPES);
		onCreate(db);
	}

	@Override
	public List<String> getAllRecipeTitels() {
		List recipeTitels = new ArrayList<String>();
				
		SQLiteDatabase db = getReadableDatabase();
		String sql = "SELECT " + COLUMN_TITLE + " FROM "
		+ TABLE_RECIPES;
		Cursor cursor = db.rawQuery(sql, null);
		
		while(cursor.moveToNext()){
			recipeTitels.add(cursor.getString(0));
		}
		
		return recipeTitels;
	}

	@Override
	public List<Ingredient> getIngredientsForRecipe(String recipeTitle) {
		
		SQLiteDatabase db = getReadableDatabase();
		String sql = "SELECT "+ TABLE_INGREDIENTS_TO_RECIPES + "." + COLUMN_INDIGRENTS
		+", " + TABLE_INGREDIENTS_TO_RECIPES + "." + COLUMN_AMOUNT
		+ " FROM " + TABLE_INGREDIENTS_TO_RECIPES
		+ " INNER JOIN "+ TABLE_RECIPES 
		+" ON " + TABLE_RECIPES+"." + COLUMN_RECIPE_ID + "="
		+ TABLE_INGREDIENTS_TO_RECIPES + "." + COLUMN_RECIPES
		+ " WHERE " + TABLE_RECIPES +"." + COLUMN_TITLE + "="
		+ recipeTitle;
		
		Cursor cursor = db.rawQuery(sql, null);
		Map ingredientIDAndAmount = new HashMap<String, Integer>();
		
		List ingredients = new ArrayList<Ingredient>();
		IIngredientFactory factory = new IngredientFactory();
		
		while(cursor.moveToNext()){
			ingredientIDAndAmount.put(cursor.getString(0), cursor.getString(1));		
		}
		
		Iterator iteratorIngredientsIDs = ingredientIDAndAmount.keySet().iterator();
		while(iteratorIngredientsIDs.hasNext()){
			
			ingredients.add(factory.createIngredient(
					getIgredientNameByID(iteratorIngredientsIDs.next().toString()), 
					(Integer) ingredientIDAndAmount.get(iteratorIngredientsIDs)
					, getIgredientUnitByID(iteratorIngredientsIDs.next().toString())));
		}
		
		return ingredients;
	}

	private Unit getIgredientUnitByID(String id) {
		SQLiteDatabase db = getReadableDatabase();
		String sql = "SELECT " + COLUMN_UNIT + " FROM "
		+ TABLE_INDIGRENTS;
		Cursor cursor = db.rawQuery(sql, null);
		cursor.moveToNext();
		return Unit.valueOf(cursor.getString(0));
	}

	private String getIgredientNameByID(String id) {
		SQLiteDatabase db = getReadableDatabase();
		String sql = "SELECT " + COLUMN_UNIT + " FROM "
		+ TABLE_INDIGRENTS;
		Cursor cursor = db.rawQuery(sql, null);
		cursor.moveToNext();
		return cursor.getString(0);
	}

	@Override
	public void cacheAllRecipes(Map<String[], List<String[]>> recipes) {
		//KEY: String Array - Value: String Array
		//     rezept				 zutaten
		//     0=id 1=titel			 0=id 1=titel 2=einheit 3=menge
		
		Iterator<String[]> iterator = recipes.keySet().iterator();
		if(iterator.hasNext()){
			String[] currentRecipe = iterator.next();
			String currentRecipeID = currentRecipe[0];
			String currentRecipeTitle = currentRecipe[1];
			writeRecipeToDB(currentRecipeID, currentRecipeTitle);		
			
			List<String[]> ingredientList = recipes.get(currentRecipe);
			for(String[] currentIngredient : ingredientList){
				writeConectionToRecipeToDB(currentRecipeID, currentIngredient);
			}
			
		}
	}

	private void writeConectionToRecipeToDB(String recipeID, String[] ingredient) {
		ContentValues values = new ContentValues();
		values.put(COLUMN_RECIPES, recipeID);
		values.put(COLUMN_INDIGRENTS, ingredient[0]);
		values.put(COLUMN_AMOUNT, ingredient[3]);
	
	SQLiteDatabase writableDatabase = getWritableDatabase();
	writableDatabase.insertWithOnConflict(TABLE_INGREDIENTS_TO_RECIPES, null, values,
			SQLiteDatabase.CONFLICT_IGNORE);
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
		for(String[] currentIngredient : ingredients){
			writeIngredientToDB(currentIngredient);
		}
		
	}	
	private void writeIngredientToDB(String[] ingredient) {
		ContentValues values = new ContentValues();
			values.put(COLUMN_INDIGRENTS_ID, ingredient[0]);
			values.put(COLUMN_NAME, ingredient[1]);
			values.put(COLUMN_UNIT, ingredient[2]);
		
		SQLiteDatabase writableDatabase = getWritableDatabase();
		writableDatabase.insertWithOnConflict(TABLE_INDIGRENTS, null, values,
				SQLiteDatabase.CONFLICT_IGNORE);
		writableDatabase.close();
		Log.i(TAG, "database of Indigrents updated");
	}

}
