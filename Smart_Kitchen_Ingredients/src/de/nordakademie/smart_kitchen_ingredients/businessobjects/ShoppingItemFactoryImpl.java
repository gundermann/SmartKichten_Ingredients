package de.nordakademie.smart_kitchen_ingredients.businessobjects;

public class ShoppingItemFactoryImpl implements ShoppingListFactory {

	public ShoppingListItem createShoppingListItem(boolean buyed) {
		ShoppingListItemImpl item = new ShoppingListItemImpl(buyed);
		return (ShoppingListItem) item;

	}

}
