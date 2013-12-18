package de.nordakademie.smart_kitchen_ingredients.shoppinglist;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import de.nordakademie.smart_kitchen_ingredients.R;
import de.nordakademie.smart_kitchen_ingredients.R.id;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.IShoppingList;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.ShoppingList;
import de.nordakademie.smart_kitchen_ingredients.collector.AdapterFactory;
import de.nordakademie.smart_kitchen_ingredients.collector.IngredientCollectorActivity;

/**
 * 
 * @author Frauke Trautmann
 * 
 */

public class ShoppingListActivity extends AbstractActivity implements
		OnClickListener, OnItemClickListener {

	private static String TAG = ShoppingListActivity.class.getSimpleName();
	private ListView shoppingListView;
	private ImageButton btAddNewShoppingList;
	private BroadcastReceiver notifyShoppingdataChange;
	private Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_layout);
		btAddNewShoppingList = (ImageButton) findViewById(R.id.addNewShoppingItem);
		btAddNewShoppingList.setOnClickListener(this);
		shoppingListView = (ListView) findViewById(R.id.shoppingList);

		AdapterFactory<IShoppingList> adapterFactory = new AdapterFactory<IShoppingList>();
		ListAdapter adapter = adapterFactory.createAdapter(
				getApplicationContext(), R.layout.list_view_entry, getName());

		shoppingListView.setAdapter(adapter);
		shoppingListView.setOnItemClickListener(this);
		Log.i(TAG, "created");
	}

	private List<IShoppingList> getName() {
		List<IShoppingList> shoppingList = new ArrayList<IShoppingList>();
		TreeSet<IShoppingList> listName = new TreeSet<IShoppingList>();
		listName.addAll(app.getShoppingDbHelper().getAllShoppingLists());
		for (IShoppingList item : listName) {
			shoppingList.add(item);
		}
		return shoppingList;
	}

	@Override
	public void onClick(View v) {
		final Dialog dialog = new Dialog(context);
		dialog.setContentView(R.layout.number_picker_layout);
		dialog.setTitle("Neue Einkaufsliste");

		final EditText listName = (EditText) dialog
				.findViewById(id.quantityPickerCurrentQuantityInput);
		Button dialogButton = (Button) dialog
				.findViewById(id.quantityPickerIncreaseButton);
		dialogButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				app.getShoppingDbHelper().addItem(
						new ShoppingList(listName.toString()));
				startNextActivity(IngredientCollectorActivity.class);
			}
		});

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
	public void onItemClick(AdapterView<?> adapterView, View view,
			int position, long id) {
		AlertDialog.Builder adb = new AlertDialog.Builder(
				ShoppingListActivity.this);
		startActivity(new Intent(getApplicationContext(),
				ShoppingListIngredientsActivity.class).putExtra(
				"shoppingListName", adapterView.getAdapter().getItem(position)
						.toString()));
	}

}