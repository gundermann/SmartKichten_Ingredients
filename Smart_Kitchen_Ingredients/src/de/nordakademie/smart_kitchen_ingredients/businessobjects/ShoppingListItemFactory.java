package de.nordakademie.smart_kitchen_ingredients.businessobjects;

public interface ShoppingListItemFactory {

	ShoppingListItem createShoppingListItem(String title, int amount,
			Unit unit, boolean bought);

}
