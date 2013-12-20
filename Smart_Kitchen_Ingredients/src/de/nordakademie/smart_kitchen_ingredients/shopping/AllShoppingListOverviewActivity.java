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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
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

public class AllShoppingListOverviewActivity extends
		AbstractDeletableListActivity<IShoppingList> implements
		IInsertNameDialogListener, OnItemClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initElements();
		getList().setOnItemClickListener(this);
		Log.i(TAG, "created");
	}

	@Override
	public void onClick(View v) {
		DialogFragment dialog = new InsertNameDialog(app);
		dialog.show(getSupportFragmentManager(), TAG);
	}

	private void delteShoppingList(IShoppingList shoppingLists) {
		app.getShoppingListDbHelper().delete(shoppingLists);

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
			startNextActivity(SmartKitchenIngredientsPrefsActivity.class);
		default:
			break;
		}
		return true;
	}

	@Override
	public void onPositiveFinishedDialog(String name) {
		app.getShoppingListDbHelper().addItem(new ShoppingList(name));
		startActivity(new Intent(getApplicationContext(),
				SingleShoppingListActivity.class).putExtra("shoppingListName",
				name));
	}

	@Override
	protected AlertDialog getDialog(final int position) {
		AlertDialog.Builder dialog = new AlertDialog.Builder(
				AllShoppingListOverviewActivity.this);
		dialog.setTitle(R.string.deleteShoppingListTitle);
		dialog.setMessage(R.string.deleteShoppingListSure);
		dialog.setNegativeButton(android.R.string.cancel, null);
		dialog.setPositiveButton(android.R.string.ok,
				new AlertDialog.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						IShoppingList shoppingLists = (IShoppingList) getList()
								.getItemAtPosition(position);
						delteShoppingList(shoppingLists);
						updateList();
					}
				});
		return dialog.create();
	}

	@Override
	protected ListAdapter getAdapter() {
		return AdapterFactory.createShoppingListAdapter(
				getApplicationContext(), R.layout.list_view_entry,
				getElements());
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position,
			long arg3) {
		startActivity(new Intent(getApplicationContext(),
				SingleShoppingListActivity.class).putExtra("shoppingListName",
				((IShoppingList) getList().getItemAtPosition(position))
						.getName()));
	}

	@Override
	protected List<IShoppingList> getElements() {
		return app.getShoppingListDbHelper().getAllShoppingLists();
	}
}
