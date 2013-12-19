package de.nordakademie.smart_kitchen_ingredients.stock;

import java.util.List;
import java.util.TreeSet;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import de.nordakademie.smart_kitchen_ingredients.IngredientsApplication;
import de.nordakademie.smart_kitchen_ingredients.R;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.IIngredient;

public class StockListAdapter extends ArrayAdapter<IIngredient> {

	private SharedPreferences prefs;
	private IngredientsApplication app;
	private TextView nameTextView;
	private TextView stockTextView;

	public StockListAdapter(IngredientsApplication application) {
		super(application, R.layout.stored_rowlayout, R.id.labelOfStockList);
		prefs = PreferenceManager.getDefaultSharedPreferences(application);
		app = application;
		setupItems();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) app.getApplicationContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View rowView = inflater.inflate(R.layout.stored_rowlayout, parent,
				false);
		stockTextView = (TextView) rowView
				.findViewById(R.id.labelOfAmountFromList);
		nameTextView = (TextView) rowView.findViewById(R.id.labelOfStockList);
		updateLayout(getItem(position));

		return rowView;
	}

	private void updateLayout(IIngredient item) {
		nameTextView.setTextColor(Color.BLACK);
		int quantity = getQuantityOfItem(item);

		// TODO quick and dirty
		if (getQuantityOfItem(item) <= Integer.valueOf(prefs.getString(item
				.getUnit().toString(), String.valueOf(item.getUnit()
				.getDefaultMinimum())))) {
			stockTextView.setTextColor(Color.RED);
		} else {
			stockTextView.setTextColor(Color.GREEN);
		}

		nameTextView.setText(item.getName());
		stockTextView.setText(quantity + " " + item.getUnit());
	}

	private int getQuantityOfItem(IIngredient item) {
		return app.getStoredDbHelper().getQuantity(item);
	}

	private void setupItems() {
		TreeSet<IIngredient> shoppingItems = new TreeSet<IIngredient>();
		shoppingItems.addAll(getAllStoredItems());
		for (IIngredient item : shoppingItems) {
			add(item);
		}
	}

	private List<IIngredient> getAllStoredItems() {
		return app.getStoredDbHelper().getAllStoredIngredients();
	}

}
