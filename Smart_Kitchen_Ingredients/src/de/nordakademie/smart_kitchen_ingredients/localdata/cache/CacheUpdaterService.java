package de.nordakademie.smart_kitchen_ingredients.localdata.cache;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import de.nordakademie.smart_kitchen_ingredients.IngredientsApplication;

public class CacheUpdaterService extends Service {

	private static final String TAG = CacheUpdaterService.class.getSimpleName();

	@Override
	public void onCreate() {
		super.onCreate();
		IngredientsApplication app = (IngredientsApplication) getApplication();
		// new FetchDataAsyncTask<IIngredient>(null,
		// app.getIngredientsDbHelper(),
		// this).execute();
		// new FetchDataAsyncTask<IRecipe>(null, app.getRecipeDbHelper(), this)
		// .execute();
		Log.i(TAG, "onCreate");
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

	// @Override
	// public void update(AsyncTask<Void, Void, List<IIngredient>> task) {
	//
	// }
	//
	// @Override
	// public void update(AsyncTask<Void, Void, List<IRecipe>> task) {
	//
	// }

}
