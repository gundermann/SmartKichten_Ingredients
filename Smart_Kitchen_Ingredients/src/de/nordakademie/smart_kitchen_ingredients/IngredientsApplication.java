package de.nordakademie.smart_kitchen_ingredients;

import android.app.Application;
import android.util.Log;
import de.nordakademie.smart_kitchen_ingredients.localdata.IShoppingData;
import de.nordakademie.smart_kitchen_ingredients.localdata.ShoppingData;

public class IngredientsApplication extends Application {

	public static final String CHANGING = "de.nordakademie.smart_kitchen_ingredient.CHANGING";
	public static final String PERMISSION = "de.nordakademie.smart_kitchen_ingredients.SHOPPING_LIST_CHANGING";
	private final String TAG = IngredientsApplication.class.getSimpleName();
	private IShoppingData shoppingDbHelper;

	@Override
	public void onCreate() {
		super.onCreate();

		shoppingDbHelper = new ShoppingData(getApplicationContext());

		Log.i(TAG, "Application started");
	}

	public IShoppingData getDbHelper() {
		return shoppingDbHelper;
	}

}
