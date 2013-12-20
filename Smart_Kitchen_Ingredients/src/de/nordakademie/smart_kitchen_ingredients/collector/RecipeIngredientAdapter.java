package de.nordakademie.smart_kitchen_ingredients.collector;

import java.util.Map;
import java.util.TreeSet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import de.nordakademie.smart_kitchen_ingredients.IngredientsApplication;
import de.nordakademie.smart_kitchen_ingredients.R;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.IIngredient;

public class RecipeIngredientAdapter extends ArrayAdapter<IIngredient> {

	private IngredientsApplication app;
	private TextView nameTextView;
	private TextView stockTextView;
	private Map<IIngredient, Integer> entrySet;

	public RecipeIngredientAdapter(IngredientsApplication app,
			Map<IIngredient, Integer> entrySet) {
		super(app, R.layout.ingredients_with_amount_and_unit_rowlayout,
				R.id.labelOfList);
		this.app = app;
		this.entrySet = entrySet;
		setupItems();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) app.getApplicationContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View rowView = inflater.inflate(
				R.layout.ingredients_with_amount_and_unit_rowlayout, parent,
				false);
		stockTextView = (TextView) rowView
				.findViewById(R.id.labelOfAmountAndUnitFromList);
		nameTextView = (TextView) rowView.findViewById(R.id.labelOfList);
		updateLayout(getItem(position));

		return rowView;
	}

	private void updateLayout(IIngredient item) {
		int quantity = getQuantityOfItem(item);
		nameTextView.setText(item.getName());
		stockTextView.setText(quantity + " " + item.getUnit());
	}

	private int getQuantityOfItem(IIngredient item) {
		return entrySet.get(item);
	}

	private void setupItems() {
		TreeSet<IIngredient> shoppingItems = new TreeSet<IIngredient>();
		shoppingItems.addAll(entrySet.keySet());
		for (IIngredient item : shoppingItems) {
			add(item);
		}
	}
}
