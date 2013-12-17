package de.nordakademie.smart_kitchen_ingredients.businessobjects;

public class ShoppingListItemFactory implements IShoppingListItemFactory {

	@Override
	public IShoppingListItem createShoppingListItem(String title, Unit unit,
			boolean bought) {
		IShoppingListItem item = new ShoppingListItem(title, unit, bought);
		return item;

	}

}
