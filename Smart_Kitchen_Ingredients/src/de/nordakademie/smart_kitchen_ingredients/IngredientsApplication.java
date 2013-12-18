package de.nordakademie.smart_kitchen_ingredients;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;
import de.nordakademie.smart_kitchen_ingredients.barcodescan.BarcodeServerConnector;
import de.nordakademie.smart_kitchen_ingredients.barcodescan.BarcodeServerHandler;
import de.nordakademie.smart_kitchen_ingredients.barcodescan.IBarcodeServerHandler;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.IIngredient;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.IIngredientFactory;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.IRecipe;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.IRecipeFactory;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.IShoppingListItemFactory;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.IngredientFactory;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.RecipeFactory;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.ShoppingListItemFactory;
import de.nordakademie.smart_kitchen_ingredients.localdata.CacheData;
import de.nordakademie.smart_kitchen_ingredients.localdata.DateDatabase;
import de.nordakademie.smart_kitchen_ingredients.localdata.ICacheData;
import de.nordakademie.smart_kitchen_ingredients.localdata.IDatabaseHelper;
import de.nordakademie.smart_kitchen_ingredients.localdata.IDateDbHelper;
import de.nordakademie.smart_kitchen_ingredients.localdata.IShoppingData;
import de.nordakademie.smart_kitchen_ingredients.localdata.IStoredData;
import de.nordakademie.smart_kitchen_ingredients.localdata.IngredientDatabaseHelper;
import de.nordakademie.smart_kitchen_ingredients.localdata.RecipeDatabaseHelper;
import de.nordakademie.smart_kitchen_ingredients.localdata.SmartKitchenData;
import de.nordakademie.smart_kitchen_ingredients.scheduling.DateFactory;
import de.nordakademie.smart_kitchen_ingredients.scheduling.IDateFactory;
import de.nordakademie.smart_kitchen_ingredients.smartkitchen_server.ISmartKitchenServerHandler;
import de.nordakademie.smart_kitchen_ingredients.smartkitchen_server.SmartKitchenServerConnector;
import de.nordakademie.smart_kitchen_ingredients.smartkitchen_server.SmartKitchenServerHandler;

/**
 * Die allgemeine Application, die alle Factories und Datenbankhelper enthällt.
 * 
 * @author niels
 * 
 */
public class IngredientsApplication extends Application {

	public static final String CHANGING = "de.nordakademie.smart_kitchen_ingredient.CHANGING";
	public static final String PERMISSION = "de.nordakademie.smart_kitchen_ingredients.SHOPPING_LIST_CHANGING";
	private static final long ONE_DAY = 24 * 60 * 60 * 1000;
	private final String TAG = IngredientsApplication.class.getSimpleName();
	private IShoppingData shoppingDbHelper;
	private ISmartKitchenServerHandler serverHandler;
	private IIngredientFactory ingredientFactory;
	private IRecipeFactory recipeFactory;
	private IShoppingListItemFactory shoppingListItemFactory;
	private IBarcodeServerHandler barcodeEvaluator;
	private ICacheData serverDataHelper;
	private IStoredData stockDbHelper;
	private IDateDbHelper dateDbHelper;
	private IDatabaseHelper<IIngredient> ingredientDbHelper;
	private IDatabaseHelper<IRecipe> recipeDbHelper;
	private IDateFactory dateFactory;
	private long lastUpdate = 0;

	@Override
	public void onCreate() {
		super.onCreate();

		serverDataHelper = new CacheData(this);
		shoppingDbHelper = new SmartKitchenData(this);
		serverHandler = new SmartKitchenServerHandler(
				new SmartKitchenServerConnector());
		ingredientFactory = new IngredientFactory();
		dateFactory = new DateFactory();
		ingredientDbHelper = new IngredientDatabaseHelper(this);
		recipeDbHelper = new RecipeDatabaseHelper(this);
		dateDbHelper = new DateDatabase(this);
		shoppingListItemFactory = new ShoppingListItemFactory();
		recipeFactory = new RecipeFactory();
		barcodeEvaluator = new BarcodeServerHandler(
				new BarcodeServerConnector());
		stockDbHelper = new SmartKitchenData(this);

		Log.i(TAG, "Application started");
	}

	public IShoppingData getShoppingDbHelper() {
		return shoppingDbHelper;
	}

	public ICacheData getCacheDbHelper() {
		return serverDataHelper;
	}

	public ISmartKitchenServerHandler getServerHandler() {
		return serverHandler;
	}

	public IIngredientFactory getIngredientFactory() {
		return ingredientFactory;
	}

	public IRecipeFactory getRecipeFactory() {
		return recipeFactory;
	}

	public IBarcodeServerHandler getBarcodeEvaluator() {
		return barcodeEvaluator;
	}

	public IShoppingListItemFactory getShoppingListItemFactory() {
		return shoppingListItemFactory;
	}

	public IStoredData getStoredDbHelper() {
		return stockDbHelper;
	}

	public IDatabaseHelper<IIngredient> getIngredientsDbHelper() {
		return ingredientDbHelper;
	}

	public IDatabaseHelper<IRecipe> getRecipeDbHelper() {
		return recipeDbHelper;
	}

	public IDateFactory getDateFactory() {
		return dateFactory;
	}

	public IDateDbHelper getDateDbHelper() {
		return dateDbHelper;
	}

	public void updateCache() {
		if (System.currentTimeMillis() - lastUpdate > ONE_DAY) {
			serverDataHelper
					.insertOrUpdateAllIngredientsFromServer(serverHandler
							.getIngredientListFromServer());
			serverDataHelper.insertOrUpdateAllRecipesFromServer(serverHandler
					.getRecipeListFromServer());
			lastUpdate = System.currentTimeMillis();
		}
	}

	public void informUserAboutSomething() {
		Toast.makeText(getApplicationContext(), "AppToast", Toast.LENGTH_LONG)
				.show();
	}
}
