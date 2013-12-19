package de.nordakademie.smart_kitchen_ingredients.scheduling;

import android.app.Activity;
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
import android.widget.ListAdapter;
import android.widget.ListView;
import de.nordakademie.smart_kitchen_ingredients.IngredientsApplication;
import de.nordakademie.smart_kitchen_ingredients.R;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.IDate;
import de.nordakademie.smart_kitchen_ingredients.collector.AdapterFactory;
import de.nordakademie.smart_kitchen_ingredients.stock.StoredIngredientActivity;

/**
 * 
 * @author Frauke Trautmann
 * 
 */

public class ShoppingDateListActivity extends Activity implements
		OnClickListener, OnItemClickListener {

	private static final String TAG = StoredIngredientActivity.class
			.getSimpleName();

	private ListView shoppingDateList;
	private ImageButton addShoppingDate;

	private IngredientsApplication app;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_layout);

		shoppingDateList = (ListView) findViewById(R.id.shoppingList);
		addShoppingDate = (ImageButton) findViewById(R.id.addNewShoppingItem);

		addShoppingDate.setOnClickListener(this);

		app = (IngredientsApplication) getApplication();

		updateDateList();
		shoppingDateList.setOnItemClickListener(this);
		Log.i(TAG, "datelist updated");

	}

	@Override
	protected void onResume() {
		super.onResume();
		updateDateList();
	}

	private void updateDateList() {
		ListAdapter adapter = new AdapterFactory<IDate>()
				.createDateAdapter(app);
		shoppingDateList.setAdapter(adapter);
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
				ShoppingDateListActivity.this);
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
