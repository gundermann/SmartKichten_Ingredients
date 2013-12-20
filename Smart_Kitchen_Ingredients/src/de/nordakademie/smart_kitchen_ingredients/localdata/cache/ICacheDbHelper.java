package de.nordakademie.smart_kitchen_ingredients.localdata.cache;
/**
 * 
 * @author Niels Gundermann
 *
 */
import java.util.List;

public abstract interface ICacheDbHelper<T> {
	List<T> getDatabaseEntries();

	T getExplicitItem(String title);

}
