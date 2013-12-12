package de.nordakademie.smart_kitchen_ingredients;

import android.app.Application;
import android.util.Log;
import de.nordakademie.smart_kitchen_ingredients.barcodescan.BarcodeServerConnector;
import de.nordakademie.smart_kitchen_ingredients.barcodescan.BarcodeServerHandler;
import de.nordakademie.smart_kitchen_ingredients.barcodescan.IBarcodeServerHandler;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.IIngredientFactory;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.IShoppingListItemFactory;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.IngredientFactory;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.IRecipeFactory;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.RecipeFactory;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.ShoppingListItemFactory;
import de.nordakademie.smart_kitchen_ingredients.localdata.ICacheData;
import de.nordakademie.smart_kitchen_ingredients.localdata.IIngredientCacheData;
import de.nordakademie.smart_kitchen_ingredients.localdata.IRecipeCacheData;
import de.nordakademie.smart_kitchen_ingredients.localdata.IShoppingData;
import de.nordakademie.smart_kitchen_ingredients.localdata.CacheData;
import de.nordakademie.smart_kitchen_ingredients.localdata.SmartKitchenData;
import de.nordakademie.smart_kitchen_ingredients.onlinedata.ISmartKitchenServerHandler;
import de.nordakademie.smart_kitchen_ingredients.onlinedata.SmartKitchenServerConnector;
import de.nordakademie.smart_kitchen_ingredients.onlinedata.SmartKitchenServerHandler;

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

	private CacheData serverDataHelper;

	@Override
	public void onCreate() {
		super.onCreate();

		serverDataHelper = new CacheData(this);
		shoppingDbHelper = new SmartKitchenData(this);
		serverHandler = new SmartKitchenServerHandler(new SmartKitchenServerConnector());
		ingredientFactory = new IngredientFactory();
		shoppingListItemFactory = new ShoppingListItemFactory();
		recipeFactory = new RecipeFactory();
		barcodeEvaluator = new BarcodeServerHandler(
				new BarcodeServerConnector());

		Log.i(TAG, "Application started");
	}

	public IShoppingData getShoppingDbHelper() {
		return shoppingDbHelper;
	}

	public ICacheData getCacheDbHelper() {
		return serverDataHelper;
	}

	public IRecipeCacheData getRecipesFromCacheHelper() {
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

	public IIngredientCacheData getIngredientDbHelper() {
		return serverDataHelper;
	}

}
