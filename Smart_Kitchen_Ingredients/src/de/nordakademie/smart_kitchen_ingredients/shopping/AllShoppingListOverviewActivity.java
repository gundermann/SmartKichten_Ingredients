package de.nordakademie.smart_kitchen_ingredients.shopping;

import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import de.nordakademie.smart_kitchen_ingredients.AbstractActivity;
import de.nordakademie.smart_kitchen_ingredients.R;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.IShoppingList;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.ShoppingList;
import de.nordakademie.smart_kitchen_ingredients.factories.AdapterFactory;
import de.nordakademie.smart_kitchen_ingredients.scheduling.ShoppingDateOverviewActivity;
import de.nordakademie.smart_kitchen_ingredients.stock.StockOverviewActivity;

/**
 * 
 * @author Frauke Trautmann
 * 
 */

public class AllShoppingListOverviewActivity extends AbstractActivity implements
		OnClickListener, OnItemClickListener, InsertNameDialogListener,
		OnItemLongClickListener {

	private static String TAG = AllShoppingListOverviewActivity.class
			.getSimpleName();
	private ListView shoppingListsView;
	private ImageButton btAddNewShoppingList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.all_shopping_list_overview_layout);

		btAddNewShoppingList = (ImageButton) findViewById(R.id.addNewShoppingItem);
		btAddNewShoppingList.setOnClickListener(this);
		shoppingListsView = (ListView) findViewById(R.id.shoppingList);

		ListAdapter adapter = AdapterFactory.createShoppingListAdapter(
				getApplicationContext(), R.layout.list_view_entry,
				getShoppingLists());

		shoppingListsView.setAdapter(adapter);
		shoppingListsView.setOnItemClickListener(this);

		shoppingListsView.setOnItemLongClickListener(this);
		Log.i(TAG, "created");

	}

	private List<IShoppingList> getShoppingLists() {
		return app.getShoppingDbHelper().getAllShoppingLists();
	}

	@Override
	public void onClick(View v) {
		DialogFragment dialog = new InsertNameDialog(app);
		dialog.show(getSupportFragmentManager(), TAG);
	}

	@Override
	protected void onResume() {
		super.onResume();
		updateShoppingList();
	};

	private void updateShoppingList() {
		shoppingListsView.setAdapter(AdapterFactory.createShoppingListAdapter(
				app, android.R.layout.simple_list_item_1, getShoppingLists()));
		Log.i(TAG, "shoppinglist updated");
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> adapterView, View view,
			int position, long id) {
		AlertDialog.Builder adb = new AlertDialog.Builder(
				AllShoppingListOverviewActivity.this);
		adb.setTitle(R.string.deleteShoppingListTitle);
		adb.setMessage(R.string.deleteShoppingListSure);
		final int positionToRemove = position;
		adb.setNegativeButton(android.R.string.cancel, null);
		adb.setPositiveButton(android.R.string.ok,
				new AlertDialog.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						IShoppingList shoppingLists = (IShoppingList) shoppingListsView
								.getItemAtPosition(positionToRemove);
						delteShoppingList(shoppingLists);
						updateShoppingList();
					}
				});
		adb.show();
		return true;
	}

	private void delteShoppingList(IShoppingList shoppingLists) {
		app.getShoppingDbHelper().delete(shoppingLists);

	}

	@Override
	public void onItemClick(AdapterView<?> adapterView, View view,
			int position, long id) {
		startActivity(new Intent(getApplicationContext(),
				ShoppingListIngredientsActivity.class).putExtra(
				"shoppingListName", ((IShoppingList) shoppingListsView
						.getItemAtPosition(position)).getName()));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.shopping, menu);
		Log.i(TAG, "menu inflated");
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_shoppingdate:
			startNextActivity(ShoppingDateOverviewActivity.class);
			break;
		case R.id.menu_edit_stored_items:
			startNextActivity(StockOverviewActivity.class);
			break;
		case R.id.menu_barcode_apikey:
			startActivity(new Intent(getApplicationContext(),
					SmartKitchenIngredientsPrefsActivity.class));
		default:
			break;
		}
		return true;
	}

	@Override
	public void onPositiveFinishedDialog(String name) {
		app.getShoppingDbHelper().addItem(new ShoppingList(name));
		startActivity(new Intent(getApplicationContext(),
				ShoppingListIngredientsActivity.class).putExtra(
				"shoppingListName", name));
	}

}
