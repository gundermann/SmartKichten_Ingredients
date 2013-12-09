package de.nordakademie.smart_kitchen_ingredients.shoppinglist;

import java.util.ArrayList;
import java.util.List;

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
import de.nordakademie.smart_kitchen_ingredients.IngredientsApplication;
import de.nordakademie.smart_kitchen_ingredients.ModifyableList;
import de.nordakademie.smart_kitchen_ingredients.R;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.IShoppingListItem;
import de.nordakademie.smart_kitchen_ingredients.ingredient_management.IngredientRegistrationActivity;

public class ShoppingActivity extends Activity implements ModifyableList,
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

		default:
			break;
		}
		return true;
	}

	@Override
	public List<String> getValues() {
		List<String> values = new ArrayList<String>();
		for (IShoppingListItem item : app.getDbHelper().getAllShoppingItems()) {
			values.add(item.getTitle());
		}
		return values;
	}

	@Override
	public void deleteAndUpdateValueAtPosition(int position) {
		IShoppingListItem item = app.getDbHelper().getAllShoppingItems()
				.get(position);
		item.setBought(true);
		app.getDbHelper().updateShoppingItem(item);
		updateShoppingList();
	}

	@Override
	public List<IShoppingListItem> getShoppingItems() {
		return app.getDbHelper().getAllShoppingItems();
	}

	@Override
	public void onClick(View view) {
		Intent intent = new Intent(getApplicationContext(),
				IngredientRegistrationActivity.class);
		startActivity(intent);
	}

}
