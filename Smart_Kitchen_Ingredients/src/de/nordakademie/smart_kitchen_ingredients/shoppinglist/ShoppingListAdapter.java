package de.nordakademie.smart_kitchen_ingredients.shoppinglist;

import java.util.List;
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
public class ShoppingListAdapter extends ArrayAdapter<IShoppingListItem>
		implements OnClickListener {
	private IngredientsApplication app;
	private CheckBox checkBox;
	private TextView textView;
	private int selectedPosition;

	public ShoppingListAdapter(IngredientsApplication application) {
		super(application.getApplicationContext(),
				R.layout.checkable_rowlayout, R.id.labelOfCheckableList);
		app = application;
		setupShoppingItems();
	}

	private void setupShoppingItems() {
		TreeSet<IShoppingListItem> shoppingItems = new TreeSet<IShoppingListItem>();
		shoppingItems.addAll(getAllShoppingItems());
		for (IShoppingListItem item : shoppingItems) {
			add(item);
		}
	}

	private List<IShoppingListItem> getAllShoppingItems() {
		return app.getShoppingDbHelper().getAllShoppingItems();
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) app.getApplicationContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View rowView = inflater.inflate(R.layout.checkable_rowlayout, parent,
				false);
		textView = (TextView) rowView.findViewById(R.id.labelOfCheckableList);
		checkBox = (CheckBox) rowView.findViewById(R.id.buyedCheck);
		checkBox.setOnClickListener(getListenerForPosition(position));

		updateLayout(getItem(position).isBought());
		textView.setText(getItem(position).getName());
		return rowView;
	}

	private OnClickListener getListenerForPosition(int position) {
		selectedPosition = position;
		return this;
	}

	private void updateShoppingItem(IShoppingListItem item) {
		app.getShoppingDbHelper().updateShoppingItem(item);
	}

	private void updateLayout(boolean bought) {
		if (bought) {
			textView.setPaintFlags(textView.getPaintFlags()
					| Paint.STRIKE_THRU_TEXT_FLAG);
			checkBox.setChecked(true);
		}
	}

	@Override
	public void onClick(View v) {
		IShoppingListItem item = getItem(selectedPosition);
		if (!item.isBought()) {
			item.setBought(true);
		} else {
			item.setBought(false);
		}
		updateShoppingItem(item);
		app.sendBroadcast(new Intent(IngredientsApplication.CHANGING),
				IngredientsApplication.PERMISSION);
	}

}
