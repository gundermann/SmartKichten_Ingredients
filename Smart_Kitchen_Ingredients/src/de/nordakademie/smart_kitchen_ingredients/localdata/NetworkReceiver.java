package de.nordakademie.smart_kitchen_ingredients.localdata;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.util.Log;

public class NetworkReceiver extends BroadcastReceiver {
	private static final String TAG = NetworkReceiver.class.getSimpleName();

	@Override
	public void onReceive(Context context, Intent intent) {
		if (intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY,
				true)) {
			Log.d(TAG, "startService");
			context.startService(new Intent(context, CacheUpdaterService.class));
		}
	}
}
