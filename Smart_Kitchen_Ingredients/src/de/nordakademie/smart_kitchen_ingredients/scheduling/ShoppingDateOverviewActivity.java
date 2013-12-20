package de.nordakademie.smart_kitchen_ingredients.scheduling;

import java.util.List;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListAdapter;
import de.nordakademie.smart_kitchen_ingredients.R;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.IDate;
import de.nordakademie.smart_kitchen_ingredients.factories.AdapterFactory;
import de.nordakademie.smart_kitchen_ingredients.stock.AbstractListActivity;

/**
 * 
 * @author Frauke Trautmann
 * 
 */

public class ShoppingDateOverviewActivity extends AbstractListActivity<IDate>
		implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initElements();
	}

	@Override
	public void onClick(View v) {
		startNextActivity(ShoppingDateActivity.class);
	}

	private void deleteDate(IDate date) {
		AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
		Intent broadcast_intent = new Intent(getApplicationContext(),
				ShoppingDateAlarmReceiver.class);
		int intentFlag = app.getDateDbHelper().getIntentFlagByTime(
				date.getTimestamp());
		PendingIntent pendingIntent = PendingIntent.getBroadcast(
				getApplicationContext(), 0, broadcast_intent, intentFlag);

		alarmManager.cancel(pendingIntent);
		app.getDateDbHelper().remove(date);
		updateList();
	}

	@Override
	protected AlertDialog getDialog(final int position) {
		AlertDialog.Builder dialog = new AlertDialog.Builder(
				ShoppingDateOverviewActivity.this);
		dialog.setTitle(R.string.deleteDateTitle);
		dialog.setMessage(R.string.delteDateSure);
		dialog.setNegativeButton(android.R.string.cancel, null);
		dialog.setPositiveButton(android.R.string.ok,
				new AlertDialog.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						IDate date = (IDate) getList().getItemAtPosition(
								position);
						deleteDate(date);
					}
				});
		return dialog.create();
	}

	@Override
	protected ListAdapter getAdapter() {
		return AdapterFactory.createDateAdapter(app);
	}

	@Override
	protected List<IDate> getElements() {
		return app.getDateDbHelper().getAllDates();
	}

}
