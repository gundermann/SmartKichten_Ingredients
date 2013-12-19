package de.nordakademie.smart_kitchen_ingredients.localdata.cache;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import de.nordakademie.smart_kitchen_ingredients.IngredientsApplication;
import de.nordakademie.smart_kitchen_ingredients.localdata.AbstractData;
import de.nordakademie.smart_kitchen_ingredients.localdata.cache.tables.IngredientsTable;
import de.nordakademie.smart_kitchen_ingredients.localdata.cache.tables.IngredientsToRecipeTable;
import de.nordakademie.smart_kitchen_ingredients.localdata.cache.tables.RecipesTable;

/**
 * 
 * @author Kathrin Kurtz
 * 
 */
public abstract class AbstractCacheData extends AbstractData {

	protected static String TAG = AbstractCacheData.class.getSimpleName();
	private static final int DATABASE_VERSION = 4;
	private static final String DATABASE_NAME = "cache.db";

	public AbstractCacheData(IngredientsApplication application) {
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

	protected void updateIfNecessary() {
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

}
