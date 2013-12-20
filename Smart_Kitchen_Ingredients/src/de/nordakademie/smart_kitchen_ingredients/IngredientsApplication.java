package de.nordakademie.smart_kitchen_ingredients;

import android.app.Application;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.IIngredient;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.IRecipe;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.IRecipeFactory;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.IShoppingList;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.RecipeFactory;
import de.nordakademie.smart_kitchen_ingredients.localdata.cache.CacheUpdateDbHelper;
import de.nordakademie.smart_kitchen_ingredients.localdata.cache.IAbstractCacheDbHelper;
import de.nordakademie.smart_kitchen_ingredients.localdata.cache.ICacheDbUpdateHelper;
import de.nordakademie.smart_kitchen_ingredients.localdata.cache.IngredientDbHelper;
import de.nordakademie.smart_kitchen_ingredients.localdata.cache.RecipeDbHelper;
import de.nordakademie.smart_kitchen_ingredients.localdata.smartkitchen.IDateDbHelper;
import de.nordakademie.smart_kitchen_ingredients.localdata.smartkitchen.IShoppingDbHelper;
import de.nordakademie.smart_kitchen_ingredients.localdata.smartkitchen.IStoredDbHelper;
import de.nordakademie.smart_kitchen_ingredients.localdata.smartkitchen.SmartKitchenDateData;
import de.nordakademie.smart_kitchen_ingredients.localdata.smartkitchen.SmartKitchenShoppingData;
import de.nordakademie.smart_kitchen_ingredients.localdata.smartkitchen.SmartKitchenStoredData;
import de.nordakademie.smart_kitchen_ingredients.shopping.barcodescan.BarcodeServerConnector;
import de.nordakademie.smart_kitchen_ingredients.shopping.barcodescan.BarcodeServerHandler;
import de.nordakademie.smart_kitchen_ingredients.shopping.barcodescan.IBarcodeServerHandler;
import de.nordakademie.smart_kitchen_ingredients.smartkitchen_server.ISmartKitchenServerHandler;
import de.nordakademie.smart_kitchen_ingredients.smartkitchen_server.SmartKitchenServerConnector;
import de.nordakademie.smart_kitchen_ingredients.smartkitchen_server.SmartKitchenServerHandler;

/**
 * Die allgemeine Application, die alle Datenbankhelper enthällt.
 * 
 * @author niels
 * 
 */
public class IngredientsApplication extends Application {

	public static final String CHANGING = "de.nordakademie.smart_kitchen_ingredient.CHANGING";
	public static final String PERMISSION = "de.nordakademie.smart_kitchen_ingredients.SHOPPING_LIST_CHANGING";
	private static final long ONE_DAY = 24 * 60 * 60 * 1000;
	private final String TAG = IngredientsApplication.class.getSimpleName();
	private IShoppingDbHelper shoppingDbHelper;
	private ISmartKitchenServerHandler serverHandler;
	private IRecipeFactory recipeFactory;
	private IBarcodeServerHandler barcodeEvaluator;
	private ICacheDbUpdateHelper serverDataHelper;
	private IStoredDbHelper stockDbHelper;
	private IDateDbHelper dateDbHelper;
	private IAbstractCacheDbHelper<IIngredient> ingredientDbHelper;
	private IAbstractCacheDbHelper<IRecipe> recipeDbHelper;
	private long lastUpdate = 0;
	private IShoppingList shoppingList;
	private boolean isUpdating = false;

	@Override
	public void onCreate() {
		super.onCreate();

		serverDataHelper = new CacheUpdateDbHelper(this);
		shoppingDbHelper = new SmartKitchenShoppingData(this);
		serverHandler = new SmartKitchenServerHandler(
				new SmartKitchenServerConnector());
		ingredientDbHelper = new IngredientDbHelper(this);
		recipeDbHelper = new RecipeDbHelper(this);
		dateDbHelper = new SmartKitchenDateData(this);
		recipeFactory = new RecipeFactory();
		barcodeEvaluator = new BarcodeServerHandler(
				new BarcodeServerConnector());
		stockDbHelper = new SmartKitchenStoredData(this);

		Log.i(TAG, "Application started");
	}

	public IShoppingDbHelper getShoppingListDbHelper() {
		return shoppingDbHelper;
	}

	public IShoppingList getName() {
		return shoppingList;
	}

	public ICacheDbUpdateHelper getCacheDbHelper() {
		return serverDataHelper;
	}

	public ISmartKitchenServerHandler getServerHandler() {
		return serverHandler;
	}

	public IRecipeFactory getRecipeFactory() {
		return recipeFactory;
	}

	public IBarcodeServerHandler getBarcodeEvaluator() {
		return barcodeEvaluator;
	}

	public IStoredDbHelper getStoredDbHelper() {
		return stockDbHelper;
	}

	public IAbstractCacheDbHelper<IIngredient> getIngredientsDbHelper() {
		return ingredientDbHelper;
	}

	public IAbstractCacheDbHelper<IRecipe> getRecipeDbHelper() {
		return recipeDbHelper;
	}

	public IDateDbHelper getDateDbHelper() {
		return dateDbHelper;
	}

	public void updateCache() {
		if (!isUpdating && System.currentTimeMillis() - lastUpdate > ONE_DAY
				&& isNetworkConnected()) {
			isUpdating = true;
			serverDataHelper
					.insertOrUpdateAllIngredientsFromServer(serverHandler
							.getIngredientListFromServer());
			serverDataHelper.insertOrUpdateAllRecipesFromServer(serverHandler
					.getRecipeListFromServer());
			lastUpdate = System.currentTimeMillis();
			isUpdating = false;
		}
	}
	
	public boolean isNetworkConnected(){
		Boolean connected = false;
		ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if(netInfo != null && netInfo.isConnected()){
			connected = true;
		}
		return connected;
	}

	public void informUser(int stringId) {
		Toast.makeText(getApplicationContext(), stringId, Toast.LENGTH_LONG)
				.show();
	}

}
