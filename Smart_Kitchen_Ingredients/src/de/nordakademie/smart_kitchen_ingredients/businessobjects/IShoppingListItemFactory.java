package de.nordakademie.smart_kitchen_ingredients.businessobjects;

public interface IShoppingListItemFactory {

	IShoppingListItem createShoppingListItem(String title, Unit unit,
			boolean bought);

}
