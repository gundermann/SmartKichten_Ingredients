package de.nordakademie.smart_kitchen_ingredients.localdata;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import de.nordakademie.smart_kitchen_ingredients.IngredientsApplication;
import de.nordakademie.smart_kitchen_ingredients.onlinedata.Connector;
import de.nordakademie.smart_kitchen_ingredients.onlinedata.IServerHandler;
import de.nordakademie.smart_kitchen_ingredients.onlinedata.ServerHandler;

public class CacheUpdaterService extends Service {

	private static final String TAG = CacheUpdaterService.class.getSimpleName();
	IngredientsApplication app;

	@Override
	public void onCreate() {
		super.onCreate();
		Log.i(TAG, "onCreate");
		
		IServerHandler handler = new ServerHandler(new Connector());
		ICacheRecipes cacher = new RecipeData(getApplicationContext());
		
		cacher.cacheAllIngredients(handler.getIngredientListFromServer());
		cacher.cacheAllRecipes(handler.getRecipeListFromServer());
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.i(TAG, "onStartCommand");
		return START_STICKY;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.i(TAG, "onDestroy");
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

}
