package de.nordakademie.smart_kitchen_ingredients;

import java.util.List;

import de.nordakademie.smart_kitchen_ingredients.businessobjects.ShoppingListItemImpl;

public interface ModifyableList {

	List<String> getValues();

	List<ShoppingListItemImpl> getShoppingItems();

	void deleteAndUpdateValueAtPosition(int position);
}
