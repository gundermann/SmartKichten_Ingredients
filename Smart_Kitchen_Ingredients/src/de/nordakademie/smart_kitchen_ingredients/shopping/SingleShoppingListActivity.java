package de.nordakademie.smart_kitchen_ingredients.shopping;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.TreeSet;

import android.app.AlertDialog;
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
import android.widget.ListAdapter;
import android.widget.TextView;

import com.google.zxing.client.android.IntentIntegrator;
import com.google.zxing.client.android.IntentResult;

import de.nordakademie.smart_kitchen_ingredients.IngredientsApplication;
import de.nordakademie.smart_kitchen_ingredients.R;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.IShoppingListItem;
import de.nordakademie.smart_kitchen_ingredients.collector.ShoppingListIngredientCollectorActivity;
import de.nordakademie.smart_kitchen_ingredients.factories.AdapterFactory;
import de.nordakademie.smart_kitchen_ingredients.shopping.barcodescan.CheckBarcodeAysncTask;
import de.nordakademie.smart_kitchen_ingredients.stock.AbstractListActivity;
import de.nordakademie.smart_kitchen_ingredients.stock.StockOverviewActivity;

/**
 * 
 * @author Niels Gundermann
 * 
 */
public class SingleShoppingListActivity extends
		AbstractListActivity<IShoppingListItem> implements OnClickListener {

	private BroadcastReceiver notifyShoppingdataChange;
	private String currentShoppingListName;
	private SharedPreferences prefs;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shopping_list_layout);
		prefs = PreferenceManager.getDefaultSharedPreferences(this);
		TextView listName = (TextView) findViewById(R.id.shoppingListName);
		initElements();
		if (getIntent().getExtras() != null) {
			currentShoppingListName = getIntent().getExtras().getString(
					"shoppingListName");
		}
		listName.setText(currentShoppingListName);
	}

	@Override
	protected void onResume() {
		super.onResume();
		notifyShoppingdataChange = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				updateList();
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
			updateList();
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
				ShoppingListIngredientCollectorActivity.class).putExtra(
				"shoppingListName", currentShoppingListName));
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
						app.getBarcodeEvaluator(), getElements(),
						currentShoppingListName, app.getShoppingDbHelper(),
						apikey, this).execute();
			}
		} catch (NullPointerException npe) {
			makeLongToast(R.string.scanerror);
		}
	}

	public void evaluateBarcodeScan(String content) {
		boolean success = false;
		for (IShoppingListItem shoppingItem : getElements()) {
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

	@Override
	protected AlertDialog getDialog(int position) {
		// TODO anders abstrahieren
		return null;
	}

	@Override
	protected List<IShoppingListItem> getElements() {
		List<IShoppingListItem> shoppingItems = new ArrayList<IShoppingListItem>();
		TreeSet<IShoppingListItem> ingredients = new TreeSet<IShoppingListItem>();
		ingredients.addAll(app.getShoppingDbHelper().getAllShoppingItems(
				currentShoppingListName));
		for (IShoppingListItem item : ingredients) {
			shoppingItems.add(item);
		}
		return shoppingItems;

	}

	@Override
	protected ListAdapter getAdapter() {
		return AdapterFactory.createCheckableAdapter(app,
				currentShoppingListName);
	}
}
