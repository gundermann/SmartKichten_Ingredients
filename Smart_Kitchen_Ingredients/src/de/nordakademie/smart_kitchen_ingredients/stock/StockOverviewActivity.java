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
import android.widget.ListAdapter;
import de.nordakademie.smart_kitchen_ingredients.QuantityPickerDialogListener;
import de.nordakademie.smart_kitchen_ingredients.R;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.IIngredient;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.IListElement;
import de.nordakademie.smart_kitchen_ingredients.collector.StoredIngredientCollectorActivity;
import de.nordakademie.smart_kitchen_ingredients.factories.AdapterFactory;
import de.nordakademie.smart_kitchen_ingredients.shopping.AbstractDeletableListActivity;

/**
 * 
 * 
 * @author niels
 * 
 */
public class StockOverviewActivity extends
		AbstractDeletableListActivity<IIngredient> implements
		OnSharedPreferenceChangeListener, QuantityPickerDialogListener {

	SharedPreferences prefs;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		prefs = PreferenceManager.getDefaultSharedPreferences(this);
		prefs.registerOnSharedPreferenceChangeListener(this);
		initElements();
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

	@Override
	public void onClick(View view) {
		startNextActivity(StoredIngredientCollectorActivity.class);
	}

	private String getTitleFromList(int position) {
		return getList().getItemAtPosition(position).toString();
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences preference,
			String key) {
		updateList();
	}

	@Override
	public void onPositiveFinishedDialog(IListElement element, int quantity) {
		app.getStoredDbHelper().insertOrUpdateIngredient((IIngredient) element,
				quantity);
		updateList();
	}

	@Override
	protected AlertDialog getDialog(int position) {
		Builder builder = new StoredIngredientOptionDialog(
				getTitleFromList(position), this);
		return builder.create();
	}

	@Override
	protected ListAdapter getAdapter() {
		return AdapterFactory.createStoreAdapter(app);
	}

	@Override
	protected List<IIngredient> getElements() {
		List<IIngredient> storedShoppingItems = new ArrayList<IIngredient>();
		TreeSet<IIngredient> ingredients = new TreeSet<IIngredient>();
		ingredients.addAll(app.getStoredDbHelper().getAllStoredIngredients());
		for (IIngredient item : ingredients) {
			storedShoppingItems.add(item);
		}
		return storedShoppingItems;
	}

}
