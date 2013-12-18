package de.nordakademie.smart_kitchen_ingredients.shoppinglist;

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
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import de.nordakademie.smart_kitchen_ingredients.R;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.IShoppingList;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.ShoppingList;
import de.nordakademie.smart_kitchen_ingredients.collector.AdapterFactory;
import de.nordakademie.smart_kitchen_ingredients.dialog.InsertNameDialog;
import de.nordakademie.smart_kitchen_ingredients.dialog.InsertNameDialogListener;
import de.nordakademie.smart_kitchen_ingredients.scheduling.ShoppingDateListActivity;
import de.nordakademie.smart_kitchen_ingredients.stock.StoredIngredientActivity;

/**
 * 
 * @author Frauke Trautmann
 * 
 */

public class ShoppingListActivity extends AbstractActivity implements
		OnClickListener, OnItemClickListener, InsertNameDialogListener,
		OnItemLongClickListener {

	private static String TAG = ShoppingListActivity.class.getSimpleName();
	private ListView shoppingListView;
	private ImageButton btAddNewShoppingList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// don't destroy dialog on rotate
		int FLAG = WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM;
		getWindow().setFlags(FLAG, FLAG);
		setContentView(R.layout.list_layout);
		btAddNewShoppingList = (ImageButton) findViewById(R.id.addNewShoppingItem);
		btAddNewShoppingList.setOnClickListener(this);
		shoppingListView = (ListView) findViewById(R.id.shoppingList);

		AdapterFactory<IShoppingList> adapterFactory = new AdapterFactory<IShoppingList>();
		ListAdapter adapter = adapterFactory.createAdapter(
				getApplicationContext(), R.layout.list_view_entry, getName());

		shoppingListView.setAdapter(adapter);
		shoppingListView.setOnItemClickListener(this);

		shoppingListView.setOnItemLongClickListener(this);
		Log.i(TAG, "created");

	}

	private List<IShoppingList> getName() {
		// TreeSet<IShoppingList> listName = new TreeSet<IShoppingList>();
		// listName.addAll(app.getShoppingDbHelper().getAllShoppingLists());
		// List<IShoppingList> shoppingList = new ArrayList<IShoppingList>(
		// listName);
		// return shoppingList;
		return app.getShoppingDbHelper().getAllShoppingLists();
	}

	@Override
	public void onClick(View v) {
		DialogFragment dialog = new InsertNameDialog();
		dialog.show(getSupportFragmentManager(), TAG);
	}

	@Override
	protected void onResume() {
		super.onResume();
		updateShoppingList();
	};

	private void updateShoppingList() {
		ListAdapter adapter = new AdapterFactory<IShoppingList>()
				.createAdapter(app, android.R.layout.simple_list_item_1,
						getName());
		shoppingListView.setAdapter(adapter);
		Log.i(TAG, "shoppinglist updated");
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> adapterView, View view,
			int position, long id) {
		AlertDialog.Builder adb = new AlertDialog.Builder(
				ShoppingListActivity.this);
		adb.setTitle(R.string.deleteShoppingListTitle);
		adb.setMessage(R.string.deleteShoppingListSure);
		final int positionToRemove = position;
		adb.setNegativeButton(android.R.string.cancel, null);
		adb.setPositiveButton(android.R.string.ok,
				new AlertDialog.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						IShoppingList shoppingLists = (IShoppingList) shoppingListView
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
				"shoppingListName", ((IShoppingList) shoppingListView
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
			startNextActivity(ShoppingDateListActivity.class);
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
	public void onPositiveFinishedDialog(String name) {
		app.getShoppingDbHelper().addItem(new ShoppingList(name));
		startActivity(new Intent(getApplicationContext(),
				ShoppingListIngredientsActivity.class).putExtra(
				"shoppingListName", name));
	}
}
