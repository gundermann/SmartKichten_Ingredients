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
import de.nordakademie.smart_kitchen_ingredients.onlinedata.Connector;
import de.nordakademie.smart_kitchen_ingredients.onlinedata.IServerHandler;
import de.nordakademie.smart_kitchen_ingredients.onlinedata.ServerHandler;

public class IngredientsApplication extends Application {

	public static final String CHANGING = "de.nordakademie.smart_kitchen_ingredient.CHANGING";
	public static final String PERMISSION = "de.nordakademie.smart_kitchen_ingredients.SHOPPING_LIST_CHANGING";
	private final String TAG = IngredientsApplication.class.getSimpleName();
	private IShoppingData shoppingDbHelper;
	private IServerHandler serverHandler;
	private IIngredientFactory ingredientFactory;
	private RecipeFactory recipeFactory;
	private IShoppingListItemFactory shoppingListItemFactory;

	@Override
	public void onCreate() {
		super.onCreate();

		shoppingDbHelper = new ShoppingData(this);
		serverHandler = new ServerHandler(new Connector());
		ingredientFactory = new IngredientFactory();
		shoppingListItemFactory = new ShoppingListItemFactory();
		recipeFactory = new RecipeFactoryImpl();

		Log.i(TAG, "Application started");
	}

	public IShoppingData getShoppingDbHelper() {
		return shoppingDbHelper;
	}

	public IServerHandler getServerHandler() {
		return serverHandler;
	}

	public IIngredientFactory getIngredientFactory() {
		return ingredientFactory;
	}

	public RecipeFactory getRecipeFactory() {
		return recipeFactory;
	}

	public IShoppingListItemFactory getShoppingListItemFactory() {
		return shoppingListItemFactory;
	}

}
