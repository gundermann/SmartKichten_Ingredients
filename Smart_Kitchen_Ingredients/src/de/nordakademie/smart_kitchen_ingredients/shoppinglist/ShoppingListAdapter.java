package de.nordakademie.smart_kitchen_ingredients.shoppinglist;

import java.util.TreeSet;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import de.nordakademie.smart_kitchen_ingredients.IngredientsApplication;
import de.nordakademie.smart_kitchen_ingredients.R;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.IShoppingListItem;

/**
 * Der Adapter realisiert die abhakbare Liste.
 * 
 * @author Niels Gundermann
 * 
 */
public class ShoppingListAdapter extends ArrayAdapter<IShoppingListItem> {
	private IngredientsApplication app;
	private CheckBox checkBox;
	private TextView textView;

	public ShoppingListAdapter(IngredientsApplication application) {
		super(application.getApplicationContext(),
				R.layout.checkable_rowlayout, R.id.labelOfCheckableList);
		app = application;
		setupShoppingItems();
	}

	public void setupShoppingItems() {
		TreeSet<IShoppingListItem> ingredients = new TreeSet<IShoppingListItem>();
		ingredients.addAll(app.getShoppingDbHelper().getAllShoppingItems());
		for (IShoppingListItem item : ingredients) {
			add(item);
		}
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) app.getApplicationContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.checkable_rowlayout, parent,
				false);
		textView = (TextView) rowView.findViewById(R.id.labelOfCheckableList);
		checkBox = (CheckBox) rowView.findViewById(R.id.buyedCheck);
		checkBox.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				IShoppingListItem item = getItem(position);
				if (!item.isBought()) {
					item.setBought(true);
				} else {
					item.setBought(false);
				}
				app.getShoppingDbHelper().updateShoppingItem(item);
				app.sendBroadcast(new Intent(IngredientsApplication.CHANGING),
						"de.nordakademie.smart_kitchen_ingredients.SHOPPING_LIST_CHANGING");
			}
		});

		updateLayout(getItem(position).isBought());
		textView.setText(getItem(position).getName());
		return rowView;
	}

	private void updateLayout(boolean bought) {
		if (bought) {
			textView.setPaintFlags(textView.getPaintFlags()
					| Paint.STRIKE_THRU_TEXT_FLAG);
			checkBox.setChecked(true);
		}
	}

}
