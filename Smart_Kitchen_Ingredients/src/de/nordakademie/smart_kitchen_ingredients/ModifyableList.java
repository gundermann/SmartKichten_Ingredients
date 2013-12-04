package de.nordakademie.smart_kitchen_ingredients;

import java.util.List;

import de.nordakademie.smart_kitchen_ingredients.businessobjects.ShoppingItem;

public interface ModifyableList {

	List<String> getValues();

	List<ShoppingItem> getShoppingItems();

	void deleteAndUpdateValueAtPosition(int position);
}
