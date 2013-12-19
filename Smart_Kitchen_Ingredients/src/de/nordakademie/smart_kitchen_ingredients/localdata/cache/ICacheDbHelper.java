package de.nordakademie.smart_kitchen_ingredients.localdata.cache;

import java.util.List;

public interface ICacheDbHelper<T> {
	List<T> getDatabaseEntries();

	T getExplicitItem(String title);

}
