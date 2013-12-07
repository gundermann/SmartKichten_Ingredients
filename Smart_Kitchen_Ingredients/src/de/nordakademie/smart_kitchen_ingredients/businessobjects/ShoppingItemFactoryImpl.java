package de.nordakademie.smart_kitchen_ingredients.businessobjects;

public class ShoppingItemFactoryImpl implements ShoppingListFactory {

	public ShoppingListItem createShoppingListItem(boolean bought,
			Ingredient ingredient) {
		ShoppingListItemImpl item = new ShoppingListItemImpl(bought, ingredient);
		return item;

	}

}
