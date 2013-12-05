package de.nordakademie.smart_kitchen_ingredients;

import android.app.Application;
import android.util.Log;
import de.nordakademie.smart_kitchen_ingredients.localdata.ShoppingData;
import de.nordakademie.smart_kitchen_ingredients.localdata.ShoppingDataImpl;

public class IngredientsApplication extends Application {

	private final String TAG = IngredientsApplication.class.getSimpleName();
	private ShoppingData shoppingDbHelper;

	@Override
	public void onCreate() {
		super.onCreate();

		shoppingDbHelper = new ShoppingDataImpl(getApplicationContext());

		Log.i(TAG, "Application started");
	}

	public ShoppingData getDbHelper() {
		return shoppingDbHelper;
	}

}
