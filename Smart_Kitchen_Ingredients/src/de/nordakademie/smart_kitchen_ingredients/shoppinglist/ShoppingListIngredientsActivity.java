package de.nordakademie.smart_kitchen_ingredients.shoppinglist;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.TreeSet;

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
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.zxing.client.android.IntentIntegrator;
import com.google.zxing.client.android.IntentResult;

import de.nordakademie.smart_kitchen_ingredients.IngredientsApplication;
import de.nordakademie.smart_kitchen_ingredients.R;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.IShoppingListItem;
import de.nordakademie.smart_kitchen_ingredients.collector.AdapterFactory;
import de.nordakademie.smart_kitchen_ingredients.collector.IngredientCollectorActivity;
import de.nordakademie.smart_kitchen_ingredients.stock.StoredIngredientActivity;

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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_layout);
		currentShoppingListName = getIntent().getExtras().getString(
				"shoppingListName");
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
		ListAdapter adapter = new AdapterFactory<IShoppingListItem>()
				.createCheckableAdapter(app, currentShoppingListName);
		shoppingListView.setAdapter(adapter);
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
			startNextActivity(StoredIngredientActivity.class);
			break;
		default:
			break;
		}
		return true;
	}

	@Override
	public void onClick(View view) {
		startActivity(new Intent(getApplicationContext(),
				IngredientCollectorActivity.class).putExtra("shoppingListName",
				currentShoppingListName));
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		IntentResult scanningResult = IntentIntegrator.parseActivityResult(
				requestCode, resultCode, intent);
		try {
			String itemDescription = app.getBarcodeEvaluator()
					.getItemDescription(scanningResult.getContents());
			if (evaluateBarcodeScan(itemDescription.toLowerCase(Locale.GERMAN))) {
				makeLongToast(R.string.scansuccess);
			} else {
				makeLongToast(R.string.scanfault);
			}
		} catch (NullPointerException npe) {
			makeLongToast(R.string.scanerror);
		}
	}

	private boolean evaluateBarcodeScan(String content) {
		for (IShoppingListItem shoppingItem : getShoppingItems()) {
			if (content.contains(shoppingItem.getName().toLowerCase(
					Locale.GERMAN))) {
				app.getShoppingDbHelper()
						.getShoppingItem(shoppingItem.getName())
						.setBought(true);
				return true;
			}
		}
		return false;
	}

}
