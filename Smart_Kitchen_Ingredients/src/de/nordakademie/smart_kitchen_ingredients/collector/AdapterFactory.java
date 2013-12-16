package de.nordakademie.smart_kitchen_ingredients.collector;

import java.util.List;

import android.content.Context;
import android.widget.ListAdapter;

/**
 * @author frederic.oppermann
 * @date 16.12.2013
 * @description
 * @param <T>
 */
public class AdapterFactory<T> implements IAdapterFactory<T> {

	@Override
	public ListAdapter createAdapter(Context applicationContext, int entryView,
			List<T> elements) {
		return new CollectorAdapter<T>(applicationContext, entryView,
				entryView, elements);
	}
}
