package de.nordakademie.smart_kitchen_ingredients;

import java.util.List;

public interface CheckableList {

	List<String> getValues();

	List<ShoppingItem> getShoppingItems();

	void deleteAndUpdateValueAtPosition(int position);
}
