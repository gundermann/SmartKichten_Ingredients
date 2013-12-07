package de.nordakademie.smart_kitchen_ingredients.shoppinglist;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import de.nordakademie.smart_kitchen_ingredients.R;

public class ShoppingDateAlarmReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		createNotification(context);
	}

	public void createNotification(Context context) {
		// Intent intent = new Intent(context, ShoppingDateActivity.class);
		// PendingIntent pIntent = PendingIntent
		// .getActivity(context, 0, intent, 0);
		NotificationCompat.Builder builder = new NotificationCompat.Builder(
				context).setSmallIcon(R.drawable.ic_launcher)
				.setTicker("Einkaufen").setContentTitle("Einkaufen")
				// .setContentText("Einkaufen").setContentIntent(pIntent)
				.addAction(R.drawable.ic_launcher, "Action Button", null)
				.setAutoCancel(true);

		NotificationManager notificationmanager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		notificationmanager.notify(0, builder.build());

	}
}
