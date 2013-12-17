package de.nordakademie.smart_kitchen_ingredients.localdata;

import java.util.List;

public interface IDatabaseHelper<T> {
	List<T> getDatabaseEntries();
	
	
}
