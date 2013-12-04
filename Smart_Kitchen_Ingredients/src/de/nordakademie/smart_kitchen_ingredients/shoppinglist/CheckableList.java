package de.nordakademie.smart_kitchen_ingredients.shoppinglist;

import java.util.List;

import de.nordakademie.smart_kitchen_ingredients.businessobjects.ShoppingItem;

public interface CheckableList {

	List<String> getValues();

	List<ShoppingItem> getShoppingItems();

	void deleteAndUpdateValueAtPosition(int position);
}
