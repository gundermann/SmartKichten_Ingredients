/**
 * 
 */
package de.nordakademie.smart_kitchen_ingredients;

import java.util.List;

import de.nordakademie.smart_kitchen_ingredients.R;
import de.nordakademie.smart_kitchen_ingredients.businessobjects.IListElement;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * @author frederic.oppermann
 * @date 16.12.2013
 * @description
 */
public class CollectorAdapter<T> extends ArrayAdapter<T> {

	private List<T> items;
	private Context context;

	/**
	 * @param context
	 * @param resource
	 * @param textViewResourceId
	 * @param items
	 */
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
