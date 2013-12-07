package de.nordakademie.smart_kitchen_ingredients.shoppinglist;

import de.nordakademie.smart_kitchen_ingredients.ModifyableList;
import de.nordakademie.smart_kitchen_ingredients.R;
import de.nordakademie.smart_kitchen_ingredients.R.id;
import de.nordakademie.smart_kitchen_ingredients.R.layout;
import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

public class ShoppingListAdapter extends ArrayAdapter<String> {
	private final Context context;
	private final ModifyableList list;

	public ShoppingListAdapter(Context context, ModifyableList list) {
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
		TextView textView = (TextView) rowView
				.findViewById(R.id.labelOfCheckableList);
		CheckBox checkBox = (CheckBox) rowView.findViewById(R.id.buyedCheck);
		checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				list.deleteAndUpdateValueAtPosition(position);
			}
		});

		if (list.getShoppingItems().get(position).isBought()) {
			textView.setPaintFlags(textView.getPaintFlags()
					| Paint.STRIKE_THRU_TEXT_FLAG);
			checkBox.setChecked(true);
		}

		textView.setText(list.getValues().get(position));

		return rowView;
	}

}
