package de.nordakademie.smart_kitchen_ingredients.collector;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import de.nordakademie.smart_kitchen_ingredients.R;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.IListElement;

/**
 * @author Frederic Oppermann
 * @date 16.12.2013
 * @description: implementation of an ArrayAdapter. Do always use the getName()
 *               and not toString() method to display item in a list.
 */
public class CollectorAdapter<T> extends ArrayAdapter<T> {

	private List<T> items;
	private Context context;

	public CollectorAdapter(Context context, int resource,
			int textViewResourceId, List<T> items) {
		super(context, resource, textViewResourceId, items);
		this.context = context;

		this.items = items;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflator = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflator.inflate(R.layout.list_view_entry, parent, false);
		TextView textView = (TextView) view.findViewById(R.id.listEntryText);
		textView.setText(((IListElement) items.get(position)).getName());
		return view;
	}
}
