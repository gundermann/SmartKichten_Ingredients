package de.nordakademie.smart_kitchen_ingredients.collector;

import java.util.List;

import android.content.Context;
import android.widget.ListAdapter;
import de.nordakademie.smart_kitchen_ingredients.IngredientsApplication;
import de.nordakademie.smart_kitchen_ingredients.scheduling.DateListAdapter;
import de.nordakademie.smart_kitchen_ingredients.shoppinglist.ShoppingListAdapter;
import de.nordakademie.smart_kitchen_ingredients.stock.StockListAdapter;

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

	@Override
	public ListAdapter createCheckableAdapter(IngredientsApplication application) {
		return new ShoppingListAdapter(application);
	}

	@Override
	public ListAdapter createStoreAdapter(IngredientsApplication application) {
		return new StockListAdapter(application);
	}

	public ListAdapter createDateAdapter(IngredientsApplication application) {
		return new DateListAdapter(application);
	}

}
