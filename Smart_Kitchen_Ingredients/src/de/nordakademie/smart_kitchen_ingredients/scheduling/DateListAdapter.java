package de.nordakademie.smart_kitchen_ingredients.scheduling;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.TreeSet;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import de.nordakademie.smart_kitchen_ingredients.IngredientsApplication;
import de.nordakademie.smart_kitchen_ingredients.R;

public class DateListAdapter extends ArrayAdapter<IDate> {

	private IngredientsApplication app;
	private TextView nameTextView;
	private TextView dateTextView;

	public DateListAdapter(IngredientsApplication application) {
		super(application.getApplicationContext(), R.layout.stock_layout,
				R.id.labelOfStockList);
		app = application;
		setupItems();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) app.getApplicationContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View rowView = inflater.inflate(R.layout.stored_rowlayout, parent,
				false);
		dateTextView = (TextView) rowView
				.findViewById(R.id.labelOfAmountFromList);
		nameTextView = (TextView) rowView.findViewById(R.id.labelOfStockList);
		updateLayout(getItem(position));

		return rowView;
	}

	private void updateLayout(IDate item) {
		dateTextView.setTextColor(Color.BLACK);
		nameTextView.setTextColor(Color.BLACK);
		nameTextView.setText(item.getTitle());
		java.util.Date date = new java.util.Date(item.getTimestamp());
		SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm",
				Locale.GERMAN);
		dateTextView.setText(formatter.format(date));
	}

	private void setupItems() {
		TreeSet<IDate> shoppingItems = new TreeSet<IDate>();
		shoppingItems.addAll(getAllItems());
		for (IDate item : shoppingItems) {
			add(item);
		}
	}

	private List<IDate> getAllItems() {
		return app.getDateDbHelper().getAllDates();
	}
}
