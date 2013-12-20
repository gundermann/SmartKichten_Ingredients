package de.nordakademie.smart_kitchen_ingredients.scheduling;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import de.nordakademie.smart_kitchen_ingredients.AbstractActivity;
import de.nordakademie.smart_kitchen_ingredients.R;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.IDate;
import de.nordakademie.smart_kitchen_ingredients.factories.AdapterFactory;

/**
 * 
 * @author Frauke Trautmann
 * 
 */

public class ShoppingDateOverviewActivity extends AbstractActivity implements
		OnClickListener, OnItemClickListener {

	private ListView shoppingDateList;
	private ImageButton addShoppingDate;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.date_overview_layout);
		initElemts();
		updateDateList();
	}

	private void initElemts() {
		shoppingDateList = (ListView) findViewById(R.id.shoppingList);
		addShoppingDate = (ImageButton) findViewById(R.id.addNewShoppingItem);
		addShoppingDate.setOnClickListener(this);
		shoppingDateList.setOnItemClickListener(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		updateDateList();
	}

	private void updateDateList() {
		shoppingDateList.setAdapter(AdapterFactory.createDateAdapter(app));
		Log.i(TAG, "datelist updated");
	}

	@Override
	public void onClick(View v) {
		Intent dateIntent = new Intent(this, ShoppingDateActivity.class);
		startActivity(dateIntent);

	}

	@Override
	public void onItemClick(AdapterView<?> adapterView, View view,
			int position, long id) {
		AlertDialog.Builder adb = new AlertDialog.Builder(
				ShoppingDateOverviewActivity.this);
		adb.setTitle(R.string.deleteDateTitle);
		adb.setMessage(R.string.delteDateSure);
		final int positionToRemove = position;
		adb.setNegativeButton(android.R.string.cancel, null);
		adb.setPositiveButton(android.R.string.ok,
				new AlertDialog.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						IDate date = (IDate) shoppingDateList
								.getItemAtPosition(positionToRemove);
						deleteDate(date);
					}

				});
		adb.show();
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
		updateDateList();
	}
}
