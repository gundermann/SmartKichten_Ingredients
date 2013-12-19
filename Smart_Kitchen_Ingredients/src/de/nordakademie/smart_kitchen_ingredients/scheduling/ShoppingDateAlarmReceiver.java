package de.nordakademie.smart_kitchen_ingredients.scheduling;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import de.nordakademie.smart_kitchen_ingredients.R;
import de.nordakademie.smart_kitchen_ingredients.shoppinglist.ShoppingListActivity;
import de.nordakademie.smart_kitchen_ingredients.shoppinglist.ShoppingListIngredientsActivity;

/**
 * Der Receiver wirft eine Notification je nachdem, wann die App an den Einkauf
 * erinnern soll.
 * 
 * @author Niels Gundermann
 * 
 */
public class ShoppingDateAlarmReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		createNotification(context);
	}

	public void createNotification(Context context) {
		Intent intent = new Intent(context, ShoppingListActivity.class);
		PendingIntent pIntent = PendingIntent
				.getActivity(context, 0, intent, 0);
		NotificationCompat.Builder builder = new NotificationCompat.Builder(
				context).setSmallIcon(R.drawable.ic_launcher)
				.setTicker("Einkaufen").setContentTitle("Einkaufen")
				.setContentText("Sie wollten heute einkaufen")
				.setContentIntent(pIntent)
				.addAction(R.drawable.ic_launcher, "Action Button", null)
				.setAutoCancel(true);

		NotificationManager notificationmanager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		notificationmanager.notify(0, builder.build());

	}
}
