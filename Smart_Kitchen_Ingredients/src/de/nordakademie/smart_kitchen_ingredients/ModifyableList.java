package de.nordakademie.smart_kitchen_ingredients;

import java.util.List;

import de.nordakademie.smart_kitchen_ingredients.businessobjects.ShoppingListItem;

public interface ModifyableList {

	List<String> getValues();

	List<ShoppingListItem> getShoppingItems();

	void deleteAndUpdateValueAtPosition(int position);
}
