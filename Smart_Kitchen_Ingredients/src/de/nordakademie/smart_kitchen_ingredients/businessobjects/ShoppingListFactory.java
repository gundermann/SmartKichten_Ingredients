package de.nordakademie.smart_kitchen_ingredients.businessobjects;

public interface ShoppingListFactory {

	ShoppingListItem createShoppingListItem(boolean bought,
			Ingredient ingredient);

}
