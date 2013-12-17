package de.nordakademie.smart_kitchen_ingredients.stock;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import de.nordakademie.smart_kitchen_ingredients.R;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.IIngredient;
import de.nordakademie.smart_kitchen_ingredients.collector.AdapterFactory;
import de.nordakademie.smart_kitchen_ingredients.collector.AddStoredIngredientActivity;

/**
 * 
 * 
 * @author niels
 * 
 */
public class StoredIngredientActivity extends AbstractFragmentActivity
		implements OnClickListener, OnItemLongClickListener,
		OnSharedPreferenceChangeListener {

	private static final String TAG = StoredIngredientActivity.class
			.getSimpleName();

	ListView stockList;
	SharedPreferences prefs;
	ImageButton addStoredIngredientButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.stock_layout);
		prefs = PreferenceManager.getDefaultSharedPreferences(this);
		prefs.registerOnSharedPreferenceChangeListener(this);
		addStoredIngredientButton = (ImageButton) findViewById(R.id.addNewStoredItem);
		addStoredIngredientButton.setOnClickListener(this);
		stockList = (ListView) findViewById(R.id.stockList);
		stockList.setOnItemLongClickListener(this);

		updateStockList();
		Log.i(TAG, "created");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.stock_menu, menu);
		Log.i(TAG, "menu inflated");
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_stock_settings:
			startNextActivity(StockPrefsActivity.class);
			break;
		default:
			break;
		}
		return true;
	}

	private void updateStockList() {
		ListAdapter adapter = new AdapterFactory<IIngredient>()
				.createStoreAdapter(app);
		stockList.setAdapter(adapter);
		Log.i(TAG, "shoppinglist updated");
	}

	@Override
	public void onClick(View view) {
		startNextActivity(AddStoredIngredientActivity.class);
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> adapter, View view,
			final int position, long arg3) {
		Builder builder = new StoredIngredientOptionDialog(
				getTitleFromList(position), this);
		AlertDialog dialog = builder.create();
		dialog.show();
		return true;
	}

	private String getTitleFromList(int position) {
		String listItem = stockList.getItemAtPosition(position).toString();
		return listItem;
	}

	public List<String> getStoredValues() {
		List<String> values = new ArrayList<String>();
		for (IIngredient item : getStoredItems()) {
			values.add(item.getName() + " - "
					+ String.valueOf(item.getQuantity()) + " " + item.getUnit());
		}
		Log.i(TAG, "title of shoppingitems collected");
		return values;
	}

	public List<IIngredient> getStoredItems() {
		List<IIngredient> storedShoppingItems = new ArrayList<IIngredient>();
		TreeSet<IIngredient> ingredients = new TreeSet<IIngredient>();
		ingredients.addAll(app.getStoredDbHelper().getAllStoredIngredients());
		for (IIngredient item : ingredients) {
			storedShoppingItems.add(item);
		}
		Log.i(TAG, "sort ingredients from database");
		return storedShoppingItems;
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences preference,
			String key) {
		updateStockList();
	}

}
