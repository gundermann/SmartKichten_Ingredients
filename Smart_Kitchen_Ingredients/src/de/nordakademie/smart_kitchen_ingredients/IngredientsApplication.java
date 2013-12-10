package de.nordakademie.smart_kitchen_ingredients;

import android.app.Application;
import android.util.Log;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.IIngredientFactory;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.IShoppingListItemFactory;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.IngredientFactory;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.RecipeFactory;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.RecipeFactoryImpl;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.ShoppingListItemFactory;
import de.nordakademie.smart_kitchen_ingredients.localdata.IShoppingData;
import de.nordakademie.smart_kitchen_ingredients.localdata.ShoppingData;
import de.nordakademie.smart_kitchen_ingredients.onlinedata.BarcodeServerConnector;
import de.nordakademie.smart_kitchen_ingredients.onlinedata.BarcodeServerHandler;
import de.nordakademie.smart_kitchen_ingredients.onlinedata.IBarcodeServerHandler;
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

	@Override
	public void onCreate() {
		super.onCreate();

		shoppingDbHelper = new ShoppingData(this);
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

}
