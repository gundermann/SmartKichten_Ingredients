package de.nordakademie.smart_kitchen_ingredients.collector;

import java.util.List;

import android.content.Context;
import android.widget.ListAdapter;
import de.nordakademie.smart_kitchen_ingredients.IngredientsApplication;

/**
 * @author frederic.oppermann
 * @date 16.12.2013
 * @description
 * @param <T>
 */
public interface IAdapterFactory<T> {
	ListAdapter createAdapter(Context applicationContext, int entryView,
			List<T> elements);

	ListAdapter createCheckableAdapter(IngredientsApplication application,
			String currentShoppingListName);

	ListAdapter createStoreAdapter(IngredientsApplication application);

	ListAdapter createDateAdapter(IngredientsApplication application);
}
