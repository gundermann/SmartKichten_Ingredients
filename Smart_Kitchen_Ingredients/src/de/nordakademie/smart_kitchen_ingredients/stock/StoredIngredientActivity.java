package de.nordakademie.smart_kitchen_ingredients.stock;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import de.nordakademie.smart_kitchen_ingredients.IngredientsApplication;
import de.nordakademie.smart_kitchen_ingredients.R;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.IIngredient;
import de.nordakademie.smart_kitchen_ingredients.collector.AddStoredIngredientActivity;

/**
 * 
 * 
 * @author niels
 * 
 */
public class StoredIngredientActivity extends Activity implements
		OnClickListener, OnItemLongClickListener {

	private static final String TAG = StoredIngredientActivity.class
			.getSimpleName();

	IngredientsApplication app;
	ListView stockList;
	ImageButton addStoredIngredientButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.stock_layout);

		app = (IngredientsApplication) getApplication();

		addStoredIngredientButton = (ImageButton) findViewById(R.id.addNewStoredItem);
		addStoredIngredientButton.setOnClickListener(this);

		stockList = (ListView) findViewById(R.id.stockList);
		stockList.setOnItemLongClickListener(this);

		updateStockList();
		Log.i(TAG, "created");
	}

	private void updateStockList() {
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, getStoredValues());
		stockList.setAdapter(adapter);
		Log.i(TAG, "shoppinglist updated");
	}

	@Override
	public void onClick(View view) {
		Intent intent = new Intent(getApplicationContext(),
				AddStoredIngredientActivity.class);
		startActivity(intent);
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> adapter, View view,
			final int position, long arg3) {
		Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(stockList.getItemAtPosition(position).toString());
		builder.setCancelable(true);
		builder.setPositiveButton("Delete",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						app.getStoredDbHelper().getStoredIngredient(
								getTitleFromList(position));
					}

				});

		builder.setNeutralButton("Change",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Intent intent = new Intent(getApplicationContext(),
								AddStoredIngredientActivity.class);
						intent.putExtra("ingredientTitle",
								getTitleFromList(position));
						startActivity(intent);
					}
				});
		builder.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});
		AlertDialog dialog = builder.create();
		dialog.show();
		return true;
	}

	private String getTitleFromList(int position) {
		String listItem = stockList.getItemAtPosition(position).toString();
		return listItem.substring(0, listItem.indexOf(' '));
	}

	public List<String> getStoredValues() {
		List<String> values = new ArrayList<String>();
		for (IIngredient item : getStoredItems()) {
			values.add(item.getTitle() + " - "
					+ String.valueOf(item.getAmount()) + " " + item.getUnit());
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

}
