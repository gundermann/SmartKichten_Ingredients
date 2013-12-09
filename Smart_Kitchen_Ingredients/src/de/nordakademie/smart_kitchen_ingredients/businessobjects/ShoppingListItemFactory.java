package de.nordakademie.smart_kitchen_ingredients.businessobjects;

public class ShoppingListItemFactory implements IShoppingListItemFactory {

	@Override
	public IShoppingListItem createShoppingListItem(String title, int amount,
			Unit unit, boolean bought) {
		IShoppingListItem item = new ShoppingListItem(title, amount, unit,
				bought);
		return item;

	}

}
