package de.nordakademie.smart_kitchen_ingredients.ingredient_management;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import de.nordakademie.smart_kitchen_ingredients.R;
import de.nordakademie.smart_kitchen_ingredients.shoppinglist.IModifyableList;

public class DeletableArrayAdapter extends ArrayAdapter<String> {
	private final Context context;
	private final List<String> values;
	private final IModifyableList list;

	public DeletableArrayAdapter(Context context, IModifyableList list) {
		super(context, R.layout.rowlayout, R.id.labelOfDeletableList, list
				.getValues());
		this.context = context;
		values = list.getValues();
		this.list = list;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.rowlayout, parent, false);
		TextView textView = (TextView) rowView
				.findViewById(R.id.labelOfDeletableList);
		ImageButton imageButton = (ImageButton) rowView
				.findViewById(R.id.deleteButton);
		imageButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				String titleOfChangedItem = values.get(position);
				list.checkAndUpdateValueAtPosition(titleOfChangedItem);
			}

		});
		textView.setText(values.get(position));

		return rowView;
	}

}