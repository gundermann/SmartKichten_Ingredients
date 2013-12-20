package de.nordakademie.smart_kitchen_ingredients.shopping;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.TreeSet;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.google.zxing.client.android.IntentIntegrator;
import com.google.zxing.client.android.IntentResult;

import de.nordakademie.smart_kitchen_ingredients.AbstractActivity;
import de.nordakademie.smart_kitchen_ingredients.IngredientsApplication;
import de.nordakademie.smart_kitchen_ingredients.R;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.IShoppingListItem;
import de.nordakademie.smart_kitchen_ingredients.collector.ShoppingListIngredientCollectorActivity;
import de.nordakademie.smart_kitchen_ingredients.factories.AdapterFactory;
import de.nordakademie.smart_kitchen_ingredients.shopping.barcodescan.CheckBarcodeAysncTask;
import de.nordakademie.smart_kitchen_ingredients.stock.StockOverviewActivity;

/**
 * 
 * @author Niels Gundermann
 * 
 */
public class ShoppingListIngredientsActivity extends AbstractActivity implements
		OnClickListener {

	private static String TAG = ShoppingListIngredientsActivity.class
			.getSimpleName();
	private ListView shoppingListView;
	private ImageButton btAddNewShoppingItem;
	private BroadcastReceiver notifyShoppingdataChange;
	private String currentShoppingListName;
	private SharedPreferences prefs;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shopping_list_layout);
		prefs = PreferenceManager.getDefaultSharedPreferences(this);
		TextView listName = (TextView) findViewById(R.id.shoppingListName);
		currentShoppingListName = getIntent().getExtras().getString(
				"shoppingListName");
		listName.setText(currentShoppingListName);
		btAddNewShoppingItem = (ImageButton) findViewById(R.id.addNewShoppingItem);
		btAddNewShoppingItem.setOnClickListener(this);
		shoppingListView = (ListView) findViewById(R.id.shoppingList);
		setupShoppingList();
		if (getIntent().getExtras() != null) {
			String shoppingListName = (String) getIntent().getExtras().get(
					"shoppingListName");
			Log.d(TAG, shoppingListName);
		}
		Log.i(TAG, "created");
	}

	private void setupShoppingList() {
		TextView emptyView = new TextView(getApplicationContext());
		emptyView.setText(R.string.emptyShoppingList);
		shoppingListView.setEmptyView(findViewById(android.R.id.empty));
	}

	@Override
	protected void onResume() {
		super.onResume();
		updateShoppingIngredientsList();
		notifyShoppingdataChange = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				updateShoppingIngredientsList();
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
		Log.i(TAG, "broadcastreceiver unregisted");
	}

	private List<IShoppingListItem> getShoppingItems() {
		List<IShoppingListItem> shoppingItems = new ArrayList<IShoppingListItem>();
		TreeSet<IShoppingListItem> ingredients = new TreeSet<IShoppingListItem>();
		ingredients.addAll(app.getShoppingDbHelper().getAllShoppingItems(
				currentShoppingListName));
		for (IShoppingListItem item : ingredients) {
			shoppingItems.add(item);
		}
		return shoppingItems;
	}

	private void updateShoppingIngredientsList() {
		shoppingListView.setAdapter(AdapterFactory.createCheckableAdapter(app,
				currentShoppingListName));
		Log.i(TAG, "shoppinglist updated");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.shopping_ingredient, menu);
		Log.i(TAG, "menu inflated");
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_clean_shoppingList:
			app.getShoppingDbHelper().cleanShoppingIngredients();
			updateShoppingIngredientsList();
			break;
		case R.id.menu_qrscan:
			IntentIntegrator scanIntegrator = new IntentIntegrator(this);
			scanIntegrator.initiateScan();
			break;
		case R.id.menu_edit_stored_items:
			startNextActivity(StockOverviewActivity.class);
			break;
		default:
			break;
		}
		return true;
	}

	@Override
	public void onClick(View view) {
		startActivity(new Intent(getApplicationContext(),
				ShoppingListIngredientCollectorActivity.class).putExtra("shoppingListName",
				currentShoppingListName));
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		IntentResult scanningResult = IntentIntegrator.parseActivityResult(
				requestCode, resultCode, intent);
		try {
			if (app.isNetworkConnected()) {
				String apikey = prefs.getString("barcodeApiKey",
						"E1C9A73C52A822FB");
				new CheckBarcodeAysncTask(scanningResult.getContents(),
						app.getBarcodeEvaluator(), getShoppingItems(),
						currentShoppingListName, app.getShoppingDbHelper(),
						apikey, this).execute();
			}
		} catch (NullPointerException npe) {
			makeLongToast(R.string.scanerror);
		}
	}

	public void evaluateBarcodeScan(String content) {
		boolean success = false;
		for (IShoppingListItem shoppingItem : getShoppingItems()) {
			if (content.contains(shoppingItem.getName().toLowerCase(
					Locale.GERMAN))) {
				app.getShoppingDbHelper()
						.getShoppingItem(shoppingItem.getName(),
								currentShoppingListName).setBought(true);
				success = true;
				break;
			}
		}
		if (success) {
			app.informUser(R.string.scansuccess);
		} else {
			app.informUser(R.string.scanfault);
		}
	}
}
