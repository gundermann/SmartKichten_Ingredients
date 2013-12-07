package de.nordakademie.smart_kitchen_ingredients.businessobjects;

public class ShoppingListItemFactoryImpl implements ShoppingListItemFactory {

	@Override
	public ShoppingListItem createShoppingListItem(String title, int amount,
			Unit unit, boolean bought) {
		ShoppingListItem item = new ShoppingListItemImpl(title, amount, unit,
				bought);
		return item;

	}

}
