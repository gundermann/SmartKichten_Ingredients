package de.nordakademie.smart_kitchen_ingredients.businessobjects;

/**
 * 
 * @author Frauke Trautmann
 * 
 */

public class ShoppingListItemFactory implements IShoppingListItemFactory {

	@Override
	public IShoppingListItem createShoppingListItem(String title, int quantity,
			Unit unit, boolean bought) {
		IShoppingListItem item = new ShoppingListItem(title, quantity, unit,
				bought);
		return item;

	}
}
