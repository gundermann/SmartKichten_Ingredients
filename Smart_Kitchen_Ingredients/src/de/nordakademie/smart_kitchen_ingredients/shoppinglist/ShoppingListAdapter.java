package de.nordakademie.smart_kitchen_ingredients.shoppinglist;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import de.nordakademie.smart_kitchen_ingredients.R;

/**
 * Der Adapter realisiert die abhakbare Liste.
 * 
 * @author Niels Gundermann
 * 
 */
public class ShoppingListAdapter extends ArrayAdapter<String> {
	private final Context context;
	private final IModifyableList list;
	private CheckBox checkBox;
	private TextView textView;

	public ShoppingListAdapter(Context context, IModifyableList list) {
		super(context, R.layout.checkable_rowlayout, R.id.labelOfCheckableList,
				list.getValues());
		this.context = context;
		this.list = list;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.checkable_rowlayout, parent,
				false);
		textView = (TextView) rowView.findViewById(R.id.labelOfCheckableList);
		checkBox = (CheckBox) rowView.findViewById(R.id.buyedCheck);
		checkBox.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				String titleOfChangedItem = list.getValues().get(position);
				list.checkAndUpdateValueAtPosition(titleOfChangedItem);
			}
		});

		updateLayout(list.getShoppingItems().get(position).isBought());
		textView.setText(list.getValues().get(position));
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
