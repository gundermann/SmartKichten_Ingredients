package de.nordakademie.smart_kitchen_ingredients.collector;

import java.util.List;

/**
* @author frederic.oppermann
* @date 16.12.2013
* @description
* @param <T>
*/
public interface IDatabaseHelper<T> {
	List<T> getDatabaseEntries();
}
