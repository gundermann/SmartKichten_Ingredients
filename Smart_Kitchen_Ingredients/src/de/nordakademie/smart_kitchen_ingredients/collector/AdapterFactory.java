package de.nordakademie.smart_kitchen_ingredients.collector;

import java.util.List;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

public class AdapterFactory<T> implements IAdapterFactory<T> {

	@Override
	public ListAdapter createAdapter(Context applicationContext, int entryView,
			List<T> elements) {
		return new ArrayAdapter<T>(applicationContext, entryView, elements);
	}

}
