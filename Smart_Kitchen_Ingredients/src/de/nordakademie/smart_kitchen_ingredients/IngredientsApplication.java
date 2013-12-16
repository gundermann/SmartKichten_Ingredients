package de.nordakademie.smart_kitchen_ingredients;

import android.app.Application;
import android.util.Log;
import de.nordakademie.smart_kitchen_ingredients.barcodescan.BarcodeServerConnector;
import de.nordakademie.smart_kitchen_ingredients.barcodescan.BarcodeServerHandler;
import de.nordakademie.smart_kitchen_ingredients.barcodescan.IBarcodeServerHandler;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.IIngredientFactory;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.IRecipeFactory;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.IShoppingListItemFactory;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.IngredientFactory;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.RecipeFactory;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.ShoppingListItemFactory;
import de.nordakademie.smart_kitchen_ingredients.localdata.CacheData;
import de.nordakademie.smart_kitchen_ingredients.localdata.ICacheData;
import de.nordakademie.smart_kitchen_ingredients.localdata.IIngredientData;
import de.nordakademie.smart_kitchen_ingredients.localdata.IRecipeData;
import de.nordakademie.smart_kitchen_ingredients.localdata.IShoppingData;
import de.nordakademie.smart_kitchen_ingredients.localdata.IStoredData;
import de.nordakademie.smart_kitchen_ingredients.localdata.SmartKitchenData;
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
	private final String TAG = IngredientsApplication.class.getSimpleName();
	private IShoppingData shoppingDbHelper;
	private ISmartKitchenServerHandler serverHandler;
	private IIngredientFactory ingredientFactory;
	private IRecipeFactory recipeFactory;
	private IShoppingListItemFactory shoppingListItemFactory;
	private IBarcodeServerHandler barcodeEvaluator;
	private IIngredientData cachedIngredientsHelper;
	private IRecipeData cachedRecipesHelper;
	private CacheData serverDataHelper;
	private IStoredData stockDbHelper;

	@Override
	public void onCreate() {
		super.onCreate();

		serverDataHelper = new CacheData(this);
		shoppingDbHelper = new SmartKitchenData(this);
		serverHandler = new SmartKitchenServerHandler(
				new SmartKitchenServerConnector());
		ingredientFactory = new IngredientFactory();
		shoppingListItemFactory = new ShoppingListItemFactory();
		recipeFactory = new RecipeFactory();
		barcodeEvaluator = new BarcodeServerHandler(
				new BarcodeServerConnector());
		cachedIngredientsHelper = new CacheData(this);
		cachedRecipesHelper = new CacheData(this);
		stockDbHelper = new SmartKitchenData(this);

		Log.i(TAG, "Application started");
	}

	public IIngredientData getCachedIngredientsHelper() {

		return cachedIngredientsHelper;
	}


	public IRecipeData getCachedRecipesHelper() {
		return cachedRecipesHelper;
	}

	public IShoppingData getShoppingDbHelper() {
		return shoppingDbHelper;
	}

	public ICacheData getCacheDbHelper() {
		return serverDataHelper;
	}

	public IRecipeData getRecipesFromCacheHelper() {
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

	public IIngredientData getIngredientDbHelper() {
		return serverDataHelper;
	}

	public IStoredData getStoredDbHelper() {
		return stockDbHelper;
	}

}
