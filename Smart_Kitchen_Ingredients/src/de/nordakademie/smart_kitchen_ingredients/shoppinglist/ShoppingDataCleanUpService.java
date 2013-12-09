package de.nordakademie.smart_kitchen_ingredients.shoppinglist;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import de.nordakademie.smart_kitchen_ingredients.IngredientsApplication;
import de.nordakademie.smart_kitchen_ingredients.localdata.IShoppingData;

/**
 * Der Service entfernt gekaufte Artikel aus der Datenbank
 * 
 * @author Niels Gundermann
 * 
 */
public class ShoppingDataCleanUpService extends Service {

	private static String TAG = ShoppingDataCleanUpService.class
			.getSimpleName();

	@Override
	public void onCreate() {
		super.onCreate();
		Log.i(TAG, "created");
		IngredientsApplication app = (IngredientsApplication) getApplication();
		IShoppingData db = app.getDbHelper();
		db.cleanShoppingIngredients();
		Log.i(TAG, "db cleaned");

		ShoppingDataCleanUpService.this
				.sendBroadcast(new Intent(IngredientsApplication.CHANGING),
						"de.nordakademie.smart_kitchen_ingredients.SHOPPING_LIST_CHANGING");
		Log.i(TAG, "broadcast send");
		stopSelf();
	}

	@Override
	public IBinder onBind(Intent intent) {
		Log.d(TAG, "onBind");
		return null;
	}

	@Override
	public boolean onUnbind(Intent intent) {
		Log.d(TAG, "onUnBind");
		return super.onUnbind(intent);

	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.i(TAG, "onStartCommand");
		return START_STICKY;
	}

}
