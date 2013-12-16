package de.nordakademie.smart_kitchen_ingredients.collector;

import java.util.List;

import android.content.Context;
import android.widget.ListAdapter;

public interface IAdapterFactory<T> {
	ListAdapter createAdapter(Context applicationContext, int entryView,
			List<T> elements);
}
