package de.nordakademie.smart_kitchen_ingredients;

import java.util.List;

import de.nordakademie.smart_kitchen_ingredients.businessobjects.IShoppingListItem;

public interface ModifyableList {

	List<String> getValues();

	List<IShoppingListItem> getShoppingItems();

	void deleteAndUpdateValueAtPosition(int position);
}
