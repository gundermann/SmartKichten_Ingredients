package de.nordakademie.smart_kitchen_ingredients.shoppinglist;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import de.nordakademie.smart_kitchen_ingredients.IngredientsApplication;
import de.nordakademie.smart_kitchen_ingredients.R;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.IShoppingListItem;
import de.nordakademie.smart_kitchen_ingredients.collectors.IngredientCollectorActivity;

/**
 * 
 * @author Niels Gundermann
 * 
 */
public class ShoppingActivity extends Activity implements IModifyableList,
		OnClickListener {

	private static String TAG = ShoppingActivity.class.getSimpleName();
	private ListView shoppingListView;
	private ImageButton btAddNewShoppingItem;
	private IngredientsApplication app;
	private BroadcastReceiver notifyShoppingdataChange;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shopping_layout);

		app = (IngredientsApplication) getApplication();
		btAddNewShoppingItem = (ImageButton) findViewById(R.id.addNewShoppingItem);
		btAddNewShoppingItem.setOnClickListener(this);
		shoppingListView = (ListView) findViewById(R.id.shoppingList);

		TextView emptyView = new TextView(getApplicationContext());
		emptyView.setText(R.string.emptyShoppingList);
		shoppingListView.setEmptyView(findViewById(android.R.id.empty));

		Log.i(TAG, "created");
	}

	@Override
	protected void onResume() {
		super.onResume();
		updateShoppingList();
		notifyShoppingdataChange = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				updateShoppingList();
				Log.d(this.getClass().getSimpleName(), "onReceive");
			}
		};

		IntentFilter broadcastIntentFilter = new IntentFilter(
				IngredientsApplication.CHANGING);
		registerReceiver(notifyShoppingdataChange, broadcastIntentFilter,
				IngredientsApplication.PERMISSION, null);
		Log.i(TAG, "broadcastreceiver registed");
	}

	@Override
	protected void onPause() {
		super.onPause();
		unregisterReceiver(notifyShoppingdataChange);
	}

	private void updateShoppingList() {
		ShoppingListAdapter adapter = new ShoppingListAdapter(
				getApplicationContext(), this);
		shoppingListView.setAdapter(adapter);
		Log.i(TAG, "shoppinglist updated");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.shopping, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_clean_shoppingList:
			Intent intent = new Intent(this, ShoppingDataCleanUpService.class);
			startService(intent);
			break;
		case R.id.menu_qrscan:
			IntentIntegrator scanIntegrator = new IntentIntegrator(this);
			scanIntegrator.initiateScan();
			break;
		case R.id.menu_shoppingdate:
			Intent dateIntent = new Intent(this, ShoppingDateActivity.class);
			startActivity(dateIntent);
			break;
		default:
			break;
		}
		return true;
	}

	@Override
	public List<String> getValues() {
		List<String> values = new ArrayList<String>();
		for (IShoppingListItem item : getShoppingItems()) {
			values.add(item.getTitle());
		}
		Log.i(TAG, "title of shoppingitems collected");
		return values;
	}

	@Override
	public void checkAndUpdateValueAtPosition(String title) {
		IShoppingListItem item = app.getDbHelper().getShoppingItem(title);
		if (!item.isBought()) {
			item.setBought(true);
		} else {
			item.setBought(false);
		}
		app.getDbHelper().updateShoppingItem(item);
		Log.i(TAG, "ingredient bought");
		updateShoppingList();
	}

	@Override
	public List<IShoppingListItem> getShoppingItems() {
		List<IShoppingListItem> shoppingItems = new ArrayList<IShoppingListItem>();
		TreeSet<IShoppingListItem> ingredients = new TreeSet<IShoppingListItem>();
		ingredients.addAll(app.getDbHelper().getAllShoppingItems());
		for (IShoppingListItem item : ingredients) {
			shoppingItems.add(item);
		}
		Log.i(TAG, "sort ingredients from database");

		return shoppingItems;
	}

	@Override
	public void onClick(View view) {
		Intent intent = new Intent(getApplicationContext(),
				IngredientCollectorActivity.class);
		startActivity(intent);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		IntentResult scanningResult = IntentIntegrator.parseActivityResult(
				requestCode, resultCode, intent);
		if (scanningResult != null) {
			String scanContent = scanningResult.getContents();
			for (IShoppingListItem shoppingItem : getShoppingItems()) {
				if (scanContent.contains(shoppingItem.getTitle())) {
					checkAndUpdateValueAtPosition(shoppingItem.getTitle());
					Toast toast = Toast.makeText(getApplicationContext(),
							getText(R.string.scansuccess), Toast.LENGTH_SHORT);
					toast.show();
					Log.i(TAG, "ingredients matches");
					break;
				}
			}
		} else {
			Toast toast = Toast.makeText(getApplicationContext(),
					getText(R.string.scanerror), Toast.LENGTH_SHORT);
			toast.show();
			Log.i(TAG, "scan finished with errors");
		}
	}

}
