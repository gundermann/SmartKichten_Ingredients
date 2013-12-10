package de.nordakademie.smart_kitchen_ingredients;

import android.app.Application;
import android.util.Log;
import de.nordakademie.smart_kitchen_ingredients.barcodescan.BarcodeServerConnector;
import de.nordakademie.smart_kitchen_ingredients.barcodescan.BarcodeServerHandler;
import de.nordakademie.smart_kitchen_ingredients.barcodescan.IBarcodeServerHandler;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.IIngredientFactory;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.IShoppingListItemFactory;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.IngredientFactory;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.RecipeFactory;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.RecipeFactoryImpl;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.ShoppingListItemFactory;
import de.nordakademie.smart_kitchen_ingredients.localdata.ICacheRecipes;
import de.nordakademie.smart_kitchen_ingredients.localdata.IIngredientData;
import de.nordakademie.smart_kitchen_ingredients.localdata.IRecipeData;
import de.nordakademie.smart_kitchen_ingredients.localdata.IShoppingData;
import de.nordakademie.smart_kitchen_ingredients.localdata.CacheData;
import de.nordakademie.smart_kitchen_ingredients.localdata.SmartKitchenData;
import de.nordakademie.smart_kitchen_ingredients.onlinedata.ISKIServerHandler;
import de.nordakademie.smart_kitchen_ingredients.onlinedata.SKIServerConnector;
import de.nordakademie.smart_kitchen_ingredients.onlinedata.SKIServerHandler;

public class IngredientsApplication extends Application {

	public static final String CHANGING = "de.nordakademie.smart_kitchen_ingredient.CHANGING";
	public static final String PERMISSION = "de.nordakademie.smart_kitchen_ingredients.SHOPPING_LIST_CHANGING";
	private final String TAG = IngredientsApplication.class.getSimpleName();
	private IShoppingData shoppingDbHelper;
	private ISKIServerHandler serverHandler;
	private IIngredientFactory ingredientFactory;
	private RecipeFactory recipeFactory;
	private IShoppingListItemFactory shoppingListItemFactory;
	private IBarcodeServerHandler barcodeEvaluator;

	private CacheData serverDataHelper;

	@Override
	public void onCreate() {
		super.onCreate();

		serverDataHelper = new CacheData(this);
		shoppingDbHelper = new SmartKitchenData(this);
		serverHandler = new SKIServerHandler(new SKIServerConnector());
		ingredientFactory = new IngredientFactory();
		shoppingListItemFactory = new ShoppingListItemFactory();
		recipeFactory = new RecipeFactoryImpl();
		barcodeEvaluator = new BarcodeServerHandler(
				new BarcodeServerConnector());

		Log.i(TAG, "Application started");
	}

	public IShoppingData getShoppingDbHelper() {
		return shoppingDbHelper;
	}

	public ICacheRecipes getCacheDbHelper() {
		return serverDataHelper;
	}

	public IRecipeData getRecipesFromCacheHelper() {
		return serverDataHelper;
	}

	public ISKIServerHandler getServerHandler() {
		return serverHandler;
	}

	public IIngredientFactory getIngredientFactory() {
		return ingredientFactory;
	}

	public RecipeFactory getRecipeFactory() {
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

}
